/**
 * Created by a13887 on 2016/10/29.
 */

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.stream.ActorMaterializer
import akka.http.scaladsl.server.{Directives, Route}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes
import com.typesafe.scalalogging.Logger
import controllers.{ApiPagingOutput, ArticleOutput, ArticleSiteOutput}
import controllers.OutputFormatter._
import services.{ArticleService, FavoriteService}
import spray.json.DefaultJsonProtocol
import util.LoggerSupport

import scala.io.StdIn
import scala.concurrent.Future

case class FavoriteInput(uuid: String)
case class FavoriteOutput(success: Boolean = false)

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val ArticleSiteOutputFormat = jsonFormat2(ArticleSiteOutput)
  implicit val ArticleOutputFormat = jsonFormat6(ArticleOutput)
  implicit val FavoriteInputFormat = jsonFormat1(FavoriteInput)
  implicit val FavoriteOutputFormat = jsonFormat1(FavoriteOutput)
  implicit def ApiPagintOutputFormat = jsonFormat2(ApiPagingOutput[ArticleOutput])
}

object WebServer extends Directives with JsonSupport with LoggerSupport{

  def main(args: Array[String]) {
    // needed to run the route
    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()

    // needed for the future map/flatmap in the end
    implicit val executionContext = system.dispatcher

    val route: Route =
      get {
        pathPrefix("articles") {
          parameters('offset.as[Int].?, 'count.as[Int].?, 'sortby.as[String].?) { (offsetOpt, countOpt, sortbyOpt) =>
            logger.info(s"get articles. offset = $offsetOpt, count = $countOpt, sortby = $sortbyOpt")
            val articles: Future[Seq[ArticleOutput]] = ArticleService.find(offsetOpt, countOpt, sortbyOpt)
            onSuccess(articles) {
              case articles: Seq[ArticleOutput] => complete (articles.toRes)
              case _ => complete(StatusCodes.NotFound)
            }
          }
        }
      } ~
        post {
          pathPrefix("articles" / IntNumber / "favorite") { siteId =>
            entity(as[FavoriteInput]) { input =>
              logger.info(s"post favorite site_id = $siteId.")
              val res: Future[Int] = FavoriteService.save(input.uuid, siteId)
                onSuccess(res) {
                  case 1 => complete(FavoriteOutput(true))
                  case _ => complete(FavoriteOutput(false))
                }
            }
          }
        }

    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)
    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
//    bindingFuture
//      .flatMap(_.unbind()) // trigger unbinding from the port
//      .onComplete(_ ⇒ system.terminate()) // and shutdown when done
  }
}
