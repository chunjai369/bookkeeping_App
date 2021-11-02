package de.vogella.android.myapplication.mainpage_Fragment.record_page

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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
    protected lateinit var recyclerView : RecyclerView
    protected lateinit var incomeView : TextView
    protected lateinit var expendView : TextView
    protected lateinit var totalView : TextView
    protected lateinit var launchSomeActivity : ActivityResultLauncher<Intent>
    protected lateinit var data :ArrayList<ArrayList<String>>
    var total_Income  = 0
    var total_Expend  = 0
    var year_beforePosition =0
    var month_beforePosition =0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_record, container, false)
        recyclerView = root.findViewById(R.id.show_item)
        incomeView = root.findViewById(R.id.record_income)
        expendView = root.findViewById(R.id.record_expend)
        totalView = root.findViewById(R.id.record_total)
        month_spinner = root.findViewById(R.id.month)
        year_spinner = root.findViewById(R.id.year)
        val year_adapter = activity?.let { ArrayAdapter.createFromResource(it, R.array.years, android.R.layout.simple_spinner_dropdown_item) }
        year_spinner.adapter = year_adapter
        val month_adapter = activity?.let { ArrayAdapter.createFromResource(it, R.array.months, android.R.layout.simple_spinner_dropdown_item) }
        month_spinner.adapter = month_adapter
        year_beforePosition =0
        month_beforePosition =0
        launchSomeActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val position = result.data?.getStringExtra("position")?.toInt()
                if (position != null) {
                    data.removeAt(position)
                    recyclerView.adapter?.notifyItemRemoved(position)
                    recyclerView.adapter?.notifyItemRangeChanged(position, data.size)
                }
            }
        }

        val queue_Manager = context?.let { requestQueue_Manager(it) }
        if (queue_Manager != null) {
            queue_Manager.Request(
                "get", getUrl(), null,
                Response.Listener<JSONArray>{ res ->
                    val (a,b,c) = getshowdata(res)
                    data= a
                    total_Income=b
                    total_Expend=c
                    incomeView.setText("$"+total_Income.toString())
                    expendView.setText("$"+total_Expend.toString())
                    totalView.setText("$"+(total_Income-total_Expend).toString())
                    resetRecyclerView(data)
                }
            )
        }
        return root
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        year_spinner.setSelection(0)
        month_spinner.setSelection(0)
        year_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position != year_beforePosition){
                    year_beforePosition = position
                    val queue_Manager = context?.let { requestQueue_Manager(it) }
                    queue_Manager?.Request2("get",getUrl(), null, Response.Listener{ res ->
                        val (a,b,c) = getshowdata(res)
                        data= a
                        total_Income=b
                        total_Expend=c
                        incomeView.setText("$"+total_Income.toString())
                        expendView.setText("$"+total_Expend.toString())
                        totalView.setText("$"+(total_Income-total_Expend).toString())
                        resetRecyclerView(data)
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
                        val (a,b,c) = getshowdata(res)
                        data= a
                        total_Income=b
                        total_Expend=c
                        incomeView.setText("$"+total_Income.toString())
                        expendView.setText("$"+total_Expend.toString())
                        totalView.setText("$"+(total_Income-total_Expend).toString())
                        resetRecyclerView(data)
                    })
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

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

    fun resetRecyclerView(data:ArrayList<ArrayList<String>>){
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = record_Adapter(launchSomeActivity,data)
    }

}