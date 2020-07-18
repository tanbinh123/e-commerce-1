package com.ecommerce.service;

import com.ecommerce.common.base.CommonPage;
import com.ecommerce.pojo.SysMenu;
import com.ecommerce.pojo.SysResource;
import com.ecommerce.pojo.SysRole;
import com.ecommerce.pojo.SysUser;
import com.ecommerce.vojo.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
    /**
     * 根据用户名获取用户信息
     */
    SysUser getUserByName(String username);

    /**
     * 注册功能
     */
    SysUser register(RegisterVO registerVO);

    /**
     * 登录功能
     * @param username 用户名
     * @param password 密码
     * @return 生成的JWT的token
     */
    String login(String username,String password);

    /**
     * 刷新token的功能
     * @param oldToken 旧的token
     */
    String refreshToken(String oldToken);

    /**
     * 获取所有用户
     */
    CommonPage<SysUser> getAllUser(PageVO pageVO);

    /**
     * 模糊匹配用户
     */
    CommonPage<SysUser> searchUser(SearchUserVO searchUserVO);

    /**
     * 根据用户id获取用户
     */
    SysUser getItem(Long id);

    /**
     * 更新用户的身份
     */
    public int updateRole(Long adminId, List<Long> roleIds);

    /**
     * 获取所有角色
     */
    List<SysRole> getRoleList();

    /**
     * 获取可访问接口资源列表
     */
    List<SysResource> getResourceList();

    /**
     * 获取可访问菜单资源列表
     */
    List<MenuVO> getMenuList();

    /**
     * 修改用户接口权限
     */
    int updateResource(Long adminId, List<Long> permissionIds);

    /**
     * 修改用户菜单权限
     */
    int updateMenu(Long roleId, List<Long> menuIds);

    /**
     * 获取角色所有接口权限
     */
    List<Long> getPermissionResourceList(Long roleId);

    /**
     * 获取角色所有菜单权限
     */
    List<String> getPermissionMenuList(Long userId);

    /**
     * 修改密码
     */
    int updatePassword(UpdatePasswordVO updatePasswordVO);

    /**
     * 获取用户信息
     */
    UserDetails loadUserByUsername(String username);
}
