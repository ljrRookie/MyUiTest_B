package com.example.myuitest_b;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class DragView extends View {
    /**
     * 圆半径
     */
    private float mBubbleRadius = 100f;
    private PointF mBubMovableCenter;
    private Paint mBubblePaint;
    private Context mContext;
    private float mDist;

    public DragView(Context context) {
        this(context, null);
        mContext = context;
    }

    public DragView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //抗锯齿
        mBubblePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBubblePaint.setColor(Color.RED);
        mBubblePaint.setStyle(Paint.Style.FILL);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (mBubMovableCenter == null) {
            mBubMovableCenter = new PointF(w / 2, h / 2);
        } else {
            mBubMovableCenter.set(w / 2, h / 2);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mBubMovableCenter.x, mBubMovableCenter.y, mBubbleRadius, mBubblePaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDist = (float) Math.hypot(event.getX() - mBubMovableCenter.x, event.getY() - mBubMovableCenter.y);
                if (mDist < mBubbleRadius) {
                    Toast.makeText(mContext, mContext.getResources().getString(R.string.drag), Toast.LENGTH_SHORT).show();
                    Log.e(">>>>>", "点到我啦，可以滑动啦");
                } else {
                    Toast.makeText(mContext, mContext.getResources().getString(R.string.not_drag), Toast.LENGTH_SHORT).show();
                    Log.e(">>>>>", "要点到我才可以拖动");
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mDist < mBubbleRadius) {
                    mBubMovableCenter.x = event.getX();
                    mBubMovableCenter.y = event.getY();
                    invalidate();

                } 
                break;
            case MotionEvent.ACTION_UP:
                break;

        }
        return true;
    }
}
