package com.crimsom.mydelapp.utilities

import com.crimsom.mydelapp.models.Product

object ShoppingCart  {

    private lateinit var instance : ShoppingCart;
    private var items = mutableListOf<Product>();

    fun addProduct(product: Product){
        items.add(product);
    }

    fun removeProduct(product: Product){
        items.remove(product);
    }

    fun getItems(): List<Product>{
        return items;
    }

    fun reset(){
        items.clear();
    }

    fun getProductCount(product: Product): Int{
        return items.filter { it.id == product.id }.size;
    }

}