package accumulator

import application.TracerConfiguration
import com.mildlyskilled.Image
import com.mildlyskilled.Color

class ImageAccumulator (configuration: TracerConfiguration) {
  
  var image: Image = _
  
  def initialize () = {
    image = new Image(configuration.dimensions._1,configuration.dimensions._2)
  }
  
  def finish () = {
    image.print(configuration.outfile) 
  }
  
  def accumulate(x: Int, y: Int, c: Color) = {
	
    image(x, y) = c
  }
  
  
}
