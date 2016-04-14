package test

import application.ColumnPixelSequencer
import com.mildlyskilled.harness.DefaultHarness
import com.mildlyskilled._
import application.BlockPixelDistributor
import application.RowPixelSequencer



class BlockPixelDistributorSpec extends DefaultHarness {

  val cols = 800
  val rows = 600

  behavior of "RowPixelDistributorSpec"

  it should s"return the correct number of blocks" in {
    val numOfBlocks = 9
    val pixels = RowPixelSequencer.sequence(rows, cols)
    val blocks = BlockPixelDistributor.distribute(numOfBlocks, pixels)
    blocks.size should be (numOfBlocks)
  }
  
  
  it should s"return the correct number of pixels" in {
    val numOfBlocks = 9
    val pixels = RowPixelSequencer.sequence(rows, cols)
    val blocks = BlockPixelDistributor.distribute(numOfBlocks, pixels)
    val numOfPixels = blocks.foldLeft(0){(m,n) => m + n.size}
    numOfPixels should be (cols * rows)
  }
  
  
  

}