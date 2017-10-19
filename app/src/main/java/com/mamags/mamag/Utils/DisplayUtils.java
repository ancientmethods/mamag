package com.mamags.mamag.Utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.mamags.mamag.R;

/**
 * Created by samer on 19/10/2017.
 */

public class DisplayUtils {

    public static void displaySnackbar(View view, String message, int length, Context context) {
        Snackbar snackbar = Snackbar.make(view, message, length);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(ContextCompat.getColor(context,R.color.colorPrimaryDark));
        snackbar.show();
    }
}
