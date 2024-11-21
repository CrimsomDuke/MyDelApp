package com.crimsom.mydelapp.utilities

import android.content.Context
import android.os.Build
import com.techiness.progressdialoglibrary.ProgressDialog

object ProgressDialogBuilder {
    public fun startLoadingDialog(context : Context, title : String, message : String) : ProgressDialog{
        var progressDialog = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            ProgressDialog(context, ProgressDialog.THEME_FOLLOW_SYSTEM)
        } else {
            ProgressDialog(context)
        }

        with(progressDialog){
            setMessage(message)
            setTitle(title)
            show()
        }

        return progressDialog;
    }
}