package com.firestore.firebaseConfig;

//import com.firestore.dashboard.MemberUI.Notice;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.cloud.firestore.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Dataservice {
    private static Firestore db;
    static {
        try {
            initializeFirebase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void initializeFirebase() throws IOException {

        FileInputStream serviceAccount = new FileInputStream(
                "E:\\socityyyy final\\socityyyy final\\society\\society\\firestoree\\src\\main\\resources\\societymanagement-2cb88-firebase-adminsdk-qag5i-5a5daf051e.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                // .setDatabaseUrl("https:")
                .build();

        FirebaseApp.initializeApp(options);
        db = FirestoreClient.getFirestore();

    }

    // Create operation
    public static void addMember(String name, Map<String, Object> data)
            throws ExecutionException, InterruptedException {
        DocumentReference docRef = db.collection("members").document(name);
        ApiFuture<WriteResult> result = docRef.set(data);
        result.get();
    }

    // Read operation
    public List<Map<String, Object>> getAllMembers() throws ExecutionException, InterruptedException {
        List<Map<String, Object>> documentsList = new ArrayList<>();
        ApiFuture<QuerySnapshot> querySnapshot = db.collection("members").get();

        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            documentsList.add(document.getData());
        }

        return documentsList;
    }

    // Update operation
    public void updateMember(String name, Map<String, Object> updatedData)
            throws ExecutionException, InterruptedException {
        // DocumentReference docRef = db.collection("members").document(name);
        // ApiFuture<WriteResult> future = docRef.set(updatedData, SetOptions.merge());
        // future.get(); // Block until complete
        CollectionReference collection = db.collection("members");
        DocumentReference docRef = collection.document(name);
        ApiFuture<WriteResult> future = docRef.set(updatedData, SetOptions.merge());
        future.get();

    }

    // Delete operation
    public void deleteMember(String name) throws ExecutionException, InterruptedException {

        DocumentReference docRef = db.collection("members").document(name);
        ApiFuture<WriteResult> future = docRef.delete();
        future.get();
    }

    // Create operation for adding complaints
    public void addComplaint(String document, Map<String, Object> data)
            throws ExecutionException, InterruptedException {
        DocumentReference docRef = db.collection("complaints").document(document);
        ApiFuture<WriteResult> result = docRef.set(data);
        result.get();
    }

    // Read complaints from Firestore
    public List<Map<String, Object>> getAllComplaints() throws ExecutionException, InterruptedException {
        List<Map<String, Object>> noticeList = new ArrayList<>();
        ApiFuture<QuerySnapshot> querySnapshot = db.collection("complaints").get();

        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            noticeList.add(document.getData());
        }

        return noticeList;
    }

    // Create operation for adding notices
    public void addNotice(String document, Map<String, Object> data)
            throws ExecutionException, InterruptedException {
        DocumentReference docRef = db.collection("notices").document(document);
        ApiFuture<WriteResult> result = docRef.set(data);
        result.get();
    }

    // Read complaints from Firestore
    public List<Map<String, Object>> getAllNotices() throws ExecutionException, InterruptedException {
        List<Map<String, Object>> complaintsList = new ArrayList<>();
        ApiFuture<QuerySnapshot> querySnapshot = db.collection("notices").get();

        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            complaintsList.add(document.getData());
        }

        return complaintsList;
    }

    public void addData(String collection, String document, String email, String flatNo, String mobileNo,
            String noOfFamilynumber, Map<String, Object> data)
            throws ExecutionException, InterruptedException {
        DocumentReference docRef = db.collection(collection).document(document);
        ApiFuture<WriteResult> result = docRef.set(data);
        result.get();
    }

    public DocumentSnapshot getData(String collection, String key)
            throws ExecutionException, InterruptedException {
        try {
            DocumentReference docRef = db.collection(collection).document(key);
            ApiFuture<DocumentSnapshot> future = docRef.get();
            return future.get();
        } catch (Exception e) {

            e.printStackTrace(); // Example: Print the stack trace for debugging
            throw e; // Re-throw the exception or handle it based on your application's needs
        }
    }

    public boolean authenticateUser(String username, String password)
            throws ExecutionException, InterruptedException {
        DocumentSnapshot document = db.collection("users").document(username).get().get();
        if (document.exists()) {

            String storedPassword = document.getString("password");

            // return password.equals(storedPassword);
            System.out.println(document.getString("username"));

            if (storedPassword == null) {
                System.out.println("One of the required fields is null in the Firestore document.");
                return false;
            }
            boolean isPasswordMatch = password.equals(storedPassword);

            System.out.println("Password match: " + isPasswordMatch);

            return isPasswordMatch;

        } else {
            System.out.println("No document found for user: " + username);

        }
        return false;
    }

    public boolean isAdmin(String username) throws ExecutionException,
            InterruptedException {
        DocumentSnapshot document = db.collection("users").document(username).get().get();
        if (document.exists()) {
            String role = document.getString("role");
            System.out.println(role);
            return "admin".equals(role);
        }

        return false;
    }

    // new method

    public void deleteProject(String collectionName, String leaderName)
            throws ExecutionException, InterruptedException {

        System.out.println(leaderName);
        ApiFuture<WriteResult> writeResult = db.collection(collectionName).document(leaderName).delete();
        writeResult.get();
    }

    public List<Map<String, Object>> getDataInDescendingOrder(String collectionName,
            String orderByField) throws ExecutionException, InterruptedException {
        List<Map<String, Object>> documentsList = new ArrayList<>();

        // Create a query against the collection
        CollectionReference collection = db.collection(collectionName);
        Query query = collection.orderBy(orderByField, Query.Direction.DESCENDING);

        // Execute the query
        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            documentsList.add(document.getData());
        }

        return documentsList;
    }

    public void updateData(String collectionName, String documentId, Map<String, Object> updatedData)
            throws ExecutionException, InterruptedException {

        // Reference to the Firestore collection
        CollectionReference collection = db.collection(collectionName);

        // Reference to the document to update
        DocumentReference docRef = collection.document(documentId);

        // Update the document
        ApiFuture<WriteResult> future = docRef.set(updatedData, SetOptions.merge());

        // Wait for the update to complete
        future.get();
    }

}