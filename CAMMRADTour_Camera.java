package com.juxtopia.cammradtour;

import android.app.Activity;
import android.content.Context;
import android.util.Size;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.zxing.Result;

import java.util.concurrent.ExecutionException;

public class CAMMRADTour_Camera extends AppCompatActivity {

    public PreviewView previewV;
    public ImageAnalysis imageAnalysis;
    public String qrCode;
    public Button qrCodeFoundButton;
    public Activity MainAct;
    public Context mainCon;
    public LifecycleOwner LF;
    public ListenableFuture<ProcessCameraProvider> cameraProviderFuture;

    public void startCamera()
    {
        cameraProviderFuture.addListener(() ->
        {
            try
            {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                Toast.makeText(mainCon, "Camera Started", Toast.LENGTH_SHORT).show();
                bindCameraPreview(cameraProvider);
            }
            catch (ExecutionException | InterruptedException error)
            {
                Toast.makeText(mainCon,
                        "There was an error trying to start the camera " + error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        }, ContextCompat.getMainExecutor(mainCon));
    }

    private void bindCameraPreview(@NonNull ProcessCameraProvider cameraProvider) {
        previewV.setPreferredImplementationMode(PreviewView.ImplementationMode.SURFACE_VIEW);

        Preview preview = new Preview.Builder()
                .build();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        preview.setSurfaceProvider(previewV.createSurfaceProvider());

        imageAnalysis =
                new ImageAnalysis.Builder()
                        .setTargetResolution(new Size(1280, 720))
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build();

        imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(mainCon), new CAMMRADTour_QRCode(new findingQrCode() {
            @Override
            public void onQRCodeFound(Result _qrCode) {
                qrCode = _qrCode.getText();
                qrCodeFoundButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void qrCodeNotFound() {
                qrCodeFoundButton.setVisibility(View.INVISIBLE);
            }
        }));

        Camera camera = cameraProvider.bindToLifecycle(LF, cameraSelector, imageAnalysis, preview);
    }

    public void stopCamera()
    {

    }
}
