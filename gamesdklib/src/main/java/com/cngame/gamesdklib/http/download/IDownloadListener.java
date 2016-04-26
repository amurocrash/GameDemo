package com.cngame.gamesdklib.http.download;

/**
 * Created by Amuro on 2016/4/25.
 */
public interface IDownloadListener
{
    void onGetFileLength(int length);
    void onProgress(int percent);
    void onFileExist();
    void onFinish();
    void onError(String errorMsg);
}
