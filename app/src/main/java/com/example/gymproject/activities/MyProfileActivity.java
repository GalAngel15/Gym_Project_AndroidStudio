package com.example.gymproject.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymproject.R;
import com.example.gymproject.adapters.ProgressEntryAdapter;
import com.example.gymproject.adapters.ProgressImageAdapter;
import com.example.gymproject.managers.MyDbStorageManager;
import com.example.gymproject.models.ProgressEntry;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class MyProfileActivity extends BaseActivity {

    private ImageView btnAddImageIcon;
    private RecyclerView recyclerViewProgressImages;
    private ProgressImageAdapter progressImageAdapter;
    private ArrayList<Uri> imagesUri;
    private ActivityResultLauncher<PickVisualMediaRequest> pickMultipleMedia;
    private static final int MAX_SELECTION = 5;
    private int imgSelected = 0;
    private MaterialButton btnReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        MyDbStorageManager.init(this);

        imagesUri = new ArrayList<>();

        findViews();
        initButtons();
//        initAdapter();

        // הגדרת ה-RecyclerView להצגת תמונות לפי תאריכים
        recyclerViewProgressImages.setLayoutManager(new LinearLayoutManager(this));

        // טעינת התמונות ממסד הנתונים
        loadProgressImages(currentUser.getUid());
        // Initialize the media picker
        setPickMedia();
    }

    private void initAdapter() {
        // Initialize the image list and the RecyclerView adapter
        imagesUri = new ArrayList<>();
        progressImageAdapter = new ProgressImageAdapter(imagesUri, this, currentUser.getUid());
        recyclerViewProgressImages.setLayoutManager(new GridLayoutManager(this, 3)); // Display 3 images in a row
        recyclerViewProgressImages.setAdapter(progressImageAdapter);
        progressImageAdapter.setImgRemovedCallBack((remove) -> updateImgSelectUI());
    }

    private void initButtons() {
        btnReturn.setOnClickListener(v -> navigateBackToPlans());
        btnAddImageIcon.setOnClickListener(v -> uploadImages());
    }

    private void navigateBackToPlans() {
        Intent intent = new Intent(this, MyPlansActivity.class);
        startActivity(intent);
    }

    private void findViews() {
        btnAddImageIcon = findViewById(R.id.btn_add_image_icon);
        recyclerViewProgressImages = findViewById(R.id.recycler_view_progress_images);
        btnReturn = findViewById(R.id.btnReturnFromProfile);
    }

    // טעינת תמונות ממסד הנתונים לפי תאריך (שנה/חודש)
    private void loadProgressImages(String userId) {
        DatabaseReference progressImagesRef = FirebaseDatabase.getInstance().getReference("users")
                .child(userId).child("bodyProgress");

        progressImagesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<ProgressEntry> progressEntries = new ArrayList<>();

                for (DataSnapshot yearSnapshot : dataSnapshot.getChildren()) {
                    String year = yearSnapshot.getKey();

                    for (DataSnapshot monthSnapshot : yearSnapshot.getChildren()) {
                        String month = monthSnapshot.getKey();
                        ArrayList<Uri> imagesForMonth = new ArrayList<>();

                        for (DataSnapshot imageSnapshot : monthSnapshot.getChildren()) {
                            String imageUrl = imageSnapshot.getValue(String.class);
                            Uri imageUri = Uri.parse(imageUrl);
                            imagesForMonth.add(imageUri);
                        }

                        // הוספת התמונות לפי החודש לרשימה
                        progressEntries.add(new ProgressEntry(year + "/" + month, imagesForMonth));
                    }
                }

                // עדכון ה-RecyclerView עם הנתונים
                updateRecyclerView(progressEntries);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase", "Failed to load data: " + databaseError.getMessage());
            }
        });
    }


    // עדכון ה-RecyclerView עם הנתונים
    private void updateRecyclerView(ArrayList<ProgressEntry> progressEntries) {
        ProgressEntryAdapter adapter = new ProgressEntryAdapter(progressEntries, this, currentUser.getUid());
        recyclerViewProgressImages.setAdapter(adapter);
    }

    // Initialize the media picker for selecting images
    private void setPickMedia() {
        pickMultipleMedia = registerForActivityResult(new ActivityResultContracts.PickMultipleVisualMedia(MAX_SELECTION - imgSelected), uris -> {
            if (!uris.isEmpty()) {
                if (uris.size() > MAX_SELECTION - imgSelected) {
                    Toast.makeText(this, "You can select only " + (MAX_SELECTION - imgSelected) + " more images", Toast.LENGTH_SHORT).show();
                } else {
                    MyDbStorageManager.getInstance().uploadBodyImages(new ArrayList<>(uris), currentUser.getUid(), new MyDbStorageManager.ImgListCallBack() {
                        @Override
                        public void onSuccess(ArrayList<String> list) {
                            if (imagesUri == null) {
                                imagesUri = new ArrayList<>();
                            }
                            imagesUri.addAll(uris);// הוספת התמונות ל-RecyclerView לאחר העלאה מוצלחת

                            // בדוק אם progressImageAdapter כבר מאותחל
                            if (progressImageAdapter == null) {
                                progressImageAdapter = new ProgressImageAdapter(imagesUri, MyProfileActivity.this, currentUser.getUid());
                                recyclerViewProgressImages.setLayoutManager(new GridLayoutManager(MyProfileActivity.this, 3)); // 3 תמונות בשורה
                                recyclerViewProgressImages.setAdapter(progressImageAdapter);
                            } else {
                                progressImageAdapter.notifyDataSetChanged();
                            }

                            updateImgSelectUI();
                        }

                        @Override
                        public void onFailure(Exception exception) {
                            Toast.makeText(MyProfileActivity.this, "שגיאה בהעלאת התמונות", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } else {
                Toast.makeText(this, "No media selected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Launch the media picker
    private void uploadImages() {
        pickMultipleMedia.launch(new PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                .build());
    }

    // Update the UI after selecting images
    private void updateImgSelectUI() {
        imgSelected = imagesUri.size();
        if (imgSelected >= MAX_SELECTION) {
            btnAddImageIcon.setVisibility(ImageView.GONE);
        } else {
            btnAddImageIcon.setVisibility(ImageView.VISIBLE);
        }
    }
}
