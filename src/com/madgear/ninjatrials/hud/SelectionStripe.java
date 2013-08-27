package com.madgear.ninjatrials.hud;

import org.andengine.entity.Entity;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.adt.align.HorizontalAlign;

import com.madgear.ninjatrials.ResourceManager;


public class SelectionStripe extends Entity {
    private static final float SCALE_INIT = 1f;
    private static final float SCALE_FINAL = 1.6f;
    private static final float SCALE_TIME = 0.1f;   
    private final static float BORDER_SIZE = 200;
    private int selectedItem;
    private String[] items;
    private Text[] textItems;
    private float xPos;
    private float yPos;
    
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
    }

    private void select(int selectedItem) {
        textItems[selectedItem].clearEntityModifiers();
        textItems[selectedItem].registerEntityModifier(
                        new ScaleModifier(SCALE_TIME, SCALE_INIT, SCALE_FINAL));
    }

}
