package com.example.getandpostlocation

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewUser : AppCompatActivity() {
    lateinit var name : EditText
    lateinit var Location : EditText
    lateinit var savebtn : Button
    lateinit var buttonGET : Button
    lateinit var tvLocation : TextView
    lateinit var edName : EditText
    lateinit var etID : EditText
    private lateinit var peopleInfo: ArrayList<People.PeopleItem>
    var personID = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_user)

        name = findViewById(R.id.editTextName)
        etID = findViewById(R.id.etID)
        Location = findViewById(R.id.editTextLoc)
        savebtn = findViewById(R.id.button)
        buttonGET = findViewById(R.id.buttonGET)
        tvLocation = findViewById(R.id.tvLocation)
        edName = findViewById(R.id.edName)
        peopleInfo = arrayListOf()

        savebtn.setOnClickListener {

            addSingleuser()
        }
        buttonGET.setOnClickListener {
            getID()
            getLocation()
        }
    }

    private fun getID() {
        val celebrityNames = arrayListOf<Int>()

        for (c in peopleInfo) {
            celebrityNames.add(c.pk)
        }
        intent.putExtra("celebrityNames", celebrityNames)
        startActivity(intent)
    }

    private fun getLocation() {
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        val progressDialog = ProgressDialog(this@NewUser)
        progressDialog.setMessage("Please wait")
        progressDialog.show()


        if (apiInterface != null) {
            apiInterface.getLocations(3).enqueue(object : Callback<People.PeopleItem> {
                override fun onResponse(
                    call: Call<People.PeopleItem>,
                    response: Response<People.PeopleItem>
                ) {
                    Toast.makeText(applicationContext, "Save Success!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss()
                    val User = response.body()!!
                    tvLocation.text = " Location: ${User.location} "


                }

                override fun onFailure(call: Call<People.PeopleItem>, t: Throwable) {
                    Toast.makeText(applicationContext, "Error!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss()

                }
            })


        }
    }

    private fun addSingleuser() {

        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        val progressDialog = ProgressDialog(this@NewUser)
        progressDialog.setMessage("Please wait")
        progressDialog.show()

        val nameIN = name.text.toString()
        val locationIN = Location.text.toString()
        val IDIN = etID.text.toString().toInt()
        if (apiInterface != null) {
            apiInterface.addUser(People.PeopleItem(locationIN,nameIN,IDIN)).enqueue(object : Callback<People.PeopleItem> {
                override fun onResponse(
                    call: Call<People.PeopleItem>,
                    response: Response<People.PeopleItem>
                ) {
                    Toast.makeText(applicationContext, "Save Success!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss()
                }

                override fun onFailure(call: Call<People.PeopleItem>, t: Throwable) {
                    Toast.makeText(applicationContext, "Error!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss()

                }
            })
        }

    }
    fun addnew(view: android.view.View) {
        intent = Intent(applicationContext, NewUser::class.java)
        startActivity(intent)
    }

    fun viewusers(view: android.view.View) {
        intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
    }
}


