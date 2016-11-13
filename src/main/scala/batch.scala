import akka.actor.ActorSystem
import services.{KintoreSokuhouService, KintoreBlogService, KintoreChannelService}
import util.LoggerSupport

object CrawlingJob extends LoggerSupport {

  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem()
    implicit val executionContext = system.dispatcher

    val KIN_CHA_ID = 1
    val KIN_BLO_ID = 2
    val KIN_SOK_ID = 3

    logger.info("start clawling job ...")
    KintoreChannelService.importArticles(KIN_CHA_ID)
    KintoreBlogService.importArticles(KIN_BLO_ID)
    KintoreSokuhouService.importArticles(KIN_SOK_ID)
    logger.info("finished clawling article.")
    system.terminate()
  }
}