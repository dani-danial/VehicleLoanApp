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
import android.view.Gravity
import android.text.TextWatcher
import android.text.Editable

class HomeFragment : Fragment() {

    // Declare views
    private lateinit var etVehiclePrice: EditText
    private lateinit var etDownPayment: EditText
    private lateinit var etLoanPeriod: EditText
    private lateinit var etInterestRate: EditText
    private lateinit var btnCalculate: Button
    private lateinit var btnReset: Button // <--- NEW: Declare Reset Button
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
        btnReset = view.findViewById(R.id.btnReset) // <--- NEW: Initialize Reset Button

        tvLoanAmount = view.findViewById(R.id.tvLoanAmount)
        tvTotalInterest = view.findViewById(R.id.tvTotalInterest)
        tvTotalPayment = view.findViewById(R.id.tvTotalPayment)
        tvMonthlyPayment = view.findViewById(R.id.tvMonthlyPayment)

        // Apply real-time input validation
        addInputValidationWatcher(etVehiclePrice)
        addInputValidationWatcher(etDownPayment)
        addInputValidationWatcher(etInterestRate)

        // Set Click Listener for Calculate
        btnCalculate.setOnClickListener {
            calculateLoan()
        }

        // <--- NEW: Set Click Listener for Reset
        btnReset.setOnClickListener {
            resetFields()
        }

        return view
    }

    // Helper function to show custom-positioned Toast
    private fun showToast(message: String) {
        val customToast = Toast.makeText(context, message, Toast.LENGTH_LONG)
        customToast.setGravity(Gravity.CENTER, 0, -300)
        customToast.show()
    }

    // New function to handle real-time input validation
    private fun addInputValidationWatcher(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(editable: Editable) {
                if (editable.toString() == ".") {
                    editable.clear()
                    showToast("Invalid format. Start with 0 before the decimal (e.g., 0.5).")
                }
            }
        })
    }

    private fun calculateLoan() {
        etDownPayment.error = null

        val priceText = etVehiclePrice.text.toString()
        val downPaymentText = etDownPayment.text.toString()
        val periodText = etLoanPeriod.text.toString()
        val rateText = etInterestRate.text.toString()

        if (priceText.isEmpty() || downPaymentText.isEmpty() ||
            periodText.isEmpty() || rateText.isEmpty()) {
            showToast("Please fill in all fields")
            return
        }

        try {
            val price = priceText.toDouble()
            val downPayment = downPaymentText.toDouble()
            val periodYears = periodText.toInt()
            val rate = rateText.toDouble()

            if (downPayment >= price) {
                showToast("Down payment cannot be more than Vehicle Price!")
                return
            }

            val loanAmount = price - downPayment
            val totalInterest = loanAmount * (rate / 100) * periodYears
            val totalPayment = loanAmount + totalInterest
            val monthlyPayment = totalPayment / (periodYears * 12)

            tvLoanAmount.text = String.format("Loan Amount: RM %.2f", loanAmount)
            tvTotalInterest.text = String.format("Total Interest: RM %.2f", totalInterest)
            tvTotalPayment.text = String.format("Total Payment: RM %.2f", totalPayment)
            tvMonthlyPayment.text = String.format("Monthly Payment: RM %.2f", monthlyPayment)

        } catch (e: Exception) {
            showToast("Invalid Input Format (Check Decimals/Characters)")
        }
    }

    // <--- NEW FUNCTION: Clears all inputs and resets text
    private fun resetFields() {
        etVehiclePrice.text.clear()
        etDownPayment.text.clear()
        etLoanPeriod.text.clear()
        etInterestRate.text.clear()

        // Reset results to default label
        tvLoanAmount.text = "Loan Amount: RM 0.00"
        tvTotalInterest.text = "Total Interest: RM 0.00"
        tvTotalPayment.text = "Total Payment: RM 0.00"
        tvMonthlyPayment.text = "Monthly Payment: RM 0.00"

        // Remove any error messages
        etDownPayment.error = null

        showToast("Fields Reset")
    }
}