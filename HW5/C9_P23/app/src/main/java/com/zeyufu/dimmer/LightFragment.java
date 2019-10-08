package com.zeyufu.dimmer;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class LightFragment extends Fragment {

    RelativeLayout layout;

    public LightFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_light, container, false);
        layout = (RelativeLayout) v;
        return v;
    }

    public void setTransparency(float alpha) {
        layout.setAlpha(alpha);
    }
}
