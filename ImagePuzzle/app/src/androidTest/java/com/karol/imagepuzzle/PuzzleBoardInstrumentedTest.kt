package com.karol.imagepuzzle

import android.R
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)

class MainActivityTest {

    @get:Rule
    public var rule =
        ActivityTestRule(MainActivity::class.java)

    @Test
    fun checkImageIdentifierGetter() {
        val activity = rule.activity
        val imageIdentifier = rule.activity.getImagesIdentifiers()
        Assert.assertNotEquals(0, imageIdentifier.count())
    }

}
