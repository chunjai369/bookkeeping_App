package de.vogella.android.signin_upFragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import de.vogella.android.myapplication.R
import de.vogella.android.myapplication.components.createBuilder
import de.vogella.android.myapplication.mainpage
import org.json.JSONObject

class signinFragment : Fragment() {
    private lateinit var queue: RequestQueue
    private lateinit var textPasseord:EditText
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.signin_fragment, container, false)
        val btn = root.findViewById(R.id.signinButton) as Button
        val textEmail = root.findViewById(R.id.signinEmail) as EditText
        val buliderManager = context?.let{ createBuilder(it) }
        textPasseord = root.findViewById(R.id.singinPassword)
        val pref = context?.getSharedPreferences("bookkeeping",0)
        val url = "http://10.0.2.2:3001/login"
        if (pref != null) {
            if(pref.getString("email","") != ""){
                textEmail.setText(pref.getString("email",""))
            }
        }
        btn.setOnClickListener {
            val jsonObj = JSONObject()
            jsonObj.put("email",textEmail.text.toString())
            jsonObj.put("password",textPasseord.text.toString())
            queue = Volley.newRequestQueue(activity)
            val req = JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonObj,
                { res ->
                    val isVerify  = res.getBoolean("verify")
                    if (isVerify){
                        val token = res.get("token").toString()
                        val editor = pref?.edit()
                        val isCommit = editor?.putString("token",token)?.commit()
                        editor?.putString("email",textEmail.text.toString())?.apply()
                        if(isCommit == true){
                            startActivity(Intent(activity, mainpage::class.java))
                            activity?.finish()
                        }else{
                            Log.e("showLog", "Commit error")
                            val builder = buliderManager?.basisBulider(R.string.error,R.string.local_commit_error,null)
                            builder?.create()?.show()
                        }
                    }else{
                        Log.e("showLog", "login fall")
                        val builder = buliderManager?.basisBulider(R.string.loginfail,R.string.check_email_password,null)
                        builder?.create()?.show()
                    }
                },
                { error -> Log.e("showLog", error.toString()) })

            queue.add(req)
        }
        return root
    }

    override fun onPause() {
        super.onPause()
        textPasseord.setText("")
    }

}
