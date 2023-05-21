package org.d3if3139.notemify.repository

import org.d3if3139.notemify.db.NoteDatabase
import org.d3if3139.notemify.model.Note

class NoteRepository(
    private val db: NoteDatabase
) {

    suspend fun insertNote(note: Note) = db.noteDao().insertNote(note)

    suspend fun deleteNote(note: Note) = db.noteDao().deleteNote(note)

    suspend fun updateNote(note: Note) = db.noteDao().updateNote(note)

    fun getAllNotes() = db.noteDao().getAllNotes()

}