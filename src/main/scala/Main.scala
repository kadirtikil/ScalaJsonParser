package scala

@main def hello(): Unit = {
  val jsonString = """{"key" : "value", "some list" : 123, "number" : 123.321, "flag" : true, "test" : 123.456789, "another flag" : true}""" 
  val jsonStringWithList = """{"key" : "value", "some list" : [1, 2, 3], "another list" : ["one", "two", "three"]}"""
  val jsonInsideJson = """{"key" : {"anotherkey" : "withValue"} }"""

  val temp = new FunctionalJsonTokenizer(jsonString) 
  val tokens = temp.tokenizer()
  print("\n" + tokens + "\n")


  val parser = new FunctionalJsonObject(tokens)
  val result = parser.functionalMapBuilderForJson()



  print(result)
}


