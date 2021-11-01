package de.vogella.android.myapplication
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import de.vogella.android.myapplication.mainpage_Fragment.insert_page.insert_expend_Fragment
import de.vogella.android.myapplication.mainpage_Fragment.insert_page.insert_income_Fragment

open class PageAdapter(fa: FragmentManager, lifecycle: Lifecycle, private var fragments: ArrayList<Fragment>)
    : FragmentStateAdapter(fa, lifecycle) {

        open var itemcount: Int = fragments.size

        override fun getItemCount(): Int {
            return itemcount
        }

        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }

}