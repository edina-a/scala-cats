package catsnote

import cats.{Applicative, Traverse}
import org.scalatest.{Matchers, WordSpec}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

/**
  * Created by edinakim on 2016. 8. 16..
  */
class TraverseSpec extends WordSpec with Matchers {
  // sequence :
  // List[Future[String]] => Future[List[String]]
  // F[G[T]] --- sequence + map[T->R] ---> G[F[T]]

  "traverse" in {
    import cats.std.list.listInstance
    import cats.std.option._
    // List(Option(1), Option(2), Option(3)) => Option(List(1,2,3))
    import cats.syntax.traverse._
    println(List(Option(1), Option(2), Option(3)).traverse(identity))
  }

  import scala.concurrent.ExecutionContext.Implicits.global
  val userIds = List(1, 2, 3, 4)
  case class User(id: Int, name: String)
  def getUser(id: Int): Future[User] = Future { User(id, s"name-$id") }
  val expected = List(User(1, "name-1"), User(2, "name-2"), User(3, "name-3"), User(4, "name-4"))

  // 1. userIds 를 가지고 getUser함수를 호출
  val users: List[Future[User]] = userIds.map(x=> getUser(x))
  users.map(f => f.map(user => println(user)))

  import cats.syntax.all._
  import cats.std.all._
  // 2. List[Future[User]] => Future[List[User]] 로
  //val traverse: Future[List[User]] = users.traverse(x=>x)
  // 단순히 List[Future 를  Future[List 로 바꾸는 역할은 sequence :: Applicative[Future].sequence(users)
  // 바꾸면서 특정 액션을 태우려면 traverse :: Traverse[List].traverse(userIds)(getUser)

  // map + sequence
  val listFutureUser = userIds.map(getUser)
  val futureListUser = Applicative[Future].sequence(listFutureUser)

  // traverse
  Traverse[List].traverse(userIds)(getUser)
  Await.result(futureListUser, Duration.Inf) shouldBe expected

}
