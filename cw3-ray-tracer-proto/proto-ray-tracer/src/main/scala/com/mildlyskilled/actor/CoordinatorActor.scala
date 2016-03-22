package com.mildlyskilled.actor

//import akka.actor.{Actor, ActorLogging}
import akka.actor.{Actor, ActorRef, Props}
import akka.routing.RoundRobinPool


import com.mildlyskilled.{Image}
import com.mildlyskilled.protocol._
import com.mildlyskilled.Scene;


//We should or might need to use extend ActorLogging, for the moment, have taken it off.


class CoordinatorActor(outputFile: String, image: Image, scene: Scene) extends Actor {

    // Number of pixels we're waiting for to be set.
    var waiting = image.width * image.height
    
   
    val tracerActorRouter = context.actorOf(Props(classOf[TracerActor],scene).withRouter(RoundRobinPool(image.height)), name = "tracerActorRouter")
  
    def receive = {
      
      case StartMessage => {
        for (i ← 0 until image.height)
            tracerActorRouter ! WorkUnit(i)   
      }
        
      case SetPixel(x,y,c) =>  {
        image(x, y) = c
        waiting -= 1
        if (waiting == 0 ) {
          image.print(outputFile)
        }     
      }
      
      
    }

    
    
}
