package edu.umb.cs443termproject.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update

@Dao
interface HistoryDao {

    @Insert(onConflict = REPLACE) // if there is id conflict, it will be just updated
    suspend fun addHistory(history : History)

    @Query("SELECT * FROM history ORDER BY eventDate DESC, id DESC")
    suspend fun getAllHistory(): List<History>

    @Query("DELETE FROM history")
    suspend fun deleteAllHistory()

    @Update
    suspend fun updateHistory(history: History)

    @Delete
    suspend fun deleteHistory(history: History)

}