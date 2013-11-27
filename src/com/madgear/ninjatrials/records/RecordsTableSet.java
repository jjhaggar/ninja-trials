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

public class RecordsTableSet implements Serializable {

    // Serial version:
    private static final long serialVersionUID = 1L;

    // Number of records in a table:
    public static final int SIZE = 12;
    
    public RecordsTable todayRecords;
    public RecordsTable monthRecords;
    public RecordsTable allTimeRecords;    
    
    // Const:
    public RecordsTableSet() {
        todayRecords = new RecordsTable(SIZE);
        monthRecords = new RecordsTable(SIZE);
        allTimeRecords = new RecordsTable(SIZE);
    }
    
    /**
     * Insert a record in a table in his proper order.
     * @param record
     */
    public void insert(Record record) {
        todayRecords.insert(record);
        monthRecords.insert(record);
        allTimeRecords.insert(record);
    }
    
    
    /**
     * Checks if the today or month table must be erased.
     */
    @SuppressWarnings("deprecation")
    public void update() {
        Date currentDate = new Date();
        
        if(currentDate.getDay() > todayRecords.updateDate.getDay()) {
            todayRecords = new RecordsTable(SIZE);
        }
        if(currentDate.getMonth() > monthRecords.updateDate.getMonth()) {
            monthRecords = new RecordsTable(SIZE);
        }
    }
}
