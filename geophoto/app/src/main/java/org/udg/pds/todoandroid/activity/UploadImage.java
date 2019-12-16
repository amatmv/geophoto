package org.udg.pds.todoandroid.activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.udg.pds.todoandroid.R;
import org.udg.pds.todoandroid.TodoApp;
import org.udg.pds.todoandroid.entity.User;
import org.udg.pds.todoandroid.entity.callUploadPhoto;
import org.udg.pds.todoandroid.rest.TodoApi;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadImage extends AppCompatActivity implements View.OnClickListener  {

    private Button buttonChoose;
    private Button buttonUpload;
    TodoApi mTodoService;
    private ImageView imageView;
    String token;
    private EditText editTextName;
    ByteArrayOutputStream byteArrayOutputStream;
    byte[] byteArray;
    private Bitmap bitmap;
    String str_image;

    private int PICK_IMAGE_REQUEST = 1;

    private String UPLOAD_URL ="http://almkhrfich.esy.es/VolleyUpload/upload.php";

    private String KEY_IMAGE = "image";
    private String KEY_NAME = "name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_image);
        mTodoService = ((TodoApp)this.getApplication()).getAPI();

        Intent i = getIntent();
        token = (String) i.getSerializableExtra("token");

        Toast toast = Toast.makeText(UploadImage.this, token, Toast.LENGTH_SHORT);
        toast.show();


        buttonChoose = (Button) findViewById(R.id.buttonChoose);
        buttonUpload = (Button) findViewById(R.id.buttonUpload);

        editTextName = (EditText) findViewById(R.id.editText);

        imageView  = (ImageView) findViewById(R.id.imageView);

        buttonChoose.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage(){

        String name = editTextName.getText().toString().trim();
        String image = getStringImage(bitmap);

        callUploadPhoto c = new callUploadPhoto();
        c.photo=str_image;
        c.title=name;

        Call<User> call = mTodoService.uploadPhoto("Bearer "+token,c);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if (response.isSuccessful()) {

                    UploadImage.this.finish();
                } else {
                    Toast toast = Toast.makeText(UploadImage.this, "Upload failure", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast toast = Toast.makeText(UploadImage.this, "Connection error", Toast.LENGTH_SHORT);
                toast.show();

            }

        //Creating a Request Queue
       // RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        //requestQueue.add(stringRequest);
        });
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                 byteArray = byteArrayOutputStream.toByteArray();
                  str_image= Base64.encodeToString(byteArray, Base64.DEFAULT);
                //Setting the Bitmap to ImageView
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {

        if(v == buttonChoose){
            showFileChooser();
        }

        if(v == buttonUpload){
            uploadImage();
        }
    }
}