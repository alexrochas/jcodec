# JCodec
> Simple and opportunistic POC to decode QRCode

## How-to

For a input string **0102120504123403123**

```java

  List<Codec> hCodecs = new ArrayList<>();
  hCodecs.add(new LLCodec("01", "Padding two and size 02", 2));
  hCodecs.add(new LLCodec("05", "Padding two and size 05", 2));
  hCodecs.add(new LLCodec("My Key", "Without padding with size 03"));
  
  Map<String, String> decoded = new HCodec(hCodecs).decode(input).getLeft();
```

The return will be a Map with:

```
{
  "01" -> "12",
  "05" -> "1234",
  "My Key" -> "123"
}
```

## Meta

Alex Rocha - [about.me](http://about.me/alex.rochas) -
