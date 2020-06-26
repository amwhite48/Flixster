package com.example.flixster.adapters;

import java.util.List;

import android.content.Context;
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
import com.example.flixster.R;
import com.example.flixster.models.Movie;

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
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
        }

        public void bind(Movie movie) {
            //populate viewHolder with movie's information
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            // use Glide library to embed images
            String imageURL;
            int placeholder;
            // use poster if vertical, backdrop if horizontal
            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                imageURL = movie.getBackdropPath();
                placeholder = R.drawable.flicks_backdrop_placeholder;
            } else {
                imageURL = movie.getPosterPath();
                placeholder = R.drawable.flicks_movie_placeholder;
            }
            Glide.with(context).load(imageURL).placeholder(placeholder).into(ivPoster);
        }
    }
}
