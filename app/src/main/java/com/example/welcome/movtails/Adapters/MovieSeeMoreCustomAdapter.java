package com.example.welcome.movtails.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.welcome.movtails.Constants;
import com.example.welcome.movtails.Network.Movie.MovieResponseGeneral;
import com.example.welcome.movtails.R;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

/**
 * Created by WELCOME on 03-12-2017.
 */

public class MovieSeeMoreCustomAdapter extends RecyclerView.Adapter<MovieSeeMoreCustomAdapter.ViewHolderPro> {

    private Context mContext;
    private ArrayList<MovieResponseGeneral> mMovies;

    public MovieSeeMoreCustomAdapter(Context mContext, ArrayList<MovieResponseGeneral> mMovies) {
        this.mContext = mContext;
        this.mMovies = mMovies;
    }

    @Override
    public ViewHolderPro onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolderPro(LayoutInflater.from(mContext).inflate(R.layout.movie_see_more_adapter_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(final ViewHolderPro holder, int position) {

        AlphaAnimation animation = new AlphaAnimation(0,1);
        animation.setDuration(700);

        Glide.with(mContext.getApplicationContext()).load(Constants.IMAGE_LOADING_BASE_URL_342 + mMovies.get(position).getBackdrop_path())
                .asBitmap()
                .centerCrop()
                .animate(animation)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<String, Bitmap>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                        holder.progress.hide();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        holder.progress.hide();
                        return false;
                    }
                }).into(holder.imageView);

        if(mMovies.get(position).getTitle() != null){
            holder.titleTextView.setText(mMovies.get(position).getTitle());
        }else{
            holder.titleTextView.setText("No Title Found");
        }

        if(mMovies.get(position).getVote_average() != null && mMovies.get(position).getVote_average() > 0){
            holder.ratingTextView.setText(mMovies.get(position).getVote_average() + "\u2B50");
        }else {
            holder.ratingTextView.setText("");
        }

    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public class ViewHolderPro extends RecyclerView.ViewHolder{

        public CardView cardView;
        public RelativeLayout relativeLayout;
        public ImageView imageView;
        public TextView titleTextView;
        public TextView ratingTextView;
        public AVLoadingIndicatorView progress;

        public ViewHolderPro(View itemView) {

            super(itemView);

            cardView = itemView.findViewById(R.id.cardView);
            relativeLayout = itemView.findViewById(R.id.imageRelative);
            imageView = itemView.findViewById(R.id.imageView);
            titleTextView = itemView.findViewById(R.id.title);
            ratingTextView = itemView.findViewById(R.id.rating);
            progress = itemView.findViewById(R.id.avi_progress);

            relativeLayout.getLayoutParams().width = (int)(mContext.getResources().getDisplayMetrics().widthPixels * 0.48);
            relativeLayout.getLayoutParams().height = (int)((mContext.getResources().getDisplayMetrics().widthPixels * 0.48) / 1.77);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mContext.startActivity(new Intent(mContext, MovieDetailActivity.class).putExtra(Constants.MOVIE_ID,mMovies.get(getAdapterPosition()).getId()));

                }
            });


        }
    }

}
