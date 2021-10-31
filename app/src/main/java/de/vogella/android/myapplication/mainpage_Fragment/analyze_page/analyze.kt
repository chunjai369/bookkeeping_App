package de.vogella.android.myapplication.mainpage_Fragment.analyze_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import de.vogella.android.myapplication.R

class analyze : Fragment() {

    protected lateinit var bottomNavigation : BottomNavigationView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_analyze, container, false)
        bottomNavigation = root.findViewById(R.id.report_bottomNavigationView)
        showFragment(0)
        bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.income_pie -> {
                    showFragment(0)
                }
                R.id.expend_pie -> {
                    showFragment(1)
                }
            }
            true
        }
        return root
    }

    override fun onResume() {
        super.onResume()
        bottomNavigation.setSelectedItemId(R.id.income_pie)
    }

    private fun showFragment(position : Int){
        val pagerAdapter = analyze_fragmentAdapter()
        val manager = activity?.supportFragmentManager
        val transaction = manager?.beginTransaction()
        transaction?.replace(R.id.report_container , pagerAdapter.createFragment(position))
        transaction?.commit()
    }
}