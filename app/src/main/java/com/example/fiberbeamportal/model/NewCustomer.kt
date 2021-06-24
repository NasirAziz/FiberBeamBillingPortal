package com.example.fiberbeamportal.model

data class NewCustomer(val name :String, val designation:String, val bill:String, val dateofconnection:String, val adress:String, val phone:String){
constructor():
        this("","","","","","")
}
