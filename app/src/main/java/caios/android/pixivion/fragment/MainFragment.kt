package caios.android.pixivion.fragment

import android.animation.ObjectAnimator
import android.graphics.drawable.Animatable
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.transition.Fade
import caios.android.pixivion.R
import caios.android.pixivion.databinding.FragmentMainBinding
import caios.android.pixivion.model.MainViewModel
import caios.android.pixivion.utils.SystemUtils
import caios.android.pixivion.utils.ToastUtils
import caios.android.pixivion.utils.autoCleared
import com.google.android.material.navigation.NavigationView

class MainFragment: Fragment(R.layout.fragment_main), NavigationView.OnNavigationItemSelectedListener {

    private var binding by autoCleared<FragmentMainBinding>()
    private val mainModel by lazy { ViewModelProvider(requireActivity()).get(MainViewModel::class.java) }

    private var isShouldAnimateNavigationIcon = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        reenterTransition = Fade()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentMainBinding.bind(view)

        animateNavigationIcon()

        binding.drawerNavigationView.setNavigationItemSelectedListener(this)

        binding.navigationIcon.setOnClickListener {
            binding.root.openDrawer(binding.drawerNavigationView)
        }

        binding.searchBar.setOnClickListener {
            findNavController().navigate(
                MainFragmentDirections.actionGlobalSearchFragment(true),
                FragmentNavigatorExtras(binding.searchBar to binding.searchBar.transitionName)
            )
        }

        binding.bottomNavigationView.setOnItemSelectedListener {
            changeTabFragment(it.itemId)
            return@setOnItemSelectedListener true
        }

        binding.appbarLayout.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                binding.appbarLayout.viewTreeObserver.removeOnGlobalLayoutListener(this)
                mainModel.searchBarHeight.value = binding.appbarLayout.height
            }
        })

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, insets ->
            val systemBarInsets = SystemUtils.getSystemBarInsets(insets)

            binding.statusBarSpace.updateLayoutParams { height = systemBarInsets.top }
            binding.bottomNavigationView.updatePadding(bottom = systemBarInsets.bottom)

            mainModel.headerHeight.value = systemBarInsets.top
            mainModel.footerHeight.value = systemBarInsets.bottom

            SystemUtils.statusBarHeight = systemBarInsets.top
            SystemUtils.navigationBarHeight = systemBarInsets.bottom

            return@setOnApplyWindowInsetsListener insets
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        binding.root.closeDrawer(GravityCompat.START)
        binding.root.addDrawerListener(object : DrawerLayout.SimpleDrawerListener() {
            override fun onDrawerClosed(drawerView: View) {
                binding.root.removeDrawerListener(this)

                when (item.itemId) {
                    R.id.menuSetting  -> ToastUtils.show(requireContext(), R.string.comingSoon)
                    R.id.menuAboutApp -> ToastUtils.show(requireContext(), R.string.comingSoon)
                }
            }
        })
        return true
    }

    private fun getPrimaryFragment(): Fragment? {
        val navFragment = childFragmentManager.findFragmentById(binding.navigationHostLibrary.id)
        return navFragment?.childFragmentManager?.primaryNavigationFragment
    }

    private fun changeTabFragment(itemId: Int) {
        val targetNavigation = getNavigationFromId(itemId) ?: return
        val primaryNavigation = getPrimaryNavigation() ?: return

        if(targetNavigation.first != primaryNavigation.first) {
            binding.navigationHostLibrary.findNavController().navigate(targetNavigation.second)
            animationSearchBar(true)
        }
    }

    private fun getPrimaryNavigation(): Pair<Int, Int>? {
        return when (getPrimaryFragment()) {
            is HomeFragment  -> R.id.homeFragment to R.id.action_global_homeFragment
            is TrendFragment -> R.id.trendFragment to R.id.action_global_trendFragment
            is NewlyFragment -> R.id.newlyFragment to R.id.action_global_newlyFragment
            is UserFragment  -> R.id.userFragment to R.id.action_global_userFragment
            else             -> null
        }
    }

    private fun getNavigationFromId(itemId: Int): Pair<Int, Int>? {
        return when (itemId) {
            R.id.homeFragment  -> R.id.homeFragment to R.id.action_global_homeFragment
            R.id.trendFragment -> R.id.trendFragment to R.id.action_global_trendFragment
            R.id.newlyFragment -> R.id.newlyFragment to R.id.action_global_newlyFragment
            R.id.userFragment  -> R.id.userFragment to R.id.action_global_userFragment
            else               -> null
        }
    }

    private fun animationSearchBar(isShow: Boolean) {
        if (isShow) {
            ObjectAnimator.ofFloat(binding.appbarLayout, "translationY", 0f).apply {
                duration = resources.getInteger(R.integer.anim_duration_refresh_recycler_view).toLong()
                interpolator = AnimationUtils.loadInterpolator(requireContext(), R.anim.extra_decelerate_interpolator)
            }.start()
        } else {
            ObjectAnimator.ofFloat(binding.appbarLayout, "translationY", -binding.appbarLayout.height.toFloat()).apply {
                duration = resources.getInteger(R.integer.anim_duration_refresh_recycler_view).toLong()
                interpolator = AnimationUtils.loadInterpolator(requireContext(), R.anim.extra_decelerate_interpolator)
            }.start()
        }
    }

    private fun animateNavigationIcon() {
        binding.navigationIcon.setColorFilter(ContextCompat.getColor(requireContext(), R.color.colorSecondaryText))

        if (isShouldAnimateNavigationIcon) {
            isShouldAnimateNavigationIcon = false

            (binding.navigationIcon.drawable as AnimatedVectorDrawable).reset()
            (binding.navigationIcon.drawable as? Animatable)?.start()
        }
    }
}