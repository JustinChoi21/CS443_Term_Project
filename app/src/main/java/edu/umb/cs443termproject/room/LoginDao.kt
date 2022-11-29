package edu.umb.cs443termproject.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update

@Dao
interface LoginDao {

    @Insert(onConflict = REPLACE) // if there is id conflict, it will be just updated
    suspend fun addLogin(login: Login)

    @Query("SELECT * FROM login ORDER BY id DESC")
    suspend fun getAllLogin(): List<Login>

    @Update
    suspend fun updateLogin(login: Login)

    @Delete
    suspend fun deleteLogin(login: Login)

}