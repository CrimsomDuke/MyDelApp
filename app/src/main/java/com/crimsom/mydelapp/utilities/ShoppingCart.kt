package com.crimsom.mydelapp.utilities

import com.crimsom.mydelapp.models.Order
import com.crimsom.mydelapp.models.Product
import com.crimsom.mydelapp.models.aux_models.OrderDetailOnSending

object ShoppingCart  {

    private var items = mutableListOf<Product>();
    public var orderAddress : String = "Vacio";

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
        orderAddress = "Vacio";
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

    //aux model type for sending data
    private fun getOrderDetailsFromCart(): List<OrderDetailOnSending>{
        var orderDetails = ArrayList<OrderDetailOnSending>();
        items.distinct().forEach {
            orderDetails.add(OrderDetailOnSending(0, getProductCount(it), it.price.toDouble(), it));
        }
        return orderDetails.toList();
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

        order.orderDetailsOnSending = getOrderDetailsFromCart();

        return order;
    }

}