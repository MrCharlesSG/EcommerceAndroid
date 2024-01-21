package hr.algebra.ecommerce.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import hr.algebra.ecommerce.R
import hr.algebra.ecommerce.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {

        binding.llTotalExpended.setOnClickListener {
            findNavController().navigate(R.id.action_profile_to_total_expended)
        }

        binding.llAcquisitions.setOnClickListener {
            findNavController().navigate(R.id.action_profile_to_acquisitions)
        }
    }
}