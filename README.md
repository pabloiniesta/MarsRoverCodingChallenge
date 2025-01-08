
# Mars Rover - Android coding challenge

A native Android solution for the Mars Rover coding challenge using Kotlin, Jetpack Compose, MVVM architecture & dependency injection with hilt.


## Challenge description

A robotic rover is to be landed by NASA on a plateau on Mars. This plateau, which is curiously
rectangular, must be navigated by the rovers so that their on-board cameras can get a complete
view of the surrounding terrain to send back to Earth.

A rover's position and location is represented by a combination of x and y co-ordinates and a letter
representing one of the four cardinal compass points. The plateau is divided up into a grid to
simplify navigation. An example position might be 0, 0, N, which means the rover is in the bottom
left corner and facing North.

In order to control a rover, NASA sends a simple string of letters. The possible letters are 'L', 'R' and
'M'. 'L' and 'R' makes the rover spin 90 degrees left or right, respectively, without moving from its
current spot. 'M' means move forward one grid point, and maintain the same heading. If the rover
tries to move and is heading to the limit of the plateau, It won’t move.

Assume that the square directly North from (x, y) is (x, y+1).
## Input

To ease the processing of the plateau creation and reading the rover’s movements, all data will be sent in JSON format:
- topRightCorner: indicates the upper-right coordinates of the plateau. 
- roverPosition: Indicates the rover initial position inside the plateau
- roverDirection: Indicates the rover initial heading
- movements: Instructions telling the rover how to explore the plateau
## Output

The output for the rover should be its final co-ordinates and heading, separated by spaces.
## Example

Input:
```
{
 "topRightCorner": {
 "x": 5,
 "y": 5
 },
 "roverPosition": {
 "x": 1,
 "y": 2
 },
 "roverDirection": "N",
 "movements": "LMLMLMLMM"
}

```
Expected output: 1 3 N
