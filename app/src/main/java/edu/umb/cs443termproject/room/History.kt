package edu.umb.cs443termproject.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
class History {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    var id: Int = 0

    @ColumnInfo
    var eventType: String = ""

    @ColumnInfo
    var eventDate: String = ""

    @ColumnInfo
    var eventDescription: String = ""

    @ColumnInfo
    var fuelAmount: Int = 0

    @ColumnInfo
    var fuelPrice: Int = 0


    constructor(eventType: String, eventDate: String, eventDescription: String, fuelAmount: Int, fuelPrice: Int) {
        this.eventType = eventType
        this.eventDate = eventDate
        this.eventDescription = eventDescription
        this.fuelAmount = fuelAmount
        this.fuelPrice = fuelPrice
    }
}
