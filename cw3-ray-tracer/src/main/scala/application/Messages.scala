package application

sealed trait RayTracerMessage

case object Initialize extends RayTracerMessage

case object Finalize extends RayTracerMessage


