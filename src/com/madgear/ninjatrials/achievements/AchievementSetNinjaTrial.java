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

public class AchievementSetNinjaTrial extends AchievementSet {

    public static final int ACHIEV_NUM = 35;
    
    
    public AchievementSetNinjaTrial() {
        super(ACHIEV_NUM);
        init();
    }

    
    /**
     * Initializes the Ninja Trials achiev. set.
     */
    public void init() {
        for(int i = 0; i < ACHIEV_NUM; i++) {
            achievements[i] = new Achievement("Nombre"+i, "Descrp"+i, "Exito"+i);
        }
        
        // TODO: put initial values here!
        achievements[2] = new Achievement("Correr Mucho", "Descrip Corre mucho", "Exito!!!", 0, 100);
        //.........
    }
}
