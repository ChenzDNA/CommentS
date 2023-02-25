package icu.chenz.comments.controller;

import icu.chenz.comments.entity.IDEntity;
import icu.chenz.comments.service.LikeService;
import icu.chenz.comments.utils.R;
import icu.chenz.comments.utils.exception.BadRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author : Chenz
 * @date : 2023-02-25 16:21
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/like")
public class LikeController {
    private final LikeService likeService;

    /**
     * @param uid
     * @param cid
     * @param type 0: 踩, 1: 赞
     * @return
     * @throws BadRequest type 值不为 0 或 1 时抛出
     */
    @PostMapping("/create")
    public R<Integer> create(@RequestAttribute("user") Long uid, Long cid, Byte type) throws BadRequest {
        return R.ok(likeService.create(uid, cid, type));
    }

    @PostMapping("/delete")
    public R<Integer> delete(@RequestAttribute("user") Long uid, @RequestBody IDEntity cid) {
        return R.ok(likeService.delete(uid, cid.getId()));
    }

}
