package com.tyro.weatherapplication.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.Forward
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.NotificationsActive
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Thermostat
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tyro.weatherapplication.data.ThemeMode
import com.tyro.weatherapplication.ui.components.AboutCard
import com.tyro.weatherapplication.ui.components.CustomDropDownMenu
import com.tyro.weatherapplication.ui.components.CustomTextField
import com.tyro.weatherapplication.ui.components.SettingCard
import com.tyro.weatherapplication.ui.components.ThinCheckbox
import com.tyro.weatherapplication.ui.components.ThinSwitch
import com.tyro.weatherapplication.utils.Temperature
import com.tyro.weatherapplication.viewModels.MainViewModel
import com.tyro.weatherapplication.viewModels.ThemeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
){

    val viewModel: MainViewModel = hiltViewModel()
    val themeViewModel: ThemeViewModel = hiltViewModel()


    val themeMode by themeViewModel.themeMode.collectAsState()

    val darkModeOn = themeMode == ThemeMode.DARK
    var dailyForecast by remember { mutableStateOf(true) }
    var weatherAlert by remember { mutableStateOf(true) }

    val selectedTemp = viewModel.currentTemperature
    var showTemperatureSelector by remember { mutableStateOf(false) }
    val temps = listOf(Temperature.CELSIUS, Temperature.FAHRENHEIT)

    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Outlined.Settings, contentDescription = "Calendar",
                    tint = MaterialTheme.colorScheme.primary
                )
                Text("Settings",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold
                )
            }
            Spacer(Modifier.height(24.dp))
            Text("Appearance",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.height(12.dp))
            SettingCard(Icons.Outlined.DarkMode, "Dark Mode", "Easy on the eyes"){
                ThinSwitch(checked = darkModeOn,
                    onCheckedChange = {
                        themeViewModel.toggleDarkMode()
                })
            }
            Spacer(Modifier.height(24.dp))
            Text("Notifications",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.height(12.dp))
            SettingCard(Icons.Outlined.NotificationsActive, "Weather Alert", "Severe weather warnings"){
                ThinCheckbox(checked = weatherAlert, onCheckedChange = {weatherAlert = it})
            }
            Spacer(Modifier.height(12.dp))
            SettingCard(Icons.Outlined.NotificationsActive, "Daily Forecast", "Morning weather update"){
                ThinCheckbox(checked = dailyForecast, onCheckedChange = {dailyForecast = it})
            }
            Spacer(Modifier.height(24.dp))
            Text("Unit",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.height(12.dp))
            SettingCard(Icons.Outlined.Thermostat, "Temperature", selectedTemp.name){

                IconButton(onClick = {showTemperatureSelector = true}) {
                    Icon(imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = "")
                }
                if(showTemperatureSelector){
                    BasicAlertDialog(onDismissRequest = {showTemperatureSelector = false}) {

                        Box(modifier = Modifier.width(400.dp).height(250.dp)
                            .background(color = MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(5)),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Icon(imageVector = Icons.Outlined.Close, contentDescription = "close",
                                tint = Color.Red, modifier = Modifier.align(
                                    Alignment.TopEnd).padding(20.dp))
                            Text(modifier = Modifier.align(Alignment.TopStart).padding(18.dp),
                                text = "Temperature Unit",
                                color = MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)

                            Column(modifier = Modifier.fillMaxWidth().padding(18.dp)) {
                                temps.forEach{ option ->
                                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text(option.name, color = MaterialTheme.colorScheme.onSurface)
                                        RadioButton(
                                            selected = option == selectedTemp,
                                            onClick = {
                                                viewModel.updateCurrentTemperatureType(option)
                                                showTemperatureSelector = false
                                            }
                                        )
                                    }
                                }
                            }
                            Button(onClick = {
                                showTemperatureSelector = false
                            }, modifier = Modifier.padding(bottom = 18.dp).fillMaxWidth().align(Alignment.BottomCenter).padding(horizontal = 18.dp), shape = RoundedCornerShape(30)) {
                                Text("Cancel")
                            }
                        }
                    }
                }
            }
            Spacer(Modifier.height(24.dp))
            Text("About",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.height(12.dp))
            AboutCard()
        }
    }
}