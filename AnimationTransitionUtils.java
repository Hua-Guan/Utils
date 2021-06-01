package com.photovault.Utils;

import android.view.Window;

/**
 * @author guanhua
 */
public class AnimationTransitionUtils {

    public static void setAnimationTransition(Window window){

        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        android.transition.Fade fade = new android.transition.Fade();
        fade.setDuration(1000);
        window.setEnterTransition(fade);
        window.setExitTransition(fade);

    }

}
