package com.cngame.gamesdklib.mvp.model;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Amuro on 2016/3/24.
 */
public abstract class AbsModel
{
    public static <M extends AbsModel> M getInstance(Class<? extends AbsModel> clazz)
    {
        return ModelManager.getModel(clazz);
    }

    protected AbsModel()
    {

    }

    protected List<Object> subscribers = new ArrayList<Object>();

    public void registerEventSubscriber(Object subscriber)
    {
        this.subscribers.add(subscriber);
    }

    public void removeEventSubscriber(Object subscriber)
    {
        int index = this.subscribers.indexOf(subscriber);

        if(index != -1)
        {
            this.subscribers.remove(index);
        }
    }

    protected void notifyEvent(String eventType, Object... args)
    {
        for(Object subscriber : subscribers)
        {
            if(subscriber != null)
            {
                Method[] methods = subscriber.getClass().getDeclaredMethods();
                for (Method method : methods)
                {
                    Event event = method.getAnnotation(Event.class);
                    if (event != null)
                    {
                        if(eventType.equals(event.value()))
                        {
                            try
                            {
                                method.setAccessible(true);
                                method.invoke(subscriber, args);
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }

    public abstract String getEventType();
}
