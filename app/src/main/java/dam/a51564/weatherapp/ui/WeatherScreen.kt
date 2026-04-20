package dam.a51564.weatherapp.ui

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import dam.a51564.weatherapp.R
import dam.a51564.weatherapp.data.WMOWeatherCode
import dam.a51564.weatherapp.data.getWeatherCodeMap
import dam.a51564.weatherapp.viewmodel.WeatherViewModel

@Composable
fun WeatherUI(weatherViewModel: WeatherViewModel = viewModel()) {
    val weatherUIState by weatherViewModel.uiState.collectAsState()
    val latitude = weatherUIState.latitude
    val longitude = weatherUIState.longitude
    val temperature = weatherUIState.temperature
    val windSpeed = weatherUIState.windspeed
    val windDirection = weatherUIState.winddirection
    val weatherCode = weatherUIState.weathercode
    val seaLevelPressure = weatherUIState.seaLevelPressure
    val time = weatherUIState.time

    val configuration = LocalConfiguration.current

    val day = true // Must change this in the future
    val mapt = getWeatherCodeMap()
    val wCode = mapt[weatherCode]
    val wImage = when (wCode) {
        WMOWeatherCode.CLEAR_SKY,
        WMOWeatherCode.MAINLY_CLEAR,
        WMOWeatherCode.PARTLY_CLOUDY -> if (day) wCode.image + "day"
        else wCode.image + "night"
        else -> wCode?.image
    }

    val context = LocalContext.current
    val wIcon = context.resources.getIdentifier(wImage, "drawable", context.packageName)

    if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        LandscapeWeatherUI(
            wIcon,
            latitude,
            longitude,
            temperature,
            windSpeed,
            windDirection,
            weatherCode,
            seaLevelPressure,
            time,
            onLatitudeChange = { newValue ->
                newValue.toFloatOrNull()?.let { weatherViewModel.updateLatitude(it) }
            },
            onLongitudeChange = { newValue ->
                newValue.toFloatOrNull()?.let { weatherViewModel.updateLongitude(it) }
            },
            onUpdateButtonClick = {
                weatherViewModel.fetchWeather()
            }
        )
    } else {
        PortraitWeatherUI(
            wIcon,
            latitude,
            longitude,
            temperature,
            windSpeed,
            windDirection,
            weatherCode,
            seaLevelPressure,
            time,
            onLatitudeChange = { newValue ->
                newValue.toFloatOrNull()?.let { weatherViewModel.updateLatitude(it) }
            },
            onLongitudeChange = { newValue ->
                newValue.toFloatOrNull()?.let { weatherViewModel.updateLongitude(it) }
            },
            onUpdateButtonClick = {
                weatherViewModel.fetchWeather()
            }
        )
    }
}

@Composable
fun PortraitWeatherUI(
    wIcon: Int,
    latitude: Float,
    longitude: Float,
    temperature: Float,
    windSpeed: Float,
    windDirection: Int,
    weathercode: Int,
    seaLevelPressure: Float,
    time: String,
    onLatitudeChange: (String) -> Unit,
    onLongitudeChange: (String) -> Unit,
    onUpdateButtonClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Adds a little space to the top, so the image isn't too close to the topbar
        Spacer(modifier = Modifier.height(16.dp))

        if (wIcon != 0) {
            Image(
                painter = painterResource(id = wIcon),
                contentDescription = stringResource(R.string.weather_icon_desc),
                modifier = Modifier.size(120.dp)
            )
        }

        CoordinatesCard(
            latitude = latitude,
            longitude = longitude,
            onLatitudeChange = onLatitudeChange,
            onLongitudeChange = onLongitudeChange
        )

        WeatherCard(
            temperature = temperature,
            windSpeed = windSpeed,
            windDirection = windDirection,
            seaLevelPressure = seaLevelPressure,
            time = time
        )

        Button(
            onClick = onUpdateButtonClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = stringResource(R.string.update_weather_btn), fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}

@Composable
fun LandscapeWeatherUI(
    wIcon: Int,
    latitude: Float,
    longitude: Float,
    temperature: Float,
    windSpeed: Float,
    windDirection: Int,
    weathercode: Int,
    seaLevelPressure: Float,
    time: String,
    onLatitudeChange: (String) -> Unit,
    onLongitudeChange: (String) -> Unit,
    onUpdateButtonClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // LEFT COLUMN: Icon, Inputs, and Button
        Column(
            modifier = Modifier
                .weight(0.5f)
                .padding(start = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (wIcon != 0) {
                Image(
                    painter = painterResource(id = wIcon),
                    contentDescription = stringResource(R.string.weather_icon_desc),
                    modifier = Modifier.size(100.dp)
                )
            }

            CoordinatesCard(
                latitude = latitude,
                longitude = longitude,
                onLatitudeChange = onLatitudeChange,
                onLongitudeChange = onLongitudeChange,
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = onUpdateButtonClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = stringResource(R.string.update_weather_btn), fontWeight = FontWeight.Bold)
            }
        }

        // RIGHT COLUMN: Weather Details
        Column(
            modifier = Modifier
                .weight(0.5f)
                .padding(end = 24.dp),
            verticalArrangement = Arrangement.Center
        ) {
            WeatherCard(
                temperature = temperature,
                windSpeed = windSpeed,
                windDirection = windDirection,
                seaLevelPressure = seaLevelPressure,
                time = time,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}