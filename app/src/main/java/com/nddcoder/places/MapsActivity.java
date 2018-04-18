package com.nddcoder.places;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.location.Location;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.directions.route.Segment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.nddcoder.places.adapters.PlaceTypesAdapter;
import com.nddcoder.places.adapters.PlacesAdapter;
import com.nddcoder.places.adapters.SegmentsAdapter;
import com.nddcoder.places.models.Geometry;
import com.nddcoder.places.models.ItemClickSupport;
import com.nddcoder.places.models.Place;
import com.nddcoder.places.models.PlaceType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.android.volley.VolleyLog.TAG;

public class MapsActivity extends FragmentActivity implements LocationListener, OnMapReadyCallback {

    private final int MY_PERMISSION_REQUEST_ACCESS_FINE_LOCATION = 1;

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;

    private LatLng mLastLocation;

    private RecyclerView rvTypes;
    private PlaceTypesAdapter placeTypesAdapter;
    private List<PlaceType> placeTypes;
    private List<PlaceType> searchTypes;

    private RecyclerView rvPlaces;
    private PlacesAdapter placesAdapter;
    private List<Place> places;

    private RecyclerView rvDirections;
    private SegmentsAdapter segmentsAdapter;
    private List<Segment> segments;

    private FloatingActionButton fabSearch, fabListPlace, fabSettings;
    private BottomSheetBehavior bottomSheetType;
    private BottomSheetBehavior bottomSheetPlace;
    private BottomSheetBehavior bottomSheetDirections;
    private TextView tvDuretionDistance;
    private EditText edtSearch;
    private Button btnViewPlacesOnMaps;

    private SharedPreferences sharedPreferences;
    private int currentMakerId;
    private Marker customMarker;

    private RequestQueue rq;

    public MapsActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        rq = Volley.newRequestQueue(MapsActivity.this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        init();
        initPlaceTypes();
        initPlaces();
        initDirections();
        addEvents();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void initPlaceTypes() {
        rvTypes = findViewById(R.id.rv_types);
        rvTypes.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        rvTypes.setLayoutManager(layoutManager);
        placeTypes = PlaceType.createPlaceTypes();
        searchTypes = PlaceType.createPlaceTypes();
        placeTypesAdapter = new PlaceTypesAdapter(placeTypes);
        rvTypes.setAdapter(placeTypesAdapter);
    }

    private void initPlaces() {
        rvPlaces = findViewById(R.id.rv_places);
        rvPlaces.setHasFixedSize(true);
        rvPlaces.setLayoutManager(new LinearLayoutManager(this));
        places = new ArrayList<>();
        placesAdapter = new PlacesAdapter(places);
        rvPlaces.setAdapter(placesAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        rvPlaces.addItemDecoration(itemDecoration);
    }

    private void initDirections() {
        tvDuretionDistance = findViewById(R.id.duretion_distance);
        rvDirections = findViewById(R.id.rvDirections);
        rvDirections.setHasFixedSize(true);
        rvDirections.setLayoutManager(new LinearLayoutManager(this));
        segments = new ArrayList<>();
        segmentsAdapter = new SegmentsAdapter(segments);
        rvDirections.setAdapter(segmentsAdapter);
    }

    private void init() {

        fabSearch = findViewById(R.id.fabSearch);
        fabListPlace = findViewById(R.id.fabListPlace);
        fabListPlace.setVisibility(View.INVISIBLE);
        fabSettings = findViewById(R.id.fabSettings);

        bottomSheetType = BottomSheetBehavior.from(findViewById(R.id.bottom_sheet_type));
        bottomSheetType.setHideable(true);
        bottomSheetType.setState(BottomSheetBehavior.STATE_HIDDEN);

        bottomSheetPlace = BottomSheetBehavior.from(findViewById(R.id.bottom_sheet_place));
        bottomSheetPlace.setHideable(true);
        bottomSheetPlace.setState(BottomSheetBehavior.STATE_HIDDEN);

        bottomSheetDirections = BottomSheetBehavior.from(findViewById(R.id.bottom_sheet_directions));
        bottomSheetDirections.setHideable(true);
        bottomSheetDirections.setState(BottomSheetBehavior.STATE_HIDDEN);

        edtSearch = findViewById(R.id.edit_search);
    }

    private void addEvents() {
        findViewById(R.id.head_direction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDirections.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        fabSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSearch();
            }
        });

        fabListPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickViewPlacesInBottomSheets();
            }
        });

        fabSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettings();
            }
        });

        //click in BottomSheetPlaceType
        ItemClickSupport.addTo(rvTypes).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                String radius = sharedPreferences.getString("list_radius", "5000");

//                log(radius);

                currentMakerId = getResources().getIdentifier("marker_" + searchTypes.get(position).getId(), "drawable", getApplicationContext().getPackageName());

                requestPlaces(searchTypes.get(position).getId(), mLastLocation, Integer.parseInt(radius));
//                setTitle(searchTypes.get(position).getName());
//                bottomSheetType.setPeekHeight(200);
//                bottomSheetType.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });

        //
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                s = s.toString().toLowerCase();
                final List<PlaceType> filteredList = new ArrayList<>();
                for (PlaceType t : placeTypes) {
                    String name = t.getName().toLowerCase();
                    if (name.contains(s)) {
                        filteredList.add(t);
                    }
                }
                searchTypes.clear();
                searchTypes.addAll(filteredList);
                placeTypesAdapter = new PlaceTypesAdapter(filteredList);
                rvTypes.setAdapter(placeTypesAdapter);
                placeTypesAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void openSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private void clickViewPlacesInBottomSheets() {
        bottomSheetPlace.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    private void clickSearch() {
        bottomSheetType.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSION_REQUEST_ACCESS_FINE_LOCATION);

            return;
        }

        addEventForMap();

    }

    private void addEventForMap() {
        enableMyLocation();
        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
//                log("mylocatio clicked");
                moveToLocation(mLastLocation);
                return false;
            }
        });

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                bottomSheetPlace.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                longClickOnMap(latLng);
            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                bottomSheetPlace.setState(BottomSheetBehavior.STATE_HIDDEN);
                return false;
            }
        });

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                infoWinDowClicked(marker);
            }
        });

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                if (marker.equals(customMarker)) {
                    customMarker.setPosition(marker.getPosition());
                }
            }
        });

        mMap.setOnInfoWindowLongClickListener(new GoogleMap.OnInfoWindowLongClickListener() {
            @Override
            public void onInfoWindowLongClick(Marker marker) {
                askToDeleteCustomMarker();
            }
        });

        moveToLocation(mLastLocation);
    }

    private void askToDeleteCustomMarker() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete this marker?").setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                customMarker.remove();
                customMarker = null;
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();
    }

    private void infoWinDowClicked(Marker marker) {

        if (marker.getTitle().equalsIgnoreCase(this.getString(R.string.find_direction))) {
            currentMakerId = -1;
        }

        bottomSheetPlace.setState(BottomSheetBehavior.STATE_HIDDEN);
        LatLng latLng = marker.getPosition();
        mMap.clear();
        direction(latLng);
        if (currentMakerId == -1) {
            mMap.addMarker(new MarkerOptions().position(latLng).title(marker.getTitle()).snippet(marker.getSnippet()));
        } else {
            mMap.addMarker(new MarkerOptions().position(latLng).title(marker.getTitle()).snippet(marker.getSnippet()).icon(BitmapDescriptorFactory.fromResource(currentMakerId)));
        }
    }

    private void longClickOnMap(LatLng latLng) {
        customMarker = mMap.addMarker(new MarkerOptions().position(latLng).draggable(true)
                .title(this.getString(R.string.find_direction)));
        customMarker.showInfoWindow();
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = new LatLng(location.getLatitude(), location.getLongitude());
    }

    private void enableMyLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSION_REQUEST_ACCESS_FINE_LOCATION);

            return;
        }

        // Access to the location has been granted to the app.
        mMap.setMyLocationEnabled(true);
    }

    private void moveToLocation(final LatLng latLng) {

        if (latLng == null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                enableMyLocation();
                                mLastLocation = new LatLng(location.getLatitude(), location.getLongitude());
                                moveToLocation(mLastLocation);
                            }
                        }
                    });
            return;
        }

//        log("Move to " + latLng.latitude + ", " + latLng.longitude);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng).zoom(13).build();
        mMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));
    }

    private void log(String mess) {
        Toast.makeText(this, mess, Toast.LENGTH_SHORT).show();
    }

    private void direction(LatLng end) {
//        bottomSheetPlace.setPeekHeight(0);
//        bottomSheetPlace.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetPlace.setState(BottomSheetBehavior.STATE_HIDDEN);


        bottomSheetDirections.setPeekHeight(tvDuretionDistance.getHeight() + 25);
//        bottomSheetDirections.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetDirections.setState(BottomSheetBehavior.STATE_COLLAPSED);

        Routing routing = new Routing.Builder()
                .travelMode(Routing.TravelMode.DRIVING)
                .language("VI")
                .withListener(new RoutingListener() {
                    @Override
                    public void onRoutingFailure(RouteException e) {

                    }

                    @Override
                    public void onRoutingStart() {

                    }

                    @Override
                    public void onRoutingSuccess(ArrayList<Route> arrayList, int i) {
                        List<Polyline> polylines = new ArrayList<>();
                        for (Route r : arrayList) {
                            tvDuretionDistance.setText(r.getDistanceText() + " (" + r.getDurationText() + ")");
                            segments.clear();
                            segments.addAll(r.getSegments());
                            segmentsAdapter.notifyDataSetChanged();

                            PolylineOptions polyOptions = new PolylineOptions();
                            polyOptions.color(Color.BLUE);
                            polyOptions.addAll(r.getPoints());
                            Polyline polyline = mMap.addPolyline(polyOptions);
                            polylines.add(polyline);
                        }
                        CameraPosition cameraPosition = new CameraPosition.Builder()
                                .target(mLastLocation).bearing(45).zoom(15).build();
                        mMap.animateCamera(CameraUpdateFactory
                                .newCameraPosition(cameraPosition));
                    }

                    @Override
                    public void onRoutingCancelled() {

                    }
                })
                .waypoints(mLastLocation, end)
                .build();
        routing.execute();
    }

    private void requestPlaces(final String type, LatLng latLng, int radius) {

//        log(this.getString(R.string.google_maps_web_key));
        StringBuilder googlePlacesUrl =
                new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlacesUrl.append("location=").append(latLng.latitude).append(",").append(latLng.longitude);
        googlePlacesUrl.append("&radius=").append(radius);
        googlePlacesUrl.append("&types=").append(type);
        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key=").append(this.getString(R.string.google_maps_web_key));

        JsonObjectRequest request = new JsonObjectRequest(googlePlacesUrl.toString(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject result) {

                        parseLocationResult(type, result);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "onErrorResponse: Error= " + error);
                        Log.e(TAG, "onErrorResponse: Error= " + error.getMessage());
                    }
                });

        rq.add(request);
    }

    private void parseLocationResult(String type, JSONObject result) {

        try {
            JSONArray jsonArray = result.getJSONArray("results");


            if (result.getString("status").equalsIgnoreCase("OK") && jsonArray.length() > 0) {
                mMap.clear();
                final List<Place> responsePlaces = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject place = jsonArray.getJSONObject(i);
                    Place p = new Place();

                    if (!place.isNull("name")) {
                        p.setName(place.getString("name"));
                    }

                    if (!place.isNull("vicinity")) {
                        p.setVicinity(place.getString("vicinity"));
                    }

                    p.setGeometry(new Geometry(new com.nddcoder.places.models.Location(
                            place.getJSONObject("geometry").getJSONObject("location").getDouble("lat"),
                            place.getJSONObject("geometry").getJSONObject("location").getDouble("lng")
                    )));

                    responsePlaces.add(p);
                }

                places.clear();
                places.addAll(responsePlaces);
                placesAdapter.notifyDataSetChanged();
                ItemClickSupport.addTo(rvPlaces).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
//                        mMap.clear();
                        Place place = responsePlaces.get(position);
                        com.nddcoder.places.models.Location location = place.getGeometry().getLocation();
                        LatLng latLng = new LatLng(location.getLat(), location.getLng());
//                        direction(latLng);
                        mMap.addMarker(new MarkerOptions().position(latLng).title(place.getName()).snippet(place.getVicinity()).icon(BitmapDescriptorFactory.fromResource(currentMakerId))).showInfoWindow();
                        moveToLocation(latLng);
                        bottomSheetPlace.setState(BottomSheetBehavior.STATE_HIDDEN);
                    }
                });

//                bottomSheetPlace.setPeekHeight(200);
//                bottomSheetPlace.setState(BottomSheetBehavior.STATE_COLLAPSED);

                mMap.clear();
                for (Place place : responsePlaces) {
                    com.nddcoder.places.models.Location location = place.getGeometry().getLocation();
                    LatLng latLng = new LatLng(location.getLat(), location.getLng());
                    mMap.addMarker(new MarkerOptions().position(latLng).title(place.getName()).snippet(place.getVicinity()).icon(BitmapDescriptorFactory.fromResource(currentMakerId)));
                }

                fabListPlace.setVisibility(View.VISIBLE);
                bottomSheetPlace.setState(BottomSheetBehavior.STATE_COLLAPSED);
                bottomSheetType.setState(BottomSheetBehavior.STATE_HIDDEN);
                return;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "parseLocationResult: Error=" + e.getMessage());
        }

        log("No result for " + type);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                    addEventForMap();

                } else {
                    Toast.makeText(this, "Cannot access current location", Toast.LENGTH_LONG).show();
                }

                break;
            }

        }
    }
}
