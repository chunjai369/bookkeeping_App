package de.vogella.android.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import de.vogella.android.myapplication.mainpage_Fragment.record_page.item_Info_Fragment

class record_itemInformationActivity : AppCompatActivity() {
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
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.item_Info_container,fragments,"firstPage")
        transaction.commit()
    }

}