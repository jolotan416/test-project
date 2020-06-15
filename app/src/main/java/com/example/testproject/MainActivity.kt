package com.example.testproject

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.testproject.home.SearchResultsFragment
import com.example.testproject.navigation.ViewPagerTestAdapter
import com.example.testproject.utilities.PagingEnabler
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(R.layout.activity_main), PagingEnabler,
    SearchResultsFragment.SearchResultsCallback {
    private lateinit var tabLayoutMediator: TabLayoutMediator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViewPager()
        setupSearchBar()
    }

    override fun enablePaging(willEnablePaging: Boolean) {
        viewPager.isUserInputEnabled = willEnablePaging
    }

    override fun onSearchClose() {
        searchBar.clearFocus()
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    private fun setupViewPager() {
        viewPager.adapter = ViewPagerTestAdapter(this)

        val mainTabTitles = resources.getStringArray(R.array.main_tabs)
        tabLayoutMediator = TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = mainTabTitles[position]
        }
        tabLayoutMediator.attach()
    }

    private fun setupSearchBar() {
        searchBar.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                val searchResultsFragment = SearchResultsFragment()
                searchResultsFragment.searchResultsCallback = this@MainActivity

                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.apply {
                    setCustomAnimations(R.anim.slide_up, R.anim.slide_up)
                    replace(R.id.mainFragmentContainer, searchResultsFragment)
                    commit()
                }
            }
        }
    }
}
