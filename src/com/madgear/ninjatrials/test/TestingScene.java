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

import org.andengine.entity.scene.Scene;

import com.madgear.ninjatrials.ControllerOptionsScene;
import com.madgear.ninjatrials.GameScene;
import com.madgear.ninjatrials.MainMenuScene;
import com.madgear.ninjatrials.MainOptionsScene;
import com.madgear.ninjatrials.managers.ResourceManager;
import com.madgear.ninjatrials.managers.SceneManager;
import com.madgear.ninjatrials.trials.TrialSceneCut;
import com.madgear.ninjatrials.trials.TrialSceneJump;
import com.madgear.ninjatrials.trials.TrialSceneShuriken;

public class TestingScene extends GameScene {
    private TestGrid testGrid;
    
    public TestingScene() {
        super(0f);  // loading screen disabled.
    }

    @Override
    public Scene onLoadingScreenLoadAndShown() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onLoadingScreenUnloadAndHidden() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onLoadScene() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onShowScene() {
        this.getBackground().setColor(0f, 0f, 0f);
        testGrid = new TestGrid(100);
        populateGrid();
        testGrid.selectItem(0);
        attachChild(testGrid);
    }

    @Override
    public void onHideScene() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onUnloadScene() {
        // TODO Auto-generated method stub
        
    }

    /**
     * Add items to the grid
     */
    private void populateGrid() {
        testGrid.addItem(new TestGridItem("Main Menu") {
            @Override
            public void onAction() {
                SceneManager.getInstance().showScene(new MainMenuScene());
            }
        });
        
        testGrid.addItem(new TestGridItem("Main Opts") {
            @Override
            public void onAction() {
                SceneManager.getInstance().showScene(new MainOptionsScene());
            }
        });

        testGrid.addItem(new TestGridItem("Ctrl. Opts") {
            @Override
            public void onAction() {
                SceneManager.getInstance().showScene(new ControllerOptionsScene());
            }
        });
        
        testGrid.addItem(new TestGridItem("Trial Cut") {
            @Override
            public void onAction() {
                SceneManager.getInstance().showScene(new TrialSceneCut());
            }
        });
        
        testGrid.addItem(new TestGridItem("Trial Jump") {
            @Override
            public void onAction() {
                SceneManager.getInstance().showScene(new TrialSceneJump());
            }
        });
        
        testGrid.addItem(new TestGridItem("Trial Shrkn") {
            @Override
            public void onAction() {
                SceneManager.getInstance().showScene(new TrialSceneShuriken());
            }
        });
        
        testGrid.addItem(new TestGridItem("Music Test") {
            @Override
            public void onAction() {
                SceneManager.getInstance().showScene(new MusicTest());
            }
        });
        
    }

    @Override
    public void onPressDpadLeft() {
        testGrid.moveLeft();
    }

    @Override
    public void onPressDpadRight() {
        testGrid.moveRight();
    }

    @Override
    public void onPressDpadUp() {
        testGrid.moveUp();
    } 

    @Override
    public void onPressDpadDown() {
        testGrid.moveDown();
    }  

    @Override
    public void onPressButtonMenu() {
        if (ResourceManager.getInstance().engine != null) {
            SceneManager.getInstance().mCurrentScene.onHideManagedScene();
            SceneManager.getInstance().mCurrentScene.onUnloadManagedScene();
            ResourceManager.getInstance().unloadHUDResources();
            ResourceManager.getInstance().unloadFonts();
            System.exit(0);
        }
    }

    @Override
    public void onPressButtonO() {
        testGrid.onActionPressed();
    }
}
