package org.udg.pds.todoandroid.rest;

import org.udg.pds.todoandroid.R;
import org.udg.pds.todoandroid.entity.GenericResponse;
import org.udg.pds.todoandroid.entity.IdObject;
import org.udg.pds.todoandroid.entity.Post;
import org.udg.pds.todoandroid.entity.PostEnvio;
import org.udg.pds.todoandroid.entity.RegisterAnswer;
import org.udg.pds.todoandroid.entity.Task;
import org.udg.pds.todoandroid.entity.TokenRegister;
import org.udg.pds.todoandroid.entity.User;
import org.udg.pds.todoandroid.entity.UserLogin;
import org.udg.pds.todoandroid.entity.UserRegister;
import org.udg.pds.todoandroid.entity.VoidClass;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

/**
 * Created by imartin on 13/02/17.
 */
public interface TodoApi {
  @POST("/users/login")
  Call<User> login(@Body UserLogin login);

  @POST("/users/register")
  Call<RegisterAnswer> register(@Body UserRegister register);

  @POST("/users/me/setToken")
  Call<GenericResponse> registrarToken(@Body TokenRegister Token);

  @GET("/users/check")
  Call<GenericResponse> check();

  @GET("/users/{id}")
  Call<User> obtenirUsuari(@Path("id") Long id);

  @POST("/tasks")
  Call<IdObject> addTask(@Body Task task);

  @POST("/posts")
  Call<IdObject> createPost(@Body PostEnvio post);

  @GET("/posts/{id}/replies")
  Call<List<Post>> obtenirRespostes(@Path("id") Long id );

  @Multipart
  @POST("/images/{id}")
  Call<GenericResponse> uploadPostImage(
//          @Part("file") RequestBody description,
          @Part MultipartBody.Part file,
          @Path("id") Long id
  );

  @GET("/images/{filename}")
  Call<ResponseBody> getPostImage(@Path("filename") String filename);

  @Multipart
  @POST("/images/user")
  Call<GenericResponse> uploadUserImage(
          @Part MultipartBody.Part file
  );

  @POST("/users/{id}/follow")
  Call<IdObject> follow(@Path("id") Long id);

  @POST("/users/{id}/unfollow")
  Call<IdObject> unfollow(@Path("id") Long id);

  @GET("/users/{id}/following")
  Call<Boolean> isFollowing(@Path("id") Long id);

  @GET("/tasks")
  Call<List<Task>> getTasks();

  @GET("/tasks/{id}")
  Call<Task> getTask(@Path("id") String id);

  @GET("/users/me/posts")
  Call<List<Post>> obtenirPostUsuari();

  @GET("/users/{id}/posts")
  Call<List<Post>> obtenirPostsUsuariAlie(@Path("id") Long id);

  @GET("/users/search?")
  Call<List<User>> obtenirUsuarisSearch(@Query("word") String word);

  @GET("/posts/search?")
  Call<List<Post>> obtenirPostTags(@Query("word") String word);

  @GET("/users/me")
  Call<User> obtenirUsuariLocal();

  @POST("/users/logout")
  Call<GenericResponse> logout(@Body VoidClass v);

  @DELETE("/posts/{id}")
  Call<GenericResponse> delete_post(@Path("id") String id);

  @GET("/posts/{id}/like")
  Call<List<User>> obtenirLikesPost(@Path("id") Long id);

  @POST("/posts/{id}/like")
  Call<VoidClass> donar_like_post(@Path("id") Long id);

  @GET("/posts")
  Call<List<Post>> obtenirTimeline();

  @DELETE("/users/{id}")
  Call<GenericResponse> delete_user(@Path("id") String id);

  //  TODO change when defined in server
  @POST("/users/mod?")
  Call<GenericResponse> modificar_informacio(
          @Query("username") String username,
          @Query("fullName") String fullName,
          @Query("biography") String biography,
          @Query("email") String email,
          @Query("password") String password
  );
}

