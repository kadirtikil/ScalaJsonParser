package scala

import scala.annotation.tailrec

// traits:
// a json value can consist of different datatype. so 
sealed trait JsonValue 
case class JsonString(jsonString: String) extends JsonValue
case class JsonInt(jsonInt: Int) extends JsonValue
case class JsonFloat(jsonFloat: Float) extends JsonValue
case class JsonDouble(jsonDouble: Double) extends JsonValue
case class JsonBoolean(jsonBoolean: Boolean) extends JsonValue
case class JsonList(jsonList: List[JsonValue]) extends JsonValue

class FunctionalJsonObject(listOfTokens: List[String]) {

  def functionalMapBuilderForJson() : Map[String,  JsonValue] = {
    @tailrec
    def helper(parsingTokens: List[String], mapOfJsonVals: Map[String, JsonValue]): Map[String, JsonValue] = {
      if(parsingTokens.isEmpty) mapOfJsonVals
      else {
        parsingTokens.head match {
          case s if (s.startsWith("\"")) => {
            // val restListForParsing = something that returns the list after adding key and value 
          }
          case s if (s.startsWith(":")) => mapOfJsonVals
          case s if (s.startsWith(",")) =>mapOfJsonVals 
          case s if (s.startsWith("[")) =>mapOfJsonVals
          case s if (s.startsWith("}")) => mapOfJsonVals
          case _ => helper(parsingTokens.tail, mapOfJsonVals) 
          
        }
      }
    }
    
    helper(listOfTokens, Map())
  }

}
