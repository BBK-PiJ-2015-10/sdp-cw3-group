package actor

import akka.actor.Actor
import application.WorkUnit
import application.TracerConfiguration
import com.mildlyskilled._
import application.SetPixel
import com.mildlyskilled.Color
import worker.PixelWorker

/**
  * Resolves a set of pixels based on a scene.
  */
class WorkerActor (configuration : TracerConfiguration) extends Actor {
  
  // scene field based on configuration scene objects and lights.
  private val scene = new Scene((configuration.scene.objects, configuration.scene.lights))

  // pixel worker to handle pixel resolution task. 
  private var pixelWorker = new PixelWorker(configuration,scene)
  
  /**
    * Handles the following messages:
    * - WorkUnit: Resolves the color for a set of pixels.
    */  
  def receive = {
    
    case WorkUnit(pixels) => {

        sender ! SetPixel(pixelWorker.resolvePixels(pixels))      
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

          case WorkUnit(pixels) => {
            println(s"Restarting after failure")
            sender ! SetPixel(pixelWorker.resolvePixels(pixels))

          }
          case _ => {
            println(s"Unknown message after failure }")
          }
        }

      }

    }

  }
  
  
  
}
