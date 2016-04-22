package actor

import application.TracerConfiguration
import akka.actor.Actor
import application._
import accumulator.ImageAccumulator

/**
  * Handles the messages from the WorkerActor and updates the imageAccumulator
  * with their content.
  */
class AccumulatorActor (configuration: TracerConfiguration) extends Actor {
  
 
   //ImageAccumulator to carry out the image functions.
  private var imageAccumulator = new ImageAccumulator(configuration)
  
  /**
    * Handles the following messages:
    * - Initialize: generates a new image.
    * - Finalize: output images.
    * - SetPixels: updates image coordinates from a supplied set of pixels. 
    */
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
  
  /**
    * In case of actor failure redo any fail messages.
    */ 
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
