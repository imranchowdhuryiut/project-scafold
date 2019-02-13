package com.example.projectscafold

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import com.facebook.stetho.Stetho
import com.singularitybd.sunbeam.di.DaggerAppComponent
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject


/**
 * Created by Sadman Sarar on 9/9/17.
 * Application class
 */
class App : Application(), HasActivityInjector{

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>


    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingAndroidInjector
    }


    override fun onCreate() {
        super.onCreate()
        //Instantiate Stetho
        Stetho.initializeWithDefaults(this)
        //Instantiate Dagger
        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this)


        //Register activity lifeCycle callback listener for automatically injecting every activity
        //that launches
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks() {
            override fun onActivityCreated(activity: Activity?, bundle: Bundle?) {
                try {
                    activity?.let { AndroidInjection.inject(activity) }
                } catch (ex: Exception) {
                    Log.w("APP", "No Contributor for the activity")
                }
            }
        })
    }


    abstract class ActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {
        override fun onActivityPaused(activity: Activity?) {
        }

        override fun onActivityResumed(activity: Activity?) {
        }

        override fun onActivityStarted(activity: Activity?) {
        }

        override fun onActivityDestroyed(activity: Activity?) {
        }

        override fun onActivitySaveInstanceState(activity: Activity?, bundle: Bundle?) {
        }

        override fun onActivityStopped(activity: Activity?) {
        }

        override fun onActivityCreated(activity: Activity?, bundle: Bundle?) {
        }
    }
}
