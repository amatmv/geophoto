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
    private OnNoteListener mOnNoteListener;
    private Context c;

    public class GridItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        SquareImageView siv;
        OnNoteListener onNoteListener;

        public GridItemViewHolder(View view, OnNoteListener onNoteListener) {
            super(view);
            siv = view.findViewById(R.id.siv);
            this.onNoteListener=onNoteListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }

    public ImageGridAdapter(Context c, List imageList, OnNoteListener onNoteListener) {
        this.c = c;
        this.imageList = imageList;
        this.mOnNoteListener=onNoteListener;
    }

    @Override
    public GridItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_grid_adapter, parent, false);
        return new GridItemViewHolder(itemView,mOnNoteListener);
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
                mOnNoteListener.onNoteClick(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public interface OnNoteListener{
        void onNoteClick(int position);
    }

}