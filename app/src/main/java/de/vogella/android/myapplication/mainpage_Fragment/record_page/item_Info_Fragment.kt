package de.vogella.android.myapplication.mainpage_Fragment.record_page

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import de.vogella.android.myapplication.R
import de.vogella.android.myapplication.components.requestQueue_Manager
import de.vogella.android.myapplication.mainpage_Fragment.record_page.item_Fragment.item_Adapter
import org.json.JSONArray

class item_Info_Fragment : Fragment() {
    private val url1 = "http://10.0.2.2:3001/trade/income_expend?type=income&date="
    private val url2 = "http://10.0.2.2:3001/trade/income_expend?type=expend&date="
    private lateinit var  date : String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_item_info, container, false)
        date = this.requireArguments().getString("date").toString()
        var data1 : ArrayList<ArrayList<String>>
        var data2 : ArrayList<ArrayList<String>>
        val dateTextView = root.findViewById(R.id.item_info_date) as TextView
        dateTextView.setText(date)
        val manager = activity?.supportFragmentManager
        val recyclerView_income = root.findViewById<RecyclerView>(R.id.recyclerview_income)
        val recyclerView_expend = root.findViewById<RecyclerView>(R.id.recyclerview_expend)


        val requestManage = context?.let { requestQueue_Manager(it) }
        requestManage?.Request("get",url1+date,null,Response.Listener<JSONArray>{  res->
            if(res.length() != 0){
                data1 = changeData(res)
                recyclerView_income.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                recyclerView_income.adapter = manager?.let { item_Adapter(it, true,data1) }
            }
        })

        requestManage?.Request("get",url2+date,null,Response.Listener<JSONArray>{  res->
            if(res.length() != 0){
                data2 = changeData(res)
                recyclerView_expend.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                recyclerView_expend.adapter = manager?.let { item_Adapter(it, false,data2) }
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
            data.add(tempArrayList)
        }
        return data
    }

}