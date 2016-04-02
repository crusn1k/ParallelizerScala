package in.nishikant_patil.parallelizer

import java.util

import in.nishikant_patil.parallelizer.helpers.ChunkProcessor

/**
  * API for performing tasks on a data set over multiple threads.
  */
object Parallelizer {
  def parallelize[T, U, V](dataSet: util.List[T], mapper: util.List[T] => util.List[U], reducer: util.List[util.List[U]] => V): V = {
    new ChunkProcessor().process(dataSet, mapper, reducer)
  }
}
