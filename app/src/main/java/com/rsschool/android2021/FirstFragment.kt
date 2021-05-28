package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class FirstFragment : Fragment() {

    private var dataPassListener: DataPassListener? = null
    private var generateButton: Button? = null
    private var previousResult: TextView? = null

    interface DataPassListener {
        fun passDataSecondFragment(min: Int, max: Int)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is DataPassListener) {
            dataPassListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        dataPassListener = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = "Previous result: ${result.toString()}"

        val minValueInput: EditText = view.findViewById(R.id.min_value)
        val maxValueInput: EditText = view.findViewById(R.id.max_value)
        generateButton?.setOnClickListener {
            try {
                var min = minValueInput.text.toString().toInt()
                var max = maxValueInput.text.toString().toInt()

                if (min < max && (min in 0..MAX_INTEGER) && (max in 0..MAX_INTEGER)) {
                    dataPassListener?.passDataSecondFragment(min, max)
                } else {
                    Toast.makeText(context, R.string.invalid_data_toast, Toast.LENGTH_SHORT).show()
                }
            } catch (ex: NumberFormatException) {
                Toast.makeText(context, R.string.invalid_data_toast, Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
        private const val MAX_INTEGER = 2147483647
    }
}