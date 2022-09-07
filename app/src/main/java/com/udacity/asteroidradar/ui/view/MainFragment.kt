package com.udacity.asteroidradar.ui.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.ui.adapter.AsteroidAdapter
import com.udacity.asteroidradar.ui.adapter.OnItemClick
import com.udacity.asteroidradar.ui.viewmodel.MainViewModel

class MainFragment : Fragment() {

    lateinit var binding:FragmentMainBinding
    lateinit var adapter: AsteroidAdapter
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

         adapter = AsteroidAdapter(OnItemClick { asteroidId ->
            viewModel.onAsteroidItemClick(asteroidId)
        })

        binding.asteroidRecycler.adapter = adapter
        viewModel.allAsteroids.observe(viewLifecycleOwner) { asteroids ->
            adapter.submitList(asteroids)
        }

        viewModel.navigateToDetailFragment.observe(viewLifecycleOwner) { asteroid ->
            asteroid?.let {
                findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
                viewModel.onDetailFragmentNavigated()
            }
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.today -> {
                binding.asteroidRecycler.adapter = adapter
                viewModel.oneAsteroids.observe(viewLifecycleOwner) { asteroids ->
                    adapter.submitList(asteroids)
                }
            }
            R.id.week -> {
                binding.asteroidRecycler.adapter = adapter
                viewModel.allAsteroids.observe(viewLifecycleOwner) { asteroids ->
                    adapter.submitList(asteroids)
                }
            }

        }
        return true
    }
}
