package com.laioffer.tinnews.codeexample;

public class MyActivity implements MyView.OnClickListener {

    void main() {
        onCreate();
    }

    void onAttach(MyFragment myFragment) {

    }

    public void onCreate() {
        MyView myView = new MyView();
        setContentView(myView);
//        myView.setMyActivity(this);
        myView.setOnClickListener(v -> {

        });
        myView.setOnClickListener(this);
        onAttach(new MyFragment());
    }

    public void setContentView(MyView view) {
    }

    public void onMyViewClick() {

    }

    @Override
    public void onClick(MyView v) {

    }
}
