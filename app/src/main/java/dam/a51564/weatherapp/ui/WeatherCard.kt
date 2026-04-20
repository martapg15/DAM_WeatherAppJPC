package dam.a51564.weatherapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dam.a51564.weatherapp.R

@Composable
fun WeatherCard(
    temperature: Float,
    windSpeed: Float,
    windDirection: Int,
    seaLevelPressure: Float,
    time: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            WeatherRow(label = stringResource(R.string.sea_level_pressure), value = "$seaLevelPressure hPa")
            WeatherRow(label = stringResource(R.string.wind_direction), value = "$windDirection°")
            WeatherRow(label = stringResource(R.string.wind_speed), value = "$windSpeed km/h")
            WeatherRow(label = stringResource(R.string.temperature), value = "$temperature°C")
            WeatherRow(label = stringResource(R.string.time), value = time)
        }
    }
}