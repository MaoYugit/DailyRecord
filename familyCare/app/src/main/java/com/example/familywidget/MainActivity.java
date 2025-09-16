package com.example.familywidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.UriPermission; // 导入正确的类
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageView avatarPreview;
    private EditText wechatRemarkInput;
    private Button selectPhotoButton;
    private Button saveSettingsButton;

    private Uri selectedImageUri = null;

    public static final String PREFS_NAME = "FamilyWidgetPrefs";
    public static final String KEY_WECHAT_REMARK = "wechat_remark";
    public static final String KEY_AVATAR_URI = "avatar_uri";
    private static final String TAG = "MainActivity";

    // 新的 Activity Result API：注册一个用于选择单个文档（图片）的启动器
    private final ActivityResultLauncher<String[]> mGetContent = registerForActivityResult(
            new ActivityResultContracts.OpenDocument(), // 使用 OpenDocument 来获取持久权限
            uri -> {
                // 这个 lambda 表达式就是处理返回结果的地方，替代了 onActivityResult
                if (uri != null) {
                    try {
                        // 关键：获取对该URI的持久化读取权限
                        final int takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION;
                        getContentResolver().takePersistableUriPermission(uri, takeFlags);

                        // 将新选择的URI保存并显示
                        selectedImageUri = uri;
                        avatarPreview.setImageURI(uri);
                        Log.d(TAG, "成功获取并显示图片: " + uri);

                    } catch (SecurityException e) {
                        Log.e(TAG, "获取图片持久化权限失败", e);
                        Toast.makeText(this, "无法获取该图片的权限，请尝试其他图片", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        avatarPreview = findViewById(R.id.iv_avatar_preview);
        wechatRemarkInput = findViewById(R.id.et_wechat_remark);
        selectPhotoButton = findViewById(R.id.btn_select_photo);
        saveSettingsButton = findViewById(R.id.btn_save_settings);

        selectPhotoButton.setOnClickListener(v -> {
            // 使用新的启动器来打开图片选择器
            // 传入的是MIME类型数组
            mGetContent.launch(new String[]{"image/*"});
        });

        saveSettingsButton.setOnClickListener(v -> {
            String remark = wechatRemarkInput.getText().toString().trim();

            if (remark.isEmpty()) {
                Toast.makeText(MainActivity.this, "请先输入微信备注！", Toast.LENGTH_SHORT).show();
                return;
            }
            if (selectedImageUri == null) {
                Toast.makeText(MainActivity.this, "请先选择一张照片！", Toast.LENGTH_SHORT).show();
                return;
            }

            SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();

            editor.putString(KEY_WECHAT_REMARK, remark);
            editor.putString(KEY_AVATAR_URI, selectedImageUri.toString());
            editor.apply();

            Toast.makeText(MainActivity.this, "设置已保存！正在请求添加到桌面...", Toast.LENGTH_LONG).show();
            
            // 保存成功后，立即更新小组件
            updateWidget();
            
            // 请求用户添加小组件到桌面
            requestPinAppWidget();
        });

        // 添加一个测试小组件的按钮
        Button testWidgetButton = findViewById(R.id.btn_test_widget);
        if (testWidgetButton != null) {
            testWidgetButton.setOnClickListener(v -> {
                if (selectedImageUri != null && !wechatRemarkInput.getText().toString().trim().isEmpty()) {
                    updateWidget();
                    Toast.makeText(this, "小组件已更新，请检查桌面", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "请先设置头像和微信备注", Toast.LENGTH_SHORT).show();
                }
            });
        }

        loadSettings();
    }

    // 使用了新的API后，不再需要重写 onActivityResult 方法了
    // @Override
    // protected void onActivityResult(...) { ... }

    private void loadSettings() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String remark = prefs.getString(KEY_WECHAT_REMARK, "");
        wechatRemarkInput.setText(remark);

        String uriString = prefs.getString(KEY_AVATAR_URI, null);
        if (uriString != null) {
            selectedImageUri = Uri.parse(uriString);

            boolean hasPermission = false;
            // 核心修复：遍历 List<UriPermission>
            List<UriPermission> persistedUriPermissions = getContentResolver().getPersistedUriPermissions();
            for (UriPermission permission : persistedUriPermissions) {
                if (permission.getUri().equals(selectedImageUri)) {
                    hasPermission = true;
                    break;
                }
            }

            if (hasPermission) {
                avatarPreview.setImageURI(selectedImageUri);
                Log.d(TAG, "成功从已保存的URI加载图片: " + uriString);
            } else {
                Log.w(TAG, "URI权限已丢失，需要用户重新选择: " + uriString);
                selectedImageUri = null; // 清空无效的URI
                avatarPreview.setImageResource(R.drawable.ic_launcher_foreground); // 设置一个默认图片
                Toast.makeText(this, "图片权限已失效，请重新选择", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // 添加更新小组件的方法
    private void updateWidget() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        ComponentName componentName = new ComponentName(this, FamilyWidgetProvider.class);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(componentName);
        
        if (appWidgetIds.length > 0) {
            // 如果已经有小组件存在，立即更新
            Intent updateIntent = new Intent(this, FamilyWidgetProvider.class);
            updateIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            updateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            sendBroadcast(updateIntent);
            Log.d(TAG, "已更新现有小组件");
        } else {
            Log.d(TAG, "还没有小组件被添加到桌面");
        }
    }

    // 检查是否为MIUI系统
    private boolean isMIUI() {
        String manufacturer = android.os.Build.MANUFACTURER;
        String brand = android.os.Build.BRAND;
        String product = android.os.Build.PRODUCT;
        
        Log.d(TAG, "设备信息 - 制造商: " + manufacturer + ", 品牌: " + brand + ", 产品: " + product);
        
        return manufacturer.toLowerCase().contains("xiaomi") || 
               brand.toLowerCase().contains("xiaomi") || 
               product.toLowerCase().contains("xiaomi");
    }

    // 改进的请求添加小组件方法
    private void requestPinAppWidget() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            AppWidgetManager appWidgetManager = getSystemService(AppWidgetManager.class);
            ComponentName myProvider = new ComponentName(this, FamilyWidgetProvider.class);

            if (appWidgetManager != null && appWidgetManager.isRequestPinAppWidgetSupported()) {
                // 创建一个回调的 PendingIntent，当用户操作完成后，系统会通知我们
                Intent pinnedWidgetCallbackIntent = new Intent(this, MainActivity.class);
                PendingIntent successCallback = PendingIntent.getBroadcast(this, 0,
                        pinnedWidgetCallbackIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

                // 发起请求
                appWidgetManager.requestPinAppWidget(myProvider, null, successCallback);
                Log.d(TAG, "已请求添加小组件到桌面");
            } else {
                Log.w(TAG, "设备不支持固定小组件");
                showManualAddInstructions();
            }
        } else {
            Log.w(TAG, "系统版本过低，不支持固定小组件");
            showManualAddInstructions();
        }
    }

    // 显示手动添加小组件的说明
    private void showManualAddInstructions() {
        String message;
        if (isMIUI()) {
            message = "检测到MIUI系统，请按以下步骤手动添加小组件：\n\n" +
                     "1. 长按桌面空白处\n" +
                     "2. 选择'添加工具'\n" +
                     "3. 找到'Family Widget'并点击\n" +
                     "4. 选择合适的大小并放置到桌面";
        } else {
            message = "您的设备不支持自动添加，请手动操作：\n\n" +
                     "1. 长按桌面空白处\n" +
                     "2. 选择'小组件'\n" +
                     "3. 找到'Family Widget'并拖拽到桌面";
        }
        
        new android.app.AlertDialog.Builder(this)
            .setTitle("手动添加小组件")
            .setMessage(message)
            .setPositiveButton("知道了", null)
            .show();
    }
}