package com.example.gymproject.managers;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.gymproject.models.User;
import com.example.gymproject.utilities.DatabaseUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MyDbStorageManager {

    private static Context context;
    private static volatile MyDbStorageManager instance;
    private FirebaseStorage storage;
    private DatabaseReference database;

    private MyDbStorageManager(Context context) {
        this.context = context;
        this.storage = FirebaseStorage.getInstance();
        this.database = FirebaseDatabase.getInstance().getReference(); // Reference to Firebase Realtime Database

    }

    public static MyDbStorageManager init(Context context) {
        if (instance == null) {
            synchronized (MyDbStorageManager.class) {
                if (instance == null)
                    instance = new MyDbStorageManager(context);
            }
        }
        return getInstance();
    }

    public static MyDbStorageManager getInstance() {
        return instance;
    }

    // Upload profile picture to Firebase Storage
    public void uploadProfilePictureUrl(Uri uri, String userUid, ImgCallBack imgcallBack) {
        StorageReference imageRef = storage.getReference().child("Users").child(userUid + ".jpg");
        uploadTask(imageRef, imageRef.putFile(uri), imgcallBack);
    }

    // Delete profile picture or other user images
    public void deleteProfilePicture(User user){
        if (user.getProfilePictureUrl() != null && !user.getProfilePictureUrl().isEmpty()) {
            StorageReference imageRef = storage.getReferenceFromUrl(user.getProfilePictureUrl());
            imageRef.delete().addOnSuccessListener(aVoid -> {
                Log.d("DeleteProfilePicture", "Profile picture deleted successfully");
            }).addOnFailureListener(exception -> {
                Log.d("DeleteProfilePicture", "Error deleting profile picture: " + exception.getMessage());
            });
        } else {
            Log.d("DeleteProfilePicture", "No profile picture to delete");
        }
    }

    /*public void deleteFile(User user){
        StorageReference rootRef = storage.getReference().child("Houses").child(user.getUserId());

        for(int i = 0 ; i < user.getProfilePictureUrl().size() ; i++){
            String imgId = "img" + i;
            StorageReference imageRef = rootRef.child(imgId + ".jpg");
            imageRef.delete();
        }
//        rootRef.delete().addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                // Folder and images deleted successfully
//                Log.d("Delete","House folder and images deleted successfully");
//            } else {
//                // Handle the error
//                Log.d("Delete","Error deleting house folder and images: " + task.getException().getMessage());
//            }
//        });
    }*/

    public void uploadBodyImages(ArrayList<Uri> imagesUri, String userUid, String currentYear, String currentMonth, ImgListCallBack imagesCallBack) {
        String imgId;

        StorageReference rootRef = storage.getReference("users").child(userUid).child("bodyProgress").child(currentYear).child(currentMonth);
        UploadTask uploadTask;
        ArrayList<String> imageUrls = new ArrayList<>();
        for(int i = 0 ; i < imagesUri.size() ; i++){
            int j = i;
            imgId = "img" + i;
            StorageReference imageRef = rootRef.child(imgId + ".jpg");
            uploadTask = imageRef.putFile(imagesUri.get(i));
            // Register observers to listen for when the download is done or if it fails
            String finalImgId = imgId;
            uploadTask.addOnFailureListener(exception -> {
                Log.e("FirebaseStorage", "Failed to upload image: " + exception.getMessage());
                imagesCallBack.onFailure(exception);
            }).addOnSuccessListener((taskSnapshot) -> {
                        Log.e("FirebaseStorage", "Image uploaded successfully");
                        imageRef.getDownloadUrl().addOnCompleteListener((task) -> {
                            String imageUrl = task.getResult().toString();
                            imageUrls.add(imageUrl);
                            DatabaseUtils.uploadBodyImages(imageUrl, userUid, currentYear, currentMonth, finalImgId);

                            if(j == imagesUri.size()-1) {
                                imagesCallBack.onSuccess(imageUrls);
                            }
                        });
                    });
        }
    }

    public void deleteProfilePicture(String userUid, String imageUrl, ImgCallBack imgcallBack) {
        StorageReference imageRef = storage.getReferenceFromUrl(imageUrl);
        imageRef.delete().addOnSuccessListener(aVoid -> {
            // מחיקת ה-URL מה-DB
            String currentMonth = new SimpleDateFormat("yyyy/MM", Locale.getDefault()).format(new Date());
            database.child("users").child(userUid).child("bodyProgress").child(currentMonth).orderByValue().equalTo(imageUrl)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                snapshot.getRef().removeValue();
                            }
                            imgcallBack.onSuccess(null); // מחיקה הצליחה
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            imgcallBack.onFailure(error.toException()); // טיפול בשגיאות
                        }
                    });
        }).addOnFailureListener(exception -> {
            imgcallBack.onFailure(exception); // טיפול בשגיאות במחיקת התמונה מה-Storage
        });
    }


    private void uploadTask(StorageReference imageRef, UploadTask uploadTask, ImgCallBack imgcallBack){
        uploadTask.addOnFailureListener((exception) -> {
            imgcallBack.onFailure(exception);
        }).addOnSuccessListener((taskSnapshot) -> imageRef.getDownloadUrl().addOnCompleteListener((task) -> {
            String imageUrl = task.getResult().toString();
            imgcallBack.onSuccess(imageUrl);
        }));
    }

    public interface ImgListCallBack {
        void onSuccess(ArrayList<String> list);
        void onFailure(Exception exception);

    }

    public interface ImgCallBack {
        void onSuccess(String imageUrl);
        void onFailure(Exception exception);
    }
}

