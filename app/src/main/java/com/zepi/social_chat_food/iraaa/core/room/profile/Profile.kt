package com.zepi.social_chat_food.iraaa.core.room.profile

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile_table")
data class Profile(
    @ColumnInfo(name = "namedb")
    var namedb: String = "Name",
    @ColumnInfo(name = "surnamedb")
    var surnamedb: String = "Surname",
    @ColumnInfo(name = "genderdb")
    var genderdb: String = "Gender",
    @ColumnInfo(name = "birthdaydb")
    var birthdaydb: String = "Birthday",
    @ColumnInfo(name = "photodb")
    var photodb: String = "Photo",
    var description: String = "description"
) {
    @PrimaryKey()
    var id: Int = 0
}
