package com.juxtopia.cammradtour;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import net.minidev.json.parser.ParseException;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;

public class CAMMRADTour_UI_AR extends AppCompatActivity {

    public Activity MainAct;
    public Context mainCon;
    public File pPath;

    public void displayMedia(String Path) {
        File filePath = new File(Path);
        openFile(filePath);


    }
    public void playAudio(){

    }
    public void stopDisplay(){

    }
    public String JSONReader(String strValue) throws IOException, ParseException, JSONException {
        JUICEFileManager juice = new JUICEFileManager();
        String path = juice.getPath(strValue);
        return path;
    }

    private void openFile(File file) {
        try {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            String type = "image/*";
            intent.setDataAndType(Uri.parse(file.getAbsolutePath()), type);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            MainAct.startActivity(intent);
            Toast.makeText(mainCon, "Opened", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            if(file.exists())
                Toast.makeText(mainCon, "Does not exist", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(mainCon, "Cannot open the file", Toast.LENGTH_SHORT).show();
        }
    }
}
