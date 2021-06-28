package com.example.fiberbeamportal.model

data class FreeCustomer(var name :String,
                        var designation:String,
                        var dateofconnection:String,
                        var adress:String,
                        var phone:String,
                        var status:String){
    constructor():
            this("","","","","","")
}