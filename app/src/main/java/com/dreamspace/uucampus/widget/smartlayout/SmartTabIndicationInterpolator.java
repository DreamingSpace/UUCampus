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

import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

public abstract class SmartTabIndicationInterpolator {

    public static final SmartTabIndicationInterpolator SMART = new SmartIndicationInterpolator();
    public static final SmartTabIndicationInterpolator LINEAR = new LinearIndicationInterpolator();

    static final int ID_SMART = 0;
    static final int ID_LINEAR = 1;

    public static SmartTabIndicationInterpolator of(int id) {
        switch (id) {
            case ID_SMART:
                return SMART;
            case ID_LINEAR:
                return LINEAR;
            default:
                throw new IllegalArgumentException("Unknown id: " + id);
        }
    }

    public abstract float getLeftEdge(float offset);

    public abstract float getRightEdge(float offset);

    public float getThickness(float offset) {
        return 1f; //Always the same thickness by default
    }

    public static class SmartIndicationInterpolator extends SmartTabIndicationInterpolator {

        private static final float DEFAULT_INDICATOR_INTERPOLATION_FACTOR = 3.0f;

        private final Interpolator mLeftEdgeInterpolator;
        private final Interpolator mRightEdgeInterpolator;

        public SmartIndicationInterpolator() {
            this(DEFAULT_INDICATOR_INTERPOLATION_FACTOR);
        }

        public SmartIndicationInterpolator(float factor) {
            mLeftEdgeInterpolator = new AccelerateInterpolator(factor);
            mRightEdgeInterpolator = new DecelerateInterpolator(factor);
        }

        @Override
        public float getLeftEdge(float offset) {
            return mLeftEdgeInterpolator.getInterpolation(offset);
        }

        @Override
        public float getRightEdge(float offset) {
            return mRightEdgeInterpolator.getInterpolation(offset);
        }

        @Override
        public float getThickness(float offset) {
            return 1f / (1.0f - getLeftEdge(offset) + getRightEdge(offset));
        }

    }

    public static class LinearIndicationInterpolator extends SmartTabIndicationInterpolator {

        @Override
        public float getLeftEdge(float offset) {
            return offset;
        }

        @Override
        public float getRightEdge(float offset) {
            return offset;
        }

    }
}
