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

package dev.aftermoon.slideactionlayout.createindicator

import android.content.Context
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.StateListDrawable
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import dev.aftermoon.slideactionlayout.R

class CreateIndicator {
    companion object {
        /**
         * Create Circle Indicator
         * @param context Context
         * @param selectedColor Selected Circle Fill Color Value (argb)
         * @param unselectedColor Unselected Circle Fill Color Value (argb)
         */
        fun createCircleIndicator(context: Context, selectedColor: Int, unselectedColor: Int): StateListDrawable {
            val stateListDrawable = StateListDrawable()

            val dotLayout = ResourcesCompat.getDrawable(context.resources, R.drawable.selected_dot, null) as LayerDrawable
            val selectedShape = dotLayout.findDrawableByLayerId(R.id.slideactionlayout_dot1) as GradientDrawable
            selectedShape.setColor(selectedColor)
            stateListDrawable.addState(intArrayOf(android.R.attr.state_selected), selectedShape)

            val unDotLayout = ResourcesCompat.getDrawable(context.resources, R.drawable.unselected_dot, null) as LayerDrawable
            val unselectedShape = unDotLayout.findDrawableByLayerId(R.id.slideactionlayout_dot2) as GradientDrawable
            unselectedShape.setColor(unselectedColor)
            stateListDrawable.addState(intArrayOf(), unselectedShape)

            return stateListDrawable
        }
    }
}