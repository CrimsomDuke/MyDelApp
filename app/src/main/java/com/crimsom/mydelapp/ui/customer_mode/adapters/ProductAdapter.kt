package com.crimsom.mydelapp.ui.customer_mode.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.crimsom.mydelapp.aux_interfaces.OnShoppingCartInteractionListener
import com.crimsom.mydelapp.databinding.ProductListItemBinding
import com.crimsom.mydelapp.models.Product
import com.crimsom.mydelapp.utilities.ShoppingCart

class ProductAdapter(var productList : List<Product>) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>(),
    OnShoppingCartInteractionListener{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return return ProductViewHolder(ProductListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false).root)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(productList[position], this)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var binding : ProductListItemBinding;

        init{
            binding = ProductListItemBinding.bind(itemView)
        }

        public fun bind(product: Product, listener: OnShoppingCartInteractionListener){
            binding.custRestProductNameLabel.text = product.nombre;
            binding.custRestAddProductButton.setOnClickListener {
                listener.onProductAdd(product)
                binding.custRestProductCounterLabel.text = ShoppingCart.getProductCount(product).toString()
            }

            binding.custRestRemoveProductButton.setOnClickListener {
                listener.onProductRemove(product)
                binding.custRestProductCounterLabel.text = ShoppingCart.getProductCount(product).toString()
            }
        }
    }

    override fun onProductAdd(product: Product) {
        ShoppingCart.addProduct(product)
    }

    override fun onProductRemove(product: Product) {
        ShoppingCart.removeProduct(product)
    }
}