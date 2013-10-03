package br.com.caelum.fj59.carangos.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import br.com.caelum.fj59.carangos.MainActivity;
import br.com.caelum.fj59.carangos.R;
import br.com.caelum.fj59.carangos.adapter.BlogPostAdapter;

public class ListaDePostsFragment extends Fragment {

    private ListView postsList;
    private BlogPostAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.postsList = (ListView) inflater.inflate(R.layout.posts_list, container, false);

        MainActivity activity = ((MainActivity) this.getActivity());

        this.adapter = new BlogPostAdapter(getActivity(), activity.getPosts());
        this.postsList.setAdapter(this.adapter);

        return this.postsList;
    }

}
