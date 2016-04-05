
package worker

import akka.actor.Actor
import akka.actor.actorRef2Scala

class Counter extends Actor {
  
  import Counter._
  
  var count: Int = 0
  
  def receive = {
    
    case Increment => 
      count += 1
    case Decrement =>
      count -=1
    case GetCount =>
      sender ! count
    
  }
    
}

object Counter {
  case object Increment
  case object Decrement
  case object GetCount
}