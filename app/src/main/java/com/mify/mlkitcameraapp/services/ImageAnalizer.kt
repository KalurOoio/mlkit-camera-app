package com.mify.mlkitcameraapp.services

import android.annotation.SuppressLint
import android.util.Size
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.pose.PoseDetection
import com.google.mlkit.vision.pose.PoseDetectorOptionsBase
import com.google.mlkit.vision.pose.PoseLandmark
import com.google.mlkit.vision.pose.accurate.AccuratePoseDetectorOptions
import com.mify.mlkitcameraapp.cameraActivity.LandmarkView
import java.lang.Math.max
import java.lang.Math.min

class ImageAnalizer(
    private val view: LandmarkView
): ImageAnalysis.Analyzer {
    private val options = AccuratePoseDetectorOptions
        .Builder()
        .setDetectorMode(AccuratePoseDetectorOptions.STREAM_MODE)
        .build()

    private val detector = PoseDetection.getClient(options)

    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(image: ImageProxy) {
        val frameImage = image.image
        if(frameImage != null){
            val imageForDetector = InputImage.fromMediaImage(frameImage, image.imageInfo.rotationDegrees)
            val task = detector.process(imageForDetector)
            task
                .addOnSuccessListener {
                    val size = Size(
                        min(image.width, image.height),
                        max(image.width, image.height)
                    )
                    view.setPose(it, size)
                    it.getPoseLandmark(PoseLandmark.RIGHT_ELBOW)
                    image.close()
                }
                .addOnFailureListener{
                    image.close()
                }

        }

    }
}