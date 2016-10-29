package dao

import dao.Query.SiteQuery
import org.scalatest.{Matchers, FlatSpec}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
 * Created by a13887 on 2016/10/30.
 */
class SiteDaoSpec extends FlatSpec with Matchers {
  "SiteDao" should "insert" in {
    val dto = SiteDto(None, "http://example.com", "hogeまとめ")
    val newId = Await.result(SiteDao.insert(dto), Duration.Inf)
    val q = SiteQuery(newId)
    print(Await.result(SiteDao.find(q), Duration.Inf))
  }
}
