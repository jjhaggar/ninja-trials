package com.madgear.ninjatrials.sequences;


// TODO Maybe this should be an Abstract class instead of an Interface :/
public interface Sequence {
	public void goToNextSubSequence();
	public boolean isInLastSubSequence();
	public void finishSequence();
	public void stopMusicAndSFX(); 
}
