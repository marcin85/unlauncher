package com.sduduzog.slimlauncher.models.hidden

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sduduzog.slimlauncher.data.HiddenAppsDao

class CustomiseHiddenAppsViewModel @ViewModelInject constructor(hiddenAppsDao: HiddenAppsDao) : ViewModel() {

    private val _hiddenAppsRepository = HiddenAppsRepository(hiddenAppsDao)
    private var _hiddenApps: LiveData<List<HiddenApp>>

    init {
        _hiddenApps = _hiddenAppsRepository.apps
    }

    val apps: LiveData<List<HiddenApp>>
        get() = _hiddenApps

    fun update(vararg args: HiddenApp) {
        _hiddenAppsRepository.update(*args)
    }

    fun remove(app: HiddenApp) {
        _hiddenAppsRepository.remove(app)
    }

    fun clearTable(){
        _hiddenAppsRepository.clearTable()
    }
}