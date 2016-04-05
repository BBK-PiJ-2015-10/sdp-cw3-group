package actor

import akka.actor.Actor
import application.WorkUnit
import application.TracerConfiguration
import com.mildlyskilled._
import application.SetPixel
import com.mildlyskilled.Color
import worker.PixelWorker

class WorkerActor (configuration : TracerConfiguration) extends Actor {
  
  val scene = new Scene((configuration.scene.objects, configuration.scene.lights))

  var pixelWorker = new PixelWorker(configuration,scene)
  
  def receive = {
    
    case WorkUnit(pixels) => {

        sender ! SetPixel(pixelWorker.resolvePixels(pixels))      
    }    
  }
  
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
