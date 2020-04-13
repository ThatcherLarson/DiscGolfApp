package com.example.discgolfapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import controllers.ProfileController;
import io.grpc.Context;
import models.ProfileModel;
import util.Constants;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth auth;
    private ProfileController controller;
    private ProfileModel model;
    private Button btnSignOut;
    private TextView textName;
    private TextView textEmail;
    private Button btnResetPass;
    private ImageButton btnEditName;
    private ImageButton btnEditEmail;
    private ProgressBar update;
    private ImageView profImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        auth = FirebaseAuth.getInstance();
        controller = new ProfileController(auth, this);
        model = new ProfileModel();

        btnSignOut = findViewById(R.id.signOutBtn);
        btnEditName = findViewById(R.id.btnEditName);
        btnEditEmail = findViewById(R.id.btnEditEmail);
        textName = findViewById(R.id.textName);
        textEmail = findViewById(R.id.textEmailAddr);
        btnResetPass = findViewById(R.id.btnResetPassword);
        update = findViewById(R.id.updateBar);
        profImg = findViewById(R.id.imageProfilePic);

        btnSignOut.setOnClickListener(this);
        btnEditName.setOnClickListener(this);
        btnEditEmail.setOnClickListener(this);
        btnResetPass.setOnClickListener(this);
        profImg.setOnClickListener(this);

        update.setVisibility(View.INVISIBLE);

        FirebaseUser user = auth.getCurrentUser();

        updateUi(user);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nav_menu, menu);
        return true;
    }

    private void updateUi(FirebaseUser user) {

        if (user != null) {
            textName.setText(user.getDisplayName());
            textEmail.setText(user.getEmail());
            if (user.getPhotoUrl() != null) {
                loadImage(user.getPhotoUrl(), true);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signOutBtn:
                signOut();
                break;

            case R.id.btnResetPassword:
                update.setVisibility(View.VISIBLE);
                controller.resetPassword().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        update.setVisibility(View.INVISIBLE);
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "A password reset email has been sent to your account email.", Toast.LENGTH_LONG).show();
                        } else {
                            String message = task.getException().getMessage();
                            Toast.makeText(getApplicationContext(), "An exception occurred: " + message, Toast.LENGTH_LONG).show();
                        }
                        signOut();
                    }
                });
                break;

            case R.id.btnEditName:
                buildEditDialog(false);
                break;

            case R.id.btnEditEmail:
                buildEditDialog(true);
                break;

            case R.id.imageProfilePic:
                buildEditProfPicDialog();
                break;

            default:
        }
    }

    private void signOut() {
        controller.signOutUser();
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
    }

    private void buildEditDialog(final boolean editEmail) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_edit_profile, null);
        builder.setView(view);
        final EditText value = view.findViewById(R.id.editField);
        String title = getResources().getString(R.string.string_change) + " ";
        if (editEmail) {
            title +=  getResources().getString(R.string.prompt_email);
        } else {
            title += getResources().getString(R.string.string_name);
        }

        builder.setTitle(title);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int field = 0;
                if (editEmail) {
                    field = Constants.EMAIL;
                } else {
                    field = Constants.NAME;
                }
                update.setVisibility(View.VISIBLE);
                controller.updateProfile(field, value.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        update.setVisibility(View.INVISIBLE);
                        if (task.isSuccessful() && editEmail) {
                            Toast.makeText(getApplicationContext(), "A verification email has been sent to your new email. Please verify your new email.", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
                            startActivity(intent);
                        } else if (task.isSuccessful()) {
                            updateUi(auth.getCurrentUser());
                        } else {
                            String message = task.getException().getMessage();
                            Toast.makeText(getApplicationContext(), "An error has occurred: " + message, Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void buildEditProfPicDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Change Profile Picture?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPhotoGalleryPermissions();
                } else {
                    Intent photoIntent = controller.openPhotoGallery();
                    startActivityForResult(photoIntent, Constants.RESULT_SEL_IMG);
                }
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create();
        builder.show();

    }

    private void requestPhotoGalleryPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Constants.REQUEST_READ_GALLERY);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Constants.REQUEST_READ_GALLERY
            && grantResults.length > 0
            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Intent photoIntent = controller.openPhotoGallery();
            startActivityForResult(photoIntent, Constants.RESULT_SEL_IMG);
        } else {
            Toast.makeText(this, "We do not have permission to access the photo gallery. Please change your app permission settings.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            loadImage(data.getData(), false);
        } else {
            Toast.makeText(this, "An image was not selected.", Toast.LENGTH_LONG).show();
        }
    }

    public void loadImage(Uri uri, boolean onCreate) {
        update.setVisibility(View.VISIBLE);
        try {
            if (!onCreate) {
                final InputStream iStream = getContentResolver().openInputStream(uri);
                final Bitmap img = BitmapFactory.decodeStream(iStream);
                uploadImage(img);
            } else {
                StorageReference ref = FirebaseStorage.getInstance().getReference().child("pics/" + auth.getCurrentUser().getUid());
                ref.getBytes(2*1024*1024).addOnCompleteListener(new OnCompleteListener<byte[]>() {
                    @Override
                    public void onComplete(@NonNull Task<byte[]> task) {
                        if (task.isSuccessful()) {
                            Bitmap img = BitmapFactory.decodeByteArray(task.getResult(), 0, task.getResult().length);
                            profImg.setImageBitmap(img);
                            update.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }
        } catch(FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "An error occurred while loading the image", Toast.LENGTH_LONG).show();
        }
    }

    private void uploadImage(Bitmap img) {
        final Bitmap imgBm = img;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final StorageReference storage = FirebaseStorage.getInstance().getReference().child("pics/" + auth.getCurrentUser().getUid());
        img.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imgBytes = baos.toByteArray();
        storage.putBytes(imgBytes).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    storage.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            controller.updateProfile(Constants.PICTURE, task.getResult().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        update.setVisibility(View.INVISIBLE);
                                        profImg.setImageBitmap(imgBm);
                                    }
                                }
                            });
                        }
                    });
                }
            }
        });

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        //respond to menu item selection
        //respond to menu item selection
        switch (item.getItemId()) {
            case R.id.find:
                startActivity(new Intent(this, FindCourseActivity.class));
                return true;
            case R.id.profile:
                startActivity(new Intent(this, ProfileActivity.class));
                return true;
            case R.id.more:
                startActivity(new Intent(this, MoreActivity.class));
                return true;
            case R.id.bag:
                startActivity(new Intent(this, MyBagActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
