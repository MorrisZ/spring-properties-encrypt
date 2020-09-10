package com.morrisz.tools.samples;

import com.morrisz.tools.samples.dao.SysUserDAO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhangyoumao
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class SysUserDAOTest {

    @Autowired
    private SysUserDAO sysUserDAO;

    @Test
    public void findAll() {
        Assert.assertTrue(sysUserDAO.findAll().size() > 0);
    }
}
