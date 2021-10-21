package com.example.getandpostlocation

class People : ArrayList<People.PeopleItem>(){
    data class PeopleItem(
        val location: String,
        val name: String,
        val pk: Int
    )
}