package com.example.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.Entity.Employee;
import com.example.Service.EmployeeService;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

	private final EmployeeService employeeService;
	private final PasswordEncoder passwordEncoder;

	public EmployeeController(EmployeeService employeeService, PasswordEncoder passwordEncoder) {
		this.employeeService = employeeService;
		this.passwordEncoder = passwordEncoder;
	}

	// 全一覧画面表示
	@GetMapping
	public String listEmployee(Model model) {
		List<Employee> employees = employeeService.findAll();
		model.addAttribute("employees", employees);
		return "employees/list";
	}

	// 新規登録画面表示
	@GetMapping("/new")
	public String newEmployeeFrom(Model model) {
		model.addAttribute("employee", new Employee());
		model.addAttribute("departments", employeeService.findAllDepartments());
		return "employees/form";
	}

	// 新規登録
	@PostMapping
	public String saveEmployee(@Validated @ModelAttribute Employee employee, BindingResult result,
			RedirectAttributes redirectAttributes, Model model) {
		//エンティティクラスの条件を元にチェック
		if (result.hasErrors()) {
			// 部署リストを再表示
			model.addAttribute("departments", employeeService.findAllDepartments());
			return "employees/form";
		}
		employee.setPassword(passwordEncoder.encode(employee.getPassword())); //パスワードをハッシュ化して、メモリ上に保存
		employeeService.save(employee); // データベースに保存
		redirectAttributes.addFlashAttribute("message", "従業員が正常に登録または更新されました。");
		return "redirect:/employees";
	}

	//更新処理
	@PostMapping("/{id}")
	public String updateEmployee(@PathVariable Long id, @Validated @ModelAttribute Employee employee, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("departments", employeeService.findAllDepartments());
			return "employees/form";
		}
		employee.setId(id); // 更新対象の入力フォームを呼び出す。
		employee.setPassword(passwordEncoder.encode(employee.getPassword()));
		employeeService.save(employee);
		redirectAttributes.addFlashAttribute("message", "従業員が正常に更新されました。");
		return "redirect:/employees";
	}
	//詳細画面表示
//	@GetMapping("/{id}")
//	public String showEmployee(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
//		Optional<Employee> employeeOpt = employeeOpt = employeeService.findById(id);
//		if (employeeOpt.isPresent()) {
//			model.addAttribute("employee", employeeOpt.get());
//			return "employees/detail";
//		} else {
//			redirectAttributes.addFlashAttribute("errorMesseage","指定された従業員は見つかりませんでした。");
//			return "redirect:/employees";
//			}
//		}
	
	//従業員編集フォーム画面表示
	@GetMapping("/{id}/edit")
	public String editEmployeeForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
		Optional<Employee>employeeOpt = employeeService.findById(id);
		if(employeeOpt.isPresent()) {
			model.addAttribute("employee", employeeOpt.get());
			model.addAttribute("departments", employeeService.findAllDepartments());
			return "employees/form";
		} else {
			redirectAttributes.addFlashAttribute("errorMessage", "指定された従業員は見つかりませんでした。");
			return "redirect:/employees";
		}
	}
	
	//削除処理
	@PostMapping("/{id}/delete")
	public String deleteEmployee(@PathVariable Long id,RedirectAttributes redirectAttributes) {
		employeeService.deleteById(id);
		redirectAttributes.addFlashAttribute("message", "従業員が削除されました。");
		return "redirect:/employees";
	}

}
