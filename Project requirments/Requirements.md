# Open Sesame
Project Requirments
Micheal Hatch & Phillip Vickers
Copyright 2022

## Introduction 

@author Mike Hatch

In today's world, vehicles come with a wide range of options. The options range from telling you how many miles your car will go on a current gas level to when it's time to change the oil. Furthermore, some cars have options that dim their lights from high beam to low beam when you approach another car.

It is common for people to have something in their hands when approaching a vehicle. If a door could be opened without having to use your hands in these situations, it would be very convenient. Nowadays, most of these options are controlled by motion sensors, which alert the main computer to open the hatch when movement is detected. There is a requirement for one leg to be used with this system. It is difficult for me to operate with one leg and a heavy item in one hand. I would prefer voice-activated accommodation. With voice recognition, any door will be able to be opened by the vehicle operator.

When was the last time you were asked, "Can you unlock your car door?" Next, you must find your keys, go to the window, and unlock the car door with the remote. With this App, you can say, "Ford, unlock all the doors." You can also do this while sitting comfortably.

Since the mid-2000s, cars have had apps that alert you of oil changes, remote starting and stopping, and when the door is opened. Therefore, I'm developing an App to open doors with voice recognition. By walking within twenty feet of your car, you can say, "Ford, open the passenger door." The App would already know your make, model, and VIN number. With this application, you can operate any door in your car.

Furthermore, the vehicle would include safety features to prevent children from getting pinned when the doors are opened or closed. When a signal is received from the door robot arm, the program instructs the car's computer on which way to open or close the door. The vehicle can then be entered and exited safely by children. 
From the car computer to the door operation, the car manufacturer is responsible for all mechanical and computer operations.

In conclusion, this project will only intail a protype. We will not be alternating an existing car system. Our prototype will include an Adrunino processor, Blue tooth technology, servo motors to represent car doors opening and closing, handheld device(phone) and LED lights representing go or no go for door operation. 

## Functional Requirments

@author Mike Hatch

### Use case #1 

Goal

A door opens when the voice recognition system receives a command from the handheld device.

Other Resources Needed

car -> prototype
Handheld Phone

User Action

First the user opens the APP. Then the user touchs the Voice Activation Button. Then the user say's "Open right front door." 

Product Action

The signal travels by blue tooth to the car and the car computer opens the right front door if all safty conditions are meet.

Safty Conditions

Parking Brake must be applied.
Car must be parked.

### Use case #2

Goal

The door closes after the voice recognition receives a command from the operator through the handheld device. 

Other Resources Needed

Handheld device
Car car -> prototype

User Action
First the user opens the APP. Then the user touchs the Voice Activation Button. Then the user say's "Close right front door." 
Product Action

The signal travels by blue tooth to the car and the car computer opens the right front door if all safty conditions are meet.

Safty Conditions

Parking Brake must be applied.
Car must be parked.

## Use case #3

Car computer sends notification that car door can not open becasue door is being blocked.

Goal

When a door is opening and an object is blocking it, an alert appears on the App. 

Other Resources Needed

Car computer 

User Action

Once you've received the notification, check to see what the obstruction is.  If necessary, remove the obstruction and repeat the initial command. 

Product Action

When the door is blocked, you receive a notification from the App.  As soon as the new command is received, the App completes the operation. 

## Use case #4

Car Computer sends notification about door closing during operation and bound by an object.

Goal

If an object obstructs a door while it is closing, the App notifies you. 

Other Resources Needed

Car Computer

User Action

Once you receive the notification, look for the obstruction.  Clear it and repeat the original command. 

Product Action

A notification is sent to you when an obstruction is detected at the door.  Once the App receives the new command, it will initiate the operation. 

## Use case #5

When parking brake is apllied, the system will allow for door operation.

Other Resources

N/A

Goal

If the parking break is not applied the App will not open a car door. If the parking break is applied the App will complete the operation.

User Action

Before leaving a parked car, make sure the parking break has been applied. Then use the App as the App is attended for.

Product Action

This action is completed with a parking break being applied and the car sends the safty signal that the break is applied.

## Use case #6

Car is moving while App is being asked to complete a task.

Goal

If there is movement in the tire roatation, the App will Not allow any voice activations.

Other Resources

Tire Monitor - in the proto type this be be a LED light. The LED light will be green for no tire rotation and red for tire rotation.

User Action

If a voice caommand is given and the command is not completed the APP will dispay a "tire rolling error." The car must be stopped by the user
and then use the voice command.

Product action

When the LED light is green, the APP voice commands will complete.  If the LED light is red the App will not operate.


## Non-Functional Requirements

@author Phillip Vickers

### Platform

Open Sesame will be a mobile application that operates only the Android OS. The mobile application will be designed to be compatable with the latest version of the OS Android 12 Snowcone.

The hardware component of Open Sesame will be based on an Arduino developmentboard. An Arduino Uno Rev 3 will be used in this project. The Uno is built upon an Atmega328p.

### Performance

#### Moblile App 

| Category | Limit |
|--------- |-------|
|**Loading time:** | 3 sec |
|**App Size:** |  30Mb |
|**Command response time** | 1 sec |
|**Bluetooth range:** | 50 ft |

#### Door Actuation System 

| Category | Limit |
|--------- |-------|
|**Open doors** | 4 |
|**Door opening time** |  5 sec |
|**Memory usage** | 30kb |

Arduino Uno [Tech Specs](https://docs.arduino.cc/resources/datasheets/A000066-datasheet.pdf?_gl=1*1tzapmj*_ga*MjEyODY5MjA4MS4xNjYzNDU4MDg0*_ga_NEXN8H46L5*MTY2MzQ1ODA4NC4xLjEuMTY2MzQ1OTkxMy4wLjAuMA)


### Security

### User Characteristics

### Documentation

**Design Documentation.** This document shall be a detailed design that describes how the various components of the system work. It may include block diagrams, schematics, or code snippets, as appropriate. The audience of this document is other develoers who might want to understand and extend the system.

**User Documentation.** This document shall describe how to install, configure, and use the system. The audience of this document is people who want to use the system without understanding the internal operation of the system

**Website.** A website shall be produced that describes the system. The audience of this site is potential users considering the system and current users looking for technical support. This site can be hosted anywhere that is convenient

### Data Formats

Mobile app input methods will consist of both voice commands and button presses. The app will connect with the development board via bluetooth. Bluetooth 2.0 will be used.

The development board will connect to 4 servos that opens individual doors. The 4 servos will use the PWM pins on the dev board.

### Internationalization

Open Seasame will only support english at this time.

### Environment



### Expected Enhancements

### Date
