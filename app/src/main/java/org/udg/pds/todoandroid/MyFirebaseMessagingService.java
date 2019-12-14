package org.udg.pds.todoandroid;


import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessagingService;

import org.udg.pds.todoandroid.entity.IdObject;
import org.udg.pds.todoandroid.rest.TodoApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.firebase.inappmessaging.internal.Logging.TAG;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    String TAG= "Firebase Postter";

    @Override
    public void onNewToken(String token) {

        Log.d(TAG, "Refreshed token: " + token);

        TodoApi todoApi = ((TodoApp) TodoApp.getAppContext()).getAPI();



    }


}
