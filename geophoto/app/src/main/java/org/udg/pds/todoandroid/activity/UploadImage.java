package org.udg.pds.todoandroid.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.udg.pds.todoandroid.R;
import org.udg.pds.todoandroid.TodoApp;
import org.udg.pds.todoandroid.entity.User;
import org.udg.pds.todoandroid.entity.UserLogin;
import org.udg.pds.todoandroid.rest.TodoApi;
import org.udg.pds.todoandroid.util.Global;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// This is the Login fragment where the user enters the username and password and
// then a RESTResponder_RF is called to check the authentication
public class UploadImage extends AppCompatActivity {

    TodoApi mTodoService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(Global.theme);
        setContentView(R.layout.login);
        mTodoService = ((TodoApp)this.getApplication()).getAPI();
        Button b = (Button)findViewById(R.id.login_button);

        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                closeKeyboard();
                EditText u = (EditText) UploadImage.this.findViewById(R.id.login_username);
                EditText p = (EditText) UploadImage.this.findViewById(R.id.login_password);

                if(u.getText().toString().isEmpty())
                {
                    Toast toast = Toast.makeText(UploadImage.this, "Introdueix el teu nom d'usuari", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if(p.getText().toString().isEmpty())
                {
                    Toast toast = Toast.makeText(UploadImage.this, "Introdueix la teva contrasenya", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    UploadImage.this.checkCredentials(u.getText().toString(), p.getText().toString());
                }
            }
        });
        TextView r = findViewById(R.id.register_button);
        r.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                UploadImage.this.startActivity(new Intent(UploadImage.this, Register.class));
               // Login.this.finish();
            }
        });

    }
    // This method is called when the "Login" button is pressed in the Login fragment
    public void checkCredentials(String username, String password) {


        ProgressDialog progress = ProgressDialog.show(this, null,
                "Authenticating...", true);


        UserLogin ul = new UserLogin();
        ul.username = username;
        ul.password = password;
        Call<User> call = mTodoService.login(ul);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if (response.isSuccessful()) {

                    progress.dismiss();
                    UploadImage.this.startActivity(new Intent(UploadImage.this, NavigationMenu.class));
                    UploadImage.this.finish();
                } else {
                    progress.dismiss();
                    Toast toast = Toast.makeText(UploadImage.this, "Login failure", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                    progress.dismiss();
                    Toast toast = Toast.makeText(UploadImage.this, "Error logging in", Toast.LENGTH_SHORT);
                    toast.show();

            }
        });
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}