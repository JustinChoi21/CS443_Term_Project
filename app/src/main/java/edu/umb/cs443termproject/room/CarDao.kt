package edu.umb.cs443termproject.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update

@Dao
interface CarDao {

    @Insert(onConflict = REPLACE) // if there is id conflict, it will be just updated
    suspend fun addCar(car: Car)

    @Query("SELECT * FROM car ORDER BY id DESC")
    suspend fun getAllCar(): List<Car>

    @Query("DELETE FROM car")
    suspend fun deleteAllCar()

    @Update
    suspend fun updateCar(car: Car)

    @Delete
    suspend fun deleteCar(car: Car)

}