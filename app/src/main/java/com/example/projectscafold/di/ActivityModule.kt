package com.example.projectscafold.di

import com.example.projectscafold.view.activities.MainActivity

import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * https://github.com/googlesamples/android-architecture-components/tree/master/GithubBrowserSample/app/src/main/java/com/android/example/github/di
 */
@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    internal abstract fun contributeMainActivity(): MainActivity

}