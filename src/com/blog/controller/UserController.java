package com.blog.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.blog.model.User;
import com.blog.service.UserService;
import com.blog.util.Functions;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Controller
public class UserController {
	@Autowired
	UserService userService;
	
	@RequestMapping("/loginUser")
	public String loginForm(RedirectAttributes attributes){
		Subject subject = SecurityUtils.getSubject();
		if(subject.isAuthenticated()==false){
			return "login";
		}
//		String username= (String) subject.getPrincipal();
		attributes.addFlashAttribute("msg", "��¼�ɹ�");
//		attributes.addFlashAttribute("username",username);
		return "redirect:/success";
	}
	
	
	/**
	 * �û�ע��
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping("/regist")
	public String addUser(User user,Model model){
		System.out.println("�û�ע�����"+user.getUsername()+user.getPassword());
		List<User> userList = userService.selectUserByUsernameOfReg(user.getUsername());
		if(userList.size()==0){
			String gravatarImg = Functions.getGravatar(user.getEmail());
			user.setGravatarImg(gravatarImg);
			userService.addUser(user);
			model.addAttribute("msg","ע��ɹ�");
			return "registSuccess";
		}else{
			model.addAttribute("msg","�û������ڣ�ע��ʧ��");
			return "register";
		}
	}
	/**
	 * ��֤�û���¼
	 * @param username
	 * @param password
	 * @param model
	 * @return
	 */
	@RequestMapping("/login")
	public String login(@PathParam("username") String username,
			@PathParam("password") String password,
			Model model,
			RedirectAttributes attributes){
		System.out.println("�û���¼ʱ����:"+username+"--"+password);
		
		Subject subject = SecurityUtils.getSubject();
		System.out.println(subject.isAuthenticated());
		if(subject.isAuthenticated()==false){
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			try {
				subject.login(token);
				System.out.println("��¼�ɹ�");
				Session session = subject.getSession();
	            // ���session
	            System.out.println("sessionId:" + session.getId() + ";sessionHost:" + session.getHost());
	            attributes.addFlashAttribute("msg", "��¼�ɹ�");
	            
//	            attributes.addFlashAttribute("username",username);
				return "redirect:/success";
			} catch (AuthenticationException ae) {
				// TODO: handle exception
				ae.printStackTrace();
				model.addAttribute("msg", "�û������������");
				return "login";
			}
		}else{
			return "/success";
		}
	}
	/**
	 * ��ȡ�����û��б�
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/admin/getAllUser")
	public String getAllUser(
			@RequestParam(value="pn",defaultValue="1")Integer pn,
			Model model){
		PageHelper.startPage(pn, 5);
		List<User> user = userService.findAll();
		PageInfo pageUser = new PageInfo(user);
		model.addAttribute("userList", pageUser);  
		return "/admin/allUser";
	}
	/**
	 * �༭�û�
	 * @param user
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/admin/updateUser")
	public String updateUser(User user,
			Model model,
			RedirectAttributes attributes){
		String gravatarImg = Functions.getGravatar(user.getEmail());
		user.setGravatarImg(gravatarImg);
		if(userService.update(user)){
//			user = userService.findById(user.getId());
			//request.setAttribute("user", user);
//			model.addAttribute("user", user);
//			model.addAttribute("msg", "�޸ĳɹ�");
			attributes.addFlashAttribute("msg", "�޸ĳɹ�");
			return "redirect:/admin/getAllUser";
		}else {
			model.addAttribute("msg", "�޸ĳ���");
			return "error";
		}
	}
	/**
	 * ����id��ѯ�����û�
	 * @param id
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/admin/getUser")
	public String getUser(int id,HttpServletRequest request,Model model){
		User user = userService.findById(id);
		request.setAttribute("user", user);
		model.addAttribute("user", user);
		return "/admin/editUser";
	}
	/**
	 * ����idɾ���û�
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/admin/deleteUser")
	public String deleteUser(int id,RedirectAttributes attributes){
		if(userService.delete(id)){
//			model.addAttribute("msg", "ɾ���ɹ�");
			attributes.addFlashAttribute("msg", "ɾ���ɹ�");
			return "redirect:/admin/getAllUser";
//			return "/admin/allUser";
		}else{
			return "error";
		}
	}
	@RequestMapping("/success")
	public String success(Model model){
		return "/admin/adminWelcome";
	}
	@RequestMapping("/unauthorized")
	public String unauthorized(){
		return "unauthorized";
	}
	@RequestMapping("/loginout")
	public String loginout(){
		Subject subject = SecurityUtils.getSubject();
		if(subject.isAuthenticated()){
			subject.logout();
			System.out.println("�ǳ��ɹ�");
		}
		return "redirect:login.jsp";
	}
}	
