package com.test.positionsapp.ui.positions

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.positionsapp.R
import com.test.positionsapp.data.entities.Positions
import com.test.positionsapp.databinding.PositionsFragmentBinding
import com.test.positionsapp.utils.Resource
import com.test.positionsapp.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONArray
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


@AndroidEntryPoint
class PositionsFragment : Fragment(), PositionsAdapter.PositionItemListener {

    companion object {
        fun newInstance() = PositionsFragment()
        private val TAG = "POSITIONS"
    }
    private var binding: PositionsFragmentBinding by autoCleared()
    private val viewModel: PositionsViewModel by viewModels()
    private lateinit var adapter: PositionsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PositionsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        adapter = PositionsAdapter(this)
        binding.positionsRv.layoutManager = LinearLayoutManager(requireContext())
        binding.positionsRv.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.positions.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    if (!it.data.isNullOrEmpty()) {
                        adapter.setItems(ArrayList(it.data))
                        Log.e(TAG, "LISTADO: ${it.data}")
                        /*viewModel.positions.observe(viewLifecycleOwner, Observer { positions ->
                            Log.e(TAG, "LISTADO DATABASE: $it")
                        })*/
                    }
                }
                Resource.Status.ERROR ->
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING ->
                    binding.progressBar.visibility = View.VISIBLE
            }
        })
    }

    override fun onClickedPosition(position: Positions) {

        findNavController().navigate(
            R.id.action_positionsFragment_to_positionDetailFragment,
            bundleOf("pos" to position)
        )

    }

}