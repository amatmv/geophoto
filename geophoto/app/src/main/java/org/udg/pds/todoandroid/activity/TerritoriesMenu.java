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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.udg.pds.todoandroid.R;
import org.udg.pds.todoandroid.TodoApp;
import org.udg.pds.todoandroid.entity.longLat;
import org.udg.pds.todoandroid.entity.searchAroundAnswer;
import org.udg.pds.todoandroid.entity.searchWithinCall;
import org.udg.pds.todoandroid.rest.TodoApi;
import org.udg.pds.todoandroid.util.Global;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TerritoriesMenu extends AppCompatActivity {


    TodoApi mTodoService;
    String token;
    String tipusTerritori;
    String territoriBuscat;
    int comarcaPulsada=-1;
    int provinciaPulsada=-1;
    int municipiPulsat=-1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(Global.theme);
        setContentView(R.layout.territories_menu);
        mTodoService = ((TodoApp) this.getApplication()).getAPI();



        Intent i = getIntent();
        token = (String) i.getSerializableExtra("token");

        Button fotosComarca = (Button) findViewById(R.id.comarca_button);
        Button fotosProvincia = (Button) findViewById(R.id.provincia_button);
        Button fotosMunicipi = (Button) findViewById(R.id.municipi_button);





        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);

        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                territoriBuscat =  input.getText().toString();
                if(comarcaPulsada!=-1) {
                    tipusTerritori = "comarca";
                    comarcaPulsada=-1;
                }
                else if(provinciaPulsada!=-1) {
                    tipusTerritori = "provincia";
                    provinciaPulsada=-1;
                }
                else if (municipiPulsat!=-1)
                {
                    tipusTerritori="municipi";
                    municipiPulsat=-1;
                }



                searchWithinCall crida= new searchWithinCall();
                crida.zone=territoriBuscat;
                crida.zone_type=tipusTerritori;


                Call<ArrayList<searchAroundAnswer>> call = mTodoService.searchWithin("Bearer "+token, crida);
                call.enqueue(new Callback<ArrayList<searchAroundAnswer>>() {
                    @Override
                    public void onResponse(Call<ArrayList<searchAroundAnswer>> call, Response<ArrayList<searchAroundAnswer>> response) {

                        if (response.isSuccessful()) {

                            if(response.body()==null || response.body().size()==0)
                            {
                                Toast toast = Toast.makeText(TerritoriesMenu.this, "No hi ha cap foto al territori entrat", Toast.LENGTH_SHORT);
                                toast.show();

                            }
                            else {
                                Intent intent = new Intent(TerritoriesMenu.this, ShowImages.class);

                                intent.putExtra("llistaFotos", response.body());
                                startActivity(intent);
                            }


                        } else {
                            Toast toast = Toast.makeText(TerritoriesMenu.this, "Search failure", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<searchAroundAnswer>> call, Throwable t) {
                        Toast toast = Toast.makeText(TerritoriesMenu.this, "Error searching photos", Toast.LENGTH_SHORT);
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


        final AlertDialog alert=builder.create();
        fotosComarca.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                alert.setTitle("Introdueix el nom de la comarca que vols buscar");
                comarcaPulsada=1;
                alert.show();
            }
        });




        fotosProvincia.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //NavigationMenu.this.startActivity(new Intent(NavigationMenu.this, UploadImage.class));
                alert.setTitle("Introdueix el nom de la provincia que vols buscar");
                provinciaPulsada=1;
                alert.show();
            }
        });

        fotosMunicipi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                alert.setTitle("Introdueix el nom del municipi que vols buscar");
                municipiPulsat=1;
                alert.show();

            }
        });



    }

}

