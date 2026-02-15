package com.mathy.contestprep

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.PointF
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener
import androidx.appcompat.widget.AppCompatImageView
import kotlin.math.abs
import kotlin.math.min

class ZoomableImageView(context: Context) : AppCompatImageView(context) {
   var  matrix2: Matrix = Matrix()
    var mode: Int = NONE
    var last: PointF = PointF()
    var start: PointF = PointF()
    var minScale: Float = 1f
    var maxScale: Float = 6f
    var m: FloatArray
    var redundantXSpace: Float = 0f
    var redundantYSpace: Float = 0f
    var width: Float = 0f
    var height: Float = 0f
    var saveScale: Float = 1f
    var right: Float = 0f
    var bottom: Float = 0f
    var origWidth: Float = 0f
    var origHeight: Float = 0f
    var bmWidth: Float = 0f
    var bmHeight: Float = 0f
    var mScaleDetector: ScaleGestureDetector
    //var context: Context

    init {
        super.setClickable(true)
       // this.context = context
        mScaleDetector = ScaleGestureDetector(context, ScaleListener())
       // var matrix: Matrix = Matrix()
            matrix2.setTranslate(1f, 1f)
        m = FloatArray(9)
        imageMatrix = matrix
        scaleType = ScaleType.MATRIX

        setOnTouchListener { v, event ->
            mScaleDetector.onTouchEvent(event)
            matrix2.getValues(m)
            val x = m[Matrix.MTRANS_X]
            val y = m[Matrix.MTRANS_Y]
            val curr = PointF(event.x, event.y)
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    last[event.x] = event.y
                    start.set(last)
                    mode = ZoomableImageView.Companion.DRAG
                }

                MotionEvent.ACTION_POINTER_DOWN -> {
                    last[event.x] = event.y
                    start.set(last)
                    mode = ZoomableImageView.Companion.ZOOM
                }

                MotionEvent.ACTION_MOVE -> if (mode == ZoomableImageView.Companion.ZOOM || (mode == ZoomableImageView.Companion.DRAG && saveScale > minScale)) {
                    var deltaX = curr.x - last.x
                    var deltaY = curr.y - last.y
                    val scaleWidth = Math.round(origWidth * saveScale).toFloat()
                    val scaleHeight = Math.round(origHeight * saveScale).toFloat()
                    if (scaleWidth < width) {
                        deltaX = 0f
                        if (y + deltaY > 0) deltaY = -y
                        else if (y + deltaY < -bottom) deltaY = -(y + bottom)
                    } else if (scaleHeight < height) {
                        deltaY = 0f
                        if (x + deltaX > 0) deltaX = -x
                        else if (x + deltaX < -right) deltaX = -(x + right)
                    } else {
                        if (x + deltaX > 0) deltaX = -x
                        else if (x + deltaX < -right) deltaX = -(x + right)
                        if (y + deltaY > 0) deltaY = -y
                        else if (y + deltaY < -bottom) deltaY = -(y + bottom)
                    }
                    matrix2.postTranslate(deltaX, deltaY)
                    last[curr.x] = curr.y
                }

                MotionEvent.ACTION_UP -> {
                    mode = NONE
                    val xDiff = abs((curr.x - start.x).toDouble()).toInt()
                    val yDiff = abs((curr.y - start.y).toDouble()).toInt()
                    if (xDiff < ZoomableImageView.Companion.CLICK && yDiff < ZoomableImageView.Companion.CLICK) performClick()
                }

                MotionEvent.ACTION_POINTER_UP -> mode = NONE
            }
            imageMatrix = matrix2
            invalidate()
            true
        }
    }

    override fun setImageBitmap(bm: Bitmap) {
        super.setImageBitmap(bm)
        bmWidth = bm.width.toFloat()
        bmHeight = bm.height.toFloat()
    }

    fun setMaxZoom(x: Float) {
        maxScale = x
    }

    private inner class ScaleListener : SimpleOnScaleGestureListener() {
        override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
            mode = ZoomableImageView.Companion.ZOOM
            return true
        }

        override fun onScale(detector: ScaleGestureDetector): Boolean {
            var mScaleFactor = detector.scaleFactor
            val origScale = saveScale
            saveScale *= mScaleFactor
            if (saveScale > maxScale) {
                saveScale = maxScale
                mScaleFactor = maxScale / origScale
            } else if (saveScale < minScale) {
                saveScale = minScale
                mScaleFactor = minScale / origScale
            }
            right = width * saveScale - width - (2 * redundantXSpace * saveScale)
            bottom = height * saveScale - height - (2 * redundantYSpace * saveScale)
            if (origWidth * saveScale <= width || origHeight * saveScale <= height) {
                matrix2.postScale(mScaleFactor, mScaleFactor, width / 2, height / 2)
                if (mScaleFactor < 1) {
                    matrix2.getValues(m)
                    val x = m[Matrix.MTRANS_X]
                    val y = m[Matrix.MTRANS_Y]
                    if (mScaleFactor < 1) {
                        if (Math.round(origWidth * saveScale) < width) {
                            if (y < -bottom) matrix2.postTranslate(0f, -(y + bottom))
                            else if (y > 0) matrix2.postTranslate(0f, -y)
                        } else {
                            if (x < -right) matrix2.postTranslate(-(x + right), 0f)
                            else if (x > 0) matrix2.postTranslate(-x, 0f)
                        }
                    }
                }
            } else {
                matrix2.postScale(mScaleFactor, mScaleFactor, detector.focusX, detector.focusY)
                matrix2.getValues(m)
                val x = m[Matrix.MTRANS_X]
                val y = m[Matrix.MTRANS_Y]
                if (mScaleFactor < 1) {
                    if (x < -right) matrix2.postTranslate(-(x + right), 0f)
                    else if (x > 0) matrix2.postTranslate(-x, 0f)
                    if (y < -bottom) matrix2.postTranslate(0f, -(y + bottom))
                    else if (y > 0) matrix2.postTranslate(0f, -y)
                }
            }
            return true
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        width = MeasureSpec.getSize(widthMeasureSpec).toFloat()
        height = MeasureSpec.getSize(heightMeasureSpec).toFloat()
        val scale: Float
        val scaleX = width / bmWidth
        val scaleY = height / bmHeight
        scale = min(scaleX.toDouble(), scaleY.toDouble()).toFloat()
        matrix2.setScale(scale, scale)
        imageMatrix = matrix2
        saveScale = 1f
        redundantYSpace = height - (scale * bmHeight)
        redundantXSpace = width - (scale * bmWidth)
        redundantYSpace /= 2f
        redundantXSpace /= 2f
        matrix2.postTranslate(redundantXSpace, redundantYSpace)
        origWidth = width - 2 * redundantXSpace
        origHeight = height - 2 * redundantYSpace
        right = width * saveScale - width - (2 * redundantXSpace * saveScale)
        bottom = height * saveScale - height - (2 * redundantYSpace * saveScale)
        imageMatrix = matrix2
    }

    companion object {
        const val NONE: Int = 0
        const val DRAG: Int = 1
        const val ZOOM: Int = 2
        const val CLICK: Int = 3
    }
}
