package br.com.caelum.fj59.carangos.task;

import android.os.AsyncTask;

import br.com.caelum.fj59.carangos.application.CarangosApplication;
import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.push.InformacoesUsuario;
import br.com.caelum.fj59.carangos.webservice.WebClient;

public class RegistraDeviceTask extends AsyncTask<String, Void, Boolean> {

    private final CarangosApplication application;

    public RegistraDeviceTask(CarangosApplication application) {
        this.application  = application;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        try {

            String registrationId = params[0];
            String email = InformacoesUsuario.getEmail(application);

            new WebClient("device/register/" + email + "/" + registrationId).post();

        }catch (Exception e) {
            MyLog.e("Problema no registro do device no server!" + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);

        application.lidaComRespostaDoRegistroNoServidor(result);

    }

}
