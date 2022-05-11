package caios.android.pixivion.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import caios.android.pixivion.R
import caios.android.pixivion.databinding.FragmentNewlyBinding
import caios.android.pixivion.utils.autoCleared

class NewlyFragment: Fragment(R.layout.fragment_newly) {

    private var binding by autoCleared<FragmentNewlyBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentNewlyBinding.bind(view)
    }
}