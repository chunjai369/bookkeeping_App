package de.vogella.android.myapplication.components

import android.view.View
import android.widget.EditText

open class get_data(view : View) {
    private val view1 = view

    fun get_EditText(editText_Id:ArrayList<Int>) : ArrayList<EditText>{
        var editText: ArrayList<EditText> =  arrayListOf()
        for(i in editText_Id){
            var edit = view1.findViewById<EditText>(i)
            editText.add(edit)
        }
        return editText
    }

    fun get_data(editText_Id:ArrayList<Int>) : ArrayList<String> {
        var editText = get_EditText(editText_Id)
        var data : ArrayList<String> =  arrayListOf()
        for(i in editText){
            data.add(i.text.toString())
        }
        return data
    }
}