package scala

import scala.annotation.tailrec

// Traits representing different JSON value types
sealed trait JsonValue 
case class JsonString(jsonString: String) extends JsonValue
case class JsonInt(jsonInt: Int) extends JsonValue
case class JsonFloat(jsonFloat: Float) extends JsonValue
case class JsonDouble(jsonDouble: Double) extends JsonValue
case class JsonBoolean(jsonBoolean: Boolean) extends JsonValue
case class JsonList(jsonList: List[JsonValue]) extends JsonValue
case class JsonNull(jsonNull: String = "null") extends JsonValue // Providing a default value for JsonNull

class FunctionalJsonObject(listOfTokens: List[String]) {

  def functionalMapBuilderForJson(): Map[String, JsonValue] = {
    @tailrec
    def helper(parsingTokens: List[String], mapOfJsonVals: Map[String, JsonValue]): Map[String, JsonValue] = {
      if (parsingTokens.isEmpty) mapOfJsonVals
      else {
        parsingTokens.head match {
          case s if s.startsWith("\"") =>
            // Key found:
            val keyOfVal = s.stripPrefix("\"").stripSuffix("\"")
            // Check for the next element (no list yet):
            parsingTokens.tail match {
              case ":" :: value :: tail =>
                val valueForKey = valueTyper(value)
                helper(tail, mapOfJsonVals + (keyOfVal -> valueForKey))
              case _ => helper(parsingTokens.tail, mapOfJsonVals)
            }
          case _ => helper(parsingTokens.tail, mapOfJsonVals)
        }
      }
    }

    helper(listOfTokens, Map())
  }

  // Helper function to return either an element for a key or a list.
  def valueTyper(token: String): JsonValue = {
    token match {
      case s if s.startsWith("\"") => JsonString(s.stripPrefix("\"").stripSuffix("\""))
      case s if s == "true" => JsonBoolean(true)
      case s if s == "false" => JsonBoolean(false)
      case s if helperIsNumber(s) =>
        if (s.contains(".")) JsonDouble(s.toDouble)
        else JsonInt(s.toInt)
      case _ => JsonNull() // Default case to handle unexpected tokens
    }
  }

  def helperIsNumber(potentialNum: String): Boolean = {
    potentialNum.forall(c => c.isDigit || c == '.') && potentialNum.nonEmpty
  }
}
