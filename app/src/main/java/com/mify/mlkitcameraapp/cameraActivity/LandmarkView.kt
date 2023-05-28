package com.mify.mlkitcameraapp.cameraActivity

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.graphics.PointF
import android.util.AttributeSet
import android.util.Size
import android.view.View
import androidx.lifecycle.ViewTreeLifecycleOwner
import com.google.mlkit.vision.common.PointF3D
import com.google.mlkit.vision.pose.Pose
import com.google.mlkit.vision.pose.PoseLandmark

class LandmarkView(
    private val context: Context,
    private val attributes: AttributeSet
): View(context, attributes) {

    private var viewSize = Size (0, 0,)
    private var mainPaint = Paint (ANTI_ALIAS_FLAG)
    private var detectedPose: Pose? = null
    private var sourceSize = Size(0, 0)

    init {
        mainPaint.color = Color.RED
        mainPaint.style = Paint.Style.FILL
        mainPaint.strokeWidth = 10F
    }

    fun setPose(newPose: Pose, newSize: Size){
        sourceSize = newSize
        detectedPose = newPose
        invalidate()
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        viewSize = Size (w, h)
    }
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawPoints(canvas)
        drawLines(canvas)

    }
    private fun convertPoint(targetPoint:PointF3D): PointF {
        val x1 = targetPoint.x
        val y1 = targetPoint.y
        val w1 = sourceSize.width
        val h1 = sourceSize.height
        val w2 = viewSize.width
        val h2 = viewSize.height
        val x2 = x1 * w2 / w1
        val y2 = y1 * h2 / h1
        return PointF(x2, y2)

    }
    private fun drawPoint (center: PointF3D, canvas: Canvas?){
        val convertedPoint = convertPoint (center)
        canvas?.drawCircle(
            convertedPoint.x,
            convertedPoint.y,
            30F,
             mainPaint

        )
    }
    private fun drawPoints (canvas: Canvas?){
        var currentPoint = detectedPose?.getPoseLandmark(PoseLandmark.NOSE)
        if (currentPoint != null){
            drawPoint(currentPoint.position3D, canvas)
        }
        currentPoint = detectedPose?.getPoseLandmark(PoseLandmark.LEFT_EYE)
        if (currentPoint != null){
            drawPoint(currentPoint.position3D, canvas)
        }
    }

    private fun drawline(start: PointF3D, end: PointF3D, canvas: Canvas?){
        val convertedStart = convertPoint(start)
        val convertedEnd = convertPoint(end)
        canvas?.drawLine(
        convertedStart.x,
        convertedStart.y,
        convertedEnd.x,
        convertedEnd.y,
        mainPaint
        )
    }

    private fun drawLines(canvas: Canvas?){
        var startPoint = detectedPose?.getPoseLandmark(PoseLandmark.LEFT_SHOULDER)
        var endPoint = detectedPose?.getPoseLandmark(PoseLandmark.LEFT_ELBOW)
        if (startPoint != null && endPoint != null) {
            drawline(startPoint.position3D, endPoint.position3D, canvas)
        }
    }

}