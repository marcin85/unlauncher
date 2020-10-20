package com.sduduzog.slimlauncher.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.models.HiddenApp
import com.sduduzog.slimlauncher.models.HiddenAppsViewModel

class RemoveAllHiddenAppsDialog : DialogFragment(){

    private lateinit var apps: List<HiddenApp>
    private lateinit var model: HiddenAppsViewModel


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(R.string.remove_all_apps_dialog_title)
        builder.setMessage(R.string.remove_all_apps_dialog_message)
        builder.setPositiveButton("OK") {_, _ ->
            model.clearTable()
        }
        return builder.create()
    }

    companion object{
        fun getInstance(apps: List<HiddenApp>, model: HiddenAppsViewModel): RemoveAllHiddenAppsDialog{
            return RemoveAllHiddenAppsDialog().apply {
                this.apps = apps
                this.model = model
            }
        }
    }
}