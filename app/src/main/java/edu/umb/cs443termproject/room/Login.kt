package edu.umb.cs443termproject.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Login(
    var email: String = "",
    var stayLoggedIn: Boolean = false
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}
