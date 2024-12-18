package pablo.iniesta.marsrovercodingchallenge.data.dto

import com.example.roverchallenge.util.Direction
import com.example.roverchallenge.util.Position

data class MarsInput(
    val topRightCorner: Position,
    val roverPosition: Position,
    val roverDirection: Direction,
    val movements: String
)
