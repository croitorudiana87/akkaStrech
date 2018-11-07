package models

/**
  * Created by diana on 06-Nov-18.
  */

import scala.language.postfixOps

case class User(
                 id: Option[Long],
                 username: String,
                 password: String
               )

object User {

  import play.api.libs.functional.syntax._
  import play.api.libs.json._

  implicit val UserFromJson: Reads[User] = (
    (__ \ "id").readNullable[Long] ~
      (__ \ "username").read(Reads.email) ~
      (__ \ "password").read[String]
    ) (User.apply _)

  implicit val UserToJson: Writes[User] = (
    (__ \ "id").writeNullable[Long] ~
      (__ \ "username").write[String] ~
      (__ \ "password").writeNullable[String]
    ) (user => (
    user.id,
    user.username,
    None
  ))


  import anorm.SqlParser._
  import anorm.{~, _}
  import play.api.db.DB

  implicit val app: play.api.Application = play.api.Play.current

  val parser = {
    get[Option[Long]]("id") ~
      get[String]("username") ~
      get[String]("password")
  } map {
    case id ~ username ~ password  =>
      User(id, username, password)
  }

  val multiParser: ResultSetParser[Seq[User]] = parser *

  def findOneById(id: Long): Option[User] = DB.withConnection { implicit connection =>
    SQL("SELECT * FROM usertable WHERE id = {id}").on('id -> id).as(parser.singleOpt)
  }

  def findByUsernameAndPassword(username: String, password: String): Option[User] = DB.withConnection {
    implicit connection =>
      SQL("SELECT * FROM usertable WHERE username = {username} AND password = {password}").on(
        'username -> username, 'password -> password
      ).as(parser.singleOpt)
  }

}
