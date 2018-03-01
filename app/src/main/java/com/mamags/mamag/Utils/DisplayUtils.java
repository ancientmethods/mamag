package com.mamags.mamag.Utils;

import android.app.Application;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.mamags.mamag.R;

/**
 * Created by samer on 19/10/2017.
 */

public class DisplayUtils {

    Application application;
    public DisplayUtils(Application application){
        this.application = application;
    }

    public  void displaySnackbar(View view, String message, int length, Context context) {
        Snackbar snackbar = Snackbar.make(view, message, length);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(ContextCompat.getColor(context,R.color.colorPrimaryDark));
        snackbar.show();
    }

    public  void displaySuccessMessage(View view){
        displaySnackbar(view, "Successful response", Snackbar.LENGTH_SHORT, application);

    }

    public  void displayErrorMessage(View view){
        displaySnackbar(view, "Error processing request", Snackbar.LENGTH_SHORT, application);

    }

    public  void displayErrorMessage(View view, String server_message){
        displaySnackbar(view, server_message, Snackbar.LENGTH_SHORT, application);

    }
}
