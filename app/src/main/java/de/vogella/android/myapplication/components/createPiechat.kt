package de.vogella.android.myapplication.components

import android.content.Context
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import de.vogella.android.myapplication.R

class createPiechat(context:Context) {
    val context = context

    fun getPiechatData(data:ArrayList<ArrayList<String>>) : PieData{
        val entries = ArrayList<PieEntry>()
        for(i in data)
            entries.add(PieEntry(i[1].toFloat(),i[0]))

        val colors = ArrayList<Int>()
        colors.add(ContextCompat.getColor(context,R.color.black))
        colors.add(ContextCompat.getColor(context,R.color.purple_700))

        val dataSet = PieDataSet(entries,"")
        dataSet.setColors(colors)

        val pieData = PieData(dataSet)
        pieData.setDrawValues(true)
        return pieData
    }
}