package in.nishikant_patil.parallelizer.helpers

import java.util
import java.util.concurrent.Callable

/**
  * Processor to processes the data set in chunks. The data list will be split into equal chunks and the operation will be
  * performed by multiple threads on these chunks. The operation should not have dependency between the data in different
  * chunks.
  */
class ChunkProcessor extends Processor {
  override def getCallables[U, T](dataSet: util.List[T], mapper: (util.List[T]) => util.List[U]): util.List[Callable[util.List[U]]] = {
    val callables: util.ArrayList[Callable[util.List[U]]] = new util.ArrayList[Callable[util.List[U]]]()
    for (i <- List.range(0, DEGREE_OF_PARALLELISM-1)) {
      val chunkSize: Int = Math.ceil(dataSet.size() * 1.0 / DEGREE_OF_PARALLELISM).asInstanceOf[Int]
      val startIndex: Int = i * chunkSize
      callables.add(new Callable[util.List[U]] {
        override def call(): util.List[U] = {
          mapper(dataSet.subList(startIndex, Math.min(dataSet.size(), startIndex + chunkSize)))
        }
      })
    }
    callables
  }
}
