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

package com.madgear.ninjatrials.hud;

import org.andengine.entity.Entity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.FadeInModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.adt.align.HorizontalAlign;

import com.madgear.ninjatrials.managers.GameManager;
import com.madgear.ninjatrials.managers.ResourceManager;

public class AchievementNotify extends Entity {
    private static final int MOVE_TIME = 1;
    private static final int DELAY_TIME = 3;

    private Sprite achievContainerSprite;
    private Text achievContainerText;
    
    public AchievementNotify() {
        achievContainerSprite = new Sprite(0, 0,
                ResourceManager.getInstance().hudAchievementIngameContainer,
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        achievContainerSprite.setVisible(false);

        achievContainerText = new Text(0, 0,
                ResourceManager.getInstance().fontMedium,
                "achievement name placeholder text achievement name placeholder text",
                new TextOptions(HorizontalAlign.CENTER),
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        achievContainerText.setVisible(false);
        
        achievContainerSprite.attachChild(achievContainerText);
        attachChild(achievContainerSprite);
    }
    
    
    public void showAchievementCompleted(int achievNumber) {
        achievContainerText.setText(GameManager.player1achiev.achievements[achievNumber - 1].name);
        achievContainerSprite.setX(
                ResourceManager.getInstance().cameraWidth + achievContainerSprite.getWidth()/2);
        achievContainerSprite.setY(ResourceManager.getInstance().cameraHeight * 2/3);
        achievContainerSprite.setVisible(true);
        achievContainerText.setVisible(true);
        
        achievContainerSprite.registerEntityModifier(new SequenceEntityModifier(
                new MoveModifier(
                        MOVE_TIME,
                        achievContainerSprite.getX(),
                        achievContainerSprite.getY(),
                        achievContainerSprite.getX() - achievContainerSprite.getWidth()/2 - 20,
                        achievContainerSprite.getY()),
                        
                        new DelayModifier(DELAY_TIME),
                        
                        new MoveModifier(
                                MOVE_TIME,
                                achievContainerSprite.getX(),
                                achievContainerSprite.getY(),
                                achievContainerSprite.getX() + achievContainerSprite.getWidth()/2 + 20,
                                achievContainerSprite.getY())
                ));
        
        
    }
}
