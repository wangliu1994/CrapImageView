package com.winnie.widget.crapimageview;

import android.graphics.RectF;

import androidx.annotation.Nullable;

/**
 * @author : winnie
 * @date : 2018/11/9
 * @desc
 */
public enum Edge {
    // 对应着裁剪框的上下左右
    // 使用枚举类型，防止再实例化其他的Edge类型
    LEFT,
    TOP,
    RIGHT,
    BOTTOM;

    /**
     * 裁剪框的最小宽度或者高度
     */
    private final int MIN_CROP_LENGTH_PX = 150;

    /**
     * 上下左右边界的的坐标值，比如LEFT，RIGHT两条边的值是对应的边距离图片最左边的距离
     */
    private float mCoordinate;

    public void initCoordinate(float coordinate) {
        mCoordinate = coordinate;
    }

    /**
     * 随着手指的移动而改变坐标值
     */
    public void offset(float distance) {
        mCoordinate += distance;
    }

    public float getCoordinate() {
        return mCoordinate;
    }

    /**
     * 更新某条边的坐标位置
     */
    public void updateCoordinate(float x, float y,  @Nullable RectF imageRect) {
        switch (this) {
            case LEFT:
                mCoordinate = addJustLeft(x, imageRect);
                break;
            case TOP:
                mCoordinate = addJustTop(y, imageRect);
                break;
            case RIGHT:
                mCoordinate = addJustRight(x, imageRect);
                break;
            case BOTTOM:
            default:
                mCoordinate = addJustBottom(y, imageRect);
                break;
        }
    }

    /**
     * 获取剪切框的宽
     */
    public static float getWidth() {
        return Edge.RIGHT.getCoordinate() - Edge.LEFT.getCoordinate();
    }

    /**
     * 获取剪切框的高
     */
    public static float getHeight() {
        return Edge.BOTTOM.getCoordinate() - Edge.TOP.getCoordinate();
    }

    private float addJustLeft(float x, @Nullable RectF imageRect){
        final float resultX;
        //左边越界
        if(x < imageRect.left){
            resultX = imageRect.left;
        }else {
            //裁剪框左边超过右边最小范围
            float right = Edge.RIGHT.getCoordinate();
            if(x > right - MIN_CROP_LENGTH_PX){
                x = right - MIN_CROP_LENGTH_PX;
            }
            resultX = x;
        }
        return resultX;
    }

    private float addJustTop(float y, @Nullable RectF imageRect){
        final float resultY;
        //左边越界
        if(y < imageRect.top){
            resultY = imageRect.top;
        }else {
            //裁剪框上边超过下边最小范围
            float bottom = Edge.BOTTOM.getCoordinate();
            if(y > bottom - MIN_CROP_LENGTH_PX){
                y = bottom - MIN_CROP_LENGTH_PX;
            }
            resultY = y;
        }
        return resultY;
    }

    private float addJustRight(float x, @Nullable RectF imageRect){
        final float resultX;
        //左边越界
        if(x > imageRect.right){
            resultX = imageRect.right;
        }else {
            //裁剪框右边超过左边或者最小范围
            float left = Edge.LEFT.getCoordinate();
            if(x < left + MIN_CROP_LENGTH_PX){
                x = left + MIN_CROP_LENGTH_PX;
            }
            resultX = x;
        }
        return resultX;
    }

    private float addJustBottom(float y, @Nullable RectF imageRect){
        final float resultY;
        //左边越界
        if(y > imageRect.bottom){
            resultY = imageRect.bottom;
        }else {
            //裁剪框左边超过右边或者最小范围
            float top = Edge.TOP.getCoordinate();
            if(y < top + MIN_CROP_LENGTH_PX){
                y = top + MIN_CROP_LENGTH_PX;
            }
            resultY = y;
        }
        return resultY;
    }

    /**
     * 判断裁剪框是否超越图片指定的边界
     */
    public boolean isOutside(@Nullable RectF rectF){
        final boolean result;

        switch (this) {
            case LEFT:
                result = mCoordinate < rectF.left;
                break;
            case TOP:
                result = mCoordinate < rectF.top;
                break;
            case RIGHT:
                result = mCoordinate > rectF.right;
                break;
            case BOTTOM:
            default:
                result = mCoordinate > rectF.bottom;
                break;
        }
        return result;
    }
}
