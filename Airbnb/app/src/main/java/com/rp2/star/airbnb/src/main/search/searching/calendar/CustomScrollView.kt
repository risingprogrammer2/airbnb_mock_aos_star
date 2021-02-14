package com.rp2.star.airbnb.src.main.search.searching.calendar

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.core.widget.NestedScrollView

class CustomScrollView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): NestedScrollView(context, attrs, defStyleAttr) {

    /* 스크롤 가능한지 */ var scrollable = true

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                super.onTouchEvent(ev) && scrollable
            }
            else -> super.onTouchEvent(ev)
        }
    }

    /*override fun onTouchEvent(ev: MotionEvent): Boolean {
        return when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                val dragShadowBuilder = View.DragShadowBuilder(this)
                this.startDrag(null,dragShadowBuilder,this,0)
                true
            }
            else ->
                false
        }
    }*/

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return super.onInterceptTouchEvent(ev) && scrollable
    }


   /* override fun onDragEvent(dragEvent: DragEvent?): Boolean {
        return when(dragEvent!!.action){
            DragEvent.ACTION_DRAG_STARTED -> {
                super.onDragEvent(dragEvent) && scrollable
            }
            else -> super.onDragEvent(dragEvent)
        }
    }*/


}
