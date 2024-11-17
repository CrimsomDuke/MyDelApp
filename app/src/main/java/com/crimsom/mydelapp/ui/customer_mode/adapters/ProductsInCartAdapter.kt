package com.crimsom.mydelapp.ui.customer_mode.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.crimsom.mydelapp.R
import com.crimsom.mydelapp.databinding.ProductInCartListItemBinding
import com.crimsom.mydelapp.utilities.ShoppingCart

class ProductsInCartAdapter(var productsInCartList : List<ShoppingCart.ProductInCartViewModel>) : RecyclerView.Adapter<ProductsInCartAdapter.ProductInCartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductInCartViewHolder {
        return ProductInCartViewHolder(ProductInCartListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false).root)
    }

    override fun getItemCount(): Int {
        return productsInCartList.size
    }

    override fun onBindViewHolder(holder: ProductInCartViewHolder, position: Int) {
        holder.bind(productsInCartList[position])
    }

    class ProductInCartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var binding : ProductInCartListItemBinding;

        init{
            binding = ProductInCartListItemBinding.bind(itemView)
        }

        public fun bind(productInCart: ShoppingCart.ProductInCartViewModel){
            binding.cartProductName.text = productInCart.product.name;
            binding.cartProductQuantity.text = "Cantidad: " + productInCart.quantity.toString();
            binding.cartProductTotal.text = "Total: " + productInCart.getTotalPrice().toString();

            //cargar imagen con glide
            if(productInCart.product.image.contains("placehold") || productInCart.product.image.isEmpty()){
                binding.cartProductImage.setImageResource(R.drawable.round_shape);
            }else{
                Glide.with(itemView.context)
                    .load(productInCart.product.image)
                    .transform(RoundedCorners(30))
                    .into(binding.cartProductImage);
            }
        }
    }
}