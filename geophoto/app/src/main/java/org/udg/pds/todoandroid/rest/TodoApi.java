package org.udg.pds.todoandroid.rest;

import org.udg.pds.todoandroid.entity.IdObject;
import org.udg.pds.todoandroid.entity.RegisterAnswer;
import org.udg.pds.todoandroid.entity.Task;
import org.udg.pds.todoandroid.entity.User;
import org.udg.pds.todoandroid.entity.UserLogin;
import org.udg.pds.todoandroid.entity.UserRegister;
import org.udg.pds.todoandroid.entity.callUploadPhoto;
import org.udg.pds.todoandroid.entity.longLat;
import org.udg.pds.todoandroid.entity.searchAroundAnswer;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.ArrayList;
import java.util.List;


public interface TodoApi {
  @POST("/login/")
  Call<User> login(@Body UserLogin login);

  @POST("/register/")
  Call<RegisterAnswer> register(@Body UserRegister register);


  @POST("geophoto_api/photos/")
  Call<User> uploadPhoto(@Header ("Authorization") String token, @Body callUploadPhoto callUploadPhoto);

  @POST("geophoto_api/search_around/")
  Call<ArrayList<searchAroundAnswer>> searchAround(@Header ("Authorization") String token, @Body longLat longLat);

  @GET("/users/check")
  Call<String> check();

  @POST("/tasks")
  Call<IdObject> addTask(@Body Task task);

  @GET("/tasks")
  Call<List<Task>> getTasks();

  @GET("/tasks/{id}")
  Call<Task> getTask(@Path("id") String id);

}

