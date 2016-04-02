package in.nishikant_patil.parallelizer.helpers

import java.util
import scala.collection.JavaConversions._
import java.util.concurrent.{Callable, ExecutorService, Executors, Future}

/**
  * Created by Nishikant on 4/2/2016.
  */
abstract class Processor {
  val DEGREE_OF_PARALLELISM: Int = 4

  val executorService: ExecutorService = Executors.newFixedThreadPool(DEGREE_OF_PARALLELISM)

  def getCallables[U, T](dataSet: util.List[T], mapper: util.List[T] => util.List[U]): util.List[Callable[util.List[U]]]

  def collectProcessedData[U](futures: util.List[Future[util.List[U]]]): util.List[util.List[U]] = {
    val data: util.List[util.List[U]] = new util.ArrayList[util.List[U]]()
    for (future: Future[util.List[U]] <- futures) {
      data add future.get()
    }
    data
  }

  def process[T, U, V](dataSet: util.List[T], mapper: util.List[T] => util.List[U], reducer: util.List[util.List[U]] => V): V = {
    try {
      val futures = executorService.invokeAll(getCallables(dataSet, mapper))
      reducer(collectProcessedData(futures))
    } finally {
      executorService.shutdown()
    }
  }
}
