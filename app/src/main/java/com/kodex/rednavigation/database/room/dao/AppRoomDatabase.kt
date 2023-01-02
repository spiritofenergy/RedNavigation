package com.kodex.rednavigation.database.room.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kodex.rednavigation.database.room.repository.NoteRoomDao
import com.kodex.rednavigation.model.Note
import com.kodex.rednavigation.utils.Constants.Keys.NOTES_DATABASE

@Database(entities = [Note::class], version = 1)
abstract class AppRoomDatabase: RoomDatabase(){

    abstract fun getRomDao(): NoteRoomDao

    companion object{

        @Volatile
        private var INSTANCE : AppRoomDatabase? = null
        fun getInstance(context: Context): AppRoomDatabase{
            return if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    AppRoomDatabase::class.java,
                    NOTES_DATABASE
                ).build()
                INSTANCE as AppRoomDatabase
            }else INSTANCE as AppRoomDatabase
        }
    }
}