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

import android.util.Log;
import org.andengine.entity.Entity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;

import com.madgear.ninjatrials.managers.ResourceManager;


public class PowerBar extends Entity {
    private Sprite bar;
    private Rectangle rect;
    private int rectHeight = 200;
    private int rectWidth = 80;
    private int minPower;
    private int maxPower;

    public PowerBar(float posX, float posY, int minPower, int maxPower) {
        this.minPower = minPower;
        this.maxPower = maxPower;
		bar = new Sprite(posX, posY, ResourceManager.getInstance().hudPowerBarPushTR,
               ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        rect = new Rectangle(posX, posY, rectWidth, rectHeight,
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
		rect.setColor(0f, 0f, 0f);
		rect.setScaleCenterY(1);
		attachChild(bar);
		attachChild(rect);
	}

    public void update(int power) {
        float powerScale = power / (float)maxPower;
        assert powerScale >= 0f;
        assert powerScale <= 1f;
        rect.setScaleY(1f - powerScale);
    }
}
