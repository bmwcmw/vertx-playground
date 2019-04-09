package com.databerries.gatling

import java.util.UUID

import io.gatling.core.feeder.Feeder

import scala.util.Random

object UuidFeeder {
  private val random: Random = new Random()

  val feeder = new Feeder[String] {
    override def hasNext = true

    override def next: Map[String, String] = {
      Map("uuid" -> ("gatling_" + new UUID(random.nextLong(), random.nextLong())))
    }
  }
}