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

import org.andengine.entity.Entity;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.adt.align.HorizontalAlign;

import com.madgear.ninjatrials.managers.ResourceManager;

public class TestGridItem extends Entity {

    private String itemName;
    private Text itemText;
    
    public TestGridItem(String name) {
        itemName = name;
        itemText = new Text(
                0, 0,
                ResourceManager.getInstance().fontSmall,
                name,
                new TextOptions(HorizontalAlign.CENTER),
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        itemText.setColor(android.graphics.Color.BLUE);        
        attachChild(itemText);
    }
    
    public void setText(Text t) {
        if(itemText == null) {
            itemText = t;
            attachChild(itemText);
        }
    }
    
    /**
     * Get the text of the item.
     * @return The item name.
     */
    public String getName() {
        return itemName;
    }
    
    /**
     * This method is called when the item is selected (changes color).
     */
    public void onSelected() {
        itemText.setColor(android.graphics.Color.WHITE);
    }

    public void onDeselected() {
        itemText.setColor(android.graphics.Color.BLUE);        
    }

    public void setTextPosition(float x, float y) {
        itemText.setX(x);
        itemText.setY(y);
        //itemText.setX(itemText.getX() + itemText.getWidth()/2);
    }

    /**
     * The item is selected and action button is pressed.
     * This method must be override in the item creation, when added to the TestGrid
     */
    public void onAction() {}
}
