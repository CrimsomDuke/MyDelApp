package com.crimsom.mydelapp.ui.driver_mode.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.crimsom.mydelapp.aux_interfaces.OnUntakenOrderClickListener
import com.crimsom.mydelapp.databinding.DriverUntakenOrderListItemBinding
import com.crimsom.mydelapp.models.Order

class UntakenOrderAdapter(var untakerOrders : List<Order>, var onUntakenOrderClickListener: OnUntakenOrderClickListener) : RecyclerView.Adapter<UntakenOrderAdapter.UntakenOrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UntakenOrderViewHolder {
        return UntakenOrderViewHolder(DriverUntakenOrderListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false).root)
    }

    override fun getItemCount(): Int {
        return untakerOrders.size
    }

    override fun onBindViewHolder(holder: UntakenOrderViewHolder, position: Int) {
        holder.bind(untakerOrders[position], onUntakenOrderClickListener)
    }

    public fun updateData(newData : List<Order>){
        untakerOrders = newData;
        notifyDataSetChanged();
    }

    class UntakenOrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var binding : DriverUntakenOrderListItemBinding;

        init{
            binding = DriverUntakenOrderListItemBinding.bind(itemView)
        }

        public fun bind(order: Order, onUntakenOrderClickListener: OnUntakenOrderClickListener){
            binding.driverOrdAddressLabel.text = order.address;
            binding.driverOrdTotalToPay.text = order.total.toString();
            binding.driverOrdOrderIdLabel.text = order.id.toString();

            binding.untakenOrderLayout.setOnClickListener{
                onUntakenOrderClickListener.onUntakenOrderClick(order)
            }
        }
    }
}