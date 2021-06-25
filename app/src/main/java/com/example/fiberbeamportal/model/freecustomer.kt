package com.example.fiberbeamportal.model

data class freecustomer(val name :String, val designation:String, var dateofconnection:String, val adress:String, val phone:String,
                       var status:String){
    constructor():
            this("","","","","","")
}