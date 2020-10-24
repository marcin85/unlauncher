package com.sduduzog.slimlauncher.utils

import android.view.View
import com.sduduzog.slimlauncher.models.home.HomeApp

interface OnLaunchAppListener{
    fun onLaunch(app: HomeApp, view: View)
}