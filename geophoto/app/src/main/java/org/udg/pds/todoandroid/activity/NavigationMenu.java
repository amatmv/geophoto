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
import org.udg.pds.todoandroid.entity.User;
import org.udg.pds.todoandroid.entity.UserLogin;
import org.udg.pds.todoandroid.entity.longLat;
import org.udg.pds.todoandroid.rest.TodoApi;
import org.udg.pds.todoandroid.util.Global;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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


                longLat l= new UserLogin();
                l.location_lat=latitude;
                l.location_lon=longitude;
                l.distance=5;
                Call<User> call = mTodoService.login(ul);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {

                        if (response.isSuccessful()) {
                            progress.dismiss();
                            // response.body().id;
                            Toast toast = Toast.makeText(Login.this,response.body().token , Toast.LENGTH_SHORT);
                            toast.show();
                            Intent intent=new Intent(Login.this, NavigationMenu.class);
                            intent.putExtra("token",response.body().token);
                            startActivity(intent);
                            Login.this.finish();


                        } else {
                            progress.dismiss();
                            Toast toast = Toast.makeText(Login.this, "Login failure", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }



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
