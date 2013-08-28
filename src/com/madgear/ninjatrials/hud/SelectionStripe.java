package com.madgear.ninjatrials.hud;

import org.andengine.entity.Entity;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;

import com.madgear.ninjatrials.ResourceManager;


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
    
    public void moveLeft() {
        if(selectedItem > 0) {
            unselect(selectedItem);
            selectedItem--;
            select(selectedItem);
        }
    }
    
    public void moveRight() {
        if(selectedItem < items.length - 1) {
            unselect(selectedItem);
            selectedItem++;
            select(selectedItem);
        }   
    }
    
    public void setItemSelected(int itemIndex) {
        unselect(selectedItem);
        selectedItem = itemIndex;
        select(selectedItem);
    }
    
    public int getSelectedIndex() {
        return selectedItem;
    }
    
    public String getSelectedString() {
        return items[selectedItem];
    }
    
    private void unselect(int selectedItem) {
        textItems[selectedItem].registerEntityModifier(
                new ScaleModifier(SCALE_TIME, SCALE_FINAL, SCALE_INIT));
        textItems[selectedItem].setColor(android.graphics.Color.WHITE);

    }

    private void select(int selectedItem) {
        textItems[selectedItem].clearEntityModifiers();
        textItems[selectedItem].registerEntityModifier(
                        new ScaleModifier(SCALE_TIME, SCALE_INIT, SCALE_FINAL));
        textItems[selectedItem].setColor(android.graphics.Color.YELLOW);
    }

}
