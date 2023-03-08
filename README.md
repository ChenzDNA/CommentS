# CommentS 评论服务

[![OSCS Status](https://www.oscs1024.com/platform/badge/CommentS.svg?size=small)](https://www.murphysec.com/accept?code=4207b9367e9511badb55691c447f83db&type=1&from=2)

## 介绍

可以部署到自己的服务器的评论服务。

包含基本的登录注册（使用 JWT），楼中楼评论。可以引入各静态博客中，依靠 context 区分不同文章的评论。

[frontend](https://github.com/ZhengDNA/comments-ui)

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

对接示例：[example](example)，有流程图。

## todos

- feat: 目前新增 context 需要 author 用户访问才能创建，并且不能删除。待实现后台管理。
- feat: 登录验证（邮箱，手机号等）
- feat: 接口限流

*第一次设计并实现这种 SDK 一样的系统，有些地方可能想的比较天真，欢迎提 issue 或 pull request*