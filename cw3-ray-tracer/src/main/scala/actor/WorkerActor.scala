package actor

import akka.actor.Actor
import application.WorkUnit
import application.TracerConfiguration
import com.mildlyskilled.Scene
import application.SetPixel
import com.mildlyskilled.Color

class WorkerActor (configuration : TracerConfiguration) extends Actor {
  
  val scene = new Scene((configuration.scene.objects, configuration.scene.lights))
  
  def receive = {
    
    case WorkUnit(x) => {
      
      println(s"Work out value for ${x} pixels")
      
      sender ! SetPixel(0,0, Color.black)
      
    }
    
    
  }
  
  
  
  
  
}