package com.example.fiberbeamportal.model

data class NewCustomer(val name :String,
                       var designation:String,
                       var bill:String,
                       var dateofconnection:String,
                       var adress:String,
                       var phone:String,
                       var status:String,
                       var pkg:String){
constructor():
        this("","","","","","","","")
}
