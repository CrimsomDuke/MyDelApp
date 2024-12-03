package com.crimsom.mydelapp.tasks

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.crimsom.mydelapp.aux_interfaces.OnOrderUpdateStatusListener

class UpdateOrderStatusTask {

    private lateinit var onOrderUpdateStatusListener: OnOrderUpdateStatusListener;

    private var UPDATE_INTERVAL : Long = 10000L;
    private lateinit var ACTIVITY : AppCompatActivity;

    private val handler = Handler(Looper.getMainLooper()) // For posting tasks to the main thread
    private var isRunning = false // To track if the task is active

    private var periodicTask : Runnable;

    private constructor(activity: AppCompatActivity){
        this.ACTIVITY = activity;

        this.periodicTask = object : Runnable {
            override fun run() {
                if (isRunning) {

                    //we execute the fucking method
                    onOrderUpdateStatusListener.onOrderUpdateStatus();

                    // Repost the task with the specified delay
                    handler.postDelayed(this, UPDATE_INTERVAL);
                }
            }
        }

    }

    companion object{
        private var _instance : UpdateOrderStatusTask? = null;
        public fun getInstance(activity: AppCompatActivity) : UpdateOrderStatusTask{
            if(_instance == null){
                _instance = UpdateOrderStatusTask(activity);
            }
            return _instance!!;
        }
    }

    fun startTask() {
        if (isRunning) {
            Log.w("UpdateOrderStatusWithHandler", "Task is already running")
            return
        }
        isRunning = true
        handler.post(periodicTask) // Start the task immediately
    }

    fun stopTask() {
        if (!isRunning) {
            Log.w("UpdateOrderStatusWithHandler", "Task is not running")
            return
        }
        isRunning = false
        handler.removeCallbacks(periodicTask) // Stop the periodic task
        Log.i("UpdateOrderStatusWithHandler", "Task stopped")
    }

    fun setUpdateInterval(interval: Long) {
        this.UPDATE_INTERVAL = interval
    }

    fun setOnOrderUpdateStatusListener(listener: OnOrderUpdateStatusListener){
        this.onOrderUpdateStatusListener = listener;
    }

}