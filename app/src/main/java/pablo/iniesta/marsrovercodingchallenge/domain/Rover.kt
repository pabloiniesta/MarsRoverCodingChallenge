package pablo.iniesta.marsrovercodingchallenge.domain

import pablo.iniesta.marsrovercodingchallenge.util.Direction
import pablo.iniesta.marsrovercodingchallenge.util.Position

data class Rover(
    var coordinates: Position,
    var direction: Direction,
    var boundaries: Position
) {
    fun move(movement: Char): Rover {
        val newRover = this.copy()
        when (movement) {
            'L' -> newRover.turnLeft()
            'R' -> newRover.turnRight()
            'M' -> newRover.moveForward()
        }
        return newRover
    }

    private fun moveForward() {
        when (direction) {
            Direction.N -> if (coordinates.y < boundaries.x) coordinates.y++
            Direction.S -> if (coordinates.y > 0) coordinates.y--
            Direction.E -> if (coordinates.x < boundaries.y) coordinates.x++
            Direction.W -> if (coordinates.x > 0) coordinates.x--
        }
    }

    private fun turnRight() {
        direction = when (direction) {
            Direction.N -> Direction.E
            Direction.E -> Direction.S
            Direction.S -> Direction.W
            Direction.W -> Direction.N
        }
    }

    private fun turnLeft() {
        direction = when (direction) {
            Direction.N -> Direction.W
            Direction.W -> Direction.S
            Direction.S -> Direction.E
            Direction.E -> Direction.N
        }
    }
}
