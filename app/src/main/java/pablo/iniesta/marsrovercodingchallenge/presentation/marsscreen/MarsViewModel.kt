package pablo.iniesta.marsrovercodingchallenge.presentation.marsscreen

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import pablo.iniesta.marsrovercodingchallenge.util.Direction
import pablo.iniesta.marsrovercodingchallenge.util.Position
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import pablo.iniesta.marsrovercodingchallenge.data.datasource.MarsDataSource
import pablo.iniesta.marsrovercodingchallenge.data.dto.MarsInput
import pablo.iniesta.marsrovercodingchallenge.domain.Rover
import javax.inject.Inject

@HiltViewModel
class MarsViewModel @Inject constructor(
    marsDataSource: MarsDataSource
) : ViewModel() {

    val parcelList = mutableStateOf<List<Boolean>>(listOf())
    val rover = mutableStateOf<Rover?>(null)
    val movements = mutableStateOf("")
    val error = mutableStateOf<String?>(null)
    private val viewModelScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    init {
        try {
            val marsInput = marsDataSource.loadInput()
            if (!isMarsInputValid(marsInput)) throw Exception("Invalid rover starting position or plateau bounds")
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
            //Start the movement processing
            setUpMovementProcessing()
        } catch (e: Exception) {
            //TODO handle error loading data
            error.value = e.message
        }
    }

    private fun setUpMovementProcessing() {
        val movementsFlow = snapshotFlow { movements.value }

        //Every second, process a new movement to move the rover accordingly and remove the movement once processed
        movementsFlow
            .filter { it.isNotEmpty() }
            .onEach { movementsString ->
                repeat(movementsString.length) { index ->
                    rover.value = rover.value?.move(movements.value.first())
                    movements.value = movements.value.drop(1)
                    occupyParcel()
                    delay(1000L)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun occupyParcel() {
        //Set the current parcel the Rover is standing on to true
        if (rover.value != null) {
            parcelList.value = List(parcelList.value.size) { i ->
                i == rover.value!!.getCurrentParcel()
            }
        }
    }

    private fun isMarsInputValid(marsInput: MarsInput): Boolean {
        //Validate that the plateau bounds are greater than 0 and the rover position is within the bounds
        if (marsInput.topRightCorner.x <= 0 || marsInput.topRightCorner.y <= 0) return false
        else if (marsInput.roverPosition.x < 0 || marsInput.roverPosition.y < 0) return false
        else if (marsInput.roverPosition.x > marsInput.topRightCorner.x || marsInput.roverPosition.y > marsInput.topRightCorner.y) return false
        return true
    }

    private fun Rover.getTotalParcels(): Int {
        //TotalParcels = Row x Col
        return (this.boundaries.x + 1) * (this.boundaries.y + 1)
    }

    private fun Rover.getCurrentParcel(): Int {
        //Current parcel index = (Row - 1 - rover.y) * Columns + rover.x
        return ((this.boundaries.x + 1) - 1 - this.coordinates.y) * (this.boundaries.y + 1) + this.coordinates.x
    }

}