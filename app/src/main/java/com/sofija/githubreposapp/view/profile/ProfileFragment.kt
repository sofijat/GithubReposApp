package com.sofija.githubreposapp.view.profile

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.sofija.githubreposapp.R
import com.sofija.githubreposapp.databinding.ProfileFragmentBinding
import com.sofija.githubreposapp.model.User

class ProfileFragment(): Fragment() {

    private var _binding: ProfileFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var user: User

    companion object {
        fun newInstance(user: User): ProfileFragment {
            val args = Bundle()
            args.putSerializable("user", user)
            val rf = ProfileFragment(user)
            rf.arguments = args
            return rf
        }
    }

    constructor(currentUser: User): this(){
        user = currentUser
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        user = arguments?.get("user") as User
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.menuItemProfile)?.isVisible = false
        super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        _binding = ProfileFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initProfilePhoto()
        binding.name.text = user?.name
        binding.company.text = user?.company
    }

    private fun initProfilePhoto() {
        context?.let {
            Glide.with(it)
                .load(user?.avatar_url)
                .circleCrop()
                .into(binding.profileImage)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}