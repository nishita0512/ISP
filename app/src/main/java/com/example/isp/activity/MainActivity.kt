package com.example.isp.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
import com.example.isp.*
import com.example.isp.adapters.ViewPagerAdapter
import com.example.isp.databinding.ActivityMainBinding
import com.example.isp.fragment.MapFragment
import com.example.isp.fragment.OverviewFragment
import com.example.isp.fragment.SupportFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= 33) {
            onBackInvokedDispatcher.registerOnBackInvokedCallback(OnBackInvokedDispatcher.PRIORITY_DEFAULT) {
                finishAffinity()
            }
        } else {
            onBackPressedDispatcher.addCallback(this@MainActivity, object: OnBackPressedCallback(true){
                override fun handleOnBackPressed() {
                    finishAffinity()
                }
            })
        }

        val fragmentList = arrayListOf(
            OverviewFragment(),
            MapFragment(),
            SupportFragment()
        )

        val viewPagerAdapter = ViewPagerAdapter(fragmentList,this.supportFragmentManager,lifecycle)

        binding.apply{

            viewPagerMainActivity.isUserInputEnabled = false

            bottomMenu.setItemSelected(R.id.overview)
            viewPagerMainActivity.adapter = viewPagerAdapter
            bottomMenu.setOnItemSelectedListener {
                when(it){
                    R.id.overview ->{
                        viewPagerMainActivity.currentItem = 0
                    }
                    R.id.connectionMap ->{
                        viewPagerMainActivity.currentItem = 1
                    }
                    R.id.support ->{
                        viewPagerMainActivity.currentItem = 2
                    }
                }
            }

        }

    }

}
