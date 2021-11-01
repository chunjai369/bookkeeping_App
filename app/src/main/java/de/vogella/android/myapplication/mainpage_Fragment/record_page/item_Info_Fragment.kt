package de.vogella.android.myapplication.mainpage_Fragment.record_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.vogella.android.myapplication.R
import de.vogella.android.myapplication.mainpage_Fragment.record_page.item_Fragment.item_Adapter

class item_Info_Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_item_info, container, false)
        val date = this.requireArguments().getString("date")
        val dateTextView = root.findViewById(R.id.item_info_date) as TextView
        dateTextView.setText(date)
        val manager = activity?.supportFragmentManager
        val recyclerView_income = root.findViewById<RecyclerView>(R.id.recyclerview_income)
        val recyclerView_expend = root.findViewById<RecyclerView>(R.id.recyclerview_expend)

        val testdata1 = arrayListOf(
            arrayListOf("aaa","aaa","123"),
            arrayListOf("aaaa","aaf","1233"),
            arrayListOf("aaab","aag","1234"),
            arrayListOf("aaac","aah","1235"),
            arrayListOf("aaad","aaj","1236"),
            arrayListOf("aaae","aak","1237"),
        )

        val testdata2 = arrayListOf(
            arrayListOf("aaar","aaa","123"),
            arrayListOf("aaaae","aaqf","71233"),
            arrayListOf("aaabw","aasg","31234"),
            arrayListOf("aaacy","aaxh","41235"),
            arrayListOf("aaadu","aafj","61236"),
            arrayListOf("aaaei","aavk","81237"),
        )

        recyclerView_income.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView_income.adapter = manager?.let { item_Adapter(it, true,testdata1) }

        recyclerView_expend.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView_expend.adapter = manager?.let { item_Adapter(it, false,testdata2) }
        return root
    }

}