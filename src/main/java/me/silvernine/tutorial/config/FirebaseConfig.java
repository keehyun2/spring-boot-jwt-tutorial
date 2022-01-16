package me.silvernine.tutorial.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@Configuration
@Slf4j
public class FirebaseConfig {

    private static final String FIREBASE_CONFIG_PATH = "yogi-bc96d-firebase-adminsdk-v7nhg-e6ce006a8f.json";

    @Bean
    public void initailize() throws IOException {
        if (!FirebaseApp.getApps().isEmpty()) return;
        FirebaseOptions options = FirebaseOptions.builder()
            .setCredentials(
                GoogleCredentials.fromStream(new ClassPathResource(FIREBASE_CONFIG_PATH).getInputStream()))
            .build();
        FirebaseApp defaultApp = FirebaseApp.initializeApp(options);
        log.info("Firebase application has been initialized, NAME : " + defaultApp.getName());
    }
}
