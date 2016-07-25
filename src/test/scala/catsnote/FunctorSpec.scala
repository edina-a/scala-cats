package catsnote

import cats._
import org.scalatest.{Matchers, WordSpec}

/**
  * Created by edina on 2016. 7. 21..
  */
class FunctorSpec extends WordSpec with Matchers {
  "functor group test" should {
    implicit val optionFunctor: Functor[Option] = new Functor[Option] {
      def map[A, B](fa: Option[A])(f: A => B) = fa map f
    }

    implicit val listFunctor: Functor[List] = new Functor[List] {
      def map[A, B](fa: List[A])(f: A => B) = fa map f
    }

    "functor" in {
      Functor[Option].map(Option("Hello"))(_.length) should be(Some(5))
      Functor[Option].map(None: Option[String])(_.length) should be(None)
    }

    "lift" in {
      val lenOption: Option[String] ⇒ Option[Int] = Functor[Option].lift(_.length)
      lenOption(Some("Hello")) should be(Some(5))
    }

    "fproduct" in {
      val source = List("Cats", "is", "awesome")
      val product = Functor[List].fproduct(source)(_.length).toMap

      product.get("Cats").getOrElse(0) should be(4)
      product.get("is").getOrElse(0) should be(2)
      product.get("awesome").getOrElse(0) should be(7)
    }

    "compose" in {
      val listOpt = Functor[List] compose Functor[Option]
      listOpt.map(List(Some(1), None, Some(3)))(_ + 1) should be(List(Some(2), None, Some(4)))
    }

    // instance 를 만드려면 implicit
    "my first option functor instance" in {
//      implicit val optionInstance = new Functor[Option] {
//        override def map[A, B](fa: Option[A])(f: (A) => B): Option[B] = {
//          fa match {
//            case Some(x) => Some(f(x))
//            case None => None
//          }
//        }
//      }
//
//      implicit val optionInstance2 = new Functor[Option] {
//        override def map[A, B](fa: Option[A])(f: (A) => B): Option[B] = fa map f
//      }
//
//      println(Functor[Option].map(Some(1))(_ + 1))
    }

    "my foo instance" in {
      case class Foo[T](a:T)

      import cats.syntax.functor._
      val foo = Foo[Int](1)

      implicit val fooFunctor = new Functor[Foo] {
        override def map[A, B](fa: Foo[A])(f: (A) => B): Foo[B] = Foo(f(fa.a))
      }

      println(foo.map(_ + 10))
      foo.map(_ + 10) shouldBe Functor[Foo].map(foo)(_+10)


    }

  }
}
