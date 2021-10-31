package de.vogella.android.myapplication.mainpage_Fragment.analyze_page

import androidx.fragment.app.Fragment

class analyze_fragmentAdapter() {

    var fragments: ArrayList<Fragment> = arrayListOf(
        incomeReport_Fragment(),
        expendReport_Fragment()
    )

    fun createFragment(position : Int) : Fragment{
        return fragments[position]
    }
}