package com.winnie.widget.crapimageview;

/**
 * @author : winnie
 * @date : 2018/11/9
 * @desc
 */
public enum Edge {
    //对应着裁剪框的上下左右
    LEFT,
    TOP,
    RIGHT,
    BOTTOM;

    /**
     *   上下左右边界的的坐标值，比如LEFT，RIGHT两条边的值是对应的边距离图片最左边的距离
     */
    private float mCoordinate;

    public void initCoorinate(float coordinate) {
        mCoordinate = coordinate;
    }
}
