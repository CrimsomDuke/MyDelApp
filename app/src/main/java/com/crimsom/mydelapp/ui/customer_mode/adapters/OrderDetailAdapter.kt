package com.crimsom.mydelapp.ui.customer_mode.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.crimsom.mydelapp.databinding.OrderDetailListItemBinding
import com.crimsom.mydelapp.models.OrderDetail

class OrderDetailAdapter(var orderDetails : List<OrderDetail>) : RecyclerView.Adapter<OrderDetailAdapter.OrderDetailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailViewHolder {
        return OrderDetailViewHolder(OrderDetailListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false).root);
    }

    override fun onBindViewHolder(holder: OrderDetailViewHolder, position: Int) {
        holder.bind(orderDetails[position]);
    }

    override fun getItemCount(): Int {
        return orderDetails.size;
    }

    public fun updateData(newData : List<OrderDetail>){
        orderDetails = newData;
        notifyDataSetChanged();
    }

    class OrderDetailViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        private val binding = OrderDetailListItemBinding.bind(itemView);

        fun bind(orderDetail : OrderDetail){
            binding.ordDetProductLabel.text = orderDetail.product.name;
            binding.ordDetTotalDetLabel.text = "Bs${orderDetail.price * orderDetail.quantity}";
            binding.ordDetQuantityLabel.text = "x${orderDetail.quantity}";

            Glide.with(itemView.context)
                .load(orderDetail.product.image)
                .into(binding.ordDetProdImage);
        }
    }
}