package jp.co.tis.stc.boboticbase.core.fiware_xperiahello

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

class MainActivity : AppCompatActivity() {
    private var animSlideUp : Animation? = null
    private var animSlideDown : Animation? = null

    private val statusBarHeight : Int
        get() {
            val rect = Rect()
            window.decorView.getWindowVisibleDisplayFrame(rect)
            return rect.top
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menuInflater.inflate(R.menu.settings, menu)
        return true
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

    override fun onOptionsItemSelected(item: MenuItem) : Boolean {
        Log.d("xxxxx", item.toString())
        return true
    }
}
