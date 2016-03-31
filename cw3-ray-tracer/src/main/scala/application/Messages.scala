package application

import com.mildlyskilled.Color

sealed trait RayTracerMessage

case object Initialize extends RayTracerMessage

case object Finalize extends RayTracerMessage

case class SetPixel(x: Int, y: Int, c: Color) extends RayTracerMessage

case class WorkUnit(x: Int) extends RayTracerMessage


