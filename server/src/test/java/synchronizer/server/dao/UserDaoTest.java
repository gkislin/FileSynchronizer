package synchronizer.server.dao;

import org.junit.Assert;
import org.junit.Test;
import synchronizer.common.util.XmlUserUtil;
import synchronizer.common.xml.schema.XMLUser;

import java.util.Collections;

public class UserDaoTest {

    @Test
    public void test() throws Exception {
        XMLUser user = XmlUserUtil.generate();
        UserDao.save(Collections.singletonList(user));
        XMLUser dbUser = UserDao.get(user.getCode());
        Assert.assertTrue(XmlUserUtil.compare(user, dbUser));
    }
}