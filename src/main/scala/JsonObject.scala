package scala

import scala.annotation.tailrec

// Takes a JsonString (not as Byte just as a simple String) and returns a map containing each Element of it 

class FunctionalJsonTokenizer(jsonString: String){ 

  // The tokenizer has to be functional, so it cannot mutate any state of any value.
  // it has to parse the string, and allocate new memory for a list, which will then contain the elements as string.
  // the iteration will be accomplished by using tail recursion
  // the parsing by using pattern matching
  def tokenizer(): List[String] = {
    @tailrec
    def helper(reducedJsonString: String, listOfTokenizedElements: List[String]): List[String] = {
      // Any preparation on the string here

      if(reducedJsonString.isEmpty) {
        listOfTokenizedElements
      } else {
        // value, with the firs telement being a token and the second being the rest of the string
        val (token, rest) = tokenParser(reducedJsonString)
        helper(rest, listOfTokenizedElements :+ token)
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

      case _ =>
        throw new RuntimeException("Unexpected character in JSON input")
    }
  } 
}
  
