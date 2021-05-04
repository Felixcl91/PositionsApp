package com.test.positionsapp.ui.positiondetail

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.test.positionsapp.R
import com.test.positionsapp.data.entities.Positions
import com.test.positionsapp.databinding.PositionDetailFragmentBinding
import com.test.positionsapp.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.positions_fragment.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.time.ExperimentalTime


@AndroidEntryPoint
class PositionDetailFragment : Fragment() {

    companion object {
        fun newInstance() = PositionDetailFragment()
        private val TAG = "POSITIONDET"
    }
    private var binding: PositionDetailFragmentBinding by autoCleared()
    private lateinit var viewModel: PositionDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PositionDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @ExperimentalTime
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val arg = arguments?.get("pos")
        if (arg == null) {
            progress_bar.visibility = View.VISIBLE
        } else {
            progress_bar.visibility = View.GONE
            bindPosition(arg as Positions)
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    @ExperimentalTime
    private fun bindPosition(positions: Positions) {
        binding.company.text = positions.company
        binding.title.text = positions.title
        binding.location.text = "${positions.location} * ${positions.type}"
        // Conversion de string a date
        val date = positions.createdAt
        val sdf = SimpleDateFormat(
            "EE MMM dd HH:mm:ss z yyyy",
            Locale.ENGLISH
        )
        val parsedDate = sdf.parse(date)
        val print = SimpleDateFormat("MMM d, yyyy HH:mm:ss")
        val conv = print.format(parsedDate)
        Log.e(TAG, "FECHA---$conv")
        // La fecha actual
        val fechaactual = Date(System.currentTimeMillis())
        val parseDate2 = sdf.parse(fechaactual.toString())
        val conv2 = print.format(parseDate2)
        Log.e(TAG, "FECHA ACt---$conv2")
        // compare dates
        val milisecondsByDay = 86400000
        val dias = ((parseDate2.time - parsedDate.getTime()) / milisecondsByDay)
        if (dias.toDouble() == 0.0) {
            binding.datePos.text = "Hoy"
        } else {
            binding.datePos.text = "Hace $dias días"
        }

        // subrayado
        val textSub = SpannableString(positions.url)
        textSub.setSpan(UnderlineSpan(), 0, textSub.length, 0)
        binding.urlCompany.text = textSub
        // html
        binding.descCompany.text = Html.fromHtml("${positions.description}")
        // image
        Glide.with(binding.root)
            .load(positions.companyLogo)
            .into(binding.image)

        binding.urlCompany.setOnClickListener {
            findNavController().navigate(
                R.id.action_positionDetailFragment_to_webFragment,
                bundleOf("pos_url" to positions.companyUrl)
            )
        }

    }

}