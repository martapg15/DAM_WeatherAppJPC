package dam.a51564.weatherapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import dam.a51564.weatherapp.R

class LocationPickerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var selectedLocation by remember { mutableStateOf<LatLng?>(null) }

            Column(modifier = Modifier.fillMaxSize()) {
                GoogleMap(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    onMapClick = { latLng -> selectedLocation = latLng }
                ) {
                    selectedLocation?.let {
                        Marker(state = MarkerState(position = it))
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = {
                            selectedLocation?.let {
                                val resultIntent = Intent().apply {
                                    putExtra("latitude", it.latitude.toFloat())
                                    putExtra("longitude", it.longitude.toFloat())
                                }
                                setResult(RESULT_OK, resultIntent)
                                finish()
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = selectedLocation != null
                    ) {
                        Text(stringResource(R.string.confirm_location_btn))
                    }
                }
            }
        }
    }
}