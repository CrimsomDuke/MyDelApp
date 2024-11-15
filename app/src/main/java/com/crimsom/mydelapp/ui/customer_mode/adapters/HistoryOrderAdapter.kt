package com.crimsom.mydelapp.ui.customer_mode.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.crimsom.mydelapp.R
import com.crimsom.mydelapp.databinding.CustOrderListItemBinding
import com.crimsom.mydelapp.models.Order

class HistoryOrderAdapter(var orderList : List<Order>) : RecyclerView.Adapter<HistoryOrderAdapter.HistoryOrderViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryOrderViewHolder {
        return HistoryOrderViewHolder(CustOrderListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false).root)
    }

    override fun onBindViewHolder(holder: HistoryOrderViewHolder, position: Int) {
        holder.bind(orderList[position])
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    class HistoryOrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var binding : CustOrderListItemBinding;

        init{
            binding = CustOrderListItemBinding.bind(itemView)
        }

        public fun bind(order: Order){

            //I must add the else stmt, because without it the color changes in the slide
            if(order.estado == 3){
                binding.apply {
                    custOrdRestaurantLabel.setTextColor(Color.BLACK)
                    custOrdAddressLabel.setTextColor(Color.BLACK)
                    custOrdDriverLabel.setTextColor(Color.BLACK)
                }
                itemView.setBackgroundResource(R.drawable.round_shape_white)
            }else{
                itemView.setBackgroundResource(R.drawable.round_shape)
            }

            binding.custOrdRestaurantLabel.text = order.restauranteId.toString()
            binding.custOrdAddressLabel.text = order.direccion;
            binding.custOrdDriverLabel.text = order.choferId.toString();

            binding.custOrdDateLabel.visibility = View.VISIBLE;
            binding.custOrdDateLabel.text = order.fechaHora.toString();
        }
    }

}