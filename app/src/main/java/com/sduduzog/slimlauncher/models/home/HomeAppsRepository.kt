package com.sduduzog.slimlauncher.models.home

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.sduduzog.slimlauncher.data.HomeAppsDao

class HomeAppsRepository(private val homeAppsDao: HomeAppsDao) {

    private val _homeApps = homeAppsDao.apps

    val homeApps: LiveData<List<HomeApp>>
        get() = _homeApps

    fun add(app: HomeApp) {
        AddAppAsyncTask(homeAppsDao).execute(app)
    }

    fun update(vararg list : HomeApp) {
        UpdateAppAsyncTask(homeAppsDao).execute(*list)
    }

    fun remove(app: HomeApp) {
        RemoveAppAsyncTask(homeAppsDao).execute(app)
    }

    fun clearTable(){
        ClearTableAsyncTask(homeAppsDao).execute()
    }

    private class AddAppAsyncTask(private val mAsyncTaskDao: HomeAppsDao) : AsyncTask<HomeApp, Void, Void>() {

        override fun doInBackground(vararg params: HomeApp): Void? {
            mAsyncTaskDao.add(params[0])
            return null
        }
    }

    private class UpdateAppAsyncTask(private val mAsyncTaskDao: HomeAppsDao) : AsyncTask<HomeApp, Void, Void>() {

        override fun doInBackground(vararg params: HomeApp): Void? {
            mAsyncTaskDao.update(*params)
            return null
        }
    }

    private class RemoveAppAsyncTask(private val mAsyncTaskDao: HomeAppsDao) : AsyncTask<HomeApp, Void, Void>() {

        override fun doInBackground(vararg params: HomeApp): Void? {
            mAsyncTaskDao.remove(params[0])
            return null
        }
    }

    private class ClearTableAsyncTask(private val mAsyncTaskDao: HomeAppsDao) : AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg params: Void): Void? {
            mAsyncTaskDao.clearTable()
            return null
        }
    }
}
