package com.mahmoudalyudeen.plutus.di

import com.mahmoudalyudeen.plutus.di.data.DatabaseModule
import com.mahmoudalyudeen.plutus.di.data.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityBuilderModule::class,
        FragmentBuilderModule::class,
        NetworkModule::class,
        DatabaseModule::class,
        AppModule::class
    ]
)
interface AppComponent : AndroidInjector<DaggerApplication> {

    override fun inject(app: DaggerApplication)

    @Component.Factory
    interface Builder {

        fun create(@BindsInstance app: DaggerApplication): AppComponent
    }
}