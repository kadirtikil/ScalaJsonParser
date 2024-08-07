package scala

@main def hello(): Unit = {
  val jsonString = """{"key":"value","number":"123","flag":"true"}""" 
  val temp = new FunctionalJsonTokenizer(jsonString)
  
  val tokens = temp.tokenizer()

  print(tokens)
}


