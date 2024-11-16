package com.crimsom.mydelapp.ui.customer_mode.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.crimsom.mydelapp.R
import com.crimsom.mydelapp.databinding.CustOrderListItemBinding
import com.crimsom.mydelapp.models.Order

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

            if(order.estado == 3){
                binding.apply {
                    custOrdRestaurantLabel.setTextColor(Color.BLACK)
                    custOrdAddressLabel.setTextColor(Color.BLACK)
                    custOrdDriverLabel.setTextColor(Color.BLACK)
                }
                binding.orderLayout.setBackgroundResource(R.drawable.round_shape_white)
            }else{
                binding.apply {
                    custOrdRestaurantLabel.setTextColor(Color.WHITE)
                    custOrdAddressLabel.setTextColor(Color.WHITE)
                    custOrdDriverLabel.setTextColor(Color.WHITE)
                }
                binding.orderLayout.setBackgroundResource(R.drawable.round_shape)
            }

            binding.custOrdRestaurantLabel.text = order.restauranteId.toString()
            binding.custOrdAddressLabel.text = order.direccion;
            binding.custOrdDriverLabel.text = order.choferId.toString();

        }
    }
}