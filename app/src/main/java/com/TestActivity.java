package com;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import com.customview.CustomVideoProgressBar;
import com.download.FileDownloadManager;
import com.download.ZipFileDownloadListener;
import com.download.utils.FileUtils;
import com.uphealth.cn.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by orson on 16/8/8.
 */
public class TestActivity extends Activity {
    private Handler mHandler;
    private int progress;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_test_layout);

        testDownloadFile();
        //textProgress();
    }

    private void testDownloadFile(){
        //这行代码应在应用启动的时候调用(最先执行 )
        FileDownloadManager.init(this);

        //下面的代码，一定要在上一行代码之后执行
        FileDownloadManager.download(this,"http://115.28.1.196:80//apu/app/temp/6f8e0779aa42492b95ae0bdc1d371507.zip", new ZipFileDownloadListener(){
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
        final CustomVideoProgressBar progressBar = (CustomVideoProgressBar)findViewById(R.id.progress_bar);
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
        }, 500);

    }
}
