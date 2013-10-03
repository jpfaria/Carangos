package br.com.caelum.fj59.carangos.app;


import android.app.Application;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

public class CarangosApplication extends Application {

    private List<AsyncTask<?,?,?>> tasks = new ArrayList<AsyncTask<?,?,?>>();

    @Override
    public void onTerminate() {
        super.onTerminate();

        for (AsyncTask task : this.tasks) {
            task.cancel(true);
        }
    }

    public void registra(AsyncTask<?,?,?> task) {
        tasks.add(task);
    }

    public void desregistra(AsyncTask<?,?,?> task) {
        tasks.remove(task);
    }
}
