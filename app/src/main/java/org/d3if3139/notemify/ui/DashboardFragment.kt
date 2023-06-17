package org.d3if3139.notemify.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import org.d3if3139.notemify.MainActivity
import org.d3if3139.notemify.R
import org.d3if3139.notemify.adapter.NoteAdapter
import org.d3if3139.notemify.databinding.FragmentDashboardBinding
import org.d3if3139.notemify.model.Note
import org.d3if3139.notemify.viewmodel.NoteViewModel

class DashboardFragment : Fragment() {

    private lateinit var binding : FragmentDashboardBinding
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var myAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteViewModel = (activity as MainActivity).noteViewModel

        noteViewModel.scheduleUpdater(requireActivity().application)

        setUpRecyclerView()

        binding.aboutBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_dashboardFragment_to_aboutFragment)
        }

        binding.fabAddNote.setOnClickListener {
            it.findNavController().navigate(R.id.action_dashboardFragment_to_addNoteFragment)
        }
    }

    private fun setUpRecyclerView() {
        myAdapter = NoteAdapter()
        binding.recyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL
            )
            setHasFixedSize(true)
            adapter = myAdapter
        }
        activity?.let {
            noteViewModel.getAllNote().observe(viewLifecycleOwner, { note ->
                myAdapter.differ.submitList(note)
                updateUI(note)
            })
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_dashboard, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_about -> {
                findNavController().navigate(
                    R.id.action_dashboardFragment_to_aboutFragment
                )
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateUI(note: List<Note>) {
        if (note.isNotEmpty()) {
            binding.recyclerView.visibility = View.VISIBLE
        } else {
            binding.recyclerView.visibility = View.GONE
        }
    }

}