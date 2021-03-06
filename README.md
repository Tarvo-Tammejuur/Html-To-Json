HtmlToJson
=========
This little snippet will try its best to convert DOM-compatible source to JSON format.


NOTE
-------------
This project is still under development. I haven't done extensive testing yet.
**There are possibly many corner cases unhandled!**

Demo program (Jsoup.parse)
----
```java
    public static void main(String[] args) throws IOException {
            String html = new String(
				            Files.readAllBytes(Paths.get("index.html")),
				            StandardCharsets.UTF_8
					      );
					      
            String json = new HtmlToJson(Jsoup.parse(html))
				            .transform()
				            .asFormattedJsonString();
            
            System.out.println(json);
    }
```


Input (HTML):
-------
```html

<!DOCTYPE html>
<html>
	<head>
		<meta name="description" content="for demo" />	
		<title>Page Title</title>
	</head>
	<body>
		<h1 class="classy" id="title">This is a heading</h1>
		<div id="nice">
			<h2>This is another heading</h2>
		</div>
	</body>
</html>

```

Output (JSON):
-------
```js
{
  "element": "html",
  "content": "",
  "attributes": {},
  "children": [
    {
      "element": "head",
      "content": "",
      "attributes": {},
      "children": [
        {
          "element": "meta",
          "content": "",
          "attributes": {
            "name": "description",
            "content": "for demo"
          },
          "children": []
        },
        {
          "element": "title",
          "content": "Page Title",
          "attributes": {},
          "children": []
        }
      ]
    },
    {
      "element": "body",
      "content": "",
      "attributes": {},
      "children": [
        {
          "element": "h1",
          "content": "This is a heading",
          "attributes": {
            "class": "classy",
            "id": "title"
          },
          "children": []
        },
        {
          "element": "div",
          "content": "",
          "attributes": {
            "id": "nice"
          },
          "children": [
            {
              "element": "h2",
              "content": "This is another heading",
              "attributes": {},
              "children": []
            }
          ]
        }
      ]
    }
  ]
}
```

----------