package com.nivida.bossb2b;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class ProgressActivity extends ProgressDialog {

    Context context;
    Animation myRotation;
    String comment;
    ImageView img;

    public ProgressActivity() {
        super(null);
    }

    public ProgressActivity(Context context) {
        super(context);
    }

    public ProgressActivity(Context context, String comment) {
        super(context);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.comment = comment;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);

        img = (ImageView) findViewById(R.id.img);

        myRotation = AnimationUtils.loadAnimation(context, R.anim.rotate);

    }

    @Override
    public void show() {
        super.show();
        myRotation.setRepeatCount(Animation.INFINITE);
        img.startAnimation(myRotation);

    }

    @Override
    public void dismiss() {
        super.dismiss();
        myRotation.cancel();

    }
}
