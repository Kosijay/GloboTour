package com.sriyank.globochat.favorite

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sriyank.globochat.R
import com.sriyank.globochat.city.City

class FavoriteAdapter(val context: Context, val cityList : ArrayList<City>)
    : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        //initialize position and city
        private var currentPosition : Int = -1
        private var currentCity: City? = null

        //Initialize the resources
        private val txvCityName = itemView.findViewById<TextView>(R.id.txv_city_name)
        private val imvCityImage = itemView.findViewById<ImageView>(R.id.imv_city)

        fun setData(city: City, position: Int){
            //set the position and city
            this.currentPosition = position
            this.currentCity = city
            //change the city name and image in recyclerAdapter
            txvCityName.text = city.name
            imvCityImage.setImageResource(city.imageId)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.list_item_favorite, parent, false)
        return FavoriteViewHolder(itemView)
    }

    override fun onBindViewHolder(favoriteViewHolder: FavoriteViewHolder, position: Int) {
        val city = cityList[position]
        favoriteViewHolder.setData(city, position)
    }

    override fun getItemCount() = cityList.size
}