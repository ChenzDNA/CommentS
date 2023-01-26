# CommentS 评论系统

## 介绍

包含基本的登录注册（使用 JWT），楼中楼评论。可以引入各静态博客中，依靠 context 区分不同文章的评论。

[frontend](https://github.com/ChenzDNA/comments-ui)

## 基本配置

```yaml
comments:
  jwt:
    secret:       <string>
    expire-time:  <number> # millisecond. default 86400000L (1 day).
  author:
    name:         <string> # 你的用户名。应和你自己账号的 username 一致。
```

## 对接现有的服务

### 1.配置

```yaml
comments:
  adapter:
    enable:       true
    key:          <string>
    value:        <string>
    verify:       <verify password api url string>
    verify-key:   <string>
    verify-value: <string>
```

### 2.应该主动调用的接口

在注册用户时调用本服务的 `POST /user/r` 接口，携带请求头:

```
Content-Type:           application/json;charset=utf8
<commentx.adapter.key>: <commentx.adapter.value>
```

请求体:

```json
{
  "nickname": "<string>",
  "username": "<string>",
  "password": "<encrypted string>"
}
```

**注意：这个接口不应该由前端调用。**

### 3.应该提供的接口

应该提供 `POST <commentx.adapter.verify>` 接口，本系统会携带请求头:

```
Content-Type:                   application/json;charset=utf8
<commentx.adapter.verify-key>:  <commentx.adapter.verify-value>
```

如果没有配置 `commentx.adapter.verify-key` 和 `commentx.adapter.verify-value` 则不会带上请求头。但是你要做好防护，比如说针对接口的
ip 白名单。

请求体：

```json
{
  "username": "<string>",
  "password": "<string>"
}
```

本服务会根据响应的 Http 状态码来判断密码验证是否成功（200）。

## todos

- test: 还没测试对接其他服务
- feat: 目前 context 还没有实现优雅的管理方法
- feat: 登录验证（邮箱，手机号等）
- feat: 接口限流

*第一次设计并实现这种 SDK 一样的系统，有些地方可能想的比较天真，欢迎提 issue 或 pull request*