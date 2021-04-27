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

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable

class CreateIndicator {
    companion object {
        /*
            Create Circle Indicator
            @param selectedColor Selected Color Code (Hex) - ex. #FFFFFF
            @param unselectedColor Unselected Color Code (Hex) - ex. #FFFFFF
         */
        fun createCircleIndicator(selectedColor: String, unselectedColor: String): StateListDrawable {
            val stateListDrawable = StateListDrawable()

            val selectedShape = GradientDrawable()
            selectedShape.shape = GradientDrawable.RING
            selectedShape.setColor(Color.parseColor(selectedColor))

            val unselectedShape = GradientDrawable()
            unselectedShape.shape = GradientDrawable.RING
            unselectedShape.setColor(Color.parseColor(unselectedColor))

            stateListDrawable.addState(intArrayOf(android.R.attr.state_pressed), selectedShape)
            stateListDrawable.addState(intArrayOf(), unselectedShape)

            return stateListDrawable
        }
    }
}