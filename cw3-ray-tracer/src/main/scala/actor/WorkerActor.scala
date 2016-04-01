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


//    	pixels.foreach(pixel => sender ! SetPixel(pixel._1,pixel._2, Color.green))      
        sender ! SetPixel(resolvePixels(pixels))
    }    
  }  

  def resolvePixels(pixels:IndexedSeq[(Int, Int)]):IndexedSeq[(Int, Int, Color)] = {

     	pixels.map { pixel => (pixel._1,pixel._2, trace(pixel._1)) }
  }

  def trace(value:Int):Color = if (value > configuration.dimensions._1 / 2) Color.red else Color.blue
}
