package de.vogella.android.myapplication.mainpage_Fragment.analyze_page

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.vogella.android.myapplication.R

class report_Adapter(private val dataSet: ArrayList<ArrayList<String>>):
    RecyclerView.Adapter<report_Adapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textView: ArrayList<TextView> = arrayListOf()
        val textView_id :ArrayList<Int> = arrayListOf(
            R.id.report_class
            , R.id.report_val
            , R.id.report_money
        )
        init {
            for(i in textView_id)
                textView.add(view.findViewById(i))
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.report_item_view, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        var j  = 0
        for (i in viewHolder.textView) {
            when(j){
                1 -> i.text = dataSet[position][j] + "  %"
                0 -> i.text = dataSet[position][j]
                else -> i.text = "$" + dataSet[position][j]
            }
            j++
        }
    }

    override fun getItemCount() = dataSet.size
}