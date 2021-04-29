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

package dev.aftermoon.slideactionlayout.fragment

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import dev.aftermoon.slideactionlayout.R

class ImageFragment: Fragment() {
    private var bitmap: Bitmap? = null

    companion object {
        fun newInstance(drawable: Drawable) = ImageFragment().apply {
            arguments = Bundle().apply {
                putParcelable("image", drawable.toBitmap())
            }
        }

        fun newInstance(bitmap: Bitmap) = ImageFragment().apply {
            arguments = Bundle().apply {
                putParcelable("image", bitmap)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_image, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            bitmap = it.getParcelable("image")

            if(bitmap != null) {
                Log.d(this.javaClass.simpleName, "Image Success")
                val imgView = view.findViewById(R.id.iv_slideactionlayoutimg) as ImageView
                imgView.setImageBitmap(bitmap)
            }
            else {
                Log.d(this.javaClass.simpleName, "Image is Null")
                throw NullPointerException()
            }
        }
    }
}