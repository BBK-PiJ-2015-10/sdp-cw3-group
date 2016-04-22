package accumulator

import application.TracerConfiguration
import com.mildlyskilled.Image
import com.mildlyskilled.Color

/**
  *Accumulates pixels into an image.  
  * 
  * It generates an image based on the configuration dimensions,  
  * updates an image based on a set of pixels, prints an
  * image upon request.  
  */

class ImageAccumulator (configuration: TracerConfiguration) {
  
  /**
    * image variable encapsulated in this class. 
    */
  var image: Image = _
  
  /**
    * Initializes an image based on configuration dimensions.
    */
  def initialize () = {
    image = new Image(configuration.dimensions._1,configuration.dimensions._2)
  }
  
  /**
   * Output image to the output file specified in the configuration.
   */
  def finish () = {
    image.print(configuration.outfile) 
  }
  
  /**
    * Convenience method to update a single pixel location with a Color.
    */
  def accumulate(x: Int, y: Int, c: Color) = {
    image(x, y) = c
  }
  
  /**
    * Update a set of pixels with the resolved Color.
    */
  def accumulate(pixels:IndexedSeq[(Int, Int, Color)]) = {
   pixels.foreach(pixel => image(pixel._1, pixel._2) = pixel._3)
  }
}
