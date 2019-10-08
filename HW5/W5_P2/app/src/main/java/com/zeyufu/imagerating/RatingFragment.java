package com.zeyufu.imagerating;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;


/**
 * A simple {@link Fragment} subclass.
 */
public class RatingFragment extends Fragment {

    private ImageView imgCover;
    private RatingBar rtgBar;
    private int[] albums = new int[] {
        R.drawable.album1,
        R.drawable.album2,
        R.drawable.album3,
        R.drawable.album4,
        R.drawable.album5
    };

    private int currImg = 0;
    private float[] ratings = new float[] {0, 0, 0, 0, 0};

    public RatingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_rating, container, false);
        imgCover = v.findViewById(R.id.imgCover);
        rtgBar = v.findViewById(R.id.rtgBar);
        imgCover.setImageResource(albums[currImg]);

        rtgBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                ratings[currImg] = v;
            }
        });
        return v;
    }

    public void setAlbum(int increment) {
        currImg += increment;
        if (currImg < 0) currImg = albums.length - 1;
        if (currImg >= albums.length) currImg = 0;
        imgCover.setImageResource(albums[currImg]);
        rtgBar.setRating(ratings[currImg]);
    }

}
