package com.sduduzog.slimlauncher.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.models.hidden.HiddenApp
import com.sduduzog.slimlauncher.utils.OnItemActionListener
import com.sduduzog.slimlauncher.utils.OnShitDoneToHiddenAppsListener

class CustomiseHiddenAppsAdapter(private val listener: OnShitDoneToHiddenAppsListener) : RecyclerView.Adapter<CustomiseHiddenAppsAdapter.ViewHolder>(), OnItemActionListener {

    private var hiddenApps: MutableList<HiddenApp> = mutableListOf()

    override fun getItemCount(): Int = hiddenApps.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.customise_apps_fragment_list_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = hiddenApps[position]
        holder.appName.text = item.appName
        holder.menuIcon.setOnClickListener {
            listener.onAppMenuClicked(it, item)
        }
    }

    fun setItems(apps: List<HiddenApp>) {
        this.hiddenApps = sanitiseIndexes(apps) as MutableList<HiddenApp>
        notifyDataSetChanged()
    }

    private fun sanitiseIndexes(apps: List<HiddenApp>): List<HiddenApp> {
        for (i in apps.indices) {
            apps[i].sortingIndex = i
        }
        return apps
    }


    override fun onViewMoved(oldPosition: Int, newPosition: Int): Boolean {
        return false
    }


    override fun onViewIdle() {
        listener.onAppsUpdated(hiddenApps)
    }

    override fun onViewSwiped(position: Int) {
        // Do nothing. We are under attack!
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val appName: TextView = itemView.findViewById(R.id.ca_list_item_app_name)
        val menuIcon: ImageView = itemView.findViewById(R.id.ca_list_item_more_icon)

        override fun toString(): String {
            return super.toString() + " '${appName.text}'"
        }
    }
}