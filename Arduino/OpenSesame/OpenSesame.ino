/* 
 *  OpenSesame
 *  
 *  This is the beginnings of a program to conrol multiple servo motors with bluetooth.
 *  
 *  Currently implementation: 1 servo with input via bluetooth.
 *  
 *  @author Phillip Vickers
 *  
 *  Last Edit: 11/14/2022
 *  
 *  
*/
#include <Servo.h>

Servo driverSideFront;  // create servo objects to control servos
Servo driverSideRear;
Servo passengerSideFront;
Servo passengerSideRear;

const int DRIVER_FRONT_SERVO_PIN=6; // Arduino pins connected to servo motor's pin
const int DRIVER_REAR_SERVO_PIN=7;
const int PASSENGER_FRONT_SERVO_PIN=8;
const int PASSENGER_REAR_SERVO_PIN=9;

int pos = 0;

void setup() {
  Serial.begin(9600); 
  
  driverSideFront.attach(DRIVER_FRONT_SERVO_PIN);
  driverSideRear.attach(DRIVER_REAR_SERVO_PIN);
  passengerSideFront.attach(PASSENGER_FRONT_SERVO_PIN);
  passengerSideRear.attach(PASSENGER_REAR_SERVO_PIN);
}

void loop() {
  if(Serial.available()>0)
   {     
      char data= Serial.read(); // reading the data received from the bluetooth module
      switch(data)
      {
        case '1': 
        {
          for (pos = 0; pos <= 180; pos += 1) { 
            driverSideFront.write(pos);              
            delay(5); 
          }
          break;
        }
        case '2': 
        {
           for (pos = 180; pos >= 0; pos -= 1) { 
            driverSideFront.write(pos);             
            delay(5);                       
           }
          break;
        }
        default : break;
      }
      Serial.println(data);
   }
   delay(50);     
 }
