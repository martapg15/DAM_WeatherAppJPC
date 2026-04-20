package dam.a51564.weatherapp.ui

// Data class that represents the app UI state
data class WeatherUiState(
    val latitude: Float = 38.709652f,
    val longitude: Float = -9.193547f,
    val temperature: Float = 0f,
    val windspeed: Float = 0f,
    val winddirection: Int = 0,
    val weathercode: Int = 0,
    val seaLevelPressure: Float = 0f,
    val time: String = ""
)