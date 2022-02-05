---
layout: page
title: About
permalink: /about/
---
<div align="center">
<img src="{{site.baseurl}}/asset/images/breadtunes.png"/>
</div>
<br/>

### Application Design: Brilliant Decisions & Remaining Smells

One of our better design decisions was the choice to use Java `Observables` for component communication. We have a service that controls subscribing and unsubscribing to various events, which allows subscriptions to events to be done on a per-component basis. This reduces coupling by reducing the amount of communication that happens directly between components. The observables we use are for:

- Notifying when parental control mode status changes.
- Notifying status changes for shuffle and repeat mode.
- Notifying when the database has updated.
- Notifying when the song playing changes.

<div align="center">
<img src="{{site.baseurl}}/asset/images/observable_service.png"/>
</div>
<br/>

One design smell we would like to see fixed in the future is to reduce the numbers of song lists in the app to only one. Several of the fragments use their own copy of the song list, which has made updating the song list when it changes a bit messy.

### System Architecture

We were told to use a 3-layer MVC architecture for this project (Presentation, Logic, Persistence). While we did end up using this architecture, we added another layer that all of the other layers communicate with. This is the _Services_ layer. The _services_ layer is concerned with supplying application-wide singletons, and for handling observables, as mentioned above. A typical use of the _services_ layer would be the presentation layer asking for a persistence object so that it can get all of the songs from the persistence layer. While we could have handled this in the logic layer, the sample project we were given took this approach, and we ended up going along with it.


<div align="center">
<img src="{{site.baseurl}}/asset/images/simple_architecture.png"/>
</div>
<br/>

### How large is the project?

The main application has a total of 43 java classes (not including tests). They are each dedicated to the following purpose:

- 1 / 40 classes (2.5%) are Android Application classes.
- 12 / 40 classes (30%) are for the presentation layer
- 11 / 40 classes (27.5%) are for the business layer and all the related observables
- 8 / 40 classes (20%) are for the persistence layer
- 4 / 40 classes (10%) are for the services layer
- 3 / 40 classes (7.5%) are domain models
- 4 / 40 classes (10%) are exception classes

The following bar chart shows the number of classes per application component visually.

<div align="center">
<img src="{{site.baseurl}}/asset/images/classes_bar_chart.png"/>
</div>
<br/>

### Future Improvements

Due to issues with the system tests, we were unable to get Audio Focus working. Audio Focus is where our app stops playing music when another app wants to play sound. We did get it working for Iteration 3 but it was causing errors with the system tests, so we eventually had to remove it.

A few other things we would like to see in a future version of our app are:

- A notification bar player for our app so that users can control music playback outside of our app
- An album and artist view rather than just a song view
- A more modern UI design
- Playlist creation
- File source management
- Add the ability to edit the tags associated with music files

### Conclusions

Because this project was everyone's first major Android application they worked on, there are some things we would have done differently to build the system in hindsight. But in the end, this project turned out to be a great learning experience. Everyone on the team got valuable experience with:

- Building Android applications
- Writing tests of various levels
- Agile methodology
- 3-tier architectures

### Contributors

__Bamibo Isichei (Software Engineering)__<br />
Things learned from the project:
- Design Patterns
- Git
- Jekyll
- Software architecture
- Testing

__Daniel Lovegrove (Software Engineering)__<br />
Things learned from the project:
- System architecture planning & design
- Android-specific programming (e.g., audio focus, permissions, and Android MediaStore)
- System testing

__Mario Mendez (Computer Science)__<br />
Things learned from the project:
- Design patterns
- Teamwork
- System testing
- Android UI

__Raven Carencia (Computer Science)__<br />
Things learned from the project:
- White box testing
- Design patterns
- Git
- Android UI
