package com.cngame.gamesdklib.mvp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Amuro on 2016/3/25.
 */
public abstract class BaseFragment extends Fragment
{
    protected View rootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    protected abstract void initData();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        initData();

        if((rootView = getRootView()) == null)
        {
            if (getRootViewId() != -1)
            {
                rootView = inflater.inflate(getRootViewId(), container, false);
            }
            else
            {
                throw new RuntimeException("no view for fragment to show!");
            }
        }

        initView(savedInstanceState);
        
        return rootView;
    }

    protected abstract void initView(Bundle savedInstanceState);

    protected View findViewById(int id)
    {
        return rootView.findViewById(id);
    }

    protected int getRootViewId()
    {
        return -1;
    }

    protected View getRootView()
    {
        return null;
    }
}
