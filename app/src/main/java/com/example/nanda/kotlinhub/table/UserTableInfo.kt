package com.example.nanda.kotlinhub.table

import android.provider.BaseColumns

class UserTableInfo : BaseColumns {
    companion object {
        val TABLE_NAME = "user"
        val COLUMN_USERNAME = "username"
        val COLUMN_PASSWORD = "password"
        val COLUMN_EMAIL = "email"
        val COLUMN_POINTS = "points"
        val COLUMN_ISLOGGEDIN = "isLoggedIn"
        val COLUMN_PROGRESS = "progress"
        // val COLUMN_PROGRESS is a number which is the id of the topic.
    }
}