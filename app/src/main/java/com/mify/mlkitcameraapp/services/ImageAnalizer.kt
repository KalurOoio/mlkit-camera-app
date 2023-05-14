package com.mify.mlkitcameraapp.services

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy

class ImageAnalizer: ImageAnalysis.Analyzer {
    override fun analyze(image: ImageProxy) {
        println("Hey guys")



        image.close()
    }
}