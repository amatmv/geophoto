package org.udg.pds.todoandroid.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.udg.pds.todoandroid.R;
import org.udg.pds.todoandroid.TodoApp;
import org.udg.pds.todoandroid.entity.RegisterAnswer;
import org.udg.pds.todoandroid.entity.User;
import org.udg.pds.todoandroid.entity.UserLogin;
import org.udg.pds.todoandroid.entity.UserRegister;
import org.udg.pds.todoandroid.rest.TodoApi;
import org.udg.pds.todoandroid.util.Global;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    TodoApi mTodoService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(Global.theme);setContentView(R.layout.register);

        mTodoService = ((TodoApp)this.getApplication()).getAPI();

        Button b = (Button)findViewById(R.id.register_button);


        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText username = (EditText) Register.this.findViewById(R.id.input_Username);
                EditText fullname = (EditText) Register.this.findViewById(R.id.input_fullName);
                EditText email = (EditText) Register.this.findViewById(R.id.input_email);
                EditText password = (EditText) Register.this.findViewById(R.id.input_password);
                EditText confirmPassword = (EditText) Register.this.findViewById(R.id.input_ConfirmPassword);

                if(username.getText().toString().isEmpty())
                {
                    Toast toast = Toast.makeText(Register.this, "Introdueix el teu nom d'usuari", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if(fullname.getText().toString().isEmpty())
                {
                    Toast toast = Toast.makeText(Register.this, "Introdueix el teu nom complet", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if(email.getText().toString().isEmpty())
                {
                    Toast toast = Toast.makeText(Register.this, "Introdueix el teu email", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if(password.getText().toString().isEmpty())
                {
                    Toast toast = Toast.makeText(Register.this, "Introdueix la teva contrasenya", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if(confirmPassword.getText().toString().isEmpty())
                {
                    Toast toast = Toast.makeText(Register.this, "Introdueix la confirmació de contrasenya", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if(!confirmPassword.getText().toString().equals(password.getText().toString()) )
                {
                    confirmPassword.getText().clear();
                    password.getText().clear();
                    Toast toast = Toast.makeText(Register.this, "Les contrasenyes no coincideixen", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    Register.this.checkCredentials(username.getText().toString(), fullname.getText().toString(), password.getText().toString(), email.getText().toString());
                }
            }
        });
        TextView r = findViewById(R.id.link_login);
        r.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               // Register.this.startActivity(new Intent(Register.this, Login.class));
                Register.this.finish();
            }
        });

    }
    public void checkCredentials(String username,String fullname, String password, String email ) {


        ProgressDialog progress = ProgressDialog.show(this, null,
                "Registrant", true);


        UserRegister ul = new UserRegister();
        ul.username = username;
        ul.full_name=fullname;
        ul.password = password;
        ul.email=email;
        Call<RegisterAnswer> call = mTodoService.register(ul);
        call.enqueue(new Callback<RegisterAnswer>() {
            @Override
            public void onResponse(Call<RegisterAnswer> call, Response<RegisterAnswer> response) {

                if (response.isSuccessful()) {
                    Toast toast = Toast.makeText(Register.this, "Registrat correctament", Toast.LENGTH_SHORT);
                    toast.show();
                    progress.dismiss();
                    //Register.this.startActivity(new Intent(Register.this, Login.class));
                    Register.this.finish();
                } else {
                    progress.dismiss();
                    Toast toast = Toast.makeText(Register.this, ""+response.message(), Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<RegisterAnswer> call, Throwable t) {
                progress.dismiss();
                Toast toast = Toast.makeText(Register.this, "Error de connexió", Toast.LENGTH_SHORT);
                toast.show();

            }
        });
    }

}
