# debzap

## ドメイン

**https://planet-meron.com**

## GET /articles
記事一覧の取得

### クエリパラメータ

|key |説明 |型 |必須 |備考|
|---|---|---|---|---|
|sortby|並び順 | string| | created_date:新着順, fav:人気順。デフォ新着|
|offset |オフセット|int | | |
|count |取得件数 | int | |デフォルト50件 |

### レスポンス

|key |説明 |型 |NULLあり|備考 |
|---|---|---|---|---|
|total |記事の全件数| Int | YES | |
|articles |記事一覧 | List | | |
|└ id |記事ID |int | | |
|└ title |タイトル |string | | |
|└ link |リンクURL |string | | |
|└ created_date|作成日付 |date | |yyyy-MM-ddThh:mm:ss|
|└ source |リンク元 |object| | |
|__└ id |リンク元ID |int | | |
|__└ name |リンク元名前 |string | | |

**レスポンス例**
[GET] /articles?sortby=created_date&offset=10&count=20

```
{
  "total": 200,
  "values" [
    {
      "id": 123,
      "title": "PPAPはすごかった",
      "link": "http://example.com",
      "created_date": "2016-10-30T01:28:15.000+09:00",
      "source": {
        "id": 10,
        "name": "筋肉まとめ"
      }
    }
  ]
}
```
