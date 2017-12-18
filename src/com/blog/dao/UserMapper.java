package com.blog.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.blog.model.User;

public interface UserMapper {
	//����û�
	public void addUser(User user);
	//�û����������ѯ�û�
	public User findUserByNameAndPwd(@Param("username") String username,
				@Param("password") String password);
	//�����û�
    boolean update(User user); 
    //ɾ���û�
    boolean delete(int id);    
    //����id��ѯ
    User findById(int id);  
    //����code��ѯuser
    User findByCode(String code);
    //��ѯ�����û�
    List<User> findAll();  
    //�����û��������û�
    User selectUserByUsername(String userName);
    List<User> selectUserByUsernameOfReg(String userName);
    //�����û������ҽ�ɫ
    Set<String> selectRolesByUserName(String userName);
    //�����û�������Ȩ��
    Set<String> selectPermissionByUserName(String userName);
}
