package br.com.caelum.fj59.carangos.infra;

import android.app.Application;
import android.os.Build;

import br.com.caelum.fj59.carangos.R;

public class MyServer {
    private static String uri;

    //static {
	//	uri = "http://carangos.herokuapp.com/%s";
    //}

    public MyServer(Application application) {
        if (taNoEmulador()) {
            uri = "http://10.0.2.2:8080/%s";
        } else {
            uri = application.getResources().getString(R.string.server_uri);
        }
    }

    public static String uriFor(String value) {
        return String.format(uri, value);
    }

    private static boolean taNoEmulador() {
        return Build.PRODUCT.equals("google_sdk")
                || Build.PRODUCT.equals("sdk");
    }

}
