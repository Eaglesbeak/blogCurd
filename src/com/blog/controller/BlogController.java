package com.blog.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.blog.model.Blog;
import com.blog.model.Diary;
import com.blog.service.BlogService;
import com.blog.service.DiaryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @author glj
 * @
 */
@Controller
public class BlogController {
	@Autowired
	BlogService blogService;
		
		/**
		 * �洢������Ϣ
		 * @param blog
		 * @return
		 */
		@RequestMapping("/admin/writeBlog")
		public String writeBlog(Blog blog,RedirectAttributes attributes){
					//��ȡ��ǰ����
					Date currentTime = new Date();
				 	//������ת��Ϊָ����ʽ
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					String dateString = formatter.format(currentTime);
					blog.setTime(dateString);
					this.blogService.writeBlog(blog);
					attributes.addFlashAttribute("msg", "������³ɹ�");
					return "redirect:/admin/adminblog";
		}
		
		/**
		 * ����ȫ���ͣ�������ҳ��ʾ
		 * @param pn
		 * @param model
		 * @return
		 */
		@RequestMapping("/selectAllBlog")
		public String selectAllBlog2(
				@RequestParam(value="pn",defaultValue="1")Integer pn,
				Model model
				){
			try {
				PageHelper.startPage(pn, 8);
				PageHelper.orderBy("blogid desc");//����Ϊ����
				List<Blog> blogs =blogService.selectAllBlog();
				PageInfo page = new PageInfo(blogs);
				model.addAttribute("pageInfo", page);
				return "blogindex";
			} catch (Exception e) {
				System.out.println(e);
				return null;
			}
		}
		
		
		/**
		 * ͨ��id���Ҳ��ͣ�������ʾ���͵�����
		 * @param request
		 * @return
		 */
		@RequestMapping("/selectBlogById")
		public String selectBlogById(HttpServletRequest request){
			try {
				//��ȡid��ת������
				String id = request.getParameter("blogid");
				int blogid = Integer.parseInt(id);
				List<Blog> blogs = new ArrayList<Blog>();
				blogs =  this.blogService.selectBlogById(blogid);
				//����ѯ�������
				request.setAttribute("blog", blogs.get(0));
				return "page";
			} catch (Exception e) {
				System.out.println(e);
				return null;
			}
		}
		/**
		 * ͬ���ǲ�ѯ���в�����Ϣ�����ڹ�����
		 * @param pn
		 * @param model
		 * @return
		 */
		@RequestMapping("/admin/adminblog")
		public String adminblog(
				@RequestParam(value="pn",defaultValue="1")Integer pn,
				Model model){
			try {
				PageHelper.startPage(pn, 8);
				PageHelper.orderBy("blogid desc");//����Ϊ����
				List<Blog> blogs = blogService.selectAllBlog();
				PageInfo page1 = new PageInfo(blogs);
				model.addAttribute("blogs", page1);
				return "/admin/adminPageList";
			} catch (Exception e) {
				System.out.println(e);
				return null;
			}
		}
		/**
		 * ɾ��������Ϣ
		 * @param request
		 * @return
		 */
		@RequestMapping("/admin/deleteBlogById")
		public String deleteBlogById(HttpServletRequest request,RedirectAttributes attributes){
			try {
				String id = request.getParameter("blogid");
				int blogid = Integer.parseInt(id);
				//����ɾ��
				this.blogService.deleteBlogById(blogid);
				attributes.addFlashAttribute("msg", "ɾ�����³ɹ�");
				return "redirect:/admin/adminblog";
			} catch (Exception e) {
				return null;	
			}
		}
		/**
		 * ��������
		 * @param blog
		 * @param model
		 * @param request
		 * @return
		 */
		@RequestMapping("/admin/updateBlog")
		public String updateBlog(Blog blog,Model model,
				HttpServletRequest request,
				RedirectAttributes attributes){
			if(blogService.updateBlog(blog)){
				blog = blogService.findById(blog.getBlogid());
				request.setAttribute("blog", blog);
				model.addAttribute("blog", blog);
//				model.addAttribute("msg", "�޸ĳɹ�");
				attributes.addFlashAttribute("msg", "�޸����³ɹ�");
				return "redirect:/admin/adminblog";
			}
			attributes.addAttribute("msg", "�޸����³���");
			return "error";
		}
		/**
		 * ������������Ҫ����id������
		 * @param id
		 * @param request
		 * @param model
		 * @return
		 */
		@RequestMapping("/admin/getBlog")
		public String getBlog(int id,HttpServletRequest request,Model model){
			Blog blog = blogService.findById(id);
			request.setAttribute("blog", blog);
			model.addAttribute("blog", blog);
			return "/admin/editBlog";
		}
}
