package worker

import application.WorkUnit
import application.TracerConfiguration
import com.mildlyskilled._
import application.SetPixel
import com.mildlyskilled.Color

class PixelWorker (configuration : TracerConfiguration, scene: Scene) {
  

  def resolvePixels(pixels:IndexedSeq[(Int, Int)]):IndexedSeq[(Int, Int, Color)] = {

     	pixels.map { pixel => (pixel._1,pixel._2, trace(pixel)) }
  }

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
                        
            val c = scene.trace(Ray(scene.eye, dir)) / (ss * ss)
            colour += c  
             
          }
        }
//	println(s"Colour is ${colour}")
	colour
  }  
}
