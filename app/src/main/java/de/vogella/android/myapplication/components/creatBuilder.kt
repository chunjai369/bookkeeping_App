package de.vogella.android.myapplication.components

import android.content.Context
import androidx.appcompat.app.AlertDialog
import de.vogella.android.myapplication.R

class createBuilder (context: Context){
    val context = context
    fun basisBulider(title:Int, message:Int) : AlertDialog.Builder{
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("Confirm", { ialog, whichButton -> System.exit(0) })
        builder.setOnCancelListener{System.exit(0)}
        return  builder
    }

    fun basisBulider(title:Int, message:Int , is_OnCance:Boolean? = false,btnFuntion: (()->Unit)?) : AlertDialog.Builder{
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("Confirm", { ialog, whichButton -> btnFuntion })
        if (is_OnCance != null){
            if (is_OnCance){
                builder.setOnCancelListener{btnFuntion}
            }
        }
        return  builder
    }

}

//{ dialog, whichButton ->
//    System.exit(0)
//}