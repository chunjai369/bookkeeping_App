package de.vogella.android.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.android.volley.Response
import de.vogella.android.myapplication.components.requestQueue_Manager
import org.json.JSONObject

class app_info_page : AppCompatActivity() {
    private val url = "http://10.0.2.2:3001/appinfo"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_info_page)

        val updateText = findViewById<TextView>(R.id.app_update_date)
        val versionText = findViewById<TextView>(R.id.app_update_ver)
        val infoText = findViewById<TextView>(R.id.app_update_info)
        val privacyText = findViewById<TextView>(R.id.app_update_privacy)
        val queue_Manager = requestQueue_Manager(this)
        queue_Manager.Request("get",url,null, Response.Listener<JSONObject>{
                res ->
            updateText.setText(res.getString("date"))
            versionText.setText(res.getString("version"))
            infoText.setText(res.getString("info"))
            privacyText.setText(res.getString("privacy"))
            Log.v("showLog",res.toString())
        })

    }
}