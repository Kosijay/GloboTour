package com.sriyank.globochat.city

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.sriyank.globochat.R

class CityAdapter(val context : Context, val cityList : ArrayList<City>)
    : RecyclerView.Adapter<CityAdapter.CityViewHolder>() {

    inner class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        //initialize position and city
        private var currentPosition : Int = -1
        private var currentCity: City? = null

        //Initialize the resources
        private val txvCityName = itemView.findViewById<TextView>(R.id.txv_city_name)
        private val imvCityImage = itemView.findViewById<ImageView>(R.id.imv_city)
        private val imvDelete = itemView.findViewById<ImageView>(R.id.imv_delete)
        private val imvFavorite = itemView.findViewById<ImageView>(R.id.imv_favorite)

        //Initialize resources used to change status of favorites button
        private val icFavoriteFilledImg = ResourcesCompat.getDrawable(context.resources,
            R.drawable.ic_favorite_filled, null)
        private val icFavoriteBorderedImg = ResourcesCompat.getDrawable(context.resources,
        R.drawable.ic_favorite_bordered, null)

        fun setData(city: City, position: Int){
            //set the position and city
            this.currentPosition = position
            this.currentCity = city
            //change the city name and image in recyclerAdapter
            txvCityName.text = city.name
            imvCityImage.setImageResource(city.imageId)
            //condition to change the drawable of the favorites button when clicked
            if(city.isFavorite)
                imvFavorite.setImageDrawable(icFavoriteFilledImg)
            else
                imvFavorite.setImageDrawable(icFavoriteBorderedImg)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {

        Log.i("CityAdapter", "onCreateViewHolder : Viewholder created")

        val itemView = LayoutInflater.from(context).inflate(R.layout.list_item_city, parent, false)
        return CityViewHolder(itemView)
    }

    override fun onBindViewHolder(cityViewHolder: CityViewHolder, position: Int) {

        Log.i("CityAdapter", "onBindViewHolder : position $position")

        val city = cityList[position]
        cityViewHolder.setData(city, position)
    }

    override fun getItemCount() = cityList.size
}