package com.winnie.widget.crapimageview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

/**
 * @author : winnie
 * @date : 2018/11/15
 * @desc
 */
public class CropUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    private Context mContext;

    private static class CropUncaughtExceptionHandlerHolder {
        @SuppressLint("StaticFieldLeak")
        private static CropUncaughtExceptionHandler sInstance = new CropUncaughtExceptionHandler();
    }

    public static CropUncaughtExceptionHandler getInstance() {
        return CropUncaughtExceptionHandlerHolder.sInstance;
    }

    public void init(Context context) {
        mContext = context;
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(final Thread thread, final Throwable throwable) {
        new Thread() {
            @Override
            public void run() {
                //生成日志详情
                StringBuilder details = new StringBuilder();
                details.append("Thread : ").append(thread.getName()).append("\n");
                Throwable temp = throwable;
                while (temp != null) {
                    details.append(throwable.toString()).append("\n");

                    StackTraceElement[] elements = throwable.getStackTrace();
                    for (StackTraceElement element : elements) {
                        details.append("\tat ")
                                .append(element.toString())
                                .append("\n");
                    }

                    temp = temp.getCause();
                    if (temp != null) {
                        details.append("Caused by: ");
                    }
                }
                System.out.println(details);

                //生成日志摘要
                StringBuilder summary = new StringBuilder();
                summary.append(throwable.toString())
                        .append("\n")
                        .append("\tat ");
                if (throwable.getStackTrace().length > 0) {
                    summary.append(throwable.getStackTrace()[0].toString());
                }
                System.out.println(summary);
            }
        }.start();

        Toast.makeText(mContext, "程序出错啦:" + throwable.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        System.out.println("Thread.currentThread:" + Thread.currentThread().getName());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {

        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(10);
    }
}
