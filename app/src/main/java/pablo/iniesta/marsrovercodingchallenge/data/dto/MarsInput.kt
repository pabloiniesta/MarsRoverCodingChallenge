package pablo.iniesta.marsrovercodingchallenge.data.dto

import pablo.iniesta.marsrovercodingchallenge.util.Direction
import pablo.iniesta.marsrovercodingchallenge.util.Position

data class MarsInput(
    val topRightCorner: Position,
    val roverPosition: Position,
    val roverDirection: Direction,
    val movements: String
)
