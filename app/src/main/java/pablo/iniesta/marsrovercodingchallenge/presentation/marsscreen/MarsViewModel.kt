package pablo.iniesta.marsrovercodingchallenge.presentation.marsscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import pablo.iniesta.marsrovercodingchallenge.data.datasource.MarsDataSource
import javax.inject.Inject

@HiltViewModel
class MarsViewModel @Inject constructor(
    marsDataSource: MarsDataSource
) : ViewModel() {

    init {
        val marsInput = marsDataSource.loadInput()
        Log.d("MarsViewModel", "Mars Input: $marsInput")
    }

}