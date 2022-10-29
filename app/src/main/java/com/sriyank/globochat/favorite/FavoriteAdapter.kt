package com.sriyank.globochat.favorite

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.sriyank.globochat.R
import com.sriyank.globochat.city.City
import com.sriyank.globochat.city.VacationSpots
import com.sriyank.globochat.city.VacationSpots.favoriteCityList

class FavoriteAdapter(val context: Context, val cityList : ArrayList<City>)
    : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        //initialize position and city
        private var currentPosition : Int = -1
        private var currentCity: City? = null
        private val imvFavorite = itemView.findViewById<ImageView>(R.id.imv_favorite)

        //Initialize the resources
        private val txvCityName = itemView.findViewById<TextView>(R.id.txv_city_name)
        private val imvCityImage = itemView.findViewById<ImageView>(R.id.imv_city)
        private val imvUnFavorite = itemView.findViewById<ImageView>(R.id.unFavorite)
        private val icFavoriteBorderedImg = ResourcesCompat.getDrawable(context.resources,
            R.drawable.ic_favorite_bordered, null)
        private val icFavoriteFilledImg = ResourcesCompat.getDrawable(context.resources,
            R.drawable.ic_favorite_filled, null)


        fun setData(city: City, position: Int){
            //set the position and city
            this.currentPosition = position
            this.currentCity = city
            //change the city name and image in recyclerAdapter
            txvCityName.text = city.name
            imvCityImage.setImageResource(city.imageId)
        }

        fun setListeners() {
            imvUnFavorite.setOnClickListener(this@FavoriteViewHolder)
        }

        override fun onClick(v: View?) {
            when (v!!.id){
                R.id.unFavorite -> removeFavorite()
            }
        }

        private fun removeFavorite() {
//            imvFavorite.setImageDrawable(icFavoriteBorderedImg)
//            VacationSpots.favoriteCityList.removeAt(currentPosition)
            currentCity?.isFavorite = !(currentCity?.isFavorite!!)
            if(currentCity?.isFavorite!!){      //if it is favorite, update city icon and add city object to favorite List
                imvUnFavorite.setImageDrawable(icFavoriteFilledImg)
            }else{      //else if not favorite, update city icon and remove from favorite list
//                imvFavorite.setImageDrawable(icFavoriteBorderedImg)
                VacationSpots.favoriteCityList.remove(currentCity!!)
                notifyItemRemoved(currentPosition)            //notify adapter of deleted item
                notifyItemRangeChanged(currentPosition, favoriteCityList.size)
//                showSnackBar(view)
            }
        }

        private fun showSnackBar(view:View) {
            Snackbar.make(context,view, "Removed from Favorites", Snackbar.LENGTH_LONG).show()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.list_item_favorite, parent, false)
        return FavoriteViewHolder(itemView)
    }

    override fun onBindViewHolder(favoriteViewHolder: FavoriteViewHolder, position: Int) {
        val city = cityList[position]
        favoriteViewHolder.setData(city, position)
        favoriteViewHolder.setListeners()
    }

    override fun getItemCount() = cityList.size
}