package com.example.fiberbeamportal.model

data class NewCustomer(val name :String, val designation:String, val bill:String, var dateofconnection:String, val adress:String, val phone:String){
constructor():
        this("","","","","","")
}
