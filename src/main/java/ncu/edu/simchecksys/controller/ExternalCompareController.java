
package ncu.edu.simchecksys.controller;

import ncu.edu.simchecksys.service.ExternalCompareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author Ke Qiwen
 * @Date 2022/4/1 11:18
 * @Version 1.0
 * @Copyright KernelCurry
 * @description 扩展工具查重服务
 */
@Controller
@RequestMapping(value = "externalCompare", method = {RequestMethod.GET, RequestMethod.POST},
produces = "application/json;charset=utf-8")
public class ExternalCompareController {
	private ExternalCompareService externalCompareService;

	@Autowired
	public ExternalCompareController(ExternalCompareService externalCompareService) {
		this.externalCompareService = externalCompareService;
	}

	@GetMapping(value="jplag")
	@ResponseBody
	public String[] jplag(@RequestParam String lang, @RequestParam int simValue,
						  HttpServletRequest httpServletRequest) {
		return externalCompareService.jplag(lang, simValue, httpServletRequest.getSession().getId());
	}

}
