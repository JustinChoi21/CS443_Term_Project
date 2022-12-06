package edu.umb.cs443termproject.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "car")
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

    @ColumnInfo
    var engineOil: Int = 0

    @ColumnInfo
    var fuelTank: Int = 0

    @ColumnInfo
    var tire: Int = 0

    @ColumnInfo
    var regularService: Int = 0

    @ColumnInfo
    var horsePower: Int = 0


    constructor(icon: Int, manufacturer: String, model: String, selectedDate: String,
                engineOil: Int, fuelTank: Int, tire: Int, regularService: Int, horsePower: Int) {
        this.icon = icon
        this.manufacturer = manufacturer
        this.model = model
        this.selectedDate = selectedDate
        this.engineOil = engineOil
        this.fuelTank = fuelTank
        this.tire = tire
        this.regularService = regularService
        this.horsePower = horsePower
    }
}
