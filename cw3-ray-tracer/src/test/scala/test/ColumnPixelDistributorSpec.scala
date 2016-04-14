package test

import application.ColumnPixelSequencer
import com.mildlyskilled.harness.DefaultHarness
import com.mildlyskilled._
import application.RowPixelSequencer

//import application.PixelDistributor

class ColumnPixelDistributorSpec extends DefaultHarness {

  val cols = 800
  val rows = 600

  behavior of "ColumnPixelDistributorSpec"

  it should s"be of size equal to ${rows * cols}" in {
    val c = ColumnPixelSequencer.sequence(rows, cols)
    c.size should be(rows * cols)
  }

  it should s"not have any duplicates" in {
    val c = ColumnPixelSequencer.sequence(rows, cols)
    c.toSet.size should be(rows * cols)
  }
  
  

  
  
  

}