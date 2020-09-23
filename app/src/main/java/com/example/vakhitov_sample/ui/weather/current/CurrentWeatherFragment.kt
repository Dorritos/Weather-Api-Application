package com.example.vakhitov_sample.ui.weather.current

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.vakhitov_sample.R
import com.example.vakhitov_sample.data.network.ConnectivityInterceptorImpl
import com.example.vakhitov_sample.data.network.WeatherNetworkDataSourceImpl
import com.example.vakhitov_sample.data.network.WeatherStackApi
import com.example.vakhitov_sample.internal.glide.GlideApp
import com.example.vakhitov_sample.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class CurrentWeatherFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()

    private val viewModelFactory: CurrentWeatherViewModelFactory by instance()

    companion object {
        fun newInstance() =
            CurrentWeatherFragment()
    }

    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(CurrentWeatherViewModel::class.java)

        bindUI()
    }

    private fun bindUI() = launch {
        val currentWeather = viewModel.weather.await()
        currentWeather.observe(this@CurrentWeatherFragment, Observer {
            it?.let {
                group_loading.visibility = View.GONE
                updateLocation("Kazan")
                updateTemperature(it.temperature, it.feelslike)
                updatePrecip(it.precip)
                updateWind(it.windSpeed)
            }
        })

        val currentDate = viewModel.weatherDate.await()
        currentDate.observe( this@CurrentWeatherFragment, Observer {
             it?.let {
                 updateDate(it.localtime)
                 updateLocation(it.name)
             }
        })
    }

    private fun updateLocation(name: String) {
        textViewCity.text = "$name"
    }

    private fun updateDate(localtime: String) {
        textViewDate.text = "$localtime"
    }

    private fun updateTemperature(temperature: Int, feelslike: Int) {
        if (temperature > 30 || temperature < -20) {
            imageViewSunny.setImageResource(R.drawable.ic_dead)
            textViewTemp.text = "Смерть"
            textViewFeels.text = "Ощущается как: ебаный ад"
        }
        else {
            textViewTemp.text = "$temperature °C"
            textViewFeels.text = "Feels like: $feelslike °C"
        }
    }

    private fun updatePrecip(precip: Double) {
        textViewPrecip.text = "Precip: $precip" + "  mm"
    }

    private fun updateWind(windSpeed: Int) {
        textViewWind.text = "Wind: $windSpeed" + " m/s"
    }

}