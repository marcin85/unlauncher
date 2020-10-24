package com.sduduzog.slimlauncher.models.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sduduzog.slimlauncher.data.HomeAppsDao
import com.sduduzog.slimlauncher.data.model.App
import javax.inject.Inject

class HomeAppsViewModel @Inject constructor(homeAppsDao: HomeAppsDao) : ViewModel() {

    private val _homeAppsRepository = HomeAppsRepository(homeAppsDao)
    private var _homeApps: LiveData<List<HomeApp>>

    init {
        _homeApps = _homeAppsRepository.homeApps
    }

    val apps: LiveData<List<HomeApp>>
        get() = _homeApps

    fun add(app: App) {
        val index = _homeApps.value!!.size
        _homeAppsRepository.add(HomeApp.from(app, index))
    }
}