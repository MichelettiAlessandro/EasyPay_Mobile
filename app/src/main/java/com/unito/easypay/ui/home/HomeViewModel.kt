package com.unito.easypay.ui.home

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import java.io.IOException


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

        return barcodeEncoder.createBitmap(bitMatrix)
    }

}