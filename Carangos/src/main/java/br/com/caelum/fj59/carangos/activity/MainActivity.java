package br.com.caelum.fj59.carangos.activity;

import android.os.Bundle;
import android.app.Activity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.fj59.carangos.R;
import br.com.caelum.fj59.carangos.activity.EstadoMainActivity;
import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.application.CarangosApplication;
import br.com.caelum.fj59.carangos.delegate.BuscaMaisPostsDelegate;
import br.com.caelum.fj59.carangos.modelo.BlogPost;
import br.com.caelum.fj59.carangos.receiver.EventoBlogPostsRecebidos;
import br.com.caelum.fj59.carangos.task.BuscaMaisPostsTask;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;

public class MainActivity extends Activity implements BuscaMaisPostsDelegate {

    private static final String ESTADO_ATUAL = "ESTADO_ATUAL";
    private static final String POSTS = "POSTS";

    private List<BlogPost> posts;
    private EstadoMainActivity estado;
    private EventoBlogPostsRecebidos evento;
    private PullToRefreshAttacher attacher;

    public List<BlogPost> getPosts() {
        return posts;
    }

    public PullToRefreshAttacher getAttacher() {
        return attacher;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        this.attacher = PullToRefreshAttacher.get(this);

        this.estado = EstadoMainActivity.INICIO;

        if (savedInstanceState != null) {
            this.estado = (EstadoMainActivity) savedInstanceState.getSerializable(ESTADO_ATUAL);
            this.posts = (List<BlogPost>) savedInstanceState.getSerializable(POSTS);
        }

        this.evento = EventoBlogPostsRecebidos.registraObservador(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyLog.i("EXECUTANDO ESTADO:" + this.estado);
        this.estado.executa(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        MyLog.i("SALVANDO ESTADO!");
        outState.putSerializable(ESTADO_ATUAL, this.estado);
        outState.putSerializable(POSTS, (ArrayList<BlogPost>) this.posts);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.evento.desregistra(getCarangosApplication());
    }

    @Override
    public void lidaComRetorno(List<BlogPost> post) {
        this.posts = post;
        this.estado = EstadoMainActivity.PRIMEIROS_POSTS_RECEBIDOS;
        this.estado.executa(this);
        this.attacher.setRefreshComplete();
    }

    @Override
    public void lidaComErro(Exception e) {
        Toast.makeText(this, "Erro ao buscar dados", Toast.LENGTH_LONG).show();
    }

    @Override
    public CarangosApplication getCarangosApplication() {
        return (CarangosApplication) getApplication();
    }

    public void buscaPrimeirosPosts() {
        new BuscaMaisPostsTask(getCarangosApplication()).execute();
    }

    public void alteraEstadoPara(EstadoMainActivity estado) {
        this.estado = estado;
        this.estado.executa(this);
    }

}
