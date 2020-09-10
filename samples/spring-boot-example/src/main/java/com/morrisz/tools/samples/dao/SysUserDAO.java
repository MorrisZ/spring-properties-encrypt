package com.morrisz.tools.samples.dao;

import com.morrisz.tools.samples.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zhangyoumao
 */
@Repository("sysUserDAO")
public interface SysUserDAO extends JpaRepository<SysUser, Integer> {
}
