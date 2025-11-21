package com.tyro.weatherapplication.ui.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.NotificationsActive
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Thermostat
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tyro.weatherapplication.data.ThemeMode
import com.tyro.weatherapplication.ui.components.AboutCard
import com.tyro.weatherapplication.ui.components.CustomDropDownMenu
import com.tyro.weatherapplication.ui.components.SettingCard
import com.tyro.weatherapplication.ui.components.ThinCheckbox
import com.tyro.weatherapplication.ui.components.ThinSwitch
import com.tyro.weatherapplication.viewModels.ThemeViewModel

@Composable
fun SettingsScreen(
){

    val themeViewModel: ThemeViewModel = hiltViewModel()


    val themeMode by themeViewModel.themeMode.collectAsState()

    val darkModeOn = themeMode == ThemeMode.DARK

    var notification by remember { mutableStateOf(true) }
    var dailyForecast by remember { mutableStateOf(true) }
    var weatherAlert by remember { mutableStateOf(true) }

    val temperatures = listOf("°C", "°F")
    var selectedTemperature by rememberSaveable { mutableStateOf(temperatures[0]) }

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
            SettingCard(Icons.Outlined.Thermostat, "Temperature", "Fahrenheit"){
                CustomDropDownMenu(temperatures, selectedTemperature){
                    selectedTemperature = it
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