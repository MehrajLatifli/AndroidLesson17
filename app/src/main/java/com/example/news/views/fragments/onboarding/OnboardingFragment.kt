package com.example.news.views.fragments.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.news.R
import com.example.news.databinding.FragmentOnboardingBinding
import com.example.news.models.onboarding.Onboard
import com.example.news.utilities.gone
import com.example.news.utilities.visible
import com.example.news.views.adapters.OnboardAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OnboardingFragment : Fragment() {

    private var _binding: FragmentOnboardingBinding? = null
    private val binding get() = _binding!!

    private var onboardList = arrayListOf(
        Onboard(R.drawable.onboarding_1, "Title 1", "Description 1"),
        Onboard(R.drawable.onboarding_2, "Title 2", "Description 2"),
        Onboard(R.drawable.onboarding_3, "Title 3", "Description 3")
    )


    private val onboardAdapter = OnboardAdapter()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        lifecycleScope.launch(Dispatchers.Main) {


            onboardAdapter.updateList(onboardList)


            binding?.let { binding ->
                binding.viewPager2.adapter = onboardAdapter


                val indicator = binding.indicator


                val dotsIndicator = binding.indicator
                val viewPager = binding.viewPager2
                viewPager.adapter = onboardAdapter
                dotsIndicator.attachTo(viewPager)


            }


            binding.NextButton.setOnClickListener {

                findNavController().navigate(OnboardingFragmentDirections.actionOnboardingFragmentToLoginFragment())
            }


            binding.viewPager2.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

                    if (position == onboardAdapter.itemCount - 1) {

                        binding.NextButton.visible()
                    } else {

                        binding.NextButton.gone()
                    }
                }
            })

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
