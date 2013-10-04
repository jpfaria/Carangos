package br.com.caelum.fj59.carangos.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import java.util.List;

import br.com.caelum.fj59.carangos.R;
import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.modelo.BlogPost;

public class BlogPostAdapter extends BaseAdapter {

    private Context context;
    private final List<BlogPost> posts;

    public BlogPostAdapter(Context mContext, List<BlogPost> posts) {
        this.context = mContext;
        this.posts = posts;
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Object getItem(int i) {
        return posts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        ViewHolder holder;

        int linha;

        if (isImpar(position)) {
            linha = R.layout.post_linha_impar;
            MyLog.i("Linha IMPAR!");
        } else {
            linha = R.layout.post_linha_par;
            MyLog.i("Linha PAR!");
        }

        if (convertView != null ) {
            holder = (ViewHolder) convertView.getTag();
            MyLog.i("APROVEITOU LINHA");
        } else {
            convertView = LayoutInflater.from(context).inflate(linha, viewGroup, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
            MyLog.i("CRiOU LINHA");
        }

        preencheHolder(position, holder);

        return convertView;
    }

    private void preencheHolder(int position, ViewHolder holder) {
        BlogPost blogPost = (BlogPost) getItem(position);

        holder.mensagem.setText(blogPost.getMensagem());
        holder.nomeAutor.setText(blogPost.getAutor().getNome());

        holder.foto.setImageDrawable(this.context.getResources().getDrawable(R.drawable.ic_car));

        int idImagem = 0;
        switch (blogPost.getEstadoDeHumor()) {
            case ANIMADO: idImagem = R.drawable.ic_muito_feliz; break;
            case INDIFERENTE: idImagem = R.drawable.ic_feliz; break;
            case TRISTE: idImagem = R.drawable.ic_indiferente; break;
        }

        holder.emoticon.setImageDrawable(this.context.getResources().getDrawable(idImagem));

        UrlImageViewHelper.setUrlDrawable(holder.foto, blogPost.getFoto(),
                this.context.getResources().getDrawable(R.drawable.loading));
    }

    private boolean isImpar(int position) {
        if (position % 2 == 0) {
            return false;
        }
        return true;
    }

    class ViewHolder {

        ImageView foto;
        TextView mensagem;
        TextView nomeAutor;
        ImageView emoticon;

        ViewHolder(View linha) {
            this.foto = (ImageView) linha.findViewById(R.id.foto);
            this.mensagem = (TextView) linha.findViewById(R.id.mensagem);
            this.nomeAutor = (TextView) linha.findViewById(R.id.nome_autor);
            this.emoticon = (ImageView) linha.findViewById(R.id.emoticon);
        }

    }

}