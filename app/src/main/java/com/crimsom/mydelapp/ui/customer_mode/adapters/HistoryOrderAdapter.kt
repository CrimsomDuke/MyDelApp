package com.crimsom.mydelapp.ui.customer_mode.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.crimsom.mydelapp.R
import com.crimsom.mydelapp.aux_interfaces.OnCurrentOrderItemListener
import com.crimsom.mydelapp.aux_interfaces.OnOrderCustomerInteractionListener
import com.crimsom.mydelapp.aux_interfaces.OnOrderDetailsListener
import com.crimsom.mydelapp.databinding.CustOrderListItemBinding
import com.crimsom.mydelapp.models.Order
import com.crimsom.mydelapp.utilities.Auth
import com.crimsom.mydelapp.utilities.Constants

class HistoryOrderAdapter(var orderList : List<Order>, var onOrderDetailsListener: OnOrderDetailsListener, var onCurrentOrderItemListener: OnCurrentOrderItemListener) :
    RecyclerView.Adapter<HistoryOrderAdapter.HistoryOrderViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryOrderViewHolder {
        return HistoryOrderViewHolder(CustOrderListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false).root)
    }

    override fun onBindViewHolder(holder: HistoryOrderViewHolder, position: Int) {
        holder.bind(orderList[position], onOrderDetailsListener, onCurrentOrderItemListener);
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    public fun updateData(newData : List<Order>){
        orderList = newData;
        notifyDataSetChanged();
    }

    class HistoryOrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var binding : CustOrderListItemBinding;

        init{
            binding = CustOrderListItemBinding.bind(itemView)
        }

        public fun bind(order: Order, onOrderDetailsListener: OnOrderDetailsListener, onCurrentOrderItemListener: OnCurrentOrderItemListener){

            //I must add the else stmt, because without it the color changes in the slide
            if(order.status == Constants.ORDER_STATUS_DELIVERED){
                binding.apply {
                    custOrdStatusDescLabel.setTextColor(Color.BLACK)
                    custOrdAddressLabel.setTextColor(Color.BLACK)
                    custOrdDriverLabel.setTextColor(Color.BLACK)
                }
                binding.orderLayout.setBackgroundResource(R.drawable.round_shape_white)

                //si la orden ya terminó, nos manda directamente a los detalles
                binding.orderLayout.setOnClickListener {
                    onOrderDetailsListener.onGoToOrderDetailsById(order.id);
                }
            }else{
                binding.apply {
                    custOrdStatusDescLabel.setTextColor(Color.WHITE)
                    custOrdAddressLabel.setTextColor(Color.WHITE)
                    custOrdDriverLabel.setTextColor(Color.WHITE)
                }
                binding.orderLayout.setBackgroundResource(R.drawable.round_shape)

                //si no termino, nos manda al mapa
                binding.orderLayout.setOnClickListener {
                    onCurrentOrderItemListener.onCurrentOrderItemClick(order.id);
                }
            }

            binding.custOrdStatusDescLabel.text = Auth.getOrderStatusDescription(order.status);
            binding.custOrdAddressLabel.text = order.address;
            binding.custOrdDriverLabel.text = "Bs" + order.total.toString();

            binding.custOrdDateLabel.visibility = View.VISIBLE;
            binding.custOrdDateLabel.text = Constants.getFullDateInFormat(order.createdAt);
        }
    }
}