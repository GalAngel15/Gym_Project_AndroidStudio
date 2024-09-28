package com.example.gymproject.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymproject.R;
import com.example.gymproject.adapters.ProgressImageAdapter;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class MyProfileActivity extends AppCompatActivity {

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

        findViews();
        initButtons();

        // Initialize the image list and the RecyclerView adapter
        imagesUri = new ArrayList<>();
        progressImageAdapter = new ProgressImageAdapter(imagesUri, this);
        recyclerViewProgressImages.setLayoutManager(new GridLayoutManager(this, 3)); // Display 3 images in a row
        recyclerViewProgressImages.setAdapter(progressImageAdapter);

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
        btnReturn = findViewById(R.id.btnReturnFromSettings);
    }

    // Initialize the media picker for selecting images
    private void setPickMedia() {
        pickMultipleMedia = registerForActivityResult(new ActivityResultContracts.PickMultipleVisualMedia(MAX_SELECTION - imgSelected), uris -> {
            if (!uris.isEmpty()) {
                if (uris.size() > MAX_SELECTION - imgSelected) {
                    Toast.makeText(this, "You can select only " + (MAX_SELECTION - imgSelected) + " more images", Toast.LENGTH_SHORT).show();
                } else {
                    imagesUri.addAll(uris);
                    progressImageAdapter.notifyDataSetChanged();
                    updateImgSelectUI();
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
