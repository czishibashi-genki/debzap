package dao

import dao.Query.{FavoriteQuery, SiteQuery}
import org.joda.time.DateTime
import slick.driver.MySQLDriver.api._
import com.github.tototoshi.slick.MySQLJodaSupport._

/**
 * Created by a13887 on 2016/10/29.
 */
case class FavoriteDto(
                       uuid: String,
                       siteId: Int,
                       createdDate: DateTime = new DateTime)

class Favorites(tag: Tag) extends Table[FavoriteDto](tag, "favorite") {
  def uuid = column[String]("uuid", O.PrimaryKey)
  def siteId = column[Int]("site_id")
  def createdDate = column[DateTime]("created_date")
  def * = (uuid, siteId, createdDate) <>(FavoriteDto.tupled, FavoriteDto.unapply)
}

object FavoriteDao extends TableQuery(new Favorites(_)) with BaseDao {

  /**
   * 記事の保存
   * @param dto
   * @return 新規作成されたID
   */
  def insert(dto: FavoriteDto) = {
    db.run(
      this += (dto)
    )
  }

  def upsert(dto: FavoriteDto) = {
    db.run(
      this.insertOrUpdate(dto)
    )
  }

  def remove(uuid: String) = {
    db.run(
      this.filter(_.uuid === uuid).delete
    )
  }

  def find(query: FavoriteQuery) = {
    db.run(
      this.filter{table =>
        val repTrue: Rep[Boolean] = true
        val q = if (query.uuid.isDefined) table.uuid === query.uuid.get else repTrue
        val q2 = if (query.siteId.isDefined) table.siteId === query.siteId.get else q
        q2
      }.result
    )
  }
}

