package de.vogella.android.myapplication.mainpage_Fragment.insert_page
import android.util.Log
import android.view.View
import de.vogella.android.myapplication.components.get_data
import org.json.JSONObject

class get_trade_data(view : View) : get_data(view){
    fun get_jsonData(editText_Id:ArrayList<Int>,email : String, type : Int) :JSONObject{
        val data = get_data(editText_Id)
        val jsonObj = JSONObject()
        jsonObj.put("email",email)
        jsonObj.put("is_income",type.toString())
        jsonObj.put("date",data[0])
        jsonObj.put("how_mach",data[1])
        jsonObj.put("class",data[2])
        jsonObj.put("type",data[3])
        jsonObj.put("info",data[4])
        return jsonObj
    }
}