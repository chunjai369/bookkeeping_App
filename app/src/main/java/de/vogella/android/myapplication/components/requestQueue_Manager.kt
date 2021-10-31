package de.vogella.android.myapplication.components
import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

class requestQueue_Manager(context: Context) {
    private val context = context
    private val pref = context?.getSharedPreferences("bookkeeping",0)
    private val token = pref?.getString("token", "noToken")
    private lateinit var queue: RequestQueue

    fun Request(Method:String,url:String,resF :Response.Listener<JSONObject>){
        queue = Volley.newRequestQueue(context)
        val req = JsonObjectRequest(when(Method){
            "get" -> Request.Method.GET
            "post" -> Request.Method.POST
            "patch" -> Request.Method.PATCH
            else -> Request.Method.DELETE
        },
            url,
            null,
            resF,
            {error -> Log.e("showLog", error.toString())})
        queue.add(req)
    }

    fun Request(Method:String,url:String,jsonObj:JSONObject?,resF : Response.Listener<JSONObject>){

        queue = Volley.newRequestQueue(context)
        val req = object : JsonObjectRequest(
            when(Method){
                "get" -> Request.Method.GET
                "post" -> Request.Method.POST
                "patch" -> Request.Method.PATCH
                else -> Request.Method.DELETE
                        },
            url,
            jsonObj,
            resF,
            { error -> Log.e("showLog", error.toString()) }){
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Bearer $token"
                return headers
            }
        }
        queue.add(req)
        }

    fun Request(Method:String,url:String,jsonObj:JSONArray?,resF : Response.Listener<JSONArray>){
        queue = Volley.newRequestQueue(context)
        val req = object : JsonArrayRequest(
            when(Method){
                "get" -> Request.Method.GET
                "post" -> Request.Method.POST
                "patch" -> Request.Method.PATCH
                else -> Request.Method.DELETE
            },
            url,
            jsonObj,
            resF,
            { error -> Log.e("showLog", error.toString()) }){
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Bearer $token"
                return headers
            }
        }
        queue.add(req)
    }


    fun Request2(Method:String, url:String, jsonObj:JSONObject?, resF : Response.Listener<JSONArray?>){
        queue = Volley.newRequestQueue(context)
        val req = object : CustomJsonArrayRequest(when(Method){
            "get" -> Request.Method.GET
            "post" -> Request.Method.POST
            "patch" -> Request.Method.PATCH
            else -> Request.Method.DELETE
            },
            url,
            jsonObj,
            resF,
            { error -> Log.e("showLog", error.toString()) }){
            override fun getHeaders():MutableMap<String, String>{
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Bearer $token"
                return headers
            }
        }
        queue.add(req)
    }

}
