package controllers

/**
 * Created by a13887 on 2016/11/03.
 */
case class ApiPagingOutput[A](total: Int, values: Seq[A])

object OutputFormatter {
  implicit class ApiPagingOutputFormatter[A](values: Seq[A]) {
    def toRes = ApiPagingOutput(values.length, values)
  }
}
