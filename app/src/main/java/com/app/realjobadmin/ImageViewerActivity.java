package com.app.realjobadmin;

import static com.app.realjobadmin.constants.IConstants.EXTRA_GROUP_NAME;
import static com.app.realjobadmin.constants.IConstants.EXTRA_IMGPATH;
import static com.app.realjobadmin.constants.IConstants.EXTRA_USERNAME;
import static com.app.realjobadmin.constants.IConstants.IMG_DEFAULTS;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.app.realjobadmin.managers.Utils;
import com.app.realjobadmin.views.SingleClickListener;
import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

public class ImageViewerActivity extends AppCompatActivity {
    int placeholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();

        setContentView(R.layout.activity_image_viewer);
        final String imgPath = extras.getString(EXTRA_IMGPATH);
        final Uri imageUri = Uri.parse(imgPath);
        final String groupName = extras.getString(EXTRA_GROUP_NAME, "");
        final String username = extras.getString(EXTRA_USERNAME, "");

        findViewById(R.id.imgBack).setOnClickListener(new SingleClickListener() {
            @Override
            public void onClickView(View v) {
                onBackPressed();
            }
        });

        final PhotoView imageViewZoom = findViewById(R.id.imgPath);
        final TextView txtMyName = findViewById(R.id.txtMyName);

        if (Utils.isEmpty(groupName)) {
            if (Utils.isEmpty(username)) {
                txtMyName.setVisibility(View.GONE);
            } else {
                txtMyName.setVisibility(View.VISIBLE);
                txtMyName.setText(username);
            }
        } else {
            txtMyName.setText(groupName);
        }

        if (!Utils.isEmpty(groupName) && imgPath.equalsIgnoreCase(IMG_DEFAULTS)) {
            placeholder = R.drawable.img_group_default_orange;
        } else {
            placeholder = R.drawable.profile_avatar;
        }

        try {
            if (imgPath.equals(IMG_DEFAULTS)) {
                Glide.with(this).load(placeholder).into(imageViewZoom);
            } else {
                Glide.with(this).load(imageUri).into(imageViewZoom);
            }
        } catch (Exception ignored) {
        }

    }

}
