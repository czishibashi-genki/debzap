package dao

import dao.Query.SiteQuery
import slick.driver.MySQLDriver.api._

/**
 * Created by a13887 on 2016/10/29.
 */
case class SiteDto(
                       id: Option[Int],
                       baseUrl: String,
                       name: String)

class Sites(tag: Tag) extends Table[SiteDto](tag, "site") {
  def id = column[Option[Int]]("id", O.PrimaryKey, O.AutoInc)
  def baseUrl = column[String]("base_url")
  def name = column[String]("name")
  def * = (id, baseUrl, name) <>(SiteDto.tupled, SiteDto.unapply)
}

object SiteDao extends TableQuery(new Sites(_)) with BaseDao {

  /**
   * 記事の保存
   * @param dto
   * @return 新規作成されたID
   */
  def insert(dto: SiteDto) = {
    db.run(
      this.returning(this.map(_.id)) += (dto)
    )
  }

  def upsert(dto: SiteDto) = {
    db.run(
      this.insertOrUpdate(dto)
    )
  }

  def remove(id: Int) = {
    db.run(
      this.filter(_.id === id).delete
    )
  }

  def find(query: SiteQuery) = {
    db.run(
      this.filter{table =>
        val repTrue: Rep[Option[Boolean]] = Some(true)
        val q = if (query.id.isDefined) table.id === query.id else repTrue
        val q2 = if (query.ids.isDefined) table.id.inSetBind(query.ids.get) else q
        q2
      }.result
    )
  }
}

