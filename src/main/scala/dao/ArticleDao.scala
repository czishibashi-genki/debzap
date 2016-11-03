package dao

import dao.Query.{SortingColumn, ArticleQuery}
import org.joda.time.DateTime
import slick.driver.MySQLDriver.api._
import com.github.tototoshi.slick.MySQLJodaSupport._

/**
 * Created by a13887 on 2016/10/29.
 */
case class ArticleDto(
                       id: Option[Int],
                       title: String,
                       link: String,
                       favCount: Int,
                       siteId: Int,
                       createdDate: Option[DateTime])

class Articles(tag: Tag) extends Table[ArticleDto](tag, "article") {
  def id = column[Option[Int]]("id", O.PrimaryKey, O.AutoInc)

  def title = column[String]("title")

  def link = column[String]("link")

  def favCount = column[Int]("fav_count")

  def siteId = column[Int]("site_id")

  def createdDate = column[Option[DateTime]]("created_date")

  def * = (id, title, link, favCount, siteId, createdDate) <>(ArticleDto.tupled, ArticleDto.unapply)
}

object ArticleDao extends TableQuery(new Articles(_)) with BaseDao {

  /**
   * 記事の保存
   * @param dto
   * @return 新規作成されたID
   */
  def insert(dto: ArticleDto) = {
    db.run(
      this.returning(this.map(_.id)) += (dto)
    )
  }

  def upsert(dto: ArticleDto) = {
    db.run(
      this.insertOrUpdate(dto)
    )
  }

  def remove(id: Int) = {
    db.run(
      this.filter(_.id === id).delete
    )
  }

  def find(query: ArticleQuery) = {
    db.run{
      val act1 = this.filter{table =>
        val repTrue: Rep[Option[Boolean]] = Some(true)
        val q = if (query.id.isDefined) (table.id === query.id) else repTrue
        q
      }
      val act2 = query.sortby match {
        case SortingColumn.New.column => act1.sortBy(_.createdDate.desc)
        case SortingColumn.Fav.column => act1.sortBy(_.favCount.desc)
        case _ => act1
      }

      val offset = query.offset.fold(0)(o => o)
      val count = query.count.fold(1000)(c => c) // デフォルト1000件
      act2.drop(offset).take(count).result
    }
  }
}
