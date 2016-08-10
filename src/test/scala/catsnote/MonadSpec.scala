package catsnote

import cats.Monad
import org.scalatest.{Matchers, WordSpec}

/**
  * Created by edinakim on 2016. 8. 9..
  */
class MonadSpec extends WordSpec with Matchers {

  "foo monad instance" in {
    case class Foo[T](a: T)

    val fooInstance = new Monad[Foo]{
      override def pure[A](x: A): Foo[A] = Foo(x)

      override def flatMap[A, B](fa: Foo[A])(f: (A) => Foo[B]): Foo[B] = {
        f(fa.a)
      }
    }
  }

  "option monad instance" in {
    val optionInstance = new Monad[Option] {

      override def pure[A](x: A): Option[A] = Option(x)

      override def flatMap[A, B](fa: Option[A])(f: (A) => Option[B]): Option[B] = {
        // fa 에서 a를 끄집어 내서 f에 적용시킨다 f(fa.a)
        fa match {
          case Some(x) => f(x)
          case None => None
        }
      }
    }
  }

  // monad를 쓰면 뭐가 좋아지나?
  // 예외사항에 대한 if문 없이 flatmap 으로 엮어도 에러 핸들링이 가능

}
