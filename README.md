# nfon-api-client-java
NFON administration portal REST API client

The [NFON AG](https://nfon.com) offers a very extensive REST API with almost the same scope as the portal UI. But using the API is a bit complicated, this small package should help with the usage.

> [!WARNING]
> **Attention:** The use is expressly at your own risk. 

### Requirements

You need to have
1. the main URL to the API (i.e. `https://api09.nfon.com`)
2. your API public and (i.e. `NFON-ABCDEF-123456`)
3. your API secret and (i.e. `gAp3bTxxUev5JkxOcBdeC5Absm7J84jp6mEhJZd3XiLdjzoGSF`)
4. your account name (i.e. `K1234`)

> [!NOTE]
> If you do not have this information, please contact your NFON partner for assistance.

> [!WARNING]
> **Never share your `API secret`!**

## Usage

Add the package to you pom.xml:
```bash
...
    <dependency>
        <groupId>com.silegio.nfon</groupId>
        <artifactId>junfonapinit</artifactId>
        <version>1.0</version>
    </dependency>
...
```

```java
	...
    String apiRootURL = "https://api09.nfon.com";
    String apiKey = "NFON-ABCDEF-123456";
    String apiSecret = "gAp3bTxxUev5JkxOcBdeC5Absm7J84jp6mEhJZd3XiLdjzoGSF";
    String customer = "";

    ApiConfig apiConfig = new ApiConfig(apiRootURL, apiKey, apiSecret);
    ApiRequest apiRequest = new ApiRequest(apiRootURL, "/api/customers/" + customer + "/phone-books?_pagesize=3",
            "GET", "", "application/json");

    AuthResult result = Auth.getAuthentication(apiRequest, apiConfig);

    HttpURLConnection connection = ApiClient.Request(apiConfig, apiRequest, result);

    if (responseCode == 200) {
        ...
    } else {
        ... // error handling
    }
	...
```

### Result Parsing
The result is JSON-like, but difficult to read or process. So as a little help see the example [PhoneBookRequest](https://github.com/art-pub/nfacj/blob/main/nfonapi/src/main/java/com/silegio/nfon/examples/PhoneBookRequest.java#L56).

### Examples
There are two examples, one with a single result request [StatusRequest](https://github.com/art-pub/nfon-api-client-java/blob/main/nfonapi/src/main/java/com/silegio/nfon/examples/StatusRequest.java) and one with a multiple result request [PhoneBookRequest](https://github.com/art-pub/nfon-api-client-java/blob/main/nfonapi/src/main/java/com/silegio/nfon/examples/PhoneBookRequest.java).

#### StatusRequest
You can start this example by providing the API url:
```bash
bash$ java StatusRequest.java https://api09.nfon.com
Version: 1.17.8.0, BuilTime: 2023-11-07 21:19, Host: api09.nfon.com
bash$
```

#### PhoneBookRequest
You need to have a valid API key pair for this request:
```bash
bash$ java PhoneBookRequest.java https://api09.nfon.com NFON-ABCDEF-123456 gAp3bTxxUev5JkxOcBdeC5Absm7J84jp6mEhJZd3XiLdjzoGSF K1234
-> First Contact: +49 (89) 123-456
-> Hans im Glueck: +49 (894) 123-457
-> Captain Nemo: +49 (894) 123-458
```

### Good to know

#### Datasets and Pagination

Endpoints that return more than one record will return a maximum of 100 records on the first request. The result contains the following information:

Href: Path of the current request

Total: Amount of all datasets (not pages!)

Offset: Offset starting with 0

Size: Amount of maximum results in the response. You can set the amount in the request with the parameter `pageSize=XXX` with `XXX` being max. 100.

Query: You filter the results with the additional parameter `_q`, i.e. `/api/customers/K1234/phone-books?_q=SomeName`.

Links: Array of links including the first, the next and the last URL to retrieve all data. See `LinksMap["first"]`, `LinksMap["last"]` and `LinksMap["next"]` in the example above.

> [!IMPORTANT]
> **Please note:** You have to iterate through all those links to retrieve all data. Just repeat with the `next` given `Href` until your current `Href` (path of the current request) matches the `last` entry.

> [!IMPORTANT]
> If the `last` entry is empty, you already have all data in the current response.


## Links

* [Latest NFON API Documentation (zip)](https://cdn.cloudya.com/API_Documentation.zip)
* [go/golang client for NFON API](https://github.com/art-pub/nfon-api-client)
* [PHP client for NFON API](https://github.com/art-pub/nfon-api-client-php)
* [node.js client for NFON API](https://www.npmjs.com/package/nfon)
