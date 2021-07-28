package com.oxygen.managment.oxyavailibility.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.oxygen.managment.oxyavailibility.pojo.CustomUser;
import com.oxygen.managment.oxyavailibility.pojo.OxygenRequestPojo;
import com.oxygen.managment.oxyavailibility.service.OxygenRequestService;
import com.oxygen.managment.oxyavailibility.utility.ApplicationConstant;

@Controller
public class HomeController {
	/*
	 * @GetMapping("/home") public String home() { return "home"; }
	 * 
	 */
	
	@Autowired
	OxygenRequestService reqService;
	
	@GetMapping("/request")
	public String request() {
		
		return "request";
		
	}
	
	@CrossOrigin
	@PostMapping("/generate_request")
	@ResponseBody
	public OxygenRequestPojo  genrateRequest(@RequestBody OxygenRequestPojo req) {
		if(req.isStockIncreaseRequest()) {
			req.setReqStatus(ApplicationConstant.REQ_STATUS_STOCK_ADD);
			
		}else {
			req.setReqStatus(ApplicationConstant.REQ_STATUS_NOT_APPROVED);
			req.setStockIncreaseComment(ApplicationConstant.NOT_APPLICABLE);
			
		}
		
		req  = reqService.generateRequest(req);
		
		CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		return req;
		
	}
	
	
	@CrossOrigin
	@DeleteMapping("/delete_request")
	@ResponseBody
	public int deleteRequest(@RequestBody OxygenRequestPojo req) {
		
		int delstatus  = reqService.deleteRequest(req);
		
		CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return delstatus;
		
	}
	
	
	@CrossOrigin
	@GetMapping("/active_request")
	@ResponseBody
	public List<OxygenRequestPojo> getActiveRequestList() {
		
		List<OxygenRequestPojo> oxygenReqList  = reqService.activeOxygenRequest();
		
		
		CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if(user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(ApplicationConstant.ROLE_ADMIN))) {
			
		}else {
			oxygenReqList = oxygenReqList.stream().filter(elem -> elem.getUserid() == user.getId()).collect(Collectors.toList());
		}
		
		
		return oxygenReqList;
		
	}
	
	@CrossOrigin
	@PostMapping("/update_request")
	@ResponseBody
	public OxygenRequestPojo updateRequest( @RequestBody OxygenRequestPojo req) {
		
		OxygenRequestPojo oxygenReq  = reqService.updateOxygenRequest(req);
		
		
		CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return req;
		
	}
	
	
	
	
	@CrossOrigin
	@GetMapping("/admin_page")
	public String showAdminPage() {
		System.out.println("Show Admin page");
		
		
		CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return "admin_req_approval";
		
	}
	
	

	
	@CrossOrigin
	@PutMapping("/req_updation_by_admin")
	@ResponseBody
	public OxygenRequestPojo updateRequestStatus(@RequestBody OxygenRequestPojo req) {
		
		req  = reqService.updateRequestStatus(req);
		
		
		CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return req;
		
	}
	
}
