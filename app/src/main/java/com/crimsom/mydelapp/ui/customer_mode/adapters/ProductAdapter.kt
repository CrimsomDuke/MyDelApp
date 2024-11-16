package com.crimsom.mydelapp.ui.customer_mode.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.crimsom.mydelapp.aux_interfaces.OnShoppingCartInteractionListener
import com.crimsom.mydelapp.databinding.ProductListItemBinding
import com.crimsom.mydelapp.models.Product
import com.crimsom.mydelapp.utilities.ShoppingCart

class ProductAdapter(var productList : List<Product>) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>(),
    OnShoppingCartInteractionListener{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(ProductListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false).root)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(productList[position], this)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    public fun updateData(products : List<Product>){
        productList = products;
        notifyDataSetChanged()
    }

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var binding : ProductListItemBinding;

        init{
            binding = ProductListItemBinding.bind(itemView)
        }

        public fun bind(product: Product, listener: OnShoppingCartInteractionListener){

            //load current quantity
            binding.custRestProductCounterLabel.text = ShoppingCart.getProductCount(product).toString()

            binding.custRestProductNameLabel.text = product.name;
            binding.custRestAddProductButton.setOnClickListener {
                listener.onProductAdd(product)
                binding.custRestProductCounterLabel.text = ShoppingCart.getProductCount(product).toString()
            }

            binding.custRestRemoveProductButton.setOnClickListener {
                listener.onProductRemove(product)
                binding.custRestProductCounterLabel.text = ShoppingCart.getProductCount(product).toString()
            }

            binding.productLayout.setOnClickListener {
                if(binding.buttonsGroup.visibility == View.VISIBLE){
                    binding.buttonsGroup.visibility = View.GONE
                }else{
                    binding.buttonsGroup.visibility = View.VISIBLE
                }
            }

            if(product.image.contains("placehold") || product.image.isEmpty()){
                binding.custRestProductPic.setImageResource(android.R.drawable.ic_menu_report_image)
            }else{
                Glide
                    .with(itemView.context)
                    .load(product.image)
                    .transform(RoundedCorners(16))
                    .into(binding.custRestProductPic);

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