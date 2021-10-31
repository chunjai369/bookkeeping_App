package de.vogella.android.mainpage_Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import de.vogella.android.myapplication.R
import de.vogella.android.myapplication.app_info_page
import de.vogella.android.myapplication.signin_upActivity
import de.vogella.android.myapplication.userpage


class other : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_other, container, false)
        val logout_btn = root.findViewById<Button>(R.id.LogOut)
        val userdata_btn = root.findViewById<Button>(R.id.userdata)
        val appInfo_btn = root.findViewById<Button>(R.id.info)
        val pref = context?.getSharedPreferences("bookkeeping",0)
        val editor = pref?.edit()

        logout_btn.setOnClickListener {
            if (editor != null) {
                editor.remove("token").commit()
                startActivity(Intent(activity, signin_upActivity::class.java))
                activity?.finish()
            }
        }

        userdata_btn.setOnClickListener {
            startActivity(Intent(activity,userpage::class.java))
        }

        appInfo_btn.setOnClickListener {
            startActivity(Intent(activity,app_info_page::class.java))
        }

        return root
    }

}