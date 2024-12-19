package pablo.iniesta.marsrovercodingchallenge.data.datasource

import pablo.iniesta.marsrovercodingchallenge.util.Direction
import pablo.iniesta.marsrovercodingchallenge.util.Position
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import pablo.iniesta.marsrovercodingchallenge.data.dto.MarsInput

class MarsInputDeserializer : JsonDeserializer<MarsInput> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: java.lang.reflect.Type?,
        context: JsonDeserializationContext?
    ): MarsInput {
        val jsonObject = json?.asJsonObject

        val topRightCorner = jsonObject?.getAsJsonObject("topRightCorner")
        val roverPosition = jsonObject?.getAsJsonObject("roverPosition")
        val roverDirection = jsonObject?.get("roverDirection")?.asString
        val movements = jsonObject?.get("movements")?.asString

        // Check for missing fields in topRightCorner
        if (topRightCorner != null) {
            if (!topRightCorner.has("x") || !topRightCorner.has("y")) {
                throw JsonParseException("Missing fields in topRightCorner")
            }
        } else throw JsonParseException("Missing topRightCorner object")

        // Check for missing fields in roverPosition
        if (roverPosition != null) {
            if (!roverPosition.has("x") || !roverPosition.has("y")) {
                throw JsonParseException("Missing fields in roverPosition")
            }
        } else throw JsonParseException("Missing roverPosition object")

        // Check if roverDirection & movements are valid
        if (roverDirection.isNullOrEmpty() || !isDirectionValid(roverDirection)) throw JsonParseException("Missing or wrong roverDirection field")
        if (movements.isNullOrEmpty()) throw JsonParseException("Missing movements field")

        // Create MarsInput object
        return MarsInput(
            topRightCorner = Position(topRightCorner.get("x").asInt, topRightCorner.get("y").asInt),
            roverPosition = Position(roverPosition.get("x").asInt, roverPosition.get("y").asInt),
            roverDirection = Direction.valueOf(roverDirection),
            movements = movements
        )
    }

    private fun isDirectionValid(direction: String): Boolean {
        return Direction.entries.any { it.name == direction }
    }
}