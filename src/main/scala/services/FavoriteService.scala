package services

import dao.{FavoriteDao, FavoriteDto}
import dao.Query.FavoriteQuery

/**
  * Created by a13887 on 2016/11/16.
  */
object FavoriteService {

  def save(uuid: String, siteId: Int) = {
    FavoriteDao.insert(
      FavoriteDto(uuid, siteId)
    )
  }
}
