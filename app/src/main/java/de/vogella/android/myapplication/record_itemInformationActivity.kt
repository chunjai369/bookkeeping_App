package de.vogella.android.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

class record_itemInformationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_item_information)
        val intent = getIntent()
        val date = intent.getStringExtra("date")
        val dateTextView = findViewById(R.id.item_info_date) as TextView
        dateTextView.setText(date)
    }
}