package com.kc.module_home.other;

import android.Manifest;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.dovar.router_annotation.Route;
import com.kc.common_service.util.RxUtils;
import com.kc.module_home.R;
import com.kc.module_home.databinding.ActivityQrCodeBinding;
import com.king.zxing.CaptureActivity;
import com.king.zxing.util.CodeUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

@Route(path = "other/qrCode")
public class QrCodeActivity extends CaptureActivity {

    private ActivityQrCodeBinding mBinding;
    private boolean mIsFlashLampOn = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_qr_code;
    }

    @Override
    public boolean isContentView(int layoutId) {
        return false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.setContentView(this, getLayoutId());
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initUI() {
        super.initUI();
        mBinding.ivFlashLamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsFlashLampOn){
                    offFlash();
                }else {
                    openFlash();
                }
                mIsFlashLampOn = !mIsFlashLampOn;
                mBinding.ivFlashLamp.setSelected(mIsFlashLampOn);
            }
        });

        mBinding.btGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new RxPermissions(QrCodeActivity.this)
                        .request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean grant) throws Exception {
                                if (grant){
                                    Intent pickIntent = new Intent(Intent.ACTION_PICK,
                                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                    pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                                    startActivityForResult(pickIntent, 11);
                                }else {
//                                    ToastUtils.showShort("抱歉，需要启用权限才能使用");
                                }
                            }
                        });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11 && resultCode == Activity.RESULT_OK){
            final String path = getImagePath(this,data);
            if(TextUtils.isEmpty(path)){
                return;
            }
            Observable.just(path)
                    .map(new Function<String, String>() {
                        @Override
                        public String apply(String s) throws Exception {
                            return CodeUtils.parseCode(s);
                        }
                    })
                    .compose(RxUtils.<String>applySchedulers())
                    .subscribe(new Consumer<String>() {
                        @Override
                        public void accept(String s) throws Exception {
//                            ToastUtils.showLong(s);
                            finish();
                        }
                    });
        }
    }

    /**
     * 获取图片
     */
    public String getImagePath(Context context, Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        //获取系統版本
        int currentapiVersion = Build.VERSION.SDK_INT;
        if(currentapiVersion> Build.VERSION_CODES.KITKAT){
            Log.d("uri=intent.getData :", "" + uri);
            if (DocumentsContract.isDocumentUri(context, uri)) {
                String docId = DocumentsContract.getDocumentId(uri);
                Log.d("getDocumentId(uri) :", "" + docId);
                Log.d("uri.getAuthority() :", "" + uri.getAuthority());
                if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                    String id = docId.split(":")[1];
                    String selection = MediaStore.Images.Media._ID + "=" + id;
                    imagePath = getImagePath(context,MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
                } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                    Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                    imagePath = getImagePath(context,contentUri, null);
                }

            } else if ("content".equalsIgnoreCase(uri.getScheme())) {
                imagePath = getImagePath(context,uri, null);
            }
        }else{
            imagePath = getImagePath(context,uri, null);
        }

        return imagePath;

    }

    /**
     * 通过uri和selection来获取真实的图片路径,从相册获取图片时要用
     */
    private String getImagePath(Context context,Uri uri, String selection) {
        String path = null;
        Cursor cursor = context.getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    @Override
    public boolean onResultCallback(String result) {
//        ToastUtils.showLong(result);
        finish();
        return true;
    }

    /**
     * 关闭闪光灯（手电筒）
     *
     */
    private void offFlash(){
        Camera camera = getCaptureHelper().getCameraManager().getOpenCamera().getCamera();
        Camera.Parameters parameters = camera.getParameters();
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(parameters);
    }

    /**
     * 开启闪光灯（手电筒）
     */
    public void openFlash(){
        Camera camera = getCaptureHelper().getCameraManager().getOpenCamera().getCamera();
        Camera.Parameters parameters = camera.getParameters();
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(parameters);
    }
}
