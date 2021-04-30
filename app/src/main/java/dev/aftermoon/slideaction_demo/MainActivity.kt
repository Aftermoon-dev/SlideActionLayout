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

package dev.aftermoon.slideaction_demo

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import dev.aftermoon.slideactionlayout.SlideActionLayout
import dev.aftermoon.slideactionlayout.createindicator.CreateIndicator

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layout = findViewById<SlideActionLayout>(R.id.slideactionlayout)

        layout.setTabBackground(CreateIndicator.createCircleIndicator(this, getColor(android.R.color.holo_blue_dark), getColor(android.R.color.darker_gray)))
        layout.setIndicatorGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL)
        layout.setSlideOrientation(ViewPager2.ORIENTATION_HORIZONTAL)
        layout.setIndicatorRotation(0F)
        ContextCompat.getDrawable(this, R.drawable.first)?.let { layout.addImageFragment(it) }
        ContextCompat.getDrawable(this, R.drawable.second)?.let { layout.addImageFragment(it) }
        ContextCompat.getDrawable(this, R.drawable.third)?.let { layout.addImageFragment(it) }
        layout.addFragment(TestFragment())
    }
}