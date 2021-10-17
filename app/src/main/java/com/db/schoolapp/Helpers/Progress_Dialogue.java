package com.db.schoolapp.Helpers;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import com.db.schoolapp.R;


public class Progress_Dialogue {


    public static Dialog showProgressDialog(Context context, String message) {
        Dialog dialog = null;
        if (dialog == null) {
            dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCanceledOnTouchOutside(false);

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setContentView(R.layout.loader);

        }
        dialog.findViewById(R.id.progressBar);
        dialog.show();
        return dialog;
    }
}
