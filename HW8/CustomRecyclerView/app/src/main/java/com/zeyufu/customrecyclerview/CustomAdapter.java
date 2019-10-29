package com.zeyufu.customrecyclerview;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.EpViewHolder> {

    private Context context;
    ArrayList<Integer> episodeImages;
    String[] episodes;
    String[] descriptions;
    String[] epUrls;

    // Constructor
    public CustomAdapter(Context aContext) {
        this.context = aContext;
        episodes = aContext.getResources().getStringArray(R.array.episodes);
        descriptions = aContext.getResources().getStringArray(R.array.episode_descriptions);
        epUrls = aContext.getResources().getStringArray(R.array.episode_urls);
        episodeImages = new ArrayList<>();
        episodeImages.add(R.drawable.st_spocks_brain);
        episodeImages.add(R.drawable.st_arena__kirk_gorn);
        episodeImages.add(R.drawable.st_this_side_of_paradise__spock_in_love);
        episodeImages.add(R.drawable.st_mirror_mirror__evil_spock_and_good_kirk);
        episodeImages.add(R.drawable.st_platos_stepchildren__kirk_spock);
        episodeImages.add(R.drawable.st_the_naked_time__sulu_sword);
        episodeImages.add(R.drawable.st_the_trouble_with_tribbles__kirk_tribbles);
    }

    @NonNull
    @Override
    public EpViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerview_row, parent, false);
        return new EpViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EpViewHolder epViewHolder, final int position) {
        epViewHolder.imgEpisode.setImageResource(episodeImages.get(position));
        epViewHolder.tvEpTitle.setText(episodes[position]);
        epViewHolder.tvEpDesc.setText(descriptions[position]);
        epViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WebActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(WebActivity.KEY_EP_URL, epUrls[position]);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return episodes.length;
    }

    /**
     * Inner class for view holder
     */
    public static class EpViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgEpisode;
        public TextView tvEpTitle;
        public TextView tvEpDesc;

        public EpViewHolder(@NonNull View itemView) {
            super(itemView);
            imgEpisode = itemView.findViewById(R.id.imgEpisode);
            tvEpTitle = itemView.findViewById(R.id.tvEpTitle);
            tvEpDesc = itemView.findViewById(R.id.tvEpDesc);
        }
    }
}
