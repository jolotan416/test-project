package com.example.testproject.search

import android.content.pm.ResolveInfo
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testproject.R
import kotlinx.android.synthetic.main.app_item.view.*

class AppAdapter(
    private val resolveInfo: List<ResolveInfo>,
    private val appClickListener: AppClickListener
) :
    RecyclerView.Adapter<AppAdapter.AppViewHolder>() {
    override fun getItemCount() = resolveInfo.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        AppViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.app_item, parent, false))

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        holder.bindData(resolveInfo[position])
    }

    inner class AppViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val appLogo = itemView.appLogo
        private val appNameTextView = itemView.appNameTextView
        private val context = itemView.context

        fun bindData(resolveInfo: ResolveInfo) {
            appLogo.setImageDrawable(resolveInfo.loadIcon(context.packageManager))
            appNameTextView.text = resolveInfo.loadLabel(context.packageManager)

            itemView.setOnClickListener {
                appClickListener.redirectToApp(resolveInfo)
            }
        }
    }

    interface AppClickListener {
        fun redirectToApp(resolveInfo: ResolveInfo)
    }
}