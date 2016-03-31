package com.mildlyskilled.actor

import akka.actor.{Actor, ActorRef, Props}

import com.mildlyskilled.{Image}
import com.mildlyskilled.protocol._


class IntegratorActor (outputFile: String, image: Image) extends Actor {
  
    // Number of pixels we're waiting for to be set.
    var waiting = image.width * image.height
    
  
    def receive = {
          
      case SetPixel(x,y,c) =>  {
        image(x, y) = c
        waiting -= 1
        if (waiting == 0 ) {
          image.print(outputFile)
          //This should also send a message to Coordinator that is done. So coordinate, shuts
          //everything down.
          
        }     
      }
      
      
      
      
      
    }
  
  
}