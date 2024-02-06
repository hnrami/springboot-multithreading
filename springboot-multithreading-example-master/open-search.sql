{
  "query": {
    "bool": {
      "must": [
        {
          "multi_match": {
            "query": "well",
            "fields": ["country", "city"]
          }
        },
        {
          "bool": {
            "must_not": {
              "exists": {
                "field": "recorddat"
              }
            }
          }
        }
      ]
    }
  },
  "sort": [
    {
      "_score": {
        "order": "desc"
      }
    }
  ],
  "size": 10,
  "from": 0
}
