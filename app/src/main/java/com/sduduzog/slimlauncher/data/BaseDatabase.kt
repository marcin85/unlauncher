package com.sduduzog.slimlauncher.data

import android.os.Process
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.sduduzog.slimlauncher.models.hidden.HiddenApp
import com.sduduzog.slimlauncher.models.home.HomeApp


@Database(entities = [HomeApp::class, HiddenApp::class], version = 8, exportSchema = false)
abstract class BaseDatabase : RoomDatabase() {

    abstract fun homeAppsDao(): HomeAppsDao
    abstract fun hidenAppsDao(): HiddenAppsDao

    companion object {
         val UPDATE_HOME_APPS_SORTING_INDEX = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE `home_apps` ADD COLUMN `sorting_index` INTEGER NOT NULL DEFAULT 0")
                val cursor = database.query("SELECT package_name FROM home_apps")
                cursor.moveToFirst()
                var index = 0
                while (!cursor.isAfterLast) {
                    val column = cursor.getString(cursor.getColumnIndex("package_name"))
                    database.execSQL("UPDATE `home_apps` SET `sorting_index`=$index WHERE `package_name`='$column'")
                    cursor.moveToNext()
                    index++
                }
            }
        }

        val UPDATE_HIDDEN_APPS_SORTING_INDEX = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE `hidden_apps` ADD COLUMN `sorting_index` INTEGER NOT NULL DEFAULT 0")
                val cursor = database.query("SELECT package_name FROM hidden_apps")
                cursor.moveToFirst()
                var index = 0
                while (!cursor.isAfterLast) {
                    val column = cursor.getString(cursor.getColumnIndex("package_name"))
                    database.execSQL("UPDATE `hidden_apps` SET `sorting_index`=$index WHERE `package_name`='$column'")
                    cursor.moveToNext()
                    index++
                }

            }
        }

         val ADD_HOME_APPS_APP_NICKNAME = object : Migration(3, 4){
            override fun migrate(database: SupportSQLiteDatabase) {
               database.execSQL("ALTER TABLE `home_apps` ADD COLUMN `app_nickname` TEXT")
            }
        }

        val UPDATE_HOME_APPS_TABLE = object : Migration(5, 6){
            override fun migrate(database: SupportSQLiteDatabase) {
                val userSerial = Process.myUserHandle().hashCode()
                database.execSQL("ALTER TABLE `home_apps` ADD COLUMN `user_serial` INTEGER NOT NULL DEFAULT " + userSerial.toString())

                database.execSQL("CREATE TABLE home_apps_copy(" +
                        "package_name TEXT NOT NULL, " +
                        "user_serial INTEGER NOT NULL, " +
                        "app_name TEXT NOT NULL, " +
                        "app_nickname TEXT, " +
                        "activity_name TEXT NOT NULL, " +
                        "sorting_index INTEGER NOT NULL, " +
                        "PRIMARY KEY(package_name, user_serial))"
                )
                database.execSQL("INSERT INTO home_apps_copy (package_name, user_serial, app_name, app_nickname, activity_name, sorting_index) " +
                        "SELECT package_name, user_serial, app_name, app_nickname, activity_name, sorting_index FROM home_apps")
                database.execSQL("DROP TABLE home_apps")
                database.execSQL("ALTER TABLE home_apps_copy RENAME TO home_apps")
            }
        }

        val UPDATE_HIDDEN_APPS_TABLE = object : Migration(7, 8){
            override fun migrate(database: SupportSQLiteDatabase) {
                val userSerial = Process.myUserHandle().hashCode()
                database.execSQL("ALTER TABLE `hidden_apps` ADD COLUMN `user_serial` INTEGER NOT NULL DEFAULT " + userSerial.toString())

                database.execSQL("CREATE TABLE hidden_apps_copy(" +
                        "package_name TEXT NOT NULL, " +
                        "user_serial INTEGER NOT NULL, " +
                        "app_name TEXT NOT NULL, " +
                        "activity_name TEXT NOT NULL, " +
                        "sorting_index INTEGER NOT NULL, " +
                        "PRIMARY KEY(package_name, user_serial))"
                )
                database.execSQL("INSERT INTO hidden_apps_copy (package_name, user_serial, app_name, activity_name, sorting_index) " +
                        "SELECT package_name, user_serial, app_name, activity_name, sorting_index FROM hidden_apps")
                database.execSQL("DROP TABLE hidden_apps")
                database.execSQL("ALTER TABLE hidden_apps_copy RENAME TO hidden_apps")
            }
        }
    }
}
