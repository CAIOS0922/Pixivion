package caios.android.pixivion.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import caios.android.pixivion.R
import caios.android.pixivion.databinding.FragmentSearchBinding
import caios.android.pixivion.utils.autoCleared

class SearchFragment: Fragment(R.layout.fragment_search) {

    private var binding by autoCleared<FragmentSearchBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentSearchBinding.bind(view)
    }
}