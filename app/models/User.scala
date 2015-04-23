package models

import org.joda.time.DateTime
import play.api.db.DB
import slick.lifted.{Rep, ProvenShape, Tag, TableQuery}
import slick.driver.MySQLDriver.simple._
import com.github.tototoshi.slick.MySQLJodaSupport._

/**
 * Created by m.cherkasov on 22.04.15.
 */

case class LoginForm(email:String,
                     password:String)

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


case class Token(uuid: Long, created:
                DateTime = new DateTime(),
                expirationTime: DateTime = new DateTime().plusDays(30)) {

}

object Tables extends WithDefaultSession {

  var tokens = new TableQuery[]()
  var users = new TableQuery[UsersTable](new UsersTable(_)) {

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

    def register(email: String, password: String): Option[User] = withSession {
      implicit session =>
              findByEmail(email) match {
                case None =>
                  println("Create new user")
                  val user = User(
                    email = email,
                    password = password
                  )

                  this.insert(user)

                  Some(user)
                case Some(existingUser) =>
                  println("Update existing user")

//                  val userRow = for {
//                    u <- this
//                    if u.id === existingUser.id
//                  } yield u
//
//                  val updatedUser = user.copy(id = existingUser.id)
//                  userRow.update(updatedUser)
//                  user
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
  }
}

