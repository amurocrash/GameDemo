package com.cngame.gamesdkcore.shorcut.activity;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.cngame.gamesdklib.http.core.downloadSample.AppInfo;
import com.cngame.gamesdklib.http.core.downloadSample.DownloadInfo;
import com.cngame.gamesdklib.http.core.downloadSample.DownloadManager;

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

    Handler handler = new Handler()
    {

    };

    private void downloadFile()
    {
        String urlStr = "http://192.168.202.64:8080/amuro/plugins/as.zip";
        String localFileDir =
                Environment.getExternalStorageDirectory()
                        + "/gamesdk/download/";

        DownloadManager downloadManager = DownloadManager.getInstance();
        downloadManager.registerObserver(new DownloadManager.DownloadObserver()
        {
            @Override
            public void onDownloadStateChanged(DownloadInfo info)
            {
                int state = info.getDownloadState();

                if(state == DownloadManager.STATE_PAUSED)
                {

                }
            }

            @Override
            public void onDownloadProgressed(final DownloadInfo info)
            {
                handler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        textViewProgress.setText(info.getProgress() + "%");
                    }
                });


            }
        });
        AppInfo appInfo = new AppInfo();
        appInfo.setDownloadUrl(urlStr);
        appInfo.setId(1);
        downloadManager.download(appInfo);
    }

    private void pauseDownload()
    {
        AppInfo appInfo = new AppInfo();
        appInfo.setId(1);
        DownloadManager.getInstance().pause(appInfo);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }
}
