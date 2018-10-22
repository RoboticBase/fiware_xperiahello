package jp.co.tis.stc.roboticbase.core.fiware_xperiahello

import android.content.Intent
import android.graphics.Rect
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), Mixin {
    private var animSlideUp : Animation? = null
    private var animSlideDown : Animation? = null
    private val statusBarHeight : Int by lazy {
        val rect = Rect()
        window.decorView.getWindowVisibleDisplayFrame(rect)
        rect.top
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setUpView()
        setUpButton()
    }

    private fun setUpView() {
        animSlideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up)
        animSlideDown = AnimationUtils.loadAnimation(this, R.anim.slide_down)

        window.decorView.setOnSystemUiVisibilityChangeListener { visibility: Int ->
            if ((visibility and View.SYSTEM_UI_FLAG_FULLSCREEN) == View.VISIBLE) {
                toolbar?.startAnimation(animSlideDown)
                supportActionBar?.show()
            } else {
                toolbar?.startAnimation(animSlideUp)
                supportActionBar?.hide()
            }
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        toolbar.setTranslationY(statusBarHeight.toFloat())
        if (hasFocus) hideSystemUI()
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
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menuInflater.inflate(R.menu.settings, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) : Boolean {
        when (item.itemId) {
            R.id.settings -> {
                val intent = Intent(application, SettingsActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUpButton() {
        mainButton.setOnClickListener {
            Log.d(TAG, "mainButton.onClick")
            val intent = Intent(application, OperationActivity::class.java)
            startActivityForResult(intent, OPERATION_BACK_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == OPERATION_BACK_CODE && resultCode == RESULT_OK) {
            Log.d(TAG, "!!! result ${data?.getStringExtra(OPERATION_RESULT_KEY)}")
            val message = when (data?.getStringExtra(OPERATION_RESULT_KEY)) {
                "triangle" -> {
                    "誘導ロボットを三角に動かします"
                }
                "square" -> {
                    "誘導ロボットを四角く動かします"
                }
                "circle" -> {
                    "誘導ロボットを丸く動かします"
                }
                "cross" -> {
                    "誘導ロボットを停止します"
                }
                else -> {
                    ""
                }
            }
            Log.d(TAG, "!!! msg=${message}")
        }
    }
}
