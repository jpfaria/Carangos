package br.com.caelum.fj59.carangos.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import br.com.caelum.fj59.carangos.activity.EstadoMainActivity;
import br.com.caelum.fj59.carangos.activity.MainActivity;
import br.com.caelum.fj59.carangos.R;
import br.com.caelum.fj59.carangos.adapter.BlogPostAdapter;
import br.com.caelum.fj59.carangos.infra.MyLog;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;

public class ListaDePostsFragment extends Fragment implements PullToRefreshAttacher.OnRefreshListener {

    private ListView postsList;
    private BlogPostAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.postsList = (ListView) inflater.inflate(R.layout.posts_list, container, false);

        MainActivity activity = ((MainActivity) this.getActivity());
        activity.getAttacher().addRefreshableView(this.postsList, this);

        this.adapter = new BlogPostAdapter(getActivity(), activity.getPosts());
        this.postsList.setAdapter(this.adapter);

        return this.postsList;
    }

    @Override
    public void onRefreshStarted(View view) {
        MyLog.i("PULL TO REFRESH INICIADO!!!!");
        MainActivity activity = (MainActivity) this.getActivity();
        activity.alteraEstadoPara(EstadoMainActivity.PULL_TO_REFRESH_REQUISITADO);
    }

}
