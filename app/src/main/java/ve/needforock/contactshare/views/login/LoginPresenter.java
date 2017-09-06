package ve.needforock.contactshare.views.login;

import com.google.firebase.auth.FirebaseUser;

import ve.needforock.contactshare.data.CurrentUser;

/**
 * Created by Soporte on 26-Aug-17.
 */

public class LoginPresenter {
    LoginCallback loginCallback;

    public LoginPresenter(LoginCallback loginCallback) {
        this.loginCallback = loginCallback;
    }

    public void validation(){
        FirebaseUser currentUser = new CurrentUser().getCurrentUser();
        if (currentUser != null) {
            loginCallback.logged();
        } else {
            loginCallback.signUp();
        }
    }
}
