package br.com.caelum.fj59.carangos.gcm;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;

public class InformacoesDoUsuario {

        public static String getEmail(Context context) {

            AccountManager accountManager = AccountManager.get(context);
            Account account = getAccount(accountManager);

            if (account == null) {
                return null;
            } else {
                return account.name;
            }

        }

        public static Account getAccount(AccountManager accountManager) {
            Account[] accounts = accountManager.getAccountsByType("com.google");
            Account account;

            if(accounts.length > 0) {
                account = accounts[0];
            } else  {
                account = null;
            }

            return account;
        }

}
