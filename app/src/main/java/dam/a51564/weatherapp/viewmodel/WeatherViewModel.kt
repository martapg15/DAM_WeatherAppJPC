package dam.a51564.weatherapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dam.a51564.weatherapp.data.WeatherApiClient
import dam.a51564.weatherapp.ui.WeatherUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    // Weather app UI state
    private val _uiState = MutableStateFlow(WeatherUiState()) // Backing property to avoid state updates from other classes
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow() // asStateFlow() makes _uiState read-only

    // Updates the latitude in the UI state
    fun updateLatitude(newLatitude: Float) {
        _uiState.update { currentState ->
            currentState.copy(latitude = newLatitude)
        }
    }

    // Updates the longitude in the UI state
    fun updateLongitude(newLongitude: Float) {
        _uiState.update { currentState ->
            currentState.copy(longitude = newLongitude)
        }
    }

    fun fetchWeather() {
        // Launch a coroutine in the ViewModel's scope to make the network request
        viewModelScope.launch{
            val data = WeatherApiClient.getWeather(
                _uiState.value.latitude,
                _uiState.value.longitude
            )

            // If the data is not null the code will be executed and call for object "weather"
            data?.let { weather ->
                val currentTime = weather.current_weather.time
                val hourIndex = weather.hourly.time.indexOf(currentTime)
                val pressure = if (hourIndex >= 0)
                    weather.hourly.pressure_msl[hourIndex].toFloat()
                else 0f // Returns 0 if it didn't find anything

                // Updates the UI state with the fetched weather data
                _uiState.update { currentState ->
                    currentState.copy(
                        temperature = weather.current_weather.temperature,
                        windspeed = weather.current_weather.windspeed,
                        winddirection = weather.current_weather.winddirection,
                        weathercode = weather.current_weather.weathercode,
                        seaLevelPressure = pressure,
                        time = currentTime
                    )
                }
            }
        }
    }
}