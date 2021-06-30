package com.example.fiberbeamportal.model

data class NewUser(var name:String,
                   var email:String,
                   var phone:String,
                   var password:String,
                   var address:String,
                   var isPAF:String)
{
    constructor():
            this("", "", "", "","","")
}
