package com.example.testproject.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.testproject.R
import kotlinx.android.synthetic.main.fragment_search_results.view.*

class SearchResultsFragment : Fragment(R.layout.fragment_search_results) {
    var searchResultsCallback: SearchResultsCallback? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.searchBar.requestFocus()

        view.cancelButton.setOnClickListener {
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.apply {
                remove(this@SearchResultsFragment)
                commit()
            }

            searchResultsCallback?.onSearchClose()
        }
    }

    interface SearchResultsCallback {
        fun onSearchClose()
    }
}
