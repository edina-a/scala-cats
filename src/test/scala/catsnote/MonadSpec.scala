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

  "ifm" in {
    import cats.std.all._
    Monad[Option].ifM(Option(true))(Option("truthy"), Option("falsy")) should be(Some("truthy"))
    Monad[List].ifM(List(true, false, true))(List(1, 2), List(3, 4)) should be(List(1,2,3,4,1,2))

  }

//  "composition" in {
//    case class OptionT[F[_], A](value: F[Option[A]])
//
//    implicit def optionTMonad[F[_]](implicit F: Monad[F]) = {
//      new Monad[OptionT[F, ?]] {
//        def pure[A](a: A): OptionT[F, A] = OptionT(F.pure(Some(a)))
//        def flatMap[A, B](fa: OptionT[F, A])(f: A => OptionT[F, B]): OptionT[F, B] =
//          OptionT {
//            F.flatMap(fa.value) {
//              case None => F.pure(None)
//              case Some(a) => f(a).value
//            }
//          }
//      }
//    }
//
//    import cats.std.list._
//    println(optionTMonad[List].pure(42))
//  }
}
