package dao

import dao.Query.ArticleQuery
import org.joda.time.DateTime
import org.scalatest.{FlatSpec, Matchers}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
 * Created by a13887 on 2016/10/30.
 */
class ArticleDaoSpec extends FlatSpec with Matchers {
  "ArticleDao" should "insert" in {
    val dto = ArticleDto(None, "title", "http://example.com", 1, 1, Some(new DateTime()))
    val newId = Await.result(ArticleDao.insert(dto), Duration.Inf)
    val q = ArticleQuery(newId)
    print(Await.result(ArticleDao.find(q), Duration.Inf))
  }
}
