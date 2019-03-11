package ir.sharif.uicomponents;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

public class ApplicationLoader extends Application {

    @SuppressLint("StaticFieldLeak")
    public static volatile Context applicationContext;

    @Override
    public void onCreate() {
        try {
            applicationContext = getApplicationContext();
        } catch (Throwable ignore) {

        }
        super.onCreate();
        if (applicationContext == null) {
            applicationContext = getApplicationContext();
        }
    }
}
