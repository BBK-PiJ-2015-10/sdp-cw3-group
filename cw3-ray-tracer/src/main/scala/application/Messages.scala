package application

import com.mildlyskilled.Color

sealed trait RayTracerMessage

case object Initialize extends RayTracerMessage

case object Finalize extends RayTracerMessage

//case class SetPixel(x: Int, y: Int, c: Color) extends RayTracerMessage
case class SetPixel(pixels: IndexedSeq[(Int, Int, Color)]) extends RayTracerMessage

//case class WorkUnit(x: Int) extends RayTracerMessage

//case class WorkUnit(x1: Int, y1:Int, x2: Int, y2:Int) extends RayTracerMessage

case class WorkUnit(pixels: IndexedSeq[(Int, Int)]) extends RayTracerMessage



