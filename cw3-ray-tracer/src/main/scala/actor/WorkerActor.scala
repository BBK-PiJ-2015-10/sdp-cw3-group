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
  
  var pixel :IndexedSeq[(Int, Int)] = _
  
  var pixelWorker = new PixelWorker(pixel,configuration,scene)
  
  def receive = {
    
//    case WorkUnit(x1, y1, x2, y2) => {
//
//      for (i <- x1 until x2 + 1) {
//       sender ! SetPixel(i,y1, Color.black)        
//      }

    case WorkUnit(pixels) => {

        sender ! SetPixel(pixelWorker.resolvePixels(pixels))
      
//    	pixels.foreach(pixel => sender ! SetPixel(pixel._1,pixel._2, Color.green))      
        //sender ! SetPixel(resolvePixels(pixels))
      
    }    
  }  

  /*
  
  def resolvePixels(pixels:IndexedSeq[(Int, Int)]):IndexedSeq[(Int, Int, Color)] = {

     	pixels.map { pixel => (pixel._1,pixel._2, trace(pixel)) }
  }

  def trace(value:Int):Color = if (value > configuration.dimensions._1 / 2) Color.red else Color.blue

  def trace(pixel:(Int, Int)):Color = {

	val i = pixel._2
	val x = pixel._1

	val angle = 90f 
    	val frustum = (.5 * angle * math.Pi / 180).toFloat
    	val cosf = math.cos(frustum)
    	val sinf = math.sin(frustum)
	
        var colour = Color.black
        
        val ss = configuration.antiAliasingFactor
        
        for (dx <- 0 until ss) {
          for (dy <- 0 until ss) {
            
            val dir = Vector(

              (sinf * 2 * ((x + dx.toFloat / ss) / configuration.dimensions._1 - .5)).toFloat,
              (sinf * 2 * (configuration.dimensions._2.toFloat / configuration.dimensions._1) * (.5 - (i + dy.toFloat / ss) / configuration.dimensions._2)).toFloat,
              cosf.toFloat).normalized  
                
              //I remove references to scene.t; since we are not capturing that data in the configuration.
              //(sinf * 2 * ((x + dx.toFloat / ss) / scene.t.Width - .5)).toFloat,
              //(sinf * 2 * (scene.t.Height.toFloat / scene.t.Width) * (.5 - (i + dy.toFloat / ss) / scene.t.Height)).toFloat,
              //cosf.toFloat).normalized
            
            val c = scene.trace(Ray(scene.eye, dir)) / (ss * ss)
            colour += c  
             
          }
        }
        
	
	       // I am not sure what is the purpose of this count. I suggest to comment it out.
        //if (Vector(colour.r, colour.g, colour.b).norm < 1)
          //scene.t.darkCount += 1
        //if (Vector(colour.r, colour.g, colour.b).norm > 1)
          //scene.t.lightCount += 1

	colour
}
  
 */
  
}
