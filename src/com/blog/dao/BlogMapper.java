package com.blog.dao;

import java.util.List;

import com.blog.model.Blog;


public interface BlogMapper {
	//��������
	void writeBlog(Blog blog);
	//����ȫ������
	List<Blog> selectAllBlog();
	//����id��ѯ
	List<Blog> selectBlogById(int blogid);
	//����id��ѯ
	Blog findById(int blogid);
	//ɾ������
	void deleteBlogById(int blogid);
	//��������
	boolean updateBlog(Blog blog);
}
