package caios.android.pixivion.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import caios.android.pixivion.R
import caios.android.pixivion.databinding.FragmentUserBinding
import caios.android.pixivion.utils.LogUtils.TAG
import caios.android.pixivion.utils.autoCleared
import java.time.LocalDateTime

class UserFragment: Fragment(R.layout.fragment_user) {

    private var binding by autoCleared<FragmentUserBinding>()
    private var time = "null"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentUserBinding.bind(view)

        Log.d(TAG, "UserFragment onViewCreated: $time")
        time = LocalDateTime.now().toString()
    }
}