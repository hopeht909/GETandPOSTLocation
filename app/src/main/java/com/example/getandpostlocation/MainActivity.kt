package com.example.getandpostlocation

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var rvMain: RecyclerView
    private lateinit var rvAdapter: RVAdapter
    private lateinit var info: ArrayList<String>
    private lateinit var addName: Button
    private lateinit var peopleInfo: ArrayList<People.PeopleItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        info =  arrayListOf()
        rvMain = findViewById(R.id.rvMain)
        rvAdapter = RVAdapter(info)
        addName = findViewById(R.id.addName)
         peopleInfo = arrayListOf()
        //an object from APIInterface to call the method
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        //progress dialog to show that something happened
        val progressDialog = ProgressDialog(this@MainActivity)
        progressDialog.setMessage("Please wait")
        progressDialog.show()

        if (apiInterface != null) {
            apiInterface.getUser()?.enqueue(object : Callback<List<People.PeopleItem>> {
                override fun onResponse(
                    call: Call<List<People.PeopleItem>>,
                    response: Response<List<People.PeopleItem>>
                ) {
                    progressDialog.dismiss()
                    for (User in response.body()!!) {
                        val fullInfo = "Name: ${User.name} \n Location: ${User.location} "
                        info.add(fullInfo)
                    }
                    rvMain.adapter = rvAdapter
                    rvMain.layoutManager = LinearLayoutManager(this@MainActivity)
                    rvMain.adapter?.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<List<People.PeopleItem>>, t: Throwable) {
                    progressDialog.dismiss()
                    Toast.makeText(applicationContext, "" + t.message, Toast.LENGTH_SHORT).show()
                }
            })
        }

    }
    fun addnew(view: android.view.View) {
        intent = Intent(applicationContext, NewUser::class.java)
        startActivity(intent)
    }




}