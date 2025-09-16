package com.example.familywidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

public class FamilyWidgetProvider extends AppWidgetProvider {

    private static final String TAG = "FamilyWidgetProvider";
    // 定义一个自定义的Action，用于区分我们的点击事件
    private static final String ACTION_WIDGET_CLICK = "com.example.familywidget.ACTION_WIDGET_CLICK";

    // 这个 updateAppWidget 方法是 onUpdate 的具体实现，我们把它独立出来，方便重用
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        Log.d(TAG, "开始更新小组件: " + appWidgetId);

        // 1. 读取我们保存在 SharedPreferences 里的数据
        SharedPreferences prefs = context.getSharedPreferences(MainActivity.PREFS_NAME, Context.MODE_PRIVATE);
        String remark = prefs.getString(MainActivity.KEY_WECHAT_REMARK, null);
        String uriString = prefs.getString(MainActivity.KEY_AVATAR_URI, null);

        // 2. 创建用于显示在桌面上的 RemoteViews
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.family_widget_provider);

        // 3. 将读取到的数据显示在小组件上
        if (uriString != null) {
            try {
                Uri imageUri = Uri.parse(uriString);
                views.setImageViewUri(R.id.widget_avatar, imageUri);
                Log.d(TAG, "成功为小组件设置图片URI: " + uriString);
            } catch (Exception e) {
                Log.e(TAG, "设置图片URI失败，使用默认图标", e);
                views.setImageViewResource(R.id.widget_avatar, R.mipmap.ic_launcher_round);
            }
        } else {
            // 如果没有保存图片，就显示一个默认图标
            views.setImageViewResource(R.id.widget_avatar, R.mipmap.ic_launcher_round);
            Log.w(TAG, "未找到图片URI，小组件显示默认图标");
        }

        // 4. 设置点击事件！这是核心！
        if (remark != null && !remark.trim().isEmpty()) {
            // 创建一个意图(Intent)，当小组件被点击时，系统会广播这个意图
            Intent clickIntent = new Intent(context, FamilyWidgetProvider.class);
            clickIntent.setAction(ACTION_WIDGET_CLICK);
            // 把微信备注作为额外数据放进去
            clickIntent.putExtra(MainActivity.KEY_WECHAT_REMARK, remark.trim());

            // 将普通 Intent 包装成 PendingIntent。
            // PendingIntent 是一种特殊的 Intent，允许其他应用（比如桌面Launcher）在未来某个时刻以我们App的权限来执行它。
            // FLAG_UPDATE_CURRENT: 如果已存在，就用新数据更新它
            // FLAG_IMMUTABLE: 表示这个PendingIntent的内容是不可变的，这是安卓系统的新安全要求
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, clickIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

            // 将这个待处理的意图绑定到我们的图片上
            views.setOnClickPendingIntent(R.id.widget_avatar, pendingIntent);
            Log.d(TAG, "成功设置小组件点击事件，备注: " + remark.trim());
        } else {
            Log.w(TAG, "微信备注为空，无法设置点击事件");
        }

        // 5. 更新小组件
        try {
            appWidgetManager.updateAppWidget(appWidgetId, views);
            Log.d(TAG, "小组件更新成功: " + appWidgetId);
        } catch (Exception e) {
            Log.e(TAG, "更新小组件失败: " + appWidgetId, e);
        }
    }

    // 当小组件需要更新时，系统会调用这个方法
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.d(TAG, "onUpdate 被调用，小组件数量: " + appWidgetIds.length);
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    // 当小组件被首次添加到桌面时调用
    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Log.d(TAG, "小组件被启用");
    }

    // 当小组件被从桌面移除时调用
    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        Log.d(TAG, "小组件被禁用");
    }

    // 当小组件被删除时调用
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        Log.d(TAG, "小组件被删除，数量: " + appWidgetIds.length);
    }

    // 当小组件被添加到桌面时调用
    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
        Log.d(TAG, "小组件选项改变: " + appWidgetId);
        updateAppWidget(context, appWidgetManager, appWidgetId);
    }

    // 当我们自己发送的广播（点击事件）被接收时，系统会调用这个方法
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent); // 必须先调用父类的方法

        Log.d(TAG, "收到广播: " + intent.getAction());

        // 检查是不是我们定义的那个点击 Action
        if (ACTION_WIDGET_CLICK.equals(intent.getAction())) {
            // 从 Intent 中取出我们之前存入的微信备注
            String remark = intent.getStringExtra(MainActivity.KEY_WECHAT_REMARK);
            Log.d(TAG, "小组件被点击！收到的备注是: " + remark);

            if (remark != null && !remark.trim().isEmpty()) {
                // 构建跳转到微信视频通话的 Intent
                try {
                    // 这是通过微信内部定义的接口来实现的，格式是固定的
                    String wechatUri = "weixin://dl/chat?contact_username=" + remark.trim() + "&chat_mode=video";
                    Intent wechatIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(wechatUri));
                    // 必须设置这个 Flag，因为我们是在 AppWidgetProvider 这个广播接收器里启动 Activity
                    wechatIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    
                    // 检查是否有应用可以处理这个Intent
                    if (wechatIntent.resolveActivity(context.getPackageManager()) != null) {
                        context.startActivity(wechatIntent);
                        Log.d(TAG, "成功启动微信视频通话，备注: " + remark.trim());
                    } else {
                        Log.w(TAG, "没有找到可以处理微信URI的应用");
                        Toast.makeText(context, "未找到微信应用，请确保已安装微信", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Log.e(TAG, "启动微信失败", e);
                    // 在实际手机上，这里可以提示用户"请先安装微信"或"启动微信失败"
                    Toast.makeText(context, "启动微信失败，请检查是否安装微信", Toast.LENGTH_LONG).show();
                }
            } else {
                Log.w(TAG, "微信备注为空，无法启动微信");
                Toast.makeText(context, "微信备注设置错误，请重新设置", Toast.LENGTH_LONG).show();
            }
        }
    }
}