/*
 * Ninja Trials is an old school style Android Game developed for OUYA & using
 * AndEngine. It features several minigames with simple gameplay.
 * Copyright 2013 Mad Gear Games <madgeargames@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.madgear.ninjatrials.managers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.madgear.ninjatrials.achievements.AchievementSetNinjaTrial;
import com.madgear.ninjatrials.records.RecordsTableSet;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Saves and loads the user data from the local machine. The data stored is:
 * - User preferences (from options menu).
 * - Player achievments.
 * - Player records.
 * 
 * @author Madgear Games
 *
 */
public class UserData {

    public static final String PREFS_FILE_NAME = "NINJATRIALS_PREFS_DATA";
    public static final String ACHIEV_FILE_NAME = "NINJATRIALS_ACHIEV_DATA";
    public static final String RECORDS_FILE_NAME = "NINJATRIALS_RECORDS_DATA";

    // DATA KEYS:
    private static final String SOUND_VOL_KEY = "soundVolKey";
    private static final String MUSIC_VOL_KEY = "musicVolKey";
    
    /* Create our shared preferences object & editor which will be used to save and load data */
    private static SharedPreferences mSettings;
    private static SharedPreferences.Editor mEditor;


    /**
     * Restore user prefs and achievments from local machine.
     * @param c
     */
    public static synchronized void init(Context c) {
        // Init prefs editor:
        if (mSettings == null) {
            mSettings = c.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE);
            mEditor = mSettings.edit();
        }
        loadPrefs(c);
        loadAchiev(c);
        loadRecords(c);
    }

    
    /**
     * Load user preferences from local machine drive
     * If the key does not exists sets to initial value
     */
    public static synchronized void loadPrefs(Context c) {
        SFXManager.setSoundVolume(mSettings.getFloat(SOUND_VOL_KEY, SFXManager.SOUND_VOL_INIT));
        SFXManager.setMusicVolume(mSettings.getFloat(MUSIC_VOL_KEY, SFXManager.MUSIC_VOL_INIT));
    }

    /**
     * Writes prefs to local drive.
     */
    public static synchronized void savePrefs() {
        mEditor.putFloat(SOUND_VOL_KEY, SFXManager.getSoundVolume());
        mEditor.putFloat(MUSIC_VOL_KEY, SFXManager.getMusicVolume());
        mEditor.commit();
    }


    /**
     * Restore user achievments from file or create new file.
     * @param c context
     */
    public static synchronized void loadAchiev(Context c) {
        try {
            FileInputStream fis = c.openFileInput(ACHIEV_FILE_NAME);
            ObjectInputStream is = new ObjectInputStream(fis);
            GameManager.setAchievSet((AchievementSetNinjaTrial) is.readObject());
            is.close();
            Log.i("UserData", "Achievs loaded from local machine. Path: " + c.getFilesDir());
        }
        catch(FileNotFoundException e) {
            // If file dont exits then create it, and save achievs to file   :)
            saveAchiev(c);
        }
        catch(IOException e){
            Log.e("UserData", "Cannot perform input.");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            Log.e("UserData", "Class not found in file.");
            e.printStackTrace();
        }
    }
    
    /**
     * Writes achievments to local drive.
     * @param c
     */
    public static synchronized void saveAchiev(Context c) {
        try {
            FileOutputStream fos = c.openFileOutput(ACHIEV_FILE_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(GameManager.getAchievSet());
            os.close();
            Log.i("UserData", "Achievs writed to machine.");
        }
        catch(IOException e){
            Log.e("UserData", "Cannot perform output.");
            e.printStackTrace();
        }
    }
    
    /**
     * Restores records from local machine
     * @param c context
     */
    public static synchronized void loadRecords(Context c) {
        try {
            FileInputStream fis = c.openFileInput(RECORDS_FILE_NAME);
            ObjectInputStream is = new ObjectInputStream(fis);
            GameManager.recordsTableSet = (RecordsTableSet) is.readObject();
            is.close();
            Log.i("UserData", "Records loaded from local machine. Path: " + c.getFilesDir());
        }
        catch(FileNotFoundException e) {
            // If file dont exits then create it, and save records to file   :)
            saveRecords(c);
        }
        catch(IOException e){
            Log.e("UserData", "Cannot perform records input.");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            Log.e("UserData", "Class not found in file.");
            e.printStackTrace();
        }
    }


    /**
     * Writes records to local machine
     * @param c context
     */
    public static synchronized void saveRecords(Context c) {
        try {
            FileOutputStream fos = c.openFileOutput(RECORDS_FILE_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(GameManager.recordsTableSet);
            os.close();
            Log.i("UserData", "Records writed to machine.");
        }
        catch(IOException e){
            Log.e("UserData", "Cannot perform records output.");
            e.printStackTrace();
        }
    }

}
