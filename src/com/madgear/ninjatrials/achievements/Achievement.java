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

public class Achievement {
    public String name = "Achiev. Name";
    public String description = "Achiev. Description";
    public String successSentence = "Success!";
    
    public boolean isProgressive = false;
    public int progress = 0;
    public int progressTotal = 100;

    public boolean isSecret = false;
    public String clueTittle = "???";
    public String clueDescription = "???";
    
    public boolean completed = false;

    
    // CONSTRUCTORS ---------------------------
    
    /**
     * Standard achievement.
     * @param cName
     * @param cDescription
     * @param cSuccessSentence
     */
    public Achievement(String cName, String cDescription, String cSuccessSentence) {
        name = cName;
        description = cDescription;
        successSentence = cSuccessSentence;
    }
    
    /**
     * Progressive achiev.
     * @param cName
     * @param cDescription
     * @param cSuccessSentence
     * @param cProgress
     * @param cProgressTotal
     */
    public Achievement(
            String cName, String cDescription, String cSuccessSentence,
            int cProgress, int cProgressTotal) {
        name = cName;
        description = cDescription;
        successSentence = cSuccessSentence;
        isProgressive = true;
        progress = cProgress;
        progressTotal = cProgressTotal;
    }
    
    /**
     * Secret achiev.
     * @param cName
     * @param cDescription
     * @param cSuccessSentence
     * @param cClueTittle
     * @param cClueDescription
     */
    public Achievement(
            String cName, String cDescription, String cSuccessSentence,
            String cClueTittle, String cClueDescription) {
        name = cName;
        description = cDescription;
        successSentence = cSuccessSentence;
        isSecret = true;
        clueTittle = cClueTittle;
        clueDescription = cClueDescription;
    }
    
    /**
     * Progresive & secret achiev.
     * @param cName
     * @param cDescription
     * @param cSuccessSentence
     * @param cProgress
     * @param cProgressTotal
     * @param cClueTittle
     * @param cClueDescription
     */
    public Achievement(
            String cName, String cDescription, String cSuccessSentence,
            int cProgress, int cProgressTotal,
            String cClueTittle, String cClueDescription) {
        name = cName;
        description = cDescription;
        successSentence = cSuccessSentence;
        isProgressive = true;
        progress = cProgress;
        progressTotal = cProgressTotal;
        isSecret = true;
        clueTittle = cClueTittle;
        clueDescription = cClueDescription;
    }
    
    
    // METHODS -----------------
    
    /**
     * Increments the progress of an achiev.
     * @param i amount to increment.
     */
    public void progressIncrement(int i) {
        if(isProgressive) {
            progress = progress + i;
            if (progress >= progressTotal) {
                progress = progressTotal;
                completed = true;
            }
        }
    }
    
    
    /**
     * Show a clue if is secret achiev and not completed, else show the name.
     * @return the name.
     */
    public String showName() {
        if(isSecret && !completed) return clueTittle;
        else return name;
    }
    
    
    /**
     * Show a clue if is secret achiev and not completed, else show the description.
     * @return the description.
     */
    public String showDescription() {
        if(isSecret && !completed) return clueDescription;
        else return description;
    }
}
