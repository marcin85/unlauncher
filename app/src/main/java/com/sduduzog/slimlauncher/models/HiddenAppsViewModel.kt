package com.sduduzog.slimlauncher.models

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sduduzog.slimlauncher.data.BaseDao
import com.sduduzog.slimlauncher.data.HiddenAppsDao

class HiddenAppsViewModel @ViewModelInject constructor(baseDao: HiddenAppsDao) : ViewModel() {

    private val repository = HiddenAppsRepository(baseDao)
    private var _apps: LiveData<List<HiddenApp>>

    init {
        _apps = repository.apps
    }

    val apps: LiveData<List<HiddenApp>>
        get() = _apps

    fun update(vararg args: HiddenApp) {
        repository.update(*args)
    }

    fun remove(app: HiddenApp) {
        repository.remove(app)
    }

    fun clearTable(){
        repository.clearTable()
    }
}