/*
 * Copyright (c) 2015 [1076559197@qq.com | tchen0707@gmail.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dreamspace.uucampus.widget.smartlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import com.dreamspace.uucampus.R;


/**
 * <p>
 * Forked from Google Samples &gt; SlidingTabsBasic &gt;
 * <a href="https://developer.android.com/samples/SlidingTabsBasic/src/com.example.android.common/view/SlidingTabLayout.html">SlidingTabStrip</a>
 */
class SmartTabStrip extends LinearLayout {

    private static final int DEFAULT_BOTTOM_BORDER_THICKNESS_DIPS = 2;

    private static final byte DEFAULT_BOTTOM_BORDER_COLOR_ALPHA = 0x26;

    private static final int SELECTED_INDICATOR_THICKNESS_DIPS = 8;

    private static final int DEFAULT_SELECTED_INDICATOR_COLOR = 0xFF33B5E5;

    private static final float DEFAULT_INDICATOR_CORNER_RADIUS = 0f;

    private static final int DEFAULT_DIVIDER_THICKNESS_DIPS = 1;

    private static final byte DEFAULT_DIVIDER_COLOR_ALPHA = 0x20;

    private static final float DEFAULT_DIVIDER_HEIGHT = 0.5f;

    private static final boolean DEFAULT_INDICATOR_IN_CENTER = false;

    private static final boolean DEFAULT_INDICATOR_IN_FRONT = false;

    private final int mBottomBorderThickness;

    private final Paint mBottomBorderPaint;

    private final RectF mIndicatorRectF = new RectF();

    private final boolean mIndicatorAlwaysInCenter;

    private final boolean mIndicatorInFront;

    private final int mIndicatorThickness;

    private final float mIndicatorCornerRadius;

    private final Paint mIndicatorPaint;

    private final Paint mDividerPaint;

    private final float mDividerHeight;

    private int mLastPosition;

    private int mSelectedPosition;

    private float mSelectionOffset;

    private SmartTabIndicationInterpolator mIndicationInterpolator;

    private SmartTabLayout.TabColorizer mCustomTabColorizer;

    private final SimpleTabColorizer mDefaultTabColorizer;

    SmartTabStrip(Context context, AttributeSet attrs) {
        super(context);
        setWillNotDraw(false);

        final float density = getResources().getDisplayMetrics().density;

        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(android.R.attr.colorForeground, outValue, true);
        final int themeForegroundColor = outValue.data;

        boolean indicatorInFront = DEFAULT_INDICATOR_IN_FRONT;
        boolean indicatorAlwaysInCenter = DEFAULT_INDICATOR_IN_CENTER;
        int indicationInterpolatorId = SmartTabIndicationInterpolator.ID_SMART;
        int indicatorColor = DEFAULT_SELECTED_INDICATOR_COLOR;
        int indicatorColorsId = NO_ID;
        int indicatorThickness = (int) (SELECTED_INDICATOR_THICKNESS_DIPS * density);
        float indicatorCornerRadius = DEFAULT_INDICATOR_CORNER_RADIUS * density;
        int underlineColor = setColorAlpha(themeForegroundColor, DEFAULT_BOTTOM_BORDER_COLOR_ALPHA);
        int underlineThickness = (int) (DEFAULT_BOTTOM_BORDER_THICKNESS_DIPS * density);
        int dividerColor = setColorAlpha(themeForegroundColor, DEFAULT_DIVIDER_COLOR_ALPHA);
        int dividerColorsId = NO_ID;
        int dividerThickness = (int) (DEFAULT_DIVIDER_THICKNESS_DIPS * density);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.stl_SmartTabLayout);
        indicatorAlwaysInCenter = a.getBoolean(R.styleable.stl_SmartTabLayout_stl_indicatorAlwaysInCenter, indicatorAlwaysInCenter);
        indicatorInFront = a.getBoolean(R.styleable.stl_SmartTabLayout_stl_indicatorInFront, indicatorInFront);
        indicationInterpolatorId = a.getInt(R.styleable.stl_SmartTabLayout_stl_indicatorInterpolation, indicationInterpolatorId);
        indicatorColor = a.getColor(R.styleable.stl_SmartTabLayout_stl_indicatorColor, indicatorColor);
        indicatorColorsId = a.getResourceId(R.styleable.stl_SmartTabLayout_stl_indicatorColors, indicatorColorsId);
        indicatorThickness = a.getDimensionPixelSize(R.styleable.stl_SmartTabLayout_stl_indicatorThickness, indicatorThickness);
        indicatorCornerRadius = a.getDimension(R.styleable.stl_SmartTabLayout_stl_indicatorCornerRadius, indicatorCornerRadius);
        underlineColor = a.getColor(R.styleable.stl_SmartTabLayout_stl_underlineColor, underlineColor);
        underlineThickness = a.getDimensionPixelSize(R.styleable.stl_SmartTabLayout_stl_underlineThickness, underlineThickness);
        dividerColor = a.getColor(R.styleable.stl_SmartTabLayout_stl_dividerColor, dividerColor);
        dividerColorsId = a.getResourceId(R.styleable.stl_SmartTabLayout_stl_dividerColors, dividerColorsId);
        dividerThickness = a.getDimensionPixelSize(R.styleable.stl_SmartTabLayout_stl_dividerThickness, dividerThickness);
        a.recycle();

        final int[] indicatorColors = (indicatorColorsId == NO_ID)
                ? new int[] { indicatorColor }
                : getResources().getIntArray(indicatorColorsId);

        final int[] dividerColors = (dividerColorsId == NO_ID)
                ? new int[] { dividerColor }
                : getResources().getIntArray(dividerColorsId);

        mDefaultTabColorizer = new SimpleTabColorizer();
        mDefaultTabColorizer.setIndicatorColors(indicatorColors);
        mDefaultTabColorizer.setDividerColors(dividerColors);

        mBottomBorderThickness = underlineThickness;
        mBottomBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBottomBorderPaint.setColor(underlineColor);

        mIndicatorAlwaysInCenter = indicatorAlwaysInCenter;
        mIndicatorInFront = indicatorInFront;
        mIndicatorThickness = indicatorThickness;
        mIndicatorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mIndicatorCornerRadius = indicatorCornerRadius;

        mDividerHeight = DEFAULT_DIVIDER_HEIGHT;
        mDividerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDividerPaint.setStrokeWidth(dividerThickness);

        mIndicationInterpolator = SmartTabIndicationInterpolator.of(indicationInterpolatorId);
    }


    void setIndicationInterpolator(SmartTabIndicationInterpolator interpolator) {
        mIndicationInterpolator = interpolator;
        invalidate();
    }

    void setCustomTabColorizer(SmartTabLayout.TabColorizer customTabColorizer) {
        mCustomTabColorizer = customTabColorizer;
        invalidate();
    }

    void setSelectedIndicatorColors(int... colors) {
        // Make sure that the custom colorizer is removed
        mCustomTabColorizer = null;
        mDefaultTabColorizer.setIndicatorColors(colors);
        invalidate();
    }

    void setDividerColors(int... colors) {
        // Make sure that the custom colorizer is removed
        mCustomTabColorizer = null;
        mDefaultTabColorizer.setDividerColors(colors);
        invalidate();
    }

    void onViewPagerPageChanged(int position, float positionOffset) {
        mSelectedPosition = position;
        mSelectionOffset = positionOffset;
        if (positionOffset == 0f && mLastPosition != mSelectedPosition) {
            mLastPosition = mSelectedPosition;
        }
        invalidate();
    }

    boolean isIndicatorAlwaysInCenter() {
        return mIndicatorAlwaysInCenter;
    }

    int getChildMeasuredWidthAt(int index) {
        return getChildAt(index).getMeasuredWidth();
    }

    int getChildWidthAt(int index) {
        return getChildAt(index).getWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        final int height = getHeight();
        final int childCount = getChildCount();
        final int dividerHeightPx = (int) (Math.min(Math.max(0f, mDividerHeight), 1f) * height);
        final SmartTabLayout.TabColorizer tabColorizer = mCustomTabColorizer != null
                ? mCustomTabColorizer
                : mDefaultTabColorizer;

        if (mIndicatorInFront) {
            // Thin underline along the entire bottom edge
            canvas.drawRect(0, height - mBottomBorderThickness, getWidth(), height,
                    mBottomBorderPaint);
        }

        // Thick colored underline below the current selection
        if (childCount > 0) {
            View selectedTitle = getChildAt(mSelectedPosition);
            int left = selectedTitle.getLeft();
            int right = selectedTitle.getRight();
            int color = tabColorizer.getIndicatorColor(mSelectedPosition);
            float thickness = mIndicatorThickness;

            if (mSelectionOffset > 0f && mSelectedPosition < (getChildCount() - 1)) {
                int nextColor = tabColorizer.getIndicatorColor(mSelectedPosition + 1);
                if (color != nextColor) {
                    color = blendColors(nextColor, color, mSelectionOffset);
                }

                // Draw the selection partway between the tabs
                float leftOffset = mIndicationInterpolator.getLeftEdge(mSelectionOffset);
                float rightOffset = mIndicationInterpolator.getRightEdge(mSelectionOffset);
                float thicknessOffset = mIndicationInterpolator.getThickness(mSelectionOffset);

                View nextTitle = getChildAt(mSelectedPosition + 1);
                left = (int) (leftOffset * nextTitle.getLeft() +
                        (1.0f - leftOffset) * left);
                right = (int) (rightOffset * nextTitle.getRight() +
                        (1.0f - rightOffset) * right);
                thickness = thickness * thicknessOffset;
            }

            mIndicatorPaint.setColor(color);
            mIndicatorRectF.set(
                    left, height - (mIndicatorThickness / 2f) - (thickness / 2f),
                    right, height - (mIndicatorThickness / 2f) + (thickness / 2f));

            if (mIndicatorCornerRadius > 0f) {
                canvas.drawRoundRect(
                        mIndicatorRectF, mIndicatorCornerRadius,
                        mIndicatorCornerRadius, mIndicatorPaint);
            } else {
                canvas.drawRect(mIndicatorRectF, mIndicatorPaint);
            }
        }

        if (!mIndicatorInFront) {
            // Thin underline along the entire bottom edge
            canvas.drawRect(0, height - mBottomBorderThickness, getWidth(), height,
                    mBottomBorderPaint);
        }

        // Vertical separators between the titles
        int separatorTop = (height - dividerHeightPx) / 2;
        for (int i = 0; i < childCount - 1; i++) {
            View child = getChildAt(i);
            mDividerPaint.setColor(tabColorizer.getDividerColor(i));
            canvas.drawLine(child.getRight(), separatorTop, child.getRight(),
                    separatorTop + dividerHeightPx, mDividerPaint);
        }
    }

    /**
     * Set the alpha value of the {@code color} to be the given {@code alpha} value.
     */
    private static int setColorAlpha(int color, byte alpha) {
        return Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color));
    }

    /**
     * Blend {@code color1} and {@code color2} using the given ratio.
     *
     * @param ratio of which to blend. 1.0 will return {@code color1}, 0.5 will give an even blend,
     *              0.0 will return {@code color2}.
     */
    private static int blendColors(int color1, int color2, float ratio) {
        final float inverseRation = 1f - ratio;
        float r = (Color.red(color1) * ratio) + (Color.red(color2) * inverseRation);
        float g = (Color.green(color1) * ratio) + (Color.green(color2) * inverseRation);
        float b = (Color.blue(color1) * ratio) + (Color.blue(color2) * inverseRation);
        return Color.rgb((int) r, (int) g, (int) b);
    }

    private static class SimpleTabColorizer implements SmartTabLayout.TabColorizer {

        private int[] mIndicatorColors;

        private int[] mDividerColors;

        @Override
        public final int getIndicatorColor(int position) {
            return mIndicatorColors[position % mIndicatorColors.length];
        }

        @Override
        public final int getDividerColor(int position) {
            return mDividerColors[position % mDividerColors.length];
        }

        void setIndicatorColors(int... colors) {
            mIndicatorColors = colors;
        }

        void setDividerColors(int... colors) {
            mDividerColors = colors;
        }
    }
}
