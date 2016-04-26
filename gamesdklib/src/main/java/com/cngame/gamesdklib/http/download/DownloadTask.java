package com.cngame.gamesdklib.http.download;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.cngame.gamesdklib.utils.FileUtil;
import com.cngame.gamesdklib.utils.IOUtils;
import com.cngame.gamesdklib.utils.LogUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Amuro on 2016/4/25.
 */
public class DownloadTask implements Runnable
{
    private static final int WHAT_FILE_LENGTH = 0x01;
    private static final int WHAT_PERCENT_CHANGE = 0x02;
    private static final int WHAT_DOWNLOAD_FINISH = 0x03;
    private static final int WHAT_DOWNLOAD_ERROR = 0x04;
    private static final int WHAT_FILE_EXISTED = 0x05;

    private static Handler handler = new Handler(Looper.getMainLooper())
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case WHAT_FILE_LENGTH:
                {
                    DownloadTask downloadTask = (DownloadTask) msg.obj;
                    if(downloadTask.downloadListener != null)
                    {
                        downloadTask.downloadListener.onGetFileLength(downloadTask.fileLength);
                    }

                    break;
                }
                case WHAT_PERCENT_CHANGE:
                {
                    DownloadTask downloadTask = (DownloadTask) msg.obj;
                    if(downloadTask.downloadListener != null)
                    {
                        downloadTask.downloadListener.onProgress(downloadTask.nowPercent);
                    }
                    break;
                }
                case WHAT_DOWNLOAD_FINISH:
                {
                    DownloadTask downloadTask = (DownloadTask) msg.obj;
                    if(downloadTask.downloadListener != null)
                    {
                        downloadTask.downloadListener.onFinish();
                    }
                    break;
                }
                case WHAT_DOWNLOAD_ERROR:
                {
                    DownloadTask downloadTask = (DownloadTask) msg.obj;
                    if(downloadTask.downloadListener != null)
                    {
                        downloadTask.downloadListener.onError(downloadTask.errorMsg);
                    }
                    break;
                }
                case WHAT_FILE_EXISTED:
                {
                    DownloadTask downloadTask = (DownloadTask) msg.obj;
                    if(downloadTask.downloadListener != null)
                    {
                        downloadTask.downloadListener.onFileExist();
                    }
                    break;
                }
            }


        }
    };

    private String urlStr;
    private String localFileDir;
    private File localFile;
    private String fileName;
    private IDownloadListener downloadListener;

    private int fileLength;
    private int nowPercent = 0;
    private String errorMsg;

    private boolean starting = false;
    private boolean pause = false;

    public DownloadTask(String url, String localFileDir, String fileName, IDownloadListener listener)
    {
        this.urlStr = url;
        this.localFileDir = localFileDir;
        this.fileName = fileName;
        this.downloadListener = listener;
    }

    @Override
    public void run()
    {
        starting = true;
        FileOutputStream fileOutputStream = null;

        try
        {
            if(!prepareFile())
            {
                handler.sendMessage(handler.obtainMessage(WHAT_FILE_EXISTED, this));
                starting = false;
                return;
            }

            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            fileLength = conn.getContentLength();
            LogUtils.e("File length -> " + fileLength);
            handler.sendMessage(handler.obtainMessage(WHAT_FILE_LENGTH, this));

            fileOutputStream = new FileOutputStream(localFile);
            InputStream inputStream = conn.getInputStream();
            int readLength;
            int totalReadLength = 0;
            byte[] buffer = new byte[1024 * 4];

            boolean finish = false;
            while(!finish)
            {
                if(pause)
                {
                    continue;
                }

                readLength = inputStream.read(buffer);
                if(readLength != -1)
                {
                    fileOutputStream.write(buffer, 0, readLength);
                    fileOutputStream.flush();

                    totalReadLength += readLength;
                    int oldPercent = nowPercent;
                    nowPercent = (int)((float)totalReadLength / (float) fileLength * 100);

                    if(nowPercent != oldPercent)
                    {
                        handler.sendMessage(
                                handler.obtainMessage(WHAT_PERCENT_CHANGE, this));
                    }
                }
                else
                {
                    handler.sendMessage(
                            handler.obtainMessage(WHAT_DOWNLOAD_FINISH, this));
                    finish = true;
                }

            }

        }
        catch (Exception e)
        {
            errorMsg = e.getMessage();
            handler.sendMessage(handler.obtainMessage(WHAT_DOWNLOAD_ERROR, this));
            localFile.delete();
        }
        finally
        {
            IOUtils.close(fileOutputStream);
            starting = false;
        }
    }

    private boolean prepareFile() throws Exception
    {
        File dir = FileUtil.getDir(localFileDir);

        localFile = new File(dir + File.separator + fileName);
        if(!localFile.exists())
        {
            localFile.createNewFile();
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean isStarting()
    {
        return starting;
    }

    public void pause()
    {
        this.pause = !pause;
    }

}
