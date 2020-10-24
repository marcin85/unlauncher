package com.sduduzog.slimlauncher.models.hidden


import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.sduduzog.slimlauncher.data.HiddenAppsDao


class HiddenAppsRepository(private val baseDao: HiddenAppsDao) {

    private val _apps = baseDao.apps

    val apps: LiveData<List<HiddenApp>>
        get() = _apps

    fun add(app: HiddenApp) {
        AddAppAsyncTask(baseDao).execute(app)
    }

    fun update(vararg list : HiddenApp) {
        UpdateAppAsyncTask(baseDao).execute(*list)
    }

    fun remove(app: HiddenApp) {
        RemoveAppAsyncTask(baseDao).execute(app)
    }

    fun clearTable(){
        ClearTableAsyncTask(baseDao).execute()
    }

    private class AddAppAsyncTask(private val mAsyncTaskDao: HiddenAppsDao) : AsyncTask<HiddenApp, Void, Void>() {

        override fun doInBackground(vararg params: HiddenApp): Void? {
            mAsyncTaskDao.add(params[0])
            return null
        }
    }

    private class UpdateAppAsyncTask(private val mAsyncTaskDao: HiddenAppsDao) : AsyncTask<HiddenApp, Void, Void>() {

        override fun doInBackground(vararg params: HiddenApp): Void? {
            mAsyncTaskDao.update(*params)
            return null
        }
    }

    private class RemoveAppAsyncTask(private val mAsyncTaskDao: HiddenAppsDao) : AsyncTask<HiddenApp, Void, Void>() {

        override fun doInBackground(vararg params: HiddenApp): Void? {
            mAsyncTaskDao.remove(params[0])
            return null
        }
    }

    private class ClearTableAsyncTask(private val mAsyncTaskDao: HiddenAppsDao) : AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg params: Void): Void? {
            mAsyncTaskDao.clearTable()
            return null
        }
    }
}
