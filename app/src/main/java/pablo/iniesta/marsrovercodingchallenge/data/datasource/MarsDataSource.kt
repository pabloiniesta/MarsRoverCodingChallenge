package pablo.iniesta.marsrovercodingchallenge.data.datasource

import android.content.Context
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import pablo.iniesta.marsrovercodingchallenge.R
import pablo.iniesta.marsrovercodingchallenge.data.dto.MarsInput
import javax.inject.Inject

class MarsDataSource @Inject constructor(@ApplicationContext private val context: Context) {

    fun loadInput(): MarsInput? {
        val inputStream = context.resources.openRawResource(R.raw.rover_data_1)
        val jsonString = inputStream.bufferedReader().use { it.readText() }

        val gson = Gson()
        return gson.fromJson(jsonString, MarsInput::class.java)
    }
}