package de.vogella.android.myapplication
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import de.vogella.android.signin_upFragment.signinFragment
import de.vogella.android.signin_upFragment.signupFragment

class signin_upActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private val title: ArrayList<String> = arrayListOf("SIGN IN", "SIGN UP")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signin_up)
        viewPager = findViewById(R.id.signin_up_container)
        tabLayout = findViewById(R.id.tabLayout)
        val fragments: ArrayList<Fragment> = arrayListOf(
        signinFragment(),
        signupFragment())
        val pagerAdapter = PageAdapter(this,fragments)
        viewPager.adapter = pagerAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = title[position]
        }.attach()
    }
}