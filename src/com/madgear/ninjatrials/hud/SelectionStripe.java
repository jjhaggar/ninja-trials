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
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.adt.align.HorizontalAlign;
import com.madgear.ninjatrials.ResourceManager;

/**
 * This class controls a stripe of text items displayed in the screen. The user can press right
 * and left and the item selected changes the same way.
 * @author Madgear Games
 *
 */
public class SelectionStripe extends Entity {
    private static final float SCALE_INIT = 1f;
    private static final float SCALE_FINAL = 1.4f;
    private static final float SCALE_TIME = 0.1f;   
    private final static float BORDER_SIZE = 150;
    private int selectedItem;
    private String[] items;
    private Text[] textItems;
    private float xPos;
    private float yPos;
    
    /**
     * Creates a stripe of text items.
     * The distance between the items is indicated in the constructor.
     * @param x X axis center of text items.
     * @param y Y axis center of text items.
     * @param itemsArray Array of string with the text elements.
     * @param itemSelectedIndex Initial item selected.
     * @param itemDistance Distance between two items.
     */
    public SelectionStripe(float x, float y, String[] itemsArray, int itemSelectedIndex,
            float itemDistance) {
        int numItems = itemsArray.length;
        textItems = new Text[numItems];
        float xItem;
        float yItem = y;
        for(int i = 0; i < numItems; i++) {
            xItem = x - ((numItems - 1) / 2) * itemDistance + (i* itemDistance);
            Text itemText = new Text(xItem, yItem,
                    ResourceManager.getInstance().fontMedium,
                    itemsArray[i],
                    new TextOptions(HorizontalAlign.CENTER),
                    ResourceManager.getInstance().engine.getVertexBufferObjectManager());
            attachChild(itemText);
            textItems[i] = itemText;
        }
        selectedItem = itemSelectedIndex;
        items = itemsArray;
        xPos = x;
        yPos = y;
        select(itemSelectedIndex);
    }

    /**
     * Creates a stripe of text items.
     * The elements are distributed along the whole screen width except for the border size.
     * @param x X axis center of text items.
     * @param y Y axis center of text items.
     * @param itemsArray Array of string with the text elements.
     * @param itemSelectedIndex Initial item selected.
     */
    public SelectionStripe(float x, float y, String[] itemsArray, int itemSelectedIndex) {
        int numItems = itemsArray.length;
        textItems = new Text[numItems];
        float xItem;
        float yItem = y;
        float s = (ResourceManager.getInstance().cameraWidth - BORDER_SIZE * 2)/ numItems;
        for(int i = 0; i < numItems; i++) {
            xItem = BORDER_SIZE + s / 2 + s * i;
            Text itemText = new Text(xItem, yItem,
                    ResourceManager.getInstance().fontMedium,
                    itemsArray[i],
                    new TextOptions(HorizontalAlign.CENTER),
                    ResourceManager.getInstance().engine.getVertexBufferObjectManager());
            attachChild(itemText);
            textItems[i] = itemText;
        }
        selectedItem = itemSelectedIndex;
        items = itemsArray;
        xPos = x;
        yPos = y;
        select(itemSelectedIndex);
    }
    
    /**
     * Move the selected item to the left one.
     */
    public void moveLeft() {
        if(selectedItem > 0) {
            deselect(selectedItem);
            selectedItem--;
            select(selectedItem);
        }
    }
    
    /**
     * Move the selected item to the right one.
     */
    public void moveRight() {
        if(selectedItem < items.length - 1) {
            deselect(selectedItem);
            selectedItem++;
            select(selectedItem);
        }   
    }
    
    /**
     * Select an item.
     * @param itemIndex The index of the item to be selected.
     */
    public void setItemSelected(int itemIndex) {
        deselect(selectedItem);
        selectedItem = itemIndex;
        select(selectedItem);
    }
    
    /**
     * Return the index of the current selected item.
     * @return The index of the current selected item.
     */
    public int getSelectedIndex() {
        return selectedItem;
    }
    
    /**
     * Return the content of the string of the selected item.
     * @return String of the selected item.
     */
    public String getSelectedString() {
        return items[selectedItem];
    }
    
    /**
     * Set the color and size of the text item to the initial values.
     * @param selectedItem The index of the deselected item.
     */
    private void deselect(int selectedItem) {
        textItems[selectedItem].registerEntityModifier(
                new ScaleModifier(SCALE_TIME, SCALE_FINAL, SCALE_INIT));
        textItems[selectedItem].setColor(android.graphics.Color.WHITE);

    }

    /**
     * Set the color and size of the text to the "selected" values.
     * @param selectedItem The index of the selected item.
     */
    private void select(int selectedItem) {
        textItems[selectedItem].clearEntityModifiers();
        textItems[selectedItem].registerEntityModifier(
                        new ScaleModifier(SCALE_TIME, SCALE_INIT, SCALE_FINAL));
        textItems[selectedItem].setColor(android.graphics.Color.YELLOW);
    }
}
