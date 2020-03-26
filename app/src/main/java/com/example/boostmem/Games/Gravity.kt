package com.example.boostmem.Games

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log

class Gravity(var context : Context){



    private lateinit var listener : Listener
    private var sensorManager: SensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private  var sensor: Sensor
    private  var sensorEventListener: SensorEventListener

    init {
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)
        sensorEventListener = object: SensorEventListener {

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                Log.d("change",accuracy.toString())
            }

            override fun onSensorChanged(event: SensorEvent?) {
                listener.onGravity(event!!.values[0],event.values[1],event.values[2])
            }
        }

    }

    internal fun setListener(l : Listener){
        this.listener = l
    }

    fun register(){
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }
    fun unregister(){
        sensorManager.unregisterListener(sensorEventListener)
    }
    interface Listener{
        fun onGravity(rotX:Float, rotY:Float, rotZ:Float)
    }
}

