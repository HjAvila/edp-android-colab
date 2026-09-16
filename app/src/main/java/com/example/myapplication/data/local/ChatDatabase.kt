package com.example.myapplication.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MessageEntity::class], version = 1, exportSchema = false)
abstract class ChatDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao

    companion object {
        @Volatile
        private var Instance: ChatDatabase? = null

        fun getDatabase(context: Context): ChatDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, ChatDatabase::class.java, "chat_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
