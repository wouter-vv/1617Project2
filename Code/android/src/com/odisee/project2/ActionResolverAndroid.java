package com.odisee.project2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent; import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.util.logging.LogRecord;

/**
 * Created by Walter on 14/11/2016.
 */

public class ActionResolverAndroid implements ActionResolver {

    Handler handler;
    Context context;
    ActionResolver actionResolver;
    AndroidLauncher al = new AndroidLauncher();

    public void setMyGameCallback(ActionResolver callback) {
        actionResolver = callback;
    }

    public ActionResolverAndroid (Context context){
        handler = new Handler();
        this.context = context;
    }
    @Override
    public void showToast(CharSequence text) {
        al.startLogingActivity(context);
    }
}
