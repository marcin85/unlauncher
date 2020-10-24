package com.sduduzog.slimlauncher.models.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sduduzog.slimlauncher.data.HomeAppsDao

class CustomiseHomeAppsViewModel @ViewModelInject constructor(homeAppsDao: HomeAppsDao) : ViewModel() {

    private val _homeAppsRepository = HomeAppsRepository(homeAppsDao)
    private var _homeApps: LiveData<List<HomeApp>>

    init {
        _homeApps = _homeAppsRepository.homeApps
    }

    val apps: LiveData<List<HomeApp>>
        get() = _homeApps

    fun update(vararg args: HomeApp) {
        _homeAppsRepository.update(*args)
    }

    fun reset(homeApp: HomeApp) {
        homeApp.appNickname = null
        update(homeApp)
    }
    fun remove(app: HomeApp) {
        _homeAppsRepository.remove(app)
    }

    fun clearTable(){
        _homeAppsRepository.clearTable()
    }
}