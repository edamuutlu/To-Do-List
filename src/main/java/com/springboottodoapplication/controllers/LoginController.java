package com.springboottodoapplication.controllers;

import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springboottodoapplication.models.Customer;
import com.springboottodoapplication.models.TodoItem;
import com.springboottodoapplication.repositories.ICustomerRepository;
import com.springboottodoapplication.repositories.ITodoItemRepository;
import com.springboottodoapplication.services.TodoItemService;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Controller
public class LoginController {
	
	@Autowired
	private ICustomerRepository customerRepository;
	
	@Autowired
    private ITodoItemRepository todoItemRepository;
	
	@Autowired
    private TodoItemService todoItemService;

	private final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	@GetMapping("/")
	public String home() {
		return "login";
	}

	@GetMapping("/login")
	public String login(@ModelAttribute Customer customer) {
		return "login";
	}

	@PostMapping("/customerLoggin")
	public String customerLoggin(@Valid @ModelAttribute Customer customer, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {
		List<Customer> customerList = customerRepository.findByStatus(1);

		Customer loggedCustomer = customerRepository.findByUsername(customer.getUsername());

		if (loggedCustomer == null) {
			model.addAttribute("showAbsentUserAlert", true);
			return "login";
		} else if (loggedCustomer.getStatus() == 0) {
			model.addAttribute("showAbsentUserAlert", true);
			return "login";
		} else if (loggedCustomer.getPassword().equals(customer.getPassword())) {
			if (loggedCustomer.getUsername().equals("admin")) {
				model.addAttribute("customer", customerList);
				return "redirect:/customerList";
			} else {
				model.addAttribute("customer", loggedCustomer);
				return "redirect:/userPage/" + loggedCustomer.getUsername();
			}
		} else {
			model.addAttribute("showWrongPasswordAlert", true);
			return "login";
		}

	}

	@GetMapping("/userPage/{username}")
    public String index(@PathVariable("username") String username, Model model) {
        logger.info("Request to GET userPage");
        
        Customer customer = customerRepository.findByUsername(username);
        
        List <TodoItem> todoItems = todoItemRepository.getCustomerById(customer.getCustomerId());
        model.addAttribute("todoItems", todoItems); 
        model.addAttribute("today", Instant.now().atZone(ZoneId.systemDefault()).toLocalDate().getDayOfWeek()); 
        model.addAttribute("username", customer.getUsername());
        model.addAttribute("customer", customer);
        
        return "todolist";
    }
	
	@GetMapping("/customerRegister")
	public String customerRegister() {
		return "customerRegister";
	}
	
	@PostMapping("/customerRegister")
	public String customerRegister(@Valid @ModelAttribute Customer customer, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {

		if (bindingResult.hasErrors()) {
			logger.info(">> Customer : {}", customer.toString());

			return "customerRegister";
		}
		System.out.println(customer);

		// Aynı TC numaralı, email adresli ve username adlı kullanıcı kontrolü
		List<Customer> sameTcList = customerRepository.findByStatusAndTc(1, customer.getTc());
		List<Customer> sameEmailList = customerRepository.findByStatusAndEmail(1, customer.getEmail());
		List<Customer> sameUsernameList = customerRepository.findByStatusAndUsername(1, customer.getUsername());

		boolean showTcAlert = !sameTcList.isEmpty();
		boolean showEmailAlert = !sameEmailList.isEmpty();
		boolean showUsernameAlert = !sameUsernameList.isEmpty();

		if (showTcAlert || showEmailAlert || showUsernameAlert) {
			model.addAttribute("showTcAlert", showTcAlert);
			model.addAttribute("showEmailAlert", showEmailAlert);
			model.addAttribute("showUsernameAlert", showUsernameAlert);
			return "customerRegister";
		} else {
			customer.setStatus(1);
			customerRepository.save(customer);
			return "redirect:/userPage/" + customer.getUsername();
		}

	}

	@PostMapping("/save")
	public String saveCustomer(@ModelAttribute Customer customer, Model model, RedirectAttributes redirectAttributes) {

		// Aynı TC numaralı kullanıcı kontrolü
		Customer myCustomer = customerRepository.getCustomerById(customer.getCustomerId());

		List<Customer> list = customerRepository.findByStatus(1);
		for (Customer c : list) {
			if (myCustomer.getTc().equals(customer.getTc())) {
				customer.setStatus(1);
				customerRepository.save(customer);
				model.addAttribute("showTcAlert", false);
				return "redirect:/userPage/" + myCustomer.getUsername();
			} else if (c.getTc().equals(customer.getTc())) {
				model.addAttribute("showTcAlert", true);
				model.addAttribute("customer", customer);
				return "customerEdit";
			}
		}
		customer.setStatus(1);
		customerRepository.save(customer);
		return "redirect:/userPage/" + myCustomer.getUsername();
	}
	
	// Customer Information

	@RequestMapping("/editCustomer/{customerId}")
	public String editCustomer(@PathVariable("customerId") int id, Model model) {

		Customer customer = customerRepository.getCustomerById(id);
		model.addAttribute("customer", customer);
		return "customerEdit";
	}

	@RequestMapping(path = { "/deleteCustomer/{customerId}" }, method = RequestMethod.GET)
	@Transactional
	public String deleteCustomer(@PathVariable("customerId") int customerId, Model model) {
		
		Customer customer = customerRepository.getCustomerById(customerId);
		System.out.println("silineccek customer: "+customer);
		customerRepository.delete(customer);
		todoItemService.deleteByCustomerId(customerId);
		
		return "/login";

	}
}

