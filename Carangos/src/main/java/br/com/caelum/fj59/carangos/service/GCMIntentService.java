package br.com.caelum.fj59.carangos.service;

import android.content.Context;
import android.content.Intent;

import com.google.android.gcm.GCMBaseIntentService;

import br.com.caelum.fj59.carangos.application.CarangosApplication;
import br.com.caelum.fj59.carangos.config.Constantes;
import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.task.RegistraDeviceTask;

public class GCMIntentService extends GCMBaseIntentService{

    public GCMIntentService() {
        super(Constantes.CGM_SERVER_ID);
    }

    @Override
    protected void onMessage(Context context, Intent intent) {
        MyLog.i("Recebendo mensagem do server via PUSH!: " + intent.getExtras());
    }

    @Override
    protected void onError(Context context, String message) {
        MyLog.i("ERROR: " + message);
    }

    @Override
    protected void onRegistered(Context context, String message) {
        MyLog.i("REGISTERED: " + message);

        CarangosApplication app = (CarangosApplication) getApplication();
        new RegistraDeviceTask(app).execute(message);
    }

    @Override
    protected void onUnregistered(Context context, String message) {
        MyLog.i("UNREGISTERED: " + message);
    }
}
