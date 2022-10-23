package com.sriyank.globochat.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sriyank.globochat.R
import com.sriyank.globochat.city.CityAdapter
import com.sriyank.globochat.city.VacationSpots


class FavoriteFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_favorite, container, false)

        setupRecyclerView(view)

        return view
    }

    private fun setupRecyclerView(view: View?) {
        //get context of this fragment
        val context = requireContext()
        //create Adapter
        val FavoriteAdapter = FavoriteAdapter(context, VacationSpots.cityList!!)
        //reinitialize recyclerview
        val recyclerView = view?.findViewById<RecyclerView>(R.id.favorite_recycler_view)
        //set Adapter
        recyclerView?.adapter = FavoriteAdapter
        recyclerView?.setHasFixedSize(true)

        //define layoutManager and set it to recyclerView
        val layoutManager = LinearLayoutManager(context)
        //this line below makes the items display vertically. It could be horizontal too... Getting ideas?
        layoutManager.orientation = RecyclerView.VERTICAL
        recyclerView?.layoutManager = layoutManager
    }
}
