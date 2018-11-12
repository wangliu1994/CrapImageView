package com.winnie.widget.crapimageview;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
                mCropImageView.setImageBitmap(mCropImageView.getCroppedImage());
            }
        });
    }
}
