package controllers

import org.joda.time.DateTime

/**
 * Created by a13887 on 2016/10/30.
 */
case class ArticleSiteOutput(id: Int, name: String)
case class ArticleOutput(
                        id: Int,
                        title: String,
                        link: String,
                        favCount: Int,
                        createdDate: String,
                        site: ArticleSiteOutput)
