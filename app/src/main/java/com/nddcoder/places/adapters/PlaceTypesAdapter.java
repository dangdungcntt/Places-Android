package com.nddcoder.places.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.nddcoder.places.models.PlaceType;
import com.nddcoder.places.R;

import java.util.ArrayList;
import java.util.List;

public class PlaceTypesAdapter  extends RecyclerView.Adapter<PlaceTypesAdapter.ViewHolder> implements Filterable {

    private List<PlaceType> placeTypes, tempItems, suggestions;
    private Context context;

    public PlaceTypesAdapter(List<PlaceType> placeTypes) {
        this.placeTypes = placeTypes;
        tempItems = new ArrayList<>(placeTypes);
        suggestions = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View typeView = inflater.inflate(R.layout.item_type, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(typeView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PlaceType placeType = placeTypes.get(position);

        ImageView imgType = holder.imgType;
        imgType.setImageResource(placeType.getIcon());
        TextView tvType = holder.tvType;
        tvType.setText(placeType.getName());
    }

    @Override
    public int getItemCount() {
        return placeTypes.size();
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgType;
        public TextView tvType;

        public ViewHolder(View itemView) {
            super(itemView);
            imgType = (ImageView) itemView.findViewById(R.id.img_type);
            tvType = (TextView) itemView.findViewById(R.id.tv_type);
        }
    }
}
