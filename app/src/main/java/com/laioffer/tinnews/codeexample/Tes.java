package com.laioffer.tinnews.codeexample;

public class Tes {

    private int a = 6;
    Tes() {


    }

    private void update() {

    }

    public class InnerTes {

        private void update() {
            a++;
            Tes.this.update();
        }
    }

    public class InnerTes1{

    }
}
