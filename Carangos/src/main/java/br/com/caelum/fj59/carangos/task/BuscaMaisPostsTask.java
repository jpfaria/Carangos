package br.com.caelum.fj59.carangos.task;

import android.os.AsyncTask;

import java.util.List;

import br.com.caelum.fj59.carangos.application.CarangosApplication;
import br.com.caelum.fj59.carangos.converter.BlogPostConverter;
import br.com.caelum.fj59.carangos.modelo.BlogPost;
import br.com.caelum.fj59.carangos.receiver.EventoBlogPostsRecebidos;
import br.com.caelum.fj59.carangos.webservice.Pagina;
import br.com.caelum.fj59.carangos.webservice.WebClient;

public class BuscaMaisPostsTask extends AsyncTask<Pagina, Void, List<BlogPost>> {

    private CarangosApplication application;
    private Exception erro;

    public BuscaMaisPostsTask(CarangosApplication application) {
        this.application = application;
        application.registra(this);
    }

    @Override
    protected List<BlogPost> doInBackground(Pagina... paginas) {

        try {
            Pagina paginaParaBuscar = paginas.length > 1 ? paginas[0] : new Pagina();

            String jsonDeResposta = new WebClient("post/list?"
                    + paginaParaBuscar, application).get();

            List<BlogPost> postsRecebidos = new BlogPostConverter()
                    .toPosts(jsonDeResposta);

            return postsRecebidos;

        } catch (Exception e) {
            this.erro = e;
            return null;
        }

    }

    @Override
    protected void onPostExecute(List<BlogPost> posts) {
        super.onPostExecute(posts);

        if (posts != null) {
            EventoBlogPostsRecebidos.notificaPostsRecebidos(this.application, posts, true);
        } else {
            EventoBlogPostsRecebidos.notificaPostsRecebidos(this.application, posts, false);
        }

        this.application.desregistra(this);

    }

}
