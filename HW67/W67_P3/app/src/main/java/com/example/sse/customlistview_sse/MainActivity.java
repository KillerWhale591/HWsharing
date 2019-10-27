package com.example.sse.customlistview_sse;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public static final String KEY_EP_URL = "ep_url";
    private static final String KEY_SP_RATING = "sp_ratings";
    private static final String PREFIX_RATING = "ratings_";

    private
    ListView lvEpisodes;
    ListAdapter lvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvEpisodes = (ListView)findViewById(R.id.lvEpisodes);
        lvAdapter = new MyCustomAdapter(this.getBaseContext());
        lvEpisodes.setAdapter(lvAdapter);
        lvEpisodes.setOnItemClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Get ratings from SharedPreferences
        SharedPreferences stateInfo = getSharedPreferences(KEY_SP_RATING, Context.MODE_PRIVATE);
        MyCustomAdapter adapter = (MyCustomAdapter) lvAdapter;
        for (int i = 0; i < adapter.getCount(); i++) {
            float rt = stateInfo.getFloat(PREFIX_RATING + adapter.getItem(i), 0f);
            adapter.setRating(i, rt);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Store ratings into SharedPreferences
        MyCustomAdapter adapter = (MyCustomAdapter) lvAdapter;
        SharedPreferences stateInfo = getSharedPreferences(KEY_SP_RATING, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = stateInfo.edit();
        for (int i = 0; i < adapter.getCount(); i++) {
            editor.putFloat(PREFIX_RATING + adapter.getItem(i), adapter.getRating(i));
        }
        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar
        getMenuInflater().inflate(R.menu.my_test_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.mnu_sort_title) {
            // Sort by title
            MyCustomAdapter adapter = (MyCustomAdapter) lvAdapter;
            adapter.sortByTitle();
            lvEpisodes.setAdapter((ListAdapter) adapter);
            return true;
        }

        if (id == R.id.mnu_sort_rt) {
            // Sort by rating
            MyCustomAdapter adapter = (MyCustomAdapter) lvAdapter;
            adapter.sortByRatings();
            lvEpisodes.setAdapter((ListAdapter) adapter);
            return true;
        }

        if (id == R.id.mnu_audio) {
            // Play audio
            MediaPlayer mp = MediaPlayer.create(this, R.raw.live_long);
            mp.start();
            return true;
        }

        if (id == R.id.mnu_video) {
            // Play video
            Intent intent = new Intent(this, VideoActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);  //if none of the above are true, do the default and return a boolean.
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        // go to webpages
        MyCustomAdapter adapter = (MyCustomAdapter) lvAdapter;
        Intent intent = new Intent(this, WebpageActivity.class);
        intent.putExtra(MainActivity.KEY_EP_URL, adapter.epUrls[i]);
        startActivity(intent);
    }
}

class MyCustomAdapter extends BaseAdapter {

    private
     Context context;
     String episodes[];
     String  episodeDescriptions[];
     ArrayList<Integer> episodeImages;
     float[] mRatings;
     MyCustomItem[] epItems;
     String[] epUrls;

    public MyCustomAdapter(Context aContext) {

        context = aContext;  //saving the context we'll need it again.

        episodes = aContext.getResources().getStringArray(R.array.episodes);  //retrieving list of episodes predefined in strings-array "episodes" in strings.xml
        episodeDescriptions = aContext.getResources().getStringArray(R.array.episode_descriptions);


        episodeImages = new ArrayList<Integer>();
        episodeImages.add(R.drawable.st_spocks_brain);
        episodeImages.add(R.drawable.st_arena__kirk_gorn);
        episodeImages.add(R.drawable.st_this_side_of_paradise__spock_in_love);
        episodeImages.add(R.drawable.st_mirror_mirror__evil_spock_and_good_kirk);
        episodeImages.add(R.drawable.st_platos_stepchildren__kirk_spock);
        episodeImages.add(R.drawable.st_the_naked_time__sulu_sword);
        episodeImages.add(R.drawable.st_the_trouble_with_tribbles__kirk_tribbles);
        epItems = new MyCustomItem[this.getCount()];
        mRatings = new float[this.getCount()];
        epUrls = aContext.getResources().getStringArray(R.array.episode_urls);
    }

//STEP 3: Override and implement getCount(..), ListView uses this to determine how many rows to render.
    @Override
    public int getCount() {
        return episodes.length;

    }

//STEP 4: Override getItem/getItemId, we aren't using these, but we must override anyway.
    @Override
    public Object getItem(int position) {
        return episodes[position];
    }

    @Override
    public long getItemId(int position) {
        return position;  //Another call we aren't using, but have to do something since we had to implement (base is abstract).
    }

//STEP 5: Easy as A-B-C
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {  //convertView is Row (it may be null), parent is the layout that has the row Views.

//STEP 5a: Inflate the listview row based on the xml.
        View row;

        if (convertView == null){  //indicates this is the first time we are creating this row.
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);  //Inflater's are awesome, they convert xml to Java Objects!
            row = inflater.inflate(R.layout.listview_row, parent, false);
        }
        else
        {
            row = convertView;
        }


//STEP 5b: Now that we have a valid row instance, we need to get references to the views within that row and fill with the appropriate text and images.
        ImageView imgEpisode = (ImageView) row.findViewById(R.id.imgEpisode);  //Q: Notice we prefixed findViewByID with row, why?  A: Row, is the container.
        TextView tvEpisodeTitle = (TextView) row.findViewById(R.id.tvEpisodeTitle);
        TextView tvEpisodeDescription = (TextView) row.findViewById(R.id.tvEpisodeDescription);
        final RatingBar rtBar = (RatingBar) row.findViewById(R.id.rbEpisode);
        rtBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                mRatings[position] = v;
            }
        });
        rtBar.setRating(mRatings[position]);

        tvEpisodeTitle.setText(episodes[position]);
        tvEpisodeDescription.setText(episodeDescriptions[position]);
        imgEpisode.setImageResource(episodeImages.get(position).intValue());


//STEP 5c: That's it, the row has been inflated and filled with data, return it.
        return row;
    }

    /**
     * Get ratings in MainActivity
     * @param position
     * @return
     */
    public float getRating(int position) {
        return mRatings[position];
    }

    /**
     * Get set ratings in MainActivity
     * @param position
     * @param rt
     */
    public void setRating(int position, float rt) {
        mRatings[position] = rt;
    }

    /**
     * Sort by title alphabet order
     */
    public void sortByTitle() {
        setEpItems();
        Arrays.sort(epItems, new Comparator<MyCustomItem>() {
            @Override
            public int compare(MyCustomItem t0, MyCustomItem t1) {
                return t0.episode.compareTo(t1.episode);
            }
        });
        setSortedItems();
    }

    /**
     * Sort by rating high -> low
     */
    public void sortByRatings() {
        setEpItems();
        Arrays.sort(epItems, new Comparator<MyCustomItem>() {
            @Override
            public int compare(MyCustomItem t0, MyCustomItem t1) {
                return Float.compare(t1.epRating, t0.epRating);
            }
        });
        setSortedItems();
    }

    /**
     * Set array of MyCustomItem
     */
    private void setEpItems() {
        for (int i = 0; i < this.getCount(); i++) {
            MyCustomItem item = new MyCustomItem(episodes[i], episodeDescriptions[i], mRatings[i], episodeImages.get(i), epUrls[i]);
            epItems[i] = item;
        }
    }

    /**
     * Set array of MyCustomItem after sorted
     */
    private void setSortedItems() {
        for (int i = 0; i < epItems.length; i++) {
            episodes[i] = epItems[i].episode;
            episodeDescriptions[i] = epItems[i].epDescription;
            episodeImages.set(i, epItems[i].epImg);
            mRatings[i] = epItems[i].epRating;
            epUrls[i] = epItems[i].epUrl;
        }
    }
}

/**
 * Helper class for storing item attributes
 */
class MyCustomItem {

    String episode;
    String epDescription;
    float epRating;
    int epImg;
    String epUrl;

    public MyCustomItem (String ep, String desc, float rt, int img, String url) {
        this.episode = ep;
        this.epDescription = desc;
        this.epRating = rt;
        this.epImg = img;
        this.epUrl = url;
    }
}