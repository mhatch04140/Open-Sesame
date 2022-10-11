# Open Sesame
Project Requirements
Micheal Hatch & Phillip Vickers
Copyright 2022

<!--- Comments by Peter Chapin, October 11, 2022 -->

## Introduction 

@author Mike Hatch

<!--- I'm not sure what tool/editor you used to produce this document, but each paragraph was
represented as a single long line (fairly normal for wordprocessors, not as normal for text
editors). I took the liberty of formatting the paragraphs with a width of 98 characters since
the word wrapping made them hard to read in my text editor. -->

In today's world, vehicles come with a wide range of options. The options range from telling you
how many miles your car will go on a current gas level to when it's time to change the oil.
Furthermore, some cars have options that dim their lights from high beam to low beam when you
approach another car.

<!--- You write "It is difficult for me.." yet this project has two authors. If you want to
refer to yourself, use "we." It is also considered more professional to avoid talking directly
to the reader with "you", although that viewpoint is somewhat controversial. -->

It is common for people to have something in their hands when approaching a vehicle. If a door
could be opened without having to use your hands in these situations, it would be very
convenient. Nowadays, most of these options are controlled by motion sensors, which alert the
main computer to open the hatch when movement is detected. There is a requirement for one leg to
be used with this system. It is difficult for me to operate with one leg and a heavy item in one
hand. I would prefer voice-activated accommodation. With voice recognition, any door will be
able to be opened by the vehicle operator.

When was the last time you were asked, "Can you unlock your car door?" Next, you must find your
keys, go to the window, and unlock the car door with the remote. With this App, you can say,
"Ford, unlock all the doors." You can also do this while sitting comfortably.

Since the mid-2000s, cars have had apps that alert you of oil changes, remote starting and
stopping, and when the door is opened. Therefore, I'm developing an App to open doors with voice
recognition. By walking within twenty feet of your car, you can say, "Ford, open the passenger
door." The App would already know your make, model, and VIN number. With this application, you
can operate any door in your car.

Furthermore, the vehicle would include safety features to prevent children from getting pinned
when the doors are opened or closed. When a signal is received from the door robot arm, the
program instructs the car's computer on which way to open or close the door. The vehicle can
then be entered and exited safely by children. From the car computer to the door operation, the
car manufacturer is responsible for all mechanical and computer operations.

Ultimately, this project will only involve a prototype. Currently, there is no plan to replace
the existing car system. Our prototype will have an Arduino processor, Bluetooth technology,
servo motors to represent the opening and closing of car doors, a handheld device (phone), and
LED lights representing go or no go for door operation.
 

## Functional Requirements

@author Mike Hatch

<!--- I recommend naming use cases instead of (or in addition to) numbering them. This limits
confusion when you feel the need to add another use case and renumber the existing ones. -->

### Use case #1 

Goal

A door opens when a handheld device speaks a command to the voice recognition system.

Other Resources Needed

car -> prototype

Handheld Phone

User Action

In order to use the application, the user must first open it. After that, the user touches the
Voice Activation Button. The user then says, "Open the right front door."

Product Action

If all safety conditions are met, a blue tooth signal is transmitted to the car and the computer
opens the right front door.

Safety Conditions

Parking Brake must be applied.
Car must be parked.

<!--- Does the user get any feedback about the success or failure of this operation? -->

### Use case #2

<!--- Should the words "Goal", "Handheld device", etc., be low-level headers? I feel like they
probably should be. -->

Goal

After the handheld device receives a command from the operator, the door closes.

Other Resources Needed

Handheld device

Car -> prototype

User Action

In order to use the app, the user must first open it. The user then presses a voice activation
button. The user then says, "Close the right front door."

Product Action

If all safety conditions are met, a blue tooth signal is transmitted to the car and the computer
opens the right front door.

Safety Conditions

Parking Brake must be applied.
Car must be parked.

<!--- Again, does the user get any feedback on the success/failure of this operation? -->

## Use case #3

<!--- I think this use case is really the error condition I was talking about earlier. Probably
this information can be merged into the other use cases. The "User Action" here is somewhat
outside the scope of these requirements since it does not pertain to the actions of your system.
However, it does lead me to ask: if there is blockage does the door close automatically, or is
it left in a half-open state? -->

The car computer informs the driver that the door cannot be opened due to a blockage.

Goal

Whenever a door is opening and an object is blocking it, an alert appears on the app. 

Other Resources Needed

Car computer 

User Action

As soon as you receive the notification, determine what obstruction is present and remove it if
necessary.

Product Action

Your App notifies you when the door is blocked. Upon removing the blocking item and ensuring all
safety measures have been implemented. Normal operations will be carried out by the app.


## Use case #4

The car computer informs the driver that the door cannot close due to a blockage.

Goal

Whenever a door is being closed and an object is blocking it, an alert appears on the app. 

Other Resources Needed

Car Computer

User Action

As soon as you receive the notification, determine what obstruction is present and remove it if necessary.

Product Action

Your App notifies you when the door is blocked. Upon removing the blocking item and ensuring all safety measures have been implemented.  Normal operations will be carried out by the app.

## Use case #5

The system will allow door operation when the parking brake is applied.

Other Resources

N/A

Goal

It will not be possible to operate the App if the parking brake is not applied. App will
complete the operation if parking brake is applied.

User Action

Make sure the parking brake has been applied before leaving a parked car. The App can then be
used since the parking brake has been taken care of.

Product Action

When the parking brake is applied, the car sends the safety signal that the break has been
applied.

## Use case #6

A task is being asked of the App while the car is moving.

Goal

It is not possible to activate the App using voice if there is movement in the tire rotation.

Other Resources

Initially, this will be a LED light that monitors tire rotation. For no tire rotation, the LED
light will be green, while for tire rotation, it will be red.

User Action

In the event that a voice command is given, but not completed, the APP will display a "tire
rolling error." The user must stop the car. Voice commands can then be used.


Product action

<!-- You're talking about a simulated LED in the application, and not a real LED, yes? You
might clarify this a little. -->

When the LED light on the APP turns green, the voice commands can be completed. There will be no
operation of the App if the LED light is red.


## Non-Functional Requirements

@author Phillip Vickers

### Platform

<!--- Some people like using "shall" when talking about specific mandatory attributes ("Open
Sesame *shall* be a mobile application..."). You might find that overly formal. See RFC-2119 for
more ideas about how to employ specific words like "may", "should", "must", etc. -->

Open Sesame will be a mobile application that operates only the Android OS. The mobile
application will be designed to be compatible with the latest version of the OS Android 12
Snowcone.

The hardware component of Open Sesame will be based on an Arduino development board. An Arduino
Uno Rev 3 will be used in this project. The Uno is built upon an Atmega328p.

<!--- The following paragraphs appear to repeat the information in the first two paragraphs. -->

Open Sesame will be a mobile application that operates only the Android OS. The mobile
application will be designed to be compatable with the latest version of the Android OS and have
compatability back to Android 12 Snowcone.

The hardware component of Open Sesame will be based on an Arduino development board. An Arduino
Uno Rev 3 will be used in this project. The Uno is built upon an Atmega328p microcontoller.


### Performance

#### Mobile App 

<!--- How is the size measured? Is that code size only, or does it include data/cache size
as well? -->

| Category | Limit |
|--------- |-------|
|**App Loading time:** | 3 sec |
|**App Size:** |  <30Mb |
|**Command response time** | 1 sec |
|**Bluetooth range:** |~ 50 ft |

#### Door Actuation System 

<!--- Are hatches and sliding doors supported? This might be a question for the functional
requirements. -->

| Category | Limit |
|--------- |-------|
|**Max # of open doors** | 4 |
|**Door opening time** |  5 sec |
|**Door closing time** |  5 sec |
|**Memory usage** | 30kb |
|**Door will detect obstruction** | 500ms |

Arduino Uno [Tech Specs](https://docs.arduino.cc/resources/datasheets/A000066-datasheet.pdf?_gl=1*1tzapmj*_ga*MjEyODY5MjA4MS4xNjYzNDU4MDg0*_ga_NEXN8H46L5*MTY2MzQ1ODA4NC4xLjEuMTY2MzQ1OTkxMy4wLjAuMA)


### Security

<!--- I wonder if fingerprint access makes sense for an app like this. It can be quicker. -->

App security will consist of a user name and password to protect against unauthorized access.
Security between the app and Bluetooth module will consist of pin matching to ensure that the
app is connecting to the correct Bluetooth device.

### User Characteristics

The mobile application's user interface needs to be simple, user friendly, and easy to use. The
purpose of the system is making it easier for a user to open a car door when they may be
otherwise burdened with their hands full.

In order for the system to benefit a user, the system must be as simple as possible.

1. The app must load quickly and after a brief splash screen the app needs to be ready to use.

<!--- A home screen widget might make sense for this app, but perhaps that is not something you
want to deal with now. A future addition, perhaps? -->

2. Once on the main screen one button press should set the app in a state to receive voice
   commands.

3. If a valid command is issued then no further user action should be required to open or close
   a door.

4. If an error or safety stoppage occurs then the app should clearly communicate the nature of
   the failure to the user.

### Documentation

**Design Documentation.** This document shall be a detailed design that describes how the
various components of the system work. It may include block diagrams, schematics, or code
snippets, as appropriate. The audience of this document is other developers who might want to
understand and extend the system.

**User Documentation.** This document shall describe how to install, configure, and use the
system. The audience of this document is people who want to use the system without understanding
the internal operation of the system

**Website.** A website shall be produced that describes the system. The audience of this site is
potential users considering the system and current users looking for technical support. This
site can be hosted anywhere that is convenient

### Data Formats

Mobile app input methods will consist of both voice commands and button presses. The app will
connect with the development board via bluetooth. Bluetooth 2.0 will be used.

The development board will connect to 4 servos that opens individual doors. The 4 servos will
use the PWM pins on the dev board.

### Internationalization

Open Sesame will only support english at this time.

### Environment

The mobile application will exist in a user’s Android device and will be subject to the
environmental conditions that the user subjects their mobile phone to.

For the purpose of this prototype, the door opening system will not be subjected to any austere
environmental conditions. In this case water, dirt, grease, road salt, etc. does not need to be
a factor.

If the system was to move beyond a prototype or proof of concept then adverse environmental
factors would need to be considered. In a production environment the door opening system would
need to be weatherproof or located in an area of the vehicle that would mitigate these concerns.
For example, if the system was to move to production, then the system would need to be robust
enough to function in temperatures ranging from -40 degrees Fahrenheit to 120 degrees
Fahrenheit. The system would need to be waterproof, resistant to interference from dirt, grease,
and salt. It would also need to withstand severe vibration and be reliable enough to be used
indefinably with possibly thousands of door openings.

### Expected Enhancements

No expected enhancements expected beyond delivery of initial prototype.

### Date

The final presentation will be on or about the last day of classes of the Spring 2023 semester.
The final demonstration and the documentation will be due at the end of finals week.
