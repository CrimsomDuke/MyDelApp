package com.crimsom.mydelapp.ui.customer_mode.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.crimsom.mydelapp.R
import com.crimsom.mydelapp.aux_interfaces.OnCurrentOrderItemListener
import com.crimsom.mydelapp.databinding.CustOrderListItemBinding
import com.crimsom.mydelapp.models.Order
import com.crimsom.mydelapp.utilities.Auth
import com.crimsom.mydelapp.utilities.Constants

class OrderAdapter(var ordersList : List<Order>, var onCurrentOrderItemListener: OnCurrentOrderItemListener) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        return OrderViewHolder(CustOrderListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false).root)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(ordersList[position], onCurrentOrderItemListener);
    }

    override fun getItemCount(): Int {
        return ordersList.size
    }

    public fun updateData(newData: List<Order>){
        ordersList = newData;
        notifyDataSetChanged();
    }

    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var binding : CustOrderListItemBinding;

        init{
            binding = CustOrderListItemBinding.bind(itemView)
        }

        public fun bind(order: Order, onCurrentOrderItemListener: OnCurrentOrderItemListener){

            if(order.status == Constants.ORDER_STATUS_DELIVERED){
                binding.apply {
                    custOrdStatusDescLabel.setTextColor(Color.BLACK)
                    custOrdAddressLabel.setTextColor(Color.BLACK)
                    custOrdDriverLabel.setTextColor(Color.BLACK)
                }
                binding.orderLayout.setBackgroundResource(R.drawable.round_shape_white)
            }else{
                binding.apply {
                    custOrdStatusDescLabel.setTextColor(Color.WHITE)
                    custOrdAddressLabel.setTextColor(Color.WHITE)
                    custOrdDriverLabel.setTextColor(Color.WHITE)
                }
                binding.orderLayout.setBackgroundResource(R.drawable.round_shape)
            }

            binding.custOrdStatusDescLabel.text = "Orden en: " + Auth.getOrderStatusDescription(order.status);
            binding.custOrdAddressLabel.text = order.address;
            binding.custOrdDriverLabel.text = order.driverId.toString();
            if(order.driverId == null){
                binding.custOrdDriverLabel.text = "Aun sin chofer"
            }else{
                binding.custOrdDriverLabel.text = "Chofer asignado ";
            }

            //action
            binding.root.setOnClickListener {
                onCurrentOrderItemListener.onCurrentOrderItemClick(order.id);
            }

        }
    }
}