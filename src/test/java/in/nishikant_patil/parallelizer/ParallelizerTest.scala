package in.nishikant_patil.parallelizer

import java.util
import java.util.Arrays

/**
  * Test for parallelizer
  */
object ParallelizerTest {
  def main(args: Array[String]): Unit = {
    println(Parallelizer.parallelize[Int, Int, Int](Arrays.asList(1, 2, 3, 4, 5),
      i => {
        val j: util.List[Int] = new util.ArrayList[Int]()
        val it = i.iterator()
        while (it.hasNext) {
          val value = it.next()
          j.add(value * value)
        }
        j
      },
      x => {
        println(x)
        -123
      }))
  }
}
