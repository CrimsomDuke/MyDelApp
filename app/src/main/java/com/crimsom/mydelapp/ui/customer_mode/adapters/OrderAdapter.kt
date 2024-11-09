package com.crimsom.mydelapp.ui.customer_mode.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.crimsom.mydelapp.databinding.CustOrderListItemBinding
import com.crimsom.mydelapp.databinding.RestaurantListItemBinding
import com.crimsom.mydelapp.models.Order
import com.crimsom.mydelapp.ui.customer_mode.adapters.RestaurantAdapter.RestaurantViewHolder

class OrderAdapter(var ordersList : List<Order>) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        return return OrderViewHolder(CustOrderListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false).root)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(ordersList[position])
    }

    override fun getItemCount(): Int {
        return ordersList.size
    }

    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var binding : CustOrderListItemBinding;

        init{
            binding = CustOrderListItemBinding.bind(itemView)
        }

        public fun bind(order: Order){
            binding.custOrdRestaurantLabel.text = order.restauranteId.toString()
            binding.custOrdAddressLabel.text = order.direccion;
            binding.custOrdDriverLabel.text = order.choferId.toString();
        }
    }
}