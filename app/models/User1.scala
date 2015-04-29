package models

import controllers.BearerTokenGenerator
import org.joda.time.DateTime
import slick.lifted.{Rep, ProvenShape, Tag, TableQuery}
import slick.driver.MySQLDriver.simple._
import com.github.tototoshi.slick.MySQLJodaSupport._
import securesocial.core._

/**
 * Created by m.cherkasov on 22.04.15.
 */


case class User1(id: Option[Long] = None,
                email:String,
                password:String, accessToken: Option[String] = None,
                created: DateTime = new DateTime()) {
//  def checkPassword(password: String): Boolean = this.password == password
}

class UsersTable(tag: Tag) extends Table[User1](tag, "users") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def email = column[String]("email")
  def password = column[String]("password")
  def accessToken = column[String]("accessToken")
  def created = column[DateTime]("created")

  def * : ProvenShape[User1] = {
    (id.?, email, password, accessToken.?, created).shaped <> (User1.tupled, User1.unapply)
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


object Users1 extends TableQuery[UsersTable](new UsersTable(_)) with WithDefaultSession {
//object Tables extends WithDefaultSession {
//
//  var users = new TableQuery[UsersTable](new UsersTable(_)) {

    def findByToken(token: String): Option[User1] = withSession {
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

    def findByEmailAndPassword(email: String, password: String): Option[User1] = withSession {
      implicit session =>
        val q = for {
          user <- this
          if user.email === email && user.password === password
        } yield user

        q.firstOption
    }

    def findByEmail(email: String): Option[User1] = withSession {
      implicit session =>
        val q = for {
          user <- this
          if user.email === email
        } yield user

        q.firstOption
    }

  def login(email: String, password: String): Option[User1] = withSession {
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

    def register(email: String, password: String): Option[User1] = withSession {
      implicit session =>
        findByEmail(email) match {
          case None =>
            println("Register new user")
            val user = User1(
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

