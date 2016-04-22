package application

import com.mildlyskilled.Scene
/**
  * A data class to encapsulate the configuration parameters in the RayTracer system
  *
  */
case class TracerConfiguration ()   {
  
/**
  * The name of the input file that contains the scene definition.
  *
  */
  var infile : String = _
/**
  * The name of the output file containing the resolved image.
  *
  */
  var outfile : String = _
  
/**
  * A Scene object representing the parsed contents of the input file. For 
  * efficiency this should be used to clone new Scene instances.
  *
  */
  var scene: Scene = _
  
/**
  * The number of worker actors to which the task is delegated. 
  *
  */
  var workers : Int = _
  
/**
  * The number of units that the task will be broken into. 
  *
  */
  var workUnits : Int = _ 
  
/**
  * The dimensions of the image to build. The values represent the x-dimension
  * and the y-dimension respectively.
  *
  */  
  var dimensions : (Int,Int) = _
  
/**
  * The anti-aliasing factor to use in the ray tracing algorithm
  *
  */    
  var antiAliasingFactor : Int = _
  
  
  
}