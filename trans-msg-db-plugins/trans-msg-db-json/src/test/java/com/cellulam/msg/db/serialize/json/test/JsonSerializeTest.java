package com.cellulam.msg.db.serialize.json.test;

import com.cellulam.msg.db.serialize.json.JsonSerialize;
import com.cellulam.msg.db.test.utils.TestUtils;
import lombok.Data;
import org.junit.Assert;
import org.junit.Test;

import java.io.Serializable;

/**
 * @author eric.li
 * @date 2022-06-12 11:43
 */
public class JsonSerializeTest {
    private JsonSerialize jsonSerialize = new JsonSerialize();

    @Test
    public void testSimple() throws Exception {
        User user = TestUtils.randomBean(User.class);

        String json = jsonSerialize.serialize(user);
        User user2 = jsonSerialize.deserialize(json, User.class);

        Assert.assertEquals(json, jsonSerialize.serialize(user2));
        TestUtils.assertSameObject(user, user2);
        System.out.println(json);
    }

    @Test
    public void testNested() throws Exception {
        ResultData<User> resultData = new ResultData<>();
        resultData.setResult("success");
        resultData.setData(TestUtils.randomBean(User.class));

        String json = jsonSerialize.serialize(resultData);
        ResultData<User> resultData2 = jsonSerialize.deserializeByNestedClass(json, ResultData.class, User.class);

        String json2 = jsonSerialize.serialize(resultData2);
        Assert.assertEquals(json, json2);

        TestUtils.assertSameObject(resultData, resultData2);
        System.out.println(json);
        System.out.println(json2);
    }

    @Data
    public final static class ResultData<T> implements Serializable {
        private T data;
        private String result;
    }

    @Data
    public final static class User implements Serializable {
        private String name;
        private int age;
    }
}
