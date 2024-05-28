package repository

import javax.inject.{Inject, Singleton}
import models.User
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserRepository @Inject()(db: Database)(implicit ec: ExecutionContext) {
  private val Users = TableQuery[UsersTable]

  def all(): Future[Seq[User]] = db.run(Users.result)

  def findById(id: Long): Future[Option[User]] = db.run(Users.filter(_.id === id).result.headOption)

  def insert(user: User): Future[Unit] = db.run(Users += user).map(_ => ())

  def update(id: Long, user: User): Future[Int] = db.run(Users.filter(_.id === id).update(user))

  def delete(id: Long): Future[Int] = db.run(Users.filter(_.id === id).delete)

  private class UsersTable(tag: Tag) extends Table[User](tag, Some("playwithscala"), "users") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def age = column[Int]("age")

    def * = (id.?, name, age) <> ((User.apply _).tupled, User.unapply)
  }
}