/*package scala

import scala.annotation.tailrec

// This class will return the datatypes of the tokens. 
// it will return them as String, such that the Map builder can later use them to initialize the instance with the appropriate datatype.
// 
class Typer(tokenList: List[String]) {
  
  def typeFinder(): List[String] = {
  
    @tailrec
    def helper(parsingList: List[String], typesOfTokens: List[String]) : List[String] = {
      if(parsingList.isEmpty) typesOfTokens
      else {
        parsingList.head match {
          case nil => typeOfTokens
          case head::tail => {
            if(head.startsWith("\"")) typesOfTokens +: "string"
            
          }
          case _ => {
            
          }
        }
      }
    }

  helper(tokenList)
  }
}*/
