package org.udg.pds.todoandroid.activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import org.udg.pds.todoandroid.R;

import java.util.List;

public class ImageGridAdapter extends RecyclerView.Adapter<ImageGridAdapter.GridItemViewHolder> {

    private List<String> imageList;

    private Context c;

    public class GridItemViewHolder extends RecyclerView.ViewHolder {
        SquareImageView siv;

        public GridItemViewHolder(View view) {
            super(view);
            siv = view.findViewById(R.id.siv);
        }
    }

    public ImageGridAdapter(Context c, List imageList) {
        this.c = c;
        this.imageList = imageList;
    }

    @Override
    public GridItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_grid_adapter, parent, false);
        return new GridItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GridItemViewHolder holder, int position) {
        final String path = imageList.get(position);

        Picasso.get()
                .load(path)
                .resize(250, 250)
                .centerCrop()
                .into(holder.siv);
        holder.siv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //handle click event on image
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

}