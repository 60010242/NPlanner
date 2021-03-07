package com.example.nplanner.service;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

@Service
public class FirebaseService {

	@PostConstruct
	private void initDB() throws IOException {

		InputStream serviceAccount = this.getClass().getClassLoader().getResourceAsStream("./nplanner-db-firebase-adminsdk-a9qon-4700dc5f6a.json");
				//new FileInputStream("path/to/serviceAccountKey.json");

		FirebaseOptions options = new FirebaseOptions.Builder()
			.setCredentials(GoogleCredentials.fromStream(serviceAccount))
			.setDatabaseUrl("https://nplanner-db-default-rtdb.firebaseio.com")
			.build();

		if (FirebaseApp.getApps().isEmpty()) {
			FirebaseApp.initializeApp(options);
		}
	}
	
	public Firestore getFirebase() {
		return FirestoreClient.getFirestore();
	}
}
