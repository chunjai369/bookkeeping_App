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
import de.vogella.android.myapplication.R
import de.vogella.android.myapplication.components.createBuilder
import de.vogella.android.myapplication.components.get_data
import de.vogella.android.myapplication.components.requestQueue_Manager
import kotlin.collections.ArrayList


class insert_expend_Fragment : Fragment() {
    private  val editText_id: ArrayList<Int> =  arrayListOf(
        R.id.expend_time,
        R.id.expend_money,
        R.id.expend_type,
        R.id.expend_pay,
        R.id.expend_info
    )
    private val url = "http://10.0.2.2:3001/trade"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_insert_expend, container, false)
        val gatdata = get_trade_data(root)
        val btn = root.findViewById<Button>(R.id.expend_save)
        val btn2 = root.findViewById<Button>(R.id.expend_clear)
        val date = root.findViewById<EditText>(R.id.expend_time)
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
            val jsonData = gatdata.get_jsonData(editText_id,1)

            val requestManage = context?.let{ requestQueue_Manager(it) }
            requestManage?.Request("post",url,jsonData){ res ->
                if (res.getBoolean("is_save")){
                    val builder = context?.let{ createBuilder(it) }
                    builder?.basisBulider(R.string.tips,R.string.is_save){}?.show()
                    clear()
                }else{
                    val builder = context?.let{ createBuilder(it) }
                    builder?.basisBulider(R.string.error,R.string.not_save){}?.show()
                }
            }
        }

        btn2.setOnClickListener {
            clear()
        }

        return root
    }

    override fun onPause() {
        super.onPause()
        clear()
    }

    fun clear(){
        val getEditView = view?.let { get_data(it) }?.get_EditText(editText_id)
        if (getEditView != null)
            for (i in getEditView)
                i.setText("")
    }
}