package de.vogella.android.myapplication.mainpage_Fragment.record_page

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.vogella.android.myapplication.R

class record_Adapter (private val dataSet: ArrayList<ArrayList<String>>):
    RecyclerView.Adapter<record_Adapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
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
    }

    override fun getItemCount() = dataSet.size

}