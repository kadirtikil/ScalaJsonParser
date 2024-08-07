package scala

import scala.annotation.tailrec

// Takes a JsonString (not as Byte just as a simple String) and returns a map containing each Element of it 

class FunctionalJsonTokenizer(jsonString: String){


  def helperIsNumber(potentialNum: String): Boolean = {
    if(potentialNum.head.isDigit) true
    else false
  }

  def helperEndNumber(jsonStringFromIteration: String): Int = {
    @tailrec
    def helper(recursiveString: String, numEndPosition: Int): Int = {
     if (recursiveString.isEmpty) numEndPosition
     else {
       recursiveString.head match {
          case c if (helperIsNumber(c.toString)) => helper(recursiveString.tail, numEndPosition + 1)
          case c if (c == '.') => helper(recursiveString.tail, numEndPosition + 1)
          case _ => numEndPosition
        }
      }
    }
  
   helper(jsonStringFromIteration, 0)
  }


  // The tokenizer has to be functional, so it cannot mutate any state of any value.
  // it has to parse the string, and allocate new memory for a list, which will then contain the elements as string.
  // the iteration will be accomplished by using tail recursion
  // the parsing by using pattern matching
  def tokenizer(): List[String] = {
    @tailrec
    def helper(reducedJsonString: String, listOfTokenizedElements: List[String]): List[String] = {

      if(reducedJsonString.isEmpty) {
        listOfTokenizedElements
      } else {
        // value, with the firs telement being a token and the second being the rest of the string
        // prepare string for iteration by ridding it of white spaces before the first default token and after the last default token.
        

        val (token, rest) = tokenParser(reducedJsonString)
        
        if(token == "") helper(rest, listOfTokenizedElements)
        else helper(rest, listOfTokenizedElements :+ token) 
      }
    }
    helper(jsonString, List())
  }
  
  // The parser itself
  // creates a token and adds it to the list
  // the parser looks for : , " { } [ ] ...
  // each of them is a token by default
  // so the substring until another one is met is a token as well
  //
  def tokenParser(jsonString: String): (String, String) = {
  
    jsonString match {
      case s if s.startsWith("\"") =>
        // Then find the next one
        val endOfToken = s.indexOf("\"", 1)
        if(endOfToken < 0) throw new RuntimeException("whoopsie")
        val token = s.substring(0, endOfToken+1)
        (token, s.substring(endOfToken + 1))
      
      // whitespaces before and after default tokens work
      case s if s.startsWith(" ") => 
        ("", s.substring(1))

      case s if s.startsWith("{") =>
        ("{", s.substring(1))

      case s if s.startsWith("}") =>
        ("}", s.substring(1))

      case s if s.startsWith("[") => 
        ("[", s.substring(1))
      
      case s if s.startsWith("]") => 
        ("]", s.substring(1))

      case s if s.startsWith(":") =>
        (":", s.substring(1))
      
      case s if s.startsWith(",") => 
        (",", s.substring(1))

      // what if its a boolean?
      // have to check, because booleans dont have "" to indicate they are strings
      case s if s.startsWith("true") =>
        ("true", s.substring(4))

      case s if s.startsWith("false") => 
        ("false", s.substring(5))

      // what is its a null?
      case s if s.startsWith("null") => 
        ("null", s.substring(4))

      // Now to the big one
      // What if its a number?
      // What kind of number is not important, the typer will do that later on
      // check if char is num with regex???
      case s if helperIsNumber(s.take(1)) => 
        val endOfNumber = helperEndNumber(s)
        if(endOfNumber < 0) throw new RuntimeException("some problems with numbers")      
        val token = s.substring(0, endOfNumber)
        (token, s.substring(endOfNumber))

      case _ =>
        throw new RuntimeException("Not a valid Json element.")
    }
  } 
}
  
