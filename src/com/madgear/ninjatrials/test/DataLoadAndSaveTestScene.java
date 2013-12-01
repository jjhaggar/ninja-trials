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

package com.madgear.ninjatrials.test;

import java.io.File;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.adt.align.HorizontalAlign;

import android.content.Context;
import android.util.Log;
import com.madgear.ninjatrials.GameScene;
import com.madgear.ninjatrials.achievements.AchievementSetNinjaTrial;
import com.madgear.ninjatrials.hud.SelectionStripe;
import com.madgear.ninjatrials.managers.GameManager;
import com.madgear.ninjatrials.managers.ResourceManager;
import com.madgear.ninjatrials.managers.SceneManager;
import com.madgear.ninjatrials.managers.UserData;
import com.madgear.ninjatrials.records.Record;
import com.madgear.ninjatrials.records.RecordsTableSet;

public class DataLoadAndSaveTestScene extends GameScene {
    private final static float WIDTH = ResourceManager.getInstance().cameraWidth;
    private final static float HEIGHT = ResourceManager.getInstance().cameraHeight;
    private Text tittleText;
    private SelectionStripe selectionStripe;
    private File file;
    private Context c;

    
    public DataLoadAndSaveTestScene() {
        super(0);
    }
    
    @Override
    public Scene onLoadingScreenLoadAndShown() {
        return null;
    }

    @Override
    public void onLoadingScreenUnloadAndHidden() {        
    }

    @Override
    public void onLoadScene() {
        
    }

    @Override
    public void onShowScene() {
        this.getBackground().setColor(0.9f, 0.2f, 0.9f);

        tittleText = new Text(WIDTH/2, HEIGHT - 200,
                ResourceManager.getInstance().fontMedium, "Data Load and Save Test Scene",
                new TextOptions(HorizontalAlign.CENTER),
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        attachChild(tittleText);
        
        // SelectionStripe:
        selectionStripe = new SelectionStripe(tittleText.getX(), tittleText.getY() - 400, 
                SelectionStripe.DISP_VERTICAL, 200f,
                new String[] {"Delete Data", "Write Achievs", "Write records"}, 
                SelectionStripe.TEXT_ALIGN_CENTER, 0);
        attachChild(selectionStripe);
        
        c = ResourceManager.getInstance().context;
    }

    @Override
    public void onHideScene() {        
    }

    @Override
    public void onUnloadScene() {
    }


    /**
     * Delete data files and variables
     */
    private void deleteData() {
        
        file = c.getFileStreamPath(UserData.ACHIEV_FILE_NAME);
        if(file.delete()) Log.i("data test", "achiev file deleted");
        GameManager.player1achiev = new AchievementSetNinjaTrial();
        GameManager.player2achiev = new AchievementSetNinjaTrial();
        
        file = c.getFileStreamPath(UserData.RECORDS_FILE_NAME);
        if(file.delete()) Log.i("data test", "records file deleted");
        GameManager.recordsTableSet = new RecordsTableSet();
        
    }

    /**
     * Write some values in achievments and save.
     */
    private void writeAchiev() {
        GameManager.player1achiev.achievements[20].name = "Test achiev";
        GameManager.player1achiev.achievements[20].completed = true;
        GameManager.player1achiev.achievements[21].name = "Test achiev2";
        GameManager.player1achiev.achievements[21].completed = true;
        UserData.saveAchiev(ResourceManager.getInstance().context);
    }
    
    /**
     * Write some values in records and save.
     */
    private void writeRecords() {
        Record r = new Record(0, "test", GameManager.CHAR_RYOKO, 1000);
        Record r2 = new Record(0, "test2", GameManager.CHAR_RYOKO, 2000);

        GameManager.recordsTableSet.allTimeRecords.recordsTable[11] = r;
        GameManager.recordsTableSet.allTimeRecords.recordsTable[10] = r;
        GameManager.recordsTableSet.monthRecords.recordsTable[11] = r;
        GameManager.recordsTableSet.monthRecords.recordsTable[10] = r;
        GameManager.recordsTableSet.todayRecords.recordsTable[11] = r;
        GameManager.recordsTableSet.todayRecords.recordsTable[10] = r;

        GameManager.recordsTableSet.insert(r2);
        
        UserData.saveRecords(ResourceManager.getInstance().context);
    }

    
    
    // INTERFACE:


    @Override
    public void onPressButtonMenu() {
        SceneManager.getInstance().showScene(new TestingScene());
    }
    
    @Override
    public void onPressDpadUp() {
        selectionStripe.movePrevious();
    }

    @Override
    public void onPressDpadDown() {
        selectionStripe.moveNext();
    }
    
    
    @Override
    public void onPressButtonO() {
        switch(selectionStripe.getSelectedIndex()) {
        case 0:
            // Delete Data:
            deleteData();
            break;
        case 1:
            // Write achiev:
            writeAchiev();
            break;
        case 2:
            // Write records:
            writeRecords();
            break;
        }
    }
}
