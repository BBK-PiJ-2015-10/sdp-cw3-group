package com.mildlyskilled.actor

//import akka.actor.{Actor, ActorLogging}
import akka.actor.{Actor, ActorRef, Props}
import akka.routing.RoundRobinPool

import com.mildlyskilled.{Image}
import com.mildlyskilled.protocol._
import com.mildlyskilled.Scene;




class CoordinatorActor(outputFile: String, image: Image, scene: Scene) extends Actor {
   
    
    val integratorActor = context.actorOf(Props(new IntegratorActor(outputFile,image)), name = "integratorActor")
    
    val tracerActorRouter = context.actorOf(Props(classOf[TracerActor],scene,integratorActor).withRouter(RoundRobinPool(image.height)), name = "tracerActorRouter")

    
    
    def receive = {
      
      case StartMessage => {
        for (i ← 0 until image.height)
            tracerActorRouter ! WorkUnit(i)   
      }
        
      //I think we should add a case for a message sent from Integrator communication that they
      //are done, so Coordinator can shut everything down.
      
    }

    
    
}

  
  
  
  
  
  
