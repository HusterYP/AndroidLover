package com.hust.ping.androidlover.widget

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.RequiresApi
import com.hust.ping.androidlover.utils.AndroidLoverPreference
import com.hust.ping.androidlover.utils.ScreenUtil
import com.hust.ping.androidlover.utils.unLock

/**
 * @created by PingYuan at 12/9/18
 * @email: husteryp@gmail.com
 * @description:
 */
class GestureUnLock : View {

    class CircleView {
        var x = 0
        var y = 0
        val innerRadius = ScreenUtil.dp2px(15f)
        val outerRadius = ScreenUtil.dp2px(30f)
        val dotRadius = ScreenUtil.dp2px(5f)
        var selected = false
        var index = 0

        fun isSelected(destX: Int, destY: Int): Boolean {
            if (destX <= x + outerRadius && destX >= x - outerRadius
                && destY <= y + outerRadius && destY >= y - outerRadius) {
                selected = true
                return true
            }
            return false
        }
    }

    interface UnLockState {
        fun unLockSuccess()
        fun unLockError()
        fun confirmLockSuccess()
        fun confirmError()
        fun confirmLock()
    }

    private var circles = Array(9) { CircleView() }
    private val innerPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val outerPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val pathPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val dotPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val pathCircle = ArrayList<CircleView>()
    private var mPath = Path()
    private var curPoint = PointF()
    private var unLockState: UnLockState? = null

    private val MARGIN = ScreenUtil.dp2px(20f)
    private val OUTTER_WIDTH = 10f
    private val PATH_WIDTH = 15f
    private var isAnimate = false
    private var hasConfirm = false

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    init {
        innerPaint.style = Paint.Style.FILL
        innerPaint.color = Color.parseColor("#30A9DE")
        outerPaint.style = Paint.Style.STROKE
        outerPaint.strokeWidth = OUTTER_WIDTH
        outerPaint.color = Color.parseColor("#8CD790")
        pathPaint.style = Paint.Style.STROKE
        pathPaint.color = Color.parseColor("#A593E0")
        pathPaint.strokeWidth = PATH_WIDTH
        pathPaint.strokeCap = Paint.Cap.ROUND
        dotPaint.style = Paint.Style.FILL
        dotPaint.color = Color.parseColor("#A593E0")
        var index = 0
        circles.forEach {
            it.index = index++
        }
        curPoint.x = -1f
        curPoint.y = -1f
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null && !isAnimate) {
            when(event.action) {
                MotionEvent.ACTION_DOWN -> {
                    val curCircle = selectCircle(event.x.toInt(), event.y.toInt())
                    curCircle?.apply {
                        pathCircle.add(this)
                    }
                    invalidate()
                }
                MotionEvent.ACTION_MOVE -> {
                    val curCircle = selectCircle(event.x.toInt(), event.y.toInt())
                    if (curCircle != null) {
                        addPathCircle(curCircle)
                        curPoint.x = -1f
                        curPoint.y = -1f
                    }
                    else {
                        curPoint.x = event.x
                        curPoint.y = event.y
                    }
                    invalidate()
                }
                MotionEvent.ACTION_UP -> {
                    curPoint.x = -1f
                    curPoint.y = -1f
                    checkUnLockState()
                    invalidate()
                }
            }
        }
        return true
    }

    private fun checkUnLockState() {
        val indexStr = StringBuilder()
        pathCircle.forEach {
            indexStr.append(it.index)
        }
        when {
            AndroidLoverPreference.unLock.isEmpty() -> {
                AndroidLoverPreference.unLock = indexStr.toString()
                reset(true)
                unLockState?.confirmLock()
            }
            AndroidLoverPreference.unLock == indexStr.toString() -> {
                unLockState?.unLockSuccess()
                unLockState?.confirmLockSuccess()
                hasConfirm = true
                reset(true)
            }
            else -> {
                unLockState?.unLockError()
                unLockState?.confirmError()
                reset(false)
            }
        }
    }

    private fun reset(success: Boolean) {
        if (!success) {
            pathPaint.color = Color.parseColor("#F9320C")
            outerPaint.color = Color.parseColor("#F9320C")
            isAnimate = true
            postDelayed({
                pathCircle.clear()
                circles.forEach {
                    it.selected = false
                }
                isAnimate = false
                invalidate()
            }, 1500)
        } else if (!hasConfirm){
            pathCircle.clear()
            circles.forEach {
                it.selected = false
            }
            invalidate()
        }
    }

    private fun addPathCircle(circle: GestureUnLock.CircleView) {
        if (pathCircle.size <= 0) {
            pathCircle.add(circle)
            return
        }
        val last = pathCircle.last()
        // 同一行
        if (last.index / 3 == circle.index / 3 && Math.abs(last.index - circle.index) >= 2) {
            pathCircle.add(circles[(last.index + circle.index) / 2])
            circles[(last.index + circle.index) / 2].selected = true
        }
        // 同一列
        if (last.index % 3 == circle.index % 3 && Math.abs(last.index - circle.index) >= 6) {
            pathCircle.add(circles[(last.index + circle.index) / 2])
            circles[(last.index + circle.index) / 2].selected = true
        }
        // 两条对角线
        if ((Math.abs(last.index - circle.index) == 8) ||
            (last.index == 2 && circle.index == 6) ||
            (last.index == 6 && circle.index == 2)) {
            circles[4].selected = true
            pathCircle.add(circles[4])
        }
        if (last.index != circle.index) pathCircle.add(circle)
    }

    private fun selectCircle(destX: Int, destY: Int): GestureUnLock.CircleView? {
        circles.forEach {
            if (it.isSelected(destX, destY))
                return it
        }
        return null
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = minOf(ScreenUtil.getDeviceWidth(), ScreenUtil.getDeviceHeight()) - MARGIN.toInt() * 2
        for (i in 0 .. 8) {
            val row = i / 3
            val col = i % 3
            circles[i].x = width / 3 * (col + 1) - width / 6
            circles[i].y = width / 3 * (row + 1) - width / 6
        }
        setMeasuredDimension(width, width)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.apply {
            drawInnerCircle(canvas)
            drawPath(canvas)
        }
        outerPaint.color = Color.parseColor("#8CD790")
        pathPaint.color = Color.parseColor("#A593E0")
        innerPaint.color = Color.parseColor("#30A9DE")
    }

    private fun drawInnerCircle(canvas: Canvas) {
        circles.forEach {
            canvas.drawCircle(it.x.toFloat(), it.y.toFloat(), it.innerRadius, innerPaint)
            if (it.selected)
                drawOuterCircle(canvas, it)
        }
    }

    private fun drawOuterCircle(canvas: Canvas, circle: GestureUnLock.CircleView? = null) {
        circle?.apply {
            canvas.drawCircle(this.x.toFloat(), this.y.toFloat(), this.outerRadius, outerPaint)
            canvas.drawCircle(this.x.toFloat(), this.y.toFloat(), this.dotRadius, dotPaint)
        }
    }

    private fun drawPath(canvas: Canvas) {
        mPath.reset()
        if (pathCircle.size <= 0)
            return
        mPath.moveTo(pathCircle[0].x.toFloat(), pathCircle[0].y.toFloat())
        val count = pathCircle.size
        for (i in 1 until count) {
            mPath.lineTo(pathCircle[i].x.toFloat(), pathCircle[i].y.toFloat())
        }
        if (curPoint.x >= 0 && curPoint.y >= 0)
            mPath.lineTo(curPoint.x, curPoint.y)
        canvas.drawPath(mPath, pathPaint)
    }

    fun setUnLockListener(unLockState: GestureUnLock.UnLockState) {
        this.unLockState = unLockState
    }
}