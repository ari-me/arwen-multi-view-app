package com.example.app_multiview.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Room
import android.content.Context
import com.example.app_multiview.model.Note

@Database(entities = [Note::class], version = 1)
//ex
abstract class NoteDatabase: RoomDatabase() {

    abstract fun getNotesDao(): NoteData

    companion object{
        @Volatile
        private var instance: NoteDatabase? = null
        private val LOCK = Any() //ensures that only one creation can happen at a time

        operator fun invoke(context: Context) = instance ?:
        synchronized(LOCK){
            instance ?:
            createDatabase(context).also{
                instance = it
            }
        }

        //creates the note database using databaseBuilder method
        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                NoteDatabase::class.java,
                "note_db"
            ).build()
    }
}