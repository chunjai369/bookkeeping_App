package de.vogella.android.myapplication
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.android.volley.Response
import de.vogella.android.myapplication.components.requestQueue_Manager
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        val req = requestQueue_Manager(this)
        val url = "http://10.0.2.2:3001/"
        var overConnect = false
        var isConnect = false
        var oldVersion = false
        Handler(Looper.getMainLooper()).postDelayed({
            if(!isConnect) {
                overConnect = true
                val builder = AlertDialog.Builder(this)
                builder.setTitle(R.string.error)
                builder.setMessage(R.string.Connect_time_out)
                builder.setPositiveButton("Confirm",{ dialog, whichButton ->
                    System.exit(0)
                })
                val dialog = builder.create()
                dialog.show()
            }
        },10000)

        Handler(Looper.getMainLooper()).post{
            req.Request("get",url,Response.Listener<JSONObject>{ res ->
                isConnect = res.getBoolean("is_connect")
                if(isConnect && !overConnect){
                    val pref = getSharedPreferences("bookkeeping",0)
                    val token = pref.getString("token", "noToken")
                    val version = pref.getString("version", "noVersion")
                    if (version == "noVersion"){
                        pref.edit().putString("version",res.getString("Version")).apply()
                    }else{
                        if (version != res.getString("Version")){
                            oldVersion = true
                            val builder = AlertDialog.Builder(this)
                            builder.setTitle(R.string.error)
                            builder.setMessage(R.string.update_version)
                            builder.setPositiveButton("Confirm",{ dialog, whichButton ->
                                System.exit(0)
                            })
                            val dialog = builder.create()
                            dialog.show()
                        }
                    }
                    if(!oldVersion){
                        if (token == "noToken") {
                            goLogin()
                        }else{
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
            })
        }
    }

    private fun goLogin() {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, signin_upActivity::class.java))
            finish()
        }, 2000)
    }

}