package br.com.wareysis.bsm.core.firebase.auth;

import java.util.Set;

import org.jboss.logging.Logger;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class FirebaseAuthFilter implements ContainerRequestFilter {

    @Inject
    Logger log;

    @Override
    public void filter(ContainerRequestContext requestContext) {

        String path = requestContext.getUriInfo().getPath();

        if (publicEndpoints(path).equals(Boolean.TRUE)) {
            return;
        }

        String authHeader = requestContext.getHeaderString("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            requestContext.abortWith(Response.status(Status.UNAUTHORIZED).build());
            return;
        }

        String token = authHeader.replaceFirst("^Bearer\\s+", "");

        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            requestContext.setProperty("firebaseUser", decodedToken);
        } catch (Exception e) {
            log.error("FIREBASE: Erro ao validar token", e);
            requestContext.abortWith(Response.status(Status.UNAUTHORIZED).build());
        }

    }

    private Boolean publicEndpoints(String path) {

        for (String publicPath : publicPahts) {
            if (path.startsWith(publicPath)) {
                return true;
            }
        }

        return false;
    }

    private static final Set<String> publicPahts = Set.of(
            "/usuarios/registro"
    );
}
