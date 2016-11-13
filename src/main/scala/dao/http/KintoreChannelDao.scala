package dao.http


import scala.xml.XML
import org.joda.time.DateTime
import util.LoggerSupport

import scalaj.http.Http

/**
 * Created by a13887 on 2016/11/06.
 */
case class KintoreChannelDto(
                              title: String,
                              link: String,
                              createdDate: DateTime
                              )

object KintoreChannelDao extends LoggerSupport {
  val BASE_URL = "http://kintore-channel.com"
  val URL = s"${BASE_URL}/index.rdf";

  def find() = {
    val res = Http(URL).asString
    val xmlTree = XML.loadString(res.body)
    xmlTree.\\("item").map{ item =>
      val title = item.\("title").text
      val link = item.\("link").text
      val createdDate = new DateTime(
        item.\("date").filter(_.prefix == "dc").text
      )
      KintoreChannelDto(title, link, createdDate)
    }
  }
}
