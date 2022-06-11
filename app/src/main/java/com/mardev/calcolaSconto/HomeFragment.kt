package com.mardev.calcolaSconto

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.mardev.calcolaSconto.databinding.FragmentHomeBinding
import java.text.DecimalFormat
import kotlin.math.roundToInt

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var prezzoFinale = 0.00

        //Quando premi sul Calcola
        binding.btnCalcolaSconto.setOnClickListener {
            VibratorHelper.vibra(requireContext(), 60)

            val prezzo = inputTesto(binding.textInputPrezzo)
            val percentuale = inputTesto(binding.textInputPercentuale)

            if (prezzo == 0.0 || percentuale == 0.0) {
                Toast.makeText(context, "Inserisci dei valori validi", Toast.LENGTH_SHORT).show()
            } else if (percentuale > 100) {
                Toast.makeText(context, "Inserisci una percentuale valida", Toast.LENGTH_SHORT)
                    .show()
            } else {
                prezzoFinale =
                    (((((100 - percentuale) / 100) * prezzo) * 100.0).roundToInt()) / 100.0
                val risparmio = (((prezzo - prezzoFinale) * 100.0).roundToInt()) / 100.0


                binding.textViewRisparmio.text = DecimalFormat("0.00").format(risparmio) + " €"
                binding.textViewRisultato.text = DecimalFormat("0.00").format(prezzoFinale) + " €"
            }
        }

        //Quando premi su Azzera
        binding.btnAzzera.setOnClickListener {
            VibratorHelper.vibra(requireContext(), 60)
            binding.textViewRisultato.text = "0,00 €"
            binding.textInputPrezzo.text = null
            binding.textInputPercentuale.text = null
            binding.textViewRisparmio.text = null
        }

        //Quando premi su Copia risultato
        binding.fab.setOnClickListener {
            VibratorHelper.vibra(requireContext(), 60)
            Snackbar.make(view, "Risultato copiato negli appunti", Snackbar.LENGTH_LONG)
                .setAnchorView(R.id.fab).show()
            val clipboardManager =
                requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("Risultato", prezzoFinale.toString())
            clipboardManager.setPrimaryClip(clipData)
        }
    }

    private fun inputTesto(textInputEditText: TextInputEditText): Double {
        //Controllo se input valido
        val stringInput = textInputEditText.text.toString()

        return if (stringInput.isNotEmpty()) {
            stringInput.toDouble()
        } else {
            0.0
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}