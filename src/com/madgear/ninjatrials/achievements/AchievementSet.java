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

public class AchievementSet {
    public Achievement[] achievements;
    
    /**
     * Constructs an achievement set.
     * @param achievementNum The total number of achievements.
     */
    public AchievementSet(int achievementNum) {
        achievements = new Achievement[achievementNum];
    }
    
    /**
     * 
     * @return The total number of achievements.
     */
    public int getAchievementNum() {
        return achievements.length;
    }
    
    /**
     * Computes the total number of achiements completed.
     * @return The number of completed achiements.
     */
    public int getCompletedAchievements() {
        int completedAchievements = 0;
        for(int i = 0; i < achievements.length; i++)
            if (achievements[i].completed)
                completedAchievements++;
        return completedAchievements;
    }
}
