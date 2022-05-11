package caios.android.pixivion.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import caios.android.pixivion.R
import caios.android.pixivion.databinding.FragmentTrendBinding
import caios.android.pixivion.utils.autoCleared

class TrendFragment: Fragment(R.layout.fragment_trend) {

    private var binding by autoCleared<FragmentTrendBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentTrendBinding.bind(view)
    }
}