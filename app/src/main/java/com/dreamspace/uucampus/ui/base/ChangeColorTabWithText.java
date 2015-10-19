package com.dreamspace.uucampus.ui.base;

/**
 * Created by money on 2015/9/14.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.dreamspace.uucampus.R;

public class ChangeColorTabWithText extends View {
    private Bitmap mBitmap;
    private String text;
    private int mColor;
    private int textSize;
    private Canvas mCanvas;
    private Bitmap mBitmapInMem;
    private Paint mPaint;
    private float mAlpha=0.0f;
    private Rect mIconRect;
    private Rect mTextRect;
    private Paint mTextPaint;
    public ChangeColorTabWithText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChangeColorTabWithText(Context context) {
        this(context, null);
    }

    public ChangeColorTabWithText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        System.out.println("CONSTRUCT IN VIEW");
        TypedArray mArray=context.obtainStyledAttributes(attrs, R.styleable.ChangeColorTabWithText);
        for(int i=0;i<mArray.getIndexCount();i++){
            int attr=mArray.getIndex(i);
            switch (attr){
                case R.styleable.ChangeColorTabWithText_maincolor:
                    mColor= mArray.getColor(attr,0xFF45C01A);
                    break;
                case R.styleable.ChangeColorTabWithText_drawable:
                    BitmapDrawable drawable=(BitmapDrawable)mArray.getDrawable(attr);
                    mBitmap=drawable.getBitmap();
                    break;
                case R.styleable.ChangeColorTabWithText_text:
                    text=mArray.getText(attr).toString();
                    break;
                case R.styleable.ChangeColorTabWithText_textsize:
                    textSize= (int) mArray.getDimension(attr, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    break;
            }
        }
        mArray.recycle();
        mTextRect=new Rect();
        mTextPaint=new Paint();
        mTextPaint.setTextSize(textSize);
        mTextPaint.setColor(0xff555555);
        mTextPaint.getTextBounds(text, 0, text.length(), mTextRect);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int iconwidth=Math.min(getMeasuredWidth()-getPaddingLeft()-getPaddingRight(),getMeasuredHeight()-getPaddingBottom()-getPaddingTop()-mTextRect.height());
        int left=getMeasuredWidth()/2-iconwidth/2;
        int top=getMeasuredHeight()/2-(iconwidth+mTextRect.height())/2;
        mIconRect=new Rect(left,top,left+iconwidth,top+iconwidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制原图
        canvas.drawBitmap(mBitmap, null, mIconRect, null);
        int alpha= (int) Math.ceil(255*mAlpha);
        //初始化mBitmap,并且通过两个BITMAP的合成来获得最终的填色版本的tab icon
        setUpTargetBitmap(alpha);
        //使用view依附的画布来将上一步生成的bitmap绘制出来，由于设置了mBitmapInMem的大小是整个控件，因此在设置绘制位置的时候从（0,0）开始就好
        canvas.drawBitmap(mBitmapInMem, 0, 0, null);
        drawSourceText(canvas, alpha);
        drawTargetText(canvas, alpha);
    }

    private void drawTargetText(Canvas canvas, int alpha) {
        mTextPaint.setColor(mColor);
        mTextPaint.setAlpha(alpha);
        canvas.drawText(text, mIconRect.left + mIconRect.width() / 2 - mTextRect.width() / 2, mIconRect.bottom + mTextRect.height(), mTextPaint);
    }

    private void drawSourceText(Canvas canvas,int alpha) {
        mTextPaint.setColor(0xff333333);
        mTextPaint.setAlpha(255 - alpha);
        canvas.drawText(text,mIconRect.left+mIconRect.width()/2-mTextRect.width()/2,mIconRect.bottom+mTextRect.height(),mTextPaint);
    }

    private void setUpTargetBitmap(int alpha) {
        //声明一个临时bitmap用于存放结果
        mBitmapInMem = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        //将结果与画布关联
        mCanvas = new Canvas(mBitmapInMem);
        //在图标矩形区域内绘制一个纯色的填满区域，作为将要填充的bitmap，即dst bitmap
        mPaint = new Paint();
        mPaint.setColor(mColor);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setAlpha(alpha);
        mCanvas.drawRect(mIconRect, mPaint);
        //设置当两个bitmap重叠时的处理方式。DST_IN：形状为交集形状，颜色使用dst的bitmap的颜色
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mPaint.setAlpha(255);
        //设置src的bitmap为用户XML中提供的bitmap，画在新声明的这个canvas中，与dst bitmap以DST_IN的方式进行重叠，最终生成的bitmap可通过mBitmapInMem来获取
        mCanvas.drawBitmap(mBitmap, null, mIconRect, mPaint);
    }
    public void setIconAlpha(float alpha){
        mAlpha=alpha;
        validateView();
    }

    private void validateView() {
        if(Looper.getMainLooper()==Looper.myLooper()){
            invalidate();
        }else{
            postInvalidate();
        }
    }

}
