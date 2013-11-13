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

package com.madgear.ninjatrials.achievements;

import com.madgear.ninjatrials.managers.GameManager;

public class AchievementSetNinjaTrial extends AchievementSet {

    public static final int ACHIEV_NUM = 35;
    
    
    public AchievementSetNinjaTrial() {
        super(ACHIEV_NUM);
        if(GameManager.DEBUG_MODE)
            initTest();
        else
            init();
    }
    
    
    /**
     * Initializes the Ninja Trials achiev. set.
     */
    public void init() {
        // TODO: put real initial values here!
        //...........
        
        initTest();
    }
    
    
    /**
     * Initializes the Ninja Trials achiev. testing set.
     */
    public void initTest() {
        for(int i = 0; i < ACHIEV_NUM; i++) {
            achievements[i] = new Achievement("Nombre"+i, "Descrp"+i, "Exito"+i);
        }
        
        // put initial test values here!
        
        // basic:
        achievements[0] = new Achievement("Basic", "Descripion basic", "Success basic!!!");
        
        // basic success:
        achievements[1] = new Achievement("Basic", "Descripion basic", "Success basic!!!");
        achievements[1].completed = true;
        
        // Progressive:
        achievements[2] = new Achievement("Progressive", "Description progressive",
                "Success progresive!!!", 0, 100);
        
        // Progressive success:
        achievements[3] = new Achievement("Progressive", "Description progressive",
                "Success progresive!!!", 0, 100);
        achievements[3].completed = true;
        
        // Secret:
        achievements[4] = new Achievement("Secret", "Description Secret", "Sucess secret!!!",
                "Clue name", "Clue description");
        
        // Secret success:
        achievements[5] = new Achievement("Secret", "Description Secret", "Sucess secret!!!",
                "Clue name", "Clue description");
        achievements[5].completed = true;
        
        // Progressive & Secret:
        achievements[6] = new Achievement("Prog and Sec", "Description progress and secret",
                "Sucess prog and secret!!!", 0, 100,
                "Clue name prog and sec", "Clue description prog and sec");
        
        // Progressive & Secret sucess:
        achievements[7] = new Achievement("Prog and Sec", "Description progress and secret",
                "Sucess prog and secret!!!", 0, 100,
                "Clue name prog and sec", "Clue description prog and sec");
        achievements[7].completed = true;
        
        
        //.........
    }
}
