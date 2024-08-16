package com.elifnuroksuz.truthordare

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuestionFragment : Fragment(R.layout.fragment_question) {

    private val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonTruth = view.findViewById<Button>(R.id.button_truth)
        val buttonDare = view.findViewById<Button>(R.id.button_dare)
        val textViewQuestion = view.findViewById<TextView>(R.id.textview_question)

        // LiveData gözlemcisi
        viewModel.responseLiveData.observe(viewLifecycleOwner, Observer { response ->
            textViewQuestion.text = response
        })

        buttonTruth.setOnClickListener {
            viewModel.getGenerativeModelResponse("Doğruluk mu cesaretlilik mi oyunu için bir doğruluk sorusu sor.Yorum yapma sadece soru olsun.Ayrıca hep farklı sorular sor.")
        }

        buttonDare.setOnClickListener {
            viewModel.getGenerativeModelResponse("Doğruluk mu cesaretlilik mi oyunu için bir cesaretlilik sorusu sor.Yorum yapma sadece soru olsun.Ayrıca hep farklı sorular sor.")
        }
    }


    private fun extractQuestionFromResponse(response: String): String {
        val startDelimiter = "**"
        val endDelimiter = "**"

        val startIndex = response.indexOf(startDelimiter)
        val endIndex = response.indexOf(endDelimiter, startIndex + startDelimiter.length)

        return if (startIndex != -1 && endIndex != -1 && endIndex > startIndex) {
            response.substring(startIndex + startDelimiter.length, endIndex).trim()
        } else {
            "" // Belirtilen format bulunamazsa boş bir string döndür
        }
    }



}
