package com.photovault.Utils;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;

import android.os.Handler;

/**
 * @author GuanHua
 */
public class LongClickUtils {

    public static void setLongClick(Handler handler, View view,Runnable r){
        view.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        handler.postDelayed(r,3000);
                        break;
                    case MotionEvent.ACTION_UP:
                        handler.removeCallbacks(r);
                        break;
                    default:return false;
                }
                return true;
            }
        });
    }

}
