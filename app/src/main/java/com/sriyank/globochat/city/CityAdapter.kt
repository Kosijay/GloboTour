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

    inner class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
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

        //in the function: set the onClickListener and extend View.OnclickListener in the inner class then implement Onclick member
        fun setListeners() {
            imvDelete.setOnClickListener(this@CityViewHolder)
            imvFavorite.setOnClickListener(this@CityViewHolder)
        }

        //I dont know what the when statement means, its probably like a higher version of the if statement
        override fun onClick(v: View?) {
            when(v!!.id){
                //These two reference the functions to delete and add to favorites. create the functions INSIDE THE INNER CLASS TOO
                R.id.imv_delete -> deleteItem()
                R.id.imv_favorite -> addToFavorites()

            }
        }

        private fun addToFavorites() {
            currentCity?.isFavorite = !(currentCity?.isFavorite!!)  //toggle Boolean value of City.isFavorite
            //update icon
            if(currentCity?.isFavorite!!){      //if it is favorite, update city icon and add city object to favorite List
                imvFavorite.setImageDrawable(icFavoriteFilledImg)
                VacationSpots.favoriteCityList.add(currentCity!!)
            }else{      //else if not favorite, update city icon and remove from favorite list
                imvFavorite.setImageDrawable(icFavoriteBorderedImg)
                VacationSpots.favoriteCityList.remove(currentCity!!)
            }
        }

        private fun deleteItem() {
            cityList.removeAt(currentPosition) //delete city obj from cityList at position it was clicked
            notifyItemRemoved(currentPosition)            //notify adapter of deleted item
            notifyItemRangeChanged(currentPosition, cityList.size)            //update visually
            VacationSpots.favoriteCityList.remove(currentCity!!)// later Added, Remove Item from Favorites if added

            //Adding an item is similar
/*            cityList.add(position,newCityObject)
            notifyItemChanged(position)
            notifyItemRangeChanged(position, cityList.size)*/
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
        //First add the new method setListeners to implement click Actions
        cityViewHolder.setListeners()
    }

    override fun getItemCount() = cityList.size
}