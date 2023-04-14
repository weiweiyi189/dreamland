package com.example.dreamland.ui.setting;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.dreamland.R;
import com.example.dreamland.entity.User;
import com.example.dreamland.service.BaseHttpService;
import com.example.dreamland.service.DownloadImageTask;
import com.example.dreamland.service.UserService;
import com.example.dreamland.ui.auth.EnrollActivity;
import com.example.dreamland.ui.auth.LoginActivity;
import com.example.dreamland.ui.dashboard.DashboardActivity;
import com.example.dreamland.ui.dreams.DreamsActivity;
import com.example.dreamland.ui.util.BitmapUtils;
import com.example.dreamland.ui.util.CameraUtils;
import com.example.dreamland.ui.util.SPUtils;
import com.example.dreamland.ui.writeDream.WriteDreamActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.imageview.ShapeableImageView;
import com.tbruyelle.rxpermissions2.RxPermissions;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SettingActivity extends AppCompatActivity {

    private final UserService userService = UserService.getInstance();

    //android 捕获返回（后退）按钮事件
    public void onBackPressed() {
        Intent intent = new Intent(SettingActivity.this, DashboardActivity.class);
        startActivity(intent, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        //检查是否有拍照或者读取相册的权限，有的话才可以执行。具体实现在最后。
        checkVersion();

        //导航栏返回按钮
        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, DashboardActivity.class);
                startActivity(intent, null);
            }
        });

        LinearLayout avatar = findViewById(R.id.avatar);
        LinearLayout account = findViewById(R.id.account);
        LinearLayout password = findViewById(R.id.password);
        LinearLayout about = findViewById(R.id.about);
        LinearLayout exit = findViewById(R.id.exit);

        TextView accounts = findViewById(R.id.accounts);
        CircleImageView touxiang = findViewById(R.id.touxiang);


        // 加载个人信息
        userService.getCurrentUser(new BaseHttpService.CallBack() {
            @Override
            public void onSuccess(BaseHttpService.CustomerResponse result) {
                //获取当前登陆用户
                User currentUser= (User) result.getData();
                System.out.println(currentUser.getUsername());
                // 加载个人信息成功
                if (result.getResponse().code() >= 200 && result.getResponse().code() < 300) {
                    System.out.println(currentUser.getImageUrl());
                    accounts.append("        "+currentUser.getUsername());
                    Toast.makeText(SettingActivity.this, userService.currentUser.getValue().getImageUrl(), Toast.LENGTH_SHORT).show();
                        String urlString = BaseHttpService.BASE_URL + currentUser.getImageUrl();
                        new DownloadImageTask(touxiang)
                                .execute(urlString);
                } else {
                    // 登陆失败 提示错误
                    Toast.makeText(SettingActivity.this, "信息预加载失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //修改信息绑定
        //修改信息绑定
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeAvatar();
            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, AccountActivity.class);
                startActivity(intent);
                finish();
            }
        });
        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, PasswordActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //关于我们
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, AboutActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //退出登陆
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }



    //以下为头像
    //权限请求
    private RxPermissions rxPermissions;

    //是否拥有权限
    private boolean hasPermissions = false;

    //底部弹窗
    private BottomSheetDialog bottomSheetDialog;
    //弹窗视图
    private View bottomView;

    //存储拍完照后的图片
    private File outputImagePath;
    //启动相机标识
    public static final int TAKE_PHOTO = 1;
    //启动相册标识
    public static final int SELECT_PHOTO = 2;

    //图片控件
    private ShapeableImageView ivHead;
    //Base64
    private String base64Pic;
    //拍照和相册获取图片的Bitmap
    private Bitmap orc_bitmap;

    /**
     * 更换头像
     *
     */
    public void changeAvatar() {
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomView = getLayoutInflater().inflate(R.layout.dialog_bottom, null);
        bottomSheetDialog.setContentView(bottomView);
        TextView tvTakePictures = bottomView.findViewById(R.id.tv_take_pictures);
        TextView tvOpenAlbum = bottomView.findViewById(R.id.tv_open_album);
        TextView tvCancel = bottomView.findViewById(R.id.tv_cancel);

        //拍照
        tvTakePictures.setOnClickListener(v -> {
//            takePhoto();
            openAlbum();
            showMsg("拍照");
            bottomSheetDialog.cancel();
        });
        //打开相册
        tvOpenAlbum.setOnClickListener(v -> {
            openAlbum();
//            showMsg("打开相册");
            bottomSheetDialog.cancel();
        });
        //取消
        tvCancel.setOnClickListener(v -> {
            bottomSheetDialog.cancel();
        });
        //底部弹窗显示
        bottomSheetDialog.show();
    }

    /**
     * 拍照
     */
    private void takePhoto() {
        if (!hasPermissions) {
            showMsg("未获取到权限");
            checkVersion();
            return;
        }

        Intent photoPickerIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 1);
    }

    /**
     * 打开相册
     */
    private void openAlbum() {
        if (!hasPermissions) {
            showMsg("未获取到权限");
            checkVersion();
            return;
        }
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 1);
    }

    /**
     * 返回到Activity
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            //拍照后返回
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    String filePath = getPath(selectedImage);
                    File file = new File(filePath);
                    MediaType MEDIA_TYPE_PNG = MediaType.parse("image/*");
                    RequestBody req = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("file", file.getName(), RequestBody.create(MEDIA_TYPE_PNG, file))
                            .build();
                    // 更新头像
                    userService.uploadImage(req,new BaseHttpService.CallBack() {
                        @Override
                        public void onSuccess(BaseHttpService.CustomerResponse result) {
                            if (result.getResponse().code() >= 200 && result.getResponse().code() < 300) {
                                Toast.makeText(SettingActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                                userService.getCurrentUser(new BaseHttpService.CallBack() {
                                    @Override
                                    public void onSuccess(BaseHttpService.CustomerResponse result) {
                                        if (result.getResponse().code() >= 200 && result.getResponse().code() < 300) {
                                            userService.currentUser.onNext((User) result.getData());
                                        } else {
                                            Toast.makeText(SettingActivity.this, "更新头像失败", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                Intent intent = new Intent(SettingActivity.this,SettingActivity.class);
                                startActivity(intent);
                            } else {
                                // 更新失败 提示错误
                                Toast.makeText(SettingActivity.this, "上传失败，请重试", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                break;
            //打开相册后返回
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    String filePath = getPath(selectedImage);
                    File file = new File(filePath);
                    MediaType MEDIA_TYPE_PNG = MediaType.parse("image/*");
                    RequestBody req = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("file", file.getName(), RequestBody.create(MEDIA_TYPE_PNG, file))
                            .build();
                    // 更新头像
                    userService.uploadImage(req,new BaseHttpService.CallBack() {
                        @Override
                        public void onSuccess(BaseHttpService.CustomerResponse result) {
                            if (result.getResponse().code() >= 200 && result.getResponse().code() < 300) {
                                Toast.makeText(SettingActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                            } else {
                                // 更新失败，提示错误
                                Toast.makeText(SettingActivity.this, "上传失败，请重试", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                break;
            default:
                break;
        }
    }

    /**
     * 通过图片路径显示图片
     */
    private void displayImage(String imagePath) {
        if (!TextUtils.isEmpty(imagePath)) {

            //放入缓存
            SPUtils.putString("imageUrl",imagePath,this);

            //显示图片
//            Glide.with(this).load(imagePath).apply(requestOptions).into(ivHead);

            //压缩图片
            orc_bitmap = CameraUtils.compression(BitmapFactory.decodeFile(imagePath));
            //转Base64
            base64Pic = BitmapUtils.bitmapToBase64(orc_bitmap);

        } else {
            showMsg("图片获取失败");
        }
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = SettingActivity.this.managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        String imagePath = cursor.getString(column_index);

        return imagePath;
    }

    /**
     * 检查版本
     */
    private void checkVersion() {
        //Android6.0及以上版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //如果你是在Fragment中，则把this换成getActivity()
            rxPermissions = new RxPermissions(this);
            //权限请求
            rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(granted -> {
                        if (granted) {//申请成功
//                            showMsg("已获取权限");
                            hasPermissions = true;
                        } else {//申请失败
                            showMsg("权限未开启");
                            hasPermissions = false;
                        }
                    });
        } else {
            //Android6.0以下
            showMsg("无需请求动态权限");
        }
    }

    /**
     * Toast提示
     *
     * @param msg
     */
    private void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


}