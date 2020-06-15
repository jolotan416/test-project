package com.example.testproject.search

import android.content.Intent
import android.content.pm.ResolveInfo
import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testproject.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_share_dialog.view.*

class ShareDialogFragment : BottomSheetDialogFragment(), AppAdapter.AppClickListener {
    companion object {
        const val TAG = "ShareDialogFragment"
        const val RESOLVE_INFO_DATA = "resolve_info_data"
        const val SEND_INTENT_DATA = "send_intent_data"
    }

    private var sendIntent: Intent? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_share_dialog, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        @Suppress("UNCHECKED_CAST")
        val resolveInfo =
            requireArguments().getParcelableArray(RESOLVE_INFO_DATA)!!.toList() as List<ResolveInfo>
        sendIntent = requireArguments().getParcelable(SEND_INTENT_DATA)
        Log.d("JOLOG", "[JOLOG] $resolveInfo")
        view.appRecyclerView.apply {
            adapter = AppAdapter(resolveInfo, this@ShareDialogFragment)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    super.getItemOffsets(outRect, view, parent, state)

                    outRect.apply {
                        right = (Resources.getSystem().displayMetrics.density * 12).toInt()
                        bottom = (Resources.getSystem().displayMetrics.density * 12).toInt()
                    }
                }
            })
            setHasFixedSize(true)
        }
    }

    override fun redirectToApp(resolveInfo: ResolveInfo) {
        sendIntent?.apply {
            setClassName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name)
            startActivity(this)
        }
    }
}
