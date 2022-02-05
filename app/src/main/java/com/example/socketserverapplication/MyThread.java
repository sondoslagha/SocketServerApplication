package com.example.socketserverapplication;

class MyThread extends Thread {
    int i;
    int j;
    int k;
    MyThread(int i, int j, int k) {
        this.i = i;
        this.j = j;
        this.k = k;
    }
    public void run() {
        //Client.a[i][j] = Client.b[i][j] + Client.a1[i][k] * Client.a2[k][j];
        MainActivity.a3[i][j] +=  MainActivity.a1[i][k] * MainActivity.a2[k][j];
    }
}