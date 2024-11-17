package com.crimsom.mydelapp.utilities

import com.crimsom.mydelapp.models.Order
import com.crimsom.mydelapp.models.OrderDetail
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

    fun getProductsInCartList(): List<ProductInCartViewModel>{
        var productsInCart = mutableListOf<ProductInCartViewModel>();
        items.distinct().forEach {
            productsInCart.add(ProductInCartViewModel(it, getProductCount(it)));
        }
        return productsInCart;
    }

    fun getTotalToPay(): Double{
        var total = 0.0;
        items.forEach {
            total += it.price;
        }
        return total;
    }

    private fun getOrderDetailsFromCart(): List<OrderDetail>{
        var orderDetails = mutableListOf<OrderDetail>();
        items.distinct().forEach {
            orderDetails.add(OrderDetail(0, getProductCount(it), it.price.toDouble(), it));
        }
        return orderDetails;
    }

    class ProductInCartViewModel(var product: Product, var quantity: Int){
        fun getTotalPrice(): Int {
            return product.price * quantity;
        }
    }

    public fun createOrderFromCart(userId : Int, restaurantId : Int, latitude : String, longitude : String) : Order {
        var order = Order(
            id = 0,
            userId = userId,
            restaurantId = restaurantId,
            total = getTotalToPay(),
            latitude = latitude,
            longitude = longitude,
            status = 1
        );

        order.orderDetails = getOrderDetailsFromCart();

        return order;
    }

}