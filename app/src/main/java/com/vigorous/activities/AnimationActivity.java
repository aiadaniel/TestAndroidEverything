package com.vigorous.activities;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.vigorous.testandroideverything.R;
import com.vigorous.widget.RoundProgress;


public class AnimationActivity extends ActionBarActivity {

    RoundProgress mRoundProgress;
    Button mBtnTestProgress;
    ActionBar mActionBar;
    ImageView mImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actionbar);
        mActionBar = getSupportActionBar();
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.show();
        mRoundProgress = (RoundProgress) findViewById(R.id.round_progress);
        mBtnTestProgress = (Button) findViewById(R.id.btn_test_progress);
        mBtnTestProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRoundProgress.stopProgress();
            }
        });
        mRoundProgress.startProgress();
        mImageView = (ImageView) findViewById(R.id.gift_image_view);

        //below is test view animation
        //loadAnimation(R.anim.tran_in);
        //loadAnimation(R.anim.alpha_in);//这里当把两个同时打开时发现最终只执行了一个,所以要嘛在一个xml里面定义多个，要嘛使用animationSet
        //loadAnimation(R.anim.scale_in);
        //loadAnimation(R.anim.rotate_in);
        loadMultiAnimation(R.anim.tran_in,R.anim.alpha_in,R.anim.scale_in,R.anim.rotate_in);

        //below is test object animation
        //startGiftAnimation();
        //startGiftAnimation2();
        //startGiftAnimation3();

        //valueAnimation();

        //multiAnimation();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void loadAnimation(int resid) {
        Animation animation = AnimationUtils.loadAnimation(this,resid);
        //animation.setDuration(1000);
        mImageView.startAnimation(animation);
    }

    public void loadMultiAnimation(int ...res) {
        AnimationSet animationSet = new AnimationSet(true);
        for (int i =0;i<res.length;i++) {
            Animation animation = AnimationUtils.loadAnimation(this,res[i]);
            animationSet.addAnimation(animation);
        }
        //mImageView.startAnimation(animationSet);
        mImageView.setAnimation(animationSet);
        animationSet.startNow();
    }

    //============================================================================



    //============================================================================

    private void startGiftAnimation() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mImageView,"translationX",0,100);
        objectAnimator.setDuration(3000);
        objectAnimator.start();
    }

    private void startGiftAnimation2() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mImageView,"suibian",0.f,1.f).setDuration(3000);
        objectAnimator.start();
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mImageView.setAlpha(value);//you can set property yourself,利用这种方式同时改变多个属性
                mImageView.setScaleX(value);
                mImageView.setScaleY(value);
            }
        });
    }

    private void startGiftAnimation3() {//这种方式同方式二
        PropertyValuesHolder pvhAlpha = PropertyValuesHolder.ofFloat("alpha",0.f,1.0f);//可以更多的float值，动画更多
        PropertyValuesHolder pvhScalex = PropertyValuesHolder.ofFloat("scaleX",0.f,1.0f);
        PropertyValuesHolder pvhScaley = PropertyValuesHolder.ofFloat("scaleY",0.f,1.0f);
        ObjectAnimator.ofPropertyValuesHolder(mImageView,pvhAlpha,pvhScalex,pvhScaley).setDuration(3000).start();
    }

    private void valueAnimation() {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(3000);
        valueAnimator.setObjectValues(new PointF(0.0f,0.0f));
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setEvaluator(new TypeEvaluator<PointF>() {
            @Override
            public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
                // x方向200px/s ，则y方向0.5 * 10 * t
                PointF point = new PointF();
                point.x = 200 * fraction * 3;
                point.y = 0.5f * 200 * (fraction * 3) * (fraction * 3);
                return point;
            }
        });
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF point = (PointF) animation.getAnimatedValue();
                mImageView.setX(point.x);
                mImageView.setY(point.y);
            }
        });

    }

    private void multiAnimation() {
        int count = 10;
        for (int i=0;i<count;i++) {
            final ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.drawable.icon_flower);
            //虽然可以把view动态加上，但是没法控制位置？
            final ViewGroup rootView = (ViewGroup) this.findViewById(android.R.id.content);
            rootView.addView(imageView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            int [] location={0,0};
            mImageView.getLocationInWindow(location);
            //PropertyValuesHolder pvhX = PropertyValuesHolder.ofInt("x",location[0],location[0]);
            PropertyValuesHolder suibian = PropertyValuesHolder.ofFloat("suibian",0.0f,1.0f*i);
            ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(imageView,suibian).setDuration(500);
            objectAnimator.start();
            objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    Object valueItem = animation.getAnimatedValue();
                    if (valueItem instanceof Integer) {
                        Log.d("11111","current " + ((Integer) valueItem).intValue());
                        return;
                    }
                    float value = (float) animation.getAnimatedValue();
                    imageView.setTranslationX(value*15);
                    imageView.setTranslationY(value*20);
                }
            });
            objectAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    //rootView.removeView(imageView);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
    }
}
