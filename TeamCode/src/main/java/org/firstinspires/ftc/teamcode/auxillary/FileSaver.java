package org.firstinspires.ftc.teamcode.auxillary;

import android.app.Application;
import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class FileSaver {
    private Context context;
    public String fileName;

    public FileSaver(String _fileName) {
        try {
            this.context = ((Application) Class.forName("android.app.ActivityThread").getMethod("currentApplication").invoke(null, (Object[]) null)).getApplicationContext();
        } catch(Exception e) {}
        this.fileName = _fileName;
    }
    public FileSaver() {}

    public ArrayList<String> readLines() {

            String thisLine;
            BufferedReader textFile;
            ArrayList<String> keyframes = new ArrayList<String>();

            try {
                textFile = new BufferedReader(new InputStreamReader(new FileInputStream(context.getExternalFilesDir(null).getPath() + "/" + fileName)));
                BufferedReader br = new BufferedReader(textFile);

                while ((thisLine = br.readLine()) != null) {
                    keyframes.add(thisLine);
                }
            } catch (Exception e) {
                //e.printStackTrace();
            }
            return keyframes;

    }

    public String getDirectory() {
        return context.getExternalFilesDir(null).getPath();
    }
    public void deleteFile() {
        File file = new File(context.getExternalFilesDir(null).getPath() + "/" + fileName);
        file.delete();
    }
    public void appendLine(String line) {
        try {
            BufferedWriter output = new BufferedWriter(new FileWriter(context.getExternalFilesDir(null).getPath() + "/" + fileName, true));
            output.newLine();
            output.write(line);
            output.close();
        } catch (Exception e) {
            //e.printStackTrace
        }
    }


}
