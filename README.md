<div align="center">
    <img alt="BreadTunes Logo" src="Images/breadtunes.png" />
</div>
<div align="center">
    <h1>BreadTunes</h1>
    <h3><i>A simple, family-friendly music player for Android.</i></h3>
</div>

<br/>


# Group 15 Final Project - Winter 2019

## Introduction

**This application was built for COMP3350 (Software Engineering 1) at the University of Manitoba for the Winter 2019 term**. The point of this project was to create an Android application with good coding practices, a 3-layer architecture, and various levels of testing. We also created a static website with Jekyll showcasing the project's features.

Our group ended up choosing to create a music player for Android devices. The player we built, **BreadTunes**, allows a user to play songs from their device. We also built in the ability to flag songs as inappropriate, so that specific songs can be hidden from the music player. This feature was targeted towards parents, as they can let their children use their phone for music without fear of them listening to explicit or inappropriate songs they don't want their children to listen to.

The initial vision for BreadTunes can be seen in the [vision](Documents/VISION.md) document.

A document analyzing this group's work with the project velocity can be found in the [retrospective](Documents/RETROSPECTIVE.md) document.

## Design

BreadTunes was designed with a 3 layer architecture. Our application follows the typical MVC architecture, but we used the names "Presentation, Logic, and Persistence" for the layers throughout the school term. We also have another static _Services_ layer that supplies application-wide singletons.

The general architecture for BreadTunes can be seen in the [architecture](Documents/Architecture.pdf) document

## Running the Application

BreadTunes was built using Android Studio. Running the code is as simple as cloning the repository, and opening it as a project in Android Studio and running it. The steps are as follows:

1. Clone this project on the command line, into a folder that you want the project.

   `git clone https://code.cs.umanitoba.ca/comp3350-winter2019/group-15-bread-tunes.git`

2. Launch Android Studio, and open the cloned project by clicking File -> Open, and navigating to the project in the opened file explorer.

3. Run the BreadTunes application on an emulator or physical Android device with the Run menu dropdown within Android Studio.

## Running the Tests

BreadTunes has 3 different types of tests: Unit, Integration, and System tests. To run any of the tests, first make sure the project is open in Android Studio. The Unit and Integration tests are written with [JUnit 4](https://junit.org/junit4/) and [Mockito](https://site.mockito.org/). The system tests are written with [Espresso](https://developer.android.com/training/testing/espresso).

#### Unit Tests

To run the unit tests, right click the file `AllTests.java` in the folder `src/test/java/comp3350/breadtunes/tests`. Then, click "Run AllTests".

#### Integration Tests

To run the integration tests, right click the file `IntegrationTests.java` in the folder `src/test/java/comp3350/breadtunes/tests`. Then, click "Run IntegrationTests".

#### System Tests

To run the system tests, right click the file `SystemTests.java` in the folder `src/androidTest/java/comp3350/breadtunes`. Then, click "Run SystemTests".

# Running the Jekyll Website

The static website we created to present our project is in the `website` folder. To serve the website locally, [Jekyll](<https://jekyllrb.com/>) must be installed. To view the website, follow these steps:

1. From a terminal, descend into the `website` directory: `cd website`
2. Serve the site with Jekyll: `jekyll serve`
3. View the site in your favourite browser by entering `localhost:4000` into the address bar

