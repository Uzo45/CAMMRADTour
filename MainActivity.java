package com.juxtopia.cammradtour;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.lifecycle.LifecycleOwner;

import com.google.common.util.concurrent.ListenableFuture;

import net.minidev.json.parser.ParseException;

import org.json.JSONException;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    public static class CAMMRADTour{
        private findingQrCode listener;

        public CAMMRADTour_Security permission = new CAMMRADTour_Security();
        public CAMMRADTour_Camera camera = new CAMMRADTour_Camera();
        public CAMMRADTour_UI_AR display = new CAMMRADTour_UI_AR();
        public CAMMRADTour_QRCode scan = new CAMMRADTour_QRCode(listener);


        public void setListener(findingQrCode listener) {
            this.listener = listener;
        }
    }

    CAMMRADTour tour = new CAMMRADTour(); //calling the class with all the classes in it
    String strPath, strKey;
    Activity mainA = this;//this is the activity for the MainActivity
    Context mainC = this;//this is the context for the MainActivity
    LifecycleOwner Lfuture = this;
    findingQrCode listener;
    ListenableFuture<ProcessCameraProvider> cameraProviderFuture;

    void setAct_Con_and_Lfuture(Activity activity, Context context, LifecycleOwner lfuture){
        tour.camera.MainAct = activity;
        tour.camera.mainCon = context;
        tour.camera.LF = lfuture;
        tour.permission.MainAct = activity;
        tour.permission.mainCon = context;
        tour.display.MainAct = activity;
        tour.display.mainCon = context;
    }

    //this will set the features form the XML file, which is the preview and the button
    void setCameraThing(PreviewView preview, Button button){
        tour.camera.previewV = preview;
        tour.camera.qrCodeFoundButton = button;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setting the listener
        tour.setListener(listener);

        //setting the activities and the contexts
        setAct_Con_and_Lfuture(mainA, mainC, Lfuture);

        //setting the camera view and the button IDs
        setCameraThing(findViewById(R.id.scanner_view), findViewById(R.id.foundButton));
        tour.camera.qrCodeFoundButton.setVisibility(View.INVISIBLE);

        cameraProviderFuture = ProcessCameraProvider.getInstance(mainC);
        tour.camera.cameraProviderFuture = cameraProviderFuture;

        tour.camera.startCamera();//starts the camera

        tour.camera.qrCodeFoundButton.setOnClickListener(v -> {
            strKey = tour.camera.qrCode;//it will get the key for the path

            try {
                strPath = tour.display.JSONReader(strKey);/*it will go through the json file and get
                 *the path
                 */
            } catch (IOException | ParseException | JSONException e) {
                e.printStackTrace();
            }
            tour.display.displayMedia(strPath);
            Toast.makeText(mainC, "Successful", Toast.LENGTH_SHORT).show();
        });

        //getting the permission
        tour.permission.getPermission();
        tour.permission.permissionStorage();
    }
}