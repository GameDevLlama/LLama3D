package com.llama3d.object;

import java.util.ArrayList;
import java.util.List;

public class Loader implements Runnable {
    
    Thread loaderThread;
    List<Object3D>loadableObjects = new ArrayList<Object3D>();
    
    public Loader(){
        this.loaderThread = new Thread(this);
        this.loaderThread.start();
    }
    public void run(){
        System.out.println("SCHLAFEN");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e){
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("FERTIG :)");
    }
    
}
