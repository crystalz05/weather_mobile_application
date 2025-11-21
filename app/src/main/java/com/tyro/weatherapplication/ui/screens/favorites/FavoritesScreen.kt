package com.tyro.weatherapplication.ui.screens.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.tyro.weatherapplication.data.room.FavoriteLocation
import com.tyro.weatherapplication.ui.components.CustomTextField
import com.tyro.weatherapplication.viewModels.FavoriteViewModel
import com.tyro.weatherapplication.viewModels.WeatherViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    weatherViewModel: WeatherViewModel,
    favoriteViewModel: FavoriteViewModel,
    navController: NavHostController
){

    val favorites by favoriteViewModel.favorites.collectAsState()

    var showSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    var location by remember { mutableStateOf("") }
    var latitude by remember { mutableStateOf("") }
    var longitude by remember { mutableStateOf("") }

    var addCoordinates by remember { mutableStateOf(false) }



    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Outlined.Favorite, contentDescription = "Calendar",
                    tint = MaterialTheme.colorScheme.primary
                )
                Text("Favourites",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold
                )
            }
            Spacer(Modifier.height(24.dp))
        }
        items(favorites) { favorite ->
            FavoriteCards(weatherViewModel = weatherViewModel,favoriteViewModel, favorite, navController)
        }
        item {
            Button(onClick = {showSheet = true}, modifier = Modifier.fillMaxWidth().padding(top = 16.dp), shape = RoundedCornerShape(8.dp)) {
                Row(modifier = Modifier.padding(4.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(imageVector = Icons.Outlined.Add, tint = MaterialTheme.colorScheme.onPrimary, contentDescription = "Add favorite")
                    Text("Add Location", color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.ExtraBold, style = MaterialTheme.typography.titleMedium)
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){

        if(addCoordinates){
            BasicAlertDialog(onDismissRequest = {addCoordinates = false}) {

                Box(modifier = Modifier.width(400.dp).height(300.dp)
                    .background(color = MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(5)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = Icons.Outlined.Close, contentDescription = "close",
                        tint = Color.Red, modifier = Modifier.clickable(onClick = {addCoordinates = false}).align(
                        Alignment.TopEnd).padding(20.dp))
                    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Enter the coordinates",
                            color = MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(18.dp))
                        //Text Field for latitude
                        CustomTextField(location = latitude.toString(),
                            onValueChange = {newValue -> latitude = newValue.filter { it.isDigit() || it == '.' }.take(12)},
                            keyboard = KeyboardOptions(keyboardType = KeyboardType.Number),
                            placeholder = "Latitude"
                        )
                        Spacer(Modifier.height(18.dp))
                        //Text Field for latitude
                        CustomTextField(location = longitude.toString(),
                            onValueChange = {newValue -> longitude = newValue.filter { it.isDigit() || it == '.' }},
                            keyboard = KeyboardOptions(keyboardType = KeyboardType.Number),
                            placeholder = "Longitude"
                        )
                        Spacer(Modifier.height(18.dp))
                        Button(onClick = {addCoordinates = false}, modifier = Modifier.fillMaxWidth().padding(horizontal = 40.dp), shape = RoundedCornerShape(30)) {
                            Text("Add Coordinates")
                        }
                    }
                }
            }
        }

        if(showSheet){
            ModalBottomSheet(
                onDismissRequest = {showSheet = false},
                shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
                sheetState = sheetState,
                containerColor = MaterialTheme.colorScheme.surface,
                dragHandle = {
                }
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                ) {
                    Row(modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Absolute.SpaceBetween) {
                        Text("Add Location", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        IconButton(onClick = {showSheet = false}) {
                            Icon(imageVector = Icons.Outlined.Close, contentDescription = "Close", tint = MaterialTheme.colorScheme.error)
                        }
                    }

                    Spacer(Modifier.height(24.dp))
                    Text("Location Name")
                    Spacer(Modifier.height(8.dp))
                    Box(
                        modifier = Modifier.fillMaxWidth().height(44.dp)
                            .clip(RoundedCornerShape(100))
                            .background(color = MaterialTheme.colorScheme.surfaceDim.copy(0.8f))
                            .border(width = 1.dp, shape = RoundedCornerShape(100), color = MaterialTheme.colorScheme.onBackground.copy(0.2f))
                    ){
                        //The text field to accept location
                        BasicTextField(
                            modifier = Modifier.align(Alignment.Center).fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            value = location,
                            keyboardOptions = KeyboardOptions.Default.copy(capitalization = KeyboardCapitalization.Sentences),
                            textStyle = TextStyle.Default.copy(color = MaterialTheme.colorScheme.onSurface),
                            onValueChange = {location = it},
                            singleLine = true,
                        )
                        //adding coordinates
                        Button(onClick = {addCoordinates = true}, Modifier.align(Alignment.CenterEnd).padding(end = 2.dp)) { Text("Add Coordinates") }
                    }
                    Spacer(Modifier.height(12.dp))
                    Text("Coordinates: Lat: $latitude | Lon: $longitude", color = Color.Green)
                    Spacer(Modifier.height(24.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(
                            shape = RoundedCornerShape(16.dp),
                            onClick = { showSheet = false },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
                        ) {
                            Text("Cancel", color = Color.White)
                        }
                        Button(
                            shape = RoundedCornerShape(16.dp),
                            onClick = {
                                favoriteViewModel.add(FavoriteLocation(name = location, latitude = latitude.toDouble(), longitude = longitude.toDouble()))
                                showSheet = false
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Add")
                        }
                    }
                }
            }
        }

    }
}