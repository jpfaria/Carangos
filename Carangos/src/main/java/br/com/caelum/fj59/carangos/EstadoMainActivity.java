package br.com.caelum.fj59.carangos;


import android.app.Fragment;
import android.app.FragmentTransaction;

import br.com.caelum.fj59.carangos.fragment.ListaDePostsFragment;
import br.com.caelum.fj59.carangos.fragment.ProgressFragment;

public enum EstadoMainActivity {

    INICIO {
        @Override
        public void executa(MainActivity activity) {
            this.colocaOuBuscaFragmentNaTela(activity, R.id.fragment_principal,
                    ProgressFragment.class, false);

            activity.buscaPrimeirosPosts();
        }
    },
    PRIMEIROS_POSTS_RECEBIDOS {
        @Override
        public void executa(MainActivity activity) {
            this.colocaOuBuscaFragmentNaTela(activity, R.id.fragment_principal,
                    ListaDePostsFragment.class, false);
        }
    };

    Fragment colocaOuBuscaFragmentNaTela(MainActivity activity,
                                         int id,
                                         Class<? extends Fragment> fragmentClass,
                                         boolean backstack) {

        Fragment naTela = activity.getFragmentManager().findFragmentByTag(fragmentClass.getCanonicalName());

        if (naTela != null) {
            return naTela;
        }

        try {
            Fragment novoFragment = fragmentClass.newInstance();
            FragmentTransaction tx = activity.getFragmentManager().beginTransaction();
            tx.replace(id, novoFragment, fragmentClass.getCanonicalName());
            if (backstack) {
                tx.addToBackStack(null);
            }
            tx.commit();
            return novoFragment;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public abstract void executa(MainActivity activity);
}
