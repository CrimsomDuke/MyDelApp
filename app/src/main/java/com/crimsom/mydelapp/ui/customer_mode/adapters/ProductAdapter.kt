package com.crimsom.mydelapp.ui.customer_mode.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.crimsom.mydelapp.databinding.ProductListItemBinding
import com.crimsom.mydelapp.models.Product

class ProductAdapter(var productList : List<Product>) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return return ProductViewHolder(ProductListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false).root)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var binding : ProductListItemBinding;

        init{
            binding = ProductListItemBinding.bind(itemView)
        }

        public fun bind(product: Product){
            binding.custRestProductNameLabel.text = product.nombre;
        }
    }
}