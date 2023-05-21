package org.d3if3139.notemify.db

import androidx.lifecycle.LiveData
import androidx.room.*
import org.d3if3139.notemify.model.Note

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM note_database ORDER BY id DESC")
    fun getAllNotes(): LiveData<List<Note>>
}