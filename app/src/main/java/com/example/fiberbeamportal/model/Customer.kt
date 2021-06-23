package com.example.fiberbeamportal.model

data class Customer(val name:String, val refNo:String, val status:String, val dateOfBill: String)
{
    constructor():
            this("","","","")
}