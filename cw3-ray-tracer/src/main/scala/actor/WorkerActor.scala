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
}
