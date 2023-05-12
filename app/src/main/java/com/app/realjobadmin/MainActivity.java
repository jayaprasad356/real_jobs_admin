package com.app.realjobadmin;

import static com.app.realjobadmin.constants.IConstants.ROLE;
import static com.app.realjobadmin.constants.IConstants.ZERO;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.app.realjobadmin.managers.Utils;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity implements PopupMenu.OnMenuItemClickListener {
    private CircleImageView mImageView;
    private TextView mTxtUsername;
    private ViewPager2 mViewPager;
    private long exitTime = 0;
    Fragment chatFragment, pendingTicketFragment, ticketFragment;
    public static FragmentManager fm = null;
    ImageView imgMenu, imgSearch;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = findViewById(R.id.imageView);
        mTxtUsername = findViewById(R.id.txtUsername);
        imgMenu = findViewById(R.id.imgMenu);
        imgSearch = findViewById(R.id.imgSearch);
        fm = getSupportFragmentManager();
        progressDialog = new ProgressDialog(this);
        final Toolbar mToolbar = findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("");

        mTxtUsername.setText(session.getData(ROLE));


        chatFragment = new ChatsFragment();
        pendingTicketFragment = new PendingTicketFragment();
        ticketFragment = new TicketFragment();


        fm.beginTransaction().replace(R.id.container, ticketFragment).commit();


        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showpopup(view);
            }
        });
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mActivity, SearchActivity.class);
                startActivity(intent);
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        exitApp();
    }

    private void exitApp() {
        try {
            if (mViewPager.getCurrentItem() == ZERO) {
                int DEFAULT_DELAY = 2000;
                if ((System.currentTimeMillis() - exitTime) > DEFAULT_DELAY) {
                    try {
                        screens.showToast("Press Again to Exist");

                    } catch (Exception e) {
                        screens.showToast("Press Again to Exist");
                    }
                    exitTime = System.currentTimeMillis();
                } else {
                    finish();
                }
            } else {
                mViewPager.setCurrentItem(ZERO);
            }
        } catch (Exception e) {
            Utils.getErrors(e);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Utils.readStatus(STATUS_ONLINE);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void showpopup(View v) {
        PopupMenu popup = new PopupMenu(mActivity, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup_menu);
        popup.show();
        MenuItem vs=popup.getMenu().findItem(R.id.newJoinings);
        vs.setVisible(true);

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.logoutitem) {
            session.logoutUser(mActivity);
        } else if (item.getItemId() == R.id.newJoinings) {
            Intent intent = new Intent(mActivity, JoiningActivity.class);
            startActivity(intent);
        }


        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}
