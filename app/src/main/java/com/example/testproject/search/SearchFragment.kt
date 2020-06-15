package com.example.testproject.search

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.testproject.R
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment(R.layout.fragment_search) {
    companion object {
        private val ACCEPTED_APP_PACKAGES = listOf(
            "facebook", "twitter", "instagram"
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        shareButton.setOnClickListener { openShareSheet() }
        shareToSocialMediaButton.setOnClickListener { openShareToSocialMediaSheet() }
    }

    private fun openShareSheet() {
        val shareIntent = Intent.createChooser(createSendIntent(), null)
        startActivity(shareIntent)
    }

    private fun openShareToSocialMediaSheet() {
        val sendIntent = createSendIntent()
        val resolveInfo = activity?.packageManager?.queryIntentActivities(sendIntent, 0)
            ?.filter { info ->
                ACCEPTED_APP_PACKAGES.any { info.activityInfo.packageName.contains(it) }
            }
        ShareDialogFragment().apply {
            arguments = Bundle().apply {
                putParcelableArray(
                    ShareDialogFragment.RESOLVE_INFO_DATA,
                    resolveInfo!!.toTypedArray()
                )
                putParcelable(ShareDialogFragment.SEND_INTENT_DATA, sendIntent)
            }
            show(this@SearchFragment.childFragmentManager, ShareDialogFragment.TAG)
        }
    }

    private fun createSendIntent() =
        Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(
                Intent.EXTRA_TEXT,
                "Hello friend, check out my LinkedIn Profile over here: https://linkedin.com/in/john-louise-tan-b76704123"
            )
            putExtra(Intent.EXTRA_TITLE, "LinkedIn Profile")
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        }
}
