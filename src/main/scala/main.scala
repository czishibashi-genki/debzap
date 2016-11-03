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
import controllers.{ApiPagingOutput, ArticleSiteOutput, ArticleOutput}
import controllers.OutputFormatter._
import services.ArticleService
import spray.json.DefaultJsonProtocol
import scala.io.StdIn
import scala.concurrent.Future

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val ArticleSiteOutputFormat = jsonFormat2(ArticleSiteOutput)
  implicit val ArticleOutputFormat = jsonFormat6(ArticleOutput)
  implicit def ApiPagintOutputFormat = jsonFormat2(ApiPagingOutput[ArticleOutput])
}

object WebServer extends Directives with JsonSupport{

  def main(args: Array[String]) {
    // needed to run the route
    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()

    // needed for the future map/flatmap in the end
    implicit val executionContext = system.dispatcher

    val route: Route =
      get {
        pathPrefix("articles") {
          parameters('offet.as[Int].?, 'count.as[Int].?) { (offsetOpt, countOpt) =>
            val articles: Future[Seq[ArticleOutput]] = ArticleService.find(offsetOpt, countOpt)
            onSuccess(articles) {
              case articles: Seq[ArticleOutput] => complete (articles.toRes)
              case _ => complete(StatusCodes.NotFound)
            }
          }
        }
      }
    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)
    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ ⇒ system.terminate()) // and shutdown when done
  }
}
