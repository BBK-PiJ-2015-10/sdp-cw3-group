package application
import scala.Vector

import org.apache.commons.math3.distribution.NormalDistribution
/*
 * Provide a method of distributing a range of pixel values into a number of discrete units
 */
trait PixelDistributor {

  /*
   * Return a number of sequences from a supplied range of pixel co-ordinates
   */
  def distribute(units: Int, pixels: IndexedSeq[(Int, Int)]): IndexedSeq[IndexedSeq[(Int, Int)]]

}
/*
 * Provide a method to transform matrix dimensions into a sequence of pixels co-ordinates
 */
trait PixelSequencer {
  /*
   * Return a sequence of pixel co-ordinates that represent the given matrix dimensions
   */
  def sequence(rows: Int, cols: Int): IndexedSeq[(Int, Int)]

}
/*
 * Split a sequence of pixel co-ordinates into a sequence of evenly sized contiguous blocks
 */
object BlockPixelDistributor extends PixelDistributor {
  /*
   * Split the sequence of pixel co-ordinates into a sequence of evenly sized blocks with any underflow being accommodated in the final block
   */
  override def distribute(units: Int, pixels: IndexedSeq[(Int, Int)]): IndexedSeq[IndexedSeq[(Int, Int)]] = {

    val blockSize = Math.ceil(Math.max(pixels.size.toDouble / units, 1)).toInt
    (0 to units - 1).map(x => pixels.slice(x * blockSize, x * blockSize + blockSize))
  }
}
/*
 * Split a sequence of pixel co-ordinates into a sequence of blocks distributed evenly across the range
 */
object EvenPixelDistributor extends PixelDistributor {
  /*
   * Split the sequence of pixel co-ordinates into a sequence of evenly sized blocks with pixels distributed uniformly across the range. Any underflow
   * is accommodated in the final block 
   */
  override def distribute(units: Int, pixels: IndexedSeq[(Int, Int)]): IndexedSeq[IndexedSeq[(Int, Int)]] = {

    val outOfBounds = (-1, -1)
    val blockSize = Math.ceil(Math.max(pixels.size.toDouble / units, 1)).toInt
    (0 to units - 1)
      .map(offset => (0 to blockSize - 1)
        .map(pixel => if (offset + pixel * units < pixels.size) pixels(offset + pixel * units) else outOfBounds).filter(_ != outOfBounds))
  }

}
/*
 * Split a sequence of pixel co-ordinates into a sequence of blocks normally distributed across the range
 */
object NormalPixelDistributor {

  val d = new NormalDistribution()
  /*
   * Split the sequence of pixel co-ordinates into a sequence of blocks with pixels inversely distributed normally across the range. Any adjustments
   * are accommodated in the first block 
   */
  def distribute(units: Int, pixels: IndexedSeq[(Int, Int)]): IndexedSeq[IndexedSeq[(Int, Int)]] = {

    val quantiles = units + 2
    val maxZ = Math.abs(d.inverseCumulativeProbability(1.toDouble / units))

    // Generate normal distribution of pixel blocks
    var blockSizeDistribution = (2 until quantiles).map(u => mapQuantileToArea(u, quantiles, maxZ, pixels.size))

    // Adjust first block size for any rounding errors
    var totalBlockSizes = blockSizeDistribution.sum
    if (pixels.size != totalBlockSizes) {

      val adjustment = pixels.size - totalBlockSizes
      blockSizeDistribution = blockSizeDistribution updated (0, blockSizeDistribution(0) + adjustment)
    }

    // Pixels mapped to normalised block sizes
    var distributedPixels = blockSizeDistribution.foldLeft((0, Vector[IndexedSeq[(Int, Int)]]())) { (m, n) => (m._1 + n, m._2 :+ pixels.slice(m._1, m._1 + n)) }

    distributedPixels._2
  }

  def mapQuantileToArea(quantile: Int, quantiles: Int, maxZ: Double, area: Int): Int = {

    var r: Int = (Math.abs(Math.abs(d.inverseCumulativeProbability((quantile - 1).toDouble / quantiles))
      - Math.abs(d.inverseCumulativeProbability(quantile.toDouble / quantiles))) / (2 * maxZ) * area).toInt

    if (r < 1) 1 else r
  }
}

object RowPixelSequencer extends PixelSequencer {

  override def sequence(rows: Int, cols: Int): IndexedSeq[(Int, Int)] = {

    (0 to rows - 1).flatMap(x => (0 to cols - 1).map { y => (x, y) })
  }
}

object ColumnPixelSequencer extends PixelSequencer {

  override def sequence(rows: Int, cols: Int): IndexedSeq[(Int, Int)] = {

    (0 to cols - 1).flatMap(y => (0 to rows - 1).map { x => (x, y) })
  }
}

