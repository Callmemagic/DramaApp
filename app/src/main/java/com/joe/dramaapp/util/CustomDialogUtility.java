package com.joe.dramaapp.util;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;
import com.joe.dramaapp.R;

public class CustomDialogUtility {

    public static void showDialog(Context context, String title, String msg) {
        new MaterialDialog.Builder(context)
                .title(title)
                .content(msg)
                .positiveText(R.string.confirm)
                .show();
    }

    public static void showDialog(Context context, String title, String msg, MaterialDialog.SingleButtonCallback callback) {
        new MaterialDialog.Builder(context)
                .title(title)
                .content(msg)
                .positiveText(R.string.confirm)
                .onPositive(callback)
                .cancelable(false)
                .show();
    }

    public static void showDialogWithOKandCancel(Context context, String title, String msg, MaterialDialog.SingleButtonCallback callback) {
        new MaterialDialog.Builder(context)
                .title(title)
                .content(msg)
                .positiveText(R.string.confirm)
                .negativeText(R.string.cancel)
                .onPositive(callback)
                .onNegative(callback)
                .show();
    }
}
