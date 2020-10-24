package com.sduduzog.slimlauncher.data.model

import com.sduduzog.slimlauncher.models.hidden.HiddenApp
import com.sduduzog.slimlauncher.models.home.HomeApp

data class App(
        val appName: String,
        val packageName: String,
        val activityName: String,
        val userSerial : Long
){
    companion object{
        fun from(homeApp: HomeApp) = App(homeApp.appName, homeApp.packageName, homeApp.activityName, homeApp.userSerial)
        fun from(homeApp: HiddenApp) = App(homeApp.appName, homeApp.packageName, homeApp.activityName, homeApp.userSerial)
    }
}