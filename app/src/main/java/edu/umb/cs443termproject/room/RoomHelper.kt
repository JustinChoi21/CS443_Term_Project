package edu.umb.cs443termproject.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Login::class), version = 1, exportSchema = false)
abstract class RoomHelper : RoomDatabase() {
    abstract fun getLoginDao(): LoginDao

    companion object {

        @Volatile
        private var INSTANCE : RoomHelper? = null

        fun getDatabase(context: Context) : RoomHelper {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomHelper::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }


//        private val LOCK = Any()
//
//        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
//            instance ?: buildDatabase(context).also {
//                instance = it
//            }
//        }
//
//        private fun buildDatabase(context: Context) = Room.databaseBuilder(
//            context.applicationContext,
//            AppDatabase::class.java,
//            "app-database"
//        ).build()
    }
}