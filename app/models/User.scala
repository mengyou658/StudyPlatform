package models

import controllers.BearerTokenGenerator
import org.joda.time.DateTime
import play.api.db.DB
import slick.lifted.{Rep, ProvenShape, Tag, TableQuery}
import slick.driver.MySQLDriver.simple._
import com.github.tototoshi.slick.MySQLJodaSupport._

/**
 * Created by m.cherkasov on 22.04.15.
 */


case class User(id: Option[Long] = None,
                email:String,
                password:String, accessToken: Option[String] = None,
                created: DateTime = new DateTime()) {
//  def checkPassword(password: String): Boolean = this.password == password
}

class UsersTable(tag: Tag) extends Table[User](tag, "users") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def email = column[String]("email")
  def password = column[String]("password")
  def accessToken = column[String]("accessToken")
  def created = column[DateTime]("created")

  def * : ProvenShape[User] = {
    (id.?, email, password, accessToken.?, created).shaped <> (User.tupled, User.unapply)
  }
}

trait WithDefaultSession {

  def withSession[T](block: (Session => T)) = {
    val a = _root_.play.api.Configuration.empty
    val conf = play.api.Play.maybeApplication.map(_.configuration).getOrElse(a)
    val databaseURL = conf.getString("db.default.url").get
    val databaseDriver = conf.getString("db.default.driver").get
    val databaseUser = conf.getString("db.default.user").getOrElse("")
    val databasePassword = conf.getString("db.default.password").getOrElse("")

    val database = Database.forURL(url = databaseURL,
      driver = databaseDriver,
      user = databaseUser,
      password = databasePassword)

    database withSession {
      session =>
        block(session)
    }
  }
}


case class Token(uuid: Long,
                 token: String,
                 created: DateTime = new DateTime(),
                 expirationTime: DateTime = new DateTime().plusDays(30)) {

}

//class TokenTable(tag: Tag) extends Table[Token](tag, "tokens") {
//  def uuid = column[Long]("id")
//  def token = column[String]("token")
//  def created = column[DateTime]("created")
//  def expirationTime = column[DateTime]("expirationTime")
//
//  def * : ProvenShape[User] = {
//    (uuid, token, created, expirationTime).shaped <> (Token.tupled, Token.unapply)
//  }
//}
//
//object Tokens extends TableQuery[TokenTable](new TokenTable(_)) with WithDefaultSession {
//
//  def findByUserId(uuid: Long): Option[Token] = withSession {
//    implicit session =>
//      val q = for {
//        token <- this
//        if token.uuid === uuid
//      } yield token
//
//      q.firstOption
//  }
//
//  def createForUser(user: User): Option[Token] = withSession {
//    implicit session =>
//      user.id match {
//        case Some(uuid) =>
//          findByUserId(uuid) match {
//            case Some(token) =>
//              Some(token)
//            case None =>
//              val token = Token(uuid,
//                BearerTokenGenerator.generateSHAToken(user.email),
//                new DateTime(), new DateTime().plusDays(30))
//              this.insert(token)
//              Some(token)
//          }
//        case None => None
//      }
//  }
//
//}

object Users extends TableQuery[UsersTable](new UsersTable(_)) with WithDefaultSession {
//object Tables extends WithDefaultSession {
//
//  var users = new TableQuery[UsersTable](new UsersTable(_)) {

    def findByToken(token: String): Option[User] = withSession {
      implicit session =>
        val q = for {
          user <- this
          if user.accessToken === token
        } yield user

        q.firstOption
    }

//    def findAll(): List[User] = withSession {
//      implicit session =>
//        val q = for {
//          user <- this
//        } yield user
//
//        q.list
//    }

    def findByEmailAndPassword(email: String, password: String): Option[User] = withSession {
      implicit session =>
        val q = for {
          user <- this
          if user.email === email && user.password === password
        } yield user

        q.firstOption
    }

    def findByEmail(email: String): Option[User] = withSession {
      implicit session =>
        val q = for {
          user <- this
          if user.email === email
        } yield user

        q.firstOption
    }

  def login(email: String, password: String): Option[User] = withSession {
    implicit session =>
      findByEmailAndPassword(email, password) match {
        case Some(u) =>
          val user = u.copy(accessToken = Some(BearerTokenGenerator.generateSHAToken(email)))

          this.insertOrUpdate(user)
          Some(user)
        case None =>
          None
      }
  }

    def register(email: String, password: String): Option[User] = withSession {
      implicit session =>
        findByEmail(email) match {
          case None =>
            println("Register new user")
            val user = User(
              email = email,
              password = password
            )

            this.insert(user)
            Some(user)

          //            Tokens.createForUser(user) match {
          //              case Some(t) =>
          //                user.accessToken = t
          //                BearerTokenGenerator.generateSHAToken(user.email)
          //                this.insert(user)
          //                Some(user)
          //              case None =>
          //                None
          //        }
          case Some(existingUser) =>
            println("User already exists")
            None
        }
    }
//    def save(user: User): User = withSession {
//      implicit session =>
//        findByEmail(user.email) match {
//          case None =>
//            println("Create new user")
//            this.insert(user)
//            user
//          case Some(existingUser) =>
//            println("Update existing user")
//
//            val userRow = for {
//              u <- this
//              if u.id === existingUser.id
//            } yield u
//
//            val updatedUser = user.copy(id = existingUser.id)
//            userRow.update(updatedUser)
//            user
//        }
//    }
//  }
}

