package org.udg.pds.todoandroid.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.udg.pds.todoandroid.R;
import org.udg.pds.todoandroid.TodoApp;
import org.udg.pds.todoandroid.entity.User;
import org.udg.pds.todoandroid.activity.ImageGridAdapter;
import org.udg.pds.todoandroid.entity.searchAroundAnswer;
import org.udg.pds.todoandroid.rest.TodoApi;
import org.udg.pds.todoandroid.util.Global;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ShowImages extends AppCompatActivity implements ImageGridAdapter.OnNoteListener {

    TodoApi mTodoService;
    private List<String> imageList;
    private ArrayList<searchAroundAnswer> llistaCompleta;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(Global.theme);
        mTodoService = ((TodoApp) this.getApplication()).getAPI();
        setContentView(R.layout.show_images);
        Intent i = getIntent();
        llistaCompleta=new ArrayList<>();
        llistaCompleta = (ArrayList<searchAroundAnswer>) i.getSerializableExtra("llistaFotos");

        RecyclerView rv = findViewById(R.id.rv);

        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rv.setLayoutManager(sglm);

        imageList = new ArrayList<>();
       for(searchAroundAnswer s: llistaCompleta)
       {
           imageList.add(s.thumbnail);
       }


        /*
        imageList.add("https://farm5.staticflickr.com/4403/36538794702_83fd8b63b7_c.jpg");
        imageList.add("https://farm5.staticflickr.com/4354/35684440714_434610d1d6_c.jpg");
        imageList.add("https://farm5.staticflickr.com/4301/35690634410_f5d0e312cb_c.jpg");
        imageList.add("https://farm4.staticflickr.com/3854/32890526884_7dc068fedd_c.jpg");
        imageList.add("https://farm8.staticflickr.com/7787/18143831616_a239c78056_c.jpg");
        imageList.add("https://farm9.staticflickr.com/8745/16657401480_57653ac8b0_c.jpg");
        imageList.add("https://farm3.staticflickr.com/2917/14144166232_44613c53c7_c.jpg");
        imageList.add("https://farm8.staticflickr.com/7453/13960410788_3dd02b7a02_c.jpg");
        imageList.add("https://farm1.staticflickr.com/920/29297133218_de38a7e4c8_c.jpg");
        imageList.add("https://farm1.staticflickr.com/888/29062858008_89851766c9_c.jpg");
        imageList.add("https://farm2.staticflickr.com/1731/27940806257_8067196b41_c.jpg");
        imageList.add("https://farm1.staticflickr.com/884/42745897912_ff65398e38_c.jpg");
        imageList.add("https://farm2.staticflickr.com/1829/27971893037_1858467f9a_c.jpg");
        imageList.add("https://farm2.staticflickr.com/1793/42937679651_3094ebb2b9_c.jpg");
        imageList.add("https://farm1.staticflickr.com/892/42078661914_b940d96992_c.jpg");
        imageList.add("https://i.pinimg.com/736x/01/4a/0c/014a0c0b33b8816332ab920c51cfd8cf.jpg");*/

        ImageGridAdapter iga = new ImageGridAdapter(ShowImages.this, imageList,this);
        rv.setAdapter(iga);
    }

    @Override
    public void onNoteClick(int position) {
        String imatgeGran;
        imatgeGran=imageList.get(position);
        Intent intent=new Intent(this, ImageFullScreen.class);
        intent.putExtra("fotogran",imatgeGran);
        startActivity(intent);

    }
}
