package org.udg.pds.todoandroid.rest;

import org.udg.pds.todoandroid.entity.IdObject;
import org.udg.pds.todoandroid.entity.RegisterAnswer;
import org.udg.pds.todoandroid.entity.Task;
import org.udg.pds.todoandroid.entity.User;
import org.udg.pds.todoandroid.entity.UserLogin;
import org.udg.pds.todoandroid.entity.UserRegister;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;

/**
 * Created by imartin on 13/02/17.
 */
public interface TodoApi {
  @POST("/users/login")
  Call<User> login(@Body UserLogin login);

  @POST("/users/register")
  Call<RegisterAnswer> register(@Body UserRegister register);

  @GET("/users/check")
  Call<String> check();

  @POST("/tasks")
  Call<IdObject> addTask(@Body Task task);

  @GET("/tasks")
  Call<List<Task>> getTasks();

  @GET("/tasks/{id}")
  Call<Task> getTask(@Path("id") String id);

}

