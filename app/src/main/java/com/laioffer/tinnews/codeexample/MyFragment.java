package com.laioffer.tinnews.codeexample;

public class MyFragment {

    public MyView onCreateView() {
        return new MyView();
    }

    public void onViewCreated(MyView view) {
        MyView myView = new MyView();
        myView.setOnClickListener(new MyView.OnClickListener() {
            @Override
            public void onClick(MyView v) {

            }
        });
    }
}
