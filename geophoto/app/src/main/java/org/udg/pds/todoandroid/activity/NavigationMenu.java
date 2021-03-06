package org.udg.pds.todoandroid.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.udg.pds.todoandroid.R;
import org.udg.pds.todoandroid.TodoApp;
import org.udg.pds.todoandroid.entity.User;
import org.udg.pds.todoandroid.entity.UserLogin;
import org.udg.pds.todoandroid.entity.Usuari;
import org.udg.pds.todoandroid.entity.longLat;
import org.udg.pds.todoandroid.entity.searchAroundAnswer;
import org.udg.pds.todoandroid.rest.TodoApi;
import org.udg.pds.todoandroid.util.Global;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NavigationMenu extends AppCompatActivity {


    TodoApi mTodoService;
    String token;
    String username;
    int distance;
    double longitude;
    double latitude;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(Global.theme);
        setContentView(R.layout.navigation_menu);
        mTodoService = ((TodoApp) this.getApplication()).getAPI();

        Intent i = getIntent();
        token = (String) i.getSerializableExtra("token");
        username = (String) i.getSerializableExtra("username");
        Button fotosProperes = (Button) findViewById(R.id.f_properes_button);
        Button penjarFoto = (Button) findViewById(R.id.upload_button);
        Button buscarPerTerritori = (Button) findViewById(R.id.territory_button);
        Button lesMevesFotos = (Button) findViewById(R.id.myphotos_button);

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();
        }

        distance = -1;
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Introdueix la distància (metres) a la que vols buscar fotos");
// Set up the input
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);


        final AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
        builder2.setTitle("De quin usuari vols buscar fotos?");
// Set up the input
        final EditText input2 = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input2.setInputType(InputType.TYPE_CLASS_TEXT);
        builder2.setView(input2);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                distance = Integer.parseInt(input.getText().toString());

                longLat l = new longLat();
                l.location_lat = latitude;
                l.location_lon = longitude;
                l.distance = distance;
                Call<ArrayList<searchAroundAnswer>> call = mTodoService.searchAround("Bearer " + token, l);
                call.enqueue(new Callback<ArrayList<searchAroundAnswer>>() {
                    @Override
                    public void onResponse(Call<ArrayList<searchAroundAnswer>> call, Response<ArrayList<searchAroundAnswer>> response) {

                        if (response.isSuccessful()) {
                            if (response.body() == null || response.body().size() == 0) {
                                Toast toast = Toast.makeText(NavigationMenu.this, "No hi ha cap foto en un radi de " + distance + " metres", Toast.LENGTH_SHORT);
                                toast.show();

                            } else {
                                Intent intent = new Intent(NavigationMenu.this, ShowImages.class);

                                intent.putExtra("llistaFotos", response.body());
                                startActivity(intent);
                            }


                        } else {
                            Toast toast = Toast.makeText(NavigationMenu.this, "Search failure", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<searchAroundAnswer>> call, Throwable t) {
                        Toast toast = Toast.makeText(NavigationMenu.this, "Error logging in", Toast.LENGTH_SHORT);
                        toast.show();

                    }
                });
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });


        // Set up the buttons
        builder2.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Usuari u = new Usuari(input2.getText().toString());
                Call<ArrayList<searchAroundAnswer>> call = mTodoService.searchMyPhotos("Bearer " + token, u);
                call.enqueue(new Callback<ArrayList<searchAroundAnswer>>() {
                    @Override
                    public void onResponse(Call<ArrayList<searchAroundAnswer>> call, Response<ArrayList<searchAroundAnswer>> response) {

                        if (response.isSuccessful()) {
                            if (response.body() == null || response.body().size() == 0) {
                                Toast toast = Toast.makeText(NavigationMenu.this, "L'usuari entrat no té cap foto penjada", Toast.LENGTH_SHORT);
                                toast.show();

                            } else {
                                Intent intent = new Intent(NavigationMenu.this, ShowImages.class);

                                intent.putExtra("llistaFotos", response.body());
                                startActivity(intent);
                            }


                        } else {
                            Toast toast = Toast.makeText(NavigationMenu.this, "Search failure", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<searchAroundAnswer>> call, Throwable t) {
                        Toast toast = Toast.makeText(NavigationMenu.this, "Error searching user photos", Toast.LENGTH_SHORT);
                        toast.show();

                    }
                });

            }});
                builder2.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });



                final AlertDialog alert = builder.create();
                final AlertDialog alert2 = builder2.create();
                fotosProperes.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if (location != null)
                            alert.show();
                        else {
                            Toast toast = Toast.makeText(NavigationMenu.this, "No es reconeix la latitud i longitud on et trobes," +
                                    " aquesta opció queda deshablitada", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                });


                penjarFoto.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        //NavigationMenu.this.startActivity(new Intent(NavigationMenu.this, UploadImage.class));

                        Intent intent = new Intent(NavigationMenu.this, UploadImage.class);
                        intent.putExtra("token", token);
                        startActivity(intent);


                    }
                });

                buscarPerTerritori.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(NavigationMenu.this, TerritoriesMenu.class);
                        intent.putExtra("token", token);
                        startActivity(intent);

                    }
                });

                lesMevesFotos.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        alert2.show();
                    }
                });
            }

    }







