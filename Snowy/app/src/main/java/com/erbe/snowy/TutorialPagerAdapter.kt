package com.erbe.snowy

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.erbe.snowy.model.Tutorial

class TutorialPagerAdapter(private val tutorialList: List<Tutorial>,
                           fm: FragmentManager
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int {
        return tutorialList.size
    }

    override fun getItem(position: Int): Fragment {
        return TutorialFragment.newInstance(tutorialList[position])
    }
}