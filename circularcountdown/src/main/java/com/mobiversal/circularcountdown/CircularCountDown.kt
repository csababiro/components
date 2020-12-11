package com.mobiversal.circularcountdown

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Handler
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View


/**
 * Created by Biro Csaba on 11/12/2020.
 */
class CircularCountDown @JvmOverloads constructor(context: Context, val attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private var backgroundPaint: Paint? = null
    private var progressPaint: Paint? = null
    private var textPaint: Paint? = null

    private var startTime: Long = 0
    private var currentTime: Long = 0
    private var maxTime: Long = 0

    private var progressMillisecond: Long = 0
    private var progress = 0.0

    private var circleBounds: RectF? = null
    private var radius = 0f
    private var handleRadius = 0f
    private var textHeight = 0f
    private var textOffset = 0f

    private var viewHandler: Handler? = null
    private var updateView: Runnable? = null

    init {
        initView()
    }

    private fun initView() {
        // used to fit the circle into
        // used to fit the circle into
        circleBounds = RectF()

        // size of circle and handle

        // size of circle and handle
        radius = 200F
        handleRadius = 10F

        // limit the counter to go up to maxTime ms

        // limit the counter to go up to maxTime ms
        maxTime = 25000

        // start and current time

        // start and current time
        startTime = System.currentTimeMillis()
        currentTime = startTime


        // the style of the background


        // the style of the background
        backgroundPaint = Paint()
        backgroundPaint?.style = Paint.Style.STROKE
        backgroundPaint?.isAntiAlias = true
        backgroundPaint?.strokeWidth = 10F
        backgroundPaint?.strokeCap = Paint.Cap.SQUARE
        backgroundPaint?.color = Color.parseColor("#4D4D4D") // dark gray


        // the style of the 'progress'

        // the style of the 'progress'
        progressPaint = Paint()
        progressPaint?.style = Paint.Style.STROKE
        progressPaint?.isAntiAlias = true
        progressPaint?.strokeWidth = 10F
        progressPaint?.strokeCap = Paint.Cap.SQUARE
        progressPaint?.color = Color.parseColor("#00A9FF") // light blue


        // the style for the text in the middle

        // the style for the text in the middle
        textPaint = TextPaint()
        textPaint?.textSize = radius / 2
        textPaint?.color = Color.BLACK
        textPaint?.textAlign = Paint.Align.CENTER

        // text attributes

        // text attributes
        textHeight = textPaint?.descent() ?: 0F - (textPaint?.ascent() ?: 0F)
        textOffset = textHeight / 2 - (textPaint?.descent() ?: 0F)


        // This will ensure the animation will run periodically


        // This will ensure the animation will run periodically
        viewHandler = Handler()
        updateView = Runnable { // update current time
            currentTime = System.currentTimeMillis()

            // get elapsed time in milliseconds and clamp between <0, maxTime>
            progressMillisecond = (currentTime - startTime) % maxTime

            // get current progress on a range <0, 1>
            progress = progressMillisecond.toDouble() / maxTime
            this@CircularCountDown.invalidate()
            viewHandler?.postDelayed(updateView!!, 1000 / 60)
        }
        viewHandler?.post(updateView!!)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        // get the center of the view
        // get the center of the view
        val centerWidth = (canvas!!.width / 2).toFloat()
        val centerHeight = (canvas!!.height / 2).toFloat()


        // set bound of our circle in the middle of the view


        // set bound of our circle in the middle of the view
        circleBounds!![centerWidth - radius, centerHeight - radius, centerWidth + radius] =
            centerHeight + radius


        // draw background circle


        // draw background circle
        canvas!!.drawCircle(centerWidth, centerHeight, radius, backgroundPaint!!)

        // we want to start at -90°, 0° is pointing to the right

        // we want to start at -90°, 0° is pointing to the right
        canvas!!.drawArc(
            circleBounds!!,
            (-90).toFloat(),
            (progress * 360).toFloat(), false,
            progressPaint!!
        )

        // display text inside the circle

        // display text inside the circle
        canvas!!.drawText(
            ((progressMillisecond / 100).toDouble() / 10).toString() + "s",
            centerWidth,
            centerHeight + textOffset,
            textPaint!!
        )

        // draw handle or the circle

        // draw handle or the circle
        canvas!!.drawCircle(
            (centerWidth + Math.sin(progress * 2 * Math.PI) * radius).toFloat(),
            (centerHeight - Math.cos(progress * 2 * Math.PI) * radius).toFloat(),
            handleRadius,
            progressPaint!!
        )
    }
}