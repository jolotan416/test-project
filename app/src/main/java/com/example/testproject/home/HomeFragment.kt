package com.example.testproject.home

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.testproject.R
import com.example.testproject.utilities.PagingEnabler
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment(R.layout.fragment_home),
    OnboardingNudgeFragment.OnboardingNudgeListener {
    private var pagingEnabler: PagingEnabler? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is PagingEnabler) {
            pagingEnabler = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.homeButton.setOnClickListener {
            Snackbar.make(homeLayout, "Hello, home!", Snackbar.LENGTH_LONG).show()
        }
        showOnboardingNudge()
    }

    override fun onSlideUp() {
        val onboardingNudgeFragment =
            childFragmentManager.findFragmentByTag(OnboardingNudgeFragment.TAG) ?: return
        val fragmentTransaction = childFragmentManager.beginTransaction()
        fragmentTransaction.apply {
            remove(onboardingNudgeFragment)
            commit()
        }

        hideOnboardingNudge()
    }

    private fun showOnboardingNudge() {
        val onboardingNudgeFragment = OnboardingNudgeFragment().apply {
            onboardingNudgeListener = this@HomeFragment
        }
        val fragmentTransaction = childFragmentManager.beginTransaction()
        fragmentTransaction.apply {
            replace(R.id.fragmentContainer, onboardingNudgeFragment, OnboardingNudgeFragment.TAG)
            commit()
        }
        pagingEnabler?.enablePaging(false)
    }

    private fun hideOnboardingNudge() {
        pagingEnabler?.enablePaging(true)
    }
}
