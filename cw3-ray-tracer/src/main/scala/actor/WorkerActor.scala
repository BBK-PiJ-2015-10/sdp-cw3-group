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
    
    case WorkUnit(x1, y1, x2, y2) => {
     
//         println(s"Work out value for ${x1}, ${y1} to ${x2}, ${y2}")
	


      for (i <- x1 until x2 + 1) {
        
//       println(s"Work out value for ${i}, ${y1}")
        sender ! SetPixel(i,y1, Color.black)
        
        
      }
      
      //println(s"Work out value for ${x} pixels")
      
      
      
    }
    
    
  }
  
 
  
  
}
