package de.vogella.android.signin_upFragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import de.vogella.android.myapplication.R
import de.vogella.android.myapplication.components.createBuilder
import org.json.JSONObject

class signupFragment : Fragment() {
    private lateinit var queue: RequestQueue
    private lateinit var textEmail:EditText
    private lateinit var textPasseord:EditText
    private lateinit var textRepasseord:EditText
    private lateinit var textUsername:EditText
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.signup_fragment, container, false)
        val btn = root.findViewById(R.id.signupButton) as Button
        textEmail = root.findViewById(R.id.signupEmail) as EditText
        textPasseord = root.findViewById(R.id.signupPassword) as EditText
        textRepasseord = root.findViewById(R.id.signupRePassword) as EditText
        textUsername = root.findViewById(R.id.singupUsername) as EditText
        val builderManage = context?.let { createBuilder(it) }
        var email_done = false
        var rePassword_done = false
        textEmail.addTextChangedListener {
            var email = textEmail.text.toString()
            if("@" in email && "." in email)
                if("@." in email || ".@" in email) {
                    textEmail.setTextColor(Color.RED)
                    email_done = false
                }else{
                    textEmail.setTextColor(Color.BLACK)
                    email_done = true
                }
            else{
                textEmail.setTextColor(Color.RED)
                email_done = false
            }
        }

        textRepasseord.addTextChangedListener {
            var password = textPasseord.text.toString()
            var rePassword = textRepasseord.text.toString()
            if(rePassword != password){
                rePassword_done = false
                textRepasseord.setTextColor(Color.RED)
            }else{
                rePassword_done = true
                textRepasseord.setTextColor(Color.BLACK)
            }
        }

        btn.setOnClickListener {
            queue = Volley.newRequestQueue(activity)
            val email = textEmail.text.toString()
            val password = textPasseord.text.toString()
            val username = textUsername.text.toString()
            val url = "http://10.0.2.2:3001/signup"
            val jsonObj = JSONObject()
            jsonObj.put("email",email)
            jsonObj.put("password",password)
            jsonObj.put("username",username)
            if (email_done && rePassword_done && email != "" && password != "" && username != ""){
                val stringRequest = JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    jsonObj,
                    { res ->
                        Log.v("hh",res.toString())
                        if(res.getBoolean("haveEmail")){
                            val bulid = builderManage?.basisBulider(
                                R.string.error,
                                R.string.email_used){}
                            bulid?.create()?.show()
                        }
                        if(res.getBoolean("isSignup")){
                            Log.v("hh",res.toString())
                            clean()
                            val bulid = builderManage?.basisBulider(R.string.tips,
                                R.string.isSignup){}
                            bulid?.create()?.show()
                        }
                    },
                    { error -> Log.e("showLog", error.toString()) })
                queue.add(stringRequest)
            }else{
                if (username == ""){
                    val bulid = builderManage?.basisBulider(R.string.error,
                        R.string.input_username){}
                    bulid?.create()?.show()
                } else {
                    val bulid = builderManage?.basisBulider(
                        R.string.error,
                        R.string.check_email_password){}
                    bulid?.create()?.show()
                }
            }
        }

        return root
    }

    override fun onPause() {
        super.onPause()
        println("onPause")
        clean()
    }

    private fun clean(){
        textEmail.setText("")
        textPasseord.setText("")
        textRepasseord.setText("")
        textUsername.setText("")
    }

}
