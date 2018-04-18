package com.nddcoder.places.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.directions.route.Segment;
import com.nddcoder.places.R;

import java.util.List;

public class SegmentsAdapter extends RecyclerView.Adapter<SegmentsAdapter.ViewHolder>{

    private List<Segment> segments;
    private Context context;

    public SegmentsAdapter(List<Segment> segments) {
        this.segments = segments;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View placeView = inflater.inflate(R.layout.item_directions, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(placeView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Segment segment = segments.get(position);

        TextView tvDirection = holder.tvDirection;
        tvDirection.setText(segment.getInstruction());

        TextView tvDuration = holder.tvDuration;
        tvDuration.setText(segment.getLength() + " m");
    }

    @Override
    public int getItemCount() {
        return segments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvDirection;
        public TextView tvDuration;

        public ViewHolder(View itemView) {
            super(itemView);

            tvDirection = (TextView) itemView.findViewById(R.id.directions);
            tvDuration = (TextView) itemView.findViewById(R.id.duration);
        }
    }
}
