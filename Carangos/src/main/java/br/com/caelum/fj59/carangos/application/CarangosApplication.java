package br.com.caelum.fj59.carangos.application;


import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.google.android.gcm.GCMRegistrar;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.fj59.carangos.gcm.Constantes;
import br.com.caelum.fj59.carangos.infra.MyLog;

public class CarangosApplication extends Application {

    private List<AsyncTask<?, ?, ?>> tasks = new ArrayList<AsyncTask<?, ?, ?>>();

    private static final String REGISTERED = "registeredInGCM";
    private SharedPreferences preferences;

    @Override
    public void onCreate() {
        super.onCreate();
        preferences = getSharedPreferences("configs", Activity.MODE_PRIVATE);

        initializeGCM();
    }

    public void initializeGCM() {
        if(usuarioNaoRegistrado()) {

            GCMRegistrar.checkDevice(this);
            GCMRegistrar.checkManifest(this);

            String registrationId = GCMRegistrar.getRegistrationId(this);

            if (registrationId.equals("")) {
                GCMRegistrar.register(this, Constantes.GCM_SERVER_ID);
                registrationId = GCMRegistrar.getRegistrationId(this);

                MyLog.i("ENVIANDO BROADCAST COM O ID " + registrationId);
            } else {
                MyLog.i("Device já registrado");
            }
        }
    }

    private boolean usuarioNaoRegistrado() {
        return !preferences.getBoolean(REGISTERED, false);
    }

    public void lidaComRespostaDoRegistroNoServidor(Boolean registroOk) {
        if (registroOk) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(REGISTERED, true);
            editor.commit();
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        for (AsyncTask task : this.tasks) {
            task.cancel(true);
        }
    }

    public void registra(AsyncTask<?, ?, ?> task) {
        tasks.add(task);
    }

    public void desregistra(AsyncTask<?, ?, ?> task) {
        tasks.remove(task);
    }

}
