package scala

@main def hello(): Unit = {
  val jsonString = """{"key" : "value", "some list" : 123, "number" : 123.321, "flag" : true, "test" : 123.456789, "another flag" : true}""" 
  val jsonStringWithList = """{"key" : "value", "some list" : [1, 2, 3], "jsonobject" : {"somelist" : [2, 3, 5]}, "another list" : ["one", "two", "three"], "testing" : "another element" }"""
  val jsonStringBigBoy = """
  {
    "name": "John Doe",
    "age": 30,
    "isStudent": false,
    "courses": ["Math", "Science", "Literature"],
    "address": {
      "street": "123 Main St",
      "city": "Anytown",
      "postalCode": "12345"
    },
    "grades": {
      "Math": "A",
      "Science": "B+",
      "Literature": "A-"
    },
    "hobbies": ["reading", "hiking", "coding"]
  }
  """

  val jsonStringListMultiple = """{"a list": [false, 123, "hello"]}"""

  val checkforquotes = """{"test" : "value"}"""

 


  val temp = new FunctionalJsonTokenizer(jsonStringBigBoy) 
  val tokens = temp.tokenizer()
  print("\n" + tokens.mkString("[\n  ", ",\n  ", "\n]") + "\n")


  val parser = new FunctionalJsonObject(tokens)
  val result = parser.functionalMapBuilderForJson()



  print(result.mkString("{\n  ", ",\n  ", "\n}") + "\n")
}


