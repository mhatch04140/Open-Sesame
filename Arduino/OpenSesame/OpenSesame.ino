/*
 *  OpenSesame
 *
 *  This is a program to conrol multiple servo motors with bluetooth.
 *
 *  @author Phillip Vickers and Mike Hatch
 *
 *  Last Edit: 4/20/2023
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


int sensorValue;

void setup() {
    Serial.begin(9600);

    driverSideFront.attach(DRIVER_FRONT_SERVO_PIN);
    driverSideRear.attach(DRIVER_REAR_SERVO_PIN);
    passengerSideFront.attach(PASSENGER_FRONT_SERVO_PIN);
    passengerSideRear.attach(PASSENGER_REAR_SERVO_PIN);

    setupDoors();
}

void loop() {
    if(Serial.available()>0){
        char data= Serial.read(); // reading the data received from the bluetooth module
        switch(data) {
            case '1':
                openDoor(driverSideFront);
                break;

            case '2':
                closeDoor(driverSideFront);
                break;

            case '3':
                openDoor(driverSideRear);
                break;

            case '4':
                closeDoor(driverSideRear);
                break;

            case '5':
                openDoor(passengerSideFront);
                break;

            case '6':
               
                closeDoor(passengerSideFront);
                break;

            case '7':
                openDoor(passengerSideRear);
                break;

            case '8':
                closeDoor(passengerSideRear);
                break;

        }//end switch
    }//end if statement
    delay(50);
}//end loop

void openDoor(Servo door){
 // move the servo to 0 degrees
  door.write(0);

  // wait for the servo to reach the desired position
  delay(350);
  sensorValue=analogRead(A0);


  if(sensorValue!=0){
    closeDoorNoCheck(door);
    return;
  }

  Serial.println(door.read());   
}

void closeDoor(Servo door){
 // move the servo to 180 degrees
  door.write(180);

  // wait for the servo to reach the desired position
  delay(350);

  // read the current position of the servo
  sensorValue=analogRead(A0);
 

  if(sensorValue!=0){
   openDoorNoCheck(door);
   return;
  }

  Serial.println(door.read());

   }

   
void closeDoorNoCheck(Servo door){
  door.write(180);
  delay(350);
  sensorValue=analogRead(A0);
  Serial.println(door.read());
}

void openDoorNoCheck(Servo door){
  door.write(0);
  delay(350);
  sensorValue=analogRead(A0);
  Serial.println(door.read());   
}

void setupDoors(){
  driverSideFront.write(180);
  driverSideRear.write(180);
  passengerSideFront.write(180);
  passengerSideRear.write(180);
  
}
