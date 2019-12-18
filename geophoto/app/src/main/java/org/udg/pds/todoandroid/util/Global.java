package org.udg.pds.todoandroid.util;

import org.udg.pds.todoandroid.R;

import java.text.SimpleDateFormat;
import java.util.TimeZone;


// Global variables (yes, I'm evil)
public class Global {
    public static final String tz = TimeZone.getDefault().getDisplayName();
    public static final SimpleDateFormat DATE_ONLY_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    public static final SimpleDateFormat TIME_ONLY_FORMAT = new SimpleDateFormat("HH::mm");
    public static final SimpleDateFormat TIME_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH::mm");
    public static final SimpleDateFormat FULL_TIME_DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm");

    // Resquest codes for startActivityForResult
    public static final int RQ_ADD_TASK = 1;
    public static int theme= R.style.AppTheme_BLUE;

    // IMPORTANT: you have to change the value of BASE_URL_PORTFORWARDING when deploying the app
    // OPENSHIFT
    // public static final String BASE_URL_PORTFORWARDING = "https://project2-pdsudg.rhcloud.com";
    // Android emulator
    // public static final String BASE_URL_PORTFORWARDING = "http://10.0.2.2:8080";
    // Genymotion emulator
    // public static final String BASE_URL_PORTFORWARDING = "http://10.0.3.2:8080";
    // For debugging with real device using port forwarding
    // https://developer.chrome.com/devtools/docs/remote-debugging?hl=de#port-forwarding
    public static final String BASE_URL_PORTFORWARDING = "http://localhost:8080";
    public static final String BASE_URL_GENYMOTION = "http://10.0.3.2:8080";
    public static final String BASE_URL_Apiary = "http://private-1da9e-geophoto.apiary-mock.com";
    public static final String Server = "http://35.180.63.203:8000";

}
