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

import java.io.Serializable;

import com.madgear.ninjatrials.R;
import com.madgear.ninjatrials.managers.GameManager;
import com.madgear.ninjatrials.managers.ResourceManager;

public class AchievementSetNinjaTrial extends AchievementSet implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int ACHIEV_NUM = 35;

    // ACHIEV. PROGRESSION VALUES:
    private static final int ACHIEV_01_TOTAL_KMS = 200;
    private static final int ACHIEV_01_TOTAL_KMS_TEST = 5;
    private static final int ACHIEV_02_TOTAL_STRAWMEN = 500;
    private static final int ACHIEV_02_TOTAL_STRAWMEN_TEST = 10;
    private static final int ACHIEV_03_TOTAL_PERFECT_JUMPS = 100;
    private static final int ACHIEV_03_TOTAL_PERFECT_JUMPS_TEST = 10;
    private static final int ACHIEV_05_TOTAL_HOURS = 100;
    private static final int ACHIEV_05_TOTAL_HOURS_TEST = 1;

    // ACHIEV. DATA:
    public static final int ACHIEV_04_CUT_PRECISSION = 100;
    public static final int ACHIEV_04_CUT_PRECISSION_TEST = 80;

    
    public AchievementSetNinjaTrial() {
        super(ACHIEV_NUM);
        init();
    }
    
    
    /**
     * Initializes the Ninja Trials achiev. set.
     */
    public void init() {
        // Default setup
        for(int i = 0; i < ACHIEV_NUM; i++) {
            achievements[i] = new Achievement("Nombre"+(i+1), "Descrp"+(i+1), "Exito"+(i+1));
        }
        
        // Achiev 1: Run a total of 200 Km
        achievements[0] = new Achievement(
                ResourceManager.getInstance().loadAndroidRes().getString(R.string.achievement_01_name),
                ResourceManager.getInstance().loadAndroidRes().getString(R.string.achievement_01_desc),
                ResourceManager.getInstance().loadAndroidRes().getString(R.string.achievement_01_success),
                0, GameManager.ACHIEV_DEBUG_MODE ? ACHIEV_01_TOTAL_KMS_TEST : ACHIEV_01_TOTAL_KMS);
        
        // Achiev 2: Destroy 500 StrawMen
        achievements[1] = new Achievement(
                ResourceManager.getInstance().loadAndroidRes().getString(R.string.achievement_02_name),
                ResourceManager.getInstance().loadAndroidRes().getString(R.string.achievement_02_desc),
                ResourceManager.getInstance().loadAndroidRes().getString(R.string.achievement_02_success),
                0, GameManager.ACHIEV_DEBUG_MODE ? ACHIEV_02_TOTAL_STRAWMEN_TEST : ACHIEV_02_TOTAL_STRAWMEN);
        
        // Achiev 3: Perform 100 perfect jumps
        achievements[2] = new Achievement(
                ResourceManager.getInstance().loadAndroidRes().getString(R.string.achievement_03_name),
                ResourceManager.getInstance().loadAndroidRes().getString(R.string.achievement_03_desc),
                ResourceManager.getInstance().loadAndroidRes().getString(R.string.achievement_03_success),
                0, GameManager.ACHIEV_DEBUG_MODE ? ACHIEV_03_TOTAL_PERFECT_JUMPS_TEST : ACHIEV_03_TOTAL_PERFECT_JUMPS);
        
        // Achiev 4: Achieve a single cut with 100% precision.
        achievements[3] = new Achievement(
                ResourceManager.getInstance().loadAndroidRes().getString(R.string.achievement_04_name),
                ResourceManager.getInstance().loadAndroidRes().getString(R.string.achievement_04_desc),
                ResourceManager.getInstance().loadAndroidRes().getString(R.string.achievement_04_success));
        
        // Achiev 5: Play the game for at least 100 hours.
        achievements[4] = new Achievement(
                ResourceManager.getInstance().loadAndroidRes().getString(R.string.achievement_05_name),
                ResourceManager.getInstance().loadAndroidRes().getString(R.string.achievement_05_desc),
                ResourceManager.getInstance().loadAndroidRes().getString(R.string.achievement_05_success),
                0, GameManager.ACHIEV_DEBUG_MODE ? ACHIEV_05_TOTAL_HOURS_TEST : ACHIEV_05_TOTAL_HOURS,
                ResourceManager.getInstance().loadAndroidRes().getString(R.string.achievement_05_clue_tittle),
                ResourceManager.getInstance().loadAndroidRes().getString(R.string.achievement_05_clue_desc));
        
        // Achiev 6: Get all default records replaced by players records.
        achievements[5] = new Achievement(
                ResourceManager.getInstance().loadAndroidRes().getString(R.string.achievement_06_name),
                ResourceManager.getInstance().loadAndroidRes().getString(R.string.achievement_06_desc),
                ResourceManager.getInstance().loadAndroidRes().getString(R.string.achievement_06_success),
                ResourceManager.getInstance().loadAndroidRes().getString(R.string.achievement_06_clue_tittle),
                ResourceManager.getInstance().loadAndroidRes().getString(R.string.achievement_06_clue_desc));    

        
        /*
        // EXAMPLES:
        
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

*/
    }
}
