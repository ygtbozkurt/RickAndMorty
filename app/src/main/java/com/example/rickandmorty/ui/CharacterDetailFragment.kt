package com.example.rickandmorty.ui

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.rickandmorty.R
import com.example.rickandmorty.data.model.RickAndMortyCharacter
import com.example.rickandmorty.databinding.FragmentCharacterDetailBinding
import com.example.rickandmorty.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailFragment : Fragment() {


    private lateinit var binding: FragmentCharacterDetailBinding
    private val viewModel: CharacterDetailViewModel by viewModels()
    private val body: MutableList<String> = ArrayList()
    private var listState: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getInt("id")?.let { viewModel.start(it) }
        setupObservers()

    }

    private fun setupObservers() {
        viewModel.rickAndMortyCharacter.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    bindCharacter(it.data!!)
                    bindExpandableList(it.data)
                    binding.progressBar.visibility = View.GONE
                    binding.characterCl.visibility = View.VISIBLE
                }

                Resource.Status.ERROR -> {
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                    showBasicAlert(requireContext(), it.message)
                }

                Resource.Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.characterCl.visibility = View.GONE
                }
            }
        })

    }

    private fun bindCharacter(rickAndMortyCharacter: RickAndMortyCharacter) {
        binding.name.text = rickAndMortyCharacter.name
        binding.speciesAndStatus.text =
            """${rickAndMortyCharacter.status} , ${rickAndMortyCharacter.species}"""
        binding.gender.text = rickAndMortyCharacter.gender
        Glide.with(binding.root)
            .load(rickAndMortyCharacter.image)
            .transform(CircleCrop())
            .into(binding.image)
    }

    private fun bindExpandableList(rickAndMortyCharacter: RickAndMortyCharacter) {
        if (listState == 0) {
            val header = getString(R.string.expandable_list_title)
            body.addAll(rickAndMortyCharacter.episode)
            binding.expandableListView.setAdapter(
                ExpandableListAdapter(
                    requireContext(),
                    binding.expandableListView,
                    header,
                    body
                )
            )
            listState = 1
        }
    }

    private fun showBasicAlert(context: Context, message: String?) {
        val builder = AlertDialog.Builder(context)
        with(builder)
        {
            setTitle(getString(R.string.alert_dialog_title))
            setMessage(message)
            setPositiveButton("cancel") { dialog, _ -> dialog.dismiss() }
            setCancelable(true)
            show()
        }
    }

}


