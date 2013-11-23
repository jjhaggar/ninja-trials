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


public interface IUserInput {
    public void onPressButtonO();
    public void onPressButtonU();
    public void onPressButtonY();
    public void onPressButtonA();
    public void onPressButtonMenu();
    public void onPressDpadUp();
    public void onPressDpadDown();
    public void onPressDpadLeft();
    public void onPressDpadRight();
    public void onReleaseButtonO();
    public void onReleaseButtonU();
    public void onReleaseButtonY();
    public void onReleaseButtonA();
    public void onReleaseButtonMenu();
    public void onReleaseDpadUp();
    public void onReleaseDpadDown();
    public void onReleaseDpadLeft();
    public void onReleaseDpadRight();
}
