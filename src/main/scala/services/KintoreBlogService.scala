package services

import dao.http.{KintoreBlogDao, KintoreChannelDao}
import dao.{ArticleDao, ArticleDto}
import util.LoggerSupport

import scala.concurrent.ExecutionContextExecutor

/**
 * Created by a13887 on 2016/11/13.
 */
object KintoreBlogService extends LoggerSupport {

  def importArticles(siteId: Int)(implicit cx: ExecutionContextExecutor) = {
    val fetchedArticles = KintoreBlogDao.find()

    //リンクURLで重複排除
    val links = fetchedArticles.map(_.link)
    ArticleDao.find(links).map{ existDtos =>
      val newLinks = links.diff(existDtos.map(_.link))
      val newArticles = fetchedArticles.filter(fa => newLinks.contains(fa.link))
      newArticles.map { dto =>
        val aDto = ArticleDto(None, dto.title, dto.link, 0, siteId, Some(dto.createdDate))
        ArticleDao.insert(aDto)
      }
    }

  }
}
