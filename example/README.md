# 对接示例

- [Spring Security 对接 Comments](SecurityDemo)

## 调用流程图

![procedure](img/procedure.png)

### 1.配置

```yaml
comments:
  adapter:
    enable:                 true
    key:                    <string>
    value:                  <string>
    token-header-key:       <string "Authorization" by default>
    token-generation-key:   <string>
    token-generation-value: <string>
```

这些值是 CommentS 和对接系统约定的值，`key-value` 对应的是请求头，用于确定调用方是否是对接的系统。当然

### 2. CommentS 提供的接口

#### (1). 注册时调用 `POST /user/r` 记录用户信息到 CommentS

**CommentS 里的用户信息只用于评论的显示以及生成 token。**

需要携带请求头:

```text
Content-Type:           application/x-www-form-url-encoded;charset=utf8
<comments.adapter.key>: <comments.adapter.value>
```

请求体:

```text
nickname=<string>&username=<string>
```

响应头：

```text
<comments.adapter.token-header-key>: <string (token)>
```

这个 token 需要返回给前端。

响应体：

```json
{
  "code": 200,
  "message": null,
  "data": <number (user id for CommentS)>
}
```

data 字段作为 CommentS 使用的 user id，需要返回给前端。

调用接口后，对接的系统已知：

- 用户 token，
- 用户 id，
- 账号名 username，
- 用户昵称 nickname（如果对接的系统中没有设计这个字段，就和账号名一样）

<p id="send-data-to-frame">在前端通过 `postMessage` 发送给 CommentS frame：</p>

```js
window.frames[0].postMessage({
          type: 'adapt',
          user: {id: 1, username: '用户名', nickname: '用户昵称'},
          token: '...'
        }, commentsFrameOrigin)
```

**注意：这个接口不应该由前端调用。**

#### (2). 登录时调用 `POST /user/g` 生成 token

需要携带请求头：

```text
Content-Type:                            application/x-www-form-url-encoded;charset=utf8
<comments.adapter.token-generation-key>: <comments.adapter.token-generation-value>
```

请求体：

```text
username=<string>
```

响应头和响应体同 `POST /user/r`，登录后在前端同样需要<a href="#send-data-to-frame">把返回信息发送给 CommentS frame</a>。
