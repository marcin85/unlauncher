package com.sduduzog.slimlauncher.utils

import android.view.View
import com.sduduzog.slimlauncher.models.hidden.HiddenApp

interface OnShitDoneToHiddenAppsListener {
    fun onAppsUpdated(list: List<HiddenApp>)
    fun onAppMenuClicked(view: View, app: HiddenApp)
}