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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymproject.R;
import com.example.gymproject.adapters.ProgressEntryAdapter;
import com.example.gymproject.adapters.ProgressImageAdapter;
import com.example.gymproject.managers.MyDbStorageManager;
import com.example.gymproject.models.ProgressEntry;
import com.example.gymproject.utilities.DatabaseUtils;
import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
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

        // הגדרת ה-RecyclerView להצגת תמונות לפי תאריכים
        recyclerViewProgressImages.setLayoutManager(new LinearLayoutManager(this));

        // טעינת התמונות ממסד הנתונים
        loadProgressImages();
        // Initialize the media picker
        setPickMedia();
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
    private void loadProgressImages() {
        ArrayList<ProgressEntry> progressEntries = new ArrayList<>();
        // עדכון ה-RecyclerView עם הנתונים
        DatabaseUtils.loadProgressImages(currentUser.getUid(), progressEntries, this::updateRecyclerView);
    }


    // עדכון ה-RecyclerView עם הנתונים
    private void updateRecyclerView(ArrayList<ProgressEntry> progressEntries) {
        ProgressEntryAdapter adapter = new ProgressEntryAdapter(progressEntries, this, currentUser.getUid());
        recyclerViewProgressImages.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    // Initialize the media picker for selecting images
    private void setPickMedia() {
        pickMultipleMedia = registerForActivityResult(new ActivityResultContracts.PickMultipleVisualMedia(MAX_SELECTION - imgSelected), uris -> {
            if (!uris.isEmpty()) {
                if (uris.size() > MAX_SELECTION - imgSelected) {
                    Toast.makeText(this, "You can select only " + (MAX_SELECTION - imgSelected) + " more images", Toast.LENGTH_SHORT).show();
                } else {
                    String currentYear = new SimpleDateFormat("yyyy", Locale.getDefault()).format(new Date());
                    String currentMonth = new SimpleDateFormat("MM", Locale.getDefault()).format(new Date());
                    MyDbStorageManager.getInstance().uploadBodyImages(new ArrayList<>(uris), currentUser.getUid(), currentYear, currentMonth, new MyDbStorageManager.ImgListCallBack() {
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
                                progressImageAdapter.setImgRemovedCallBack(MyProfileActivity.this::removeImage);
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
    private void removeImage(int size) {
        if (size < MAX_SELECTION)
            btnAddImageIcon.setVisibility(ImageView.GONE);
    }
}
