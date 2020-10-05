package com.example.vakhitov_sample.ui

import android.app.Application
import com.example.vakhitov_sample.data.db.ForecastDatabase
import com.example.vakhitov_sample.data.network.*
import com.example.vakhitov_sample.data.provider.LocationProvider
import com.example.vakhitov_sample.data.provider.LocationProviderImpl
/*import com.example.vakhitov_sample.data.provider.LocationProviderImpl*/
import com.example.vakhitov_sample.data.repository.ForecastRepository
import com.example.vakhitov_sample.data.repository.ForecastRepositoryImpl
import com.example.vakhitov_sample.ui.weather.current.CurrentWeatherViewModelFactory
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.singleton
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

class ForecastApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@ForecastApplication))

        bind() from singleton { ForecastDatabase(instance()) }
        bind() from singleton { instance<ForecastDatabase>().currentWeatherDao() }
        bind() from singleton { instance<ForecastDatabase>().currentLocationDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance())}
        bind() from singleton { WeatherStackApi(instance()) }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance())}
        bind<LocationProvider>() with singleton { LocationProviderImpl(instance()) }
        bind<ForecastRepository>() with singleton { ForecastRepositoryImpl(instance(), instance(), instance(), instance())}
        bind() from provider { CurrentWeatherViewModelFactory(instance())}
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)

    }

}