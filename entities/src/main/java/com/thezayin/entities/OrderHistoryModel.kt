package com.thezayin.entities


data class OrderHistoryModel(
    val id: String? = null,
    val userId: String? = null,
    val name: String? = null,
    val phoneNumber: String? = null,
    val email: String? = null,
    val address: String? = null,
    val message: String? = null,
    val orderDate: String? = null,
    val orderTime: String? = null,
    val orderDateTime:String?=null,
    val orderStatus: String? = null,
    val paymentMethod: String? = null,
    val totalAmount: String? = null,
    val orders: List<CartModel>? = null
)