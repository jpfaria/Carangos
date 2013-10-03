package br.com.caelum.fj59.carangos.infra;

import android.os.Build;

public class MyServer {
    private static String uri;

    static {
		uri = "http://carangos.herokuapp.com/%s";
    }

    public static String uriFor(String value) {
        return String.format(uri, value);
    }

    private static boolean taNoEmulador() {
        return Build.PRODUCT.equals("google_sdk")
                || Build.PRODUCT.equals("sdk");
    }

}
