package edu.umb.cs443termproject.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cars")
class Car {
    @PrimaryKey
    @ColumnInfo
    var id: Int = 0

    @ColumnInfo
    var icon: Int = 0

    @ColumnInfo
    var manufacturer: String = ""

    @ColumnInfo
    var model: String = ""

    @ColumnInfo
    var selectedDate: String = ""

    constructor(icon: Int, manufacturer: String, model: String, selectedDate: String) {
        this.icon = icon
        this.manufacturer = manufacturer
        this.model = model
        this.selectedDate = selectedDate
    }
}
