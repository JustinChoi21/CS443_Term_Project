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

    constructor(eventType: String, eventDate: String, eventDescription: String) {
        this.eventType = eventType
        this.eventDate = eventDate
        this.eventDescription = eventDescription
    }
}
