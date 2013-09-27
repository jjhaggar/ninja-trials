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

import org.andengine.entity.Entity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.EntityBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.adt.align.HorizontalAlign;

import com.madgear.ninjatrials.managers.ResourceManager;
import com.madgear.ninjatrials.managers.SceneManager;
import com.madgear.ninjatrials.test.TestingScene;

/**
 * Controller option screen.
 * @author Madgear Games
 *
 */
public class ControllerOptionsScene extends GameScene {
    private final static float WIDTH = ResourceManager.getInstance().cameraWidth;
    private final static float HEIGHT = ResourceManager.getInstance().cameraHeight;
    Sprite controllerSprite;
    Sprite marksSprite;
    
    /**
     * ControllerOptionsScene constructor.
     * Disabled loading screen.
     */
    public ControllerOptionsScene() {
        super(0f);
    }

    /**
     * This class is not used (loading scene is disabled).
     */
    @Override
    public Scene onLoadingScreenLoadAndShown() {
        return null;
    }

    /**
     * This class is not used (loading scene is disabled).
     */
    @Override
    public void onLoadingScreenUnloadAndHidden() {}


    @Override
    public void onLoadScene() {
        ResourceManager.getInstance().loadControllerOptionResources();        
    }

    @Override
    public void onShowScene() {
        // Background:
        // Crate the background Pattern Sprite:
        ResourceManager.getInstance().controllerOptionsPatternTR.setTextureSize(WIDTH, HEIGHT);
        Sprite patternSprite = new Sprite(WIDTH/2, HEIGHT/2,
                ResourceManager.getInstance().controllerOptionsPatternTR,
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        // Add pattern sprite to a new entity:
        Entity backgroundEntity = new Entity();
        backgroundEntity.attachChild(patternSprite);
        // Create a new background from the entity ():
        EntityBackground background = new EntityBackground(0.3f, 0.2f, 0.2f, backgroundEntity);
        setBackground(background);

        // Options tittle:
        Text tittle = new Text(WIDTH/3, 0,
                ResourceManager.getInstance().fontMedium, "Control Type: Old School",
                new TextOptions(HorizontalAlign.CENTER),
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        tittle.setY(HEIGHT - tittle.getHeight()/2 - 50);
        attachChild(tittle);   

        // Controller:
        controllerSprite = new Sprite(WIDTH / 3, HEIGHT / 2 - 100,
                ResourceManager.getInstance().controllerOuyaTR,
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        attachChild(controllerSprite);

        // Marks:
        marksSprite = new Sprite(WIDTH / 3 + 215, HEIGHT / 2 - 35,
                ResourceManager.getInstance().controllerMarksTR,
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        attachChild(marksSprite);

        // Rectangle:
        Rectangle r = new Rectangle(0, 0, 550, 200,
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        r.setX(WIDTH - r.getWidth() / 2 - 100);
        r.setY(HEIGHT - r.getHeight() / 2 - 100);
        r.setColor(android.graphics.Color.BLUE);
        r.setAlpha(0.5f);
        attachChild(r);

        // Texts:

        // Menu / In game
        Text menu = new Text(0, 0,
                ResourceManager.getInstance().fontSmall, "Menu",
                new TextOptions(HorizontalAlign.CENTER),
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        menu.setColor(android.graphics.Color.YELLOW);
        Text ingame = new Text(0, 0,
                ResourceManager.getInstance().fontSmall, " / In Game",
                new TextOptions(HorizontalAlign.CENTER),
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        menu.setX(r.getX() - 0.5f * ingame.getWidth());
        ingame.setX(r.getX() + 0.5f * menu.getWidth());
        menu.setY(r.getY() + menu.getHeight()/2 + 5f);
        ingame.setY(menu.getY());
        attachChild(menu);
        attachChild(ingame);

        // Used / Not used
        Text used = new Text(0,0,
                ResourceManager.getInstance().fontSmall, "Used",
                new TextOptions(HorizontalAlign.CENTER),
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        used.setColor(android.graphics.Color.GREEN);
        Text notused = new Text(0,0,
                ResourceManager.getInstance().fontSmall, " / Not used",
                new TextOptions(HorizontalAlign.CENTER),
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        notused.setColor(android.graphics.Color.RED);

        used.setX(r.getX() - 0.5f * notused.getWidth());
        notused.setX(r.getX() + 0.5f * used.getWidth());
        used.setY(r.getY() - used.getHeight()/2 - 5);
        notused.setY(used.getY());
        attachChild(used);
        attachChild(notused);  

        // Action
        Text action1 = new Text(0, 0,
                ResourceManager.getInstance().fontSmall, "Action",
                new TextOptions(HorizontalAlign.CENTER),
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        action1.setX(controllerSprite.getX() + 950);
        action1.setY(controllerSprite.getY() + 220);
        attachChild(action1);

        // Cancel / Action
        Text cancel = new Text(0, 0,
                ResourceManager.getInstance().fontSmall, "Cancel",
                new TextOptions(HorizontalAlign.CENTER),
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        cancel.setColor(android.graphics.Color.YELLOW);
        Text action2 = new Text(0, 0,
                ResourceManager.getInstance().fontSmall, " / Action",
                new TextOptions(HorizontalAlign.CENTER),
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());

        cancel.setX(action1.getX() - 0.5f * action2.getWidth());
        action2.setX(action1.getX() + 0.5f * cancel.getWidth());
        cancel.setY(action1.getY()- action1.getHeight() - 5f);
        action2.setY(cancel.getY());
        attachChild(cancel);
        attachChild(action2);     

        // Accept / Action
        Text accept = new Text(0, 0,
                ResourceManager.getInstance().fontSmall, "Accept",
                new TextOptions(HorizontalAlign.CENTER),
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        accept.setColor(android.graphics.Color.YELLOW);
        Text action3 = new Text(0, 0,
                ResourceManager.getInstance().fontSmall, " / Action",
                new TextOptions(HorizontalAlign.CENTER),
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());

        accept.setX(action1.getX() - 0.5f * action3.getWidth());
        action3.setX(action1.getX() + 0.5f * accept.getWidth());
        accept.setY(action2.getY()- action3.getHeight() - 5f);
        action3.setY(accept.getY());
        attachChild(accept);
        attachChild(action3);

        // Action
        Text action4 = new Text(0, 0,
                ResourceManager.getInstance().fontSmall, "Action",
                new TextOptions(HorizontalAlign.CENTER),
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        action4.setX(action1.getX());
        action4.setY(action3.getY() - action4.getHeight() - 5f);
        attachChild(action4);

        // D-Pad / D-Pad
        Text dpad1 = new Text(0, 0,
                ResourceManager.getInstance().fontSmall, "D-Pad",
                new TextOptions(HorizontalAlign.CENTER),
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        dpad1.setColor(android.graphics.Color.YELLOW);
        Text dpad2 = new Text(0, 0,
                ResourceManager.getInstance().fontSmall, " / D-Pad",
                new TextOptions(HorizontalAlign.CENTER),
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        dpad1.setX(controllerSprite.getX() - 250 - 0.5f * dpad2.getWidth());
        dpad1.setY(controllerSprite.getY() - 270);
        dpad2.setX(controllerSprite.getX() - 250 + 0.5f * dpad1.getWidth());
        dpad2.setY(dpad1.getY());
        attachChild(dpad1);
        attachChild(dpad2);

        // Up / Down / Left / Right
        Text updownleftright = new Text(0, 0,
                ResourceManager.getInstance().fontSmall, "Up / Down / Left / Right",
                new TextOptions(HorizontalAlign.CENTER),
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        updownleftright.setX(controllerSprite.getX() - 250);
        updownleftright.setY(dpad1.getY() - updownleftright.getHeight());
        attachChild(updownleftright);

        // Skip sequence / Pause Game
        Text skip = new Text(0, 0,
                ResourceManager.getInstance().fontSmall, "Skip Sequence",
                new TextOptions(HorizontalAlign.CENTER),
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        skip.setColor(android.graphics.Color.YELLOW);
        Text pause = new Text(0, 0,
                ResourceManager.getInstance().fontSmall, " / Pause Game",
                new TextOptions(HorizontalAlign.CENTER),
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        skip.setX(controllerSprite.getX() + 650 - 0.5f * pause.getWidth());
        skip.setY(controllerSprite.getY() - 350);
        pause.setX(controllerSprite.getX() + 650 + 0.5f * skip.getWidth());
        pause.setY(skip.getY());
        attachChild(skip);
        attachChild(pause);        
    }

    @Override
    public void onHideScene() {}

    @Override
    public void onUnloadScene() {
        ResourceManager.getInstance().unloadControllerOptionResources();                
    }

    /**
     * Returns to the main options screen when menu button is pressed.
     */
    @Override
    public void onPressButtonMenu() {
        if (ResourceManager.getInstance().engine != null)
            SceneManager.getInstance().showScene(new TestingScene());
    }
}
