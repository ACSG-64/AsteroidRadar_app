package com.udacity.asteroidradar.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.AsteroidCardBinding
import com.udacity.asteroidradar.Asteroid

class AsteroidListAdapter(val asteroidList : ArrayList<Asteroid>) : RecyclerView.Adapter<AsteroidListAdapter.AsteroidViewHolder>(), AsteroidClickListener {

    fun updateAsteroidList(newAsteroidList: List<Asteroid>){
        asteroidList.clear()
        asteroidList.addAll(newAsteroidList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<AsteroidCardBinding>(inflater, R.layout.asteroid_card, parent, false)
        return AsteroidViewHolder(view)
    }

    override fun getItemCount() = asteroidList.size

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        holder.view.asteroid = asteroidList[position]
        holder.view.clickListener = this
    }

    override fun onCardClicked(view: View, asteroidDetail: Asteroid){
        val action = MainFragmentDirections.actionShowDetail(asteroidDetail)
        Navigation.findNavController(view).navigate(action)
    }

    class AsteroidViewHolder(var view: AsteroidCardBinding) : RecyclerView.ViewHolder(view.root)
}