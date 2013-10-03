package br.com.caelum.fj59.carangos;

import android.os.Bundle;
import android.app.Activity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.app.CarangosApplication;
import br.com.caelum.fj59.carangos.delegate.BuscaMaisPostsDelegate;
import br.com.caelum.fj59.carangos.modelo.BlogPost;
import br.com.caelum.fj59.carangos.receiver.EventoBlogPostsRecebidos;
import br.com.caelum.fj59.carangos.task.BuscaMaisPostsTask;

public class MainActivity extends Activity implements BuscaMaisPostsDelegate {

    private static final String ESTADO_ATUAL = "ESTADO_ATUAL";
    private static final String POSTS = "POSTS";

    private List<BlogPost> posts;
    private EstadoMainActivity estado;
    private EventoBlogPostsRecebidos evento;

    public List<BlogPost> getPosts() {
        return posts;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        this.posts = new ArrayList<BlogPost>();

        this.estado = EstadoMainActivity.INICIO;

        this.evento = EventoBlogPostsRecebidos.registraObservador(this);

        if (savedInstanceState != null) {
            this.estado = (EstadoMainActivity) savedInstanceState.getSerializable(ESTADO_ATUAL);
            this.posts = (List<BlogPost>) savedInstanceState.getSerializable(POSTS);
        }

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
        MyLog.i("SALVANDO INSTANCIA: " +  this.posts);
        outState.putSerializable(ESTADO_ATUAL, this.estado);
        outState.putSerializable(POSTS, (ArrayList<BlogPost>) this.posts);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.evento.desregistra(getCarangosApplication());
    }

    @Override
    public void lidaComRetorno(List<BlogPost> posts) {
        this.posts = posts;
        this.estado = EstadoMainActivity.PRIMEIROS_POSTS_RECEBIDOS;
        this.estado.executa(this);
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


}
