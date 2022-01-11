package com.laioffer.tinnews.codeexample;

public class MyView {
//    private MyActivity myActivity;
//    private MyFragment myFragment;

    private OnClickListener onClickListener;
    public interface OnClickListener {
        void onClick(MyView v);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

//
//    public void setMyActivity(MyActivity myActivity) {
//        this.myActivity = myActivity;
//    }
//
//    public void setMyFragment(MyFragment myFragment) {
//        this.myFragment = myFragment;
//    }

    public void onHumanClick() {
        this.onClickListener.onClick(this);
//        this.myActivity.onMyViewClick();
//        this.myFragment.onMyViewClick();
    }
}
