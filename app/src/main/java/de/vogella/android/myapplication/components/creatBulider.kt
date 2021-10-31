package de.vogella.android.myapplication.components

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import de.vogella.android.myapplication.R

class creatBulider (context: Context){
    val context = context
    fun basisBulider(title:String , message:String , btnFuntion: DialogInterface.OnClickListener) : AlertDialog.Builder{
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