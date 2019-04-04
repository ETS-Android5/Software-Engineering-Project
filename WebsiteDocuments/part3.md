# Development Postmortem Report

## Ensure that you discuss the area you identified for improvement after your iteration 2 retrospective; how did it go? (Raven)

An area of improvement that was identified after iteration 2 was increasing the communication with the team. In iteration 3, the team was successful in doing this by having no less than 2 in-person meetings outside class hours.
The team was also able to practice the "Slack stand-up meetings" to keep everyone updated with their progress and ask for help in the obstacles they encountered. The #repository channel in the team's Slack group was a huge help
do keep track of changes done in master and inspect the changes closely for better understanding of the changes that have been pushed. As what has been reflected on and planned after iteration 2, the overall communication of the
team has improved a lot and this has definitely helped with the progress of the project.

## Question 2: What would you do differently, if you had the chance to start over?

If the team had the chance to start over, the team would use their time more efficiently. The team would also improve a lot on their communication and have as much in-person meetings
as possible given the time they have. The team would utilize communication software (in this instance, Slack) more efficiently and make reports every regular intervals so everyone is on track with the
changes that are being made in the project. The team will not put the project on hold until the last 2 weeks before an iteration is due; the team will start an iteration as soon as they are available to. The team will also
utilize GitLab to better issue discovered bugs and to better work on existing bugs. Up until the third iteration, the group held discussions about bugs only on Slack and live meetings. Using GitLab will make keeping track of bugs easier and
more efficient because there are features in GitLab that will be helpful in notifying other team members when one member finds a bug.

## Question 3: How large is the project?

The main application has a total of 43 classes, 11 of it dedicated to the presentation component, 6 dedicated to the persistence component, 12 dedicated to the persistence component, and the others act as helpers to these
components. The business class's observables have 11 methods. Excluding the methods in the observables, there are 65 methods that are roughly devoted to the business class. For the presentation component, there are 6 methods
in the BaseActivity class. There are 9 methods for the presentation layer's MediaPlayerController class. Excluding the BaseActivity and MediaPlayerController class, there are a total of 85 methods that are devoted to the
presentation component. There are a total of 12 methods in CredentialPersistenceHSQL and SongPersistenceHSQL. For the persistence layer's loaders, there are 7 methods amounting to a total of 19 methods for the persistence component of the application.
For the group's project velocity for both iteration 1 and 2, the group has committed to a total of 286 hours and has achieved 209 hours out of that.

## Application Design: Brilliant Decisions and Remaining Smells

One of our brilliant design decisions was the choice to use Java `Observables` for component communication. We have a service that controls subscribing and unsubscribing to various events, which allows subscriptions to events to be done on a per-component basis. This reduces coupling by reducing the amount of communication that happens directly between components. The observables we use are for:

- Notifying when parental control mode status changes.
- Notifying status changes for shuffle and repeat mode

One design smell we would like to see fixed in the future is to reduce the numbers of song lists in the app to only one. Several of the fragments use their own copy of the song list, which has made updating the song list when it changes a bit messy.

## System Architecture



## Future Improvements

Due to issues with the system tests, we were unable to get Audio Focus working. Audio Focus is where our app stops playing music when another app wants to play sound. We did get it working for Iteration 3 but it was causing errors with the system tests, so we eventually had to remove it.

A few other things we would like to see in a future version of our app are:

- A notification bar player for our app so that users can control music playback outside of our app.
- An album and artist view rather than just a song view.
- A more modern UI design.

## Conclusions





