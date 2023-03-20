/*
 *  OpenSesame
 *
 *  This is a program to conrol multiple servo motors with bluetooth.
 *
 *  @author Phillip Vickers and Mike Hatch
 *
 *  Last Edit: 3/5/2023
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

int driverFrontSensorPin = A3;
int driverRearSensorPin = A2;
int passengerFrontSensorPin=A1;
int passengerRearSensorPin= A0;

bool vehicleMoving;

int driverFrontPosistion;

int sensorPin;

int sensorValue;

int pos = 0;

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
                sensorPin = driverFrontSensorPin;
                openDoor(driverSideFront);
                break;

            case '2':
                sensorPin = driverFrontSensorPin;
                closeDoor(driverSideFront);
                break;

            case '3':
                sensorPin = driverRearSensorPin;
                openDoor(driverSideRear);
                break;

            case '4':
                sensorPin = driverRearSensorPin;
                closeDoor(driverSideRear);
                break;

            case '5':
                sensorPin = passengerFrontSensorPin;
                openDoor(passengerSideFront);
                break;

            case '6':
                sensorPin = passengerFrontSensorPin;
                closeDoor(passengerSideFront);
                break;

            case '7':
                sensorPin = passengerRearSensorPin;
                openDoor(passengerSideRear);
                break;

            case '8':
                sensorPin = passengerRearSensorPin;
                closeDoor(passengerSideRear);
                break;

        }//end switch
    }//end if statement
    delay(50);
}//end loop

void openDoor(Servo door){
    for (pos = 0; pos <= 180; pos += 1) {
        door.write(pos);
        sensorValue=analogRead(sensorPin);
        Serial.println(pos);
        
        if(sensorValue>60){
            int npos;
            for (npos = pos; npos >= 0; npos -= 1) {
              door.write(npos);
              Serial.println(npos);
              delay(5);
            }
         
          break;
        }
      
        delay(5);
    }
   
}

void closeDoor(Servo door){
   for (pos = 180; pos >= 0; pos -= 1) {
          door.write(pos);
          sensorValue=analogRead(sensorPin);
          Serial.println(pos);

          if(sensorValue>60){
            int npos;
            for (npos = pos; npos <= 180; npos += 1) {
              door.write(npos);
              Serial.println(npos);
              delay(5);
          }
        
          break;
        }
      
          delay(5);
        
   }

   }

void setupDoors(){
  driverSideFront.write(0);
  driverSideRear.write(0);
  passengerSideFront.write(0);
  passengerSideRear.write(0);
  
}
