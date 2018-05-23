package com.firebase.ginggingi.animationgesturetestapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.util.Log
import android.view.GestureDetector
import android.view.Gravity
import android.view.MotionEvent
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), GestureDetector.OnGestureListener {

    lateinit var gd: GestureDetector
    var layx = 0
    var movement = 0
    var PMValue = 3
    val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)

    val metrics = DisplayMetrics()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.windowManager.defaultDisplay.getMetrics(metrics)
        gd = GestureDetector(this, this)
        Txv.setText("Gesture Test")
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_UP) {
            Txv.setText("TouchUp")
            layx += movement
            Log.i("layx", "$layx , moved")

            val thread = Thread(Runnable {
                Thread.sleep(1)
                layx = PMValue
                params.leftMargin = layx
                if (layx < metrics.widthPixels || layx < 400)
                    Log.i("Thread", "$layx and $PMValue")
            })
            if (layx < 400) {
                PMValue = 400
                while (layx < 400) {
                    thread.run()
                    this.runOnUiThread {
                        Txv.layoutParams = params
                    }
                }
            } else if (layx > 900) {
                PMValue = metrics.widthPixels
                while (layx < metrics.widthPixels) {
                    thread.run()
                    this.runOnUiThread {
                        Txv.layoutParams = params
                        Txv.requestLayout()
                    }
                }
            }
        }
        return gd.onTouchEvent(event)
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return true
    }

    override fun onDown(e: MotionEvent?): Boolean {
        return true
    }

    override fun onLongPress(e: MotionEvent?) {}

    override fun onShowPress(e: MotionEvent?) {}

    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
        return true
    }

    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
//        Txv.setText("${e1?.x}\n , ${e2?.x}\n , $distanceX,\n $distanceY\n")

//        if (e1?.x!!.toInt() > e2?.x!!.toInt()) {
            movement = e2?.x!!.toInt() - e1?.x!!.toInt()
            Txv.setText("move : $movement")
            params.setMargins(layx + movement, 0, 0, 0)

//            Log.i("Move", "$layx : $movement moved ${e1?.x!!.toInt()}, ${e2?.x!!.toInt()} ")
            params.gravity = Gravity.CENTER_VERTICAL
            Txv.layoutParams = params
//        }
        return true
    }
}
