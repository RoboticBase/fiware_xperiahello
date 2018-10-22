package jp.co.tis.stc.roboticbase.core.fiware_xperiahello

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_operation.*

class OperationActivity : AppCompatActivity(), Mixin {
    private var sharedPref : SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideSystemUI()
        setContentView(R.layout.activity_operation)
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        setUpView()
        setUpButton()
    }

    private fun setUpView() {
        hideSystemUI()
    }

    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")

        val schema = sharedPref?.getBoolean("mqtt_use_ssl", false)?.let{if (it) "ssl" else "tcp"}
        val url = "${schema}://${sharedPref?.getString("mqtt_host", "")}:${sharedPref?.getString("mqtt_port", "")}"
        Log.d(TAG, "@@@@@@ ${url}")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    private fun setUpButton() {
        val intent = Intent()
        triangleButton.setOnClickListener {
            Log.d(TAG, "triangleButton.onClick")
            intent.putExtra(OPERATION_RESULT_KEY, "triangle")
            setResult(RESULT_OK, intent);
            finish()
        }
        squareButton.setOnClickListener {
            Log.d(TAG, "squareButton.onClick")
            intent.putExtra(OPERATION_RESULT_KEY, "square")
            setResult(RESULT_OK, intent);
            finish()
        }
        circleButton.setOnClickListener {
            Log.d(TAG, "circleButton.onClick")
            intent.putExtra(OPERATION_RESULT_KEY, "circle")
            setResult(RESULT_OK, intent);
            finish()
        }
        crossButton.setOnClickListener {
            Log.d(TAG, "crossButton.onClick")
            intent.putExtra(OPERATION_RESULT_KEY, "cross")
            setResult(RESULT_OK, intent);
            finish()
        }
    }
}
