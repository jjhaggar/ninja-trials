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

import android.content.Context;
import android.content.SharedPreferences;


public class UserData {

    private static UserData INSTANCE;

    // Include a 'filename' for our shared preferences
    private static final String PREFS_NAME = "GAME_USERDATA";

    /* These keys will tell the shared preferences editor which
       data we're trying to access */

    private static final String UNLOCKED_LEVEL_KEY = "unlockedLevels";
    private static final String SOUND_KEY = "soundKey";

    /* Create our shared preferences object & editor which will
       be used to save and load data */
    private SharedPreferences mSettings;
    private SharedPreferences.Editor mEditor;

    // keep track of our max unlocked level
    private int mUnlockedLevels;

    // keep track of whether or not sound is enabled
    private boolean mSoundEnabled;

    UserData() {
        // The constructor is of no use to us
    }

    public synchronized static UserData getInstance() {
        if(INSTANCE == null){
            INSTANCE = new UserData();
        }
        return INSTANCE;
    }

    public synchronized void init(Context pContext) {
        if (mSettings == null) {
            /* Retrieve our shared preference file, or if it's not yet
             * created (first application execution) then create it now
             */
            mSettings = pContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

            /* Define the editor, used to store data to our preference file
             */
            mEditor = mSettings.edit();

            /* Retrieve our current unlocked levels. if the UNLOCKED_LEVEL_KEY
             * does not currently exist in our shared preferences, we'll create
             * the data to unlock level 1 by default
             */
            mUnlockedLevels = mSettings.getInt(UNLOCKED_LEVEL_KEY, 1);

            /* Same idea as above, except we'll set the sound boolean to true
             * if the setting does not currently exist
             */
            mSoundEnabled = mSettings.getBoolean(SOUND_KEY, true);
        }
    }

    /* retrieve the max unlocked level value */
    public synchronized int getMaxUnlockedLevel() {
        return mUnlockedLevels;
    }

    /* retrieve the boolean defining whether sound is muted or not */
    public synchronized boolean isSoundMuted() {
        return mSoundEnabled;
    }

    /* This method provides a means to increase the max unlocked level
     * by a value of 1. unlockNextLevel would be called if a player
     * defeats the current maximum unlocked level
     */
    public synchronized void unlockNextLevel() {
        // Increase the max level by 1
        mUnlockedLevels++;

        /* Edit our shared preferences unlockedLevels key, setting its
         * value our new mUnlockedLevels value
         */
        mEditor.putInt(UNLOCKED_LEVEL_KEY, mUnlockedLevels);

        /* commit() must be called by the editor in order to save
         * changes made to the shared preference data
         */
        mEditor.commit();
    }

    /* The setSoundMuted method uses the same idea for storing new data
     * into the shared preferences. First, we overwrite the mSoundEnabled
     * boolean, use the putBoolean method to store the data, and finally
     * commit the data to the shared preferences
     */
    public synchronized void setSoundMuted(final boolean pEnableSound) {
        mSoundEnabled = pEnableSound;
        mEditor.putBoolean(SOUND_KEY, mSoundEnabled);
        mEditor.commit();
    }
}
