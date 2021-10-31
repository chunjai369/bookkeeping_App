package de.vogella.android.myapplication.mainpage_Fragment.analyze_page

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.github.mikephil.charting.charts.PieChart
import de.vogella.android.myapplication.R
import de.vogella.android.myapplication.components.createPiechat
import de.vogella.android.myapplication.components.requestQueue_Manager
import org.json.JSONArray

class expendReport_Fragment : Fragment() {
    private val url = "http://10.0.2.2:3001/trade/expend"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_expend_report, container, false)

        val piechart = root.findViewById<PieChart>(R.id.pie_chart2)
        var datamap  =  mutableMapOf<String,Int>()
        val data : ArrayList<ArrayList<String>> = arrayListOf()
        val recyclerView = root.findViewById<RecyclerView>(R.id.report_RecyclerView2)
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        piechart.getDescription().setEnabled(false)
        val queue_Manager = context?.let { requestQueue_Manager(it) }
        if (queue_Manager != null) {
            queue_Manager.Request("get",url,null, Response.Listener<JSONArray>{
                    res ->
                Log.v("showLog",res.getJSONObject(0).toString())
                var total = 0F
                for(i in 0..res.length()-1){
                    var tempClass = res.getJSONObject(i).getString("class").toString()
                    var tempMoney = res.getJSONObject(i).getString("how_mach").toInt()
                    if (!datamap.containsKey(tempClass)) {
                        datamap.put(tempClass, tempMoney)
                    }else{
                        var temp = datamap[tempClass]!!
                        temp += tempMoney
                        datamap.put(tempClass,temp)
                    }
                    total += tempMoney
                }
                for (i in datamap.keys){
                    var tempArrayList : ArrayList<String> = arrayListOf()
                    var temp1 = datamap[i]
                    tempArrayList.add(i)
                    if (temp1 != null) {
                        tempArrayList.add(String.format("%.2f",((temp1/total)*100)))
                    }
                    tempArrayList.add(temp1.toString())
                    data.add(tempArrayList)
                }
                recyclerView.adapter = report_Adapter(data)
                val pieData = context?.let { createPiechat(it) }?.getPiechatData(data)
                piechart.setData(pieData)
                piechart.invalidate()
            })
        }

        return root
    }

}