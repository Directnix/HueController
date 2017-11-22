package com.hemantithide.huecontroller.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hemantithide.huecontroller.API.ApiHandler;
import com.hemantithide.huecontroller.API.IApiResponse;
import com.hemantithide.huecontroller.MainActivity;
import com.hemantithide.huecontroller.Model.Light;
import com.hemantithide.huecontroller.R;

import java.util.ArrayList;


public class LightFragment extends Fragment implements IApiResponse {

    private OnListFragmentInteractionListener mListener;
    ArrayList<Light> lights = new ArrayList<>();
    LightRecyclerViewAdapter adapter;

    public LightFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApiHandler.getInstance(MainActivity.API_ADDRESS, this, getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_light_list, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;

            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            adapter = new LightRecyclerViewAdapter(this.lights, mListener);
            recyclerView.setAdapter(adapter);
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onLightsReceived(ArrayList<Light> lights) {
        for(Light l: lights)
            Log.i("LIGHT", l.toString());

        this.lights.clear();
        this.lights.addAll(lights);

        adapter.notifyDataSetChanged();

    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Light light);
    }
}
