package me.silvernine.tutorial.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class FirebaseConfig {

    private static final String FIREBASE_CONFIG_PATH = "gcm1-9a437-firebase-adminsdk-evvpq-61ebc623b9.json";

    private final ResourceLoader resourceLoader;

    @Bean
    public void initialize() throws IOException {
        if (!FirebaseApp.getApps().isEmpty()) return;

        Resource resource = resourceLoader.getResource("classpath:" + FIREBASE_CONFIG_PATH);
        if (resource.exists()) {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(
                            GoogleCredentials.fromStream(resource.getInputStream()))
                    .build();
            FirebaseApp defaultApp = FirebaseApp.initializeApp(options);
            log.info("Firebase application has been initialized, NAME : " + defaultApp.getName());
        } else {
            log.error("firebase admin sdk json 파일 필요");
        }
    }
}
