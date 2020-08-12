package com.example.javasampe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.globalaccelerex.globalaccelerexandroidposclientlibrary.GAClientLib;
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.printing.Receipt;
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.printing.ReceiptFormat;
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.util.Countries;

public class MainActivity extends AppCompatActivity {

    GAClientLib clientLib = new GAClientLib.Builder()
            .setCountryCode(Countries.NIGERIA)
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button printReceipt = findViewById(R.id.print_receipt);


        printReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReceiptFormat format = new ReceiptFormat(MainActivity.this);
                format.addSingleLine("This is a java test line");
                format.addSingleLine("Just to confirm!!");
                Receipt receipt = format.generateReceipt();
                clientLib.printReceipt(receipt, MainActivity.this);
            }
        });

    }
}
