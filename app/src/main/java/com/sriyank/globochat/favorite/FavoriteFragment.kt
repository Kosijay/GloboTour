package com.sriyank.globochat.favorite

import android.content.ClipData
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.sriyank.globochat.R
import com.sriyank.globochat.city.City
import com.sriyank.globochat.city.CityAdapter
import com.sriyank.globochat.city.VacationSpots
import java.util.*
import kotlin.collections.ArrayList


class FavoriteFragment : Fragment() {

    private lateinit var favoriteCityList : ArrayList<City>
    private lateinit var favoriteAdapter : FavoriteAdapter
    private lateinit var recyclerView : RecyclerView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_favorite, container, false)

        setupRecyclerView(view)

        return view
    }

    private fun setupRecyclerView(view: View?) {
        //get context of this fragment
        val context = requireContext()
        //create Adapter
        favoriteCityList = VacationSpots.favoriteCityList as ArrayList<City> //the former way just populated the older recyclerview in the favorites section. this way adds the functionality of the addToFavorites button
        favoriteAdapter = FavoriteAdapter(context, favoriteCityList)
        //reinitialize recyclerview
        recyclerView = view?.findViewById<RecyclerView>(R.id.favorite_recycler_view)!!
        //set Adapter
        recyclerView?.adapter = favoriteAdapter
        recyclerView?.setHasFixedSize(true)

        //define layoutManager and set it to recyclerView
        val layoutManager = LinearLayoutManager(context)
        //this line below makes the items display vertically. It could be horizontal too... Getting ideas?
        layoutManager.orientation = RecyclerView.VERTICAL
        recyclerView?.layoutManager = layoutManager
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
    //implementing swipe to delete in favorites
    //create instance of itemTouchHelper
    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN,ItemTouchHelper.RIGHT){
        //implement Methods
        override fun onMove(
            //called when the item is dragged
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            targetViewHolder: RecyclerView.ViewHolder
        ): Boolean {
            //set from Position and to position
            val fromPosition = viewHolder.adapterPosition
            val toPosition = targetViewHolder.adapterPosition
            Collections.swap(favoriteCityList, fromPosition,toPosition)
            recyclerView.adapter?.notifyItemMoved(fromPosition, toPosition)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//            called when the item is swiped
            //get position of object
            val position = viewHolder.adapterPosition
            //get deleted city
            val deletedCity: City = favoriteCityList[position]
            deleteItem(position)
            updateCityList(deletedCity, false)
            Snackbar.make(recyclerView, "Deleted", Snackbar.LENGTH_LONG)
                .setAction("UNDO"){
                    undoDelete(position, deletedCity)
                }
                .show()
        }

        private fun undoDelete(position: Int, deletedCity: City) {
            favoriteCityList.add(position, deletedCity)
            favoriteAdapter.notifyItemInserted(position)
            favoriteAdapter.notifyItemRangeChanged(position, favoriteCityList.size)
            updateCityList(deletedCity, true)
        }

        private fun updateCityList(deletedCity: City, isFavorite: Boolean) {
            val cityList = VacationSpots.cityList
            val position = cityList?.indexOf(deletedCity)
            cityList!![position!!].isFavorite = isFavorite
        }

        private fun deleteItem(position: Int) {
            favoriteCityList.removeAt(position)
            favoriteAdapter.notifyItemRemoved(position)
            favoriteAdapter.notifyItemRangeChanged(position, favoriteCityList.size)
        }
    })
}
