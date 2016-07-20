package com.z.myapplication;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by z on 2016/5/20.
 */
public class DialogUtil {

    private static AlertDialog sDialog;

    public static void show(Context context){
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = View.inflate(context,R.layout.dialog_tishi,null);
        view.findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sDialog.cancel();
            }
        });
        sDialog = builder.setView(view).create();
        sDialog.show();
        WindowManager.LayoutParams params = sDialog.getWindow().getAttributes();
        params.height= WindowManager.LayoutParams.WRAP_CONTENT;
        params.width= WindowManager.LayoutParams.WRAP_CONTENT;
    }

}
