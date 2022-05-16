package caios.android.pixivion.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import caios.android.pixivion.R
import caios.android.pixivion.databinding.FragmentSearchBinding
import caios.android.pixivion.utils.SystemUtils
import caios.android.pixivion.utils.autoCleared
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialSharedAxis

class SearchFragment: Fragment(R.layout.fragment_search) {

    private var binding by autoCleared<FragmentSearchBinding>()
    private val args by navArgs<SearchFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val animDuration = resources.getInteger(R.integer.anim_duration_fragment_transition).toLong()
        val enterSharedAxisTransition = MaterialSharedAxis(MaterialSharedAxis.X, true).apply { duration = animDuration }
        val returnSharedAxisTransition = MaterialSharedAxis(MaterialSharedAxis.X, false).apply { duration = animDuration }

        if (args.isSharedElement) {
            sharedElementEnterTransition = MaterialContainerTransform().apply {
                duration = animDuration
                scrimColor = Color.TRANSPARENT
                drawingViewId = R.id.navigationHostMain
                fadeMode = MaterialContainerTransform.FADE_MODE_CROSS
            }
        } else {
            enterTransition = enterSharedAxisTransition
            returnTransition = returnSharedAxisTransition
        }

        exitTransition = enterSharedAxisTransition
        reenterTransition = returnSharedAxisTransition
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentSearchBinding.bind(view)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, insets ->
            val systemBarInsets = SystemUtils.getSystemBarInsets(insets)

            binding.statusBarSpace.updateLayoutParams { height = systemBarInsets.top }

            return@setOnApplyWindowInsetsListener insets
        }
    }
}