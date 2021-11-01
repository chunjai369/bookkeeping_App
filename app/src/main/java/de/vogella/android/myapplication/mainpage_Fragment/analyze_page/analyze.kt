package de.vogella.android.myapplication.mainpage_Fragment.analyze_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import de.vogella.android.myapplication.PageAdapter
import de.vogella.android.myapplication.R

class analyze : Fragment() {

    protected lateinit var viewPager : ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var pagerAdapter : PageAdapter
    private val title: ArrayList<Int> = arrayListOf(R.string.incomeReprot, R.string.expendReprot)
    var fragments = arrayListOf(
        incomeReport_Fragment(),
        expendReport_Fragment()
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_analyze, container, false)
        viewPager = root.findViewById(R.id.reportView_container)
        viewPager.setSaveEnabled(false)
        tabLayout = root.findViewById(R.id.report_tabLayout)
        pagerAdapter = PageAdapter(getChildFragmentManager(),lifecycle,fragments)
        viewPager.adapter = pagerAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.setText(title[position])
        }.attach()

        return root
    }
}