package com.zeyufu.imagerating;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class ControlFragment extends Fragment {

    private Button btnLeft;
    private Button btnRight;
    OnImageChangeListener listener;

    public ControlFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (OnImageChangeListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_control, container, false);
        btnLeft = v.findViewById(R.id.btnLeft);
        btnRight = v.findViewById(R.id.btnRight);

        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.changeImageIndex(-1);
            }
        });
        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.changeImageIndex(1);
            }
        });

        return v;
    }

}
