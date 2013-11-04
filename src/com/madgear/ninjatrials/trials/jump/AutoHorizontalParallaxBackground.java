package com.madgear.ninjatrials.trials.jump;

/**
 * @author oldskool73
 */
public class AutoHorizontalParallaxBackground extends ParallaxBackground2d {
 
        private final float mParallaxChangePerSecond;
 
        // ===========================================================
        // Constructors
        // ===========================================================
 
        public AutoHorizontalParallaxBackground (final float pRed, final float pGreen, final float pBlue, final float pParallaxChangePerSecond) {
                super(pRed, pGreen, pBlue);
                this.mParallaxChangePerSecond = pParallaxChangePerSecond;
        }
 
        // ===========================================================
        // Methods for/from SuperClass/Interfaces
        // ===========================================================
 
        @Override
        public void onUpdate(final float pSecondsElapsed) {
                super.onUpdate(pSecondsElapsed);
 
                this.mParallaxValueX += this.mParallaxChangePerSecond * pSecondsElapsed;
        }
 
 }
