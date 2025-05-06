package br.com.wareysis.bsm.core.config.firebase;

import java.io.InputStream;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.ErrorCode;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseOptions;

import br.com.wareysis.bsm.core.config.messages.MessageService;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import io.quarkus.runtime.Startup;

@Startup
@ApplicationScoped
public class FirebaseConfig {

    @Inject
    MessageService messageService;

    @Inject
    Logger log;

    @ConfigProperty(name = "firebase.service.account.file-path")
    String serviceAccountPath;

    @PostConstruct
    void initFirebase() {

        try (InputStream serviceAccountCredentials = getClass().getClassLoader().getResourceAsStream(serviceAccountPath)) {

            if (serviceAccountCredentials == null) {
                throw new FirebaseException(ErrorCode.NOT_FOUND, "Service account file not found: " + serviceAccountPath, null);
            }

            FirebaseOptions firebaseOptions = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccountCredentials))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(firebaseOptions);
                log.info("FIREBASE: Firebase App initialized");
            } else {
                log.warn("FIREBASE: Firebase App already initialized");
            }

        } catch (Exception e) {
            log.error("FIREBASE: Error initializing Firebase App", e);
        }

    }

}
