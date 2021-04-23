package com.example.javasampe;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.javasampe.databinding.ActivityMainBinding;
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.GAClientLib;
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.contracts.PosContracts;
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.printing.TextAlignment;
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.printing.Receipt;
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.printing.ReceiptFormat;
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.transactions.Transaction;
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.util.Countries;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    GAClientLib clientLib = new GAClientLib.Builder()
            .setCountryCode(Countries.NIGERIA)
            .build();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ActivityResultLauncher<Transaction> transaction = registerForActivityResult(new PosContracts.CardTransactionContract(), result -> {
            String transactionStatus = result.getRequestStatus() == null ? "Cancelled/Failed" : result.getRequestStatus().toString();
            Toast.makeText(MainActivity.this, "Response: " + transactionStatus, Toast.LENGTH_LONG).show();
        });

        binding.printReceipt.setOnClickListener(v -> {
            ReceiptFormat format = new ReceiptFormat(MainActivity.this);
            format.addSingleLine("This is a java test line", TextAlignment.ALIGN_LEFT, null, false);
            format.addSingleLine("Just to confirm!!", TextAlignment.ALIGN_CENTER, null, false);
            Receipt receipt = format.generatePaymentReceipt();
            clientLib.getPrinter().printReceipt(receipt, MainActivity.this);
        });

        binding.transactionRequest.setOnClickListener(view -> {
            try {
                transaction.launch(Transaction.purchase(1.0, true));
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, "Payment app not installed", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
