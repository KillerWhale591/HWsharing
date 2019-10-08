package com.zeyufu.dimmer;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;


/**
 * A simple {@link Fragment} subclass.
 */
public class SliderFragment extends Fragment {

    private final int MAX_ALPHA = 1;
    private SeekBar skbSlider;
    private int sliderMax;
    OnTranspanrencyChangeListener tcl;

    public SliderFragment() {
        // Required empty public constructor
    }

    private SeekBar.OnSeekBarChangeListener listener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            tcl.passTransparency(MAX_ALPHA - (float) i / sliderMax);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        tcl = (OnTranspanrencyChangeListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_slider, container, false);
        skbSlider = v.findViewById(R.id.skbSlider);
        sliderMax = skbSlider.getMax();
        skbSlider.setOnSeekBarChangeListener(listener);
        return v;
    }

}
