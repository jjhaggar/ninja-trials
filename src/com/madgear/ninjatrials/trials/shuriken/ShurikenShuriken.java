package com.madgear.ninjatrials.trials.shuriken;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;

import android.util.Log;

import com.madgear.ninjatrials.managers.ResourceManager;

public class ShurikenShuriken extends Entity{
	
	private final float SCRNWIDTH = ResourceManager.getInstance().cameraWidth;
	private final float SCRENHEIGHT = ResourceManager.getInstance().cameraHeight;	
	private Sprite[] shurikenSprites = new Sprite[6];
	int shurikenAnimationCounter;
	float shurikenLaunchTime;
	IUpdateHandler shurikenUpdateHandler;

	public ShurikenShuriken(){
		ITextureRegion[] shurikenShurikens = ResourceManager.getInstance().shurikenShurikens;			
		for (int i = 0; i < 6; i++) {
			shurikenSprites[i] = new Sprite(SCRNWIDTH/2, SCRENHEIGHT-300-75*i, shurikenShurikens[i], ResourceManager.getInstance().engine.getVertexBufferObjectManager());
		}
		for (Sprite sprite: shurikenSprites){
	    	sprite.setAlpha(0f);
	    	attachChild(sprite);
	    }
	}
	
	public void launch(float fromX) {
		for (Sprite shuriken: shurikenSprites) {
			shuriken.setX(fromX);
		}
		shurikenAnimationCounter = 0;
		shurikenLaunchTime = ResourceManager.getInstance().engine.getSecondsElapsedTotal();
		shurikenUpdateHandler = new IUpdateHandler() {
            @Override
            public void onUpdate(float pSecondsElapsed) {
            	float period = .12f;
                if(ResourceManager.getInstance().engine.getSecondsElapsedTotal() >
                shurikenLaunchTime + period*shurikenAnimationCounter) {
                	if (shurikenAnimationCounter == 0) {
                		shurikenSprites[5-shurikenAnimationCounter].setAlpha(1f);
                	}
                	else if (shurikenAnimationCounter == 6) {
                		shurikenSprites[0].setAlpha(0f);
                		ShurikenShuriken.this.unregisterUpdateHandler(shurikenUpdateHandler);
                	}
                	else {
                		shurikenSprites[5-shurikenAnimationCounter+1].setAlpha(0f);
                		shurikenSprites[5-shurikenAnimationCounter].setAlpha(1f);
                	}
                	shurikenAnimationCounter++;
                }	                
            }
            @Override public void reset() {}
        };
        registerUpdateHandler(shurikenUpdateHandler);
	}
}
