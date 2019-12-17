package org.udg.pds.todoandroid.activity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.udg.pds.todoandroid.R;
import org.udg.pds.todoandroid.TodoApp;
import org.udg.pds.todoandroid.entity.searchAroundAnswer;
import org.udg.pds.todoandroid.rest.TodoApi;
import org.udg.pds.todoandroid.util.Global;

import java.util.ArrayList;
import java.util.List;


public class ImageFullScreen extends AppCompatActivity {
    ImageView siv;
    TodoApi mTodoService;
    String imatge;
    searchAroundAnswer dadesCompletes;
    TextView textView1;
    TextView textView2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_full_screen);
        setTheme(Global.theme);
        siv = findViewById(R.id.siv);
        mTodoService = ((TodoApp) this.getApplication()).getAPI();


        Intent i = getIntent();
        dadesCompletes = (searchAroundAnswer) i.getSerializableExtra("fotogran");
        imatge=dadesCompletes.url;
        textView1=(TextView)findViewById(R.id.textView1);
        textView1.setText("Autor: "+dadesCompletes.user.username+"\nTítol: "+dadesCompletes.title+"\n"+dadesCompletes.created_at+
                "\n"+dadesCompletes.comarca+", "+dadesCompletes.provincia+", "+dadesCompletes.municipi);
        //textView2=(TextView)findViewById(R.id.textView2);
        //textView2.setText("Títol: "+dadesCompletes.title);

        Picasso.get()
            .load(imatge)
            .into(siv);
    }
}
