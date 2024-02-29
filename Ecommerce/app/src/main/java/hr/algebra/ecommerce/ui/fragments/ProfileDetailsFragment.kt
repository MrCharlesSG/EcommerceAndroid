package hr.algebra.ecommerce.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import hr.algebra.ecommerce.R
import hr.algebra.ecommerce.auth.AuthManagerRepository
import hr.algebra.ecommerce.databinding.FragmentProfileDetailsBinding

class ProfileDetailsFragment : Fragment() {

    private lateinit var binding: FragmentProfileDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileDetailsBinding.inflate(layoutInflater)
        bindProfile()
        setupListeners()
        return binding.root
    }

    private fun bindProfile() {
        val user = AuthManagerRepository.INSTANCE.getAuthManager().getUserLogged()
        val text = user?.email ?: getString(R.string.no_email_2)
        binding.tvEmail.text = text
    }

    private fun setupListeners() {
        binding.btnLogOut.setOnClickListener {
            AuthManagerRepository.INSTANCE.getAuthManager().logOut()
            findNavController().navigate(R.id.details_to_shop)
        }
    }
}