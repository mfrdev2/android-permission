package com.example.permission_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.permission_android.databinding.ActivityMainBinding;
import com.example.permission_android.util.PermissionUtility;

import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
   private ActivityMainBinding mBinding;
    public static final int  REQUEST_CODE = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding =  DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.setStrike(true);


        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                mBinding.setStrike(false);
                Toast.makeText(getApplicationContext(),"ex",Toast.LENGTH_SHORT).show();
            }
        },5000);


        System.out.println("M::" +getResources().getColor(R.color.purple_200));


        if (PermissionUtility.hasLocationPermissions(this))
            setVisibility(true);
        else
            setVisibility(false);

        mBinding.btnCheckPermission.setOnClickListener(view -> requestPermission());
    }

    private void setVisibility(Boolean permissionStatus) {
        if (permissionStatus) {
            mBinding.tvPermissionResult.setText( "Hooray! Now you can track location in background.");
            mBinding.tvPermissionResult.setTextColor(Color.GREEN);
            mBinding.btnCheckPermission.setVisibility(View.GONE);
            //  Start your background service here.
        } else {
            mBinding.tvPermissionResult.setText("You can't track location in background.");
            mBinding.tvPermissionResult.setTextColor(Color.RED);
            mBinding.btnCheckPermission.setVisibility(View.VISIBLE);
        }
    }

    private void requestPermission() {
        if (PermissionUtility.hasLocationPermissions(this)) {
            setVisibility(true);
            return;
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            EasyPermissions.requestPermissions(
                    this,
                    "You need to accept location permission to use this app.",
                    REQUEST_CODE,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                    );
        } else {
            EasyPermissions.requestPermissions(
                    this,
                    "You need to accept location permission to use this app.",
                    REQUEST_CODE,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
                    );
        }
    }


    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        setVisibility(true);
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        setVisibility(false);
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms))
            new AppSettingsDialog.Builder(this)
                    .build().show();
        else
            requestPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private void addTextCrossPaint(TextView textView) {
        textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }
}