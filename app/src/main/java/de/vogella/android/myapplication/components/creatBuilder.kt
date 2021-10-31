package de.vogella.android.myapplication.components

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import de.vogella.android.myapplication.R

class createBuilder (context: Context){
    val context = context
    fun basisBulider(title:Int , message:Int , btnFuntion: DialogInterface.OnClickListener) : AlertDialog.Builder{
        val builder = AlertDialog.Builder(context)
        builder.setTitle(R.string.error)
        builder.setMessage(R.string.Connect_time_out)
        builder.setPositiveButton("Confirm",btnFuntion)
        return  builder
    }

}

//{ dialog, whichButton ->
//    System.exit(0)
//}