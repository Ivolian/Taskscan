package com.unicorn.taskscan.utils;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;

public class DialogUtils {

    public static MaterialDialog showMask(final Context context,final String title){
       return new MaterialDialog.Builder(context)
                .title(title)
                .progress(true, 0)
                .show();
    }

}
