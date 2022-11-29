package edu.umb.cs443termproject.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "login")
class Login {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    var id: Int = 0

    @ColumnInfo
    var email: String = ""

    @ColumnInfo
    var stayLoggedIn: Boolean = false

    constructor(email: String, stayLoggedIn: Boolean) {
        this.email = email
        this.stayLoggedIn = stayLoggedIn
    }

}
