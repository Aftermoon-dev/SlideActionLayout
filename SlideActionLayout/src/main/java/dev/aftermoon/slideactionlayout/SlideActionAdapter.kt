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

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import dev.aftermoon.slideactionlayout.exception.LastFragmentException

class SlideActionAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
    private var fragments: ArrayList<Fragment> = arrayListOf()

    /*
        Get Number of current fragment list
        @return Number of current fragment list
     */
    override fun getItemCount(): Int {
        return fragments.size
    }

    /*
       Create Fragment
       @param position Fragment Position
       @return Fragment
    */
    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    /*
       Add New Fragment
       @param fragment New Fragment
    */
    fun addFragment(fragment: Fragment) {
        fragments.add(fragment)
        notifyItemInserted(fragments.size - 1)
    }

    /*
       Add New Fragment List
       @param fragList New Fragment List
    */
    fun addFragmentList(fragList: ArrayList<Fragment>) {
        fragments.addAll(fragList)
        notifyItemInserted(fragments.size - 1)
    }

    /*
       Remove Fragment
       @param position Fragment Position to be removed
    */
    fun removeFragment(position: Int) {
        // If ItemCount > 1
        if(itemCount > 1) {
            // Removed
            fragments.removeAt(position)
            notifyItemRemoved(position)
        }
        // Else
        else {
            // Throw Error
            throw LastFragmentException()
        }
    }
}