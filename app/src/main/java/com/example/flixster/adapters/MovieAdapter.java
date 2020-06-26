package com.example.flixster.adapters;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.flixster.MovieDetailsActivity;
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    // context - where adapter is being constructed from
    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
        Log.d("MovieAdapter", "constructor, " + movies.size());
    }


    // inflates a layout and returns it inside a viewholder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");
        View movieView = LayoutInflater.from(this.context).inflate(R.layout.item_movie, parent, false);
        // wrap new view inside a ViewHolder
        return new ViewHolder(movieView);
    }

    // assign data to ViewHolder at specified position
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder " + position);
        //get movie data a specified position
        Movie movie = movies.get(position);
        //bind data into the ViewHolder
        holder.bind(movie);
    }

    // returns total number of items in list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    // ViewHolder represents a row in the RecyclerView
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // gets views to display
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);

            itemView.setOnClickListener(this);
        }

        public void bind(Movie movie) {
            //populate viewHolder with movie's information
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            // use Glide library to embed images
            String imageURL;
            int placeholder;
            int radius = 30;
            int margin = 10;
            // use poster if vertical, backdrop if horizontal
            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                imageURL = movie.getBackdropPath();
                placeholder = R.drawable.flicks_backdrop_placeholder;
            } else {
                imageURL = movie.getPosterPath();
                placeholder = R.drawable.flicks_movie_placeholder;
            }
            // loads image and displays placeholder while loading
            Glide.with(context).load(imageURL).placeholder(placeholder).transform(new RoundedCornersTransformation(radius, margin)).into(ivPoster);
        }

        // when user clicks on a view, show the MovieDetailsActivity
        @Override
        public void onClick(View view) {
            // get item position
            int pos = getAdapterPosition();
            // make sure position is valid
            if(pos != RecyclerView.NO_POSITION) {
                // get current movie
                Movie movie = movies.get(pos);
                // create intent for new activity
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                // serialize movie w/ parceler, w short name as key
                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
                // show activity
                context.startActivity(intent);
            }

        }
    }
}
