package br.com.caelum.fj59.carangos;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;

import com.google.android.gcm.GCMBaseIntentService;

import java.util.List;

import br.com.caelum.fj59.carangos.activity.MainActivity;
import br.com.caelum.fj59.carangos.application.CarangosApplication;
import br.com.caelum.fj59.carangos.gcm.Constantes;
import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.receiver.EventoLeilaoIniciado;
import br.com.caelum.fj59.carangos.task.RegistraDeviceTask;

public class GCMIntentService extends GCMBaseIntentService {

    public GCMIntentService() {
        super(Constantes.GCM_SERVER_ID);
    }

    @Override
    protected void onError(Context ctx, String message) {
        MyLog.i("ERROR:" + message);
    }

    @Override
    protected void onMessage(Context ctx, Intent intent) {
        MyLog.i("Recebendo mensagem do server via PUSH! "
                + intent.getExtras());

        boolean naoRodando = this.aplicacaoNaoEstaRodando(getApplication());

        if (naoRodando) {

// Intent irParaLeilao = new Intent(getApplication(), LeilaoActivity.class);
            Intent irParaLeilao = new Intent(getApplication(), MainActivity.class);

            PendingIntent acaoPendente = PendingIntent
                    .getActivity(getApplication(), 0, irParaLeilao, Intent.FLAG_ACTIVITY_NEW_TASK);

            irParaLeilao.putExtra("idDaNotificacao", Constantes.ID_NOTIFICACAO);

            Notification notificacao = new Notification.Builder(getApplication())
                    .setContentTitle("Um novo leilao começou!")
                    .setContentText("Leilao do " + intent.getExtras().getSerializable("message"))
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setContentIntent(acaoPendente)
                    .build();

            NotificationManager manager =
                    (NotificationManager)
                            getApplication().getSystemService(Context.NOTIFICATION_SERVICE);

            manager.notify(Constantes.ID_NOTIFICACAO, notificacao);

        } else {
            EventoLeilaoIniciado.notificaSucesso(getApplication(),
                    (String) intent.getSerializableExtra("message"));
        }

    }

    @Override
    protected void onRegistered(Context ctx, String message) {
        MyLog.i("REGISTERED:"+message);

        CarangosApplication app = (CarangosApplication) getApplication();

        new RegistraDeviceTask(app).execute(message);
    }

    @Override
    protected void onUnregistered(Context ctx, String message) {
        MyLog.i("UNREGISTERED:" + message);
    }

    private static boolean aplicacaoNaoEstaRodando(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

}
