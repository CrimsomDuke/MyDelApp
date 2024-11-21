package com.crimsom.mydelapp.ui.customer_mode.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.crimsom.mydelapp.R
import com.crimsom.mydelapp.databinding.OrderDetailListItemBinding
import com.crimsom.mydelapp.models.OrderDetail

class OrderDetailAdapter(var orderDetails : List<OrderDetail>) : RecyclerView.Adapter<OrderDetailAdapter.OrderDetailViewHolder>() {

    private var orderDetailList = mutableListOf<OrderDetail>();

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.order_detail_list_item, parent, false);
        return OrderDetailViewHolder(view);
    }

    override fun onBindViewHolder(holder: OrderDetailViewHolder, position: Int) {
        holder.bind(orderDetailList[position]);
    }

    override fun getItemCount(): Int {
        return orderDetailList.size;
    }

    public fun updateData(newData : List<OrderDetail>){
        orderDetails = newData;
        notifyDataSetChanged();
    }

    class OrderDetailViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        private val binding = OrderDetailListItemBinding.bind(itemView);

        fun bind(orderDetail : OrderDetail){
            binding.ordDetProductLabel.text = orderDetail.product.name;
            binding.ordDetTotalDetLabel.text = "Bs${orderDetail.price}";
            binding.ordDetQuantityLabel.text = "x${orderDetail.quantity}";

            Glide.with(itemView.context)
                .load(orderDetail.product.image)
                .into(binding.ordDetProdImage);
        }
    }
}