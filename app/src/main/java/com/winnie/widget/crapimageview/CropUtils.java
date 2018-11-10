package com.winnie.widget.crapimageview;

import android.content.Context;
import android.graphics.PointF;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author : winnie
 * @date : 2018/11/10
 * @desc
 */
public class CropUtils {
    /**
     * +0.5的作用是为了适用于程序中的四舍五入
     * 例如5.6f在java中转换成int类型的话是5，所以加上0.5从而变成6，提高了精度。
     */
    public static int dip2px(@Nullable Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    public static CropWindowEdgeSelector getPressedHandle(float x, float y,
                                                          float left, float top, float right, float bottom,
                                                          float targetRadius) {

        CropWindowEdgeSelector nearestCropWindowEdgeSelector = null;

        //判断手指距离裁剪框哪一个角最近

        //最近距离默认正无穷大
        float nearestDistance = Float.POSITIVE_INFINITY;

        //计算手指距离左上角的距离
        final float distanceToTopLeft = calculateDistance(x, y, left, top);
        if (distanceToTopLeft < nearestDistance) {
            nearestDistance = distanceToTopLeft;
            nearestCropWindowEdgeSelector = CropWindowEdgeSelector.TOP_LEFT;
        }


        //计算手指距离右上角的距离
        final float distanceToTopRight = calculateDistance(x, y, right, top);
        if (distanceToTopRight < nearestDistance) {
            nearestDistance = distanceToTopRight;
            nearestCropWindowEdgeSelector = CropWindowEdgeSelector.TOP_RIGHT;
        }

        //计算手指距离左下角的距离
        final float distanceToBottomLeft = calculateDistance(x, y, left, bottom);
        if (distanceToBottomLeft < nearestDistance) {
            nearestDistance = distanceToBottomLeft;
            nearestCropWindowEdgeSelector = CropWindowEdgeSelector.BOTTOM_LEFT;
        }

        //计算手指距离右下角的距离
        final float distanceToBottomRight = calculateDistance(x, y, right, bottom);
        if (distanceToBottomRight < nearestDistance) {
            nearestDistance = distanceToBottomRight;
            nearestCropWindowEdgeSelector = CropWindowEdgeSelector.BOTTOM_RIGHT;
        }

        //如果手指选中了一个最近的角，并且在缩放范围内则返回这个角
        if (nearestDistance <= targetRadius) {
            return nearestCropWindowEdgeSelector;
        }


        ///判断手指是否四个边的某条边上
        if (isInHorizontalTargetZone(x, y, left, right, top, targetRadius)) {
            //说明手指在裁剪框top区域
            return CropWindowEdgeSelector.TOP;
        } else if (isInHorizontalTargetZone(x, y, left, right, bottom, targetRadius)) {
            //说明手指在裁剪框bottom区域
            return CropWindowEdgeSelector.BOTTOM;
        } else if (isInVerticalTargetZone(x, y, left, top, bottom, targetRadius)) {
            //说明手指在裁剪框left区域
            return CropWindowEdgeSelector.LEFT;
        } else if (isInVerticalTargetZone(x, y, right, top, bottom, targetRadius)) {
            //说明手指在裁剪框right区域
            return CropWindowEdgeSelector.RIGHT;
        }


        //判断手指是在裁剪框的中间
        if (isWithinBounds(x, y, left, top, right, bottom)) {
            return CropWindowEdgeSelector.CENTER;
        }

        //手指位于裁剪框外面位置，此时移动手指什么都不做
        return null;
    }

    public static void getOffset(@NonNull CropWindowEdgeSelector cropWindowEdgeSelector,
                                 float x, float y, float left, float top, float right, float bottom,
                                 @NonNull PointF touchOffsetOutput) {

        float touchOffsetX = 0;
        float touchOffsetY = 0;

        switch (cropWindowEdgeSelector) {
            case TOP_LEFT:
                touchOffsetX = left - x;
                touchOffsetY = top - y;
                break;
            case TOP_RIGHT:
                touchOffsetX = right - x;
                touchOffsetY = top - y;
                break;
            case BOTTOM_LEFT:
                touchOffsetX = left - x;
                touchOffsetY = bottom - y;
                break;
            case BOTTOM_RIGHT:
                touchOffsetX = right - x;
                touchOffsetY = bottom - y;
                break;
            case LEFT:
                touchOffsetX = left - x;
                touchOffsetY = 0;
                break;
            case TOP:
                touchOffsetX = 0;
                touchOffsetY = top - y;
                break;
            case RIGHT:
                touchOffsetX = right - x;
                touchOffsetY = 0;
                break;
            case BOTTOM:
                touchOffsetX = 0;
                touchOffsetY = bottom - y;
                break;
            case CENTER:
                final float centerX = (right + left) / 2;
                final float centerY = (top + bottom) / 2;
                touchOffsetX = centerX - x;
                touchOffsetY = centerY - y;
                break;
            default:
                break;
        }

        touchOffsetOutput.x = touchOffsetX;
        touchOffsetOutput.y = touchOffsetY;
    }


    private static boolean isInHorizontalTargetZone(float x, float y, float handleXStart, float handleXEnd,
                                                    float handleY, float targetRadius) {

        return (x > handleXStart && x < handleXEnd && Math.abs(y - handleY) <= targetRadius);
    }


    private static boolean isInVerticalTargetZone(float x, float y, float handleX, float handleYStart,
                                                  float handleYEnd, float targetRadius) {
        return (Math.abs(x - handleX) <= targetRadius && y > handleYStart && y < handleYEnd);
    }

    private static boolean isWithinBounds(float x, float y, float left, float top, float right, float bottom) {
        return x >= left && x <= right && y >= top && y <= bottom;
    }

    /**
     * 计算 (x1, y1) 和 (x2, y2)两个点的距离
     */
    private static float calculateDistance(float x1, float y1, float x2, float y2) {
        final float side1 = x2 - x1;
        final float side2 = y2 - y1;
        return (float) Math.sqrt(side1 * side1 + side2 * side2);
    }
}
