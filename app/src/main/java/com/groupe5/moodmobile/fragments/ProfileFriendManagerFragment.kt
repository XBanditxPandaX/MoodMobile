package com.groupe5.moodmobile.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.groupe5.moodmobile.R
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.groupe5.moodmobile.classes.SharedViewModel
import com.groupe5.moodmobile.databinding.FragmentProfileFriendManagerBinding
import com.groupe5.moodmobile.dtos.Friend.DtoInputFriend

class ProfileFriendManagerFragment : Fragment() {
    lateinit var binding: FragmentProfileFriendManagerBinding
    private lateinit var sharedViewModel: SharedViewModel

    companion object {
        fun newInstance() = ProfileFriendManagerFragment()
    }

    private lateinit var viewModel: ProfileFriendManagerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileFriendManagerBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val prefs = requireActivity().getSharedPreferences("mood", Context.MODE_PRIVATE)
        val token = prefs.getString("jwtToken", "") ?: ""
        viewModel = ProfileFriendManagerViewModel(token)

        val profileFriendsFragment = childFragmentManager
            .findFragmentById(R.id.fcb_profileFriendManager_list) as ProfileFriendsFragment

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        //...
        viewModel.mutableFriendDeleteData.observe(viewLifecycleOwner) { friend ->
            sharedViewModel.friendData.value = friend
        }

        viewModel.mutableFriendDeleteData.observe(viewLifecycleOwner){
            profileFriendsFragment.deleteFriendFromUI(it)
        }
        viewModel.mutableFriendLiveData.observe(viewLifecycleOwner) {
            Log.i("Friends", it.toString())
            profileFriendsFragment.initUIWithFriends(it)
        }

        viewModel.startGetAllFriends()

        profileFriendsFragment.profileFriendRecyclerViewAdapter.setOnDeleteClickListener(object : ProfileFriendsRecyclerViewAdapter.OnDeleteClickListener {
            override fun onDeleteClick(friend: DtoInputFriend) {
                viewModel.deleteFriend(friend)
            }
        })
    }
}