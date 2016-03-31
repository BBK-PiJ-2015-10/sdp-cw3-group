package actor

import akka.actor.Actor
import application.Initialize
import application.TracerConfiguration




class CoordinatorActor (configuration: TracerConfiguration) extends Actor {
  
  def receive = {
    
    case Initialize => {
      
      println("Initializing all Actors")
     initializeSystem(configuration)
      
    }
    
    
    
  }
  
  
  def initializeSystem(configuration: TracerConfiguration) = {
    
    println("Create Accumulator")
    
    
    
    println("Create workers")
    println(s"Create ${configuration.workUnits} sub units from ${configuration.dimensions}")
    
  }

}
