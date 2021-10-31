package de.vogella.android.myapplication
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView

import de.vogella.android.mainpage_Fragment.mainpage_PageAdapter


class mainpage : AppCompatActivity() {
    private val pagerAdapter = mainpage_PageAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mainpage_activity)
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        showFragment(0)

        bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.record -> {
                    R.id.record
                    showFragment(0)
                }
                R.id.insert -> {
                    showFragment(1)
                }
                R.id.analyze -> {
                    showFragment(2)
                }
                R.id.other -> {
                    showFragment(3)
                }
            }
            true
        }
    }

    private fun showFragment(position : Int){
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.mainpage_container , pagerAdapter.createFragment(position))
        transaction.commit()
    }
}