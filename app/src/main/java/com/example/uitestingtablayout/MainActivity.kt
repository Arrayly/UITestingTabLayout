package com.example.uitestingtablayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.uitestingtablayout.fragments.FragmentOne
import com.example.uitestingtablayout.fragments.FragmentThree
import com.example.uitestingtablayout.fragments.FragmentTwo
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    lateinit var tabLayout : TabLayout

    val arrayOfIcons = arrayOf(
        R.drawable.ic_copyright, R.drawable.ic_credit_card,
        R.drawable.ic_delete
    )

    val arrayOfText = arrayOf ("Tab 1","Tab 2", "Tab 3")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val viewPager: ViewPager = findViewById(R.id.MainActivity_viewPager)
        tabLayout = findViewById(R.id.MainActivity_tabLayout)


        val adapter = ViewPagerAdapter(supportFragmentManager)
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)


        addIconsToTabLayout()

    }

    private fun addIconsToTabLayout() {
        for (x in 0 ..2){
            tabLayout.getTabAt(x)?.setIcon(arrayOfIcons[x])
        }
    }

    inner class ViewPagerAdapter internal constructor(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        private val COUNT = 3

        override fun getItem(position: Int): Fragment {
            var fragment: Fragment? = null
            when (position) {
                0 -> fragment = FragmentOne()
                1 -> fragment = FragmentTwo()
                2 -> fragment = FragmentThree()
            }

            return fragment!!
        }

        override fun getCount(): Int {
            return COUNT
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return when (position) {
                0 -> arrayOfText[0]
                1 -> arrayOfText[1]
                else -> {
                    return arrayOfText[2]
                }
            }
        }
    }
}
