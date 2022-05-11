package caios.android.pixivion.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import caios.android.pixivion.R
import caios.android.pixivion.databinding.FragmentHomeBinding
import caios.android.pixivion.utils.LogUtils
import caios.android.pixivion.utils.autoCleared
import java.time.LocalDateTime

class HomeFragment: Fragment(R.layout.fragment_home) {

    private var binding by autoCleared<FragmentHomeBinding>()
    private var time = "null"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentHomeBinding.bind(view)

        Log.d(LogUtils.TAG, "HomeFragment onViewCreated: $time")
        time = LocalDateTime.now().toString()
    }
}