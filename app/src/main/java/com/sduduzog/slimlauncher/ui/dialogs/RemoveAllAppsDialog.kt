package com.sduduzog.slimlauncher.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.models.home.CustomiseHomeAppsViewModel
import com.sduduzog.slimlauncher.models.home.HomeApp

class RemoveAllAppsDialog : DialogFragment(){

    private lateinit var apps: List<HomeApp>
    private lateinit var modelHome: CustomiseHomeAppsViewModel


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(R.string.remove_all_apps_dialog_title)
        builder.setMessage(R.string.remove_all_apps_dialog_message)
        builder.setPositiveButton("OK") {_, _ ->
            modelHome.clearTable()
        }
        return builder.create()
    }

    companion object{
        fun getInstance(apps: List<HomeApp>, modelHome: CustomiseHomeAppsViewModel): RemoveAllAppsDialog{
            return RemoveAllAppsDialog().apply {
                this.apps = apps
                this.modelHome = modelHome
            }
        }
    }
}