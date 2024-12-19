package pablo.iniesta.marsrovercodingchallenge.presentation.marsscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import pablo.iniesta.marsrovercodingchallenge.util.Direction
import pablo.iniesta.marsrovercodingchallenge.R
import pablo.iniesta.marsrovercodingchallenge.domain.Rover

@Composable
fun MarsScreen(
    viewModel: MarsViewModel = hiltViewModel()
) {
    val parcelList by remember { viewModel.parcelList }
    val rover by remember { viewModel.rover }
    val movements by remember { viewModel.movements }
    val error by remember { viewModel.error }

    if (error != null) {
        ErrorMessage(error!!)
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            MarsParcelGrid(parcelList, rover, Modifier.weight(0.75f))
            MarsStatus(rover, movements, Modifier.weight(0.25f))
        }
    }
}

@Composable
fun MarsParcelGrid(parcelList: List<Boolean>, rover: Rover?, modifier: Modifier) {
    if (rover != null) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(rover.boundaries.x + 1),
            modifier = modifier.fillMaxWidth()
        ) {
            items(parcelList) { isOccupied ->
                MarsParcel(isOccupied, rover)
            }
        }
    }
}

@Composable
fun MarsParcel(isOccupied: Boolean, rover: Rover) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(if (isOccupied) Color.Red else Color.Green),
        contentAlignment = Alignment.Center
    ) {
        if (isOccupied) {
            Image(
                painter = painterResource(rover.getRoverIcon()),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun MarsStatus(rover: Rover?, movements: String, modifier: Modifier) {
    if (rover != null) {
        Column(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
            Text(
                text = "Rover Position: (${rover.coordinates.x}, ${rover.coordinates.y}, ${rover.direction})",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = TextStyle(fontSize = 16.sp)
            )
            Text(
                text = if (movements.isEmpty()) "No more moves left" else "Rover Movements: $movements",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = TextStyle(fontSize = 16.sp)
            )
        }
    }
}

@Composable
fun ErrorMessage(message: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .background(Color.Red.copy(alpha = 0.1f))
                .border(2.dp, Color.Red)
                .padding(16.dp)
        ) {
            Text(
                text = message,
                color = Color.Red,
                style = TextStyle(textAlign = TextAlign.Center)
            )
        }
    }
}

private fun Rover.getRoverIcon(): Int {
    return when (direction) {
        Direction.N -> R.drawable.rover_icon_north
        Direction.S -> R.drawable.rover_icon_south
        Direction.E -> R.drawable.rover_icon_east
        Direction.W -> R.drawable.rover_icon_west
    }
}
