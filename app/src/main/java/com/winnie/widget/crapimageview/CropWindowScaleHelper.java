package com.winnie.widget.crapimageview;

import android.graphics.RectF;

/**
 * @author : winnie
 * @date : 2018/11/9
 * @desc 操控裁剪框的辅助类:操控裁剪框的缩放
 */
public class CropWindowScaleHelper {

    private Edge mHorizontalEdge;
    private Edge mVerticalEdge;

    public CropWindowScaleHelper(Edge horizontalEdge, Edge verticalEdge) {
        mHorizontalEdge = horizontalEdge;
        mVerticalEdge = verticalEdge;
    }

    /**
     * 随着手指的移动而改变裁剪框的大小
     *
     * @param x         手指x方向的位置
     * @param y         手指y方向的位置
     * @param imageRect 用来表示图片边界的矩形
     */
    public void updateCropWindow(float x, float y, RectF imageRect){
        if (mHorizontalEdge != null) {
            mHorizontalEdge.updateCoordinate(x, y, imageRect);
        }

        if (mVerticalEdge != null) {
            mVerticalEdge.updateCoordinate(x, y, imageRect);
        }
    }
}
