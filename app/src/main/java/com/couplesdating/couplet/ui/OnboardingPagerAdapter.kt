package com.couplesdating.couplet.ui

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.couplesdating.couplet.ui.register.EmailAndPasswordFragment
import com.couplesdating.couplet.ui.register.NameAndGenderFragment
import com.couplesdating.couplet.ui.register.PhotoFragment

class OnboardingPagerAdapter(container: Fragment) : FragmentStateAdapter(container) {
    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            EMAIL_AND_PASSWORD -> EmailAndPasswordFragment()
            NAME_AND_GENDER -> NameAndGenderFragment()
            PHOTO -> PhotoFragment()
            else -> EmailAndPasswordFragment()
        }
    }

    private companion object {
        private const val EMAIL_AND_PASSWORD = 0
        private const val NAME_AND_GENDER = 1
        private const val PHOTO = 2
    }
}