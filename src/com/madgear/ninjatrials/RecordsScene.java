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


package com.madgear.ninjatrials;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.adt.align.HorizontalAlign;

import com.madgear.ninjatrials.managers.GameManager;
import com.madgear.ninjatrials.managers.ResourceManager;
import com.madgear.ninjatrials.managers.SceneManager;
import com.madgear.ninjatrials.records.Record;
import com.madgear.ninjatrials.records.RecordsTable;
import com.madgear.ninjatrials.records.RecordsTableSet;
import com.madgear.ninjatrials.test.TestingScene;

/**
 * Records Scene
 * @author Madgear Games
 *
 */
public class RecordsScene extends GameScene {
    
    private final static float WIDTH = ResourceManager.getInstance().cameraWidth;
    private final static float HEIGHT = ResourceManager.getInstance().cameraHeight;
    
    
    private static final float SHOW_SCENE_TIME = 10f;
    private boolean pressButtonEnabled = true;
    private TimerHandler timerHandler;
    private Text recordsTittle;
    private RecordsTableEntity todayRT, monthRT, alltimeRT;

    
    public RecordsScene() {
        super(0f);  // loading screen disabled.
    }
    
    
    @Override
    public Scene onLoadingScreenLoadAndShown() {
        return null;
    }

    @Override
    public void onLoadingScreenUnloadAndHidden() {}

    @Override
    public void onLoadScene() {
        ResourceManager.getInstance().loadRecordsResources();
    }

    @Override
    public void onShowScene() {
        // Bg:
        // TODO: background
        getBackground().setColor(0f, 0f, 0.1f);
        
        // tittle:
        recordsTittle = new Text(WIDTH/2, HEIGHT - 110,
                ResourceManager.getInstance().fontBig,
                ResourceManager.getInstance().loadAndroidRes().
                    getString(R.string.record_title),
                new TextOptions(HorizontalAlign.CENTER),
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        attachChild(recordsTittle);
        
        // Today records:
        todayRT = new RecordsTableEntity(372, HEIGHT - 230,
                ResourceManager.getInstance().loadAndroidRes().getString(R.string.record_today),
                GameManager.recordsTableSet.todayRecords);
        attachChild(todayRT);
        
        // Month records:
        monthRT = new RecordsTableEntity(WIDTH/2, HEIGHT - 230,
                ResourceManager.getInstance().loadAndroidRes().getString(R.string.record_month),
                GameManager.recordsTableSet.monthRecords);
        attachChild(monthRT);
        
        // All Time records:
        alltimeRT = new RecordsTableEntity(1570, HEIGHT - 230,
                ResourceManager.getInstance().loadAndroidRes().getString(R.string.record_ever),
                GameManager.recordsTableSet.allTimeRecords);
        attachChild(alltimeRT);

    }

    @Override
    public void onHideScene() {}

    @Override
    public void onUnloadScene() {
        ResourceManager.getInstance().loadRecordsResources();
    }
    
    
    
    // INTERFACE -----------------------------------------------
    
    /**
     * If we press the O button go to the next scene or testing scene.
     */
    @Override
    public void onPressButtonO() {
        if(pressButtonEnabled)
            if(GameManager.DEBUG_MODE)
                SceneManager.getInstance().showScene(new TestingScene());
            else
                SceneManager.getInstance().showScene(new MainMenuScene());
    }
    
    @Override
    public void onPressButtonMenu() {
        onPressButtonO();
    }
    
    // AUX CLASS ------------------------------------------------
    
    class RecordsTableEntity extends Entity {
                
        /**
         * 
         * @param x the tittle x pos
         * @param y the tittle y pos
         * @param tittle the table name
         * @param rt data source
         */
        public RecordsTableEntity(float x, float y, String tittle, RecordsTable rt) {
            Text tittleText = new Text(x, y,
                    ResourceManager.getInstance().fontMedium,
                    tittle,
                    new TextOptions(HorizontalAlign.CENTER),
                    ResourceManager.getInstance().engine.getVertexBufferObjectManager());
            attachChild(tittleText);
            
            for (int i = 0; i < RecordsTableSet.SIZE; i++) {
                RecordItem r = new RecordItem(x, y - 80 - RecordItem.ITEM_HEIGHT * i,
                        rt.recordsTable[RecordsTableSet.SIZE-i-1],
                        i == 0 ? true : false);
                attachChild(r);
            }
        }
        
    }
    
    
    class RecordItem extends Entity {
        
        public static final float ITEM_WIDTH = 520;
        public static final float ITEM_HEIGHT = 60;
        public static final float SCORE_WIDTH = 140;
        public static final float HEAD_WIDTH = 50;
        public static final float GAP = 5;
        
        Sprite head;
        Text nameText;
        Text scoreText;
        
        /**
         * 
         * @param x the x center position of record item
         * @param y the y center position of record item
         * @param r the record object
         */
        public RecordItem(float x, float y, Record r, boolean first) {
            ITextureRegion headTR = ResourceManager.getInstance().menuRecordsShoHead;
            
            // Head:
            if(r.characterId == GameManager.CHAR_SHO)
                if(first)
                    headTR = ResourceManager.getInstance().menuRecordsShoHeadGold;
                else
                    headTR = ResourceManager.getInstance().menuRecordsShoHead;
            else if(r.characterId == GameManager.CHAR_RYOKO)
                if(first)
                    headTR = ResourceManager.getInstance().menuRecordsRyokoHeadGold;
                else
                    headTR = ResourceManager.getInstance().menuRecordsRyokoHead;
            
            head = new Sprite(x - ITEM_WIDTH/2 + HEAD_WIDTH/2, y, headTR,
                    ResourceManager.getInstance().engine.getVertexBufferObjectManager());
            attachChild(head);
            
            // Name:
            nameText = new Text(x, y,
                    ResourceManager.getInstance().fontSmall,
                    r.profileName,
                    new TextOptions(HorizontalAlign.CENTER),
                    ResourceManager.getInstance().engine.getVertexBufferObjectManager());
            nameText.setX(x - ITEM_WIDTH/2 + HEAD_WIDTH + GAP + nameText.getWidth()/2);
            attachChild(nameText);
            
            // Record:
            scoreText = new Text(x, y,
                    ResourceManager.getInstance().fontSmall,
                    Integer.toString(r.score),
                    new TextOptions(HorizontalAlign.CENTER),
                    ResourceManager.getInstance().engine.getVertexBufferObjectManager());
            scoreText.setX(x + ITEM_WIDTH/2 - scoreText.getWidth()/2);
            attachChild(scoreText);
        }
    }
}