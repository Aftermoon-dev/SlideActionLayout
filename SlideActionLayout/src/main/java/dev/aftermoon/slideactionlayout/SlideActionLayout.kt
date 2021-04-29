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
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dev.aftermoon.slideactionlayout.exception.NotInitialException
import dev.aftermoon.slideactionlayout.fragment.ImageFragment

class SlideActionLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0): FrameLayout(context, attrs, defStyleAttr) {
    private val viewPager: ViewPager2
    private val tabLayout: TabLayout
    private var layoutParam: LayoutParams
    private var slideActionAdapter: SlideActionAdapter? = null

    /**
     * Initial Class
     */
    init {
        val view = LayoutInflater.from(context).inflate(R.layout.slideactionlayout, this, true)
        viewPager = view.findViewById(R.id.slideactionlayout_viewpager)
        tabLayout = view.findViewById(R.id.slideactionlayout_tablayout)
        layoutParam = tabLayout.layoutParams as LayoutParams
    }

    /**
     * Initializing Layout - Must be called before use SlideActionLayout
     * @param activity FragmentActivity
     */
    fun init(activity: FragmentActivity) {
        slideActionAdapter = SlideActionAdapter(activity)
        viewPager.adapter = slideActionAdapter
        TabLayoutMediator(tabLayout, viewPager) { _, _ -> {}}.attach()
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
     * Add Image Fragment
     * @param bitmap Bitmap Type Image
    */
    fun addImageFragment(bitmap: Bitmap) {
        checkInitial()
        val imageFragment = ImageFragment.newInstance(bitmap)
        slideActionAdapter!!.addFragment(imageFragment)
    }

    /**
     * Add Custom Fragment
     * @param fragment New Fragment
    */
    fun addCustomFragment(fragment: Fragment) {
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
     * Set Indicator's Gravity
     * @param rotation Rotatation Angle
     */
    fun setIndicatorRotation(rotation: Float) {
        checkInitial()
        tabLayout.rotation = rotation
    }

    /**
     * Set Indicator Color
     * @param drawable Selector Drawable (Selected -> android:state_selected="true")
     */
    fun setIndicatorColor(drawable: Drawable) {
        checkInitial()

        val tabStrip = tabLayout.getChildAt(0)  as ViewGroup

        for(i in 0..tabStrip.childCount) {
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
    fun getViewPager(): ViewPager2 {
        checkInitial()
        return viewPager
    }

    /**
     * Get Indicator (ViewPager)
     * @return TabLayout
     */
    fun getIndicator(): TabLayout {
        checkInitial()
        return tabLayout
    }
    
    /**
     * Check Initial
     */
    private fun checkInitial() {
        if(slideActionAdapter == null) {
            throw NotInitialException("SlideActionLayout is not Initial! Please Initial before Call Function!")
        }
    }
}