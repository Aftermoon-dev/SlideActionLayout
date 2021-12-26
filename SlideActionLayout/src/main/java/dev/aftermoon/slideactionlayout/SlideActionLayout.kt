/*
 * Copyright (C) 2021 Aftermoon-dev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.aftermoon.slideactionlayout

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dev.aftermoon.slideactionlayout.exception.NotInitialException
import dev.aftermoon.slideactionlayout.fragment.ImageFragment
import java.lang.IllegalArgumentException

class SlideActionLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {
    companion object {
        private var autoSlideHandler: Handler? = null
    }

    private val viewPager: ViewPager2
    private val tabLayout: TabLayout
    private var layoutParam: LayoutParams
    private var slideActionAdapter: SlideActionAdapter? = null
    private var autoSlideEnabled: Boolean = false
    private var autoSlidePeriod: Long? = null

    /**
     * Initial Class
     */
    init {
        // Initializing Variables
        val view = LayoutInflater.from(context).inflate(R.layout.slideactionlayout, this, true)
        viewPager = view.findViewById(R.id.slideactionlayout_viewpager)
        tabLayout = view.findViewById(R.id.slideactionlayout_tablayout)
        layoutParam = tabLayout.layoutParams as LayoutParams

        // If Context is FragmentActivity or its subclass
        if (context is FragmentActivity) {
            // Initializing Adapter
            slideActionAdapter = SlideActionAdapter(context)
            viewPager.adapter = slideActionAdapter

            // Set TabLayout
            TabLayoutMediator(tabLayout, viewPager) { _, _ -> {} }.attach()

            // Get Attributes
            context.theme.obtainStyledAttributes(attrs, R.styleable.SlideActionLayout, 0, 0).apply {
                try {
                    // Slide Orientation
                    val orientation = getInteger(R.styleable.SlideActionLayout_slideOrientation, 0)

                    // Zero is Horizontal, One is Vertical
                    if (orientation == 0) {
                        setSlideOrientation(ViewPager2.ORIENTATION_HORIZONTAL)
                    } else if (orientation == 1) {
                        setSlideOrientation(ViewPager2.ORIENTATION_VERTICAL)
                    }

                    // Show Indicator
                    val showIndicator = getBoolean(R.styleable.SlideActionLayout_showIndicator, true)

                    if (showIndicator) {
                        // Indicator Gravity
                        val indicatorGravity = getInteger(R.styleable.SlideActionLayout_indicatorGravity, Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL)
                        setIndicatorGravity(indicatorGravity)

                        // Indicator Rotation
                        val indicatorRotation = getFloat(R.styleable.SlideActionLayout_indicatorRotation, 0F)
                        setIndicatorRotation(indicatorRotation)

                        // Indicator Background Color
                        val indicatorBackground = getColor(R.styleable.SlideActionLayout_indicatorBackgroundColor, Color.TRANSPARENT)
                        setIndicatorBackgroundColor(indicatorBackground)
                    }

                    // TabLayout Background
                    val tabLayoutBackground = getDrawable(R.styleable.SlideActionLayout_tabLayoutBackground)
                    if (tabLayoutBackground != null) setTabBackground(tabLayoutBackground)
                } finally {
                    recycle()
                }
            }
        } else {
            throw IllegalArgumentException()
        }
    }


    /**
     * Add Image Fragment
     * @param drawable Drawable Type Image
     */
    fun addImageFragment(drawable: Drawable) {
        checkInitial()
        val imageFragment = ImageFragment.newInstance(drawable)
        slideActionAdapter!!.addFragment(imageFragment)
    }

    /**
     * Add Image Fragment with Image Scale Type
     * @param drawable Drawable Type Image
     * @param scaleType ImageView ScaleType
     */
    fun addImageFragment(drawable: Drawable, scaleType: ImageView.ScaleType) {
        checkInitial()
        val imageFragment = ImageFragment.newInstance(drawable, scaleType)
        slideActionAdapter!!.addFragment(imageFragment)
    }

    /**
     * Add Image Fragments
     * @param drawables Drawables ArrayList
     */
    fun addDrawableFragments(drawables: ArrayList<Drawable>) {
        checkInitial()

        for (i in 0..drawables.size) {
            slideActionAdapter!!.addFragment(ImageFragment.newInstance(drawables[i]))
        }
    }


    /**
     * Add Image Fragments with Image Scale Type
     * @param drawables Drawables ArrayList
     * @param scaleType ImageView ScaleType
     */
    fun addDrawableFragments(drawables: ArrayList<Drawable>, scaleType: ImageView.ScaleType) {
        checkInitial()

        for (i in 0..drawables.size) {
            slideActionAdapter!!.addFragment(ImageFragment.newInstance(drawables[i], scaleType))
        }
    }

    /**
     * Add Image Fragment
     * @param bitmap Bitmap Type Image
     */
    fun addImageFragment(bitmap: Bitmap) {
        checkInitial()
        val imageFragment = ImageFragment.newInstance(bitmap)
        slideActionAdapter!!.addFragment(imageFragment)
    }

    /**
     * Add Image Fragment with Image Scale Type
     * @param bitmap Bitmap Type Image
     * @param scaleType ImageView ScaleType
     */
    fun addImageFragment(bitmap: Bitmap, scaleType: ImageView.ScaleType) {
        checkInitial()
        val imageFragment = ImageFragment.newInstance(bitmap, scaleType)
        slideActionAdapter!!.addFragment(imageFragment)
    }

    /**
     * Add Bitmap Fragments
     * @param bitmaps Bitmap ArrayList
     */
    fun addBitmapFragments(bitmaps: ArrayList<Bitmap>) {
        checkInitial()

        for (i in 0..bitmaps.size) {
            slideActionAdapter!!.addFragment(ImageFragment.newInstance(bitmaps[i]))
        }
    }

    /**
     * Add Bitmap Fragments with Scale Type
     * @param bitmaps Bitmap ArrayList
     * @param scaleType ImageView ScaleType
     */
    fun addBitmapFragments(bitmaps: ArrayList<Bitmap>, scaleType: ImageView.ScaleType) {
        checkInitial()

        for (i in 0..bitmaps.size) {
            slideActionAdapter!!.addFragment(ImageFragment.newInstance(bitmaps[i], scaleType))
        }
    }

    /**
     * Add Fragment
     * @param fragment New Fragment
     */
    fun addFragment(fragment: Fragment) {
        checkInitial()
        slideActionAdapter!!.addFragment(fragment)
    }

    /**
     * Remove Fragment by Position
     * @param pos Fragment Position to be removed
     */
    fun removeFragment(pos: Int) {
        checkInitial()
        slideActionAdapter!!.removeFragment(pos)
    }

    /**
     * Set Slide Orientation
     * @param orientation ViewPager2 Orientation Type (ViewPager2.ORIENTATION_HORIZONTAL or ViewPager2.ORIENTATION_VERTICAL)
     */
    fun setSlideOrientation(orientation: Int) {
        checkInitial()
        viewPager.orientation = orientation
    }

    /**
     * Set Indicator Background Color
     * @param color Background Color
     */
    fun setIndicatorBackgroundColor(color: Int) {
        checkInitial()
        tabLayout.setBackgroundColor(color)
    }

    /**
     * Set Indicator's Gravity
     * @param gravity Gravity Type
     */
    fun setIndicatorGravity(gravity: Int) {
        checkInitial()
        layoutParam.gravity = gravity
        tabLayout.layoutParams = layoutParam
    }

    /**
     * Set Indicator's Rotation
     * @param rotation Rotation Angle
     */
    fun setIndicatorRotation(rotation: Float) {
        checkInitial()
        tabLayout.rotation = rotation
    }

    /**
     * Set Tab Background
     * @param drawable Selector Drawable (Selected -> android:state_selected="true")
     * You can make Circle Tab Indicator Drawable using CreateIndicator Class
     */
    fun setTabBackground(drawable: Drawable) {
        checkInitial()

        val tabStrip = tabLayout.getChildAt(0) as ViewGroup

        for (i in 0..tabStrip.childCount) {
            val tabView = tabStrip.getChildAt(i)
            if (tabView != null) {
                val paddingLeft = tabView.paddingLeft
                val paddingTop = tabView.paddingTop
                val paddingRight = tabView.paddingRight
                val paddingBottom = tabView.paddingBottom
                ViewCompat.setBackground(tabView, drawable)
                ViewCompat.setPaddingRelative(tabView, paddingLeft, paddingTop, paddingRight, paddingBottom)
            }
        }
    }

    /**
     * Get Slide (ViewPager)
     * @return ViewPager2
     */
    fun getViewPager(): ViewPager2 = viewPager

    /**
     * Get Indicator (ViewPager)
     * @return TabLayout
     */
    fun getIndicator(): TabLayout = tabLayout

    /**
     * Set Auto Slide
     * @param period Auto Slide Period
     * @param startNow Whether to start right away
     */
    @Synchronized
    fun setAutoSlide(period: Long, startNow: Boolean) {
        checkInitial()

        if (autoSlideHandler == null) {
            synchronized(SlideActionLayout::class) {
                autoSlideHandler = Handler(Looper.getMainLooper())
            }
        }

        autoSlidePeriod = period
        setEnableAutoSlide(startNow)
    }

    /**
     * Enable / Disable Auto Slide
     * @param enabled Whether Enabled or Disabled Auto Slide
     */
    fun setEnableAutoSlide(enabled: Boolean) {
        autoSlideEnabled = enabled

        if(enabled) {
            autoSlideHandler!!.postDelayed(object : Runnable {
                override fun run() {
                    if(slideActionAdapter != null && autoSlideEnabled) {
                        if(viewPager.currentItem < slideActionAdapter!!.itemCount - 1) {
                            viewPager.currentItem = viewPager.currentItem + 1
                        }
                        else {
                            viewPager.currentItem = 0
                        }

                        autoSlideHandler!!.postDelayed(this, autoSlidePeriod!!)
                    }
                }
            }, autoSlidePeriod!!)
        }
        else {
            if (autoSlideHandler != null) {
                autoSlideHandler!!.removeCallbacksAndMessages(null)
            }
        }
    }

    /**
     * Get Current State of Auto Slide
     * @return Enabled or Disabled Auto Slide
     */
    fun isEnableAutoSlide(): Boolean = autoSlideEnabled

    /**
     * Check Initial
     */
    private fun checkInitial() {
        if (slideActionAdapter == null) {
            throw NotInitialException("SlideActionLayout is not Initial!")
        }
    }
}