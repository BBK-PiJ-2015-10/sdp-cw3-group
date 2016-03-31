

package application

import com.mildlyskilled.Scene

case class TracerConfiguration ()   {
  
  
  var infile : String = _
  
  var outfile : String = _
  
  var scene: Scene = _
  
  var workers : Int = _
  
  var workUnits : Int = _ 
  
  var dimensions : (Int,Int) = _
  
  var antiAliasingFactor : Int = _
  
  
  
}