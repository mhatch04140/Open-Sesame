/* 
 *  OpenSesame
 *  
 *  This is the beginnings of a program to conrol multiple servo motors with bluetooth.
 *  
 *  Currently implementation: 1 servo with input from pb.
 *  
 *  @author Phillip Vickers
 *  
 *  Last Edit: 10/17/2022
 *  
 *  
*/
#include <Servo.h>

Servo driverSideFront;  // create servo object to control a servo
Servo passengerSideFront;

const int DRIVER_FRONT_BUTTON_PIN = 7; // Arduino pin connected to button's pin
const int DRIVER_FRONT_SERVO_PIN  = 9; // Arduino pin connected to servo motor's pin

int angle = 0;    // variable to store the servo position
int lastButtonState;    // the previous state of button
int currentButtonState; // the current state of button

void setup() {
  driverSideFront.attach(DRIVER_FRONT_SERVO_PIN);  
  Serial.begin(9600);                // initialize serial
  pinMode(DRIVER_FRONT_BUTTON_PIN, INPUT_PULLUP); // set arduino pin to input pull-up mode

  driverSideFront.write(angle);
  currentButtonState = digitalRead(BUTTON_PIN);
}

void loop() {
  lastButtonState    = currentButtonState;      // save the last state
  currentButtonState = digitalRead(BUTTON_PIN); // read new state

  if(lastButtonState == HIGH && currentButtonState == LOW) {
    Serial.println("The button is pressed");

    // change angle of servo motor
    if(angle == 0)
      angle = 90;
    else
    if(angle == 90)
      angle = 0;

    // control servo motor arccoding to the angle
    driverSideFront.write(angle);
  }
 }
