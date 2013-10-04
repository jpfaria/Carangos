package br.com.caelum.fj59.carangos.task;

import android.os.AsyncTask;

import br.com.caelum.fj59.carangos.application.CarangosApplication;
import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.gcm.InformacoesDoUsuario;
import br.com.caelum.fj59.carangos.webservice.WebClient;

public class RegistraDeviceTask extends AsyncTask<String, Void, Boolean> {

    private CarangosApplication app;

    public RegistraDeviceTask(CarangosApplication app) {
        this.app = app;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        try {
            String registrationId = params[0];
            String email = InformacoesDoUsuario.getEmail(app);

            new WebClient("device/register/" + email + "/" + registrationId).post();
        } catch (Exception e) {
            MyLog.e("Problema no registro do device no server!" + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        app.lidaComRespostaDoRegistroNoServidor(result);
    }

}
