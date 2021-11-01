package de.vogella.android.myapplication.mainpage_Fragment.record_page

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.vogella.android.myapplication.R
import de.vogella.android.myapplication.record_itemInformationActivity


class record_Adapter (private val dataSet: ArrayList<ArrayList<String>>):
    RecyclerView.Adapter<record_Adapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView = view.findViewById(R.id.long_information) as ImageView
        var textView: ArrayList<TextView> = arrayListOf()
        val textView_id :ArrayList<Int> = arrayListOf(
            R.id.record_Item_date
            ,R.id.record_Item_income
            ,R.id.record_Item_expend
            ,R.id.record_Item_total
        )
        init {
            for(i in textView_id)
                textView.add(view.findViewById(i))
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.record_item_view, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        var j  = 0
        for (i in viewHolder.textView) {
            when(j){
                0 -> i.text = dataSet[position][j]
                else -> i.text = "$" + dataSet[position][j]
            }
            j++
        }
        viewHolder.imageView.setOnClickListener{ v ->
            Log.v("hihi","ggg")
            val intent =Intent(v.context, record_itemInformationActivity::class.java)
            intent.putExtra("date", dataSet[position][0])
            v.context.startActivity(intent)
        }
    }

    override fun getItemCount() = dataSet.size

}