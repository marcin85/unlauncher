package com.sduduzog.slimlauncher.di

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.sduduzog.slimlauncher.models.home.HomeAppsViewModel
import com.sduduzog.slimlauncher.ui.main.HomeFragment
import javax.inject.Inject

class MainFragmentFactory @Inject constructor(private val homeAppsViewModel: HomeAppsViewModel): FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(loadFragmentClass(classLoader, className)) {
            HomeFragment::class.java -> HomeFragment(homeAppsViewModel)
            else -> super.instantiate(classLoader, className)
        }
    }
}