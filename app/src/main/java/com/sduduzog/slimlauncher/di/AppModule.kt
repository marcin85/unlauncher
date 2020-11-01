package com.sduduzog.slimlauncher.di

import android.app.Application
import androidx.room.Room
import com.sduduzog.slimlauncher.data.HomeAppsDao
import com.sduduzog.slimlauncher.data.BaseDatabase
import com.sduduzog.slimlauncher.data.HiddenAppsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {
    @Provides
    @Singleton
    internal fun provideBaseDatabase(application: Application): BaseDatabase {
        return Room.databaseBuilder(application,
                BaseDatabase::class.java, "app_database")
                .addMigrations(
                        BaseDatabase.UPDATE_HOME_APPS_SORTING_INDEX,
                        BaseDatabase.UPDATE_HIDDEN_APPS_SORTING_INDEX,
                        BaseDatabase.ADD_HOME_APPS_APP_NICKNAME,
                        BaseDatabase.UPDATE_HOME_APPS_TABLE,
                        BaseDatabase.UPDATE_HIDDEN_APPS_TABLE
                )
                .build()
    }

    @Provides
    @Singleton
    internal fun provideHomeAppsDao(baseDatabase: BaseDatabase): HomeAppsDao {
        return baseDatabase.homeAppsDao()
    }

    @Provides
    @Singleton
    internal fun provideHiddenAppsDao(baseDatabase: BaseDatabase): HiddenAppsDao {
        return baseDatabase.hidenAppsDao()
    }

}