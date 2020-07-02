package uz.mirsaidoff.curexrate.common

import android.app.AlertDialog
import android.content.Context

fun Context.showAlertDialog(message: String) {
    AlertDialog.Builder(this)
        .setMessage(message)
        .setNeutralButton("OK") { dialog, _ -> dialog.dismiss() }
        .create()
        .show()
}
