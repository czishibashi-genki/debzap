package dao.Query

/**
 * Created by a13887 on 2016/10/30.
 */
case class ArticleQuery(
                         id: Option[Int] = None,
                         offset: Option[Int] = None,
                         count: Option[Int] = None)

