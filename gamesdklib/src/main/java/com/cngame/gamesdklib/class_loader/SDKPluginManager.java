package com.cngame.gamesdklib.class_loader;

import android.content.Context;

import com.cngame.gamesdklib.IGameInterface;
import com.cngame.gamesdklib.plugin.IPlugin;

import java.lang.reflect.Method;
import java.util.Map;

import dalvik.system.DexClassLoader;

/**
 * Created by Amuro on 2016/4/15.
 */
public class SDKPluginManager
{
    private static SDKPluginManager instance = new SDKPluginManager();

    public static SDKPluginManager getInstance()
    {
        return instance;
    }

    private SDKClassLoaderInitial classLoaderInitial;
    private DexClassLoader classLoader;

    /**
     * don't do this in main thread
     * @param context
     * @throws Exception
     */
    public void init(Context context) throws Exception
    {
        classLoaderInitial = new SDKClassLoaderInitial(context);
        classLoaderInitial.init();
    }

    public void loadPlugin(
            String pluginName,
            Context context,
            Map<String, String> paramMap,
            IGameInterface resultListener) throws Exception
    {
        if(classLoader == null)
        {
            classLoader = classLoaderInitial.getSDKClassLoader();
        }

        if(!classLoaderInitial.isPluginExist(pluginName))
        {
            throw new Exception("Plugin not exist");
        }

        //SDKBilling
        String pluginInterfaceName =
                "com.gamesdk." + pluginName.toLowerCase() + "." + pluginName + "Interface";
        Class<?> pluginClass = classLoader.loadClass(pluginInterfaceName);

        IPlugin plugin = (IPlugin) pluginClass.newInstance();
        plugin.invoke(context, paramMap, resultListener);

//        Method method = pluginClass.getMethod(
//                "invoke", Context.class, Map.class, IGameInterface.class);
//        method.invoke(pluginClass.newInstance(), context, paramMap, resultListener);
        
    }

}
