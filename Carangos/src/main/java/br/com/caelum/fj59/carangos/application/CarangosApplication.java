package br.com.caelum.fj59.carangos.application;


import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.google.android.gcm.GCMRegistrar;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.fj59.carangos.config.Constantes;
import br.com.caelum.fj59.carangos.infra.MyLog;

public class CarangosApplication extends Application {

    private List<AsyncTask<?,?,?>> tasks = new ArrayList<AsyncTask<?,?,?>>();

    private static final String REGISTERED = "registeredInGCM";
    private SharedPreferences preferences;

    @Override
    public void onCreate() {
        super.onCreate();
        preferences = getSharedPreferences("configs", Activity.MODE_PRIVATE);
        initializeGCM();
    }

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

    public void lidaComRespostaDoRegistroNoServidor(Boolean result) {

        if (result) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(REGISTERED, true);
            editor.commit();
        }

    }

    private void initializeGCM() {

        if(usuarioNaRegistrado()) {

            GCMRegistrar.checkDevice(this);
            GCMRegistrar.checkManifest(this);

            String registrationId = GCMRegistrar.getRegistrationId(this);

            if (registrationId.equals("")) {

                GCMRegistrar.register(this, Constantes.CGM_SERVER_ID);
                registrationId = GCMRegistrar.getRegistrationId(this);

                MyLog.i("ENVIANDO BROADCAST COM O ID " + registrationId);

            } else {

                MyLog.i("Device ja registrado");

            }

        }

    }

    private boolean usuarioNaRegistrado() {
        return !preferences.getBoolean(REGISTERED, false);
    }

}
