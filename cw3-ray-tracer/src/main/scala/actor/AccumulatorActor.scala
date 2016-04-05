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
    
    
    case SetPixel(pixels) => {
    
      imageAccumulator.accumulate(pixels)
      
    }
    
  }
  
    override def preRestart(reason: Throwable, message: Option[Any]) = {

    super.preRestart(reason, message)

    message match {

      case None => {}

      case Some(_) => {

        message.get match {

          case SetPixel(pixels) => {
            println(s"Restarting after failure")
            sender !  imageAccumulator.accumulate(pixels)

          }
          case _ => {
            println(s"Unknown message after failure }")
          }
        }

      }

    }

  }
  
  
}
