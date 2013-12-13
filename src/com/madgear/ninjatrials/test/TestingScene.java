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

import com.madgear.ninjatrials.AchievementsScene;
import com.madgear.ninjatrials.CharacterIntroScene;
import com.madgear.ninjatrials.ControllerOptionsScene;
import com.madgear.ninjatrials.GameScene;
import com.madgear.ninjatrials.MainMenuScene;
import com.madgear.ninjatrials.MainOptionsScene;
import com.madgear.ninjatrials.MapScene;
import com.madgear.ninjatrials.PlayerSelectionScene;
import com.madgear.ninjatrials.RecordsScene;
import com.madgear.ninjatrials.ResultLoseScene;
import com.madgear.ninjatrials.ResultWinScene;
import com.madgear.ninjatrials.managers.GameManager;
import com.madgear.ninjatrials.managers.ResourceManager;
import com.madgear.ninjatrials.managers.SceneManager;
import com.madgear.ninjatrials.sequences.CreditsScene;
import com.madgear.ninjatrials.managers.UserData;
import com.madgear.ninjatrials.sequences.EndingScene;
import com.madgear.ninjatrials.sequences.Intro1Scene;
import com.madgear.ninjatrials.sequences.Intro2Scene;
import com.madgear.ninjatrials.sequences.SplashIntroScene;
import com.madgear.ninjatrials.trials.TrialSceneCut;
import com.madgear.ninjatrials.trials.TrialSceneJump;
import com.madgear.ninjatrials.trials.TrialSceneRun;
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
        testGrid.addItem(new TestGridItem("Credits") {
            @Override
            public void onAction() {
                SceneManager.getInstance().showScene(new CreditsScene());
            }
        });

        testGrid.addItem(new TestGridItem("Ending") {
            @Override
            public void onAction() {
                SceneManager.getInstance().showScene(new EndingScene());
            }
        });

        testGrid.addItem(new TestGridItem("Intro2") {
            @Override
            public void onAction() {
                SceneManager.getInstance().showScene(new Intro2Scene());
            }
        });

        testGrid.addItem(new TestGridItem("Intro1") {
            @Override
            public void onAction() {
                SceneManager.getInstance().showScene(new Intro1Scene());
            }
        });

        testGrid.addItem(new TestGridItem("Splash") {
            @Override
            public void onAction() {
                SceneManager.getInstance().showScene(new SplashIntroScene());
            }
        });

        testGrid.addItem(new TestGridItem("Main Menu") {
            @Override
            public void onAction() {
                SceneManager.getInstance().showScene(new MainMenuScene());
            }
        });
        
        testGrid.addItem(new TestGridItem("Options") {
            @Override
            public void onAction() {
                SceneManager.getInstance().showScene(new MainOptionsScene());
            }
        });

        testGrid.addItem(new TestGridItem("Achievemt") {
            @Override
            public void onAction() {
                SceneManager.getInstance().showScene(new AchievementsScene());
            }
        });
        
        testGrid.addItem(new TestGridItem("Records") {
            @Override
            public void onAction() {
                SceneManager.getInstance().showScene(new RecordsScene());
            }
        });
        
        testGrid.addItem(new TestGridItem("Ctrl. Opts") {
            @Override
            public void onAction() {
                SceneManager.getInstance().showScene(new ControllerOptionsScene());
            }
        });
        
        testGrid.addItem(new TestGridItem("Player Selc") {
            @Override
            public void onAction() {
                SceneManager.getInstance().showScene(new PlayerSelectionScene());
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
        
        testGrid.addItem(new TestGridItem("Trial Run") {
            @Override
            public void onAction() {
                SceneManager.getInstance().showScene(new TrialSceneRun());
            }
        });
        
        testGrid.addItem(new TestGridItem("Music Test") {
            @Override
            public void onAction() {
                SceneManager.getInstance().showScene(new MusicTest());
            }
        });

        testGrid.addItem(new TestGridItem("Lose Scene") {
            @Override
            public void onAction() {
                SceneManager.getInstance().showScene(new ResultLoseScene());
            }
        });
        
        testGrid.addItem(new TestGridItem("Win Scene") {
            @Override
            public void onAction() {
                SceneManager.getInstance().showScene(new ResultWinScene());
            }
        });
        
        testGrid.addItem(new TestGridItem("Map Scene") {
            @Override
            public void onAction() {
                SceneManager.getInstance().showScene(new MapScene());
            }
        });
        
        testGrid.addItem(new TestGridItem("Chr Scene") {
            @Override
            public void onAction() {
                SceneManager.getInstance().showScene(new CharacterIntroScene());
            }
        });
        
        testGrid.addItem(new TestGridItem("DatLoad Test") {
            @Override
            public void onAction() {
                SceneManager.getInstance().showScene(new DataLoadAndSaveTestScene());
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
        GameManager.endGame();
    }

    @Override
    public void onPressButtonO() {
        testGrid.onActionPressed();
    }
}
