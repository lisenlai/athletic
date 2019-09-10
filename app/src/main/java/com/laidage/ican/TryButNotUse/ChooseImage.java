package com.laidage.ican.TryButNotUse;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.laidage.ican.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ChooseImage extends AppCompatActivity implements View.OnClickListener {

    private ImageView showImg;
    private Uri imageUri;
    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_image);
        initView();
    }

    private void initView() {
        findViewById(R.id.Local).setOnClickListener(this);
        findViewById(R.id.Camera).setOnClickListener(this);

        showImg = ((ImageView) findViewById(R.id.photo));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Camera:
                getPicFromCamra();
                break;
            case R.id.Local:
                getPicFromAblum();
                break;
        }
    }

    private void getPicFromCamra() {
//        创建file对象，用于存储拍照后的图片,通过getExternalCacheDir放在应用关联缓存目录
//        具体路径是／sdcard／Android/data/<packeageName>/cache,android6.0读写sd卡为
//        危险权限，需要运行时权限，而放应用关联缓存不要
        File outPutImage = new File(getExternalCacheDir(), "output_img.jpg");

        try {
            if (outPutImage.exists()) {
                outPutImage.delete();
            }
            outPutImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= 24) {
//         android7.0直接使用本地真实路径uri是不安全的，通过FileProvider是一种特殊的内容提供器
//         可以选择性的将封装过的uri提供给外部，提高安全性。参数1:context对象。参数二：可以是任意的字符串，
//          参数三：刚刚创建的file对象。注意这里用到了内容提供者
            imageUri = FileProvider.getUriForFile(ChooseImage.this, "com.laidage.ican.TryButNotUse.ChooseImage", outPutImage);
        } else {
//            获取的是这张图片的真是路径
            imageUri = Uri.fromFile(outPutImage);
        }

//        用意图打开相机
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//        指定图片输出地址
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//        隐示意图启动
        startActivityForResult(intent, TAKE_PHOTO);

    }


    /**
     * 通过相册获取图片
     */
    private void getPicFromAblum() {
//        判断授予sd卡读写权限
        if (ContextCompat.checkSelfPermission(ChooseImage.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ChooseImage.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            openAblum();
        }
    }

    /**
     * 打开相册
     */
    private void openAblum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TAKE_PHOTO:
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        showImg.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                case CHOOSE_PHOTO:
                    if (Build.VERSION.SDK_INT >= 19) {
//                        4.4及以上系统，选取相册中的图片不再返回图片真实的uri
                        handleImageOnKitKat(data);
                    } else {
                        handleImagebeforeKitKat(data);
                    }
                    break;
            }
        }
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
//        如果uri是document类型，就通过读取document id进行处理
        if (DocumentsContract.isDocumentUri(this, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
//            如果Uri的Authority是media格式的话，document id还需要进行解析，然后通过字符串
//            分割方式取出后半部分才能得到真正的数字id
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads,documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads.public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
//            如果是content类型的uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
//            如果是file类型，直接获取图片路径
            imagePath = uri.getPath();
        }
        displayImage(imagePath);
    }


    private void handleImagebeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);

    }


    /**
     * 获取图片真实路径
     *
     * @param uri
     * @param selection
     * @return
     */
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (null != cursor) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    /**
     * 根据图片路径显示图片
     *
     * @param imagePath
     */
    private void displayImage(String imagePath) {
        if (null != imagePath) {
            Bitmap b = BitmapFactory.decodeFile(imagePath);
            showImg.setImageBitmap(b);
        } else {
            Toast.makeText(this, "获取图片失败", Toast.LENGTH_LONG).show();
        }
    }


    /**
     * 处理权限的
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAblum();
                } else {
                    Toast.makeText(this, "没有权限啦～～", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
