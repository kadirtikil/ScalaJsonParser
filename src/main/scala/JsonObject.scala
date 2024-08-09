package scala

import scala.annotation.tailrec

// Traits representing different JSON value types
sealed trait JsonValue 
case class JsonString(jsonString: String) extends JsonValue
case class JsonInt(jsonInt: Int) extends JsonValue
case class JsonFloat(jsonFloat: Float) extends JsonValue // hasnt been used
case class JsonDouble(jsonDouble: Double) extends JsonValue
case class JsonBoolean(jsonBoolean: Boolean) extends JsonValue
case class JsonList(jsonList: List[JsonValue]) extends JsonValue
case class JsonNull(jsonNull: String = "null") extends JsonValue 
case class JsonObject(jsonObject: Map[String, JsonValue]) extends JsonValue


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
            // Check for the next element (only elements, lists will follow) 
            parsingTokens.tail match {
              case ":" :: value :: tail =>
                if(value == "[") {
                  val listOfElements = listFinder(tail)
                  helper(tail, mapOfJsonVals + (keyOfVal -> JsonList(listOfElements)))
                } else if(value == "{") {
                  val mapOfElements = mapFinder(value::tail)
                  helper(tail, mapOfJsonVals + (keyOfVal -> JsonObject(mapOfElements)))
                } else {
                  val valueForKey = valueTyper(value)
                  helper(tail, mapOfJsonVals + (keyOfVal -> valueForKey))
                }
              case _ => helper(parsingTokens.tail, mapOfJsonVals)
            }
          case _ => helper(parsingTokens.tail, mapOfJsonVals)
        }
      }
    }

    helper(listOfTokens, Map())
  }

  // Helper function to return an element.
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

  // Helper function to return a list of elements.
  def listFinder(listFound: List[String]) : List[JsonValue] = {
    @tailrec
    def helper(listToIterate: List[String], resultingList: List[JsonValue]): List[JsonValue] = {
      listToIterate.head match {
        case "]" => resultingList
        case s if (s.startsWith("\"")) => helper(listToIterate.tail, resultingList :+ JsonString(s))
        case s if (helperIsNumber(s)) => {
          if(s.contains(".")) helper(listToIterate.tail, resultingList :+ JsonDouble(s.toDouble))
          else helper(listToIterate.tail, resultingList :+ JsonInt(s.toInt))
        }
        case s if(s.startsWith("true")) => helper(listToIterate.tail, resultingList :+ JsonBoolean(true))
        case s if (s.startsWith("false")) => helper(listToIterate.tail, resultingList :+ JsonBoolean(false))
        case _ => helper(listToIterate.tail, resultingList)

      } 
    }
    helper(listFound, List())
  }

  // Helper function to return a map of elements.
  def mapFinder(listFound: List[String]) : Map[String, JsonValue] = {

    // Best part about this:
    // If i want to find another object inside an object, just start with from the beginning :D
    val parser = new FunctionalJsonObject(listFound)
    val resultingObject = parser.functionalMapBuilderForJson()

    return resultingObject
    
  }

}
