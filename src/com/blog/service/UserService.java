package com.blog.service;

import java.util.List;
import java.util.Set;

import com.blog.model.User;

public interface UserService {
	//�����û�
	public void addUser(User user);
	//�û����������ѯ�û�
	public User login(String username,String password);
	  
	//�����û�
    boolean update(User user); 
    //ɾ���û�
    boolean delete(int id);    
    //����id��ѯ
    User findById(int id);  
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
