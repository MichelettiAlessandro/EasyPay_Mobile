package com.unito.easypay.ui.home

import android.graphics.Bitmap
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import java.io.IOException
import java.nio.file.FileSystems
import java.nio.file.Path


class HomeViewModel : ViewModel() {



    private val QR_CODE_IMAGE_PATH = "./MyQRCode.png"

    /*
    This method takes the text to be encoded, the width and height of the QR Code,
    and returns the QR Code in the form of a byte array.
    */
    @Throws(WriterException::class, IOException::class)
    fun getQRCodeImage(
        text: String?
    ): Bitmap {

        val multiFormatWriter = MultiFormatWriter()
        val bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200)
        val barcodeEncoder = BarcodeEncoder()
        val bitmap: Bitmap = barcodeEncoder.createBitmap(bitMatrix)

        Log.d("OUUU", "creato bitmap")
        return bitmap
    }

}