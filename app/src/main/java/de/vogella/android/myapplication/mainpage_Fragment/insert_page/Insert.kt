package de.vogella.android.myapplication.mainpage_Fragment.insert_page

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import de.vogella.android.myapplication.PageAdapter
import de.vogella.android.myapplication.R

class insert : Fragment() {
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var pagerAdapter : PageAdapter
    private val title: ArrayList<String> = arrayListOf("收入", "支出")
    var fragments = arrayListOf(
        insert_income_Fragment(),
        insert_expend_Fragment()
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_insert, container, false)
        viewPager = root.findViewById(R.id.insert_container)
        viewPager.setSaveEnabled(false)
        tabLayout = root.findViewById(R.id.insert_tabLayout)
        pagerAdapter = PageAdapter(getChildFragmentManager(),lifecycle,fragments)
        viewPager.adapter = pagerAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = title[position]
        }.attach()
        return root
    }

}