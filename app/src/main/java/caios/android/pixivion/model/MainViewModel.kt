package caios.android.pixivion.model

import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    val headerHeight by lazy { MutableLiveData<Int>() }
    val footerHeight by lazy { MutableLiveData<Int>() }
    val searchBarHeight by lazy { MutableLiveData<Int>() }
    val scrollQuantity = mutableMapOf<String, Parcelable?>()

    init {
        headerHeight.value = 0
        footerHeight.value = 0
        searchBarHeight.value = 0
    }
}