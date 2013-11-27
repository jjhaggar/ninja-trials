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

package com.madgear.ninjatrials.records;

import java.io.Serializable;
import java.util.Date;

import com.madgear.ninjatrials.managers.GameManager;

public class Record implements Serializable {
    
    // record version:
    private static final long serialVersionUID = 1L;
    
    public int profileId = 0;
    public String profileName = "MADGEAR GAMES";
    public int characterId = GameManager.CHAR_SHO;
    public int score = 0;
    public Date date = new Date();
    
    
    // Empty Constructor for default tables:
    public Record() {}
    
    public Record(int profile, String name, int character, int pScore) {
        profileId = profile;
        profileName = name;
        characterId = character;
        score = pScore;
        date = new Date();
    }
}
