package io.janstenpickle.trace4cats.http4s.client

import java.util.concurrent.Executors
import cats.data.Kleisli
import cats.effect.{ContextShift, IO, Timer}
import io.janstenpickle.trace4cats.Span
import io.janstenpickle.trace4cats.base.context.Local
import io.janstenpickle.trace4cats.http4s.common.TraceContext

import scala.concurrent.ExecutionContext

object Instances {
  implicit val cs: ContextShift[IO] = IO.contextShift(ExecutionContext.fromExecutor(Executors.newCachedThreadPool()))
  implicit val timer: Timer[IO] = IO.timer(ExecutionContext.global)

  implicit val localSpan: Local[Kleisli[IO, TraceContext[IO], *], Span[IO]] =
    Local[Kleisli[IO, TraceContext[IO], *], TraceContext[IO]].focus(TraceContext.span[IO])
}
