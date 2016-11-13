package dao.http

import java.text.SimpleDateFormat
import java.util.Locale

import org.joda.time.format.DateTimeFormat

import scala.xml.XML
import org.joda.time.DateTime
import util.LoggerSupport

import scalaj.http.Http

/**
 * Created by a13887 on 2016/11/06.
 */
case class KintoreBlogDto(
                              title: String,
                              link: String,
                              createdDate: DateTime
                              )

object KintoreBlogDao extends LoggerSupport {
  val BASE_URL = "http://kintoreblog.com"
  val URL = s"${BASE_URL}/feed/";

  def find() = {
    val res = Http(URL).asString
    val xmlTree = XML.loadString(res.body)
    xmlTree.\\("item").map{ item =>
      val title = item.\("title").text
      val link = item.\("link").text
      val format = new SimpleDateFormat("EEE, dd MMMM yyyy hh:mm:ss", Locale.ENGLISH);
      val jDate = format.parse(item.\("pubDate").text)
      val createdDate = new DateTime(jDate)
      KintoreBlogDto(title, link, createdDate)
    }
  }
}
