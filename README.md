<div align="center">
    ![BreadTunes Logo](Images/breadtunes.png)
</div>
<div align="center">
    <h1>BreadTunes</h1>
    <h3>A simple, family-friendly music player for Android.</h3>
</div>
<br/>


# Group 15 Final Project - Winter 2019

## Introduction

**This application was built for COMP3350 (Software Engineering 1) at the University of Manitoba for the Winter 2019 term**. The point of this project was to create an Android application with good coding practices, a 3-layer architecture, and various levels of testing.

Our group ended up choosing to create a music player for Android devices. The player we built, **BreadTunes**, allows a user to play songs from their device. We also built in the ability to flag songs as inappropriate, so that specific songs can be hidden from the music player. This feature was targeted towards parents, as they can let their children use their phone for music without fear of them listening to explicit or inappropriate songs they don't want their children to listen to.

The initial vision for BreadTunes can be seen in the [vision](Documents/VISION.md) document.

## Design

BreadTunes was designed with a 3 layer architecture. Our application follows the typical MVC architecture, but we used the names "Presentation, Logic, and Persistence" for the layers throughout the school term. We also have another static _Services_ layer that supplies application-wide singletons.

The general architecture for BreadTunes can be seen in the [architecture](Documents/Architecture.pdf) document

## Running the Code

BreadTunes was built using Android Studio. Running the code is as simple as cloning the repository, and opening it as a project in Android Studio and running it. The steps are as follows:

1. Clone this project on the command line, into a folder that you want the project.

   `git clone https://code.cs.umanitoba.ca/comp3350-winter2019/group-15-bread-tunes.git`

2. Launch Android Studio, and open the cloned project by clicking File -> Open, and navigating to the project in the opened file explorer.

3. Run the BreadTunes application on an emulator or physical Android device with the Run menu dropdown within Android Studio.

## Running the Tests

