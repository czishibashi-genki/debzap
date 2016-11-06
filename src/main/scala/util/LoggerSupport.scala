package util

import com.typesafe.scalalogging.Logger
import org.slf4j.LoggerFactory

/**
 * Created by a13887 on 2016/11/06.
 */
trait LoggerSupport {
  val logger = Logger(this.getClass)
  val batchLog = LoggerFactory.getLogger("BATCH_LOG")
}
