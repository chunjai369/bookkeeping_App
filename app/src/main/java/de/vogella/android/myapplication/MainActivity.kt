package de.vogella.android.myapplication
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import de.vogella.android.myapplication.components.requestQueue_Manager
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
                val req = requestQueue_Manager(this)
                req.Request("post",url,null,Response.Listener<JSONObject>{ res ->
                    val isVerify = res.getBoolean("verify")
                    if(isVerify){
                        startActivity(Intent(this, mainpage::class.java))
                        finish()
                    }else{
                        pref.edit().remove("token").commit()
                        goLogin()
                    }
                })
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