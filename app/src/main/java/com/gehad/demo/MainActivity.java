package com.gehad.demo;

import android.content.Intent;
import android.os.Bundle;

import com.goda.walkthrough.WalkthroughActivity;
import com.goda.walkthrough.WalkthroughItem;


public class MainActivity extends WalkthroughActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        WalkthroughItem page1 = new WalkthroughItem(R.drawable.tutorial1, "", "");
        page1.setTitleColorID(android.R.color.holo_blue_dark)
                .setSubTitleColorID(android.R.color.holo_blue_bright)
                .setSkipColorID(R.color.blue);

        WalkthroughItem page2 = new WalkthroughItem(R.drawable.tutorial2, "", "");
        page2.setBackgroundColorID(R.color.navy)

                .setTitleColorID(android.R.color.holo_red_light)
                .setSubTitleColorID(android.R.color.holo_red_dark)
                .setSkipColorID(android.R.color.white);

        WalkthroughItem page3 = new WalkthroughItem(R.drawable.tutorial3, "", "");
        page3.setBackgroundColorID(R.color.calypso)
                .setTitleColorID(android.R.color.holo_blue_light)
                .setSubTitleColorID(R.color.calypso);

        addPage(page1);
        addPage(page2);
        addPage(page3);

        setTransitionType(STACK_TRANSFORMER);
        setProgressBarColor(R.color.red_700);

    }

    @Override
    public void onFinishButtonPressed() {
        finish();
       // startActivity(new Intent(this, LandActivity.class));
    }
}