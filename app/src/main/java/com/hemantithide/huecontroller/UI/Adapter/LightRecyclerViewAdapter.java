package com.hemantithide.huecontroller.UI.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hemantithide.huecontroller.UI.Fragments.LightFragment.OnListFragmentInteractionListener;
import com.hemantithide.huecontroller.Model.Light;
import com.hemantithide.huecontroller.R;

import java.util.List;


public class LightRecyclerViewAdapter extends RecyclerView.Adapter<LightRecyclerViewAdapter.ViewHolder> {

    private final List<Light> lights;
    private final OnListFragmentInteractionListener mListener;

    public LightRecyclerViewAdapter(List<Light> items, OnListFragmentInteractionListener listener) {
        lights = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_light, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Light light = lights.get(position);

        holder.tvName.setText(light.getName());
        holder.ivColor.setBackgroundColor(light.toHSVColor());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(light);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return lights.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tvName;
        public final ImageView ivColor;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvName = view.findViewById(R.id.fa_li_name);
            ivColor = view.findViewById(R.id.fa_li_iv_color);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
