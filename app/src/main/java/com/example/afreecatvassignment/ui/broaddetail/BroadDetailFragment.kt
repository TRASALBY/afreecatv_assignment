package com.example.afreecatvassignment.ui.broaddetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.afreecatvassignment.databinding.FragmentBroadDetailBinding

class BroadDetailFragment : Fragment() {

    private var _binding: FragmentBroadDetailBinding? = null
    private val binding
        get() = checkNotNull(_binding) { "binding was accessed outside of view lifecycle" }

    private val args: BroadDetailFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBroadDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.broad = args.broad
        setWatchBroadBtn()
    }

    private fun setWatchBroadBtn() {
        binding.btnWatchBroad.setOnClickListener {
            val broad = args.broad
            val broadUri = "http://play.afreecatv.com/${broad.userId}/${broad.broadNumber}"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(broadUri))
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}