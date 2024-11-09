package com.crimsom.mydelapp.ui.customer_mode.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.crimsom.mydelapp.R
import com.crimsom.mydelapp.databinding.RestaurantListItemBinding
import com.crimsom.mydelapp.models.Restaurant

class RestaurantAdapter(var restaurantsList : List<Restaurant>) : RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        return RestaurantViewHolder(RestaurantListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false).root)
    }

    override fun getItemCount(): Int {
        return restaurantsList.size
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.bind(restaurantsList[position])
    }

    public fun updateData(newData : List<Restaurant>){
        restaurantsList = newData
        notifyDataSetChanged()
    }

    class RestaurantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var binding : RestaurantListItemBinding;

        init{
            binding = RestaurantListItemBinding.bind(itemView)
        }

        public fun bind(restaurant: Restaurant){
            binding.restaurantNameLabel.text = restaurant.nombre
            binding.restaurantImage.setImageResource(R.drawable.ic_launcher_foreground)
        }
    }
}