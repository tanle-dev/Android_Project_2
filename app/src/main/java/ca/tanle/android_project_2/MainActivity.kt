package ca.tanle.android_project_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import android.Manifest
import android.widget.Button
import androidx.core.app.ActivityCompat
import ca.tanle.android_project_2.utils.LocationUtils

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.my_toolbar))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection.
        return when (item.itemId) {
            R.id.home -> {
                Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
                changeScreen(HomeFragment())
                true
            }
            R.id.map -> {
                Toast.makeText(this, "Map", Toast.LENGTH_SHORT).show()
                locationPermissionRequest.launch(arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ))
                changeScreen(MapFragment(this))
                true
            }
            R.id.places -> {
                Toast.makeText(this, "Places", Toast.LENGTH_SHORT).show()
                changeScreen(PlacesFragment())
                true
            }
            R.id.about -> {
                Toast.makeText(this, "About", Toast.LENGTH_SHORT).show()
                changeScreen(ShareFragment())
                true
            }
            R.id.share -> {
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show()
                changeScreen(AboutFragment())
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun changeScreen(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment)
        fragmentTransaction.commit()
    }

    val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if(permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
            && permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
            ){

        }else{
            // Ask for permission
            val rationaleRequired = ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) || ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )

            if(rationaleRequired){
                Toast.makeText(this, "Location request", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Permission Deny please grant it", Toast.LENGTH_SHORT).show()
            }
        }
    }
}