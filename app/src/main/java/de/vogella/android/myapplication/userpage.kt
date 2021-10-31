package de.vogella.android.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.android.volley.Response
import de.vogella.android.myapplication.R
import de.vogella.android.myapplication.components.requestQueue_Manager
import org.json.JSONObject

class userpage : AppCompatActivity() {
    private val url = "http://10.0.2.2:3001/user"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userpage)
        val emailText = findViewById<TextView>(R.id.userInfo_email)
        val passwordText = findViewById<TextView>(R.id.userInfo_password)
        val usernameText = findViewById<TextView>(R.id.userInfo_name)
        val sex = findViewById<TextView>(R.id.userInfo_sex)
        val createdate = findViewById<TextView>(R.id.userinfo_createdate)

        val queue_Manager = requestQueue_Manager(this)
        queue_Manager.Request("get",url,null,Response.Listener<JSONObject>{
            res ->
                emailText.setText(res.getString("email"))
                usernameText.setText(res.getString("name"))
                if (res.getString("sex")!="null")
                    sex.setText(res.getString("sex"))
                createdate.setText(res.getString("createTime"))
                Log.v("showLog",res.toString())
        })
    }
}