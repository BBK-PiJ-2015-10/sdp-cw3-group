package com.mildlyskilled.actor

import akka.actor.{ActorLogging, Actor}
import com.mildlyskilled._
import com.mildlyskilled.protocol._

/**
  * Created by kwabena on 29/02/2016.
  */

//Need to add ActorLogging and do something with it.
//Below method works, need to brainstorm how to make it more efficient and to see how
//the scheduler can split the work.

  class TracerActor(scene: Scene) extends Actor {
  
  
  def receive = {
    
    case WorkUnit(i) => {
      
      for (x <- 0 until scene.t.Width) {
      
        var colour = Color.black
        
        val ss = scene.t.AntiAliasingFactor
        
        for (dx <- 0 until ss) {
          for (dy <- 0 until ss) {
            
            val dir = Vector(
              (scene.sinf * 2 * ((x + dx.toFloat / ss) / scene.t.Width - .5)).toFloat,
              (scene.sinf * 2 * (scene.t.Height.toFloat / scene.t.Width) * (.5 - (i + dy.toFloat / ss) / scene.t.Height)).toFloat,
              scene.cosf.toFloat).normalized
            
            val c = scene.trace(Ray(scene.eye, dir)) / (ss * ss)
            colour += c  
             
          }
        }
        
        if (Vector(colour.r, colour.g, colour.b).norm < 1)
          scene.t.darkCount += 1
        if (Vector(colour.r, colour.g, colour.b).norm > 1)
          scene.t.lightCount += 1
         
        sender ! SetPixel(x,i,colour)
         
        
      }
            
    }  
    
  }
  
  

}
