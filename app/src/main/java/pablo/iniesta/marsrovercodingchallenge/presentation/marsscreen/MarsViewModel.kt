package pablo.iniesta.marsrovercodingchallenge.presentation.marsscreen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import com.example.roverchallenge.util.Direction
import com.example.roverchallenge.util.Position
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import pablo.iniesta.marsrovercodingchallenge.data.datasource.MarsDataSource
import pablo.iniesta.marsrovercodingchallenge.domain.Rover
import javax.inject.Inject

@HiltViewModel
class MarsViewModel @Inject constructor(
    marsDataSource: MarsDataSource
) : ViewModel() {

    val parcelList = mutableStateOf<List<Boolean>>(listOf())
    val rover = mutableStateOf<Rover?>(null)
    val movements = mutableStateOf("")
    private val viewModelScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    init {
        val marsInput = marsDataSource.loadInput()
        if (marsInput != null) {
            //Create the Rover using the input data
            rover.value = Rover(
                Position(marsInput.roverPosition.x, marsInput.roverPosition.y),
                Direction.valueOf(marsInput.roverDirection.toString()),
                Position(marsInput.topRightCorner.x, marsInput.topRightCorner.y)
            )
            //Assign the movements from the input
            movements.value = marsInput.movements
            //Initialize the parcel list to false for all parcels
            parcelList.value = List(rover.value!!.getTotalParcels()) { false }
            setUpMovementProcessing()
            //occupyParcel()
        } else {
            //TODO handle error reading json
            Log.e("MarsViewModel", "Error loading data")
        }
    }

    private fun setUpMovementProcessing() {
        val movementsFlow = snapshotFlow { movements.value }

        //Every second, process a new movement to move the rover accordingly and remove the movement once processed
        movementsFlow
            .filter { it.isNotEmpty() }
            .onEach { movementsString ->
                Log.d("MarsViewModel","FLOW")
                repeat(movementsString.length) { index ->
                    Log.d("MarsViewModel", "Action: " + movements.value.first())
                    rover.value = rover.value?.move(movements.value.first())
                    movements.value = movements.value.drop(1)
                    occupyParcel()
                    Log.d("MarsViewModel", "Movement $index")
                    Log.d("MarsViewModel", "Movements $movements")
                    Log.d("MarsViewModel", "Rover $rover")
                    delay(1000L)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun occupyParcel() {
        //Set the current parcel the Rover is standing on to true following the formula: (Row - 1 - rover.y) * Columns + rover.x
        if (rover.value != null) {
            Log.d("MarsViewModel", "Current parcel index: ${rover.value!!.getCurrentParcel()}")
            parcelList.value = List(parcelList.value.size) { i ->
                i == rover.value!!.getCurrentParcel()
            }
        }
    }

    private fun Rover.getTotalParcels(): Int {
        //TotalParcels = Row x Col
        return (this.boundaries.x + 1) * (this.boundaries.y + 1)
    }

    private fun Rover.getCurrentParcel(): Int {
        return ((this.boundaries.x + 1) - 1 - this.coordinates.y) * (this.boundaries.y + 1) + this.coordinates.x
    }

}