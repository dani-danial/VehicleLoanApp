package com.example.vehicleloanapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class HomeFragment : Fragment() {

    // Declare views
    private lateinit var etVehiclePrice: EditText
    private lateinit var etDownPayment: EditText
    private lateinit var etLoanPeriod: EditText
    private lateinit var etInterestRate: EditText
    private lateinit var btnCalculate: Button
    private lateinit var tvLoanAmount: TextView
    private lateinit var tvTotalInterest: TextView
    private lateinit var tvTotalPayment: TextView
    private lateinit var tvMonthlyPayment: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Initialize Views
        etVehiclePrice = view.findViewById(R.id.etVehiclePrice)
        etDownPayment = view.findViewById(R.id.etDownPayment)
        etLoanPeriod = view.findViewById(R.id.etLoanPeriod)
        etInterestRate = view.findViewById(R.id.etInterestRate)
        btnCalculate = view.findViewById(R.id.btnCalculate)
        tvLoanAmount = view.findViewById(R.id.tvLoanAmount)
        tvTotalInterest = view.findViewById(R.id.tvTotalInterest)
        tvTotalPayment = view.findViewById(R.id.tvTotalPayment)
        tvMonthlyPayment = view.findViewById(R.id.tvMonthlyPayment)

        // Set Click Listener
        btnCalculate.setOnClickListener {
            calculateLoan()
        }

        return view
    }

    private fun calculateLoan() {
        // Check if inputs are empty to prevent app crash
        if (etVehiclePrice.text.isEmpty() || etDownPayment.text.isEmpty() ||
            etLoanPeriod.text.isEmpty() || etInterestRate.text.isEmpty()) {
            Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            // 1. Get values from input
            val price = etVehiclePrice.text.toString().toDouble()
            val downPayment = etDownPayment.text.toString().toDouble()
            val periodYears = etLoanPeriod.text.toString().toInt()
            val rate = etInterestRate.text.toString().toDouble()

            // 2. Calculation Logic
            val loanAmount = price - downPayment
            val totalInterest = loanAmount * (rate / 100) * periodYears
            val totalPayment = loanAmount + totalInterest
            val monthlyPayment = totalPayment / (periodYears * 12)

            // 3. Display Results (formatted to 2 decimal places)
            tvLoanAmount.text = String.format("Loan Amount: RM %.2f", loanAmount)
            tvTotalInterest.text = String.format("Total Interest: RM %.2f", totalInterest)
            tvTotalPayment.text = String.format("Total Payment: RM %.2f", totalPayment)
            tvMonthlyPayment.text = String.format("Monthly Payment: RM %.2f", monthlyPayment)

        } catch (e: Exception) {
            Toast.makeText(context, "Invalid Input", Toast.LENGTH_SHORT).show()
        }
    }
}