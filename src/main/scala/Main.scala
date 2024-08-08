package scala

@main def hello(): Unit = {
  val jsonString = """{"key" : "value", "some list" : 123, "number" : 123.321, "flag" : true, "flag" : false, "test" : 123.456789}""" 
  val temp = new FunctionalJsonTokenizer(jsonString) 
  val tokens = temp.tokenizer()

  val parser = new FunctionalJsonObject(tokens)
  val result = parser.functionalMapBuilderForJson()



  print(result)
}


