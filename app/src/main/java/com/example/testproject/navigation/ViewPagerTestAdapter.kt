package com.example.testproject.navigation

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.testproject.account.AccountFragment
import com.example.testproject.home.HomeFragment
import com.example.testproject.search.SearchFragment

class ViewPagerTestAdapter(attachedActivity: FragmentActivity) :
    FragmentStateAdapter(attachedActivity) {
    override fun getItemCount() = 3

    override fun createFragment(position: Int) =
        when (position) {
            0 -> HomeFragment()
            1 -> SearchFragment()
            2 -> AccountFragment()
            else -> HomeFragment()
        }
}