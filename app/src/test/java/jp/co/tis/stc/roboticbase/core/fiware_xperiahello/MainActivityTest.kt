package jp.co.tis.stc.roboticbase.core.fiware_xperiahello

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import kotlinx.android.synthetic.main.activity_main.*
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config
import java.util.concurrent.ScheduledFuture

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [(Build.VERSION_CODES.O)])
class MainActivityTest {

    private fun getPrivateProperty(activity: Activity, name: String): Any? {
        return activity::class.java.declaredFields.find { it.name == name }?.let {
            it.isAccessible = true
            it.get(activity)
        }
    }

    @Test
    fun testLyfecycle() {
        val controller = Robolectric.buildActivity(MainActivity::class.java)
        val activityOnCreated = controller.create().get()
        assertNotNull(activityOnCreated.supportActionBar)
        assertTrue(activityOnCreated.mainButton.hasOnClickListeners())

        assertFalse(getPrivateProperty(activityOnCreated, "isMove") as Boolean)
        assertFalse(getPrivateProperty(activityOnCreated, "isMotion") as Boolean)
        assertFalse(getPrivateProperty(activityOnCreated, "isDemo") as Boolean)
        assertFalse(getPrivateProperty(activityOnCreated, "isSpeak") as Boolean)
        assertFalse(getPrivateProperty(activityOnCreated, "willEyeClose") as Boolean)
        assertNull(getPrivateProperty(activityOnCreated, "scheduler"))
        assertNull(getPrivateProperty(activityOnCreated, "future"))
        assertNull(getPrivateProperty(activityOnCreated, "mAPI"))
        assertEquals(0, activityOnCreated.window.decorView.systemUiVisibility)

        val activityOnResumed = controller.start().resume().get()
        assertNotNull(activityOnResumed.supportActionBar)
        assertFalse(getPrivateProperty(activityOnResumed, "isMove") as Boolean)
        assertFalse(getPrivateProperty(activityOnResumed, "isMotion") as Boolean)
        assertFalse(getPrivateProperty(activityOnResumed, "isDemo") as Boolean)
        assertFalse(getPrivateProperty(activityOnResumed, "isSpeak") as Boolean)
        assertFalse(getPrivateProperty(activityOnResumed, "willEyeClose") as Boolean)
        assertNotNull(getPrivateProperty(activityOnResumed, "scheduler"))
        assertNotNull(getPrivateProperty(activityOnResumed, "future"))
        assertNull(getPrivateProperty(activityOnResumed, "mAPI"))
        assertEquals(0, activityOnResumed.window.decorView.systemUiVisibility)

        val activityVisibled = controller.visible().get()
        activityVisibled.onWindowFocusChanged(true)

        assertEquals(3846, activityVisibled.window.decorView.systemUiVisibility)
    }

    @Test
    fun testStartSettings() {
        val activity = Robolectric.setupActivity(MainActivity::class.java)
        val future = getPrivateProperty(activity, "future") as ScheduledFuture<*>
        future.cancel(true)

        val shadow = shadowOf(activity)
        shadow.clickMenuItem(R.id.settings)
        val expected = Intent(activity, SettingsActivity::class.java)
        val actual = shadowOf(ApplicationProvider.getApplicationContext<Application>()).nextStartedActivity
        assertEquals(expected.component, actual.component)
        assertFalse(getPrivateProperty(activity, "isMove") as Boolean)
        assertFalse(getPrivateProperty(activity, "isMotion") as Boolean)
        assertTrue(getPrivateProperty(activity, "isDemo") as Boolean)
        assertFalse(getPrivateProperty(activity, "isSpeak") as Boolean)
        assertFalse(getPrivateProperty(activity, "willEyeClose") as Boolean)
    }

    @Test
    fun testCancelSettings() {
        val activity = Robolectric.setupActivity(MainActivity::class.java)
        val future = getPrivateProperty(activity, "future") as ScheduledFuture<*>
        future.cancel(true)

        val shadow = shadowOf(activity)
        shadow.clickMenuItem(R.id.cancel)
        val actual = shadowOf(ApplicationProvider.getApplicationContext<Application>()).nextStartedActivity
        assertNull(actual)
        assertFalse(getPrivateProperty(activity, "isMove") as Boolean)
        assertFalse(getPrivateProperty(activity, "isMotion") as Boolean)
        assertFalse(getPrivateProperty(activity, "isDemo") as Boolean)
        assertFalse(getPrivateProperty(activity, "isSpeak") as Boolean)
        assertFalse(getPrivateProperty(activity, "willEyeClose") as Boolean)
    }

    @Test
    fun testClickMainButton() {
        val activity = Robolectric.setupActivity(MainActivity::class.java)
        val future = getPrivateProperty(activity, "future") as ScheduledFuture<*>
        future.cancel(true)

        activity.mainButton.performClick()
        val expected = Intent(activity, OperationActivity::class.java)
        val actual = shadowOf(ApplicationProvider.getApplicationContext<Application>()).nextStartedActivity
        assertEquals(expected.component, actual.component)
        assertFalse(getPrivateProperty(activity, "isMove") as Boolean)
        assertFalse(getPrivateProperty(activity, "isMotion") as Boolean)
        assertTrue(getPrivateProperty(activity, "isDemo") as Boolean)
        assertTrue(getPrivateProperty(activity, "isSpeak") as Boolean)
        assertFalse(getPrivateProperty(activity, "willEyeClose") as Boolean)
    }
}