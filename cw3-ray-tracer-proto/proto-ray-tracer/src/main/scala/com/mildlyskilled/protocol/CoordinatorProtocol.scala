package com.mildlyskilled.protocol
import com.mildlyskilled.Color;

//Removed object encapsulation for the moment, not sure if needed or not.
//Also at the moment only leveraging the RayTracerMessage instead of each of the sealed
//traits that Kobe defined; Event, Data, State, Message.

//object CoordinatorProtocol {

  // Events
  sealed trait Event

  // Data
  sealed trait Data
  
  //case class SetPixel(x: Int, y: Int, c: Color) extends Data
  

  // Coordinator States
  sealed trait State
  
  sealed trait Message


  sealed trait RayTracerMessage
  
  case object StartMessage extends RayTracerMessage
  
  case class WorkUnit(row: Int) extends RayTracerMessage
  
  case class SetPixel(x: Int, y: Int, c: Color) extends RayTracerMessage
  
  


//}
