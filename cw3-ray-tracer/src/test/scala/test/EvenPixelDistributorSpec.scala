package test

import application.ColumnPixelSequencer
import com.mildlyskilled.harness.DefaultHarness
import com.mildlyskilled._
import application.BlockPixelDistributor
import application.RowPixelSequencer
import application.EvenPixelDistributor



class EvenPixelDistributorSpec extends DefaultHarness {

  val cols = 8
  val rows = 6
  behavior of "EvenPixelDistributorSpec"

  it should s"return the correct number of blocks" in {
    val numOfBlocks = 9
    val pixels = RowPixelSequencer.sequence(rows, cols)
    val blocks = EvenPixelDistributor.distribute(numOfBlocks, pixels)
    blocks.size should be (numOfBlocks)
  }
  
  
  it should s"return the correct number of pixels" in {
    val numOfBlocks = 9
    val pixels = RowPixelSequencer.sequence(rows, cols)
    val blocks = EvenPixelDistributor.distribute(numOfBlocks, pixels)
    val numOfPixels = blocks.foldLeft(0){(m,n) => m + n.size}
    numOfPixels should be (cols * rows)
  }

    it should s"return unique pixels" in {
    val numOfBlocks = 9
    val pixels = RowPixelSequencer.sequence(rows, cols)
    val blocks = EvenPixelDistributor.distribute(numOfBlocks, pixels)
    blocks.flatten.distinct.size should be (cols * rows)
  }
  
  

}