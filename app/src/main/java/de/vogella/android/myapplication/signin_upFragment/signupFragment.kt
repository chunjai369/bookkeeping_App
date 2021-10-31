package de.vogella.android.signin_upFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import de.vogella.android.myapplication.R

class signupFragment : Fragment() {
    private lateinit var queue: RequestQueue
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.signup_fragment, container, false)
        val btn = root.findViewById(R.id.signupButton) as Button
        val textEmail = root.findViewById(R.id.signupEmail) as EditText
        val textPasseord = root.findViewById(R.id.signupPassword) as EditText
        val textRepasseord = root.findViewById(R.id.signupRePassword) as EditText
        val textUsername = root.findViewById(R.id.singupUsername) as EditText

        btn.setOnClickListener {
            Log.v("showLog",checkpassword(textPasseord.text.toString() , textRepasseord.text.toString()).toString())
            queue = Volley.newRequestQueue(activity)
            val url = "http://10.0.2.2:3001/signup"
            val stringRequest = object :StringRequest(
                Method.POST,
                url,
                { response -> Log.v("showLog", response) },
                { error -> Log.e("showLog", error.toString()) }){
                override fun getParams(): Map<String, String> {
                    val params = HashMap<String, String>()
                    params["email"]=textEmail.text.toString()
                    params["password"]=textPasseord.text.toString()
                    params["username"]=textUsername.text.toString()
                    return params
                }
            }
            queue.add(stringRequest)
        }

        return root
    }

    fun checkpassword(password:String , repassword:String): Boolean{
        return if (password == repassword){
            true
        }else{
            false
        }
    }

}
