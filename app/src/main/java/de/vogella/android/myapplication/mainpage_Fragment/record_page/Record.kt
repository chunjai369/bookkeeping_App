package de.vogella.android.myapplication.mainpage_Fragment.record_page

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import de.vogella.android.myapplication.R
import de.vogella.android.myapplication.components.requestQueue_Manager
import org.json.JSONArray
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class record : Fragment() {
    private val url = "http://10.0.2.2:3001/trade?"
    protected lateinit var year_spinner : Spinner
    protected lateinit var month_spinner : Spinner
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_record, container, false)
        val recyclerView = root.findViewById<RecyclerView>(R.id.show_item)
        val incomeView = root.findViewById<TextView>(R.id.record_income)
        val expendView = root.findViewById<TextView>(R.id.record_expend)
        val totalView = root.findViewById<TextView>(R.id.record_total)
        month_spinner = root.findViewById<Spinner>(R.id.month)
        year_spinner = root.findViewById<Spinner>(R.id.year)
        val year_adapter = activity?.let { ArrayAdapter.createFromResource(it, R.array.years, android.R.layout.simple_spinner_dropdown_item) }
        year_spinner.adapter = year_adapter
        val month_adapter = activity?.let { ArrayAdapter.createFromResource(it, R.array.months, android.R.layout.simple_spinner_dropdown_item) }
        month_spinner.adapter = month_adapter
        var year_beforePosition = year_spinner.getSelectedItemPosition()
        var  month_beforePosition =  month_spinner.getSelectedItemPosition()

        year_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position != year_beforePosition){
                    year_beforePosition = position
                    val queue_Manager = context?.let { requestQueue_Manager(it) }
                    queue_Manager?.Request2("get",getUrl(), null, Response.Listener{ res ->
                        val (data,total_Income,total_Expend) = getshowdata(res)
                        incomeView.setText("$"+total_Income.toString())
                        expendView.setText("$"+total_Expend.toString())
                        totalView.setText("$"+(total_Income-total_Expend).toString())
                        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                        recyclerView.adapter = record_Adapter(data)
                    })
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }


        month_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position != month_beforePosition){
                    month_beforePosition = position
                    val queue_Manager = context?.let { requestQueue_Manager(it) }
                    queue_Manager?.Request2("get",getUrl(), null, Response.Listener{ res ->
                        val (data,total_Income,total_Expend) = getshowdata(res)
                        incomeView.setText("$"+total_Income.toString())
                        expendView.setText("$"+total_Expend.toString())
                        totalView.setText("$"+(total_Income-total_Expend).toString())
                        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                        recyclerView.adapter = record_Adapter(data)
                    })
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }


        val queue_Manager = context?.let { requestQueue_Manager(it) }
        if (queue_Manager != null) {
            queue_Manager.Request(
                "get", getUrl(), null,
                Response.Listener<JSONArray>{ res ->
                    val (data,total_Income,total_Expend) = getshowdata(res)
                    incomeView.setText("$"+total_Income.toString())
                    expendView.setText("$"+total_Expend.toString())
                    totalView.setText("$"+(total_Income-total_Expend).toString())
                    recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                    recyclerView.adapter = record_Adapter(data)
                }
            )
        }
        return root
    }

    override fun onPause() {
        super.onPause()
        year_spinner.setSelection(0)
        month_spinner.setSelection(0)
    }

    fun getshowdata(res:JSONArray?) : Triple<ArrayList<ArrayList<String>>,Int,Int>{
        var datamap  =  mutableMapOf<String,Array<Int>>()
        var total_Income = 0
        var total_Expend = 0
        val data : ArrayList<ArrayList<String>> = arrayListOf()
        if (res != null) {
            for(i in 0..res.length()-1){
                var temp : Array<Int> = arrayOf(0,0)
                var tempData = res.getJSONObject(i).getString("how_mach").toInt()
                var tempType = res.getJSONObject(i).getString("is_income").toInt()
                var tempDate = res.getJSONObject(i).getString("date")
                if (!datamap.containsKey(tempDate)){
                    temp[tempType] = tempData
                    datamap.put(tempDate,temp)
                }else{
                    temp = datamap[tempDate]!!
                    temp[tempType] += tempData
                    datamap.put(tempDate,temp)
                }
            }
        }
        for (i in datamap.keys){
            var tempArrayList : ArrayList<String> = arrayListOf()
            var temp1 = datamap[i]?.get(0)
            var temp2 = datamap[i]?.get(1)
            var temptotal = temp1?.minus(temp2!!)
            val dateformat =  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            dateformat.timeZone = TimeZone.getTimeZone("GMT-8")
            val format = SimpleDateFormat("yyyy-MM-dd")
            val date = format.format(dateformat.parse(i))
            tempArrayList.add(date)
            tempArrayList.add(temp1.toString())
            tempArrayList.add(temp2.toString())
            tempArrayList.add(temptotal.toString())
            if (temp1 != null && temp2 != null) {
                total_Income += temp1
                total_Expend += temp2
            }
            data.add(tempArrayList)
        }

        return Triple(data,total_Income,total_Expend)
    }

    fun getUrl():String{
        val year = year_spinner.getSelectedItem().toString()
        val month = month_spinner.getSelectedItem().toString()
        var url2 = url+"year=$year&month=$month"
        return url2
    }

}