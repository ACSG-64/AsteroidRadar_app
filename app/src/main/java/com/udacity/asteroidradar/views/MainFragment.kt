package com.udacity.asteroidradar.views

import android.content.res.Resources
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.viewModels.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }
    private val asteroidListAdapter = AsteroidListAdapter(arrayListOf())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {



        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.refresh()

        // Recycler view
        asteroid_recycler.apply{
            layoutManager = LinearLayoutManager(context)
            adapter = asteroidListAdapter
        }
        modelObserver()
    }

    private fun modelObserver(){
        viewModel.asteroidList.observe(viewLifecycleOwner, Observer { asteroids ->
            asteroids?.let{
                asteroid_recycler.visibility = View.VISIBLE
                asteroidListAdapter.updateAsteroidList(asteroids)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.show_all_menu -> {
                viewModel.getWeekAsteroids()
                modelObserver()
                Toast.makeText(context, context?.getString(R.string.showing_week_asteroids), Toast.LENGTH_SHORT).show()
                true
            }
            R.id.show_today_menu -> {
                viewModel.getTodayAsteroids()
                Toast.makeText(context, context?.getString(R.string.showing_today_asteroids), Toast.LENGTH_SHORT).show()
                modelObserver()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
