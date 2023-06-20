package com.soltys.cookingbookmobile

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.soltys.cookingbookmobile.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), SensorEventListener {

  var sensorManager:SensorManager?=null
  var sensor:Sensor?=null

  var nightStyle:Boolean = false

  private lateinit var appBarConfiguration: AppBarConfiguration
  private lateinit var binding: ActivityMainBinding



  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    setSupportActionBar(binding.toolbar)

    sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
    sensor = sensorManager?.getDefaultSensor(Sensor.TYPE_LIGHT)

    val navController = findNavController(R.id.nav_host_fragment_content_main)
    appBarConfiguration = AppBarConfiguration(navController.graph)
    setupActionBarWithNavController(navController, appBarConfiguration)
  }

  override fun onResume() {
    super.onResume()
    sensorManager?.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
  }

  override fun onPause() {
    super.onPause()
    sensorManager?.unregisterListener(this)
  }

  override fun onSupportNavigateUp(): Boolean {
    val navController = findNavController(R.id.nav_host_fragment_content_main)
    return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
  }

  override fun onSensorChanged(event: SensorEvent?) {
    println(event!!.values[0])
    if (event!!.values[0] < 15000 && !nightStyle) {
      AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
      nightStyle = true
    }
    else if (event!!.values[0] >= 15000 && nightStyle) {
      AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
      nightStyle = false
    }
  }

  override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
  }
}
