package edu.umb.cs443termproject.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cars")
class Car {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    var id: Int = 0

    @ColumnInfo
    var manufacturer: String = ""

    @ColumnInfo
    var model: String = ""

    constructor(manufacturer: String, model: String) {
        this.manufacturer = manufacturer
        this.model = model
    }

}
