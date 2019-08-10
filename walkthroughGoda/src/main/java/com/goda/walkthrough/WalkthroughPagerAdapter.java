package com.goda.walkthrough;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;


public class WalkthroughPagerAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;

    ArrayList<WalkthroughItem> mItems;
    clickOnImageListener clickOnImageListener;

    public WalkthroughPagerAdapter(Context context, clickOnImageListener clickOnImageListener) {
        mContext = context;
        this.clickOnImageListener = clickOnImageListener;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (mItems != null)
            return mItems.size();
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View itemView = mLayoutInflater.inflate(R.layout.walkthrough_item, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        RelativeLayout leftSide = itemView.findViewById(R.id.leftSide);
        RelativeLayout rightSide = itemView.findViewById(R.id.rightSide);
        TextView title = (TextView) itemView.findViewById(R.id.walkthrough_title);
        TextView details = (TextView) itemView.findViewById(R.id.walkthrough_details);

        WalkthroughItem currentWalkthroughItem = mItems.get(position);

        imageView.setImageResource(currentWalkthroughItem.getImageID());
        leftSide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickOnImageListener.clickOnImage(true);
            }
        });
        rightSide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickOnImageListener.clickOnImage(false);
            }
        });
        itemView.setBackgroundColor(mContext.getResources().getColor(currentWalkthroughItem.getBackgroundColorID()));
        title.setText(currentWalkthroughItem.getTitle());
        title.setTextColor(mContext.getResources().getColor(currentWalkthroughItem.getTitleColorID()));
        details.setText(currentWalkthroughItem.getSubTitle());
        details.setTextColor(mContext.getResources().getColor(currentWalkthroughItem.getSubTitleColorID()));

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }

    public void addItem(WalkthroughItem walkthroughItem) {
        if (mItems == null)
            mItems = new ArrayList<WalkthroughItem>();

        mItems.add(walkthroughItem);
    }

    public WalkthroughItem getItemAtPosition(int position) {
        return mItems.get(position);
    }
}
