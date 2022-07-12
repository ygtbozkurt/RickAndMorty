package com.example.rickandmorty.ui

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.FragmentCharactersBinding
import com.example.rickandmorty.utils.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CharactersFragment : Fragment(), CharactersAdapter.CharacterItemListener {

    private lateinit var binding: FragmentCharactersBinding
    private val viewModel: CharactersViewModel by viewModels()
    private lateinit var adapter: CharactersAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharactersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        adapter = CharactersAdapter(this)
        binding.charactersRv.layoutManager = (GridLayoutManager(requireContext(), 2))
        binding.charactersRv.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.characters.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    if (!it.data.isNullOrEmpty()) adapter.setItems(ArrayList(it.data))
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    showBasicAlert(requireContext(), it.message)
                }
                Resource.Status.LOADING ->
                    binding.progressBar.visibility = View.VISIBLE
            }
        })
    }

    override fun onClickedCharacter(characterId: Int) {
        findNavController().navigate(
            R.id.action_charactersFragment_to_characterDetailFragment,
            bundleOf("id" to characterId)
        )
    }

    private fun showBasicAlert(context: Context, message: String?) {
        val builder = AlertDialog.Builder(context)
        with(builder)
        {
            setTitle(getString(R.string.alert_dialog_title))
            setMessage(message)
            setNegativeButton("cancel") { dialog, _ -> dialog.dismiss() }
            setCancelable(true)
            show()
        }
    }

}