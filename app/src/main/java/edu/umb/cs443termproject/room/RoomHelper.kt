package edu.umb.cs443termproject.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Login::class, Car::class), version = 5, exportSchema = false)
abstract class RoomHelper : RoomDatabase() {

    // DB table list
    abstract fun getLoginDao(): LoginDao
    abstract fun getCarDao(): CarDao

    // Database instance
    companion object {

        @Volatile
        private var INSTANCE : RoomHelper? = null

        fun getDatabase(context: Context) : RoomHelper {
            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomHelper::class.java,
                    "app_database"
                )
                .fallbackToDestructiveMigration() // refer https://developer.android.com/training/data-storage/room/migrating-db-versions
                .build()

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