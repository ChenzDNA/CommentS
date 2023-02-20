package icu.chenz.comments;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import icu.chenz.comments.config.AuthorConfig;
import icu.chenz.comments.controller.CommentController;
import icu.chenz.comments.entity.CommentEntity;
import icu.chenz.comments.entity.IDEntity;
import icu.chenz.comments.utils.R;
import icu.chenz.comments.utils.exception.BadRequest;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.Map;

@SpringBootTest
class CommentSApplicationTests {

    @Resource
    private AuthorConfig authorConfig;

    @Resource
    private CommentController commentController;

    @Test
    public void s() {
        System.out.println(authorConfig.getName());
    }

    @Test
    public void commentControllerCreateAndTop() throws BadRequest {
        R<CommentEntity> r = commentController.create(1L, new CommentEntity("45666", "123"), null);
        Assert.notNull(r.getData(), "commentController.create returns null.");
        System.out.println(r.getData());

        R<Integer> top = commentController.top(6L, new IDEntity(r.getData().getId()));
        Assert.notNull(r.getData(), "commentController.top returns null.");
        Assert.isTrue(top.getData() == 1, "commentController.top returns not 1.");
    }

    @Test
    public void commentControllerGets() {
        R<Map<String, Object>> byContext = commentController.getByContext(1L, "789");
        Assert.notNull(byContext.getData(), "commentController.getByContext returns null.");
        System.out.println(JSONObject.toJSONString(
                byContext.getData(),
                JSONWriter.Feature.PrettyFormat,
                JSONWriter.Feature.WriteNulls
        ));
    }
}
