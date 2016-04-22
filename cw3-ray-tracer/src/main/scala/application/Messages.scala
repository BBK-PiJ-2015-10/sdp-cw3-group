package application

import com.mildlyskilled.Color

sealed trait RayTracerMessage

case object Initialize extends RayTracerMessage

case object Finalize extends RayTracerMessage

case class SetPixel(pixels: IndexedSeq[(Int, Int, Color)]) extends RayTracerMessage

case class WorkUnit(pixels: IndexedSeq[(Int, Int)]) extends RayTracerMessage



