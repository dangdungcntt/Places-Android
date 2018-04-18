package com.nddcoder.places.models;

import com.nddcoder.places.R;

import java.util.ArrayList;
import java.util.List;

public class PlaceType {
    private String id;
    private String name;
    private int icon;

    public PlaceType(String id, String name, int icon) {
        this.id = id;
        this.name = name;
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public static List<PlaceType> createPlaceTypes() {
        List<PlaceType> placeTypes = new ArrayList<>();
        placeTypes.add(new PlaceType("accounting", "Accounting", R.drawable.ic_accounting));
        placeTypes.add(new PlaceType("airport", "Airport", R.drawable.ic_airport));
        placeTypes.add(new PlaceType("amusement_park", "Amusement park", R.drawable.ic_amusement_park));
        placeTypes.add(new PlaceType("aquarium", "Aquarium", R.drawable.ic_aquarium));
        placeTypes.add(new PlaceType("art_gallery", "Art gallery", R.drawable.ic_art_gallery));
        placeTypes.add(new PlaceType("atm", "ATM", R.drawable.ic_atm));
        placeTypes.add(new PlaceType("bakery", "Bakery", R.drawable.ic_bakery));
        placeTypes.add(new PlaceType("bank", "Bank", R.drawable.ic_bank));
        placeTypes.add(new PlaceType("bar", "Bar", R.drawable.ic_bar));
        placeTypes.add(new PlaceType("beauty_salon", "Beauty salon", R.drawable.ic_beauty_salon));
        placeTypes.add(new PlaceType("bicycle_store", "Bicycle store", R.drawable.ic_bicycle_store));
        placeTypes.add(new PlaceType("book_store", "Book store", R.drawable.ic_book_store));
        placeTypes.add(new PlaceType("bowling_alley", "Bowling alley", R.drawable.ic_bowling_alley));
        placeTypes.add(new PlaceType("bus_station", "Bus station", R.drawable.ic_bus_station));
        placeTypes.add(new PlaceType("cafe", "Cafe", R.drawable.ic_cafe));
        placeTypes.add(new PlaceType("campground", "Campground", R.drawable.ic_campground));
        placeTypes.add(new PlaceType("car_dealer", "Car dealer", R.drawable.ic_car_dealer));
        placeTypes.add(new PlaceType("car_rental", "Car rental", R.drawable.ic_car_rental));
        placeTypes.add(new PlaceType("car_repair", "Car repair", R.drawable.ic_car_repair));
        placeTypes.add(new PlaceType("car_wash", "Car wash", R.drawable.ic_car_wash));
        placeTypes.add(new PlaceType("casino", "Casino", R.drawable.ic_casino));
        placeTypes.add(new PlaceType("cemetery", "Cemetery", R.drawable.ic_cemetery));
        placeTypes.add(new PlaceType("church", "Church", R.drawable.ic_church));
        placeTypes.add(new PlaceType("city_hall", "City hall", R.drawable.ic_city_hall));
        placeTypes.add(new PlaceType("clothing_store", "Clothing store", R.drawable.ic_clothing_store));
        placeTypes.add(new PlaceType("convenience_store", "Convenience store", R.drawable.ic_convenience_store));
        placeTypes.add(new PlaceType("courthouse", "Courthouse", R.drawable.ic_courthouse));
        placeTypes.add(new PlaceType("dentist", "Dentist", R.drawable.ic_dentist));
        placeTypes.add(new PlaceType("department_store", "Department store", R.drawable.ic_department_store));
        placeTypes.add(new PlaceType("doctor", "Doctor", R.drawable.ic_doctor));
        placeTypes.add(new PlaceType("electrician", "Electrician", R.drawable.ic_electrician));
        placeTypes.add(new PlaceType("electronics_store", "Electronics store", R.drawable.ic_electronics_store));
        placeTypes.add(new PlaceType("embassy", "Embassy", R.drawable.ic_embassy));
        placeTypes.add(new PlaceType("establishment", "Establishment", R.drawable.ic_establishment));
        placeTypes.add(new PlaceType("finance", "Finance", R.drawable.ic_finance));
        placeTypes.add(new PlaceType("fire_station", "Fire station", R.drawable.ic_fire_station));
        placeTypes.add(new PlaceType("florist", "Florist", R.drawable.ic_florist));
        placeTypes.add(new PlaceType("food", "Food", R.drawable.ic_food));
        placeTypes.add(new PlaceType("funeral_home", "Funeral home", R.drawable.ic_funeral_home));
        placeTypes.add(new PlaceType("furniture_store", "Furniture store", R.drawable.ic_furniture_store));
        placeTypes.add(new PlaceType("gas_station", "Gas station", R.drawable.ic_gas_station));
        placeTypes.add(new PlaceType("general_contractor", "General contractor", R.drawable.ic_general_contractor));
        placeTypes.add(new PlaceType("grocery_or_supermarket", "Grocery or supermarket", R.drawable.ic_grocery_or_supermarket));
        placeTypes.add(new PlaceType("gym", "Gym", R.drawable.ic_gym));
        placeTypes.add(new PlaceType("hair_care", "Hair care", R.drawable.ic_hair_care));
        placeTypes.add(new PlaceType("hardware_store", "Hardware store", R.drawable.ic_hardware_store));
        placeTypes.add(new PlaceType("health", "Health", R.drawable.ic_health));
        placeTypes.add(new PlaceType("hospital", "Hospital", R.drawable.ic_hospital));
        placeTypes.add(new PlaceType("insurance_agency", "Insurance agency", R.drawable.ic_insurance_agency));
        placeTypes.add(new PlaceType("jewelry_store", "Jewelry store", R.drawable.ic_jewelry_store));
        placeTypes.add(new PlaceType("laundry", "Laundry", R.drawable.ic_laundry));
        placeTypes.add(new PlaceType("lawyer", "Lawyer", R.drawable.ic_lawyer));
        placeTypes.add(new PlaceType("library", "Library", R.drawable.ic_library));
        placeTypes.add(new PlaceType("liquor_store", "Liquor store", R.drawable.ic_liquor_store));
        placeTypes.add(new PlaceType("locksmith", "Locksmith", R.drawable.ic_locksmith));
        placeTypes.add(new PlaceType("lodging", "Lodging", R.drawable.ic_lodging));
        placeTypes.add(new PlaceType("meal_delivery", "Meal delivery", R.drawable.ic_meal_delivery));
        placeTypes.add(new PlaceType("meal_takeaway", "Meal takeaway", R.drawable.ic_meal_takeaway));
        placeTypes.add(new PlaceType("mosque", "Mosque", R.drawable.ic_mosque));
        placeTypes.add(new PlaceType("movie_rental", "Movie rental", R.drawable.ic_movie_rental));
        placeTypes.add(new PlaceType("movie_theater", "Movie theater", R.drawable.ic_movie_theater));
        placeTypes.add(new PlaceType("moving_company", "Moving company", R.drawable.ic_moving_company));
        placeTypes.add(new PlaceType("museum", "Museum", R.drawable.ic_museum));
        placeTypes.add(new PlaceType("night_club", "Night club", R.drawable.ic_nightclub));
        placeTypes.add(new PlaceType("painter", "Painter", R.drawable.ic_painter));
        placeTypes.add(new PlaceType("park", "park", R.drawable.ic_park));
        placeTypes.add(new PlaceType("parking", "Parking", R.drawable.ic_parking));
        placeTypes.add(new PlaceType("pet_store", "Pet store", R.drawable.ic_pet_store));
        placeTypes.add(new PlaceType("pharmacy", "Pharmacy", R.drawable.ic_pharmacy));
        placeTypes.add(new PlaceType("physiotherapist", "Physiotherapist", R.drawable.ic_physiotherapist));
        placeTypes.add(new PlaceType("place_of_worship", "Place of worship", R.drawable.ic_place_of_worship));
        placeTypes.add(new PlaceType("plumber", "Plumber", R.drawable.ic_plumber));
        placeTypes.add(new PlaceType("police", "Police", R.drawable.ic_police));
        placeTypes.add(new PlaceType("post_office", "Post office", R.drawable.ic_post_office));
        placeTypes.add(new PlaceType("real_estate_agency", "Real estate agency", R.drawable.ic_real_estate_agency));
        placeTypes.add(new PlaceType("restaurant", "Restaurant", R.drawable.ic_restaurant));
        placeTypes.add(new PlaceType("roofing_contractor", "Roofing contractor", R.drawable.ic_roofting_contractor));
        placeTypes.add(new PlaceType("rv_park", "Rv park", R.drawable.ic_rv_park));
        placeTypes.add(new PlaceType("school", "School", R.drawable.ic_school));
        placeTypes.add(new PlaceType("shoe_store", "Shoe store", R.drawable.ic_shoe_store));
        placeTypes.add(new PlaceType("shopping_mall", "Shopping mall", R.drawable.ic_shopping_mall));
        placeTypes.add(new PlaceType("stadium", "Stadium", R.drawable.ic_stadium));
        placeTypes.add(new PlaceType("storage", "Storage", R.drawable.ic_storage));
        placeTypes.add(new PlaceType("store", "Store", R.drawable.ic_store));
        placeTypes.add(new PlaceType("subway_station", "Subway station", R.drawable.ic_subway_station));
        placeTypes.add(new PlaceType("synagogue", "Synagogue", R.drawable.ic_synagogue));
        placeTypes.add(new PlaceType("taxi_stand", "Taxi stand", R.drawable.ic_taxi_stand));
        placeTypes.add(new PlaceType("train_station", "Train station", R.drawable.ic_train_station));
        placeTypes.add(new PlaceType("transit_station", "Transit station", R.drawable.ic_transit_station));
        placeTypes.add(new PlaceType("travel_agency", "Travel agency", R.drawable.ic_travel_agency));
        placeTypes.add(new PlaceType("university", "University", R.drawable.ic_university));
        placeTypes.add(new PlaceType("veterinary_care", "Veterinary care", R.drawable.ic_veterinary_care));
        placeTypes.add(new PlaceType("zoo", "Zoo", R.drawable.ic_zoo));

        return placeTypes;
    }
}
