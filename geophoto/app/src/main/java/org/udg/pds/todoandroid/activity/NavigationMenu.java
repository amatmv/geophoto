package org.udg.pds.todoandroid.activity;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.udg.pds.todoandroid.R;
import org.udg.pds.todoandroid.TodoApp;
import org.udg.pds.todoandroid.rest.TodoApi;
import org.udg.pds.todoandroid.util.Global;

public class NavigationMenu extends AppCompatActivity {


    TodoApi mTodoService;
    String token;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(Global.theme);
        setContentView(R.layout.navigation_menu);
        mTodoService = ((TodoApp) this.getApplication()).getAPI();

        Intent i = getIntent();
        token = (String) i.getSerializableExtra("token");

        Button fotosProperes = (Button) findViewById(R.id.f_properes_button);
        Button penjarFoto = (Button) findViewById(R.id.upload_button);
        Button buscarPerTerritori = (Button) findViewById(R.id.territory_button);
        Button lesMevesFotos = (Button) findViewById(R.id.myphotos_button);

        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();



        fotosProperes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                NavigationMenu.this.startActivity(new Intent(NavigationMenu.this, ShowImages.class));
                Toast toast = Toast.makeText(NavigationMenu.this, "longitud " + longitude + " latitud " + latitude, Toast.LENGTH_SHORT);
                toast.show();
                //Toast toast = Toast.makeText(NavigationMenu.this, "Fotos properes coming soon", Toast.LENGTH_SHORT);
                //toast.show();

            }
        });

        penjarFoto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               //NavigationMenu.this.startActivity(new Intent(NavigationMenu.this, UploadImage.class));

                Intent intent=new Intent(NavigationMenu.this, UploadImage.class);
                intent.putExtra("token",token);
                startActivity(intent);

                Toast toast = Toast.makeText(NavigationMenu.this, "Penjar fotos coming soon", Toast.LENGTH_SHORT);
                toast.show();

            }
        });

        buscarPerTerritori.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast toast = Toast.makeText(NavigationMenu.this, "Buscar fotos per territori coming soon", Toast.LENGTH_SHORT);
                toast.show();

            }
        });

        lesMevesFotos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast toast = Toast.makeText(NavigationMenu.this, "Les meves fotos coming soon", Toast.LENGTH_SHORT);
                toast.show();

            }
        });



    }
}
