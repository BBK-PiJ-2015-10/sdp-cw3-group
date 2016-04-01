package actor

import application.TracerConfiguration
import akka.actor.Actor
import application._
import accumulator.ImageAccumulator

class AccumulatorActor (configuration: TracerConfiguration) extends Actor {
  
  var imageAccumulator = new ImageAccumulator(configuration)
  
  def receive = {
    
    case Initialize => {
      
     imageAccumulator.initialize()
      
    }
    
    case Finalize => {
      
      imageAccumulator.finish()
      
      sender ! Finalize
      
    }
    
//    case SetPixel(x, y, c) => {
//    
//      imageAccumulator.accumulate(x, y, c)     
//    }
    
    case SetPixel(pixels) => {
    
      imageAccumulator.accumulate(pixels)
      
    }
    
  }  
  
}
