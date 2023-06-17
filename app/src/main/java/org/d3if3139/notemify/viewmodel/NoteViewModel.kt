package org.d3if3139.notemify.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import kotlinx.coroutines.launch
import org.d3if3139.notemify.model.Note
import org.d3if3139.notemify.network.UpdateWorker
import org.d3if3139.notemify.repository.NoteRepository
import java.util.concurrent.TimeUnit

class NoteViewModel(
    app: Application,
    private val noteRepository: NoteRepository
) : AndroidViewModel(app) {

    fun scheduleUpdater(app: Application) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request = PeriodicWorkRequestBuilder<UpdateWorker>(1, TimeUnit.DAYS)
            .setInitialDelay(1, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(app).enqueueUniquePeriodicWork(
            UpdateWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.REPLACE,
            request
        )
    }

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