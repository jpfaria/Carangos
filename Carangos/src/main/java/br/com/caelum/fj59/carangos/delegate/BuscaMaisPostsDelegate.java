package br.com.caelum.fj59.carangos.delegate;

import java.util.List;

import br.com.caelum.fj59.carangos.app.CarangosApplication;
import br.com.caelum.fj59.carangos.modelo.BlogPost;

public interface BuscaMaisPostsDelegate {

    void lidaComRetorno(List<BlogPost> posts);
    void lidaComErro(Exception e);
    CarangosApplication getCarangosApplication();
}