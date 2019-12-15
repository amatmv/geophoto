package org.udg.pds.todoandroid;

import android.app.Application;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.udg.pds.todoandroid.rest.TodoApi;
import org.udg.pds.todoandroid.util.Global;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by imartin on 13/02/17.
 */
public class TodoApp extends Application {

  TodoApi mTodoService;

  @Override
  public void onCreate() {
    super.onCreate();

    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

    ClearableCookieJar cookieJar =
            new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(this));

    OkHttpClient httpClient = new OkHttpClient.Builder()
            .cookieJar(cookieJar)
            .addInterceptor(interceptor)
            .build();

    Gson gson = new GsonBuilder()
            .setLenient()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .create();

    Retrofit retrofit = new Retrofit.Builder()
            .client(httpClient)
            .baseUrl(Global.BASE_URL_PORTFORWARDING)
            //.baseUrl(Global.BASE_URL_GENYMOTION)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    mTodoService = retrofit.create(TodoApi.class);
  }

  public TodoApi getAPI() {
    return mTodoService;
  }
}
