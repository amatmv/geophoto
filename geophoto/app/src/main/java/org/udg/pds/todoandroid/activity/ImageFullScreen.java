package org.udg.pds.todoandroid.activity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.udg.pds.todoandroid.R;
import org.udg.pds.todoandroid.TodoApp;
import org.udg.pds.todoandroid.rest.TodoApi;
import org.udg.pds.todoandroid.util.Global;

import java.util.ArrayList;
import java.util.List;


public class ImageFullScreen extends AppCompatActivity {
    ImageView siv;
    TodoApi mTodoService;
    String imatge;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_full_screen);
        setTheme(Global.theme);
        siv = findViewById(R.id.siv);
        mTodoService = ((TodoApp) this.getApplication()).getAPI();


        Intent i = getIntent();
        imatge = (String) i.getSerializableExtra("fotogran");
        Picasso.get()
            .load(imatge)
            .into(siv);
    }
}
