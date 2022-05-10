package caios.android.pixivion.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import caios.android.pixivion.R
import caios.android.pixivion.databinding.FragmentMainBinding
import caios.android.pixivion.utils.autoCleared

class MainFragment: Fragment(R.layout.fragment_main) {

    private var binding by autoCleared<FragmentMainBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentMainBinding.bind(view)
    }
}