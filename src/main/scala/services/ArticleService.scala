package services

import controllers.{ArticleSiteOutput, ArticleOutput}
import dao.{SiteDao, ArticleDao}
import dao.Query.{SortingColumn, SiteQuery, ArticleQuery}

import scala.concurrent.{Future, ExecutionContextExecutor}


/**
 * Created by a13887 on 2016/10/29.
 */
object ArticleService {

  def find(offsetOpt: Option[Int], countOpt: Option[Int], sortbyOpt: Option[String])(implicit context: ExecutionContextExecutor): Future[Seq[ArticleOutput]] = {
    val aq = ArticleQuery(offset = offsetOpt, count = countOpt, sortby = sortbyOpt.getOrElse(SortingColumn.New.query)).validate()
    val articlesFt = ArticleDao.find(aq)
    val sitesFt = articlesFt.flatMap{ articleSeq =>
      val articleSiteIds = articleSeq.map(_.siteId)
      val sq = SiteQuery(ids = Some(articleSiteIds))
      SiteDao.find(sq)
    }
    for{
      articles <- articlesFt
      sites <- sitesFt
    }yield{
      articles.map{article =>
        val site = sites.find(_.id == Some(article.siteId)).get
        ArticleOutput(
          id = article.id.get,
          title = article.title,
          link = article.link,
          favCount = article.favCount,
          createdDate = article.createdDate.get.toString,
          ArticleSiteOutput(site.id.get, site.name)
        )
      }
    }
  }
}
