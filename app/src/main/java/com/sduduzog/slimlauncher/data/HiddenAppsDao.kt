package com.sduduzog.slimlauncher.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sduduzog.slimlauncher.models.hidden.HiddenApp

@Dao
interface HiddenAppsDao {

    @get:Query("SELECT * FROM hidden_apps ORDER BY sorting_index ASC")
    val apps: LiveData<List<HiddenApp>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(app: HiddenApp)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(vararg apps: HiddenApp)

    @Transaction
    fun remove(app: HiddenApp){
        removeFromTable(app)
        updateIndices(app.sortingIndex)
    }

    @Delete
    fun removeFromTable(app: HiddenApp)

    @Query("UPDATE hidden_apps SET sorting_index = sorting_index - 1 WHERE sorting_index > :sortingIndex")
    fun updateIndices(sortingIndex : Int)

    @Query("DELETE FROM hidden_apps")
    fun clearTable()
}