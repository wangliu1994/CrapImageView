package com.winnie.widget.crapimageview;

import android.graphics.RectF;

/**
 * @author : winnie
 * @date : 2018/11/10
 * @desc 表示手指再裁剪框里面，此时手指移动表明是移动（平移）裁剪框的操作
 */
public class CropWindowMoveHelper extends CropWindowScaleHelper {
    public CropWindowMoveHelper() {
        super(null, null);
    }

    @Override
    public void updateCropWindow(float x, float y, RectF imageRect) {
        //裁剪框四个角的坐标
        float left = Edge.LEFT.getCoordinate();
        float top = Edge.TOP.getCoordinate();
        float right = Edge.RIGHT.getCoordinate();
        float bottom = Edge.BOTTOM.getCoordinate();

        //裁剪框中心位置
        float centerX = (left + right) / 2;
        float centerY = (top + bottom) / 2;

        //手指滑动的距离
        float offsetX = x - centerX;
        float offsetY = y - centerY;

        Edge.LEFT.offset(offsetX);
        Edge.TOP.offset(offsetY);
        Edge.RIGHT.offset(offsetX);
        Edge.BOTTOM.offset(offsetY);

        if(Edge.LEFT.isOutside(imageRect)){
            //此时左边的坐标位置
            float coordinate = Edge.LEFT.getCoordinate();
            //越界的偏移量
            float offset = imageRect.left - coordinate;
            //修正左边的初始位置
            Edge.LEFT.initCoordinate(imageRect.left);
            //修正右边的偏移量
            Edge.RIGHT.offset(offset);

        }else if(Edge.RIGHT.isOutside(imageRect)){
            float coordinate = Edge.RIGHT.getCoordinate();
            float offset = imageRect.right - coordinate;
            Edge.RIGHT.initCoordinate(imageRect.right);
            Edge.LEFT.offset(offset);
        }

        if(Edge.TOP.isOutside(imageRect)){
            //此时左边的坐标位置
            float coordinate = Edge.TOP.getCoordinate();
            //越界的偏移量
            float offset = imageRect.top - coordinate;
            //修正左边的初始位置
            Edge.TOP.initCoordinate(imageRect.top);
            //修正右边的偏移量
            Edge.BOTTOM.offset(offset);

        }else if(Edge.BOTTOM.isOutside(imageRect)){
            float coordinate = Edge.BOTTOM.getCoordinate();
            float offset = imageRect.bottom - coordinate;
            Edge.BOTTOM.initCoordinate(imageRect.bottom);
            Edge.TOP.offset(offset);
        }
    }
}
