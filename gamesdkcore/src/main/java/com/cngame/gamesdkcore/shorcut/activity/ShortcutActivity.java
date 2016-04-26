package com.cngame.gamesdkcore.shorcut.activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.cngame.gamesdklib.http.download.DownloadManager;
import com.cngame.gamesdklib.http.download.IDownloadListener;
import com.cngame.gamesdklib.utils.ToastUtils;

/**
 * Created by Amuro on 2016/4/18.
 */
public class ShortcutActivity extends FragmentActivity
{
    private View rootView;
    private TextView textViewProgress;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        rootView = new ShortcutView(this);
        setContentView(rootView);

        initView();
    }

    private void initView()
    {
        textViewProgress = (TextView) rootView.findViewById(ShortcutView.id_tv_progress);

        rootView.findViewById(ShortcutView.id_bt_start).setOnClickListener(
                new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                downloadFile();
            }
        });

        rootView.findViewById(ShortcutView.id_bt_pause).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                pauseDownload();
            }
        });
    }

    private void downloadFile()
    {
        String urlStr = "http://192.168.202.64:8080/amuro/plugins/as.zip";
        String localFileDir =
                Environment.getExternalStorageDirectory()
                        + "/gamesdk/download/";

        DownloadManager.getInstance().download(
                urlStr, localFileDir, "as.zip", new IDownloadListener()
                {
                    @Override
                    public void onGetFileLength(int length)
                    {
                        ToastUtils.show(ShortcutActivity.this, "文件长度：" + length);
                    }

                    @Override
                    public void onFileExist()
                    {
                        ToastUtils.show(ShortcutActivity.this, "文件已存在！");
                    }

                    @Override
                    public void onProgress(int percent)
                    {
                        textViewProgress.setText(percent + "%");
                    }

                    @Override
                    public void onFinish()
                    {
                        ToastUtils.show(ShortcutActivity.this, "下载完成");
                    }

                    @Override
                    public void onError(String errorMsg)
                    {
                        ToastUtils.show(ShortcutActivity.this, "下载出错：" + errorMsg);
                    }
                });
    }

    private void pauseDownload()
    {
        String urlStr = "http://192.168.202.64:8080/amuro/plugins/as.zip";
        DownloadManager.getInstance().pause(urlStr);
    }

}
