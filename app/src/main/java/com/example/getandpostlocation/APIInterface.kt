package com.example.getandpostlocation

import retrofit2.Call
import retrofit2.http.*



interface APIInterface {
    @Headers("Content-Type: application/json")
    @GET("/test/")
    fun getUser(): Call<List<People.PeopleItem>>

    @Headers("Content-Type: application/json")
    @GET("/test/{pk}")
    fun getLocations(@Path("pk") pk: Int): Call<People.PeopleItem>

    @Headers("Content-Type: application/json")
    @POST("/test/")
    fun addUser(@Body userData: People.PeopleItem): Call<People.PeopleItem>

}