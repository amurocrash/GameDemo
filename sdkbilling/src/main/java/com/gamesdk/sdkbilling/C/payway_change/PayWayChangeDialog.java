package com.gamesdk.sdkbilling.C.payway_change;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.cngame.gamesdklib.mvp.view.BaseView;
import com.cngame.gamesdklib.mvp.view.animator.AnimatorBottomExit;
import com.cngame.gamesdklib.mvp.view.animator.AnimatorTopEnter;
import com.cngame.gamesdklib.mvp.view.animator.BaseAnimatorSet;
import com.cngame.gamesdklib.mvp.view.sdk_dialog.DialogIntent;
import com.cngame.gamesdklib.mvp.view.sdk_dialog.SDKBaseAnimationDialog;
import com.gamesdk.sdkbilling.M.ChargePoint;
import com.gamesdk.sdkbilling.V.payway_change.PayWayChangeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Amuro on 2016/5/17.
 */
public class PayWayChangeDialog extends SDKBaseAnimationDialog
{
    public PayWayChangeDialog(Context context)
    {
        super(context);
    }

    @Override
    protected BaseAnimatorSet getShowAnim()
    {
        return new AnimatorTopEnter();
    }

    @Override
    protected BaseAnimatorSet getDismissAnim()
    {
        return new AnimatorBottomExit();
    }

    @Override
    protected BaseView onCreateContentView()
    {
        return new PayWayChangeView(context);
    }

    @Override
    public void onIntent(DialogIntent intent)
    {
        chargePoint = (ChargePoint) intent.getData("chargePoint");
    }

    private ChargePoint chargePoint;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        listView = (ListView)findViewById(PayWayChangeView.id_lv);

        List<Map<String, Integer>> data = new ArrayList<>();

        for(int i = 0; i < 5; i++)
        {
            Map<String, Integer> map = new HashMap<>();
            map.put("policy", i + 1);

            data.add(map);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(
                context,
                data,
                android.R.layout.simple_list_item_1,
                new String[]{"policy"},
                new int[]{android.R.id.text1});

        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                chargePoint.setCurrentPolicy(position + 1);
                setResult(1);
                finish();
            }
        });
    }
}
