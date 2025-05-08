package br.com.wareysis.bsm.core.firebase.auth;

import com.google.firebase.auth.FirebaseToken;

import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class AuthenticatedUser {

    private FirebaseToken firebaseToken;

    public FirebaseToken getToken() {

        return firebaseToken;
    }

    public void setToken(FirebaseToken firebaseToken) {

        this.firebaseToken = firebaseToken;
    }

    public String getUid() {

        return firebaseToken != null ? firebaseToken.getUid() : null;
    }

    public String getEmail() {

        return firebaseToken != null ? firebaseToken.getEmail() : null;
    }

}
