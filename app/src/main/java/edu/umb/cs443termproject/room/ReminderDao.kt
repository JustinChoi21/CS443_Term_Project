package edu.umb.cs443termproject.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update

@Dao
interface ReminderDao {

    @Insert(onConflict = REPLACE) // if there is id conflict, it will be just updated
    suspend fun addReminder(reminder: Reminder)

    @Query("SELECT * FROM reminder ORDER BY id DESC")
    suspend fun getAllReminders(): List<Reminder>

    @Query("DELETE FROM reminder")
    suspend fun deleteAllReminders()

    @Update
    suspend fun updateReminder(reminder: Reminder)

    @Delete
    suspend fun deleteReminder(reminder: Reminder)

}