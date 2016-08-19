package com;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import com.ant.liao.GifView;
import com.customview.PullToRefreshView;
import com.download.FileDownloadManager;
import com.download.ZipFileDownloadListener;
import com.download.utils.FileUtils;
import com.uphealth.cn.R;

import java.io.File;

/**
 * Created by orson on 16/8/8.
 * android 跳转系统设置界面

 private void tell() {
     Intent intent = new Intent("/");
     ComponentName cm = new ComponentName("com.android.settings",
     "com.android.settings.RadioInfo");
     intent.setComponent(cm);
     intent.setAction("android.intent.action.VIEW");
     startActivity(intent);
 }

 其他设置界面变量

 com.android.settings.AccessibilitySettings 辅助功能设置
 　　com.android.settings.ActivityPicker 选择活动
 　　com.android.settings.ApnSettings APN设置
 　　com.android.settings.ApplicationSettings 应用程序设置
 　　com.android.settings.BandMode 设置GSM/UMTS波段
 　　com.android.settings.BatteryInfo 电池信息
 　　com.android.settings.DateTimeSettings 日期和坝上旅游网时间设置
 　　com.android.settings.DateTimeSettingsSetupWizard 日期和时间设置
 　　com.android.settings.DevelopmentSettings 应用程序设置=》开发设置
 　　com.android.settings.DeviceAdminSettings 设备管理器
 　　com.android.settings.DeviceInfoSettings 关于手机
 　　com.android.settings.Display 显示——设置显示字体大小及预览
 　　com.android.settings.DisplaySettings 显示设置
 　　com.android.settings.DockSettings 底座设置
 　　com.android.settings.IccLockSettings SIM卡锁定设置
 　　com.android.settings.InstalledAppDetails 语言和键盘设置
 　　com.android.settings.LanguageSettings 语言和键盘设置
 　　com.android.settings.LocalePicker 选择手机语言
 　　com.android.settings.LocalePickerInSetupWizard 选择手机语言
 　　com.android.settings.ManageApplications 已下载（安装）软件列表
 　　com.android.settings.MasterClear 恢复出厂设置
 　　com.android.settings.MediaFormat 格式化手机闪存
 　　com.android.settings.PhysicalKeyboardSettings 设置键盘
 　　com.android.settings.PrivacySettings 隐私设置
 　　com.android.settings.ProxySelector 代理设置
 　　com.android.settings.RadioInfo 手机信息
 　　com.android.settings.RunningServices 正在运行的程序（服务）
 　　com.android.settings.SecuritySettings 位置和安全设置
 　　com.android.settings.Settings 系统设置
 　　com.android.settings.SettingsSafetyLegalActivity 安全信息
 　　com.android.settings.SoundSettings 声音设置
 　　com.android.settings.TestingSettings 测试——显示手机信息、电池信息、使用情况统计、Wifi information、服务信息
 　　com.android.settings.TetherSettings 绑定与便携式热点
 　　com.android.settings.TextToSpeechSettings 文字转语音设置
 　　com.android.settings.UsageStats 使用情况统计
 　　com.android.settings.UserDictionarySettings 用户词典
 　　com.android.settings.VoiceInputOutputSettings 语音输入与输出设置
 　　com.android.settings.WirelessSettings 无线和网络设置
 */


/**
 * Paint类介绍
 *
 * Paint即画笔，在绘图过程中起到了极其重要的作用，画笔主要保存了颜色，
 * 样式等绘制信息，指定了如何绘制文本和图形，画笔对象有很多设置方法，
 * 大体上可以分为两类，一类与图形绘制相关，一类与文本绘制相关。
 *
 * 1.图形绘制
 * setARGB(int a,int r,int g,int b);
 * 设置绘制的颜色，a代表透明度，r，g，b代表颜色值。
 *
 * setAlpha(int a);
 * 设置绘制图形的透明度。
 *
 * setColor(int color);
 * 设置绘制的颜色，使用颜色值来表示，该颜色值包括透明度和RGB颜色。
 *
 * setAntiAlias(boolean aa);
 * 设置是否使用抗锯齿功能，会消耗较大资源，绘制图形速度会变慢。
 *
 * setDither(boolean dither);
 * 设定是否使用图像抖动处理，会使绘制出来的图片颜色更加平滑和饱满，图像更加清晰
 *
 * setFilterBitmap(boolean filter);
 * 如果该项设置为true，则图像在动画进行中会滤掉对Bitmap图像的优化操作，加快显示
 * 速度，本设置项依赖于dither和xfermode的设置
 *
 * setMaskFilter(MaskFilter maskfilter);
 * 设置MaskFilter，可以用不同的MaskFilter实现滤镜的效果，如滤化，立体等       *
 * setColorFilter(ColorFilter colorfilter);
 * 设置颜色过滤器，可以在绘制颜色时实现不用颜色的变换效果
 *
 * setPathEffect(PathEffect effect);
 * 设置绘制路径的效果，如点画线等
 *
 * setShader(Shader shader);
 * 设置图像效果，使用Shader可以绘制出各种渐变效果
 *
 * setShadowLayer(float radius ,float dx,float dy,int color);
 * 在图形下面设置阴影层，产生阴影效果，radius为阴影的角度，dx和dy为阴影在x轴和y轴上的距离，color为阴影的颜色
 *
 * setStyle(Paint.Style style);
 * 设置画笔的样式，为FILL，FILL_OR_STROKE，或STROKE    Style.FILL: 实心   STROKE:空心   FILL_OR_STROKE:同时实心与空心

 *
 * setStrokeCap(Paint.Cap cap);
 * 当画笔样式为STROKE或FILL_OR_STROKE时，设置笔刷的图形样式，如圆形样式
 * Cap.ROUND,或方形样式Cap.SQUARE
 *
 * setSrokeJoin(Paint.Join join);
 * 设置绘制时各图形的结合方式，如平滑效果等
 *
 * setStrokeWidth(float width);
 * 当画笔样式为STROKE或FILL_OR_STROKE时，设置笔刷的粗细度
 *
 * setXfermode(Xfermode xfermode);
 * 设置图形重叠时的处理方式，如合并，取交集或并集，经常用来制作橡皮的擦除效果
 *
 * 2.文本绘制
 * setFakeBoldText(boolean fakeBoldText);
 * 模拟实现粗体文字，设置在小字体上效果会非常差
 *
 * setSubpixelText(boolean subpixelText);
 * 设置该项为true，将有助于文本在LCD屏幕上的显示效果
 *
 * setTextAlign(Paint.Align align);
 * 设置绘制文字的对齐方向
 *
 * setTextScaleX(float scaleX);
 * 设置绘制文字x轴的缩放比例，可以实现文字的拉伸的效果
 *
 * setTextSize(float textSize);
 * 设置绘制文字的字号大小
 *
 * setTextSkewX(float skewX);
 * 设置斜体文字，skewX为倾斜弧度
 *
 * setTypeface(Typeface typeface);
 * 设置Typeface对象，即字体风格，包括粗体，斜体以及衬线体，非衬线体等
 *
 * setUnderlineText(boolean underlineText);
 * 设置带有下划线的文字效果
 *
 * setStrikeThruText(boolean strikeThruText);
 * 设置带有删除线的效果
 *
 */
public class TestActivity extends Activity {
    private Handler mHandler;
    private int progress;
    private GifView mGifView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_test_layout);

        //testDownloadFile();
       //textProgress();
        //testCircleProgress();

        //mGifView = (GifView)findViewById(R.id.gifview);
        //mGifView.setGifImage(R.drawable.gif_refresh);
        testPullToRefresh();
    }


    private void testPullToRefresh(){
        final PullToRefreshView listView = (PullToRefreshView)findViewById(R.id.list_view);
        //listView.setHeadGifImage(R.drawable.gif_refresh);
        //listView.setmFooterGifImage(R.drawable.gif_list_footer);

        listView.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener(){
            public void onHeaderRefresh(PullToRefreshView view) {
                try {
                    Thread.sleep(3000);
                    listView.onHeaderRefreshComplete();
                    System.out.println("--------------------------");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


        listView.setOnFooterRefreshListener(new PullToRefreshView.OnFooterRefreshListener(){
            public void onFooterRefresh(PullToRefreshView view) {
                try {
                    Thread.sleep(3000);
                    listView.onFooterRefreshComplete();
                    System.out.println("=============================");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    private void testDownloadFile(){
        //这行代码应在应用启动的时候调用(最先执行 )
        FileDownloadManager.init(this);

        //下面的代码，一定要在上一行代码之后执行
        FileDownloadManager.download(this,"http://115.28.1.196:80//apu/app/temp/6f8e0779aa42492b95ae0bdc1d371507.zip", true, new ZipFileDownloadListener(){
            public void onDownloadStart(String downloadUrl, long downloadFileSize) {
                System.out.println("=====onDownloadStart-downloadFileSize:" + downloadFileSize);
            }

            public void onDownloadProgress(long currentSize, long totalSize, float downloadSpeed) {
                System.out.println("=====onDownloadProgress-currentSize-totalSize:" + currentSize + " " + totalSize);
            }

            public void onDownloadFinish(boolean isFinish, String downloadUrl, String savePath) {
                System.out.println("=====onDownloadFinish-isFinish:" + isFinish + " savePath=" + savePath);
            }

            public void onUnzipStart(String zipFilewName, String unzipFolder) {
                System.out.println("=====onUnzipStart-zipFilewName:" + zipFilewName + " unzipFolder=" + unzipFolder);
            }

            public void onUnzipFinish(boolean isSuccess, String unzipFolder) {
                System.out.println("=====onUnzipFinish-isSuccess:" + isSuccess + " unzipFolder=" + unzipFolder);
                File file = FileUtils.getFileFromContextFiles(TestActivity.this, "20160803143427.mp4");
            }
        });
    }


    private void textProgress(){
        /*final CustomVideoProgressBar1 progressBar = (CustomVideoProgressBar1)findViewById(R.id.progress_bar);
        List<Integer> nodes = new ArrayList<>();
        nodes.add(new Integer(42345));
        nodes.add(new Integer(68888));
        nodes.add(new Integer(33324));
        nodes.add(new Integer(59999));
        progressBar.setNodeProgress(nodes);
        final int total = 42345 + 68888 + 33324 + 59999;

        mHandler = new Handler();
        mHandler.postDelayed(new Runnable(){
            public void run() {
                progress += 3000;

                if (progress >= total){
                    progress = total;
                    mHandler.removeCallbacks(this);
                }
                else
                    mHandler.postDelayed(this, 500);

                progressBar.setProgress(progress);
            }
        }, 500);*/

    }

    private void testCircleProgress(){
        /*final int total = 10000;
        final CircleProgressBar CircleProgressBar = (CircleProgressBar)findViewById(R.id.circle_progress);
        CircleProgressBar.setMaxProgress(total);


        //progressBarView2.setMax(total);
        mHandler = new Handler();

        mHandler.postDelayed(new Runnable(){
            public void run() {
                progress += 200;

                if (progress >= total){
                    progress = total;
                    mHandler.removeCallbacks(this);
                }
                else
                    mHandler.postDelayed(this, 500);

                CircleProgressBar.setCurProgress(progress);
            }
        }, 500);*/
    }
}
