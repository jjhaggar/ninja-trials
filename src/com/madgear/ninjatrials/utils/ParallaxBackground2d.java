
// Source: http://www.andengine.org/forums/features/parallax-x-y-scrolling-class-t808-10.html#p38092

// Problems found after copying the class:
// 1) After copying the class content, I had to replace "IAreaShape" by "IShape", they probably have been renamed in AndEngine
// 2) There seem to be two deprecated methods: .getWidthScaled() y .getHeightScaled()
// 3) There are three lines with error -> Suspicious method call; should probably call "draw" rather than "onDraw"
//    I solved it by adding @SuppressLint("WrongCall") at the beginning of ParallaxLayer



package com.madgear.ninjatrials.utils;


import android.annotation.SuppressLint;
import java.util.ArrayList;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.shape.IShape;
import org.andengine.opengl.util.GLState;


/**
 * @author Oldskool73
 * 
 * Parallax background that scrolls in both X and/or Y directions.
 * 
 * Usage:
 * 
 * ...x & y free scrolling tiled background...
 * mParallaxBackground.addParallaxEntity(new ParallaxBackground2d.ParallaxBackground2dEntity(-0.2f,-0.2f, new Sprite(0, 0, this.mParallaxLayerStars)));
 * JJ Fix: ------> .attachParallaxEntity(new ParallaxBackground2d.ParallaxBackground2dEntity(-0.2f,-0.2f, new Sprite(0, 0, this.mParallaxLayerStars)));
 * 
 * ...side scroller repeating strip...
 * mParallaxBackground.addParallaxEntity(new ParallaxBackground2d.ParallaxBackground2dEntity(-0.4f, 0.0f, new Sprite(0, 100, this.mParallaxLayerHills),true,false));
 *
 * ...vertical scroller repeating strip...
 * mParallaxBackground.addParallaxEntity(new ParallaxBackground2d.ParallaxBackground2dEntity(-0.0f,-0.4f, new Sprite(100, 0, this.mParallaxLayerHills),false,true));
 *
 * ...non repeating positioned item...
 * mParallaxBackground.addParallaxEntity(new ParallaxBackground2d.ParallaxBackground2dEntity(-0.4f,-0.4f, new Sprite(100, 100, this.mParallaxLayerSun),false,false,true));
 * 
 * 
 */
@SuppressLint("WrongCall")
public class ParallaxBackground2d extends Background {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private final ArrayList<ParallaxBackground2dEntity> mParallaxEntities = new ArrayList<ParallaxBackground2dEntity>();
	private int mParallaxEntityCount;

	protected float mParallaxValueX;
	protected float mParallaxValueY;

	// ===========================================================
	// Constructors
	// ===========================================================

	public ParallaxBackground2d(float pRed, float pGreen, float pBlue) {
		super(pRed, pGreen, pBlue);
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public float getParallaxValueX() {
		return this.mParallaxValueX;
	}
	public float getParallaxValueY() {
		return this.mParallaxValueY;
	}
	
	public void setParallaxValue(final float pParallaxValueX, final float pParallaxValueY) {
		this.mParallaxValueX = pParallaxValueX;
		this.mParallaxValueY = pParallaxValueY;
	}
	
	public void offsetParallaxValue(final float pParallaxValueX, final float pParallaxValueY) {
		this.mParallaxValueX += pParallaxValueX;
		this.mParallaxValueY += pParallaxValueY;
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public void onDraw(final GLState pGLState, final Camera pCamera) {
		super.onDraw(pGLState, pCamera);

		final float parallaxValueX = this.mParallaxValueX;
		final float parallaxValueY = this.mParallaxValueY;
		final ArrayList<ParallaxBackground2dEntity> parallaxEntities = this.mParallaxEntities;

		for(int i = 0; i < this.mParallaxEntityCount; i++) {
			parallaxEntities.get(i).onDraw(pGLState, parallaxValueX, parallaxValueY, pCamera);
		}
	}
	
	// ===========================================================
	// Methods
	// ===========================================================

	
	public void attachParallaxEntity(final ParallaxBackground2dEntity pParallaxEntity) {
		this.mParallaxEntities.add(pParallaxEntity);
		this.mParallaxEntityCount++;
	}

	public boolean detachParallaxEntity(final ParallaxBackground2dEntity pParallaxEntity) {
		this.mParallaxEntityCount--;
		final boolean success = this.mParallaxEntities.remove(pParallaxEntity);
		if(!success) {
			this.mParallaxEntityCount++;
		}
		return success;
	}
	
	
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

	public static class ParallaxBackground2dEntity {
		// ===========================================================
		// Constants
		// ===========================================================

		// ===========================================================
		// Fields
		// ===========================================================

		final float mParallaxFactorX;
		final float mParallaxFactorY;
		private Boolean mRepeatX; //final Boolean mRepeatX; // chapucillas JJ
		private Boolean mRepeatY; //final Boolean mRepeatY; // chapucillas JJ		
		final IShape mShape;
		final Boolean mShouldCull;

		// ===========================================================
		// Constructors
		// ===========================================================

		// add a repeating x & y texture fill
		public ParallaxBackground2dEntity(final float pParallaxFactorX, final float pParallaxFactorY, final IShape pShape) {
			this.mParallaxFactorX = pParallaxFactorX;
			this.mParallaxFactorY = pParallaxFactorY;
			this.mRepeatX = true;
			this.mRepeatY = true;
			this.mShouldCull = false;
			this.mShape = pShape;
		}

		// add an x or y only repeating strip
		public ParallaxBackground2dEntity(final float pParallaxFactorX, final float pParallaxFactorY, final IShape pShape, final Boolean pRepeatX, final Boolean pRepeatY) {
			this.mParallaxFactorX = pParallaxFactorX;
			this.mParallaxFactorY = pParallaxFactorY;
			this.mRepeatX = pRepeatX;
			this.mRepeatY = pRepeatY;
			this.mShouldCull = false;
			this.mShape = pShape;
		}			
		
		// add an x or y only repeating strip or non repeating feature that may be culled when off screen
		public ParallaxBackground2dEntity(final float pParallaxFactorX, final float pParallaxFactorY, final IShape pShape, final Boolean pRepeatX, final Boolean pRepeatY, final Boolean pShouldCull) {
			this.mParallaxFactorX = pParallaxFactorX;
			this.mParallaxFactorY = pParallaxFactorY;
			this.mRepeatX = pRepeatX;
			this.mRepeatY = pRepeatY;
			this.mShouldCull = (pRepeatX && pRepeatY)? false : pShouldCull;
			this.mShape = pShape;
		}

		
		// ===========================================================
		// Getters & Setters
		// ===========================================================
		
		// Chapucillas para conocer y cambiar la repetici�n o no repetici�n de la textura al vuelo 
		public Boolean getmRepeatX() { return mRepeatX; }
		public void setmRepeatX(Boolean mRepeatX) { this.mRepeatX = mRepeatX; }
		public Boolean tooglemRepeatX() { this.mRepeatX = !this.mRepeatX; return this.mRepeatX; }

		public Boolean getmRepeatY() { return mRepeatY; }
		public void setmRepeatY(Boolean mRepeatY) { this.mRepeatY = mRepeatY; }
		public Boolean tooglemRepeatY() { this.mRepeatY = !this.mRepeatY; return this.mRepeatY; }
		
		// ===========================================================
		// Methods for/from SuperClass/Interfaces
		// ===========================================================

		// ===========================================================
		// Methods
		// ===========================================================

		public void onDraw(final GLState pGLState, final float pParallaxValueX, final float pParallaxValueY, final Camera pCamera) {
			pGLState.pushModelViewGLMatrix();
			{
				final float cameraWidth = pCamera.getWidth();
				final float cameraHeight = pCamera.getHeight();
				final float shapeWidthScaled = this.mShape.getWidth(); // getWidthScaled();
				final float shapeHeightScaled = this.mShape.getHeight();// getHeightScaled();

				//reposition
				float baseOffsetX = (pParallaxValueX * this.mParallaxFactorX);
				if (this.mRepeatX) {
					baseOffsetX = baseOffsetX % shapeWidthScaled;
					while(baseOffsetX > 0) {
						baseOffsetX -= shapeWidthScaled;
					}
				}			
				float baseOffsetY = (pParallaxValueY * this.mParallaxFactorY);
				if (this.mRepeatY) {
					baseOffsetY = baseOffsetY % shapeHeightScaled;
					while(baseOffsetY > 0) {
						baseOffsetY -= shapeHeightScaled;
					}				
				}
				
				//optionally screen cull non repeating items
				Boolean culled = false;
				if (mShouldCull) {
					if (!this.mRepeatX) {
						if ((baseOffsetY + (shapeHeightScaled*2) < 0) || (baseOffsetY > cameraHeight)) {
							culled = true;
						}
					}	
					if (!this.mRepeatY) {
						if ((baseOffsetX + (shapeWidthScaled*2) < 0) || (baseOffsetX > cameraWidth)) {
							culled = true;
						}
					}
				}
				
				if (!culled) {
					//draw
					pGLState.translateModelViewGLMatrixf(baseOffsetX, baseOffsetY, 0);
					float currentMaxX = baseOffsetX;
					float currentMaxY = baseOffsetY;
					do {														//rows
						this.mShape.onDraw(pGLState, pCamera);
						if (this.mRepeatY) {
							currentMaxY = baseOffsetY;							
							do {												//columns
								pGLState.translateModelViewGLMatrixf(0, shapeHeightScaled, 0);
								currentMaxY += shapeHeightScaled;						
								this.mShape.onDraw(pGLState, pCamera);
							} while(currentMaxY < cameraHeight);				//end columns
							pGLState.translateModelViewGLMatrixf(0, -currentMaxY + baseOffsetY, 0);					
						} 
						pGLState.translateModelViewGLMatrixf(shapeWidthScaled, 0, 0);
						currentMaxX += shapeWidthScaled;
					} while (this.mRepeatX && currentMaxX < cameraWidth);		//end rows
				}
			}
			pGLState.popModelViewGLMatrix();
		}

		// ===========================================================
		// Inner and Anonymous Classes
		// ===========================================================
	}
}




