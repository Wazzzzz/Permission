package com.example.permissionsample

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var loadImageButton: Button
    private lateinit var imageView: ImageView
    private lateinit var requestPermissionButton: Button
    private lateinit var clickButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadImageButton = findViewById(R.id.load_image_button)
        imageView = findViewById(R.id.image_view)
        requestPermissionButton = findViewById(R.id.request_permission_button)
        clickButton = findViewById(R.id.snackbar_button)

        loadImageButton.setOnClickListener {
            loadImage()
        }

        requestPermissionButton.setOnClickListener {
            requestPermissionLocation()
        }

        clickButton.setOnClickListener {
            Snackbar.make(it, "Ini snackbar", Snackbar.LENGTH_LONG).show()

//            Snackbar.make(it, "Ini snackbar long dengan action", Snackbar.LENGTH_LONG).setAction("text action") {
//                Toast.makeText(this, "Toast dari action snackbar", Toast.LENGTH_LONG).show()
//            }
//                .show()

//            val snackBar = Snackbar.make(it, "Ini snackbar indefinite. Gak bakal pergi kalo gak di klik dismiss / Ada snackbar lainnya", Snackbar.LENGTH_INDEFINITE)
//
//            snackBar.setAction("Dismiss"){
//                snackBar.dismiss()
//            }
//            snackBar.show()
        }
    }

    private fun loadImage() {
        Glide.with(this)
            .load("https://img.icons8.com/plasticine/2x/flower.png")
            .circleCrop()
            .into(imageView)
    }

    private fun requestPermissionLocation() {
        val permissionCheck = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission Location Accepted", Toast.LENGTH_SHORT).show()
            getLongLat()
        }
            else {

            Toast.makeText(this, "Permission Location Denied", Toast.LENGTH_SHORT).show()
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 201)
        }
    }

    @SuppressLint("MissingPermission")
    fun getLongLat(){
        val locationManager =
            applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val location: Location? = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        Toast.makeText(this, "Lat: ${location?.latitude} Long : ${location?.longitude}", Toast.LENGTH_LONG).show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            201 -> {
                if (grantResults.size>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "ALLOW telah dipilih", Toast.LENGTH_LONG).show()
                    getLongLat()
                } else{
                    Toast.makeText(this, "DENY telah dipilih", Toast.LENGTH_LONG).show()
                }
            }
            else -> {
                Toast.makeText(this, "Bukan Request yang dikirm", Toast.LENGTH_LONG).show()
            }
        }

    }

}