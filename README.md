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

A key exchange request should be made at least once in the application's lifecycle. To perform this action, follow the example code shown below.

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
Note that the function must be called from withing an Activity or a Fragment.

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
            if (requestCode == GaResponseKeys.PARAMETERS_REQUEST_CODE) {
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
        
        //Assuming Key exchange and Parameters requests have been carried out.

        //Step 2
        supportLibrary.makeCardPresentTransactionRequest(
            amount = 2.0,                                          //step 2.1
            transactionType = TransactionType.CP_PURCHASE,         //step 2.2
            customPrint = true,                                    //step 2.3
            callingComponent = this                                //step 2.4
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GaResponseKeys.CARD_PURCHASE_REQUEST_CODE) {
                val cardTransactionResponse: CardTransactionResponse? = supportLibrary.getCardTransactionResponse(data)
                // Perform actions with the returned data
            }
        }
    }
}
```
### Step 1 (Setup)
This step has been covered already in the Getting Started section

### Step 2 (Initializing a transaction)
Global accelerex POS may support different transactions such as Card Present Transactions (CP), Card Not Present Transactions (CNP) and Mobile Money Transactions (MM) depending on your location.


















