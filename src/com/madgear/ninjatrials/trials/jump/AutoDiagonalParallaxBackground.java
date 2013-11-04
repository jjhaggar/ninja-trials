package com.madgear.ninjatrials.trials.jump;

public class AutoDiagonalParallaxBackground extends ParallaxBackground2d {
    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    private final float mParallaxChangePerSecond;

    // ===========================================================
    // Constructors
    // ===========================================================

    public AutoDiagonalParallaxBackground (final float pRed, final float pGreen, final float pBlue, final float pParallaxChangePerSecond) {
            super(pRed, pGreen, pBlue);
            this.mParallaxChangePerSecond = pParallaxChangePerSecond;
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    @Override
    public void onUpdate(final float pSecondsElapsed) {
            super.onUpdate(pSecondsElapsed);

            this.mParallaxValueY += this.mParallaxChangePerSecond * pSecondsElapsed;
            this.mParallaxValueX += this.mParallaxChangePerSecond * pSecondsElapsed;
    }

    // ===========================================================
    // Methods
    // ===========================================================

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
