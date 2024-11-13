package com.crimsom.mydelapp.aux_interfaces

import com.crimsom.mydelapp.models.Product

interface OnShoppingCartInteractionListener {

    public fun onProductAdd(product: Product)
    public fun onProductRemove(product: Product)

}