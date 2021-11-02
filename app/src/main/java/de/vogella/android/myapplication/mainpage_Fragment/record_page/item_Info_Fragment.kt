package de.vogella.android.myapplication.mainpage_Fragment.record_page

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import de.vogella.android.myapplication.R
import de.vogella.android.myapplication.components.requestQueue_Manager
import de.vogella.android.myapplication.mainpage_Fragment.record_page.item_Fragment.item_Adapter
import org.json.JSONArray
import org.json.JSONObject

class item_Info_Fragment : Fragment() {
    private val url = "http://10.0.2.2:3001/trade/income_expend?date="
    private lateinit var  date : String
    private lateinit var  position : String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_item_info, container, false)
        date = this.requireArguments().getString("date").toString()
        position = this.requireArguments().getString("position").toString()
        var data : ArrayList<ArrayList<String>>
        val dateTextView = root.findViewById(R.id.item_info_date) as TextView
        dateTextView.setText(date)
        val manager = activity?.supportFragmentManager
        val recyclerView_income = root.findViewById<RecyclerView>(R.id.recyclerview)
        val del_btn = root.findViewById<ImageView>(R.id.del_date)
        val requestManage = context?.let { requestQueue_Manager(it) }

        del_btn.setOnClickListener{ v->
            val url = "http://10.0.2.2:3001/trade?type=date&date=$date"
            val requestManage = requestQueue_Manager(v.context)
            requestManage.Request("delete",url,null,Response.Listener<JSONObject>{ res ->
                if (res.getBoolean("is_delete")) {
                    val del_data = Intent()
                    del_data.putExtra("position", position)
                    activity?.setResult(Activity.RESULT_OK, del_data)
                    activity?.finish()
                }
            })
        }

        requestManage?.Request("get",url+date,null,Response.Listener<JSONArray>{  res->
            if(res.length() != 0){
                data = changeData(res)
                recyclerView_income.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                recyclerView_income.adapter = manager?.let { item_Adapter(it, this,position,data) }
            }
        })

        return root
    }

    fun changeData(res:JSONArray) : ArrayList<ArrayList<String>>{
        val data : ArrayList<ArrayList<String>> = arrayListOf()
        for(i in 0 until res.length()){
            var tempArrayList : ArrayList<String> = arrayListOf()
            tempArrayList.add(date)
            tempArrayList.add(res.getJSONObject(i).getString("how_mach"))
            tempArrayList.add(res.getJSONObject(i).getString("class"))
            tempArrayList.add(res.getJSONObject(i).getString("type"))
            tempArrayList.add(res.getJSONObject(i).getString("info"))
            tempArrayList.add(res.getJSONObject(i).getString("tid"))
            tempArrayList.add(res.getJSONObject(i).getString("is_income"))
            data.add(tempArrayList)
        }
        return data
    }

}