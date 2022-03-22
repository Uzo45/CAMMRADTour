package com.juxtopia.cammradtour;

import android.os.Environment;

import androidx.core.net.ParseException;

import net.minidev.json.parser.JSONParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

@SuppressWarnings("deprecation")
public class JUICEFileManager {
    public String getFilePath (String strKey) throws IOException, ParseException, net.minidev.json.parser.ParseException, JSONException  //method that parsers through the JSON object to get the required data.
    {
        JSONParser prsParser = new JSONParser(); 										                                        //declaring and instantiating the JSON parser.
        JSONObject jsoObj = new JSONObject(); 											                                        //declaring and instantiating the JSON object.
        String strValue;											                                                        //declaring a variable for the key value which is will be stored as type string.
        File path = new File("/storage/emulated/0/Download/jsonFilePhone.json");
        Reader fleReader = new FileReader(path); 				                    //reader variable is declared and instantiated as the absolute path to the JSON file.
        jsoObj = (JSONObject)prsParser.parse(fleReader);								                                        //variable for JSON object instantiated as the casting of the JSON object using the parser variable to parse the file reader.
        strValue = jsoObj.getString(strKey);							                                                        //JIBand key value string instantiated as the casting of the string using the jsoobj variable to get the required strdataconfig parameter.

        return strValue;														                                                //the string value that must be returned as the string parameter states from the method above.
    }
    public String getPath(String strKey) throws IOException, ParseException
    {
        String strValue = null;
        String SPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        File pPath = new File(SPath, "Drive");
        //list files
        String[] listFilesS = pPath.list();
        File[] listFilesF = pPath.listFiles();
        //get length
        assert listFilesS != null;
        int length = listFilesS.length;
        int com, index = 0;
        boolean inFile = false;

        //go through file and get the path
        for(int x = 0; x < length; x++)
        {
            com = stringCompare(listFilesS[x], strKey);
            if(com == 4) {
                index = x;
                inFile = true;
                break;
            }

        }

        if(inFile) {
            assert listFilesF != null;
            File path = new File(pPath, listFilesF[index].getName());
            strValue = path.getAbsolutePath();
        }

        //return path
        return strValue;
    }
/*
    public String getFilePath2 (String strKey) throws IOException, ParseException
    {
        String strValue = null;
        JSONParser prsParser = new JSONParser(); 										                                        //declaring and instantiating the JSON parser.
        JSONObject jsoObj = new JSONObject();

        retrun strValue;
    }
 */

    int stringCompare(String str1, String str2)
    {

        int l1 = str1.length();
        int l2 = str2.length();
        int lmin = Math.min(l1, l2);

        for (int i = 0; i < lmin; i++) {
            int str1_ch = str1.charAt(i);
            int str2_ch = str2.charAt(i);

            if (str1_ch != str2_ch) {
                return str1_ch - str2_ch;
            }
        }

        if (l1 != l2) {
            return l1 - l2;
        }
        else {
            return 0;
        }
    }
}
//added the following dependencies
//implementation 'com.jayway.jsonpath:json-path:2.7.0'
