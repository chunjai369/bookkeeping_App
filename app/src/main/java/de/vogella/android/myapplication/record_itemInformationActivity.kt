package de.vogella.android.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import de.vogella.android.myapplication.mainpage_Fragment.record_page.item_Info_Fragment

class record_itemInformationActivity : AppCompatActivity() {
    private  lateinit  var manager: FragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_item_information)
        val intent = getIntent()
        val date = intent.getStringExtra("date")
        val position = intent.getStringExtra("position")
        val bundle = Bundle()
        bundle.putString("date",date)
        bundle.putString("position",position)
        val fragment = item_Info_Fragment()
        fragment.arguments = bundle
        showFragment(fragment)
    }

    private fun showFragment(fragments : Fragment ){
        manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.item_Info_container,fragments,"firstPage")
        transaction.commit()
    }

    override fun onBackPressed() {
        val fragment =
            this.supportFragmentManager.findFragmentById(R.id.item_Info_container)
        (fragment as? IOnBackPressed)?.onBackPressed()?.let {
            super.onBackPressed()
        }
    }

}

interface IOnBackPressed {
    fun onBackPressed()
}