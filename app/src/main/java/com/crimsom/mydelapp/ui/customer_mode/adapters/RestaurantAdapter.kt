package com.crimsom.mydelapp.ui.customer_mode.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.crimsom.mydelapp.R
import com.crimsom.mydelapp.aux_interfaces.OnRestaurantClickListener
import com.crimsom.mydelapp.databinding.RestaurantListItemBinding
import com.crimsom.mydelapp.models.Restaurant

class RestaurantAdapter(var restaurantsList : List<Restaurant>, var onRestaurantClickListener: OnRestaurantClickListener) : RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        return RestaurantViewHolder(RestaurantListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false).root)
    }

    override fun getItemCount(): Int {
        return restaurantsList.size
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.bind(restaurantsList[position], onRestaurantClickListener)
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

        public fun bind(restaurant: Restaurant, onRestaurantClickListener: OnRestaurantClickListener){
            binding.restaurantNameLabel.text = restaurant.name
            binding.restaurantLayout.setOnClickListener{
                onRestaurantClickListener.onRestaurantClick(restaurant.id);
            }

            if(restaurant.logoUrl.contains("placehold") || restaurant.logoUrl.isEmpty()){
                binding.restaurantImage.setImageResource(R.drawable.ic_launcher_foreground)
            }else{
                Glide
                    .with(itemView.context)
                    .load(restaurant.logoUrl)
                    .transform(RoundedCorners(30))
                    .into(binding.restaurantImage)
            }
        }
    }
}