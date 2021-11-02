package de.vogella.android.myapplication.mainpage_Fragment.record_page.item_Fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import de.vogella.android.myapplication.R
import de.vogella.android.myapplication.components.requestQueue_Manager
import de.vogella.android.myapplication.mainpage_Fragment.insert_page.insert_expend_Fragment
import de.vogella.android.myapplication.mainpage_Fragment.insert_page.insert_income_Fragment
import de.vogella.android.myapplication.record_itemInformationActivity
import org.json.JSONObject

class item_Adapter (private val fam: FragmentManager,
                    private val fa: Fragment,
                    private val is_income:Boolean,
                    private val dataSet: ArrayList<ArrayList<String>>):
    RecyclerView.Adapter<item_Adapter.ViewHolder>(){
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val edit_imageView = view.findViewById(R.id.item_edit) as ImageView
        val delete_imageView = view.findViewById(R.id.item_del) as ImageView
        var textView: ArrayList<TextView> = arrayListOf()
        val textView_id :ArrayList<Int> = arrayListOf(
            R.id.item_type
            , R.id.item_class
            , R.id.item_money
        )
        init {
            for(i in textView_id)
                textView.add(view.findViewById(i))
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.record_item_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        var j  = 0
        for (i in viewHolder.textView) {
            when(j){
                2 -> i.text = "$" + dataSet[position][3-j]
                else -> i.text = dataSet[position][3-j]
            }
            j++
        }

        viewHolder.edit_imageView.setOnClickListener{
                val transaction = fam.beginTransaction()
                transaction.addToBackStack("firstPage")
                val bundle = Bundle()
                bundle.putStringArrayList("data",dataSet[position])
                bundle.putString("method","patch")
                var fragment:Fragment
                if (is_income)
                    fragment = insert_income_Fragment()
                else
                    fragment = insert_expend_Fragment()
                fragment.arguments = bundle
                transaction.replace(R.id.item_Info_container,fragment)
                transaction.commit()
        }

        viewHolder.delete_imageView.setOnClickListener{ v->
            val url = "http://10.0.2.2:3001/trade?tid="+dataSet[position][5]
            val requestManage = requestQueue_Manager(v.context)
            requestManage.Request("delete",url,null,Response.Listener<JSONObject>{ res ->
                if (res.getBoolean("is_delete")) {
                    deleteItem(position)
                    if (dataSet.size == 0)
                    Log.v("aaa",dataSet.size.toString())
                    val del_data = Intent()
                    del_data.putExtra("position", p)
                    fa.activity?.setResult(Activity.RESULT_OK, del_data)
                    fa.activity?.finish()
                    //fa.activity?.finish()
                }
            })
        }
    }

    override fun getItemCount() = dataSet.size

    private fun deleteItem(position: Int) {
        dataSet.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, dataSet.size)
    }
}