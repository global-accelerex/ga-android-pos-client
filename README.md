# ga-android-pos-client
<a href='https://bintray.com/globalaccelerex/globalaccelerex-android/ga-android-pos-client/_latestVersion'><img src='https://api.bintray.com/packages/globalaccelerex/globalaccelerex-android/ga-android-pos-client/images/download.svg'></a>

This library has been designed for easy integration of Global Accelerex android POS services into third party android applications. 

# Requirements

Any application making use of this library should run on Global Accelerex android POS devices or on an android device with Global Accelerex Mock base app installed on it.  

# Getting Started

## Installation
You do not need to clone this repository or download the files. Just add the following lines to your app's `build.gradle`:

`implementation 'com.globalaccelerex.android:ga-android-pos-client:1.0.0'`

## Setup

After including the library in your application's `build.gradle`, create an instance of `GAClientLib` like this:

```kotlin
val supportLibrary = GAClientLib.Builder()
        .setCountryCode(Countries.NIGERIA)
        .build()
```
N.B `Countries` should represent the country in which the pos device will be used.

Now that you have an instance of the library in your project, it can be used to make all necessary and supported transactions.

Suggestion: This initialization should be in singleton format because new initializations may carry different configurations.

## Key Exchange

A key exchange request can be made to retrieve the terminal's current configuration. To perform this action, follow the example code shown below.

```kotlin
class MainActivity : AppCompatActivity() {

    val supportLibrary = GAClientLib.Builder()
        .setCountryCode(Countries.NIGERIA)
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportLibrary.makeKeyExchangeRequest(callingComponent = this) // calling component should either be an Activity or a fragment
    }
}
```
Note that the function must be called from within an Activity or a Fragment.

## Parameters Request
To get the details of the POS terminal being used, follow this example:

```kotlin
class MainActivity : AppCompatActivity() {

    val supportLibrary = GAClientLib.Builder()
        .setCountryCode(Countries.NIGERIA)
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportLibrary.makeParametersRequest(callingComponent = this) // calling component should either be an Activity or a fragment
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GaRequestKeys.PARAMETERS_REQUEST_CODE) {
                val posInformation: PosInformation? = supportLibrary.getPosParametersResponse(data)
            }
        }
    }
}
```
When this request is made, the Pos details are returned as an object of `PosInformation` in the `onActivityResult` of your Activity or Fragment.

# Usage

### Sample Usage

 ```kotlin
class MainActivity : AppCompatActivity() {

    val supportLibrary = GAClientLib.Builder()   //Step 1
        .setCountryCode(Countries.NIGERIA)
        .build()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        //Step 2
        supportLibrary.cardTransactions.purchase(
                amount = 0.01,                                   //step 2.1
                callingComponent = this,                         //step 2.2
                customPrint = true                               //step 2.3
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        
        //Step 3
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GaRequestKeys.CP_PURCHASE_REQUEST_CODE) {            //step 3.1
                val cardTransactionResponse: CardTransactionResponse? = supportLibrary.getCardTransactionResponse(data) //step 3.2
                // Perform actions with the returned data
            }
        }
    }
}
```
### Step 1 (Setup)
This step has been covered already in the Getting Started section

### Step 2 (Initializing a transaction)
Global accelerex POS may support different transactions modes such as Card Present Transactions (CP), Card Not Present Transactions (CNP) and Mobile Money Transactions (MM) depending on your location. Select a corresponding function to carry out your preferred transaction. Note: An exception will be thrown if the transaction type is not supported in your selected region. The general format for calling a transaction will be: [libraryInstance].[transactionMode].[transactionType]. Just as seen above:
```
        supportLibrary.cardtransaction.purchase(...)
```

### Step 2.1 (Setting the amount)
Any amount entered should be in `Double` format.

### Step 2.2 (Setting the calling component)
As stated earlier, these functions should be called from an `Activity` or a `Fragment`.
The calling component set to `this` refers to the activity or fragment that the functions are called from.

### Step 2.3 (Setting the print mode)
After making a transaction, you can decide to design your own custom receipt format in which case `customPrint` is set to `true`, or use the default print format in which case `customPrint` is set to `false`

### Step 3 (Retrieving transaction response)
After a transaction is called, the POS device takes over and performs all the neccesary procedures involved in carrying that transaction. When this is done, the transaction details are returned to your application. To retrieve this response, you'll have to listen for it in the activity or fragment's `onActivityResult` using the transaction Request key.

### Step 3.1 (Listening for your transaction)
For every transaction type made, there is a corresponding `GaRequestKeys` key for it. To listen for the response details compare the requestCode to the matching transaction type. The naming format for the Request keys is: GaRequestKeys.[transactionMode]_[transactiontype]_[requestCode]

### Step 3.2 (Retrieving transaction response data)
After listening for a particular transaction, you can get the response by calling a get function related to the transaction that was called from the client library.


All transactions usually follow the same format. However you can take a look at the example project for more details.

## Printing a reciept
To print a receipt, a receiptFormat object has to be created which will be used to construct the parameters to be printed. After this is done, you can pass the format to the print receipt method as shown in the example below:

```kotlin
          val receiptFormat = ReceiptFormat(applicationContext)
            receiptFormat.addSingleLine("This is a default line")
            receiptFormat.addSingleLine("This is a bold line", isBold = true)
            receiptFormat.addSingleLine("This is a large text", isBold = true, fontSize = FontSize.LARGE)
            receiptFormat.addSingleLine("This is the first printing line also testing multiline so this text has to be very very long", TextAlignment.ALIGN_LEFT)
            receiptFormat.addSpaceDivider()
            receiptFormat.addSingleLine("Just to confirm!", TextAlignment.ALIGN_RIGHT)
            receiptFormat.addLineDivider()
            receiptFormat.addKeyValuePair(key = "Header", value = "this value", isMultiLine = false, isBold = true, fontSize = FontSize.LARGE)
            supportLibrary.printer.printReceipt(receipt = receiptFormat.generatePaymentReceipt(), callingComponent = this)
```

Note that `generatePaymentReceipt` will generate two copies of the receipt; A merchant copy and a customer copy. To generate receipt for other purposes, just call `generateReceipt`.


# Issues
If you're experiencing technical difficulties or you have suggestions for the library, please raise an issue at: 
https://github.com/global-accelerex/ga-android-pos-client/issues






















