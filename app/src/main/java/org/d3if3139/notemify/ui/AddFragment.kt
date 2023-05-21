package org.d3if3139.notemify.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import org.d3if3139.notemify.MainActivity
import org.d3if3139.notemify.R
import org.d3if3139.notemify.databinding.FragmentAddBinding
import org.d3if3139.notemify.model.Note
import org.d3if3139.notemify.viewmodel.NoteViewModel

class AddFragment : Fragment() {

    private lateinit var binding : FragmentAddBinding
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var mView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteViewModel = (activity as MainActivity).noteViewModel
        mView = view

        binding.saveBtn.setOnClickListener {
            saveNote(mView)
        }
        binding.backBtn.setOnClickListener {
            view.findNavController().navigate(R.id.action_addNoteFragment_to_dashboardFragment)
        }
    }

    private fun saveNote(view: View) {
        val noteTitle = binding.etNoteTitle.text.toString()
        val noteBody = binding.etNoteBody.text.toString()

        if(noteTitle.isNotEmpty()) {
            val note = Note(0, noteTitle, noteBody)

            noteViewModel.addNote(note)
            Snackbar.make(
                view, "Note saved successfully",
                Snackbar.LENGTH_SHORT
            ).show()
            view.findNavController().navigate(R.id.action_addNoteFragment_to_dashboardFragment)
        } else {
            Snackbar.make(
                view, "Please enter note title",
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_add, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_save -> {
                saveNote(mView)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}