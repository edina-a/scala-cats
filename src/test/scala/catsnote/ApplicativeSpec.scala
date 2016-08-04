package catsnote

import cats.{Applicative, Apply}
import org.scalatest.{Matchers, WordSpec}

/**
  * Created by edinakim on 2016. 8. 4..
  */
class ApplicativeSpec  extends WordSpec with Matchers {
  "applicative" should {
    "test" in {
      import cats.std.all._
      Applicative[Option].pure(1) should be(Option(1))
      Applicative[List].pure(1) should be(List(1))
    }

    // F[A] => F[B]
    val optionInt = Option[Int](1)
    val toStr:Int => String = (a:Int) => a.toString
    val toStr2: Option[Int=>String] = Option((a:Int)=>a.toString)
    val toStr3: Int => Option[String] = (a:Int)=> Option(a.toString)

    val toStr4: Int => Option[String] = {
      (a:Int) => {
        if(a<10) {
          Some(a.toString)
        }
        else {
          None
        }
      }
    }

    val a: Option[String] = optionInt.map(toStr)
    val b: Option[Option[String]] = optionInt.map(toStr4)
    val c: Option[String] = b.flatten
    val d: Option[String] = optionInt.flatMap(toStr4)

    import cats.std.all._
    val optionStr2 = Apply[Option].ap(toStr2)(optionInt)

    // Functor.map(A=>B)(fa: F[A]) : F[B]
    // Apply.ap(F[A=>B])(fa: F[A]) : F[B]
    // Monad.flatMap(A=>F[B])(fa: F[A]) : F[B]

    "applicative instance" in {
      case class Foo[T](a:T)

      implicit val fooApplicativeInstance = new Applicative[Foo] {
        override def pure[A](x: A): Foo[A] = Foo[A](x)

        override def ap[A, B](ff: Foo[A => B])(fa: Foo[A]): Foo[B] = {
          val f: A => B = ff.a // 함수를 끄집어냄
          val a: A = fa.a // 값을 꺼냄
          val b: B = f(a) // 함수를 적용, 변환 A => B
          val fb: Foo[B] = Foo(b) // context(Foo)를 씌움
          fb
//          Foo(ff.a(fa.a))
        }
      }

      val foo1 = Foo(1)
      Applicative[Foo].ap[Int, String](Foo((x:Int) => x.toString))(foo1) should be (Foo("1"))
    }
  }
}
