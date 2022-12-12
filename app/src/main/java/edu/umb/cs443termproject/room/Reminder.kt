package edu.umb.cs443termproject.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reminder")
class Reminder {
    @PrimaryKey
    @ColumnInfo
    var id: Int = 0

    @ColumnInfo
    var refuel: Int = 0

    @ColumnInfo
    var engineOil: Int = 0

    @ColumnInfo
    var tire: Int = 0

    @ColumnInfo
    var regularService: Int = 0


    constructor(refuel: Int, engineOil: Int, tire: Int, regularService: Int) {
        this.refuel = refuel
        this.engineOil = engineOil
        this.tire = tire
        this.regularService = regularService
    }
}
