package com.winnie.widget.crapimageview;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author winnie
 */
public class MainActivity extends AppCompatActivity {

    private CropImageView mCropImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCropImageView = findViewById(R.id.crop_image_view);
        Button okButton = findViewById(R.id.ok_button);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "开始裁剪图片", Toast.LENGTH_LONG).show();
                mCropImageView.setImageBitmap(mCropImageView.getCroppedImage());
            }
        });
    }
}
