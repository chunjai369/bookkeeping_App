package de.vogella.android.myapplication
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var queue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()

        val url = "http://10.0.2.2:3001/"
        queue = Volley.newRequestQueue(this)
        Handler(Looper.getMainLooper()).post{
            Log.v("showLog","start")
            val pref = getSharedPreferences("bookkeeping",0)
            val token = pref.getString("token", "noToken")
            if (token == "noToken") {
                goLogin()
            }else{
                val req = object : JsonObjectRequest(Request.Method.POST,
                    url,
                    null,
                    {res ->
                        val isVerify = res.getBoolean("verify")
                        if(isVerify){
                            startActivity(Intent(this, mainpage::class.java))
                            finish()
                        }else{
                            pref.edit().remove("token").commit()
                            goLogin()
                        }
                    },
                    {error -> Log.e("showLog", error.toString()) }){

                    override fun getHeaders(): MutableMap<String, String> {
                        val headers = HashMap<String, String>()
                        headers["Authorization"] = "Bearer $token"
                        return headers
                    }
                }
                queue.add(req)
            }
        }
    }

    private fun goLogin() {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, signin_upActivity::class.java))
            finish()
        }, 2000)
    }
}