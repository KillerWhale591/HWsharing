package com.example.interfragmentcoomuication;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
//this will get inflated down below.

/**
 * A simple {@link Fragment} subclass.
 */
public class BottomFragment extends Fragment {

    private TextView txtFunnyMessage;
    private int[]food = {R.drawable.croissant, R.drawable.indian_curry, R.drawable.hotpot,
            R.drawable.squareburger2, R.drawable.scrabbleegg};
    private final String SQUARE_BURGER = "Delicious Burger";
    private final String CROISSANT = "French Croissant";
    private final String INDIAN_CURRY = "Indian Curry";
    private final String HOTPOT = "HotPot";
    private final String SCRABBLEEGG = "Scrabble Egg";


    ;

    public BottomFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_bottom, container, false);
        //separate me from return statement.
        txtFunnyMessage = (TextView)view.findViewById(R.id.txtFunnyMessage);      //need a chance to do this other stuff,
           //before returning the inflated view.
        return view;

    }

    //Receiving Team
    //It is best practice that this should be accessed via the main activity, not other fragments.
    public void setFunnyMessage(String msg){
        txtFunnyMessage.setText(msg);

    }
    public void setFunnyBackGround(String msg){
        try {
            if (msg.equals(SQUARE_BURGER)) {
                getView().setBackgroundResource(food[3]);

            } else if (msg.equals(INDIAN_CURRY)) {
                getView().setBackgroundResource(food[1]);

            } else if (msg.equals(SCRABBLEEGG)) {
                getView().setBackgroundResource(food[4]);

            } else if (msg.equals(HOTPOT)) {
                getView().setBackgroundResource(food[2]);

            } else if (msg.equals(CROISSANT)) {
                getView().setBackgroundResource(food[0]);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
