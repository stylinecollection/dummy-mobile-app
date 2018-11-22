package com.avijitbabu.dummy_mobile_app.Utility;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.avijitbabu.dummy_mobile_app.Activity.MainActivity;
import com.avijitbabu.dummy_mobile_app.R;


public class AppAnimation {

    public static void rotateAnimationRight(View view) {
        Animation rotate = AnimationUtils.loadAnimation(MainActivity.instance, R.anim.rotate_center_right);
        view.clearAnimation();
        view.setAnimation(rotate);
        view.animate();
    }

    public static void rotateAnimationLeft(View view) {
        Animation rotate = AnimationUtils.loadAnimation(MainActivity.instance, R.anim.rotate_center_left);
        view.clearAnimation();
        view.setAnimation(rotate);
        view.animate();
    }

}
