package dao.Query

/**
 * Created by a13887 on 2016/10/30.
 */

/**
 * クエリパラメータと検索用のカラムのマッピング
 */
abstract class SortingColumn(val query: String, val column: String)
object SortingColumn {
  val default = New.column
  val values = Seq(New, Fav)
  case object New extends SortingColumn("new", "created_date")
  case object Fav extends SortingColumn("fav", "fav")
}

case class ArticleQuery(
                         id: Option[Int] = None,
                         offset: Option[Int] = None,
                         count: Option[Int] = None,
                         sortby: String = SortingColumn.default) {
  def validate() = {
    this.copy(sortby =
      SortingColumn.values.find(_.query == sortby).map(_.column)
        .getOrElse(SortingColumn.default))
  }
}

