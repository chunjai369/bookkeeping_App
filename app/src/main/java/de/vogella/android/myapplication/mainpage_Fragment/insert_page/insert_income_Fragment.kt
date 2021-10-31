package de.vogella.android.myapplication.mainpage_Fragment.insert_page

import android.app.DatePickerDialog
import android.icu.util.Calendar
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
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class insert_income_Fragment : Fragment() {
    private  val editText_id: ArrayList<Int> =  arrayListOf(
        R.id.insert_time,
        R.id.insert_money,
        R.id.insert_type,
        R.id.insert_pay,
        R.id.insert_info
    )
    private lateinit var queue: RequestQueue
    private val url = "http://10.0.2.2:3001/trade"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_insert_income, container, false)
        val gatdata = get_trade_data(root)
        val btn = root.findViewById<Button>(R.id.insert_save)
        val btn2 = root.findViewById<Button>(R.id.insert_next)
        val date = root.findViewById<EditText>(R.id.insert_time)
        date.onFocusChangeListener = View.OnFocusChangeListener{root, b->
            if (b){
                val c = Calendar.getInstance()
                val year = c.get(Calendar.YEAR)
                val month = c.get(Calendar.MONTH)
                val day = c.get(Calendar.DAY_OF_MONTH)
                activity?.let { DatePickerDialog(it, {
                        view, year, monthOfYear, dayOfMonth ->
                    val d = year.toString() + "-" + (monthOfYear+1).toString() + "-" + dayOfMonth.toString()
                    date.setText(d)
                },year,month,day).show() }
            }
        }

        btn.setOnClickListener {
            val pref = context?.getSharedPreferences("bookkeeping",0)
            val email = pref?.getString("email","")
            val token = pref?.getString("token", "noToken")
            val jsonData = email?.let { it1 -> gatdata.get_jsonData(editText_id, it1,0) }
            Log.v("showLog", jsonData.toString())

            queue = Volley.newRequestQueue(activity)
            val req = object : JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonData,
                { res ->
                    Log.v("showLog", res.toString())
                },
                { error -> Log.e("showLog", error.toString()) }){
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Authorization"] = "Bearer $token"
                    return headers
                }
            }
            queue.add(req)

        }

        btn2.setOnClickListener {
            val editText : ArrayList<EditText> = gatdata.get_EditText(editText_id)
            for(i in editText){
                i.setText("")
            }
        }

        return root
    }
}