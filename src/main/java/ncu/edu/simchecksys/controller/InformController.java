/*
 * Copyright (c) mokeeqian 2021.
 * Bugs and suggestions please contact me via mokeeqian@gmail.com
 */

package ncu.edu.simchecksys.controller;


import ncu.edu.simchecksys.entity.Inform;
import ncu.edu.simchecksys.entity.User;
import ncu.edu.simchecksys.service.InformService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Ke Qiwen
 * @Date 2022/4/1 11:18
 * @Version 1.0
 * @Copyright KernelCurry
 * @description 公共通知映射服务
 */
@Controller
@RequestMapping("/common")
public class InformController {
	private InformService informService;

	public InformController(InformService informService) {
		this.informService = informService;
	}

	/**
	 * 通知详情的ajax跳转
	 */
	@RequestMapping(value = "/inform", method = RequestMethod.GET)
	@ResponseBody
	public Map inform(Inform detailJson){
		HashMap<String,Object> resMap = new HashMap<>();
		resMap.put("code",1);
		resMap.put("id",detailJson.getId());
		return resMap;
	}
	@RequestMapping(value = "/informDetail/{id}",method = RequestMethod.GET)
	public String informDetail(Model model, @PathVariable("id") Integer id){
		//超时会变成匿名用户anonymousUser，重定向到注销状态
		Object a =  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if ("anonymousUser".equals(a.toString())){
			return "redirect:logout";
		}else {
			User user = (User)a;
			Inform inform = informService.selectInform(id);
			model.addAttribute("detail", inform);
			model.addAttribute("current", user);
			return "informDetail";
		}
	}

}
