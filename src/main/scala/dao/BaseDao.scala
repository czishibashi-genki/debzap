package dao

import slick.driver.MySQLDriver.api._

/**
 * Created by a13887 on 2016/10/30.
 */
trait BaseDao {
  val db = Database.forConfig("db")
}
