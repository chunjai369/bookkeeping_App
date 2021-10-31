package de.vogella.android.myapplication
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

open class PageAdapter(fa: FragmentActivity, private var fragments: ArrayList<Fragment>) : FragmentStateAdapter(fa) {

    open var itemcount: Int = fragments.size

    override fun getItemCount(): Int {
        return itemcount
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }


}