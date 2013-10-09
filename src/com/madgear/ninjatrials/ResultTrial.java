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

package com.madgear.ninjatrials;

/**
 * This class is used by GameManager to keeps track of all trial results for a player in the
 * current game.
 * The class is used by ResultWinScene class to calculate the trial score, and add it to the total
 * score.
 * 
 * @author Madgear Games
 */
public class ResultTrial {

    // RUN
    public float runTime;
    public int runMaxSpeedCombo;
    public int runMaxSpeedComboTotal;
    public int runMaxSpeed;

    // CUT
    public int cutRound;
    public int cutConcentration;

    // JUMP
    public float jumpTime;
    public int jumpPerfectJumpCombo;
    public int jumpMaxPerfectJumpCombo;
    
    // SHURIKEN
    public float shurikenTime;
    public int shurikenPrecission;
}
