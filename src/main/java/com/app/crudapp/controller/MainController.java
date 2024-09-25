package com.app.crudapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.crudapp.dto.EmployeeDto;
import com.app.crudapp.model.Employee;
import com.app.crudapp.service.EmployeeRepository;

@Controller
public class MainController {
	@Autowired
	EmployeeRepository erepo;
	
	@GetMapping("/home")
   public String showIndex(Model model)
   {
		List<Employee> emplist=erepo.findAll();//select * from employee;
		model.addAttribute("emplist",emplist);
	   return "index";
	   
   }
	@GetMapping("/home/delete")
	public String DeleteEmp(@RequestParam int id)
	{
		Employee empinfo=erepo.findById(id).get();
		erepo.delete(empinfo);
		return "redirect:/home";
	}
	@GetMapping("/home/edit")
	public String ShowEditEmployee(@RequestParam int id,Model model)
	{
		Optional<Employee> opempinfo = erepo.findById(id);
		if(opempinfo.isPresent())
		{
			model.addAttribute("opempinfo",opempinfo);
			return "editemp";

		}
		else {
			return "redirect:/home";
		}
	}
	@PostMapping("/home/edit")
	public String UpdateEmployee(@ModelAttribute("opempinfo") Employee employee){
		Employee emp =erepo.findById(employee.getEmpid()).get();
		emp.setEmpid(employee.getEmpid());
		emp.setEmpname(employee.getEmpname());
		emp.setDepartment(employee.getDepartment());
		emp.setSalary(employee.getSalary());
		erepo.save(emp);

		return "redirect:/home";
		
	}
	@GetMapping("delete")
	public String Delete(@RequestParam int id)
	{
		Employee emp=erepo.findById(id).get();
		erepo.delete(emp);
		return "redirect:/";
	}
	@GetMapping("/empreg")
	public String showRegistration(Model model)
	{
		EmployeeDto dto=new EmployeeDto();
		model.addAttribute("dto",dto);
		return "empreg";
	}
	@PostMapping("/empreg")
	public String doRegistration(@ModelAttribute EmployeeDto dto)
	{
		Employee e=new Employee();
		e.setEmpid(dto.getEmpid());
		e.setEmpname(dto.getEmpname());
		e.setDepartment(dto.getDepartment());
		e.setSalary(dto.getSalary());
		erepo.save(e);
		return "redirect:/";
	}
}
