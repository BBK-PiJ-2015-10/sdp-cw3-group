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
    
//    case WorkUnit(x1, y1, x2, y2) => {
//
//      for (i <- x1 until x2 + 1) {
//       sender ! SetPixel(i,y1, Color.black)        
//      }

    case WorkUnit(pixels) => {

    	pixels.foreach(pixel => sender ! SetPixel(pixel._1,pixel._2, Color.green))      
    }    
  }  
}
