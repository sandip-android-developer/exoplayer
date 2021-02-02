package com.mediasupportingapplication.ui.multimedia

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mediasupportingapplication.R
import kotlinx.android.synthetic.main.fragment_multimedia.*

class MultiMediaFragment : Fragment() {

    private lateinit var multimediaViewModel: MultimediaViewModel
    var media: String = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /*    multimediaViewModel =
                ViewModelProvider(requireActivity()).get(MultimediaViewModel::class.java)*/
        val root = inflater.inflate(R.layout.fragment_multimedia, container, false)
        /*  val textView: TextView = root.findViewById(R.id.text_dashboard)
          multimediaViewModel.text.observe(viewLifecycleOwner, Observer {
              textView.text = it
          })*/
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        actionListner()
    }

    private fun initView() {

    }

    private fun actionListner() {
        btnonClickAudio.setOnClickListener {
            var intent = Intent(requireActivity(), AudioActivity::class.java)
            startActivity(intent)
        }
        btnonClickVideo.setOnClickListener {
            var intent = Intent(requireActivity(), VideoActivity::class.java)
            startActivity(intent)
        }

    }
}