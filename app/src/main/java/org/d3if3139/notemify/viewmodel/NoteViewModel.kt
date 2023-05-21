package org.d3if3139.notemify.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.d3if3139.notemify.model.Note
import org.d3if3139.notemify.repository.NoteRepository

class NoteViewModel(
    app: Application,
    private val noteRepository: NoteRepository
) : AndroidViewModel(app) {

    fun addNote(note: Note) =
        viewModelScope.launch {
            noteRepository.insertNote(note)
        }


    fun deleteNote(note: Note) =
        viewModelScope.launch {
            noteRepository.deleteNote(note)
        }


    fun updateNote(note: Note) =
        viewModelScope.launch {
            noteRepository.updateNote(note)
        }


    fun getAllNote() = noteRepository.getAllNotes()

}