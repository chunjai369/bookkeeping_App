package de.vogella.android.mainpage_Fragment
import androidx.fragment.app.Fragment
import de.vogella.android.myapplication.mainpage_Fragment.analyze_page.analyze
import de.vogella.android.myapplication.mainpage_Fragment.insert_page.insert
import de.vogella.android.myapplication.mainpage_Fragment.record_page.record

class mainpage_PageAdapter () {

    var fragments: ArrayList<Fragment> = arrayListOf(
        record(),
        insert(),
        analyze(),
        other()
    )

    fun createFragment(position : Int) : Fragment{
        return fragments[position]
    }

}