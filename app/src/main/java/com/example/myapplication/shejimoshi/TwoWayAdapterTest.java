package com.example.myapplication.shejimoshi;

import android.util.Log;

/**
 * 双向适配器模式
 * 适配器模式（Adapter）可扩展为双向适配器模式，双向适配器类既可以把适配者接口转换成目标接口，
 * 也可以把目标接口转换成适配者接口
 */
public class TwoWayAdapterTest {

    public TwoWayAdapterTest(){
        MuBiaoAdapter muBiaoAdapter = new MuBiaoAdapter();
        TwoAdapter adapter = new TwoAdapter(muBiaoAdapter);
        adapter.mubiaoShow();
    }

    public void showAdapter(){
        ShiPeiAdapter adapter = new ShiPeiAdapter();
        TwoAdapter twoAdapter = new TwoAdapter(adapter);
        twoAdapter.shipeiShow();
    }

    //目标接口
    interface Mubiao{
        void mubiaoShow();
    }

    //目标适配器
    public static class MuBiaoAdapter implements Mubiao{

        @Override
        public void mubiaoShow() {
            Log.d("TwoWayAdapterTest", "mubiaoShow: "+"MuBiaoAdapter");
        }
    }

    //原接口
    interface ShiPei{
        void shipeiShow();
    }

    //原适配器
    public static class ShiPeiAdapter implements ShiPei{

        @Override
        public void shipeiShow() {
            Log.d("TwoWayAdapterTest", "shipeiShow: "+"ShiPeiAdapter");
        }
    }

    //双向适配器
    public static class TwoAdapter implements Mubiao,ShiPei{
        private MuBiaoAdapter muBiaoAdapter;
        private ShiPeiAdapter shiPeiAdapter;

        public TwoAdapter(MuBiaoAdapter muBiaoAdapter){
            this.muBiaoAdapter = muBiaoAdapter;
        }

        public TwoAdapter(ShiPeiAdapter shiPeiAdapter) {
            this.shiPeiAdapter = shiPeiAdapter;
        }

        @Override
        public void mubiaoShow() {
            if(muBiaoAdapter!=null){
                muBiaoAdapter.mubiaoShow();
            }
        }

        @Override
        public void shipeiShow() {
            if(shiPeiAdapter!=null){
                shiPeiAdapter.shipeiShow();
            }
        }
    }

}
