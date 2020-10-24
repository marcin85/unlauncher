package com.sduduzog.slimlauncher.models

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.sduduzog.slimlauncher.data.HomeAppsDao
import com.sduduzog.slimlauncher.data.HiddenAppsDao
import com.sduduzog.slimlauncher.data.model.App
import com.sduduzog.slimlauncher.models.hidden.HiddenApp
import com.sduduzog.slimlauncher.models.hidden.HiddenAppsRepository
import com.sduduzog.slimlauncher.models.home.HomeApp
import com.sduduzog.slimlauncher.models.home.HomeAppsRepository

class AddAppViewModel @ViewModelInject constructor (homeAppsDao: HomeAppsDao, hiddenApp: HiddenAppsDao) : ViewModel() {
    private val repository = HomeAppsRepository(homeAppsDao)
    private val hiddenAppsRepository = HiddenAppsRepository(hiddenApp)
    private var filterQuery = ""
    private val regex = Regex("[!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/? ]")
    private val _installedApps = mutableListOf<App>()
    private val _homeApps = mutableListOf<App>()
    private val _hiddenApps = mutableListOf<App>()

    private val homeAppsObserver = Observer<List<HomeApp>> {
        this._homeApps.clear()
        it.orEmpty().forEach { item -> this._homeApps.add(App.from(item)) }
        if (it !== null) updateDisplayedApps()
    }

    private val hiddenAppsObserver = Observer<List<HiddenApp>> {
        this._hiddenApps.clear()
        it.orEmpty().forEach { item -> this._hiddenApps.add(App.from(item)) }
        if (it !== null) updateDisplayedApps()
    }
    val apps = MutableLiveData<List<App>>()

    init {
        repository.homeApps.observeForever(homeAppsObserver)
        hiddenAppsRepository.apps.observeForever(hiddenAppsObserver)
    }

    fun filterApps(query: String = "") {
        this.filterQuery = regex.replace(query, "")
        this.updateDisplayedApps()
    }

    private fun updateDisplayedApps() {
        val filteredApps = _installedApps.filterNot { _homeApps.contains(it) }.filterNot { _hiddenApps.contains(it) }
        this.apps.postValue(filteredApps.filter { regex.replace(it.appName, "").contains(filterQuery, ignoreCase = true) })
    }

    fun setInstalledApps(apps: List<App>) {
        this.filterQuery = ""
        this._installedApps.clear()
        this._installedApps.addAll(apps)
    }

    fun addAppToHomeScreen(app: App) {
        val index = _homeApps.size
        repository.add(HomeApp.from(app, index))
    }

    fun addAppToHiddenApps(app: App) {
        val index = _hiddenApps.size
        hiddenAppsRepository.add(HiddenApp.from(app, index))
    }

    override fun onCleared() {
        super.onCleared()
        repository.homeApps.removeObserver(homeAppsObserver)
        hiddenAppsRepository.apps.removeObserver(hiddenAppsObserver)
    }
}