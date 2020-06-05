package com.app.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import org.apache.commons.io.IOUtils;
import org.apache.tools.ant.taskdefs.condition.Http;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.app.pojo.AddPatient1;
import com.app.pojo.BedDetails;
import com.app.pojo.BedTypepojo;
import com.app.pojo.Beddata;
import com.app.pojo.Dcotcorpojo;
import com.app.pojo.Departmentpojo;
import com.app.pojo.Doctordetail;
import com.app.pojo.DoctotNames;
import com.app.pojo.DuePatient;
import com.app.pojo.Hospital;
import com.app.pojo.InpatientPojo;
import com.app.pojo.Laboratory;
import com.app.pojo.Locationpojo;
import com.app.pojo.Locationpojo1;
import com.app.pojo.MainDepartmentpojo;
import com.app.pojo.Mlcpojo;
import com.app.pojo.NewSupplierInfoPojo;
import com.app.pojo.OutsideConultPojo;
import com.app.pojo.Packing;
import com.app.pojo.PatientPojo;
import com.app.pojo.Patientdetail;
import com.app.pojo.Pharmacy;
import com.app.pojo.Product;
import com.app.pojo.ProductDetail;
import com.app.pojo.ProductType;
import com.app.pojo.ProductType99;
import com.app.pojo.PurchaseInvoiceePojo;
import com.app.pojo.PurchaseInvoiceeReturns;
import com.app.pojo.RegisterPojo;
import com.app.pojo.RoomPojo;
import com.app.pojo.RoomTarrif;
import com.app.pojo.RoomTypePojo;
import com.app.pojo.SalesEntryPojo;
import com.app.pojo.SalesEntryReturns;
import com.app.pojo.StockDetails;
import com.app.pojo.StockPostion;
import com.app.pojo.Supplier;
import com.app.pojo.dept;
import com.app.pojo.entry;
import com.app.service.BedService;
import com.app.service.BedTypeService;
import com.app.service.DepartmentService;
import com.app.service.Doctorservice;
import com.app.service.DuePatientService;
import com.app.service.HospitalService;
import com.app.service.InPatientService;
import com.app.service.LaboratoryService;
import com.app.service.LocationService;
import com.app.service.MLCService;
import com.app.service.OutpatientService;
import com.app.service.OutsideService;
import com.app.service.PackingService;
import com.app.service.PatientService;
import com.app.service.PatientServiceDetails;
import com.app.service.PatientServise1;
import com.app.service.PharmacyService;
import com.app.service.ProductDetailService;
import com.app.service.ProductService;
import com.app.service.ProductTypeService;
import com.app.service.PurchaseInvoiceReturnsService;
import com.app.service.PurchaseInvoiceService;
import com.app.service.RoomTypeService;
import com.app.service.SalesEntryService;
import com.app.service.SalesReturnsService;
import com.app.service.StockAdjustmentService;
import com.app.service.StockPositionService;
import com.app.service.SupplierService;
import com.app.service.TarrifService;
import com.app.service.serviceinterface;
import com.app.pojo.Employee;
import com.app.service.EmployeeService;

@Controller
public class ProductController {
	@Autowired
	private ProductService productService;

	@RequestMapping("/loginapplication")
	public ModelAndView loginapplication(
			@ModelAttribute("register") RegisterPojo register,
			BindingResult result) {
		return new ModelAndView("Login");
	}

	@RequestMapping("/loginapplicationAdmin")
	public ModelAndView LoginAdminDetails(
			@ModelAttribute("register") RegisterPojo register,
			BindingResult result) {

		System.out.println("am in admin first");
		ArrayList<String> userTypeDetails = new ArrayList<String>();
		userTypeDetails.add("Admin");
		userTypeDetails.add("Employee");
		userTypeDetails.add("Others");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("userTypeDetails", userTypeDetails);
		return new ModelAndView("LoginAdmin", model);
	}

	@RequestMapping("/loginapplicationSecondAdmin")
	public ModelAndView loginapplicationSecondAdmin(
			@ModelAttribute("register") RegisterPojo register,
			BindingResult result) {

		String dummy = register.getUserTypeDetails();

		if (dummy.equals("Admin")) {

			boolean b = productService.checkuserEmptyOrNot();

			if (b) {
				String userName1 = register.getUsername();
				String password = register.getPassword();
				if (userName1.equalsIgnoreCase("Admin")
						&& password.equalsIgnoreCase("sriL@kshmI")) {

					ArrayList<String> userName = new ArrayList<String>();
					userName.add(register.getUsername());
					Map<String, Object> model = new HashMap<String, Object>();
					model.put("userName", userName);
					return new ModelAndView("index1", model);
				} else {

					return new ModelAndView(
							"redirect:/loginapplicationPasswordWrong.html");
				}
			} else {
				
				
				boolean b1 = productService.checkuserEmptyOrNotIsNotEmpty(register);
				
				if(b1){
					ArrayList<String> userName = new ArrayList<String>();
					userName.add(register.getUsername());
					Map<String, Object> model = new HashMap<String, Object>();
					model.put("userName", userName);
					return new ModelAndView("index1", model);
				}else{
					return new ModelAndView(
							"redirect:/loginapplicationPasswordWrong.html");
				}
				
				

			}

		} else if (dummy.equals("Others")) {

			String userName1 = register.getUsername();
			String password = register.getPassword();
			if (userName1.equalsIgnoreCase("Signalss123")
					&& password.equalsIgnoreCase("Sign@l$$123")) {

				ArrayList<String> userName = new ArrayList<String>();
				userName.add(register.getUsername());
				Map<String, Object> model = new HashMap<String, Object>();
				model.put("userName", userName);
				return new ModelAndView("index1", model);
			} else {

				return new ModelAndView(
						"redirect:/loginapplicationPasswordWrong.html");
			}

		} else {

			boolean isValid = productService.checkBoolean(register);

			if (isValid) {

				ArrayList<String> userName = new ArrayList<String>();
				userName.add(register.getUsername());
				Map<String, Object> model = new HashMap<String, Object>();
				model.put("userName", userName);
				return new ModelAndView("index", model);
			} else {

				return new ModelAndView(
						"redirect:/loginapplicationPasswordWrong.html");
			}

		}
	}

	@RequestMapping("/resetPassword")
	public ModelAndView resetPassword(
			@ModelAttribute("register") RegisterPojo register,
			BindingResult result) {
		return new ModelAndView("Admin");
	}

	@RequestMapping("/resetPasswordSecond")
	public ModelAndView resetPasswordSecond(
			@ModelAttribute("register") RegisterPojo register,
			BindingResult result) {

		productService.changeAdminPwdUserName(register);

		ArrayList<String> msg = new ArrayList<String>();
		msg.add("Your user name and Password changed successfully");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("msg", msg);
		return new ModelAndView("loginapplicationAdmin", model);
	}

	@RequestMapping("/loginapplicationSecond")
	public ModelAndView loginapplicationSecond(
			@ModelAttribute("register") RegisterPojo register,
			BindingResult result) {

		boolean isValid = productService.checkBoolean(register);

		if (isValid) {

			ArrayList<String> userName = new ArrayList<String>();
			userName.add(register.getUsername());
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("userName", userName);
			return new ModelAndView("index", model);
		} else {

			return new ModelAndView(
					"redirect:/loginapplicationPasswordWrong.html");
		}
	}

	@RequestMapping("/lockScreenApplicationinapplicationSecond")
	public ModelAndView lockScreenApplicationinapplicationSecond(
			@ModelAttribute("register") RegisterPojo register,
			BindingResult result) {
		System.out.println("am in first time in controller");

		boolean isValid = productService.checkBoolean(register);

		if (isValid) {
			System.out.println("login success");
			ArrayList<String> userName = new ArrayList<String>();
			userName.add(register.getUsername());
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("userName", userName);
			return new ModelAndView("index", model);
		} else {
			System.out.println("login failure");
			ArrayList<String> userName = new ArrayList<String>();
			userName.add(register.getUsername());
			ArrayList<String> msg = new ArrayList<String>();
			msg.add("Wrong Password");
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("userName", userName);
			model.put("msg", msg);
			return new ModelAndView("lockscreen", model);
		}
	}

	@RequestMapping("/lockScreenApplicationinapplicationSecond1")
	public ModelAndView lockScreenApplicationinapplicationSecond1(
			@ModelAttribute("register") RegisterPojo register,
			BindingResult result) {
		System.out.println("am in first time in controller");

		boolean isValid = productService.checkBoolean(register);

		if (isValid) {
			System.out.println("login success");
			ArrayList<String> userName = new ArrayList<String>();
			userName.add(register.getUsername());
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("userName", userName);
			return new ModelAndView("reception", model);
		} else {
			System.out.println("login failure");
			ArrayList<String> userName = new ArrayList<String>();
			userName.add(register.getUsername());
			ArrayList<String> msg = new ArrayList<String>();
			msg.add("Wrong Password");
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("userName", userName);
			model.put("msg", msg);
			return new ModelAndView("lockscreen1", model);
		}
	}

	@RequestMapping("/lockScreenApplicationinapplicationSecond2")
	public ModelAndView lockScreenApplicationinapplicationSecond2(
			@ModelAttribute("register") RegisterPojo register,
			BindingResult result) {
		System.out.println("am in first time in controller");

		boolean isValid = productService.checkBoolean(register);

		if (isValid) {
			System.out.println("login success");
			ArrayList<String> userName = new ArrayList<String>();
			userName.add(register.getUsername());
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("userName", userName);
			return new ModelAndView("Doctor", model);
		} else {
			System.out.println("login failure");
			ArrayList<String> userName = new ArrayList<String>();
			userName.add(register.getUsername());
			ArrayList<String> msg = new ArrayList<String>();
			msg.add("Wrong Password");
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("userName", userName);
			model.put("msg", msg);
			return new ModelAndView("lockscreen2", model);
		}
	}

	@RequestMapping("/lockScreenApplicationinapplicationSecond3")
	public ModelAndView lockScreenApplicationinapplicationSecond3(
			@ModelAttribute("register") RegisterPojo register,
			BindingResult result) {
		System.out.println("am in first time in controller");

		boolean isValid = productService.checkBoolean(register);

		if (isValid) {
			System.out.println("login success");
			ArrayList<String> userName = new ArrayList<String>();
			userName.add(register.getUsername());
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("userName", userName);
			return new ModelAndView("ward", model);
		} else {
			System.out.println("login failure");
			ArrayList<String> userName = new ArrayList<String>();
			userName.add(register.getUsername());
			ArrayList<String> msg = new ArrayList<String>();
			msg.add("Wrong Password");
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("userName", userName);
			model.put("msg", msg);
			return new ModelAndView("lockscreen3", model);
		}
	}

	@RequestMapping("/lockScreenApplicationinapplicationSecond4")
	public ModelAndView lockScreenApplicationinapplicationSecond4(
			@ModelAttribute("register") RegisterPojo register,
			BindingResult result) {
		System.out.println("am in first time in controller");

		boolean isValid = productService.checkBoolean(register);

		if (isValid) {
			System.out.println("login success");
			ArrayList<String> userName = new ArrayList<String>();
			userName.add(register.getUsername());
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("userName", userName);
			return new ModelAndView("pharmacy", model);
		} else {
			System.out.println("login failure");
			ArrayList<String> userName = new ArrayList<String>();
			userName.add(register.getUsername());
			ArrayList<String> msg = new ArrayList<String>();
			msg.add("Wrong Password");
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("userName", userName);
			model.put("msg", msg);
			return new ModelAndView("lockscreen4", model);
		}
	}

	@RequestMapping("/lockScreenApplicationinapplicationSecond5")
	public ModelAndView lockScreenApplicationinapplicationSecond5(
			@ModelAttribute("register") RegisterPojo register,
			BindingResult result) {
		System.out.println("am in first time in controller");

		boolean isValid = productService.checkBoolean(register);

		if (isValid) {
			System.out.println("login success");
			ArrayList<String> userName = new ArrayList<String>();
			userName.add(register.getUsername());
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("userName", userName);
			return new ModelAndView("store", model);
		} else {
			System.out.println("login failure");
			ArrayList<String> userName = new ArrayList<String>();
			userName.add(register.getUsername());
			ArrayList<String> msg = new ArrayList<String>();
			msg.add("Wrong Password");
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("userName", userName);
			model.put("msg", msg);
			return new ModelAndView("lockscreen5", model);
		}
	}

	@RequestMapping("/lockScreenApplicationinapplicationSecond6")
	public ModelAndView lockScreenApplicationinapplicationSecond6(
			@ModelAttribute("register") RegisterPojo register,
			BindingResult result) {
		System.out.println("am in first time in controller");

		boolean isValid = productService.checkBoolean(register);

		if (isValid) {
			System.out.println("login success");
			ArrayList<String> userName = new ArrayList<String>();
			userName.add(register.getUsername());
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("userName", userName);
			return new ModelAndView("setting", model);
		} else {
			System.out.println("login failure");
			ArrayList<String> userName = new ArrayList<String>();
			userName.add(register.getUsername());
			ArrayList<String> msg = new ArrayList<String>();
			msg.add("Wrong Password");
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("userName", userName);
			model.put("msg", msg);
			return new ModelAndView("lockscreen6", model);
		}
	}

	@RequestMapping("/lockScreen")
	public ModelAndView lockScreen(
			@ModelAttribute("register") RegisterPojo register,
			BindingResult result) {
		System.out.println("am in first time in controller");
		ArrayList<String> userName = new ArrayList<String>();
		userName.add(register.getUsername());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("userName", userName);
		System.out.println(model);
		return new ModelAndView("lockscreen", model);
	}

	@RequestMapping("/loginapplicationPasswordWrong")
	public ModelAndView loginapplicationPasswordWrong(
			@ModelAttribute("register") RegisterPojo register,
			BindingResult result) {

		ArrayList<String> failureMsg = new ArrayList<String>();
		failureMsg.add("YOUR CREDENTIALS WRONG");
		ArrayList<String> userTypeDetails = new ArrayList<String>();
		userTypeDetails.add("Admin");
		userTypeDetails.add("Employee");
		userTypeDetails.add("Others");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("msg", failureMsg);
		model.put("userTypeDetails", userTypeDetails);

		return new ModelAndView("LoginAdmin", model);
	}

	@RequestMapping("/lockScreen1")
	public ModelAndView lockScreen1(
			@ModelAttribute("register") RegisterPojo register,
			BindingResult result) {
		System.out.println("am in first time in controller");
		ArrayList<String> userName = new ArrayList<String>();
		userName.add(register.getUsername());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("userName", userName);
		System.out.println(model);
		return new ModelAndView("lockscreen1", model);
	}

	@RequestMapping("/lockScreen2")
	public ModelAndView lockScreen2(
			@ModelAttribute("register") RegisterPojo register,
			BindingResult result) {
		System.out.println("am in first time in controller");
		ArrayList<String> userName = new ArrayList<String>();
		userName.add(register.getUsername());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("userName", userName);
		System.out.println(model);
		return new ModelAndView("lockscreen2", model);
	}

	@RequestMapping("/addUnit")
	public ModelAndView getRegisterForm(@ModelAttribute("p") Product p,
			BindingResult result) {

		System.out.println("adding unit Form");
		ArrayList<Integer> maxIdUOM = new ArrayList<Integer>();
		maxIdUOM.add(productService.getMaxIdOfUOM());

		ArrayList<Integer> al = new ArrayList<Integer>();
		al.add(productService.getId());

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("p", productService.listProduct());

		model.put("maxIdUOM", maxIdUOM);
		return new ModelAndView("UOM2", "model", model);
	}

	@RequestMapping(value = "/userListUOM")
	public ModelAndView getUser(@ModelAttribute("p") Product p,
			BindingResult result) {
		System.out.println("we are in showlist");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("p", productService.listProduct());
		System.out.println(model);
		return new ModelAndView("UOM1", model);

	}

	@RequestMapping("/saveUserUom2")
	public ModelAndView saveUserData(@ModelAttribute("p") Product p,
			BindingResult result) {

		boolean isValidUser = productService.saveProduct(p);
		if (isValidUser) {
			return new ModelAndView("UOM4");
		} else {
			Map<String, Object> model = new HashMap<String, Object>();
			ArrayList<String> msg = new ArrayList<String>();
			msg.add("Supplier Type already exist try another");
			model.put("msg", msg);
			return new ModelAndView("redirect:/userListUOMmsg.html");
		}

	}

	@RequestMapping(value = "/userListUOMmsg")
	public ModelAndView getUserMsg(@ModelAttribute("p") Product p,
			BindingResult result) {

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("p", productService.listProduct());
		ArrayList<String> msg = new ArrayList<String>();
		msg.add("Record Inserted Sucessfully");
		model.put("msg", msg);
		System.out.println(model);
		return new ModelAndView("UOM1", model);

	}

	@RequestMapping(value = "/updateUOM")
	public ModelAndView UpdateGet(@ModelAttribute("p") Product p,
			BindingResult result) {

		System.out.println("we are in updateGet");
		ArrayList<Integer[]> id = new ArrayList<Integer[]>();
		id.add(productService.ids());

		ArrayList<Integer> customerName1 = new ArrayList<Integer>();
		for (Integer[] s : id) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				customerName1.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}
		System.out.println(p.getUnitId());
		System.out.println("am in update controller");
		System.out.println(p.getUnitId());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("p", productService.getProduct(p.getUnitId()));
		model.put("user1", productService.listProduct());
		model.put("customerName1", customerName1);
		System.out.println("am in update controller");
		System.out.println(model);

		return new ModelAndView("UOM3", "model", model);
	}

	@RequestMapping("/saveUpdateUOM")
	public ModelAndView saveUserUpdate(@ModelAttribute("p") Product p,
			BindingResult result) {
		productService.updateProduct(p);
		return new ModelAndView("redirect:/afterUpdateuserListUOM.html");
	}

	@RequestMapping(value = "/afterUpdateuserListUOM")
	public ModelAndView afterUpdateuserListUOM(@ModelAttribute("p") Product p,
			BindingResult result) {
		System.out.println("we are in showlist");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("p", productService.listProduct());

		ArrayList<String> updateMsg = new ArrayList<String>();
		updateMsg.add("Record Updated Sucessfully");
		model.put("updateMsg", updateMsg);
		System.out.println(model);
		return new ModelAndView("UOM1", model);

	}

	// DELETE
	@RequestMapping("/deleteUOM")
	public ModelAndView deleteEmployee(@ModelAttribute("p") Product p,
			BindingResult result) {
		System.out.println("im in deleted controler");

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("p", productService.listProduct());
		productService.deleteUoM(p);
		System.out.println("delete Employee");
		return new ModelAndView("redirect:/afterDeleteuserListUOM.html");
	}

	@RequestMapping(value = "/afterDeleteuserListUOM")
	public ModelAndView aftedeleteuserListDom(@ModelAttribute("p") Product p,
			BindingResult result) {
		System.out.println("we are in showlist");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("p", productService.listProduct());

		ArrayList<String> updateMsg = new ArrayList<String>();
		updateMsg.add("Records Deleted Sucessfully");
		model.put("updateMsg", updateMsg);

		System.out.println(model);
		return new ModelAndView("UOM1", model);

	}

	/* PRODUCT SEARCH */

	@RequestMapping("/searchProductUOM")
	public ModelAndView getRegisterForm1(@ModelAttribute("p") Product p,
			BindingResult result) {
		System.out.println("am in Product Search Controller");

		ArrayList<String[]> userId = new ArrayList<String[]>();
		userId.add(productService.getIdc());
		ArrayList<String> userId1 = new ArrayList<String>();
		for (String[] s : userId) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				userId1.add(s[j]);
			}
		}

		System.out.println(p.getUnitName());

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("userId1", userId1);
		model.put("p", productService.getParticularProduct(p));
		System.out.println(model);
		return new ModelAndView("UOM1", model);
	}

	@Autowired
	private PackingService packingService;

	/* PACKING REGISTRATION */
	@RequestMapping("/registerPacking")
	public ModelAndView getRegisterForm(@ModelAttribute("pac") Packing pac,
			BindingResult result) {
		System.out.println("am in first step controller");
		System.out.println("Register Form");

		ArrayList<Integer> al = new ArrayList<Integer>();
		al.add(packingService.getId4());
		ArrayList<Integer> maxId = new ArrayList<Integer>();
		maxId.add(packingService.getMaxIdOfPacking());

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("pac", packingService.listPacking());
		model.put("maxId", maxId);
		return new ModelAndView("PackingDetails2", model);
	}

	@RequestMapping(value = "/userList4")
	public ModelAndView getUser(@ModelAttribute("pac") Packing pac,
			BindingResult result) {
		System.out.println("we are in showlist");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("pac", packingService.listPacking());
		System.out.println(model);
		return new ModelAndView("PackingDetails1", model);

	}

	@RequestMapping("/savePacking")
	public ModelAndView saveUserData(@ModelAttribute("pac") Packing pac,
			BindingResult result) {

		boolean isValidUser = packingService.savePacking(pac);
		if (isValidUser) {

			return new ModelAndView("PackingDetails4");
		} else {
			return new ModelAndView("redirect:/userList4Mg.html");
		}
	}

	@RequestMapping(value = "/userList4Mg")
	public ModelAndView userList4Mg(@ModelAttribute("pac") Packing pac,
			BindingResult result) {
		System.out.println("we are in showlist");

		Map<String, Object> model = new HashMap<String, Object>();

		ArrayList<String> msg = new ArrayList<String>();
		msg.add("Record Inserted Successfully");
		model.put("msg", msg);

		model.put("pac", packingService.listPacking());
		System.out.println(model);
		return new ModelAndView("PackingDetails1", model);

	}

	/* PACKING UPDATE */

	@RequestMapping(value = "/updatePack")
	public ModelAndView UpdateGet(@ModelAttribute("pac") Packing pac,
			BindingResult result) {

		System.out.println("we are in updateGet");
		ArrayList<Integer[]> id = new ArrayList<Integer[]>();
		id.add(packingService.ids4());

		ArrayList<Integer> customerName1 = new ArrayList<Integer>();
		for (Integer[] s : id) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				customerName1.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}
		System.out.println(pac.getPackId());
		System.out.println("am in update controller");
		System.out.println(pac.getPackId());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("pac", packingService.getPacking(pac.getPackId()));
		model.put("user1", packingService.listPacking());
		model.put("customerName1", customerName1);
		System.out.println("am in update controller");
		System.out.println(model);

		return new ModelAndView("PackingUpdate", "model", model);
	}

	@RequestMapping("/Updatepacking")
	public ModelAndView saveUserUpdate(@ModelAttribute("pac") Packing pac,
			BindingResult result) {
		packingService.updatePacking(pac);
		return new ModelAndView("redirect:/afterupdatingUserList.html");
	}

	@RequestMapping(value = "/afterupdatingUserList")
	public ModelAndView afterupdatingUserList(
			@ModelAttribute("pac") Packing pac, BindingResult result) {
		System.out.println("we are in showlist");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("pac", packingService.listPacking());

		ArrayList<String> updateMsg = new ArrayList<String>();
		updateMsg.add("Record Updated Sucessfully");
		model.put("updateMsg", updateMsg);

		System.out.println(model);
		return new ModelAndView("PackingDetails1", model);

	}

	/* PACKING SEARCH */

	@RequestMapping("/searchPacking")
	public ModelAndView getRegisterForm1(@ModelAttribute("pac") Packing pac,
			BindingResult result) {
		System.out.println("am in Product Search Controller");

		ArrayList<String[]> userId = new ArrayList<String[]>();
		userId.add(packingService.getIdc4());
		ArrayList<String> userId1 = new ArrayList<String>();
		for (String[] s : userId) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				userId1.add(s[j]);
			}
		}

		System.out.println(pac.getPackName());

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("userId1", userId1);
		model.put("pac", packingService.getParticularPackingProduct(pac));
		System.out.println(model);
		return new ModelAndView("PackingDetails1", model);
	}

	// DELETE
	@RequestMapping("/deletepack")
	public ModelAndView deleteEmployee(@ModelAttribute("pac") Packing pac,
			BindingResult result) {
		System.out.println("im in deleted controler");

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("pac", packingService.listPacking());
		packingService.deletePacking(pac);
		System.out.println("delete Employee");
		return new ModelAndView("redirect:/afterdeleteingUserList.html");
	}

	@RequestMapping(value = "/afterdeleteingUserList")
	public ModelAndView afterdeleteingUserList(
			@ModelAttribute("pac") Packing pac, BindingResult result) {
		System.out.println("we are in showlist");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("pac", packingService.listPacking());

		ArrayList<String> updateMsg = new ArrayList<String>();
		updateMsg.add("Records Deleted Sucessfully");
		model.put("updateMsg", updateMsg);

		System.out.println(model);
		return new ModelAndView("PackingDetails1", model);

	}

	@Autowired
	private ProductTypeService productTypeService;

	/* PRODUCT TYPE REGISTRATION */

	@RequestMapping("/AddProduct")
	public ModelAndView getRegisterForm(@ModelAttribute("pt") ProductType pt,
			BindingResult result) {
		System.out.println("am in first step controller");
		System.out.println("Register Form");
		ArrayList<String> card = new ArrayList<String>();
		card.add("Drug");
		card.add("General");
		ArrayList<Integer> maxId = new ArrayList<Integer>();
		maxId.add(productTypeService.getMaxId());

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("pt", productTypeService.listProduct1());
		model.put("card", card);
		model.put("maxId", maxId);
		ArrayList<Integer> al = new ArrayList<Integer>();
		al.add(productTypeService.getId1());
		System.out.println(model);
		return new ModelAndView("ProductType2", "model", model);
	}

	@RequestMapping(value = "/userListProductTypeDetailsJsp")
	public ModelAndView getUser(@ModelAttribute("pt") ProductType pt,
			BindingResult result) {
		System.out.println("we are in showlist");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("pt", productTypeService.listProduct1());
		System.out.println(model);
		return new ModelAndView("ProductType1", model);

	}

	@RequestMapping("/saveUsertype")
	public ModelAndView saveUserData(@ModelAttribute("pt") ProductType pt,
			BindingResult result) {

		ArrayList<String> card = new ArrayList<String>();
		card.add("Drug");
		card.add("General");
		ArrayList<Integer> maxId = new ArrayList<Integer>();
		maxId.add(productTypeService.getMaxId());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("card", card);
		model.put("maxId", maxId);
		boolean isValidUser = productTypeService.saveProduct1(pt);
		if (isValidUser) {
			return new ModelAndView("ProductType4", "model", model);
		} else {

			return new ModelAndView(
					"redirect:/userListProductTypeDetailsJspMsg.html");
		}

	}

	@RequestMapping(value = "/userListProductTypeDetailsJspMsg")
	public ModelAndView userListProductTypeDetailsJspMsg(
			@ModelAttribute("pt") ProductType pt, BindingResult result) {
		System.out.println("we are in showlist");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("pt", productTypeService.listProduct1());

		ArrayList<String> msg = new ArrayList<String>();
		msg.add("Record Inserted Successfully");
		model.put("msg", msg);
		System.out.println(model);
		return new ModelAndView("ProductType1", model);

	}

	/* PRODUCT Type UPDATE */

	@RequestMapping(value = "/updateProductType")
	public ModelAndView UpdateGet(@ModelAttribute("pt") ProductType pt,
			BindingResult result) {

		System.out.println("we are in updateGet");
		ArrayList<Integer[]> id = new ArrayList<Integer[]>();
		id.add(productTypeService.ids1());

		ArrayList<Integer> customerName1 = new ArrayList<Integer>();
		for (Integer[] s : id) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				customerName1.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}

		ArrayList<String> card = new ArrayList<String>();
		card.add("Drug");
		card.add("General");
		System.out.println(pt.getId());
		System.out.println("am in update controller");
		System.out.println(pt.getId());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("update", productTypeService.getProduct1(pt.getId()));
		model.put("pt", productTypeService.listProduct1());
		model.put("card", card);
		model.put("customerName1", customerName1);
		System.out.println("am in update controller");
		System.out.println(model);

		return new ModelAndView("ProductType3", "model", model);
	}

	@RequestMapping("/saveUpdate")
	public ModelAndView saveUserUpdate(@ModelAttribute("pt") ProductType pt,
			BindingResult result) {
		productTypeService.updateProduct1(pt);
		return new ModelAndView(
				"redirect:/afterUserListProductTypeUpdateProductType1.html");

	}

	@RequestMapping(value = "/afterUserListProductTypeUpdateProductType1")
	public ModelAndView afterUserListProductTypeUpdateProductType1(
			@ModelAttribute("pt") ProductType pt, BindingResult result) {
		System.out.println("we are in showlist");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("pt", productTypeService.listProduct1());
		ArrayList<String> updateMsg = new ArrayList<String>();
		updateMsg.add("Record Updated Sucessfully");
		model.put("msg", updateMsg);
		System.out.println(model);
		return new ModelAndView("ProductType1", model);

	}

	@RequestMapping(value = "/afterUserListProductTypeDeleteProductType1")
	public ModelAndView afterUserListProductTypeDeleteProductType1(
			@ModelAttribute("pt") ProductType pt, BindingResult result) {
		System.out.println("we are in showlist");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("pt", productTypeService.listProduct1());
		ArrayList<String> updateMsg = new ArrayList<String>();
		updateMsg.add("Record Deleted Sucessfully");
		model.put("updateMsg", updateMsg);
		System.out.println(model);
		return new ModelAndView("ProductType1", model);

	}

	/* PRODUCT TYPE SEARCH */

	@RequestMapping("/searchProductType")
	public ModelAndView getRegisterForm1(@ModelAttribute("pt") ProductType pt,
			BindingResult result) {
		System.out.println("am in Product Search Controller");

		ArrayList<String[]> userId = new ArrayList<String[]>();
		userId.add(productTypeService.getIdc1());
		ArrayList<String> userId1 = new ArrayList<String>();
		for (String[] s : userId) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				userId1.add(s[j]);
			}
		}

		System.out.println(pt.getName());

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("userId1", userId1);
		model.put("pt", productTypeService.getParticularProduct1(pt));
		System.out.println(model);
		return new ModelAndView("ProductType1", model);
	}

	// DELETE
	@RequestMapping("/deleteProductType")
	public ModelAndView deleteEmployee(@ModelAttribute("pt") ProductType pt,
			BindingResult result) {
		System.out.println("im in deleted controler");

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("pd", productTypeService.listProduct1());
		productTypeService.deleteProductType(pt);
		System.out.println("delete Employee");
		return new ModelAndView(
				"redirect:/afterUserListProductTypeDeleteProductType1.html");
	}

	@Autowired
	private ProductDetailService productDetailService;

	/* ProductDetail REGISTRATION */

	@RequestMapping("/controllerProductdetails")
	public ModelAndView getRegisterForm(@ModelAttribute("pd") ProductDetail pd,
			BindingResult result) {
		System.out.println("am in first step controller");
		System.out.println("Register Form");

		ArrayList<String> VATTAX = new ArrayList<String>();
		VATTAX.add("5");
		VATTAX.add("14");
		VATTAX.add("15");

		ArrayList<String[]> userId = new ArrayList<String[]>();
		userId.add(productDetailService.getIdcProductDetail());
		ArrayList<String> userId2 = new ArrayList<String>();
		for (String[] s1 : userId) {
			int i = s1.length;
			for (int j = 0; j < i; j++) {
				userId2.add(s1[j]);
			}
		}

		ArrayList<Integer> maxId = new ArrayList<Integer>();
		maxId.add(productDetailService.getMaxIdOfProductDetails());

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("pd", productDetailService.listProductDetail());
		model.put("maxId", maxId);
		model.put("userId2", userId2);
		model.put("MyFormType2", productDetailService.listMyform());
		model.put("MyFormPack2", productDetailService.listMyform2());
		model.put("MyFormUnit2", productDetailService.listMyform3());
		model.put("VATTAX", VATTAX);
		ArrayList<Integer> al = new ArrayList<Integer>();
		al.add(productDetailService.getIdProductDetail());
		System.out.println(model);
		return new ModelAndView("ProductDetails2", "model", model);
	}

	@RequestMapping(value = "/userListProductDetail")
	public ModelAndView getUser(@ModelAttribute("pd") ProductDetail pd,
			BindingResult result) {
		System.out.println("we are in showlist");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("pd", productDetailService.listProductDetail());
		System.out.println(model);
		return new ModelAndView("ProductDetails1", model);

	}

	@RequestMapping("/saveProductDetail")
	public ModelAndView saveUserData(@ModelAttribute("pd") ProductDetail pd,
			BindingResult result) {

		ArrayList<String> VATTAX = new ArrayList<String>();
		VATTAX.add("5");
		VATTAX.add("14");
		VATTAX.add("15");
		ArrayList<String[]> userId = new ArrayList<String[]>();
		userId.add(productDetailService.getIdcProductDetail());
		ArrayList<String> userId2 = new ArrayList<String>();
		for (String[] s1 : userId) {
			int i = s1.length;
			for (int j = 0; j < i; j++) {
				userId2.add(s1[j]);
			}
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("pd", productDetailService.listProductDetail());
		model.put("userId2", userId2);
		model.put("MyFormType2", productDetailService.listMyform());
		model.put("MyFormPack2", productDetailService.listMyform2());
		model.put("MyFormUnit2", productDetailService.listMyform3());
		model.put("VATTAX", VATTAX);
		ArrayList<Integer> al = new ArrayList<Integer>();
		al.add(productDetailService.getIdProductDetail());
		System.out.println(model);
		boolean isValidUser = productDetailService.saveProductDetail(pd);
		if (isValidUser) {
			return new ModelAndView("ProductDetails4", "model", model);
		} else {
			return new ModelAndView("redirect:/userListProductDetailmsg.html");
		}

	}

	@RequestMapping(value = "/userListProductDetailmsg")
	public ModelAndView getUserMsg(@ModelAttribute("pd") ProductDetail pd,
			BindingResult result) {

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("pd", productDetailService.listProductDetail());
		ArrayList<String> msg = new ArrayList<String>();
		msg.add("Record Inserted Successfully ");
		model.put("msg", msg);
		return new ModelAndView("ProductDetails1", model);

	}

	/* ProductDetail UPDATE */

	@RequestMapping(value = "/updateProductDetails")
	public ModelAndView UpdateGet(@ModelAttribute("pd") ProductDetail pd,
			BindingResult result) {

		System.out.println("we are in updateGet");
		ArrayList<Integer[]> id = new ArrayList<Integer[]>();
		id.add(productDetailService.idsProductDetail());

		ArrayList<Integer> customerName1 = new ArrayList<Integer>();
		for (Integer[] s2 : id) {
			int i = s2.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s2.length);
				customerName1.add(s2[j]);
				System.out.println(j + " " + s2[j]);
				System.out
						.println("am in controller retrive for each supplierName");
			}
		}

		ArrayList<String> VATTAX = new ArrayList<String>();
		VATTAX.add("5");
		VATTAX.add("14");
		VATTAX.add("15");
		System.out.println(pd.getProCode());
		System.out.println("am in update controller");
		System.out.println(pd.getProCode());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("update",
				productDetailService.getProductDetail(pd.getProCode()));
		model.put("pd", productDetailService.listProductDetail());
		model.put("MyFormType2", productDetailService.listMyform());
		model.put("MyFormPack2", productDetailService.listMyform2());
		model.put("MyFormUnit2", productDetailService.listMyform3());
		model.put("VATTAX", VATTAX);
		model.put("customerName1", customerName1);
		System.out.println("am in update controller");
		System.out.println(model);

		return new ModelAndView("ProductDetails3", "model", model);
	}

	@RequestMapping("/updateProduct")
	public ModelAndView saveUserUpdate(@ModelAttribute("pd") ProductDetail pd,
			BindingResult result) {

		productDetailService.updateProductDetail(pd);

		return new ModelAndView(
				"redirect:/afterUserListProductdetailupdate.html");

	}

	@RequestMapping(value = "/afterUserListProductdetailupdate")
	public ModelAndView afterUserListProductdetailupdate(
			@ModelAttribute("pd") ProductDetail pd, BindingResult result) {
		System.out.println("we are in showlist");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("pd", productDetailService.listProductDetail());
		ArrayList<String> updateMsg = new ArrayList<String>();
		updateMsg.add("Record Updated Successfully");
		model.put("updateMsg", updateMsg);
		System.out.println(model);
		return new ModelAndView("ProductDetails1", model);

	}

	@RequestMapping(value = "/afterUserListProductdetaildelete")
	public ModelAndView afterUserListProductdetaildelete(
			@ModelAttribute("pd") ProductDetail pd, BindingResult result) {
		System.out.println("we are in showlist");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("pd", productDetailService.listProductDetail());
		ArrayList<String> updateMsg = new ArrayList<String>();
		updateMsg.add("Record Deleted Successfully");
		model.put("updateMsg", updateMsg);
		System.out.println(model);
		return new ModelAndView("ProductDetails1", model);

	}

	/* ProductDetail SEARCH */

	@RequestMapping("/searchProductDetail")
	public ModelAndView getRegisterForm1(
			@ModelAttribute("pd") ProductDetail pd, BindingResult result) {
		System.out.println("am in Product Search Controller");

		ArrayList<String[]> userId = new ArrayList<String[]>();
		userId.add(productDetailService.getIdcProductDetail());
		ArrayList<String> userId1 = new ArrayList<String>();
		for (String[] s1 : userId) {
			int i = s1.length;
			for (int j = 0; j < i; j++) {
				userId1.add(s1[j]);
			}
		}

		System.out.println(pd.getProName());

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("userId1", userId1);
		model.put("pd", productDetailService.getParticularProductDetail(pd));
		System.out.println(model);
		return new ModelAndView("ProductDetails1", model);
	}

	// ProductDetailDELETE
	@RequestMapping("/deleteProductDetails")
	public ModelAndView deleteEmployee(@ModelAttribute("pd") ProductDetail pd,
			BindingResult result) {
		System.out.println("im in deleted controler");

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("pd", productDetailService.listProductDetail());
		productDetailService.deleteProductDetail(pd);
		System.out.println("delete Employee");
		return new ModelAndView(
				"redirect:/afterUserListProductdetaildelete.html");
	}

	@Autowired
	private SupplierService supplierService;

	/* SUPPLIER REGISTRATION */

	@RequestMapping("/addSuplier")
	public ModelAndView getRegisterForm(@ModelAttribute("s") Supplier s,
			BindingResult result) {
		System.out.println("am in first step controller");
		System.out.println("Register Form");
		ArrayList<String[]> syptype = new ArrayList<String[]>();
		syptype.add(supplierService.GetSupplierTypes());
		ArrayList<String> syptype1 = new ArrayList<String>();
		for (String[] s1 : syptype) {
			int i = s1.length;
			for (int j = 0; j < i; j++) {
				syptype1.add(s1[j]);
			}
		}

		ArrayList<String> contry = new ArrayList<String>();
		contry.add("Afghanistan");
		contry.add("Albania");
		contry.add("Algeria");
		contry.add("Angola");
		contry.add("Anguilla");
		contry.add("American Samoa");
		contry.add("Antarctica");
		contry.add("Antigua and Barbuda");
		contry.add("Australia");
		contry.add("Azerbaijan");
		contry.add("Bahamas");
		contry.add("Bahrain");
		contry.add("Belgium");
		contry.add("Belarus");
		contry.add("Belize");
		contry.add("Benin");
		contry.add("Bermuda");
		contry.add("Bhutan");
		contry.add("Bolivia");
		contry.add("Bosnia and Herzegowina");
		contry.add("Botswana");
		contry.add("Bouvet");
		contry.add("Brazil");
		contry.add("British Indian Ocean Territory");
		contry.add("Brunei Darussalam");
		contry.add("Bulgaria");
		contry.add("Burkina Faso");
		contry.add("Burundi");
		contry.add("Cambodia");
		contry.add("Cameroon");
		contry.add("Canada");
		contry.add("Cape Verde");
		contry.add("China");
		contry.add("Christmas Island");
		contry.add("Cuba");
		contry.add("Congo, the Democratic Republic of the");
		contry.add("Denmark");
		contry.add("Finland");
		contry.add("Greenland");
		contry.add("Hong Kong");
		contry.add("India");
		contry.add("Nepal");
		contry.add("Malaysia");
		contry.add("Pakistan");
		contry.add("South Africa");
		contry.add("Saudi Arabia");
		contry.add("United States");
		contry.add("Zimbabwe");
		ArrayList<String> state = new ArrayList<String>();
		state.add("Andhra Pradesh");
		state.add(" Arunachal Prades");
		state.add("Assam");
		state.add(" Bihar");
		state.add("Chhattisgarh");
		state.add("Goa");
		state.add("Gujarat");
		state.add("Haryana");
		state.add("Himachal Pradesh");
		state.add("Jammu & Kashmir");
		state.add("Jharkhand");
		state.add("Karnataka");
		state.add("Kerala");
		state.add("Madhya Pradesh");
		state.add("Maharashtra");
		state.add("Manipur");
		state.add("Meghalaya");
		state.add("Mizoram");
		state.add("Nagaland");
		state.add("Odisha");
		state.add("Punjab");
		state.add("Rajasthan");
		state.add("Sikkim");
		state.add("Tamil Nadu");
		state.add("Telangana");
		state.add("Tripura");
		state.add("Uttarakhand");
		state.add("Uttar Pradesh");
		state.add("West Bengal");
		state.add("Delhi");
		state.add("Andaman & Nicobar Island");
		state.add("Chandigarh");
		state.add("Dadra & Nagar Haveli");
		state.add("Daman & Diu");
		state.add("Lakshadweep");
		state.add("Puducherry");
		ArrayList<String> maxId = new ArrayList<String>();
		maxId.add(supplierService.getMaxId2());

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("s", supplierService.listSupplier());
		model.put("syptype", syptype1);
		model.put("maxId", maxId);
		model.put("state", state);
		model.put("contry", contry);
		ArrayList<Integer> al = new ArrayList<Integer>();
		al.add(supplierService.getId1());
		System.out.println(model);
		return new ModelAndView("SupplierInfo2", "model", model);
	}

	@RequestMapping(value = "/userListSupplier")
	public ModelAndView getUser(@ModelAttribute("s") Supplier s,
			BindingResult result) {
		System.out.println("we are in showlist");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("s", supplierService.listSupplier());
		System.out.println(model);
		return new ModelAndView("SuppliersInfo1", model);

	}

	@RequestMapping("/saveUser1SupplierInfo")
	public ModelAndView saveUserData(@ModelAttribute("s") Supplier s,
			BindingResult result) {

		ArrayList<String> card = new ArrayList<String>();
		card.add("Drug");
		card.add("Pharmacy");
		ArrayList<String> maxId = new ArrayList<String>();
		maxId.add(supplierService.getMaxId2());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("card", card);
		model.put("maxId", maxId);
		boolean isValidUser = supplierService.saveSupplier(s);
		if (isValidUser) {
			return new ModelAndView("redirect:/userListSupplierMsg.html");

		} else {
			ArrayList<String> msg = new ArrayList<String>();
			msg.add("Record Inserted Successfully");
			model.put("msg", msg);
			ArrayList<String[]> syptype = new ArrayList<String[]>();
			syptype.add(supplierService.GetSupplierTypes());
			ArrayList<String> syptype1 = new ArrayList<String>();
			for (String[] s1 : syptype) {
				int i = s1.length;
				for (int j = 0; j < i; j++) {
					syptype1.add(s1[j]);
				}
			}
			model.put("syptype", syptype1);

			return new ModelAndView("SupplierInfo4", "model", model);
		}

	}

	@RequestMapping(value = "/userListSupplierMsg")
	public ModelAndView getUserMsg(@ModelAttribute("s") Supplier s,
			BindingResult result) {
		System.out.println("we are in showlist");
		Map<String, Object> model = new HashMap<String, Object>();
		ArrayList<String> msg = new ArrayList<String>();
		msg.add("Record Inserted Successfully");
		model.put("msg", msg);
		model.put("s", supplierService.listSupplier());

		System.out.println(model);
		return new ModelAndView("SuppliersInfo1", model);

	}

	/* SUPPLIER UPDATE */

	@RequestMapping(value = "/updateSupplier")
	public ModelAndView UpdateGet(@ModelAttribute("s") Supplier s,
			BindingResult result) {

		System.out.println("we are in updateGet");
		ArrayList<Integer[]> id = new ArrayList<Integer[]>();
		id.add(supplierService.ids1());

		ArrayList<Integer> customerName1 = new ArrayList<Integer>();
		for (Integer[] s1 : id) {
			int i = s1.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s1.length);
				customerName1.add(s1[j]);
				System.out.println(j + " " + s1[j]);
				System.out
						.println("am in controller retrive for each supplierName");
			}
		}
		ArrayList<String> contry = new ArrayList<String>();
		contry.add("Afghanistan");
		contry.add("Albania");
		contry.add("Algeria");
		contry.add("Angola");
		contry.add("Anguilla");
		contry.add("American Samoa");
		contry.add("Antarctica");
		contry.add("Antigua and Barbuda");
		contry.add("Australia");
		contry.add("Azerbaijan");
		contry.add("Bahamas");
		contry.add("Bahrain");
		contry.add("Belgium");
		contry.add("Belarus");
		contry.add("Belize");
		contry.add("Benin");
		contry.add("Bermuda");
		contry.add("Bhutan");
		contry.add("Bolivia");
		contry.add("Bosnia and Herzegowina");
		contry.add("Botswana");
		contry.add("Bouvet");
		contry.add("Brazil");
		contry.add("British Indian Ocean Territory");
		contry.add("Brunei Darussalam");
		contry.add("Bulgaria");
		contry.add("Burkina Faso");
		contry.add("Burundi");
		contry.add("Cambodia");
		contry.add("Cameroon");
		contry.add("Canada");
		contry.add("Cape Verde");
		contry.add("China");
		contry.add("Christmas Island");
		contry.add("Cuba");
		contry.add("Congo, the Democratic Republic of the");
		contry.add("Denmark");
		contry.add("Finland");
		contry.add("Greenland");
		contry.add("Hong Kong");
		contry.add("India");
		contry.add("Nepal");
		contry.add("Malaysia");
		contry.add("Pakistan");
		contry.add("South Africa");
		contry.add("Saudi Arabia");
		contry.add("United States");
		contry.add("Zimbabwe");
		ArrayList<String> state = new ArrayList<String>();
		state.add("Andhra Pradesh");
		state.add(" Arunachal Prades");
		state.add("Assam");
		state.add(" Bihar");
		state.add("Chhattisgarh");
		state.add("Goa");
		state.add("Gujarat");
		state.add("Haryana");
		state.add("Himachal Pradesh");
		state.add("Jammu & Kashmir");
		state.add("Jharkhand");
		state.add("Karnataka");
		state.add("Kerala");
		state.add("Madhya Pradesh");
		state.add("Maharashtra");
		state.add("Manipur");
		state.add("Meghalaya");
		state.add("Mizoram");
		state.add("Nagaland");
		state.add("Odisha");
		state.add("Punjab");
		state.add("Rajasthan");
		state.add("Sikkim");
		state.add("Tamil Nadu");
		state.add("Telangana");
		state.add("Tripura");
		state.add("Uttarakhand");
		state.add("Uttar Pradesh");
		state.add("West Bengal");
		state.add("Delhi");
		state.add("Andaman & Nicobar Island");
		state.add("Chandigarh");
		state.add("Dadra & Nagar Haveli");
		state.add("Daman & Diu");
		state.add("Lakshadweep");
		state.add("Puducherry");

		ArrayList<String[]> syptype = new ArrayList<String[]>();
		syptype.add(supplierService.GetSupplierTypes());
		ArrayList<String> syptype1 = new ArrayList<String>();
		for (String[] s1 : syptype) {
			int i = s1.length;
			for (int j = 0; j < i; j++) {
				syptype1.add(s1[j]);
			}
		}
		System.out.println(s.getSupCode());
		System.out.println("am in update controller");
		System.out.println(s.getSupCode());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("s", supplierService.getSupplier(s.getSupCode()));
		model.put("pt", supplierService.listSupplier());
		model.put("syptype", syptype1);
		model.put("state", state);
		model.put("contry", contry);
		model.put("customerName1", customerName1);
		System.out.println("am in update controller");
		System.out.println(model);

		return new ModelAndView("SupplierUpdate1", "model", model);
	}

	@RequestMapping("/UpdateProductDetails")
	public ModelAndView saveUserUpdate(@ModelAttribute("s") Supplier s,
			BindingResult result) {

		supplierService.updateSupplier(s);
		return new ModelAndView("redirect:/afterupdateuserlistsupplier.html");

	}

	@RequestMapping(value = "/afterupdateuserlistsupplier")
	public ModelAndView afterupdateuserlistsupplier(
			@ModelAttribute("s") Supplier s, BindingResult result) {
		System.out.println("we are in showlist");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("s", supplierService.listSupplier());
		ArrayList<String> updateMsg = new ArrayList<String>();
		updateMsg.add("Record Updated Sucessfully");
		model.put("updateMsg", updateMsg);
		System.out.println(model);
		return new ModelAndView("SuppliersInfo1", model);

	}

	@RequestMapping(value = "/afterDeleteuserlistsupplier")
	public ModelAndView afterDeleteuserlistsupplier(
			@ModelAttribute("s") Supplier s, BindingResult result) {
		System.out.println("we are in showlist");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("s", supplierService.listSupplier());
		ArrayList<String> updateMsg = new ArrayList<String>();
		updateMsg.add("Record Deleted Sucessfully");
		model.put("msg", updateMsg);
		System.out.println(model);
		return new ModelAndView("SuppliersInfo1", model);

	}

	/* SUPPLIER SEARCH */

	@RequestMapping("/searchSupplier")
	public ModelAndView getRegisterForm1(@ModelAttribute("s") Supplier s,
			BindingResult result) {
		System.out.println("am in Product Search Controller");

		ArrayList<String[]> userId = new ArrayList<String[]>();
		userId.add(supplierService.getIdc1());
		ArrayList<String> userId1 = new ArrayList<String>();
		for (String[] s1 : userId) {
			int i = s1.length;
			for (int j = 0; j < i; j++) {
				userId1.add(s1[j]);
			}
		}

		System.out.println(s.getSupName());

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("userId1", userId1);
		model.put("s", supplierService.getParticularSupplierProduct(s));
		System.out.println(model);
		return new ModelAndView("SuppliersInfo1", model);
	}

	// DELETE
	@RequestMapping("/deleteSupplier")
	public ModelAndView deleteEmployee(@ModelAttribute("s") Supplier s,
			BindingResult result) {
		System.out.println("im in deleted controler");

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("s", supplierService.listSupplier());
		supplierService.deleteSupplier(s);
		System.out.println("delete Employee");
		return new ModelAndView("redirect:/afterDeleteuserlistsupplier.html");
	}

	@Autowired
	private RoomTypeService roomService;

	@RequestMapping(value = "/roomDetails")
	public ModelAndView viewRoomDetails(
			@ModelAttribute("roomtype") RoomTypePojo roomtype,
			BindingResult result) {

		ArrayList<String[]> names = new ArrayList<String[]>();
		names.add(roomService.getRoomTypes());
		ArrayList<String> names1 = new ArrayList<String>();
		for (String[] s : names) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				names1.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("rooms", roomService.GetParticularRoomDetails(roomtype
				.getRoomtypename()));

		System.out.println(model);
		System.out.println(names1);
		model.put("names", names1);
		model.put("rooms", roomService.GetRoomDetails());
		System.out.println(model);
		return new ModelAndView("RoomType1", model);

	}

	@RequestMapping("/roomTypeAdd")
	public ModelAndView RoomTypeAdd(
			@ModelAttribute("roomtype") RoomTypePojo roomtype,
			BindingResult result) {

		Map<String, Object> model = new HashMap<String, Object>();
		System.out.println("retriving user data Data");
		return new ModelAndView("RoomTypeAdd", model);
	}

	@RequestMapping("/saveRooms")
	public ModelAndView RoomTypeSave(
			@ModelAttribute("roomtype") RoomTypePojo roomtype,
			BindingResult result) {
		boolean b = roomService.saveRoomDetails(roomtype);
		if (b) {
			Map<String, Object> model = new HashMap<String, Object>();
			ArrayList<String[]> names = new ArrayList<String[]>();
			names.add(roomService.getRoomTypes());
			ArrayList<String> names1 = new ArrayList<String>();
			for (String[] s : names) {
				int i = s.length;
				for (int j = 0; j < i; j++) {
					System.out.println(s.length);
					names1.add(s[j]);
					System.out.println(j + " " + s[j]);
					System.out
							.println("am in controller retrive for each cusomerName");
				}
			}

			System.out.println(model);
			System.out.println(names1);
			model.put("names", names1);
			model.put("rooms", roomService.GetRoomDetails());
			System.out.println(model);
			System.out.println("retriving user data Data");
			return new ModelAndView("redirect:/roomDetailsMsg.html", model);
		} else {
			Map<String, Object> model = new HashMap<String, Object>();
			ArrayList<String> msg = new ArrayList<String>();
			msg.add("Room Type Already Exist");
			model.put("msg", msg);

			System.out.println("retriving user data Data");
			return new ModelAndView("RoomTypeAdd", model);

		}
	}

	@RequestMapping("/roomDetailsNotSavedMsg")
	public ModelAndView roomDetailsNotSavedMsg(
			@ModelAttribute("roomtype") RoomTypePojo roomtype,
			BindingResult result) {

		Map<String, Object> model = new HashMap<String, Object>();

		ArrayList<String> msg = new ArrayList<String>();
		msg.add("Room Type Already Existed");
		model.put("msg", msg);
		return new ModelAndView("redirect:/roomDetails.html", model);
	}

	@RequestMapping("/roomDetailsMsg")
	public ModelAndView roomDetailsMsg(
			@ModelAttribute("roomtype") RoomTypePojo roomtype,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();

		ArrayList<String> msg = new ArrayList<String>();
		msg.add("Record Inserted Sucessfully");
		model.put("msg", msg);

		return new ModelAndView("redirect:/roomDetails1.html", model);
	}

	@RequestMapping(value = "/roomDetails1")
	public ModelAndView viewRoomDetails1(
			@ModelAttribute("roomtype") RoomTypePojo roomtype,
			BindingResult result) {

		ArrayList<String[]> names = new ArrayList<String[]>();
		names.add(roomService.getRoomTypes());
		ArrayList<String> names1 = new ArrayList<String>();
		for (String[] s : names) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				names1.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("rooms", roomService.GetParticularRoomDetails(roomtype
				.getRoomtypename()));
		ArrayList<String> msg = new ArrayList<String>();
		msg.add("Record Inserted Sucessfully");
		model.put("msg", msg);
		System.out.println(model);
		System.out.println(names1);
		model.put("names", names1);
		model.put("rooms", roomService.GetRoomDetails());
		System.out.println(model);
		return new ModelAndView("RoomType1", model);

	}

	@RequestMapping("/roomEdit")
	public ModelAndView RoomTypeEdit(
			@ModelAttribute("roomtype") RoomTypePojo roomtype,
			BindingResult result) {

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("ABC", roomService.getRoomDetails(roomtype.getId()));
		return new ModelAndView("RoomType2", "model", model);
	}

	@RequestMapping("/updateRooms")
	public ModelAndView RoomUpDate(
			@ModelAttribute("roomtype") RoomTypePojo roomtype,
			BindingResult result) {

		roomService.UpdateRooms(roomtype);
		return new ModelAndView("redirect:/roomDetailsUpdateMsg.html");
	}

	@RequestMapping("/roomDetailsUpdateMsg")
	public ModelAndView roomDetailsUpdateMsg(
			@ModelAttribute("roomtype") RoomTypePojo roomtype,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();

		ArrayList<String> msg = new ArrayList<String>();
		msg.add("Record Updated Sucessfully ");
		model.put("msg", msg);
		return new ModelAndView("redirect:/roomDetails2.html", model);
	}

	@RequestMapping(value = "/roomDetails2")
	public ModelAndView viewRoomDetails2(
			@ModelAttribute("roomtype") RoomTypePojo roomtype,
			BindingResult result) {

		ArrayList<String[]> names = new ArrayList<String[]>();
		names.add(roomService.getRoomTypes());
		ArrayList<String> names1 = new ArrayList<String>();
		for (String[] s : names) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				names1.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("rooms", roomService.GetParticularRoomDetails(roomtype
				.getRoomtypename()));

		ArrayList<String> msg = new ArrayList<String>();
		msg.add("Record Updated Sucessfully ");
		model.put("msg", msg);
		System.out.println(model);
		System.out.println(names1);
		model.put("names", names1);
		model.put("rooms", roomService.GetRoomDetails());
		System.out.println(model);
		return new ModelAndView("RoomType1", model);

	}

	@RequestMapping("/roomDelete")
	public ModelAndView RoomDelete(
			@ModelAttribute("roomtype") RoomTypePojo roomtype,
			BindingResult result) {

		roomService.DeleteRoom(roomtype);
		return new ModelAndView("redirect:/roomDetails.html");
	}

	@RequestMapping(value = "/searchbypRoomType")
	public ModelAndView searchbyproduct(
			@ModelAttribute("roomtype") RoomTypePojo roomtype,
			BindingResult result) {

		ArrayList<String[]> names = new ArrayList<String[]>();
		names.add(roomService.getRoomTypes());
		ArrayList<String> names1 = new ArrayList<String>();
		for (String[] s : names) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				names1.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rooms", roomService.GetParticularRoomDetails(roomtype
				.getRoomtypename()));

		System.out.println(map);
		System.out.println(names1);
		map.put("names", names1);
		return new ModelAndView("RoomType1", map);
	}

	@Autowired
	private TarrifService tarrifService;

	@RequestMapping("/showtarrif")
	public ModelAndView ShowTarrif(@ModelAttribute("tarrif") RoomTarrif tarrif,
			BindingResult result) {

		ArrayList<String[]> roonnos = new ArrayList<String[]>();
		roonnos.add(tarrifService.getRoomnosList());
		ArrayList<String> roonnos1 = new ArrayList<String>();
		for (String[] s1 : roonnos) {
			int i = s1.length;
			for (int j = 0; j < i; j++) {
				roonnos1.add(s1[j]);
			}
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("td", tarrifService.GetDetails());
		model.put("roonnos", roonnos1);
		System.out.println(roonnos1);
		return new ModelAndView("RoomTariff1", model);

	}

	@RequestMapping("/AddTarrif")
	public ModelAndView getTarifDetalis(
			@ModelAttribute("tarrif") RoomTarrif tarrif, BindingResult result) {
		try {
			ArrayList<String[]> locations = new ArrayList<String[]>();

			locations.add(tarrifService.getFloors());

			ArrayList<String> locations1 = new ArrayList<String>();

			for (String[] s1 : locations) {
				int i = s1.length;
				for (int j = 0; j < i; j++) {
					locations1.add(s1[j]);
				}
			}

			ArrayList<String[]> roomtype = new ArrayList<String[]>();
			roomtype.add(tarrifService.getRoomTypedetails());
			ArrayList<String> roomtype1 = new ArrayList<String>();
			for (String[] s : roomtype) {
				int i = s.length;
				for (int j = 0; j < i; j++) {
					System.out.println(s.length);
					roomtype1.add(s[j]);
					System.out.println(j + " " + s[j]);
					System.out
							.println("am in controller retrive for each cusomerName");
				}
			}
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("locations", locations1);
			model.put("roomtype", roomtype1);
			System.out.println(model);
			return new ModelAndView("RoomTariffAdd", "model", model);

		} catch (Exception e) {
			System.out.println(e);
			return new ModelAndView("RoomTariffAddErrorEcception");
		}

	}

	@RequestMapping("/savetarrif")
	public ModelAndView SaveTarrif(@ModelAttribute("tarrif") RoomTarrif tarrif,
			BindingResult result) {

		tarrifService.SaveTarrifDetails(tarrif);

		return new ModelAndView("redirect:/showtarrifOfterSave.html");

	}

	@RequestMapping("/showtarrifOfterSave")
	public ModelAndView showtarrifOfterSave(
			@ModelAttribute("tarrif") RoomTarrif tarrif, BindingResult result) {

		ArrayList<String[]> roonnos = new ArrayList<String[]>();
		roonnos.add(tarrifService.getRoomnosList());
		ArrayList<String> roonnos1 = new ArrayList<String>();
		for (String[] s1 : roonnos) {
			int i = s1.length;
			for (int j = 0; j < i; j++) {
				roonnos1.add(s1[j]);
			}
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("td", tarrifService.GetDetails());
		model.put("roonnos", roonnos1);
		ArrayList<String> msg = new ArrayList<String>();
		msg.add("Record Inserted Sucessfully");
		model.put("msg", msg);

		return new ModelAndView("RoomTariff1", model);

	}

	@RequestMapping("/tarrifEdit")
	public ModelAndView EditTarrif(@ModelAttribute("tarrif") RoomTarrif tarrif,
			BindingResult result) {

		ArrayList<String[]> locations = new ArrayList<String[]>();

		locations.add(tarrifService.getFloors());

		ArrayList<String> locations1 = new ArrayList<String>();

		for (String[] s1 : locations) {
			int i = s1.length;
			for (int j = 0; j < i; j++) {
				locations1.add(s1[j]);
			}
		}

		ArrayList<String[]> roomtype = new ArrayList<String[]>();
		roomtype.add(tarrifService.getRoomTypedetails());
		ArrayList<String> roomtype1 = new ArrayList<String>();
		for (String[] s : roomtype) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				roomtype1.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("locations", locations1);
		map.put("roomtype", roomtype1);
		System.out.println(map);

		map.put("ABC", tarrifService.GetTarrifDetails(tarrif.getTno()));

		return new ModelAndView("RoomTariffEdit", "map", map);

	}

	@RequestMapping("/updateTarrifDetails")
	public ModelAndView BedUpDate(@ModelAttribute("tarrif") RoomTarrif tarrif,
			BindingResult result) {

		tarrifService.UpdateRooms(tarrif);
		return new ModelAndView("redirect:/showtarrifOfterUpdated.html");
	}

	@RequestMapping("/showtarrifOfterUpdated")
	public ModelAndView showtarrifOfterUpdated(
			@ModelAttribute("tarrif") RoomTarrif tarrif, BindingResult result) {

		ArrayList<String[]> roonnos = new ArrayList<String[]>();
		roonnos.add(tarrifService.getRoomnosList());
		ArrayList<String> roonnos1 = new ArrayList<String>();
		for (String[] s1 : roonnos) {
			int i = s1.length;
			for (int j = 0; j < i; j++) {
				roonnos1.add(s1[j]);
			}
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("td", tarrifService.GetDetails());
		model.put("roonnos", roonnos1);
		ArrayList<String> msg = new ArrayList<String>();
		msg.add("Record Updated Sucessfully");
		model.put("msg", msg);

		return new ModelAndView("RoomTariff1", model);

	}

	@RequestMapping("/tarrifDelete")
	public ModelAndView RoomDelete(@ModelAttribute("tarrif") RoomTarrif tarrif,
			BindingResult result) {

		tarrifService.DeleteRoom(tarrif);
		return new ModelAndView("redirect:/showtarrifMsgDelete.html");
	}

	@RequestMapping("/showtarrifMsgDelete")
	public ModelAndView showtarrifMsgDelete(
			@ModelAttribute("tarrif") RoomTarrif tarrif, BindingResult result) {

		ArrayList<String[]> roonnos = new ArrayList<String[]>();
		roonnos.add(tarrifService.getRoomnosList());
		ArrayList<String> roonnos1 = new ArrayList<String>();
		for (String[] s1 : roonnos) {
			int i = s1.length;
			for (int j = 0; j < i; j++) {
				roonnos1.add(s1[j]);
			}
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("td", tarrifService.GetDetails());
		model.put("roonnos", roonnos1);
		System.out.println(roonnos1);
		ArrayList<String> msg = new ArrayList<String>();
		msg.add("Record Deleted Sucessfully");
		model.put("msg", msg);
		return new ModelAndView("RoomTariff1", model);

	}

	@RequestMapping("/searchbyRoomno")
	public ModelAndView getRegisterForm1(
			@ModelAttribute("tarrif") RoomTarrif tarrif, BindingResult result) {
		System.out.println("am in roonnos listController");

		ArrayList<String[]> roonnos = new ArrayList<String[]>();
		roonnos.add(tarrifService.getRoomnosList());
		ArrayList<String> roonnos1 = new ArrayList<String>();
		for (String[] s1 : roonnos) {
			int i = s1.length;
			for (int j = 0; j < i; j++) {
				roonnos1.add(s1[j]);
			}
		}

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("roonnos", roonnos1);
		model.put("td", tarrifService.getParticularRoomDetail(tarrif));
		System.out.println(model);
		return new ModelAndView("RoomTariff1", model);

	}

	@Autowired
	private BedTypeService bedService;

	@RequestMapping(value = "/bedDetails")
	public ModelAndView ShowBedDetails(
			@ModelAttribute("bedtype") BedTypepojo bedtype, BindingResult result) {

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("beds", bedService.GetRoomDetails());
		ArrayList<String[]> names = new ArrayList<String[]>();
		names.add(bedService.getBedTypes());
		ArrayList<String> names1 = new ArrayList<String>();
		for (String[] s : names) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				names1.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}
		model.put("names", names1);
		return new ModelAndView("BedType1", model);

	}

	@RequestMapping("/Addbed")
	public ModelAndView BedTypeAdd(
			@ModelAttribute("bedtype") BedTypepojo bedtype, BindingResult result) {

		Map<String, Object> model = new HashMap<String, Object>();
		System.out.println("retriving user data Data");
		return new ModelAndView("BedTypeAdd", model);
	}

	@RequestMapping("/savebeds")
	public ModelAndView BedTypeSave(
			@ModelAttribute("bedtype") BedTypepojo bedtype, BindingResult result) {

		boolean b = bedService.saveBedDetails(bedtype);

		if (b) {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("beds", bedService.GetRoomDetails());
			ArrayList<String[]> names = new ArrayList<String[]>();
			names.add(bedService.getBedTypes());
			ArrayList<String> names1 = new ArrayList<String>();
			for (String[] s : names) {
				int i = s.length;
				for (int j = 0; j < i; j++) {
					System.out.println(s.length);
					names1.add(s[j]);
					System.out.println(j + " " + s[j]);
					System.out
							.println("am in controller retrive for each cusomerName");
				}
			}
			model.put("names", names1);
			ArrayList<String> msg = new ArrayList<String>();
			msg.add("Record Inserted Successfully");
			model.put("msg", msg);
			return new ModelAndView("BedType1", model);
		} else {
			Map<String, Object> model = new HashMap<String, Object>();
			ArrayList<String[]> names = new ArrayList<String[]>();
			names.add(bedService.getBedTypes());
			ArrayList<String> names1 = new ArrayList<String>();
			for (String[] s : names) {
				int i = s.length;
				for (int j = 0; j < i; j++) {
					System.out.println(s.length);
					names1.add(s[j]);
					System.out.println(j + " " + s[j]);
					System.out
							.println("am in controller retrive for each cusomerName");
				}
			}
			model.put("names", names1);
			ArrayList<String> msg = new ArrayList<String>();
			msg.add("Bed Type Already Exist");
			model.put("msg", msg);
			System.out.println("retriving user data Data");
			return new ModelAndView("BedTypeAdd", model);
		}

	}

	@RequestMapping("/bedEdit")
	public ModelAndView BedEdit(@ModelAttribute("bedtype") BedTypepojo bedtype,
			BindingResult result) {

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("ABC", bedService.getBedDetails(bedtype.getBid()));
		return new ModelAndView("BedTypeEdit", "model", model);
	}

	@RequestMapping("/updateBeds")
	public ModelAndView BedUpDate(
			@ModelAttribute("bedtype") BedTypepojo bedtype, BindingResult result) {

		bedService.UpdateRooms(bedtype);
		return new ModelAndView("redirect:/bedDetailsUpdateMsg.html");
	}

	@RequestMapping(value = "/bedDetailsUpdateMsg")
	public ModelAndView bedDetailsUpdateMsg(
			@ModelAttribute("bedtype") BedTypepojo bedtype, BindingResult result) {

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("beds", bedService.GetRoomDetails());
		ArrayList<String[]> names = new ArrayList<String[]>();
		names.add(bedService.getBedTypes());
		ArrayList<String> names1 = new ArrayList<String>();
		for (String[] s : names) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				names1.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}
		model.put("names", names1);
		ArrayList<String> msg = new ArrayList<String>();
		msg.add("Record Updated Successfully");
		model.put("msg", msg);
		return new ModelAndView("BedType1", model);

	}

	@RequestMapping("/bedDelete")
	public ModelAndView BedDelete(
			@ModelAttribute("bedtype") BedTypepojo bedtype, BindingResult result) {

		bedService.DeleteRoom(bedtype);
		return new ModelAndView("redirect:/bedDetailsDeleteMsg.html");
	}

	@RequestMapping(value = "/bedDetailsDeleteMsg")
	public ModelAndView bedDetailsbedDetailsDeleteMsg(
			@ModelAttribute("bedtype") BedTypepojo bedtype, BindingResult result) {

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("beds", bedService.GetRoomDetails());
		ArrayList<String[]> names = new ArrayList<String[]>();
		names.add(bedService.getBedTypes());
		ArrayList<String> names1 = new ArrayList<String>();
		for (String[] s : names) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				names1.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}
		model.put("names", names1);
		ArrayList<String> msg = new ArrayList<String>();
		msg.add("Record Deleted Successfully");
		model.put("msg", msg);
		return new ModelAndView("BedType1", model);

	}

	@RequestMapping(value = "/searchbybedType")
	public ModelAndView searchbyproduct(
			@ModelAttribute("bedtype") BedTypepojo bedtype, BindingResult result) {

		ArrayList<String[]> names = new ArrayList<String[]>();
		names.add(bedService.getBedTypes());
		ArrayList<String> names1 = new ArrayList<String>();
		for (String[] s : names) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				names1.add(s[j]);

			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("beds",
				bedService.GetParticularRoomDetails(bedtype.getBtypename()));

		map.put("names", names1);
		return new ModelAndView("BedType1", map);
	}

	@Autowired
	private HospitalService hospitalService;

	/* HospitalDetail REGISTRATION */

	@RequestMapping("/registerHospital")
	public ModelAndView getRegisterForm(@ModelAttribute("h") Hospital h,
			BindingResult result) {

		ArrayList<String> contry = new ArrayList<String>();
		contry.add("Afghanistan");
		contry.add("Albania");
		contry.add("Algeria");
		contry.add("Angola");
		contry.add("Anguilla");
		contry.add("American Samoa");
		contry.add("Antarctica");
		contry.add("Antigua and Barbuda");
		contry.add("Australia");
		contry.add("Azerbaijan");
		contry.add("Bahamas");
		contry.add("Bahrain");
		contry.add("Belgium");
		contry.add("Belarus");
		contry.add("Belize");
		contry.add("Benin");
		contry.add("Bermuda");
		contry.add("Bhutan");
		contry.add("Bolivia");
		contry.add("Bosnia and Herzegowina");
		contry.add("Botswana");
		contry.add("Bouvet");
		contry.add("Brazil");
		contry.add("British Indian Ocean Territory");
		contry.add("Brunei Darussalam");
		contry.add("Bulgaria");
		contry.add("Burkina Faso");
		contry.add("Burundi");
		contry.add("Cambodia");
		contry.add("Cameroon");
		contry.add("Canada");
		contry.add("Cape Verde");
		contry.add("China");
		contry.add("Christmas Island");
		contry.add("Cuba");
		contry.add("Congo, the Democratic Republic of the");
		contry.add("Denmark");
		contry.add("Finland");
		contry.add("Greenland");
		contry.add("Hong Kong");
		contry.add("India");
		contry.add("Nepal");
		contry.add("Malaysia");
		contry.add("Pakistan");
		contry.add("South Africa");
		contry.add("Saudi Arabia");
		contry.add("United States");
		contry.add("Zimbabwe");
		ArrayList<String> state = new ArrayList<String>();
		state.add("Andhra Pradesh");
		state.add(" Arunachal Prades");
		state.add("Assam");
		state.add(" Bihar");
		state.add("Chhattisgarh");
		state.add("Goa");
		state.add("Gujarat");
		state.add("Haryana");
		state.add("Himachal Pradesh");
		state.add("Jammu & Kashmir");
		state.add("Jharkhand");
		state.add("Karnataka");
		state.add("Kerala");
		state.add("Madhya Pradesh");
		state.add("Maharashtra");
		state.add("Manipur");
		state.add("Meghalaya");
		state.add("Mizoram");
		state.add("Nagaland");
		state.add("Odisha");
		state.add("Punjab");
		state.add("Rajasthan");
		state.add("Sikkim");
		state.add("Tamil Nadu");
		state.add("Telangana");
		state.add("Tripura");
		state.add("Uttarakhand");
		state.add("Uttar Pradesh");
		state.add("West Bengal");
		state.add("Delhi");
		state.add("Andaman & Nicobar Island");
		state.add("Chandigarh");
		state.add("Dadra & Nagar Haveli");
		state.add("Daman & Diu");
		state.add("Lakshadweep");
		state.add("Puducherry");
		ArrayList<Integer> maxId = new ArrayList<Integer>();
		maxId.add(hospitalService.getMaxIdOfDetails());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("h", hospitalService.listDetail());
		model.put("maxId", maxId);
		model.put("contry", contry);
		model.put("state", state);
		ArrayList<Integer> al = new ArrayList<Integer>();
		al.add(hospitalService.getIdDetail());

		return new ModelAndView("HospitalDeatils2", "model", model);
	}

	@RequestMapping(value = "/userListHospital")
	public ModelAndView getUser(@ModelAttribute("h") Hospital h,
			BindingResult result) {
		System.out.println("we are in showlist");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("h", hospitalService.listDetail());
		System.out.println(model);
		return new ModelAndView("HospitalDeatils1", model);

	}

	@RequestMapping(value = "/userListHospitalDetail")
	public ModelAndView getUser2(@ModelAttribute("h") Hospital h,
			BindingResult result) {
		System.out.println("we are in showlist");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("h", hospitalService.listDetail());
		System.out.println(model);
		return new ModelAndView("HospitalDetails4", model);

	}

	@RequestMapping("/saveHospitalform")
	public ModelAndView saveUserData(@ModelAttribute("h") Hospital h,
			BindingResult result) {
		hospitalService.saveDetail(h);
		System.out.println("Save User Data");
		System.out.println(h.getHosptName());
		return new ModelAndView(
				"redirect:/userListHospitalDetailInsertedMsg.html");

	}

	@RequestMapping(value = "/userListHospitalDetailInsertedMsg")
	public ModelAndView userListHospitalDetailInsertedMsg(
			@ModelAttribute("h") Hospital h, BindingResult result) {
		System.out.println("we are in showlist");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("h", hospitalService.listDetail());
		ArrayList<String> updateMsg = new ArrayList<String>();
		updateMsg.add("Record Inserted Successfully");
		model.put("updateMsg", updateMsg);
		System.out.println(model);
		return new ModelAndView("HospitalDetails4", model);

	}

	/* SUPPLIER UPDATE */

	@RequestMapping(value = "/updateHospital")
	public ModelAndView UpdateGet(@ModelAttribute("h") Hospital h,
			BindingResult result) {

		System.out.println("we are in updateGet");
		ArrayList<Integer[]> id = new ArrayList<Integer[]>();
		id.add(hospitalService.idsDetail());

		ArrayList<Integer> customerName1 = new ArrayList<Integer>();
		for (Integer[] s1 : id) {
			int i = s1.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s1.length);
				customerName1.add(s1[j]);
				System.out.println(j + " " + s1[j]);
				System.out
						.println("am in controller retrive for each supplierName");
			}
		}

		ArrayList<String> state = new ArrayList<String>();
		state.add("Andhra Pradesh");
		state.add(" Arunachal Prades");
		state.add("Assam");
		state.add(" Bihar");
		state.add("Chhattisgarh");
		state.add("Goa");
		state.add("Gujarat");
		state.add("Haryana");
		state.add("Himachal Pradesh");
		state.add("Jammu & Kashmir");
		state.add("Jharkhand");
		state.add("Karnataka");
		state.add("Kerala");
		state.add("Madhya Pradesh");
		state.add("Maharashtra");
		state.add("Manipur");
		state.add("Meghalaya");
		state.add("Mizoram");
		state.add("Nagaland");
		state.add("Odisha");
		state.add("Punjab");
		state.add("Rajasthan");
		state.add("Sikkim");
		state.add("Tamil Nadu");
		state.add("Telangana");
		state.add("Tripura");
		state.add("Uttarakhand");
		state.add("Uttar Pradesh");
		state.add("West Bengal");
		state.add("Delhi");
		state.add("Andaman & Nicobar Island");
		state.add("Chandigarh");
		state.add("Dadra & Nagar Haveli");
		state.add("Daman & Diu");
		state.add("Lakshadweep");
		state.add("Puducherry");

		ArrayList<String> contry = new ArrayList<String>();
		contry.add("Afghanistan");
		contry.add("Albania");
		contry.add("Algeria");
		contry.add("Angola");
		contry.add("Anguilla");
		contry.add("American Samoa");
		contry.add("Antarctica");
		contry.add("Antigua and Barbuda");
		contry.add("Australia");
		contry.add("Azerbaijan");
		contry.add("Bahamas");
		contry.add("Bahrain");
		contry.add("Belgium");
		contry.add("Belarus");
		contry.add("Belize");
		contry.add("Benin");
		contry.add("Bermuda");
		contry.add("Bhutan");
		contry.add("Bolivia");
		contry.add("Bosnia and Herzegowina");
		contry.add("Botswana");
		contry.add("Bouvet");
		contry.add("Brazil");
		contry.add("British Indian Ocean Territory");
		contry.add("Brunei Darussalam");
		contry.add("Bulgaria");
		contry.add("Burkina Faso");
		contry.add("Burundi");
		contry.add("Cambodia");
		contry.add("Cameroon");
		contry.add("Canada");
		contry.add("Cape Verde");
		contry.add("China");
		contry.add("Christmas Island");
		contry.add("Cuba");
		contry.add("Congo, the Democratic Republic of the");
		contry.add("Denmark");
		contry.add("Finland");
		contry.add("Greenland");
		contry.add("Hong Kong");
		contry.add("India");
		contry.add("Nepal");
		contry.add("Malaysia");
		contry.add("Pakistan");
		contry.add("South Africa");
		contry.add("Saudi Arabia");
		contry.add("United States");
		contry.add("Zimbabwe");

		System.out.println(h.getHostId());
		System.out.println("am in update controller");
		System.out.println(h.getHostId());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("update", hospitalService.getDetail(h.getHostId()));
		model.put("h", hospitalService.listDetail());
		model.put("contry", contry);
		model.put("state", state);
		model.put("customerName1", customerName1);
		System.out.println("am in update controller");
		System.out.println(model);

		return new ModelAndView("HospitalDeatils3", "model", model);
	}

	@RequestMapping("/updateHospitalform")
	public ModelAndView saveUserUpdate(@ModelAttribute("h") Hospital h,
			BindingResult result) {

		hospitalService.updateDetail(h);

		return new ModelAndView(
				"redirect:/ofterUpdateuserlistHospitaldeatail.html");

	}

	@RequestMapping(value = "/ofterUpdateuserlistHospitaldeatail")
	public ModelAndView ofterUpdateuserlistHospitaldeatail(
			@ModelAttribute("h") Hospital h, BindingResult result) {
		System.out.println("we are in showlist");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("h", hospitalService.listDetail());
		ArrayList<String> updateMsg = new ArrayList<String>();
		updateMsg.add("Record Updated Sucessfully");
		model.put("updateMsg", updateMsg);
		System.out.println(model);
		return new ModelAndView("HospitalDetails4", model);

	}

	/* SUPPLIER SEARCH */

	@RequestMapping("/searchHospital")
	public ModelAndView getRegisterForm1(@ModelAttribute("h") Hospital h,
			BindingResult result) {
		System.out.println("am in Product Search Controller");

		ArrayList<String[]> userId = new ArrayList<String[]>();
		userId.add(hospitalService.getIdcDetail());
		ArrayList<String> userId1 = new ArrayList<String>();
		for (String[] s1 : userId) {
			int i = s1.length;
			for (int j = 0; j < i; j++) {
				userId1.add(s1[j]);
			}
		}

		System.out.println(h.getHosptName());

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("userId1", userId1);
		model.put("h", hospitalService.getParticularDetail(h));
		System.out.println(model);
		return new ModelAndView("HospitalDetails4", model);
	}

	@Autowired
	private LaboratoryService laboratoryService;

	@RequestMapping("/registerLabaratory")
	public ModelAndView getRegisterForm(@ModelAttribute("lab") Laboratory lab,
			BindingResult result) {
		System.out.println("am in first step controller");
		System.out.println("Register Form");

		ArrayList<String> contry = new ArrayList<String>();
		contry.add("Afghanistan");
		contry.add("Albania");
		contry.add("Algeria");
		contry.add("Angola");
		contry.add("Anguilla");
		contry.add("American Samoa");
		contry.add("Antarctica");
		contry.add("Antigua and Barbuda");
		contry.add("Australia");
		contry.add("Azerbaijan");
		contry.add("Bahamas");
		contry.add("Bahrain");
		contry.add("Belgium");
		contry.add("Belarus");
		contry.add("Belize");
		contry.add("Benin");
		contry.add("Bermuda");
		contry.add("Bhutan");
		contry.add("Bolivia");
		contry.add("Bosnia and Herzegowina");
		contry.add("Botswana");
		contry.add("Bouvet");
		contry.add("Brazil");
		contry.add("British Indian Ocean Territory");
		contry.add("Brunei Darussalam");
		contry.add("Bulgaria");
		contry.add("Burkina Faso");
		contry.add("Burundi");
		contry.add("Cambodia");
		contry.add("Cameroon");
		contry.add("Canada");
		contry.add("Cape Verde");
		contry.add("China");
		contry.add("Christmas Island");
		contry.add("Cuba");
		contry.add("Congo, the Democratic Republic of the");
		contry.add("Denmark");
		contry.add("Finland");
		contry.add("Greenland");
		contry.add("Hong Kong");
		contry.add("India");
		contry.add("Nepal");
		contry.add("Malaysia");
		contry.add("Pakistan");
		contry.add("South Africa");
		contry.add("Saudi Arabia");
		contry.add("United States");
		contry.add("Zimbabwe");
		ArrayList<String> state = new ArrayList<String>();
		state.add("Andhra Pradesh");
		state.add(" Arunachal Prades");
		state.add("Assam");
		state.add(" Bihar");
		state.add("Chhattisgarh");
		state.add("Goa");
		state.add("Gujarat");
		state.add("Haryana");
		state.add("Himachal Pradesh");
		state.add("Jammu & Kashmir");
		state.add("Jharkhand");
		state.add("Karnataka");
		state.add("Kerala");
		state.add("Madhya Pradesh");
		state.add("Maharashtra");
		state.add("Manipur");
		state.add("Meghalaya");
		state.add("Mizoram");
		state.add("Nagaland");
		state.add("Odisha");
		state.add("Punjab");
		state.add("Rajasthan");
		state.add("Sikkim");
		state.add("Tamil Nadu");
		state.add("Telangana");
		state.add("Tripura");
		state.add("Uttarakhand");
		state.add("Uttar Pradesh");
		state.add("West Bengal");
		state.add("Delhi");
		state.add("Andaman & Nicobar Island");
		state.add("Chandigarh");
		state.add("Dadra & Nagar Haveli");
		state.add("Daman & Diu");
		state.add("Lakshadweep");
		state.add("Puducherry");

		ArrayList<String> Contact = new ArrayList<String>();
		Contact.add("swamy");
		Contact.add("kumar");
		Contact.add("varma");
		Contact.add("ramesh");
		ArrayList<Integer> maxId = new ArrayList<Integer>();
		maxId.add(laboratoryService.getMaxIdOfLaboratory());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("lab", laboratoryService.listLaboratory());
		model.put("maxId", maxId);
		model.put("contry", contry);
		model.put("state", state);
		model.put("Contact", Contact);
		ArrayList<Integer> al = new ArrayList<Integer>();
		al.add(laboratoryService.getIdLaboratory());
		System.out.println(model);
		return new ModelAndView("Laboratory2", "model", model);
	}

	@RequestMapping(value = "/userListLaboratory")
	public ModelAndView getUser(@ModelAttribute("lab") Laboratory lab,
			BindingResult result) {
		System.out.println("we are in showlist");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("lab", laboratoryService.listLaboratory());
		System.out.println(model);
		return new ModelAndView("Laboratory1", model);

	}

	@RequestMapping(value = "/userListLaboratoryDetail")
	public ModelAndView getUser2(@ModelAttribute("lab") Laboratory lab,
			BindingResult result) {
		System.out.println("we are in showlist");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("lab", laboratoryService.listLaboratory());
		System.out.println(model);
		ArrayList<String> msg = new ArrayList<String>();
		msg.add("Record Inserted Sucessfully");
		model.put("msg", msg);
		return new ModelAndView("Laboratory4", model);

	}

	@RequestMapping("/saveLaboratoryform")
	public ModelAndView saveUserData(@ModelAttribute("lab") Laboratory lab,
			BindingResult result) {
		laboratoryService.saveLaboratory(lab);

		System.out.println("Save User Data");
		System.out.println(lab.getLabName());
		return new ModelAndView("redirect:/userListLaboratoryDetail.html");

	}

	/* SUPPLIER UPDATE */

	@RequestMapping(value = "/updateLaboratory")
	public ModelAndView UpdateGet(@ModelAttribute("lab") Laboratory lab,
			BindingResult result) {

		System.out.println("we are in updateGet");
		ArrayList<Integer[]> id = new ArrayList<Integer[]>();
		id.add(laboratoryService.idsLaboratory());

		ArrayList<Integer> customerName1 = new ArrayList<Integer>();
		for (Integer[] s1 : id) {
			int i = s1.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s1.length);
				customerName1.add(s1[j]);
				System.out.println(j + " " + s1[j]);
				System.out
						.println("am in controller retrive for each supplierName");
			}
		}
		ArrayList<String> contry = new ArrayList<String>();
		contry.add("Afghanistan");
		contry.add("Albania");
		contry.add("Algeria");
		contry.add("Angola");
		contry.add("Anguilla");
		contry.add("American Samoa");
		contry.add("Antarctica");
		contry.add("Antigua and Barbuda");
		contry.add("Australia");
		contry.add("Azerbaijan");
		contry.add("Bahamas");
		contry.add("Bahrain");
		contry.add("Belgium");
		contry.add("Belarus");
		contry.add("Belize");
		contry.add("Benin");
		contry.add("Bermuda");
		contry.add("Bhutan");
		contry.add("Bolivia");
		contry.add("Bosnia and Herzegowina");
		contry.add("Botswana");
		contry.add("Bouvet");
		contry.add("Brazil");
		contry.add("British Indian Ocean Territory");
		contry.add("Brunei Darussalam");
		contry.add("Bulgaria");
		contry.add("Burkina Faso");
		contry.add("Burundi");
		contry.add("Cambodia");
		contry.add("Cameroon");
		contry.add("Canada");
		contry.add("Cape Verde");
		contry.add("China");
		contry.add("Christmas Island");
		contry.add("Cuba");
		contry.add("Congo, the Democratic Republic of the");
		contry.add("Denmark");
		contry.add("Finland");
		contry.add("Greenland");
		contry.add("Hong Kong");
		contry.add("India");
		contry.add("Nepal");
		contry.add("Malaysia");
		contry.add("Pakistan");
		contry.add("South Africa");
		contry.add("Saudi Arabia");
		contry.add("United States");
		contry.add("Zimbabwe");
		ArrayList<String> state = new ArrayList<String>();
		state.add("Andhra Pradesh");
		state.add(" Arunachal Prades");
		state.add("Assam");
		state.add(" Bihar");
		state.add("Chhattisgarh");
		state.add("Goa");
		state.add("Gujarat");
		state.add("Haryana");
		state.add("Himachal Pradesh");
		state.add("Jammu & Kashmir");
		state.add("Jharkhand");
		state.add("Karnataka");
		state.add("Kerala");
		state.add("Madhya Pradesh");
		state.add("Maharashtra");
		state.add("Manipur");
		state.add("Meghalaya");
		state.add("Mizoram");
		state.add("Nagaland");
		state.add("Odisha");
		state.add("Punjab");
		state.add("Rajasthan");
		state.add("Sikkim");
		state.add("Tamil Nadu");
		state.add("Telangana");
		state.add("Tripura");
		state.add("Uttarakhand");
		state.add("Uttar Pradesh");
		state.add("West Bengal");
		state.add("Delhi");
		state.add("Andaman & Nicobar Island");
		state.add("Chandigarh");
		state.add("Dadra & Nagar Haveli");
		state.add("Daman & Diu");
		state.add("Lakshadweep");
		state.add("Puducherry");

		ArrayList<String> Contact = new ArrayList<String>();
		Contact.add("swamy");
		Contact.add("kumar");
		Contact.add("varma");
		Contact.add("ramesh");

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("update", laboratoryService.getLaboratory(lab.getLabId()));
		model.put("lab", laboratoryService.listLaboratory());
		model.put("contry", contry);
		model.put("Contact", Contact);
		model.put("state", state);
		model.put("customerName1", customerName1);

		return new ModelAndView("Laboratory3", "model", model);
	}

	@RequestMapping("/updatelaboratoryform")
	public ModelAndView saveUserUpdate(@ModelAttribute("lab") Laboratory lab,
			BindingResult result) {

		laboratoryService.updateLaboratory(lab);
		return new ModelAndView("redirect:/afterupdation.html");
	}

	@RequestMapping(value = "/afterupdation")
	public ModelAndView afterUpdation(@ModelAttribute("lab") Laboratory lab,
			BindingResult result) {
		System.out.println("we are in showlist");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("lab", laboratoryService.listLaboratory());
		ArrayList<String> updateMsg = new ArrayList<String>();
		updateMsg.add(" Record Updated Sucessfully");
		model.put("updateMsg", updateMsg);
		System.out.println(model);
		return new ModelAndView("Laboratory4", model);

	}

	/* SUPPLIER SEARCH */

	@RequestMapping("/searchLaboratory")
	public ModelAndView getRegisterForm1(@ModelAttribute("lab") Laboratory lab,
			BindingResult result) {
		System.out.println("am in Product Search Controller");

		ArrayList<String[]> userId = new ArrayList<String[]>();
		userId.add(laboratoryService.getIdcLaboratory());
		ArrayList<String> userId1 = new ArrayList<String>();
		for (String[] s1 : userId) {
			int i = s1.length;
			for (int j = 0; j < i; j++) {
				userId1.add(s1[j]);
			}
		}

		System.out.println(lab.getLabName());

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("userId1", userId1);
		model.put("lab", laboratoryService.getParticularLaboratory(lab));

		return new ModelAndView("Laboratory4", model);
	}

	@Autowired
	private PharmacyService pharmacyService;

	@RequestMapping("/registerPharmacy")
	public ModelAndView getRegisterForm(@ModelAttribute("ph") Pharmacy ph,
			BindingResult result) {

		ArrayList<String> state = new ArrayList<String>();
		state.add("Andhra Pradesh");
		state.add(" Arunachal Prades");
		state.add("Assam");
		state.add(" Bihar");
		state.add("Chhattisgarh");
		state.add("Goa");
		state.add("Gujarat");
		state.add("Haryana");
		state.add("Himachal Pradesh");
		state.add("Jammu & Kashmir");
		state.add("Jharkhand");
		state.add("Karnataka");
		state.add("Kerala");
		state.add("Madhya Pradesh");
		state.add("Maharashtra");
		state.add("Manipur");
		state.add("Meghalaya");
		state.add("Mizoram");
		state.add("Nagaland");
		state.add("Odisha");
		state.add("Punjab");
		state.add("Rajasthan");
		state.add("Sikkim");
		state.add("Tamil Nadu");
		state.add("Telangana");
		state.add("Tripura");
		state.add("Uttarakhand");
		state.add("Uttar Pradesh");
		state.add("West Bengal");
		state.add("Delhi");
		state.add("Andaman & Nicobar Island");
		state.add("Chandigarh");
		state.add("Dadra & Nagar Haveli");
		state.add("Daman & Diu");
		state.add("Lakshadweep");
		state.add("Puducherry");
		ArrayList<String> contry = new ArrayList<String>();
		contry.add("Afghanistan");
		contry.add("Albania");
		contry.add("Algeria");
		contry.add("Angola");
		contry.add("Anguilla");
		contry.add("American Samoa");
		contry.add("Antarctica");
		contry.add("Antigua and Barbuda");
		contry.add("Australia");
		contry.add("Azerbaijan");
		contry.add("Bahamas");
		contry.add("Bahrain");
		contry.add("Belgium");
		contry.add("Belarus");
		contry.add("Belize");
		contry.add("Benin");
		contry.add("Bermuda");
		contry.add("Bhutan");
		contry.add("Bolivia");
		contry.add("Bosnia and Herzegowina");
		contry.add("Botswana");
		contry.add("Bouvet");
		contry.add("Brazil");
		contry.add("British Indian Ocean Territory");
		contry.add("Brunei Darussalam");
		contry.add("Bulgaria");
		contry.add("Burkina Faso");
		contry.add("Burundi");
		contry.add("Cambodia");
		contry.add("Cameroon");
		contry.add("Canada");
		contry.add("Cape Verde");
		contry.add("China");
		contry.add("Christmas Island");
		contry.add("Cuba");
		contry.add("Congo, the Democratic Republic of the");
		contry.add("Denmark");
		contry.add("Finland");
		contry.add("Greenland");
		contry.add("Hong Kong");
		contry.add("India");
		contry.add("Nepal");
		contry.add("Malaysia");
		contry.add("Pakistan");
		contry.add("South Africa");
		contry.add("Saudi Arabia");
		contry.add("United States");
		contry.add("Zimbabwe");
		ArrayList<Integer> maxId = new ArrayList<Integer>();
		maxId.add(pharmacyService.getMaxIdOfPharmacy());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("ph", pharmacyService.listPharmacy());
		model.put("maxId", maxId);
		model.put("contry", contry);
		model.put("state", state);
		ArrayList<Integer> al = new ArrayList<Integer>();
		al.add(pharmacyService.getIdPharmacy());

		return new ModelAndView("Pharmacy2", "model", model);
	}

	@RequestMapping(value = "/userListPharmacy")
	public ModelAndView getUser(@ModelAttribute("ph") Pharmacy ph,
			BindingResult result) {
		System.out.println("we are in showlist");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("ph", pharmacyService.listPharmacy());
		System.out.println(model);
		return new ModelAndView("Pharmacy1", model);

	}

	@RequestMapping(value = "/userListPharmacyDetail")
	public ModelAndView getUser2(@ModelAttribute("ph") Pharmacy ph,
			BindingResult result) {

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("ph", pharmacyService.listPharmacy());

		return new ModelAndView("Pharmacy4", model);

	}

	@RequestMapping("/savePharmacyForm")
	public ModelAndView saveUserData(@ModelAttribute("ph") Pharmacy ph,
			BindingResult result) {
		pharmacyService.savePharmacy(ph);

		return new ModelAndView("redirect:/userListPharmacyDetailSaveMsg.html");
	}

	@RequestMapping(value = "/userListPharmacyDetailSaveMsg")
	public ModelAndView userListPharmacyDetailSaveMsg(
			@ModelAttribute("ph") Pharmacy ph, BindingResult result) {

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("ph", pharmacyService.listPharmacy());
		ArrayList<String> updateMsg = new ArrayList<String>();
		updateMsg.add("Records Inserted Successfully");
		model.put("updateMsg", updateMsg);

		return new ModelAndView("Pharmacy4", model);

	}

	/* SUPPLIER UPDATE */

	@RequestMapping(value = "/updatePharmacy")
	public ModelAndView UpdateGet(@ModelAttribute("ph") Pharmacy ph,
			BindingResult result) {

		System.out.println("we are in updateGet");
		ArrayList<Integer[]> id = new ArrayList<Integer[]>();
		id.add(pharmacyService.idsPharmacy());

		ArrayList<Integer> customerName1 = new ArrayList<Integer>();
		for (Integer[] s1 : id) {
			int i = s1.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s1.length);
				customerName1.add(s1[j]);
				System.out.println(j + " " + s1[j]);
				System.out
						.println("am in controller retrive for each supplierName");
			}
		}
		ArrayList<String> state = new ArrayList<String>();
		state.add("Andhra Pradesh");
		state.add(" Arunachal Prades");
		state.add("Assam");
		state.add(" Bihar");
		state.add("Chhattisgarh");
		state.add("Goa");
		state.add("Gujarat");
		state.add("Haryana");
		state.add("Himachal Pradesh");
		state.add("Jammu & Kashmir");
		state.add("Jharkhand");
		state.add("Karnataka");
		state.add("Kerala");
		state.add("Madhya Pradesh");
		state.add("Maharashtra");
		state.add("Manipur");
		state.add("Meghalaya");
		state.add("Mizoram");
		state.add("Nagaland");
		state.add("Odisha");
		state.add("Punjab");
		state.add("Rajasthan");
		state.add("Sikkim");
		state.add("Tamil Nadu");
		state.add("Telangana");
		state.add("Tripura");
		state.add("Uttarakhand");
		state.add("Uttar Pradesh");
		state.add("West Bengal");
		state.add("Delhi");
		state.add("Andaman & Nicobar Island");
		state.add("Chandigarh");
		state.add("Dadra & Nagar Haveli");
		state.add("Daman & Diu");
		state.add("Lakshadweep");
		state.add("Puducherry");
		ArrayList<String> contry = new ArrayList<String>();
		contry.add("Afghanistan");
		contry.add("Albania");
		contry.add("Algeria");
		contry.add("Angola");
		contry.add("Anguilla");
		contry.add("American Samoa");
		contry.add("Antarctica");
		contry.add("Antigua and Barbuda");
		contry.add("Australia");
		contry.add("Azerbaijan");
		contry.add("Bahamas");
		contry.add("Bahrain");
		contry.add("Belgium");
		contry.add("Belarus");
		contry.add("Belize");
		contry.add("Benin");
		contry.add("Bermuda");
		contry.add("Bhutan");
		contry.add("Bolivia");
		contry.add("Bosnia and Herzegowina");
		contry.add("Botswana");
		contry.add("Bouvet");
		contry.add("Brazil");
		contry.add("British Indian Ocean Territory");
		contry.add("Brunei Darussalam");
		contry.add("Bulgaria");
		contry.add("Burkina Faso");
		contry.add("Burundi");
		contry.add("Cambodia");
		contry.add("Cameroon");
		contry.add("Canada");
		contry.add("Cape Verde");
		contry.add("China");
		contry.add("Christmas Island");
		contry.add("Cuba");
		contry.add("Congo, the Democratic Republic of the");
		contry.add("Denmark");
		contry.add("Finland");
		contry.add("Greenland");
		contry.add("Hong Kong");
		contry.add("India");
		contry.add("Nepal");
		contry.add("Malaysia");
		contry.add("Pakistan");
		contry.add("South Africa");
		contry.add("Saudi Arabia");
		contry.add("United States");
		contry.add("Zimbabwe");
		System.out.println(ph.getPharId());
		System.out.println("am in update controller");
		System.out.println(ph.getPharId());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("update", pharmacyService.getPharmacy(ph.getPharId()));
		model.put("ph", pharmacyService.listPharmacy());
		model.put("contry", contry);
		model.put("state", state);
		model.put("customerName1", customerName1);
		System.out.println("am in update controller");
		System.out.println(model);

		return new ModelAndView("Pharmacy3", "model", model);
	}

	@RequestMapping("/updatePharmacyform")
	public ModelAndView saveUserUpdate(@ModelAttribute("ph") Pharmacy ph,
			BindingResult result) {

		pharmacyService.updatePharmacy(ph);

		return new ModelAndView(
				"redirect:/ofterUpdateuserListPharmacyDetail.html");

	}

	@RequestMapping(value = "/ofterUpdateuserListPharmacyDetail")
	public ModelAndView ofterUpdateuserListPharmacyDetail(
			@ModelAttribute("ph") Pharmacy ph, BindingResult result) {
		System.out.println("we are in showlist");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("ph", pharmacyService.listPharmacy());
		ArrayList<String> updateMsg = new ArrayList<String>();
		updateMsg.add("Record Updated Successfully");
		model.put("updateMsg", updateMsg);
		System.out.println(model);
		return new ModelAndView("Pharmacy4", model);

	}

	/* SUPPLIER SEARCH */

	@RequestMapping("/searchPharmacy")
	public ModelAndView getRegisterForm1(@ModelAttribute("ph") Pharmacy ph,
			BindingResult result) {
		System.out.println("am in Product Search Controller");

		ArrayList<String[]> userId = new ArrayList<String[]>();
		userId.add(pharmacyService.getIdcPharmacy());
		ArrayList<String> userId1 = new ArrayList<String>();
		for (String[] s1 : userId) {
			int i = s1.length;
			for (int j = 0; j < i; j++) {
				userId1.add(s1[j]);
			}
		}

		System.out.println(ph.getPharName());

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("userId1", userId1);
		model.put("ph", pharmacyService.getParticularPharmacy(ph));
		System.out.println(model);
		return new ModelAndView("redirect:/userListPharmacyDetail.html");
	}

	@Autowired
	private StockPositionService stockService;

	@RequestMapping(value = "/stockdetails")
	public ModelAndView viewStckDetails(
			@ModelAttribute("user") ProductType99 user, BindingResult result) {

		ArrayList<String[]> ptypes = new ArrayList<String[]>();
		ptypes.add(stockService.getProductTypes());
		ArrayList<String> ptypes1 = new ArrayList<String>();
		for (String[] s : ptypes) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				ptypes1.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("user", stockService.GetParticularTypeDetails());
		model.put("ptypes", ptypes1);

		ArrayList<String[]> names = new ArrayList<String[]>();
		names.add(stockService.getProductNames());
		ArrayList<String> names1 = new ArrayList<String>();
		for (String[] s : names) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				names1.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}

		model.put("user", stockService.GetDetails());
		model.put("names", names1);
		System.out.println(model);
		return new ModelAndView("StockPosition", model);

	}

	@RequestMapping(value = "/searchbyproduct")
	public ModelAndView searchbyproduct(
			@ModelAttribute("user") ProductType99 user, BindingResult result) {

		ArrayList<String[]> ptypes = new ArrayList<String[]>();
		ptypes.add(stockService.getProductTypes());
		ArrayList<String> ptypes1 = new ArrayList<String>();
		for (String[] s : ptypes) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				ptypes1.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}

		ArrayList<String[]> names = new ArrayList<String[]>();
		names.add(stockService.getProductNames());
		ArrayList<String> names1 = new ArrayList<String>();
		for (String[] s : names) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				names1.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("user",
				stockService.GetParticularDetails(user.getProductTypeName()));
		model.put("names", names1);
		model.put("ptypes", ptypes1);

		System.out.println(model);
		return new ModelAndView("StockPosition", model);

	}

	@RequestMapping(value = "/searchbyptype")
	public ModelAndView searchbyptype(
			@ModelAttribute("user") ProductType99 user, BindingResult result) {

		ArrayList<String[]> ptypes = new ArrayList<String[]>();
		ptypes.add(stockService.getProductTypes());
		ArrayList<String> ptypes1 = new ArrayList<String>();
		for (String[] s : ptypes) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				ptypes1.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("user", stockService.GetParticularTypeDetails());
		model.put("ptypes", ptypes1);

		ArrayList<String[]> names = new ArrayList<String[]>();
		names.add(stockService.getProductNames());
		ArrayList<String> names1 = new ArrayList<String>();
		for (String[] s : names) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				names1.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}

		model.put("user",
				stockService.GetDetailsBasedonProductType(user.getType()));
		model.put("names", names1);
		System.out.println(model);
		return new ModelAndView("StockPosition", model);

	}

	@Autowired
	private StockAdjustmentService stockAdjustmentService;

	@RequestMapping(value = "stockdetails12345")
	public ModelAndView viewStckDetails2(
			@ModelAttribute("user") StockDetails user, BindingResult result) {

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("user", stockAdjustmentService.GetDetails());
		System.out.println(model);
		return new ModelAndView("StockAdjustment1", model);
	}

	@RequestMapping(value = "/stockEdit")
	public ModelAndView editStockDetails2(
			@ModelAttribute("user") StockDetails user, BindingResult result) {
		System.out.println("am in update controller");
		System.out.println(user.getId());
		int id = user.getId();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("user", stockAdjustmentService.getStockId(id));

		System.out.println(user.getId());
		System.out.println("am in edit controller");
		System.out.println(model);
		return new ModelAndView("StockAdjustmentadd", model);
	}

	@RequestMapping("/updateStock")
	public ModelAndView updateUserData2(
			@ModelAttribute("user") StockDetails user, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();

		System.out.println("am in update controller");

		stockAdjustmentService.updateUser(user);

		System.out.println("stockupdated");

		return new ModelAndView("redirect:/stockdetails12345Msg.html");
	}

	@RequestMapping(value = "/stockdetails12345Msg")
	public ModelAndView viewStckDetails2msg(
			@ModelAttribute("user") StockDetails user, BindingResult result) {

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("user", stockAdjustmentService.GetDetails());
		ArrayList<String> msg = new ArrayList<String>();
		msg.add("Record Updated Sucessfully");
		model.put("msg", msg);
		return new ModelAndView("StockAdjustment1", model);
	}

	@RequestMapping(value = "/closeStochEdit")
	public ModelAndView closeStochEdit(
			@ModelAttribute("user") StockDetails user, BindingResult result) {

		return new ModelAndView("redirect:/stockdetails12345.html");
	}

	// search by product name

	@RequestMapping("/searchProduct")
	public ModelAndView getRegisterForm1(
			@ModelAttribute("user") StockDetails user, BindingResult result) {
		System.out.println("am in Product Search Controller");

		ArrayList<String[]> userId = new ArrayList<String[]>();
		userId.add(stockAdjustmentService.getIdc());
		ArrayList<String> userId1 = new ArrayList<String>();
		for (String[] s : userId) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				userId1.add(s[j]);
			}
		}

		System.out.println(user.getPrname());

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("userId1", userId1);
		model.put("user", stockAdjustmentService.getParticularProductName(user));
		System.out.println(model);
		return new ModelAndView("StockAdjustment1", model);
	}

	@Autowired
	private DuePatientService duePatientService;

	@RequestMapping(value = "/details")
	public ModelAndView viewDuecustomwers1(
			@ModelAttribute("user") DuePatient user, BindingResult result) {

		Map<String, Object> model = new HashMap<String, Object>();

		model.put("user", duePatientService.GetDetails());
		System.out.println(model);
		return new ModelAndView("DuePatient1", model);
	}

	@RequestMapping(value = "/edit")
	public ModelAndView deleteEmployee1(
			@ModelAttribute("user") DuePatient user, BindingResult result) {
		System.out.println("am in edit controller");
		System.out.println(user.getId());
		Map<String, Object> model = new HashMap<String, Object>();
		ArrayList<String> gender = new ArrayList<String>();
		gender.add("Male");
		gender.add("Female");

		model.put("user", duePatientService.getPatient(user.getId()));
		model.put("gender", gender);
		System.out.println(user.getId());
		System.out.println("updated");
		System.out.println("am in edit controller");
		System.out.println(model);
		return new ModelAndView("DuePatientadd", model);
	}

	@RequestMapping("/updatepatient")
	public ModelAndView updateUserData1(
			@ModelAttribute("user") DuePatient user, BindingResult result) {

		duePatientService.updateUser(user);
		System.out.println("Save User Data");
		return new ModelAndView("redirect:/details.html");
	}

	@RequestMapping("/retriveDayDetailsOfPatient")
	public ModelAndView SearchPatient1(@ModelAttribute("user") DuePatient user,
			BindingResult result) {
		System.out.println(user.getSaledate());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("user", duePatientService.getUserParticularDayDetails(user
				.getSaledate()));
		System.out.println("retriving user data Data");
		return new ModelAndView("DuePatient1", model);
	}

	/* THILAK */

	@Autowired
	private DepartmentService departmentService;

	@RequestMapping("/index1Departmentdetails")
	public ModelAndView search567(HttpServletRequest req,
			@ModelAttribute("user") Departmentpojo user, BindingResult result) {
		List<MainDepartmentpojo> l1 = departmentService.getMaindepartment();

		ArrayList<String> na = new ArrayList();
		ArrayList<String> la = new ArrayList();

		Iterator<MainDepartmentpojo> i1 = l1.iterator();
		while (i1.hasNext()) {

			MainDepartmentpojo product = (MainDepartmentpojo) i1.next();

			na.add(product.getMaindepartmentName());

		}
		String[] stockArr = new String[na.size()];
		stockArr = na.toArray(stockArr);
		List<Locationpojo> l2 = departmentService.getLocation();
		Iterator<Locationpojo> i2 = l2.iterator();
		while (i2.hasNext()) {

			Locationpojo product = (Locationpojo) i2.next();

			la.add(product.getLocation());

		}
		String[] stock = new String[la.size()];
		stock = la.toArray(stock);

		int s = departmentService.getidfromdepart();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("userinc", s);
		model.put("maindept", stockArr);
		model.put("location", stock);

		System.out.println("Saving file: ");

		ModelAndView m1 = new ModelAndView("Departmentsettingadd", model);
		m1.addObject("user", new Departmentpojo());
		m1.addObject("maindept", stockArr);
		m1.addObject("location", stock);
		m1.addObject("userinc", s);
		return m1;
	}

	@RequestMapping("/index")
	public ModelAndView searchMJMJ567(HttpServletRequest req) {
		List<MainDepartmentpojo> l1 = departmentService.getMaindepartment();

		ArrayList<String> na = new ArrayList();

		Iterator<MainDepartmentpojo> i1 = l1.iterator();
		while (i1.hasNext()) {

			MainDepartmentpojo product = (MainDepartmentpojo) i1.next();

			na.add(product.getMaindepartmentName());

		}
		String[] stockArr = new String[na.size()];
		stockArr = na.toArray(stockArr);
		List<Locationpojo> l2 = departmentService.getLocation();
		Iterator<Locationpojo> i2 = l2.iterator();
		while (i2.hasNext()) {

			Locationpojo product = (Locationpojo) i2.next();

			na.add(product.getLocation());

		}
		String[] stock = new String[na.size()];
		stock = na.toArray(stock);
		int s = departmentService.getidfromdepart();
		Map<String, Object> model = new HashMap<String, Object>();

		model.put("maindept", stockArr);
		model.put("location", stock);
		model.put("userinc", s);

		System.out.println("Saving file: ");

		ModelAndView m1 = new ModelAndView("Departmentsettingadd", model);
		m1.addObject("user", new Departmentpojo());
		m1.addObject("maindept", stockArr);
		m1.addObject("location", stock);
		m1.addObject("userinc", s);
		return m1;
	}

	@RequestMapping("/indexxv1")
	public ModelAndView searchsd567(HttpServletRequest req,
			@ModelAttribute("user") Departmentpojo user, BindingResult result) {

		ModelAndView m1 = new ModelAndView("Departmentsettingview");
		m1.addObject("abc", departmentService.getUser());

		return m1;
	}

	@RequestMapping("/indexxv110")
	public ModelAndView indexxv110(HttpServletRequest req,
			@ModelAttribute("user") Departmentpojo user, BindingResult result) {

		Map<String, Object> model = new HashMap<String, Object>();
		ArrayList<String> msg = new ArrayList<String>();
		msg.add("Record Inserted Successfully...");
		model.put("msg", msg);
		model.put("abc", departmentService.getUser());
		return new ModelAndView("Departmentsettingview", model);

	}

	@RequestMapping("/saveUser1Departmentde")
	public ModelAndView saveUserData567(
			@Valid @ModelAttribute("user") Departmentpojo user,
			BindingResult result) {
		System.out.println("asaa");

		boolean b = departmentService.saveUser(user);
		if (b) {
			/*
			 * Map<String, Object> model = new HashMap<String, Object>();
			 * model.put("abc", departmentService.getUser());
			 */
			return new ModelAndView("redirect:/indexxv110.html");
		} else {
			List<MainDepartmentpojo> l1 = departmentService.getMaindepartment();

			ArrayList<String> na = new ArrayList();
			ArrayList<String> la = new ArrayList();

			Iterator<MainDepartmentpojo> i1 = l1.iterator();
			while (i1.hasNext()) {

				MainDepartmentpojo product = (MainDepartmentpojo) i1.next();

				na.add(product.getMaindepartmentName());

			}
			String[] stockArr = new String[na.size()];
			stockArr = na.toArray(stockArr);
			List<Locationpojo> l2 = departmentService.getLocation();
			Iterator<Locationpojo> i2 = l2.iterator();
			while (i2.hasNext()) {

				Locationpojo product = (Locationpojo) i2.next();

				la.add(product.getLocation());

			}
			String[] stock = new String[la.size()];
			stock = la.toArray(stock);

			int s = departmentService.getidfromdepart();
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("userinc", s);
			model.put("maindept", stockArr);
			model.put("location", stock);

			System.out.println("Saving file: ");

			ArrayList<String> msg = new ArrayList<String>();
			msg.add("Department Already Exist");
			model.put("msg", msg);
			ModelAndView m1 = new ModelAndView("Departmentsettingadd", model);
			m1.addObject("user", new Departmentpojo());
			m1.addObject("maindept", stockArr);
			m1.addObject("location", stock);
			m1.addObject("userinc", s);
			return m1;

		}

		/*
		 * ModelAndView m1 = new ModelAndView("locationInserted1"); return
		 * "redirect:indexxv1.html";
		 */
	}

	@RequestMapping("/DEPORT")
	public ModelAndView saveUserDatadfbvf567(
			@Valid @ModelAttribute("user") Departmentpojo user,
			BindingResult result) {

		System.out.println("Save User Data");
		ModelAndView m1 = new ModelAndView("locationInserted1");
		m1.addObject("abc", departmentService.getUser());
		return m1;
	}

	@RequestMapping("/Departmentsettingadd")
	public ModelAndView retrive1567(HttpServletRequest http,
			HttpServletResponse response) throws SQLException, IOException {
		String depart = http.getParameter("hospitalName");
		System.out.println(depart);
		ModelAndView mv = new ModelAndView("Departmentsettingview");
		mv.addObject("abc", departmentService.getDepartment(depart));

		return mv;

	}

	@RequestMapping("/updateUser1")
	public ModelAndView updateUserData567(
			@Valid @ModelAttribute("user") Departmentpojo user,
			BindingResult result) {
		departmentService.updateUser(user);

		return new ModelAndView("redirect:/sucessmsUpdateg.html");
	}

	@RequestMapping("/sucessmsUpdateg")
	public ModelAndView sucessmsg(
			@Valid @ModelAttribute("user") Departmentpojo user,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		ArrayList<String> msg = new ArrayList<String>();
		msg.add("Record Updated Sucessfully ");
		System.out.println("Save User Data");
		ModelAndView m1 = new ModelAndView("locationInserted1");
		m1.addObject("abc", departmentService.getUser());
		return m1;
	}

	@RequestMapping(value = "/edit1")
	public ModelAndView deletepatient567(
			@ModelAttribute("user") Departmentpojo user, BindingResult result) {
		List<MainDepartmentpojo> l1 = departmentService.getMaindepartment();
		ArrayList<String> na = new ArrayList();
		ArrayList<String> la = new ArrayList();
		Iterator<MainDepartmentpojo> i1 = l1.iterator();
		while (i1.hasNext()) {

			MainDepartmentpojo product = (MainDepartmentpojo) i1.next();

			na.add(product.getMaindepartmentName());

		}
		String[] stockArr = new String[na.size()];
		stockArr = na.toArray(stockArr);
		List<Locationpojo> l2 = departmentService.getLocation();
		Iterator<Locationpojo> i2 = l2.iterator();
		while (i2.hasNext()) {

			Locationpojo product = (Locationpojo) i2.next();

			la.add(product.getLocation());
		}
		String[] stock = new String[la.size()];
		stock = la.toArray(stock);
		System.out.println("am in update controller");
		System.out.println(user.getId());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("user", (departmentService.getUser(user.getId())));
		model.put("maindept", stockArr);
		model.put("location", stock);
		System.out.println(user.getId());
		System.out.println("updated");
		System.out.println(model);
		ModelAndView m2 = new ModelAndView("update1", model);
		m2.addObject("maindept", stockArr);
		m2.addObject("location", stock);

		return m2;
	}

	@Autowired
	private LocationService LocationService;

	@RequestMapping("/index5")
	public ModelAndView search1567(HttpServletRequest req) {

		System.out.println("Saving file: ");

		ModelAndView m1 = new ModelAndView("Locationdetailsadd");
		m1.addObject("user", new Locationpojo1());
		return m1;
	}

	@RequestMapping("/indexxv")
	public ModelAndView searchsd1567(HttpServletRequest req) {

		System.out.println("Saving file: ");

		ModelAndView m1 = new ModelAndView("Locationsettingview");
		m1.addObject("abc", LocationService.getUser());

		return m1;
	}

	@RequestMapping("/saveUser")
	public ModelAndView saveUserData567(
			@ModelAttribute("user") Locationpojo1 user, BindingResult result) {

		boolean b = LocationService.addUser(user);
		System.out.println("Save User Data");

		if (b) {

			ModelAndView m1 = new ModelAndView("locationInserted");
			m1.addObject("abc", LocationService.getUser());
			return m1;

		} else {

			Map<String, Object> model = new HashMap<String, Object>();
			ArrayList<String> msg = new ArrayList<String>();
			msg.add("Flour Already Exist");
			model.put("msg", msg);
			return new ModelAndView("Locationdetailsadd", model);
		}

	}

	@RequestMapping("/Locationdetailsadd")
	public ModelAndView retrive567(HttpServletRequest http,
			HttpServletResponse response) throws SQLException, IOException {
		String floorName = http.getParameter("hospitalName");

		ModelAndView mv = new ModelAndView("Locationsettingview");
		mv.addObject("abc", LocationService.getLocation(floorName));
		return mv;
	}

	@RequestMapping("/updateUser")
	public ModelAndView updateUserData567(
			@Valid @ModelAttribute("user") Locationpojo1 user,
			BindingResult result) {
		LocationService.updateUser(user);
		System.out.println("Save User Data");
		return new ModelAndView("success1");
	}

	@RequestMapping(value = "/edit6", method = RequestMethod.POST)
	public ModelAndView deletepatient567(
			@ModelAttribute("user") Locationpojo1 user, BindingResult result) {

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("user", (LocationService.getLocation(user.getId())));

		ModelAndView m2 = new ModelAndView("update", model);
		return m2;
	}

	@Autowired
	private PatientService userService;

	@RequestMapping("/register")
	public ModelAndView getRegisterForm() {

		List<Dcotcorpojo> l1 = userService.getdoctors();

		ArrayList<String> na = new ArrayList();

		Iterator<Dcotcorpojo> i1 = l1.iterator();
		while (i1.hasNext()) {

			Dcotcorpojo product = (Dcotcorpojo) i1.next();

			na.add(product.getDoctorName());

		}
		String[] stockArr = new String[na.size()];
		stockArr = na.toArray(stockArr);

		ArrayList<String> gender = new ArrayList<String>();
		gender.add("Male");
		gender.add("Female");

		ArrayList<String> tokenMax = new ArrayList<String>();
		tokenMax.add(userService.getTokenMax());

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("gender", gender);
		model.put("doct", stockArr);
		model.put("tokenMax", tokenMax);

	
		ArrayList<String> state = new ArrayList<String>();
		state.add("Andhra Pradesh");
		state.add(" Arunachal Prades");
		state.add("Assam");
		state.add(" Bihar");
		state.add("Chhattisgarh");
		state.add("Goa");
		state.add("Gujarat");
		state.add("Haryana");
		state.add("Himachal Pradesh");
		state.add("Jammu & Kashmir");
		state.add("Jharkhand");
		state.add("Karnataka");
		state.add("Kerala");
		state.add("Madhya Pradesh");
		state.add("Maharashtra");
		state.add("Manipur");
		state.add("Meghalaya");
		state.add("Mizoram");
		state.add("Nagaland");
		state.add("Odisha");
		state.add("Punjab");
		state.add("Rajasthan");
		state.add("Sikkim");
		state.add("Tamil Nadu");
		state.add("Telangana");
		state.add("Tripura");
		state.add("Uttarakhand");
		state.add("Uttar Pradesh");
		state.add("West Bengal");
		state.add("Delhi");
		state.add("Andaman & Nicobar Island");
		state.add("Chandigarh");
		state.add("Dadra & Nagar Haveli");
		state.add("Daman & Diu");
		state.add("Lakshadweep");
		state.add("Puducherry");
		model.put("state", state);
		ModelAndView m = new ModelAndView("module3", model);
		m.addObject("model", model);
		m.addObject("doct", stockArr);
		m.addObject("user", new PatientPojo());
		return m;
	} 

	@RequestMapping(value = "/saveUser2", params = { "module3" })
	public ModelAndView saveUserData567(
			@Valid @ModelAttribute("user") PatientPojo user,
			BindingResult result) {

		userService.addUser(user);

		System.out.println("Save User Data");
		return new ModelAndView("redirect:/ofterSavingApDetais.html");
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping("/ofterSavingApDetais")
	public ModelAndView AppoinTmentFixedSucessfully(
			@ModelAttribute("user") PatientPojo user, BindingResult result) {
	
	System.out.println("Redirectuing To SamePage");
	
	
	List<Dcotcorpojo> l1 = userService.getdoctors();

	ArrayList<String> na = new ArrayList();

	Iterator<Dcotcorpojo> i1 = l1.iterator();
	while (i1.hasNext()) {

		Dcotcorpojo product = (Dcotcorpojo) i1.next();

		na.add(product.getDoctorName());

	}
	String[] stockArr = new String[na.size()];
	stockArr = na.toArray(stockArr);

	ArrayList<String> gender = new ArrayList<String>();
	gender.add("Male");
	gender.add("Female");

	ArrayList<String> tokenMax = new ArrayList<String>();
	tokenMax.add(userService.getTokenMax());

	Map<String, Object> model = new HashMap<String, Object>();
	model.put("gender", gender);
	model.put("doct", stockArr);
	model.put("tokenMax", tokenMax);


	ArrayList<String> state = new ArrayList<String>();
	state.add("Andhra Pradesh");
	state.add(" Arunachal Prades");
	state.add("Assam");
	state.add(" Bihar");
	state.add("Chhattisgarh");
	state.add("Goa");
	state.add("Gujarat");
	state.add("Haryana");
	state.add("Himachal Pradesh");
	state.add("Jammu & Kashmir");
	state.add("Jharkhand");
	state.add("Karnataka");
	state.add("Kerala");
	state.add("Madhya Pradesh");
	state.add("Maharashtra");
	state.add("Manipur");
	state.add("Meghalaya");
	state.add("Mizoram");
	state.add("Nagaland");
	state.add("Odisha");
	state.add("Punjab");
	state.add("Rajasthan");
	state.add("Sikkim");
	state.add("Tamil Nadu");
	state.add("Telangana");
	state.add("Tripura");
	state.add("Uttarakhand");
	state.add("Uttar Pradesh");
	state.add("West Bengal");
	state.add("Delhi");
	state.add("Andaman & Nicobar Island");
	state.add("Chandigarh");
	state.add("Dadra & Nagar Haveli");
	state.add("Daman & Diu");
	state.add("Lakshadweep");
	state.add("Puducherry");
	model.put("state", state);
	ModelAndView m = new ModelAndView("afterpatientappointment", model);
	m.addObject("model", model);
	m.addObject("doct", stockArr);
	m.addObject("user", new PatientPojo());
		
		
		return m;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping("/bcaSucessmsgSaved")
	public ModelAndView getUserList567Msggg(
			@ModelAttribute("user") PatientPojo user, BindingResult result) {
		List<Dcotcorpojo> l1 = userService.getdoctors();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("abc", userService.getUserDetails(1));
		ArrayList<String> na = new ArrayList();

		Iterator<Dcotcorpojo> i1 = l1.iterator();
		while (i1.hasNext()) {

			Dcotcorpojo product = (Dcotcorpojo) i1.next();

			na.add(product.getDoctorName());

		}
		String[] stockArr = new String[na.size()];
		stockArr = na.toArray(stockArr);
		ArrayList<String> msg = new ArrayList<String>();
		msg.add("Record Inserted Sucessfully ");
		model.put("msg1", msg);
		ModelAndView m2 = new ModelAndView("module1", model);
		m2.addObject("doct", stockArr);
		m2.addObject("abc", userService.getUserDetails(1));
		return m2;
	}

	@RequestMapping(value = "/saveUser2")
	public ModelAndView getDetailsOfPatientAppointMent(
			@Valid @ModelAttribute("user") PatientPojo user,
			BindingResult result) {
		List<Dcotcorpojo> l1 = userService.getdoctors();

		ArrayList<String> na = new ArrayList();
		Iterator<Dcotcorpojo> i1 = l1.iterator();
		while (i1.hasNext()) {

			Dcotcorpojo product = (Dcotcorpojo) i1.next();

			na.add(product.getDoctorName());

		}
		String[] stockArr = new String[na.size()];
		stockArr = na.toArray(stockArr);

		ArrayList<String> gender = new ArrayList<String>();
		gender.add("Male");
		gender.add("Female");

		ArrayList<String> tokenMax = new ArrayList<String>();
		tokenMax.add(userService.getTokenMaxBasedOnDoctor(user));

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("gender", gender);
		model.put("doct", stockArr);
		model.put("tokenMax", tokenMax);
		ArrayList<String> state = new ArrayList<String>();
		state.add("Andhra Pradesh");
		state.add(" Arunachal Prades");
		state.add("Assam");
		state.add(" Bihar");
		state.add("Chhattisgarh");
		state.add("Goa");
		state.add("Gujarat");
		state.add("Haryana");
		state.add("Himachal Pradesh");
		state.add("Jammu & Kashmir");
		state.add("Jharkhand");
		state.add("Karnataka");
		state.add("Kerala");
		state.add("Madhya Pradesh");
		state.add("Maharashtra");
		state.add("Manipur");
		state.add("Meghalaya");
		state.add("Mizoram");
		state.add("Nagaland");
		state.add("Odisha");
		state.add("Punjab");
		state.add("Rajasthan");
		state.add("Sikkim");
		state.add("Tamil Nadu");
		state.add("Telangana");
		state.add("Tripura");
		state.add("Uttarakhand");
		state.add("Uttar Pradesh");
		state.add("West Bengal");
		state.add("Delhi");
		state.add("Andaman & Nicobar Island");
		state.add("Chandigarh");
		state.add("Dadra & Nagar Haveli");
		state.add("Daman & Diu");
		state.add("Lakshadweep");
		state.add("Puducherry");
		model.put("state", state);
		ModelAndView m = new ModelAndView("module3", model);
		m.addObject("model", model);
		m.addObject("doct", stockArr);
		m.addObject("user", new PatientPojo());
		return m;

	}

	@RequestMapping(value = "/updateUser2")
	public ModelAndView updateUserData567(
			@ModelAttribute("user") PatientPojo user, BindingResult result) {
		userService.updateUser(user);
		return new ModelAndView("redirect:/bcaMsgUpdate.html");
	}

	@RequestMapping("/bcaMsgUpdate")
	public ModelAndView bcaMsg(@ModelAttribute("user") PatientPojo user,
			BindingResult result) {
		List<Dcotcorpojo> l1 = userService.getdoctors();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("abc", userService.getUserDetails(1));
		ArrayList<String> na = new ArrayList();

		Iterator<Dcotcorpojo> i1 = l1.iterator();
		while (i1.hasNext()) {

			Dcotcorpojo product = (Dcotcorpojo) i1.next();

			na.add(product.getDoctorName());

		}
		String[] stockArr = new String[na.size()];
		stockArr = na.toArray(stockArr);
		ArrayList<String> msg = new ArrayList<String>();
		msg.add("Record Updated Sucessfully");
		model.put("msg", msg);

		ModelAndView m2 = new ModelAndView("module1", model);
		m2.addObject("doct", stockArr);
		m2.addObject("abc", userService.getUserDetails(1));
		return m2;
	}

	@RequestMapping("/bca")
	public ModelAndView getUserList567(
			@ModelAttribute("user") PatientPojo user, BindingResult result,
			HttpSession session) {
		List<Dcotcorpojo> l1 = userService.getdoctors();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("abc", userService.getUserDetails(1));
		ArrayList<String> na = new ArrayList();

		Iterator<Dcotcorpojo> i1 = l1.iterator();
		while (i1.hasNext()) {

			Dcotcorpojo product = (Dcotcorpojo) i1.next();

			na.add(product.getDoctorName());

		}
		session.setAttribute("pageno", 1);
		String[] stockArr = new String[na.size()];
		stockArr = na.toArray(stockArr);
		ModelAndView m2 = new ModelAndView("module1", model);
		m2.addObject("doct", stockArr);
		m2.addObject("abc", userService.getUserDetails(1));
		return m2;
	}

	@RequestMapping("/getNextappointmentpage")
	public ModelAndView getPriviousappointmentpage(
			@ModelAttribute("user") PatientPojo user, BindingResult result,
			HttpSession session) {
		Integer pageno = (Integer) session.getAttribute("pageno");
		pageno = pageno + 1;
		session.setAttribute("pageno", pageno);
		List<Dcotcorpojo> l1 = userService.getdoctors();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("abc", userService.getUserDetails(pageno));
		ArrayList<String> na = new ArrayList();

		Iterator<Dcotcorpojo> i1 = l1.iterator();

		ArrayList<Integer> max = new ArrayList<Integer>();
		max.add(userService.getmaxValue());

		int totalpageNo = 0;

		for (Integer m : max) {
			System.out.println("am in for each");
			totalpageNo = m;
			System.out.println(totalpageNo);
		}

		if (totalpageNo % 4 == 0) {
			totalpageNo = totalpageNo / 4;
		} else {

			totalpageNo = (totalpageNo / 4) + 1;
		}

		System.out.println(totalpageNo);
		System.out.println(totalpageNo);

		totalpageNo = totalpageNo / 4;
		if (pageno < totalpageNo) {
			pageno = 1;
			model.put("abc", userService.getUserDetails(pageno));
		}
		System.out.println(pageno);
		session.setAttribute("pageno", pageno);

		while (i1.hasNext()) {

			Dcotcorpojo product = (Dcotcorpojo) i1.next();

			na.add(product.getDoctorName());

		}

		String[] stockArr = new String[na.size()];
		stockArr = na.toArray(stockArr);
		ModelAndView m2 = new ModelAndView("module1", model);
		m2.addObject("doct", stockArr);
		m2.addObject("abc", userService.getUserDetails(pageno));
		return m2;
	}

	@RequestMapping("/getPriviousappointmentpage")
	public ModelAndView getNextappointmentpage(
			@ModelAttribute("user") PatientPojo user, BindingResult result,
			HttpSession session) {
		List<Dcotcorpojo> l1 = userService.getdoctors();
		Integer pageno = (Integer) session.getAttribute("pageno");
		pageno = pageno - 1;
		if (pageno <= 0) {
			pageno = 1;
		}
		session.setAttribute("pageno", pageno);

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("abc", userService.getUserDetails(pageno));
		ArrayList<String> na = new ArrayList();

		Iterator<Dcotcorpojo> i1 = l1.iterator();
		while (i1.hasNext()) {

			Dcotcorpojo product = (Dcotcorpojo) i1.next();

			na.add(product.getDoctorName());

		}
		String[] stockArr = new String[na.size()];
		stockArr = na.toArray(stockArr);
		ModelAndView m2 = new ModelAndView("module1", model);
		m2.addObject("doct", stockArr);
		m2.addObject("abc", userService.getUserDetails(pageno));
		return m2;
	}

	@RequestMapping("/get")
	public ModelAndView getenquery567(@ModelAttribute("user") PatientPojo user,
			BindingResult result) {
		/* String s1=http.getParameter("patient"); */
		/* String s2=http.getParameter("doctor"); */
		String s1 = user.getAppointmentDate();
		String s2 = user.getDoctorName();
		System.out.println(s1 + " " + s2);
		System.out.println(s1 + " " + s2);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("abc", userService.getUser(s1, s2));
		System.out.println(s1 + " " + s2);
		System.out.println(model);

		List<Dcotcorpojo> l1 = userService.getdoctors();

		ArrayList<String> na = new ArrayList();

		Iterator<Dcotcorpojo> i1 = l1.iterator();
		while (i1.hasNext()) {

			Dcotcorpojo product = (Dcotcorpojo) i1.next();

			na.add(product.getDoctorName());

		}
		String[] stockArr = new String[na.size()];
		stockArr = na.toArray(stockArr);

		ModelAndView m1 = new ModelAndView("module1");
		m1.addObject("abc", userService.getUser(s1, s2));
		m1.addObject("doct", stockArr);
		return m1;
	}

	@RequestMapping(value = "/delete")
	public ModelAndView editpatient567(
			@ModelAttribute("user") PatientPojo user, BindingResult result) {
		System.out.println("in delete controller");
		System.out.println(user.getId());
		userService.deletepatient(user);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("abc", userService.getUser(null, null));
		System.out.println("deleted");
		return new ModelAndView("redirect:/bcaMsg.html");
	}

	@RequestMapping("/bcaMsg")
	public ModelAndView getUserList567Msg(
			@ModelAttribute("user") PatientPojo user, BindingResult result) {
		List<Dcotcorpojo> l1 = userService.getdoctors();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("abc", userService.getUserDetails(1));
		ArrayList<String> na = new ArrayList();

		Iterator<Dcotcorpojo> i1 = l1.iterator();
		while (i1.hasNext()) {

			Dcotcorpojo product = (Dcotcorpojo) i1.next();

			na.add(product.getDoctorName());

		}
		String[] stockArr = new String[na.size()];
		stockArr = na.toArray(stockArr);
		ArrayList<String> msg = new ArrayList();
		msg.add("Record Deleted Sucessfully");
		model.put("msg", msg);
		ModelAndView m2 = new ModelAndView("module1", model);
		m2.addObject("doct", stockArr);
		m2.addObject("abc", userService.getUserDetails(1));
		return m2;
	}

	@RequestMapping(value = "/edit2", method = RequestMethod.POST)
	public ModelAndView deletepatient567(
			@ModelAttribute("user") PatientPojo user, BindingResult result) {

		Map<String, Object> model = new HashMap<String, Object>();

		List<Dcotcorpojo> l1 = userService.getdoctors();

		ArrayList<String> na = new ArrayList();

		Iterator<Dcotcorpojo> i1 = l1.iterator();
		while (i1.hasNext()) {

			Dcotcorpojo product = (Dcotcorpojo) i1.next();

			na.add(product.getDoctorName());

		}
		String[] stockArr = new String[na.size()];
		stockArr = na.toArray(stockArr);
		ArrayList<String> gender = new ArrayList<String>();
		gender.add("Male");
		gender.add("Female");
		model.put("user1", (userService.getPatient(user.getId())));
		model.put("gender", gender);
		System.out.println(user.getId());
		System.out.println("updated3");

		ArrayList<String> state = new ArrayList<String>();
		state.add("Andhra Pradesh");
		state.add(" Arunachal Prades");
		state.add("Assam");
		state.add(" Bihar");
		state.add("Chhattisgarh");
		state.add("Goa");
		state.add("Gujarat");
		state.add("Haryana");
		state.add("Himachal Pradesh");
		state.add("Jammu & Kashmir");
		state.add("Jharkhand");
		state.add("Karnataka");
		state.add("Kerala");
		state.add("Madhya Pradesh");
		state.add("Maharashtra");
		state.add("Manipur");
		state.add("Meghalaya");
		state.add("Mizoram");
		state.add("Nagaland");
		state.add("Odisha");
		state.add("Punjab");
		state.add("Rajasthan");
		state.add("Sikkim");
		state.add("Tamil Nadu");
		state.add("Telangana");
		state.add("Tripura");
		state.add("Uttarakhand");
		state.add("Uttar Pradesh");
		state.add("West Bengal");
		state.add("Delhi");
		state.add("Andaman & Nicobar Island");
		state.add("Chandigarh");
		state.add("Dadra & Nagar Haveli");
		state.add("Daman & Diu");
		state.add("Lakshadweep");
		state.add("Puducherry");
		model.put("state", state);
		ModelAndView m1 = new ModelAndView("update3", model);
		m1.addObject("doct", stockArr);
		m1.addObject("gr", new String[] { "Male", "FeMale" });

		return m1;
	}

	@Autowired
	private PatientServise1 patientservice;

	@RequestMapping("/index3")
	public ModelAndView search12567(HttpServletRequest req) {

		System.out.println("Saving file: ");
		ModelAndView mv = new ModelAndView("searchemploye1");
		mv.addObject("abc", patientservice.getpatientDetails());
		return mv;
	}

	@RequestMapping("/index4")
	public ModelAndView retrive12567(HttpServletRequest http,
			HttpServletResponse response) throws SQLException, IOException {
		String regno = http.getParameter("searchMrPtnNo");

		ModelAndView mv = new ModelAndView("searchemploye1");
		mv.addObject("abc", patientservice.getpatient(regno));

		return mv;

	}

	/* RAMAKRISHNA */

	@Autowired
	private PatientServiceDetails patientservice12045;

	@RequestMapping("/index100")
	public ModelAndView index() {

		Integer j = patientservice12045.getid();

		ArrayList<String> na = new ArrayList();
		ArrayList<Integer> fe = new ArrayList<>();
		List<DoctotNames> l1 = patientservice12045.getdoctors();
		ArrayList<String> names = new ArrayList();
		ArrayList<Integer> fees = new ArrayList<>();

		Map<String, Object> model = new HashMap<String, Object>();
		ArrayList<String> state = new ArrayList<String>();
		state.add("Andhra Pradesh");
		state.add(" Arunachal Prades");
		state.add("Assam");
		state.add(" Bihar");
		state.add("Chhattisgarh");
		state.add("Goa");
		state.add("Gujarat");
		state.add("Haryana");
		state.add("Himachal Pradesh");
		state.add("Jammu & Kashmir");
		state.add("Jharkhand");
		state.add("Karnataka");
		state.add("Kerala");
		state.add("Madhya Pradesh");
		state.add("Maharashtra");
		state.add("Manipur");
		state.add("Meghalaya");
		state.add("Mizoram");
		state.add("Nagaland");
		state.add("Odisha");
		state.add("Punjab");
		state.add("Rajasthan");
		state.add("Sikkim");
		state.add("Tamil Nadu");
		state.add("Telangana");
		state.add("Tripura");
		state.add("Uttarakhand");
		state.add("Uttar Pradesh");
		state.add("West Bengal");
		state.add("Delhi");
		state.add("Andaman & Nicobar Island");
		state.add("Chandigarh");
		state.add("Dadra & Nagar Haveli");
		state.add("Daman & Diu");
		state.add("Lakshadweep");
		state.add("Puducherry");
		model.put("state", state);

		String regnonew = patientservice12045.getrenumber();

		Iterator<DoctotNames> i1 = l1.iterator();
		while (i1.hasNext()) {

			DoctotNames product = (DoctotNames) i1.next();

			na.add(product.getDoctorName());
			fe.add(product.getFee());
			names.add(product.getDoctorName());
			fees.add(product.getFee());
		}
		String[] stockArr = new String[na.size()];
		stockArr = na.toArray(stockArr);

		Integer[] w = new Integer[fe.size()];
		w = fe.toArray(w);

		String[] namesd = new String[names.size()];
		namesd = names.toArray(namesd);

		Integer[] feesd = new Integer[fees.size()];
		feesd = fees.toArray(feesd);

		String[] stockArr45 = new String[] { " Male  ", "  Female  " };

		Date date = new Date();
		// Specify the desired date format
		String DATE_FORMAT = "MM/dd/yyyy";
		// Create object of SimpleDateFormat and pass the desired date format.
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		/*
		 * Use format method of SimpleDateFormat class to format the date.
		 */
		System.out.println("Today is " + sdf.format(date));
		ModelAndView m1 = new ModelAndView("registration", model);
		m1.addObject("patient", new AddPatient1());
		m1.addObject("doct", stockArr);
		m1.addObject("id", j);
		m1.addObject("fee", w);
		m1.addObject("sxmf", stockArr45);
		m1.addObject("time", sdf.format(date));
		m1.addObject("docters", patientservice12045.getPerson());
		m1.addObject("ma", "Select");
		m1.addObject("india", "");
		m1.addObject("docternames", namesd);
		m1.addObject("feesdoct", feesd);
		m1.addObject("regno", regnonew);

		return m1;
	}

	@RequestMapping("/saveaddnewpatient12")
	public ModelAndView duplicate(
			@ModelAttribute("patient") AddPatient1 document,
			HttpServletRequest req, HttpSession hht) {

		String s1 = document.getRegistNo();
		String s2 = document.getPatientName();
		String s3 = document.getOccupation();
		String s4 = document.getGourdenName();
		Long s5 = document.getAge();
		String s6 = document.getSex();
		String s7 = document.getRemarks();
		String s8 = document.getRegistDate();
		String s9 = document.getAddress1();
		String s17 = document.getAddress();

		String s10 = document.getCity();
		Long s11 = document.getPincode();
		String s12 = document.getState();
		String s13 = document.getCountry();

		String s14 = document.getDoctorName();
		Long s16 = document.getPhone();
		String appoint = document.getAppointDate();
		int s15 = 0;

		Map<String, Object> model = new HashMap<String, Object>();

		ArrayList<String> state = new ArrayList<String>();
		state.add("Andhra Pradesh");
		state.add(" Arunachal Prades");
		state.add("Assam");
		state.add(" Bihar");
		state.add("Chhattisgarh");
		state.add("Goa");
		state.add("Gujarat");
		state.add("Haryana");
		state.add("Himachal Pradesh");
		state.add("Jammu & Kashmir");
		state.add("Jharkhand");
		state.add("Karnataka");
		state.add("Kerala");
		state.add("Madhya Pradesh");
		state.add("Maharashtra");
		state.add("Manipur");
		state.add("Meghalaya");
		state.add("Mizoram");
		state.add("Nagaland");
		state.add("Odisha");
		state.add("Punjab");
		state.add("Rajasthan");
		state.add("Sikkim");
		state.add("Tamil Nadu");
		state.add("Telangana");
		state.add("Tripura");
		state.add("Uttarakhand");
		state.add("Uttar Pradesh");
		state.add("West Bengal");
		state.add("Delhi");
		state.add("Andaman & Nicobar Island");
		state.add("Chandigarh");
		state.add("Dadra & Nagar Haveli");
		state.add("Daman & Diu");
		state.add("Lakshadweep");
		state.add("Puducherry");
		model.put("state", state);

		List<DoctotNames> docterf = patientservice12045.getDoctorfee(s14);
		for (DoctotNames addPatient1 : docterf) {
			s15 = addPatient1.getFee();
			System.out.println(s15);
		}

		String data1 = req.getParameter("data1");
		Integer j = patientservice12045.getid();

		ArrayList<String> na = new ArrayList();
		ArrayList<Integer> fe = new ArrayList<>();
		List<DoctotNames> l1 = patientservice12045.getdoctors();
		ArrayList<String> names = new ArrayList();
		ArrayList<Integer> fees = new ArrayList<>();

		String regnonew = patientservice12045.getrenumber();
		String token = "";

		if (s14 != "" && appoint != "") {

			token = patientservice12045.gettokenno(s14, appoint);

		}

		Iterator<DoctotNames> i1 = l1.iterator();
		while (i1.hasNext()) {

			DoctotNames product = (DoctotNames) i1.next();

			na.add(product.getDoctorName());
			fe.add(product.getFee());
			names.add(product.getDoctorName());
			fees.add(product.getFee());
		}
		String[] stockArr = new String[na.size()];
		stockArr = na.toArray(stockArr);

		Integer[] w = new Integer[fe.size()];
		w = fe.toArray(w);

		String[] namesd = new String[names.size()];
		namesd = names.toArray(namesd);

		Integer[] feesd = new Integer[fees.size()];
		feesd = fees.toArray(feesd);

		String[] stockArr45 = new String[] { "  Male  ", "  Female  ", "Others" };

		hht.setAttribute("data1", req.getParameter("data1"));

		Date date = new Date();
		// Specify the desired date format
		String DATE_FORMAT = "MM/dd/yyyy";
		// Create object of SimpleDateFormat and pass the desired date format.
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		/*
		 * Use format method of SimpleDateFormat class to format the date.
		 */
		System.out.println("Today is " + sdf.format(date));
		ModelAndView m1 = new ModelAndView("registration", model);
		m1.addObject("patient", new AddPatient1());
		m1.addObject("doct", stockArr);
		m1.addObject("id", j);
		m1.addObject("fee", w);
		/* m1.addObject("sxmf", stockArr45); */
		m1.addObject("time", sdf.format(date));
		m1.addObject("docters", patientservice12045.getPerson());

		m1.addObject("docternames", namesd);
		m1.addObject("feesdoct", feesd);
		m1.addObject("regno", regnonew);

		m1.addObject("s1", s1);
		m1.addObject("s2", s2);
		m1.addObject("s3", s3);
		m1.addObject("s4", s4);

		m1.addObject("s5", s5);
		m1.addObject("s6", s6);
		m1.addObject("s7", s7);
		m1.addObject("s8", s8);

		m1.addObject("s9", s9);
		m1.addObject("s10", s10);
		m1.addObject("s11", s11);
		m1.addObject("s12", s12);

		m1.addObject("s13", s13);
		m1.addObject("s14", s14);
		m1.addObject("s15", s15);
		m1.addObject("s16", s16);
		m1.addObject("tkn", token);
		m1.addObject("s17", s17);
		m1.addObject("appoint", appoint);

		return m1;
	}

	@RequestMapping(value = "/saveaddnewpatient12", params = { "add" })
	public String saveLoginData(
			@ModelAttribute("patient") AddPatient1 document,
			HttpServletRequest req, HttpSession hht) {

		System.out.println("Saving file: ");
		System.out.println(document.getAddress());
		/*
		 * String s1=req.getParameter("data1"); String dada1=(String)
		 * hht.getAttribute("data1"); document.setAppointDate(dada1);
		 */
		patientservice12045.adduser(document);
		/*
		 * ModelAndView m1=new ModelAndView("success567"); m1.addObject("abc",
		 * patientservice.getpatients());
		 */

		return "redirect:finalPageADDpatient.html";
	}

	
	
	@RequestMapping(value = "/finalPageADDpatient")
	public ModelAndView OfterSavingReturnToSamePage(
			@ModelAttribute("patient") AddPatient1 document,
			HttpServletRequest req, HttpSession hht) {
		Integer j = patientservice12045.getid();

		ArrayList<String> na = new ArrayList();
		ArrayList<Integer> fe = new ArrayList<>();
		List<DoctotNames> l1 = patientservice12045.getdoctors();
		ArrayList<String> names = new ArrayList();
		ArrayList<Integer> fees = new ArrayList<>();

		Map<String, Object> model = new HashMap<String, Object>();
		ArrayList<String> state = new ArrayList<String>();
		state.add("Andhra Pradesh");
		state.add(" Arunachal Prades");
		state.add("Assam");
		state.add(" Bihar");
		state.add("Chhattisgarh");
		state.add("Goa");
		state.add("Gujarat");
		state.add("Haryana");
		state.add("Himachal Pradesh");
		state.add("Jammu & Kashmir");
		state.add("Jharkhand");
		state.add("Karnataka");
		state.add("Kerala");
		state.add("Madhya Pradesh");
		state.add("Maharashtra");
		state.add("Manipur");
		state.add("Meghalaya");
		state.add("Mizoram");
		state.add("Nagaland");
		state.add("Odisha");
		state.add("Punjab");
		state.add("Rajasthan");
		state.add("Sikkim");
		state.add("Tamil Nadu");
		state.add("Telangana");
		state.add("Tripura");
		state.add("Uttarakhand");
		state.add("Uttar Pradesh");
		state.add("West Bengal");
		state.add("Delhi");
		state.add("Andaman & Nicobar Island");
		state.add("Chandigarh");
		state.add("Dadra & Nagar Haveli");
		state.add("Daman & Diu");
		state.add("Lakshadweep");
		state.add("Puducherry");
		model.put("state", state);

		String regnonew = patientservice12045.getrenumber();

		Iterator<DoctotNames> i1 = l1.iterator();
		while (i1.hasNext()) {

			DoctotNames product = (DoctotNames) i1.next();

			na.add(product.getDoctorName());
			fe.add(product.getFee());
			names.add(product.getDoctorName());
			fees.add(product.getFee());
		}
		String[] stockArr = new String[na.size()];
		stockArr = na.toArray(stockArr);

		Integer[] w = new Integer[fe.size()];
		w = fe.toArray(w);

		String[] namesd = new String[names.size()];
		namesd = names.toArray(namesd);

		Integer[] feesd = new Integer[fees.size()];
		feesd = fees.toArray(feesd);

		String[] stockArr45 = new String[] { " Male  ", "  Female  " };

		Date date = new Date();
		// Specify the desired date format
		String DATE_FORMAT = "MM/dd/yyyy";
		// Create object of SimpleDateFormat and pass the desired date format.
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		/*
		 * Use format method of SimpleDateFormat class to format the date.
		 */
		System.out.println("Today is " + sdf.format(date));
		ModelAndView m1 = new ModelAndView("registration", model);
		m1.addObject("patient", new AddPatient1());
		m1.addObject("doct", stockArr);
		m1.addObject("id", j);
		m1.addObject("fee", w);
		m1.addObject("sxmf", stockArr45);
		m1.addObject("time", sdf.format(date));
		m1.addObject("docters", patientservice12045.getPerson());
		m1.addObject("ma", "Select");
		m1.addObject("india", "");
		m1.addObject("docternames", namesd);
		m1.addObject("feesdoct", feesd);
		m1.addObject("regno", regnonew);

		return m1;

		
	}

	
	
	
	@RequestMapping(value = "/finalPageADDpatient")
	public ModelAndView saveLoginDataSDG(
			@ModelAttribute("patient") AddPatient1 document,
			HttpServletRequest req, HttpSession hht) {

		ModelAndView m1 = new ModelAndView("success567");
		m1.addObject("abc", patientservice12045.getpatients(1));

		return m1;
	}

	@RequestMapping("/index1")
	public ModelAndView search(HttpServletRequest req) {

		System.out.println("Saving file: ");

		return new ModelAndView("searchemploye");
	}

	@RequestMapping("/index2")
	public ModelAndView retrive(HttpServletRequest http,
			HttpServletResponse response) throws SQLException, IOException {
		String username = http.getParameter("name");
		String regno = http.getParameter("regno");
		String date = http.getParameter("date");

		System.out.println(username);
		System.out.println(regno);
		System.out.println(date);

		ModelAndView mv = new ModelAndView("patientdetails");

		int i = 0;

		if (username != "" && regno != "") {
			System.out.println("bhai");
			List<AddPatient1> l1 = patientservice12045.getpatient(regno);
			for (AddPatient1 addPatient1 : l1) {
				i += 1;

			}
			mv.addObject("abc", l1);

		} else if (regno != "" && date != "") {
			System.out.println("from");
			List<AddPatient1> l1 = patientservice12045.getpatient(regno);
			for (AddPatient1 addPatient1 : l1) {
				i += 1;

			}
			mv.addObject("abc", l1);
		} else if (regno != "") {
			System.out.println("hai");
			List<AddPatient1> l1 = patientservice12045.getpatient(regno);
			for (AddPatient1 addPatient1 : l1) {
				i += 1;

			}
			mv.addObject("abc", l1);
		} else if (username != "" && regno != "" && date != "") {
			System.out.println("3");
			List<AddPatient1> l1 = patientservice12045.getpatient(regno);
			for (AddPatient1 addPatient1 : l1) {
				i += 1;

			}
			mv.addObject("abc", l1);
		} else if (username != "") {
			List<AddPatient1> l1 = patientservice12045.getbyname(username);
			for (AddPatient1 addPatient1 : l1) {
				i += 1;

			}
			mv.addObject("abc", l1);

		} else if (date != "") {
			List<AddPatient1> l1 = patientservice12045.getbydate(date);
			for (AddPatient1 addPatient1 : l1) {
				i += 1;

			}
			mv.addObject("abc", l1);

		} else {
			List<AddPatient1> l1 = patientservice12045.getpatient(regno);
			for (AddPatient1 addPatient1 : l1) {
				i += 1;
			}
			mv.addObject("abc", l1);
		}

		mv.addObject("patints", i);

		return mv;
	}

	@RequestMapping("/editaddpatient1205")
	public ModelAndView edit(HttpServletRequest req) {

		String s1 = req.getParameter("registNo");
		String[] stockArr45 = new String[] { "Male", "Female", "Others" };
		ArrayList<String> state = new ArrayList<String>();
		state.add("Andhra Pradesh");
		state.add(" Arunachal Prades");
		state.add("Assam");
		state.add(" Bihar");
		state.add("Chhattisgarh");
		state.add("Goa");
		state.add("Gujarat");
		state.add("Haryana");
		state.add("Himachal Pradesh");
		state.add("Jammu & Kashmir");
		state.add("Jharkhand");
		state.add("Karnataka");
		state.add("Kerala");
		state.add("Madhya Pradesh");
		state.add("Maharashtra");
		state.add("Manipur");
		state.add("Meghalaya");
		state.add("Mizoram");
		state.add("Nagaland");
		state.add("Odisha");
		state.add("Punjab");
		state.add("Rajasthan");
		state.add("Sikkim");
		state.add("Tamil Nadu");
		state.add("Telangana");
		state.add("Tripura");
		state.add("Uttarakhand");
		state.add("Uttar Pradesh");
		state.add("West Bengal");
		state.add("Delhi");
		state.add("Andaman & Nicobar Island");
		state.add("Chandigarh");
		state.add("Dadra & Nagar Haveli");
		state.add("Daman & Diu");
		state.add("Lakshadweep");
		state.add("Puducherry");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("state", state);
		ArrayList<String> na = new ArrayList();
		ArrayList<Integer> fe = new ArrayList<>();
		List<DoctotNames> l1 = patientservice12045.getdoctors();

		Iterator<DoctotNames> i1 = l1.iterator();
		while (i1.hasNext()) {

			DoctotNames product = (DoctotNames) i1.next();

			na.add(product.getDoctorName());
			fe.add(product.getFee());
		}

		String[] stockArr = new String[na.size()];
		stockArr = na.toArray(stockArr);

		Integer[] w = new Integer[fe.size()];
		w = fe.toArray(w);
		List<AddPatient1> b1 = patientservice12045.getpatient(s1);

		String c1 = "";
		Long j = 0L;
		String cou = "";
		String country = "";
		String MF = "";
		for (AddPatient1 addPatient1 : b1) {

			c1 = addPatient1.getDoctorName();
			j = addPatient1.getRegFee();
			cou = addPatient1.getRemarks();
			country = addPatient1.getCountry();
			MF = addPatient1.getSex();

		}
		System.out.println(j);
		Date date = new Date();
		// Specify the desired date format
		String DATE_FORMAT = "MM/dd/yyyy";
		// Create object of SimpleDateFormat and pass the desired date format.
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		/*
		 * Use format method of SimpleDateFormat class to format the date.
		 */
		System.out.println("Today is " + sdf.format(date));
		ModelAndView m1 = new ModelAndView("edit", model);
		m1.addObject("xyz", b1);
		m1.addObject("patient", new AddPatient1());
		m1.addObject("doct", stockArr);
		m1.addObject("fee", w);
		m1.addObject("ab", c1);
		m1.addObject("sxmf", stockArr45);
		m1.addObject("time", sdf.format(date));
		m1.addObject("dfee", j);
		m1.addObject("rem", cou);
		m1.addObject("count", country);

		m1.addObject("sex", MF);
		return m1;
	}

	@RequestMapping("/update")
	public ModelAndView duplicateedit(
			@ModelAttribute("patient") AddPatient1 document,
			HttpServletRequest req, HttpSession hht) {

		System.out.println("hi from the updaya");
		String s1 = document.getRegistNo();

		hht.setAttribute("s1", s1);
		System.out.println(s1);
		String s2 = document.getPatientName();
		String s3 = document.getOccupation();
		String s4 = document.getGourdenName();
		Long s5 = document.getAge();

		String s6 = document.getSex();
		String s7 = req.getParameter("remarks");

		System.out.println(s7);

		String s8 = document.getRegistDate();
		String s9 = document.getAddress1();
		String s17 = document.getAddress();

		String s10 = document.getCity();
		Long s11 = document.getPincode();
		String s12 = document.getState();
		String s13 = document.getCountry();

		String s14 = document.getDoctorName();
		Long s16 = document.getPhone();
		String appoint1 = document.getAppointDate();

		int s15 = 0;
		List<DoctotNames> docterf = patientservice12045.getDoctorfee(s14);
		for (DoctotNames addPatient1 : docterf) {
			s15 = addPatient1.getFee();
			System.out.println(s15);
		}

		String data1 = req.getParameter("data1");
		hht.setAttribute("editdata", data1);

		Integer j = patientservice12045.getid();

		ArrayList<String> na = new ArrayList();
		ArrayList<Integer> fe = new ArrayList<>();
		List<DoctotNames> l1 = patientservice12045.getdoctors();
		ArrayList<String> names = new ArrayList();
		ArrayList<Integer> fees = new ArrayList<>();

		String regnonew = patientservice12045.getrenumber();
		String token = "";

		if (s14 != "" && appoint1 != "") {

			token = patientservice12045.gettokenno(s14, appoint1);

		}

		Iterator<DoctotNames> i1 = l1.iterator();
		while (i1.hasNext()) {

			DoctotNames product = (DoctotNames) i1.next();

			na.add(product.getDoctorName());
			fe.add(product.getFee());
			names.add(product.getDoctorName());
			fees.add(product.getFee());
		}
		String[] stockArr = new String[na.size()];
		stockArr = na.toArray(stockArr);

		Integer[] w = new Integer[fe.size()];
		w = fe.toArray(w);

		String[] namesd = new String[names.size()];
		namesd = names.toArray(namesd);

		Integer[] feesd = new Integer[fees.size()];
		feesd = fees.toArray(feesd);

		String[] stockArr45 = new String[] { "  Male  ", "  Female  ", "Others" };

		hht.setAttribute("data1", req.getParameter("data1"));

		Date date = new Date();
		// Specify the desired date format
		String DATE_FORMAT = "MM/dd/yyyy";
		// Create object of SimpleDateFormat and pass the desired date format.
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		/*
		 * Use format method of SimpleDateFormat class to format the date.
		 */
		System.out.println("Today is " + sdf.format(date));
		ModelAndView m1 = new ModelAndView();
		m1.addObject("patient1", new AddPatient1());
		m1.addObject("doct", stockArr);
		m1.addObject("id", j);
		m1.addObject("fee", w);
		m1.addObject("sxmf", stockArr45);
		m1.addObject("time", sdf.format(date));
		m1.addObject("docters", patientservice12045.getPerson());
		m1.setViewName("edit1");
		m1.addObject("docternames", namesd);
		m1.addObject("feesdoct", feesd);
		/* m1.addObject("regno", regnonew); */

		/* m1.addObject("s1", s1); */
		m1.addObject("s2", s2);
		m1.addObject("s3", s3);
		m1.addObject("s4", s4);

		m1.addObject("s5", s5);
		m1.addObject("s6", s6);
		m1.addObject("s7", s7);
		m1.addObject("s8", s8);

		m1.addObject("s9", s9);
		m1.addObject("s10", s10);
		m1.addObject("s11", s11);
		m1.addObject("s12", s12);

		m1.addObject("s13", s13);
		m1.addObject("s14", s14);
		m1.addObject("s15", s15);
		m1.addObject("s16", s16);
		m1.addObject("tkn", token);
		m1.addObject("s17", s17);
		m1.addObject("appoint1", appoint1);

		return m1;
	}

	@RequestMapping(value = "/update", params = { "updatedata" })
	public ModelAndView update(
			@ModelAttribute("patient1") AddPatient1 document,
			HttpServletRequest re, HttpSession hht) {
		hht.setAttribute("pageno", 1);
		/*
		 * String s1=re.getParameter("remark"); String editdata=(String)
		 * hht.getAttribute("editdata");
		 * 
		 * document.setRemarks(s1); document.setAppointDate(editdata);
		 */

		String remark = re.getParameter("remarks");
		document.setRemarks(remark);
		int i = patientservice12045.update(document);
		System.out.println(i);

		ModelAndView m1 = new ModelAndView("success1567");
		m1.addObject("abc", patientservice12045.getpatients(1));

		return m1;
	}

	@RequestMapping("/add")
	public ModelAndView add(@ModelAttribute("patient") AddPatient1 document,
			HttpSession session) {
		List<AddPatient1> l1 = null;
		session.setAttribute("pageno", 1);
		int i = 0;
		ModelAndView m1 = new ModelAndView("patientdetails");
		m1.addObject("abc", l1 = patientservice12045.getpatients(1));
		for (AddPatient1 addPatient1 : l1) {
			i += 1;
		}
		m1.addObject("patints", i);
		return m1;
	}

	@RequestMapping("/getNextmlcpage")
	public ModelAndView getPriviousmlcpage(
			@ModelAttribute("patient") AddPatient1 document, HttpSession session) {

		List<AddPatient1> l1 = null;
		int i = 0;
		Integer pageno = (Integer) session.getAttribute("pageno");
		pageno = pageno + 1;
		session.setAttribute("pageno", pageno);
		ModelAndView m1 = new ModelAndView("patientdetails");
		m1.addObject("abc", l1 = patientservice12045.getpatients(pageno));
		for (AddPatient1 addPatient1 : l1) {
			i += 1;
		}
		m1.addObject("patints", i);
		return m1;
	}

	@RequestMapping("/getPriviousmlcpage")
	public ModelAndView getNextmlcpage(
			@ModelAttribute("patient") AddPatient1 document, HttpSession session) {
		Integer pageno = (Integer) session.getAttribute("pageno");
		pageno = pageno - 1;
		if (pageno <= 0) {
			pageno = 1;
		}
		session.setAttribute("pageno", pageno);
		List<AddPatient1> l1 = null;
		int i = 0;
		ModelAndView m1 = new ModelAndView("patientdetails");
		m1.addObject("abc", l1 = patientservice12045.getpatients(pageno));
		for (AddPatient1 addPatient1 : l1) {
			i += 1;
		}
		m1.addObject("patints", i);
		return m1;
	}

	@Autowired
	private InPatientService patientservice1;

	@RequestMapping("/inpatient")
	public ModelAndView add(@ModelAttribute("abcd1234") InpatientPojo abcd1234,
			HttpServletRequest http, HttpServletResponse response) {

		List<InpatientPojo> l1 = patientservice1.getinpatients();
		int i = 0;
		for (InpatientPojo inpatientPojo : l1) {

			i += 1;

		}
		ModelAndView m1 = new ModelAndView("inpatientdetails");
		m1.addObject("abc", l1);
		m1.addObject("numbers", i);
		return m1;
	}

	@RequestMapping("/serrchpatient")
	public ModelAndView serrchpatient(
			@ModelAttribute("abcd1234") InpatientPojo abcd1234,
			HttpServletRequest http, HttpServletResponse response)
			throws SQLException, IOException {
		try {
			String username = http.getParameter("patientmrno");
			String regno = http.getParameter("patientname");

			System.out.println(username);
			System.out.println(regno);

			ModelAndView mv = new ModelAndView("inpatientdetails");
			List<InpatientPojo> l1 = null;

			int i = 0;
			if (username != "" && regno != "") {
				System.out.println("bhai");
				mv.addObject("abc",
						l1 = patientservice1.getpatientbyreno(username));

			} else if (username != "") {
				System.out.println("hai");
				mv.addObject("abc",
						l1 = patientservice1.getpatientbyreno(username));
			} else if (regno != "") {
				mv.addObject("abc", l1 = patientservice1.getbynamebyname(regno));

			}

			for (InpatientPojo inpatientPojo : l1) {

				i += 1;

			}
			mv.addObject("numbers", i);
			return mv;
		} catch (Exception e) {
			System.out.println(e);

			List<InpatientPojo> l1 = patientservice1.getinpatients();
			int i = 0;
			for (InpatientPojo inpatientPojo : l1) {

				i += 1;

			}
			Map<String, Object> model = new HashMap<String, Object>();
			ArrayList<String> msg = new ArrayList<String>();
			msg.add("Please Select Atleast One");
			model.put("msg", msg);
			ModelAndView m1 = new ModelAndView("inpatientdetails", model);
			m1.addObject("abc", l1);
			m1.addObject("numbers", i);

			return m1;
		}
	}

	@RequestMapping("/inpatient1")
	public ModelAndView addasd(@ModelAttribute("patient") AddPatient1 document) {

		List<InpatientPojo> l1 = patientservice1.getinpatients();
		int i = 0;
		for (InpatientPojo inpatientPojo : l1) {

			i += 1;

		}
		ModelAndView m1 = new ModelAndView("outpatients");
		m1.addObject("abc", l1);
		m1.addObject("numbers", i);
		return m1;
	}

	@RequestMapping("/serrchpatient1")
	public ModelAndView serrchpatientf(HttpServletRequest http,
			HttpServletResponse response) throws SQLException, IOException {
		String username = http.getParameter("mrno");
		String regno = http.getParameter("pname");

		System.out.println(username);
		System.out.println(regno);

		ModelAndView mv = new ModelAndView("outpatients");
		List<InpatientPojo> l1 = null;

		int i = 0;
		if (username != "" && regno != "") {
			System.out.println("bhai");
			mv.addObject("abc", l1 = patientservice1.getpatientbyreno(username));

		} else if (username != "") {
			System.out.println("hai");
			mv.addObject("abc", l1 = patientservice1.getpatientbyreno(username));
		} else if (regno != "") {
			mv.addObject("abc", l1 = patientservice1.getbynamebyname(regno));

		}

		for (InpatientPojo inpatientPojo : l1) {

			i += 1;

		}
		mv.addObject("numbers", i);
		return mv;
	}

	@Autowired
	private OutsideService outsideservice1;

	@RequestMapping("/outconsult")
	public ModelAndView outconsultadd() {

		ArrayList<String> na = new ArrayList();
		List<OutsideConultPojo> l1 = outsideservice1.getOutsideConsut();
		int i = 0;
		for (OutsideConultPojo outsideConultPojo : l1) {

			na.add(outsideConultPojo.getConsultantName());

		}

		String[] stockArr = new String[na.size()];
		stockArr = na.toArray(stockArr);
		ModelAndView m1 = new ModelAndView("OutSideConsul1");
		m1.addObject("abc", l1);
		m1.addObject("outconsulnames", stockArr);
		m1.addObject("consulpojo11", new OutsideConultPojo());
		for (OutsideConultPojo addPatient1 : l1) {
			i += 1;
		}
		m1.addObject("patints", i);
		return m1;
	}

	@RequestMapping("/outSideConsuAdd")
	public ModelAndView outconsult() {

		String[] stockArr45 = new String[] { "  regional  ", "  consult  ",
				"Others" };

		ModelAndView m1 = new ModelAndView("OutSideConsulAdd");
		m1.addObject("outconsult", new OutsideConultPojo());
		m1.addObject("type", stockArr45);

		return m1;

	}

	@RequestMapping("/saveconsult")
	public String saveOutconsult(
			@ModelAttribute("outconsult") OutsideConultPojo document1,
			HttpServletRequest req, HttpSession hht) {

		String[] stockArr45 = new String[] { "  regional  ", "  consult  ",
				"Others" };

		outsideservice1.saveOutconsultname(document1);

		List<OutsideConultPojo> l1 = null;
		int i = 0;
		ModelAndView m1 = new ModelAndView("OutsideConsulsuccess");
		m1.addObject("abc", l1 = outsideservice1.getOutsideConsut());
		for (OutsideConultPojo outsideConultPojo : l1) {
			i += 1;
		}

		m1.addObject("patints", i);

		return "redirect:finalPage.html";

	}

	@RequestMapping("/finalPage")
	public ModelAndView saveOutconsulttrh(
			@ModelAttribute("outconsult") OutsideConultPojo document1,
			HttpServletRequest req, HttpSession hht) {

		List<OutsideConultPojo> l1 = null;
		int i = 0;
		ModelAndView m1 = new ModelAndView("OutsideConsulsuccess");
		m1.addObject("abc", l1 = outsideservice1.getOutsideConsut());
		for (OutsideConultPojo outsideConultPojo : l1) {
			i += 1;
		}

		m1.addObject("patints", i);

		return m1;

	}

	@RequestMapping("/editoutsideconult")
	public ModelAndView EDITOutconsult(
			@ModelAttribute("outconsult") OutsideConultPojo document1,
			HttpServletRequest req, HttpSession hht) {

		String id = req.getParameter("id");
		int id1 = Integer.parseInt(id);
		String s1 = "";
		int i = 0;

		List<OutsideConultPojo> l1 = outsideservice1.editConsutdetails(id1);
		for (OutsideConultPojo outsideConultPojo : l1) {
			System.out.println(outsideConultPojo.getConsultantFee());
			System.out.println(outsideConultPojo.getSpecialization());
			s1 = outsideConultPojo.getAddress();

			i += 1;

		}
		String[] stockArr45 = new String[] { "  regional  ", "  consult  ",
				"Others" };
		ModelAndView m1 = new ModelAndView("OutSideConsulEdit");
		m1.addObject("abc", l1);
		m1.addObject("type", stockArr45);
		m1.addObject("address", s1);

		return m1;

	}

	@RequestMapping("/updateconsult")
	public ModelAndView updateOutconsult(
			@ModelAttribute("outconsult") OutsideConultPojo document1,
			HttpServletRequest req, HttpSession hht) {

		String id = req.getParameter("id");
		String add = req.getParameter("address");
		document1.setAddress(add);
		System.out.println(add);
		int id1 = Integer.parseInt(id);
		document1.setId(id1);
		int j;

		j = outsideservice1.updateOutConsult(document1);

		List<OutsideConultPojo> l1 = null;
		int i = 0;
		System.out.println(document1.getAddress());
		ModelAndView m1 = new ModelAndView("OutsideConsulupdatesuces");
		m1.addObject("abc", l1 = outsideservice1.getOutsideConsut());

		for (OutsideConultPojo outsideConultPojo : l1) {
			i += 1;
		}

		m1.addObject("patints", i);
		return m1;

	}

	@RequestMapping("/deleteoustsideconsu")
	public ModelAndView deleteOutconsult(
			@ModelAttribute("outconsult") OutsideConultPojo document1,
			HttpServletRequest req, HttpSession hht) {

		String id = req.getParameter("id");
		int id1 = Integer.parseInt(id);
		document1.setId(id1);
		outsideservice1.deleteOutConsult(id1);
		List<OutsideConultPojo> l1 = null;
		int i = 0;
		System.out.println(document1.getAddress());
		ModelAndView m1 = new ModelAndView("OutsideConsuldeleted");
		m1.addObject("abc", l1 = outsideservice1.getOutsideConsut());

		for (OutsideConultPojo outsideConultPojo : l1) {
			i += 1;
		}

		m1.addObject("patints", i);
		return m1;

	}

	@RequestMapping("/serarchOutConsult")
	public ModelAndView searchOutconsult(
			@ModelAttribute("outconsult") OutsideConultPojo document1,
			HttpServletRequest req, HttpSession hht) {

		String name = req.getParameter("namecon");
		int i = 0;
		List<OutsideConultPojo> l1 = outsideservice1.getconsultentbyname(name);
		for (OutsideConultPojo outsideConultPojo : l1) {
			i += 1;
		}

		System.out.println(document1.getAddress());
		ModelAndView m1 = new ModelAndView("OutSideConsul1");
		m1.addObject("abc", l1);
		m1.addObject("patints", i);

		return m1;

	}

	@Autowired
	private MLCService patientservicemlc;

	@RequestMapping("/mlc")
	public ModelAndView addmlc(@ModelAttribute("mlcpatient") Mlcpojo document1) {

		List<Mlcpojo> l1 = null;
		int i = 0;
		ModelAndView m1 = new ModelAndView("MLC1");
		m1.addObject("abc", l1 = patientservicemlc.getpatients());
		for (Mlcpojo mlcpojo : l1) {

			i += 1;
		}
		m1.addObject("mlc", i);

		return m1;
	}

	@RequestMapping("/addmlc")
	public ModelAndView addmlcpati() {

		Date date = new Date();
		// Specify the desired date format
		String DATE_FORMAT = "MM/dd/yyyy";
		// Create object of SimpleDateFormat and pass the desired date format.
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		/*
		 * Use format method of SimpleDateFormat class to format the date.
		 */

		Map<String, Object> model = new HashMap<String, Object>();
		System.out.println("Today is " + sdf.format(date));
		ArrayList<String> state = new ArrayList<String>();
		state.add("Andhra Pradesh");
		state.add(" Arunachal Prades");
		state.add("Assam");
		state.add(" Bihar");
		state.add("Chhattisgarh");
		state.add("Goa");
		state.add("Gujarat");
		state.add("Haryana");
		state.add("Himachal Pradesh");
		state.add("Jammu & Kashmir");
		state.add("Jharkhand");
		state.add("Karnataka");
		state.add("Kerala");
		state.add("Madhya Pradesh");
		state.add("Maharashtra");
		state.add("Manipur");
		state.add("Meghalaya");
		state.add("Mizoram");
		state.add("Nagaland");
		state.add("Odisha");
		state.add("Punjab");
		state.add("Rajasthan");
		state.add("Sikkim");
		state.add("Tamil Nadu");
		state.add("Telangana");
		state.add("Tripura");
		state.add("Uttarakhand");
		state.add("Uttar Pradesh");
		state.add("West Bengal");
		state.add("Delhi");
		state.add("Andaman & Nicobar Island");
		state.add("Chandigarh");
		state.add("Dadra & Nagar Haveli");
		state.add("Daman & Diu");
		state.add("Lakshadweep");
		state.add("Puducherry");
		model.put("state", state);
		String s1 = patientservicemlc.getmlcpatientsregno();
		String[] stockArr45 = new String[] { "  Male  ", "  Female  " };
		ModelAndView m1 = new ModelAndView("MLCadd2", model);
		m1.addObject("mlcpatient", new Mlcpojo());
		m1.addObject("radiobutt", stockArr45);
		m1.addObject("time", sdf.format(date));
		m1.addObject("mlcregno", s1);

		return m1;
	}

	@RequestMapping("/savemlc")
	public ModelAndView savemlc(@ModelAttribute("mlcpatient") Mlcpojo document,
			@RequestParam("file") MultipartFile file,
			@RequestParam("file1") MultipartFile file1,
			@RequestParam("file2") MultipartFile file2,
			@RequestParam("file3") MultipartFile file3) throws IOException,
			ServletException {

		System.out.println("Saving file: ");

		/* try { */

		Blob blob = Hibernate.createBlob(file.getInputStream());
		Blob blob1 = Hibernate.createBlob(file1.getInputStream());
		Blob blob2 = Hibernate.createBlob(file2.getInputStream());
		Blob blob3 = Hibernate.createBlob(file3.getInputStream());

		/*
		 * byte[] imgData = file.getBytes(); byte[] imgData1 = file1.getBytes();
		 * byte[] imgData2 = file2.getBytes(); byte[] imgData3 =
		 * file3.getBytes();
		 */

		String pname = file.getOriginalFilename();
		String xname = file1.getOriginalFilename();
		String sname = file2.getOriginalFilename();
		String oname = file3.getOriginalFilename();

		document.setPatientPhoto(blob);
		document.setxRayreports(blob1);
		document.setScanReports(blob2);
		document.setOtherReports(blob3);
		document.setPatientPhotoName(pname);
		document.setxRayreportsName(xname);
		document.setScanReportsName(sname);
		document.setOtherReportsName(oname);

		/*
		 * } catch (IOException e) { e.printStackTrace(); }
		 */
		try {
			patientservicemlc.addmlcpatient(document);

			ModelAndView m1 = new ModelAndView("successfrommlc");
			m1.addObject("abc", patientservicemlc.getpatients());
			return new ModelAndView("redirect:/mlcfinal.html");
		} catch (Exception e) {
			System.out.println(e);
			Date date = new Date();
			// Specify the desired date format
			String DATE_FORMAT = "MM/dd/yyyy";
			// Create object of SimpleDateFormat and pass the desired date
			// format.
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
			/*
			 * Use format method of SimpleDateFormat class to format the date.
			 */
			System.out.println("Today is " + sdf.format(date));

			String s1 = patientservicemlc.getmlcpatientsregno();
			String[] stockArr45 = new String[] { "  Male  ", "  Female  " };

			Map<String, Object> model = new HashMap<String, Object>();
			ArrayList<String> msg = new ArrayList<String>();
			ArrayList<String> state = new ArrayList<String>();
			state.add("Andhra Pradesh");
			state.add(" Arunachal Prades");
			state.add("Assam");
			state.add(" Bihar");
			state.add("Chhattisgarh");
			state.add("Goa");
			state.add("Gujarat");
			state.add("Haryana");
			state.add("Himachal Pradesh");
			state.add("Jammu & Kashmir");
			state.add("Jharkhand");
			state.add("Karnataka");
			state.add("Kerala");
			state.add("Madhya Pradesh");
			state.add("Maharashtra");
			state.add("Manipur");
			state.add("Meghalaya");
			state.add("Mizoram");
			state.add("Nagaland");
			state.add("Odisha");
			state.add("Punjab");
			state.add("Rajasthan");
			state.add("Sikkim");
			state.add("Tamil Nadu");
			state.add("Telangana");
			state.add("Tripura");
			state.add("Uttarakhand");
			state.add("Uttar Pradesh");
			state.add("West Bengal");
			state.add("Delhi");
			state.add("Andaman & Nicobar Island");
			state.add("Chandigarh");
			state.add("Dadra & Nagar Haveli");
			state.add("Daman & Diu");
			state.add("Lakshadweep");
			state.add("Puducherry");
			model.put("state", state);
			msg.add("Browse Copies size is to Large can you please Reduce the Size...");
			model.put("msg", msg);

			ModelAndView m1 = new ModelAndView("MLCadd2", model);
			m1.addObject("mlcpatient", new Mlcpojo());
			m1.addObject("radiobutt", stockArr45);
			m1.addObject("time", sdf.format(date));
			m1.addObject("mlcregno", s1);

			return m1;
		}
	}

	@RequestMapping("/mlcfinal")
	public ModelAndView addmlcpatisdvsdv() {

		ModelAndView m1 = new ModelAndView("successfrommlc");
		m1.addObject("abc", patientservicemlc.getpatients());

		return m1;
	}

	@RequestMapping("/searchpa")
	public ModelAndView search(HttpServletRequest http,
			HttpServletResponse response) throws SQLException, IOException {
		String username = http.getParameter("name");
		String regno = http.getParameter("regno");
		String date = http.getParameter("date");

		List<Mlcpojo> l1 = null;
		int i = 0;
		ModelAndView mv = new ModelAndView("MLC1");

		if (username != "" && regno != "") {

			mv.addObject("abc", l1 = patientservicemlc.getpatient(regno));
			for (Mlcpojo mlcpojo : l1) {
				i += 1;
			}

		} else if (regno != "" && date != "") {
			System.out.println("from");
			mv.addObject("abc", l1 = patientservicemlc.getpatient(regno));
			for (Mlcpojo mlcpojo : l1) {
				i += 1;
			}
		} else if (regno != "") {
			System.out.println("hai");
			mv.addObject("abc", l1 = patientservicemlc.getpatient(regno));
			for (Mlcpojo mlcpojo : l1) {
				i += 1;
			}
		} else if (username != "" && regno != "" && date != "") {
			System.out.println("3");
			mv.addObject("abc", l1 = patientservicemlc.getpatient(regno));
			for (Mlcpojo mlcpojo : l1) {
				i += 1;
			}
		} else if (username != "") {
			mv.addObject("abc", l1 = patientservicemlc.getbyname(username));
			for (Mlcpojo mlcpojo : l1) {
				i += 1;
			}

		} else if (date != "") {
			mv.addObject("abc", l1 = patientservicemlc.getbydate(date));
			for (Mlcpojo mlcpojo : l1) {
				i += 1;
			}

		} else {
			mv.addObject("abc", l1 = patientservicemlc.getpatient(regno));
			for (Mlcpojo mlcpojo : l1) {
				i += 1;
			}
		}
		mv.addObject("mlc", i);
		return mv;
	}

	@RequestMapping("/editmlcpatient")
	public ModelAndView EDITmlcpatient(HttpServletRequest req,
			HttpSession sessss) {

		String id = req.getParameter("id");

		int id1 = Integer.parseInt(id);
		sessss.setAttribute("mlcid", id1);

		List<Mlcpojo> l1 = patientservicemlc.editMLCpatient(id1);
		String mf = null;
		for (Mlcpojo mlcpojo : l1) {
			mf = mlcpojo.getSex();

		}
		String[] stockArr45 = new String[] { " Male  ", "  Female  " };

		Map<String, Object> model = new HashMap<String, Object>();
		ArrayList<String> state = new ArrayList<String>();
		state.add("Andhra Pradesh");
		state.add(" Arunachal Prades");
		state.add("Assam");
		state.add(" Bihar");
		state.add("Chhattisgarh");
		state.add("Goa");
		state.add("Gujarat");
		state.add("Haryana");
		state.add("Himachal Pradesh");
		state.add("Jammu & Kashmir");
		state.add("Jharkhand");
		state.add("Karnataka");
		state.add("Kerala");
		state.add("Madhya Pradesh");
		state.add("Maharashtra");
		state.add("Manipur");
		state.add("Meghalaya");
		state.add("Mizoram");
		state.add("Nagaland");
		state.add("Odisha");
		state.add("Punjab");
		state.add("Rajasthan");
		state.add("Sikkim");
		state.add("Tamil Nadu");
		state.add("Telangana");
		state.add("Tripura");
		state.add("Uttarakhand");
		state.add("Uttar Pradesh");
		state.add("West Bengal");
		state.add("Delhi");
		state.add("Andaman & Nicobar Island");
		state.add("Chandigarh");
		state.add("Dadra & Nagar Haveli");
		state.add("Daman & Diu");
		state.add("Lakshadweep");
		state.add("Puducherry");
		model.put("state", state);
		ModelAndView m1 = new ModelAndView("MLCEdit2121", model);
		m1.addObject("abc", l1);
		m1.addObject("radiobutt", stockArr45);
		m1.addObject("sex", mf);
		m1.addObject("updatemlcpatient", new Mlcpojo());

		return m1;

	}

	@RequestMapping("/updatemlcpatient121")
	public ModelAndView updatemlc(
			@ModelAttribute("updatemlcpatient") Mlcpojo document,
			@RequestParam("file") MultipartFile file,
			@RequestParam("file1") MultipartFile file1,
			@RequestParam("file2") MultipartFile file2,
			@RequestParam("file3") MultipartFile file3, HttpServletRequest req,
			HttpSession sessss) throws IOException, ServletException {

		String id = req.getParameter("id");

		int id1 = Integer.parseInt(id);
		document.setId(id1);

		document.setRemarks(req.getParameter("remarksmlc"));
		document.setIdentification1(req.getParameter("identifiaction1"));
		document.setIdentification2(req.getParameter("identifiaction2"));

		try {

			Blob blob = Hibernate.createBlob(file.getInputStream());
			Blob blob1 = Hibernate.createBlob(file1.getInputStream());
			Blob blob2 = Hibernate.createBlob(file2.getInputStream());
			Blob blob3 = Hibernate.createBlob(file3.getInputStream());

			/*
			 * byte[] imgData = file.getBytes(); byte[] imgData1 =
			 * file1.getBytes(); byte[] imgData2 = file2.getBytes(); byte[]
			 * imgData3 = file3.getBytes();
			 */

			String pname = file.getOriginalFilename();
			String xname = file1.getOriginalFilename();
			String sname = file2.getOriginalFilename();
			String oname = file3.getOriginalFilename();

			document.setPatientPhoto(blob);
			document.setxRayreports(blob1);
			document.setScanReports(blob2);
			document.setOtherReports(blob3);
			document.setPatientPhotoName(pname);
			document.setxRayreportsName(xname);
			document.setScanReportsName(sname);
			document.setOtherReportsName(oname);

		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			patientservicemlc.updatemlcpatient(document);
		} catch (Exception e) {
			e.printStackTrace();

		}
		ModelAndView m1 = new ModelAndView("updatesuccess");
		m1.addObject("abc", patientservicemlc.getpatients());
		return m1;
	}

	@Autowired
	private OutpatientService outpatientService;

	@RequestMapping("/outpatient111")
	public ModelAndView addsdvs(@ModelAttribute("patient") AddPatient1 document) {

		List<AddPatient1> l1 = null;
		int i = 0;

		ModelAndView m1 = new ModelAndView("outpatientsfromaddpatint");
		m1.addObject("abc", l1 = outpatientService.getOutPatientDetails());
		for (AddPatient1 inpatientPojo : l1) {

			i += 1;

		}
		m1.addObject("numbers", i);
		return m1;
	}

	@RequestMapping("/serrchpatient1fromadddpaient")
	public ModelAndView serrchpatiensegt(HttpServletRequest http,
			HttpServletResponse response) throws SQLException, IOException {
		String username = http.getParameter("mrno");
		String regno = http.getParameter("pname");

		System.out.println(username);
		System.out.println(regno);

		ModelAndView mv = new ModelAndView("outpatientsfromaddpatint");
		List<AddPatient1> l1 = null;

		int i = 0;
		if (username != "" && regno != "") {
			System.out.println("bhai");
			mv.addObject("abc",
					l1 = outpatientService.getpatientbyreno(username));

		} else if (username != "") {
			System.out.println("hai");
			mv.addObject("abc",
					l1 = outpatientService.getpatientbyreno(username));
		} else if (regno != "") {
			System.out.println("SG");
			mv.addObject("abc", l1 = outpatientService.getbynamebyname(regno));

		}

		for (AddPatient1 inpatientPojo : l1) {

			i += 1;

		}
		mv.addObject("numbers", i);
		return mv;
	}

	/* MENA */

	@Autowired
	private serviceinterface si;

	@RequestMapping("/inpatientAddmissionDetails")
	public ModelAndView form(@ModelAttribute("inp") entry inp,
			BindingResult result) {

		System.out.println("details page");
		ModelAndView m1 = new ModelAndView("InPatientAd1");
		List<entry> l1 = null;
		int i = 0;
		// for getting all details
		m1.addObject("abc", l1 = si.getinpt());
		for (entry entry : l1) {

			i += 1;
		}
		m1.addObject("id", i);
		return m1;

	}

	@RequestMapping("/pageinpatient")
	public ModelAndView page(@ModelAttribute("inp") entry inp,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		System.out.println("pagesdfgsdg");
		ArrayList<String> al2 = new ArrayList<>();
		List<Patientdetail> l2 = si.getmrno();
		Iterator<Patientdetail> i2 = l2.iterator();
		while (i2.hasNext()) {
			Patientdetail pn2 = (Patientdetail) i2.next();
			al2.add(pn2.getPatientmrno());
		}
		String[] stock2 = new String[al2.size()];
		stock2 = al2.toArray(stock2);

		ArrayList<String> al3 = new ArrayList<>();
		List<Patientdetail> l3 = si.getnames();
		Iterator<Patientdetail> i3 = l3.iterator();
		while (i3.hasNext()) {
			Patientdetail pn3 = (Patientdetail) i3.next();
			al3.add(pn3.getPatientname());
		}
		String[] stock3 = new String[al3.size()];
		stock3 = al3.toArray(stock3);

		ArrayList<String> al4 = new ArrayList<>();
		List<Patientdetail> l4 = si.getdate();
		Iterator<Patientdetail> i4 = l4.iterator();
		while (i4.hasNext()) {
			Patientdetail pn4 = (Patientdetail) i4.next();
			al4.add(pn4.getRegdate());
		}
		String[] stock4 = new String[al4.size()];
		stock4 = al4.toArray(stock4);

		ArrayList<String> na = new ArrayList();
		List<Doctordetail> l1 = si.getdoctors();
		Iterator<Doctordetail> i1 = l1.iterator();
		while (i1.hasNext()) {
			Doctordetail pn = (Doctordetail) i1.next();

			na.add(pn.getDoctorname());

		}
		String[] stockArr = new String[na.size()];
		stockArr = na.toArray(stockArr);

		/*
		 * ArrayList<String> al5 = new ArrayList(); List<Beddata> l5 =
		 * si.getbedno(); Iterator<Beddata> i5 = l5.iterator(); while
		 * (i5.hasNext()) { Beddata pn5 = (Beddata) i5.next();
		 * 
		 * al5.add(pn5.getBedno());
		 * 
		 * } String[] stock5 = new String[al5.size()]; stock5 =
		 * al5.toArray(stock5);
		 */

		ArrayList<String> al6 = new ArrayList();
		List<Beddata> l6 = si.getroomno();
		Iterator<Beddata> i6 = l6.iterator();
		while (i6.hasNext()) {
			Beddata pn6 = (Beddata) i6.next();

			al6.add(pn6.getRoomno());

		}
		String[] stock6 = new String[al6.size()];
		stock6 = al6.toArray(stock6);

		ArrayList<String> al7 = new ArrayList();
		List<Beddata> l7 = si.getroomrent();

		Iterator<Beddata> i7 = l7.iterator();
		while (i7.hasNext()) {
			Beddata pn7 = (Beddata) i7.next();

			al7.add(pn7.getRoomrent());

		}
		String[] stock7 = new String[al7.size()];
		stock7 = al7.toArray(stock7);

		/*
		 * ArrayList<String> al8 = new ArrayList(); List<Patientdetail> l8 =
		 * si.getallotedby(); Iterator<Patientdetail> i8 = l8.iterator();
		 * 
		 * while (i8.hasNext()) { Patientdetail pn8 = (Patientdetail) i8.next();
		 * 
		 * al8.add(pn8.getAllotedby());
		 * 
		 * }
		 * 
		 * String[] stock8 = new String[al8.size()]; stock8 =
		 * al8.toArray(stock8);
		 */
		System.out.println("page");
		String[] s1 = new String[] { "yes", "no" };
		String[] s2 = new String[] { "Cash", "Bank" };
		String[] s3 = new String[] { "Cash", "Bank" };
		ModelAndView m1 = new ModelAndView("InPatientAdmAdd2");
		m1.addObject("diet1", s1);
		m1.addObject("mode", s2);
		m1.addObject("mode2", s3);
		m1.addObject("doct", stockArr);
		m1.addObject("mrno", stock2);
		m1.addObject("names", stock3);
		m1.addObject("date", stock4);
		/* m1.addObject("bedno", stock5); */
		m1.addObject("roomno", stock6);
		m1.addObject("roomrent1", stock7);
		/*
		 * m1.addObject("allotedby", stock8);
		 */
		m1.addObject("inp", new entry());
		System.out.println("sdgvdfgb");
		return m1;
	}

	@RequestMapping(value = "/display", params = { "adda" })
	public String gr(@ModelAttribute("inp") entry inp, BindingResult result,
			@ModelAttribute("inp1") Patientdetail inp1, HttpSession ses) {

		List<entry> l1 = null;
		int i = 0;
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("inp", si.getuser());

		/*
		 * String patientname = (String) ses.getAttribute("patientname");
		 * inp.setPatientname(patientname); String patientData = (String)
		 * ses.getAttribute("regdate"); inp.setRegdate(patientData); String
		 * roomn1 = (String) ses.getAttribute("rooms"); inp.setRoomno(roomn1);
		 * String roomr1 = (String) ses.getAttribute("roomrents");
		 * inp.setRoomno(roomr1); inp.setRoomrent(roomr1);
		 */

		si.adddetail(inp);

		ModelAndView m2 = new ModelAndView("InPatientAd1");
		m2.addObject("abc", l1 = si.getinpt());
		for (entry entry : l1) {

			i += 1;
		}
		m2.addObject("id", i);

		return "redirect:/doctorfinalPage.html";

	}

	@RequestMapping(value = "/doctorfinalPage")
	public ModelAndView grsdfgsdrgsdr(@ModelAttribute("inp") entry inp,
			BindingResult result, @ModelAttribute("inp1") Patientdetail inp1) {

		List<entry> l1 = null;
		int i = 0;
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("inp", si.getuser());

		ModelAndView m2 = new ModelAndView("InPatientAd1");
		m2.addObject("abc", l1 = si.getinpt());
		for (entry entry : l1) {

			i += 1;
		}
		m2.addObject("id", i);

		return m2;

	}

	@RequestMapping("/updatedinpatient1234")
	public ModelAndView updatepatient12(@ModelAttribute("inp") entry inp,
			BindingResult result, HttpServletRequest req, HttpSession sess) {

		System.out.println(inp.getId());

		System.out.println("sdsdgvsdgvsdgsdsdsdfdc");
		ArrayList<String> al2 = new ArrayList<>();
		List<Patientdetail> l2 = si.getmrno();
		Iterator<Patientdetail> i2 = l2.iterator();
		while (i2.hasNext()) {
			Patientdetail pn2 = (Patientdetail) i2.next();
			al2.add(pn2.getPatientmrno());
		}
		String[] stock2 = new String[al2.size()];
		stock2 = al2.toArray(stock2);

		ArrayList<String> al3 = new ArrayList<>();
		List<Patientdetail> l3 = si.getnames();
		Iterator<Patientdetail> i3 = l3.iterator();
		while (i3.hasNext()) {
			Patientdetail pn3 = (Patientdetail) i3.next();
			al3.add(pn3.getPatientname());
		}
		String[] stock3 = new String[al3.size()];
		stock3 = al3.toArray(stock3);

		ArrayList<String> al4 = new ArrayList<>();
		List<Patientdetail> l4 = si.getdate();
		Iterator<Patientdetail> i4 = l4.iterator();
		while (i4.hasNext()) {
			Patientdetail pn4 = (Patientdetail) i4.next();
			al4.add(pn4.getRegdate());
		}
		String[] stock4 = new String[al4.size()];
		stock4 = al4.toArray(stock4);

		ArrayList<String> na = new ArrayList();
		List<Doctordetail> l1 = doctorservice.getdoctors();
		Iterator<Doctordetail> i1 = l1.iterator();
		while (i1.hasNext()) {
			Doctordetail pn = (Doctordetail) i1.next();

			na.add(pn.getDoctorname());

		}
		String[] stockArr = new String[na.size()];
		stockArr = na.toArray(stockArr);

		ArrayList<String> al6 = new ArrayList();
		List<Beddata> l6 = si.getroomno();
		Iterator<Beddata> i6 = l6.iterator();
		while (i6.hasNext()) {
			Beddata pn6 = (Beddata) i6.next();

			al6.add(pn6.getRoomno());

		}
		String[] stock6 = new String[al6.size()];
		stock6 = al6.toArray(stock6);

		ArrayList<String> al7 = new ArrayList();
		List<Beddata> l7 = si.getroomrent();
		Iterator<Beddata> i7 = l7.iterator();
		while (i7.hasNext()) {
			Beddata pn7 = (Beddata) i7.next();

			al7.add(pn7.getRoomrent());

		}
		String[] stock7 = new String[al7.size()];
		stock7 = al7.toArray(stock7);

		ArrayList<String> al8 = new ArrayList();
		List<Patientdetail> l8 = si.getallotedby();
		Iterator<Patientdetail> i8 = l8.iterator();
		while (i8.hasNext()) {
			Patientdetail pn8 = (Patientdetail) i8.next();

			al8.add(pn8.getAllotedby());

		}
		String[] stock8 = new String[al8.size()];
		stock8 = al8.toArray(stock8);

		String mrno = inp.getPatientmrno();
		String patientname = "";
		String patientData = "";
		System.out.println("yhtf" + inp.getPatientmrno());
		System.out.println("page");

		List<Patientdetail> patient = si
				.getDetailspatient(inp.getPatientmrno());
		System.out.println(patient);
		System.out.println(patient);
		for (Patientdetail patientdetail : patient) {

			patientname = patientdetail.getPatientname();
			patientData = patientdetail.getRegdate();
			/* sess.setAttribute("patientname", patientname); */
			System.out.println(mrno + " " + patientname + " " + patientData);

		}

		String bed = inp.getBedno();
		/*
		 * String roomn1 = ""; String roomr1 = ""; List<Beddata> pat =
		 * si.getDetailspatientbed(inp.getBedno()); for (Beddata patientdetail :
		 * pat) { roomn1 = patientdetail.getRoomno(); roomr1 =
		 * patientdetail.getRoomrent(); System.out.println(bed + " " + roomn1 +
		 * " " + roomr1); }
		 */

		Map<String, Object> model1 = new HashMap<String, Object>();

		ArrayList<String> names = new ArrayList<String>();
		names.add(si.getRoomNumber(inp.getBedno()));

		model1.put("updatedDetails", si.getDetails(inp.getId()));

		String roomNo = "null";

		for (String s : names) {
			roomNo = s;
		}
		ArrayList<Double> roomAmount = new ArrayList<Double>();
		roomAmount.add(si.getRoomNumberAmount(roomNo));
		model1.put("roomNumber", names);
		model1.put("roomAmount", roomAmount);

		ArrayList<String[]> bedNos = new ArrayList<String[]>();
		bedNos.add(si.getBedNos());
		ArrayList<String> bedNos1 = new ArrayList<String>();
		for (String[] s : bedNos) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				bedNos1.add(s[j]);
				System.out.println(j + " " + s[j]);
			}
		}

		model1.put("bedNos", bedNos1);

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date datezsdv = new Date();
		String datrrgdd = dateFormat.format(datezsdv);

		String[] s1 = new String[] { "yes", "no" };
		String[] s2 = new String[] { "Cash", "Bank" };
		String[] s3 = new String[] { "Cash", "Bank" };

		ModelAndView m1 = new ModelAndView("InPatientupdate");

		m1.addObject("diet1", s1);
		m1.addObject("mode", s2);
		m1.addObject("mode2", s3);
		m1.addObject("doct", stockArr);
		m1.addObject("mrno", stock2);
		m1.addObject("names", stock3);
		m1.addObject("date", stock4);
		/* m1.addObject("bedno", stock5); */
		m1.addObject("roomno", stock6);
		m1.addObject("roomrent1", stock7);
		m1.addObject("allotedby", stock8);
		m1.addObject("mrno111", mrno);

		sess.setAttribute("patientname", patientname);
		sess.setAttribute("regdate", patientData);
		m1.addObject("regdate", patientData);

		m1.addObject("beds", bed);

		m1.addObject("time", datrrgdd);

		m1.addObject("inp", new entry());
		m1.addObject("model1", model1);

		return m1;

	}

	@RequestMapping("/search")
	public ModelAndView search1(HttpServletRequest request,
			HttpServletRequest response, @ModelAttribute("inp") entry inp,
			BindingResult result) {
		String patientmrno = request.getParameter("pmrnum");
		String patientname = request.getParameter("pname");

		if (patientmrno.equals("") && patientname.equals("")) {

			Map<String, Object> model = new HashMap<String, Object>();
			ArrayList<String> updateMsg = new ArrayList<String>();
			updateMsg.add("Please Select Atleast one...");
			model.put("mag", updateMsg);

			System.out.println("details page");
			ModelAndView m1 = new ModelAndView("InPatientAd1", model);
			List<entry> l1 = null;
			int i = 0;
			// for getting all details
			m1.addObject("abc", l1 = si.getinpt());
			for (entry entry : l1) {

				i += 1;
			}
			m1.addObject("id", i);
			return m1;

		} else {

			System.out.println(patientmrno);
			System.out.println(patientname);
			ModelAndView mv = new ModelAndView("InPatientAd1");
			int i = 0;

			if (patientname != "" && patientmrno != "") {

				System.out.println("if");
				List<entry> l2 = si.patientmrno1(patientmrno);

				for (entry entry : l2) {

					i += 1;
				}
				mv.addObject("id", i);
				mv.addObject("abc", si.patientmrno1(patientmrno));
			}

			else if (patientmrno != "") {
				System.out.println("elseif");
				List<entry> l2 = si.patientmrno1(patientmrno);

				for (entry entry : l2) {
					i += 1;
				}
				mv.addObject("id", i);
				mv.addObject("abc", si.patientmrno1(patientmrno));
			} else if (patientname != "") {
				System.out.println("else");
				List<entry> l2 = si.patientservice(patientname);

				for (entry entry : l2) {

					i += 1;
				}
				mv.addObject("id", i);
				mv.addObject("abc", si.patientservice(patientname));
			}
			/*
			 * else { List<entry> l2=si.patientmrno1(patientmrno);
			 * 
			 * for (entry entry : l2) { i+=1; } mv.addObject("id", i);
			 * mv.addObject("abc",si.patientmrno1(patientmrno));
			 * mv.addObject("abc",si.patientmrno1(patientmrno)); }
			 */

			return mv;
		}
	}

	@RequestMapping(value = "/displayDetails1234", params = { "updateDetails" })
	public ModelAndView saveUserUpdate(@ModelAttribute("inp") entry inp,
			BindingResult result, HttpSession ses) {
		/*
		 * System.out.println(inp.getPatientmrno());
		 * System.out.println(inp.getId());
		 */
		si.addUserUpdate(inp);
		/*
		 * String roomn1 = (String) ses.getAttribute("rooms");
		 * inp.setRoomno(roomn1); String roomr1 = (String)
		 * ses.getAttribute("roomrentspatient"); inp.setRoomrent(roomr1);
		 * System.out.println(inp.getPatientmrno());
		 * System.out.println(inp.getPatientname()); ModelAndView mav = new
		 * ModelAndView("InPatientAdSuccess"); // for getting all details
		 * mav.addObject("abc", si.getinpt());
		 */
		return new ModelAndView("redirect:/inpatientAddmissionDetailsMsg.html");

	}

	@RequestMapping("/inpatientAddmissionDetailsMsg")
	public ModelAndView formUpdateMsg(@ModelAttribute("inp") entry inp,
			BindingResult result) {

		System.out.println("details page");
		ModelAndView m1 = new ModelAndView("InPatientAd1");
		List<entry> l1 = null;
		int i = 0;
		// for getting all details
		m1.addObject("abc", l1 = si.getinpt());
		for (entry entry : l1) {

			i += 1;
		}
		m1.addObject("id", i);
		m1.addObject("msg", "Record Updated Successfully");
		return m1;

	}

	@RequestMapping("/updatedatainpatient")
	public ModelAndView up(@ModelAttribute("inp") entry inp,
			BindingResult result, HttpServletRequest req, HttpSession sess) {

		System.out.println(inp.getId());
		String mrno = req.getParameter("mrno");
		System.out.println(mrno);
		/* si.addUserUpdate(inp); */
		ArrayList<String> al2 = new ArrayList<>();
		List<Patientdetail> l2 = si.getmrno();
		Iterator<Patientdetail> i2 = l2.iterator();
		while (i2.hasNext()) {
			Patientdetail pn2 = (Patientdetail) i2.next();
			al2.add(pn2.getPatientmrno());
		}
		String[] stock2 = new String[al2.size()];
		stock2 = al2.toArray(stock2);
		String s6 = req.getParameter("mrno");
		List<entry> mrmdfg = si.patientmrno1(s6);
		String mrnooo = "";
		String names = "";
		String rdate = "";
		String admitdate = "";
		String consultdoct = "";
		String bedn = "";
		String roomnum = "";
		String alloted = "";
		String roomren = "";

		String dieting = "";
		String mop = "";
		String mop2 = "";
		for (entry entry : mrmdfg) {
			mrnooo = entry.getPatientmrno();
			names = entry.getPatientname();
			rdate = entry.getRegdate();
			admitdate = entry.getAdmitdateortime();
			consultdoct = entry.getDoctorname();
			alloted = entry.getAllotedby();
			bedn = entry.getBedno();
			roomnum = entry.getRoomno();
			roomren = entry.getRoomrent();
			dieting = entry.getDiet();
			mop = entry.getModeofpayment();
			mop2 = entry.getModeofpayment1();
		}

		ArrayList<String> al3 = new ArrayList<>();
		List<Patientdetail> l3 = si.getnames();
		Iterator<Patientdetail> i3 = l3.iterator();
		while (i3.hasNext()) {
			Patientdetail pn3 = (Patientdetail) i3.next();
			al3.add(pn3.getPatientname());
		}
		String[] stock3 = new String[al3.size()];
		stock3 = al3.toArray(stock3);

		ArrayList<String> al4 = new ArrayList<>();
		List<Patientdetail> l4 = si.getdate();
		Iterator<Patientdetail> i4 = l4.iterator();
		while (i4.hasNext()) {
			Patientdetail pn4 = (Patientdetail) i4.next();
			al4.add(pn4.getRegdate());
		}
		String[] stock4 = new String[al4.size()];
		stock4 = al4.toArray(stock4);

		ArrayList<String> na = new ArrayList();
		List<Doctordetail> l1 = doctorservice.getdoctors();
		Iterator<Doctordetail> i1 = l1.iterator();
		while (i1.hasNext()) {
			Doctordetail pn = (Doctordetail) i1.next();

			na.add(pn.getDoctorname());

		}
		String[] stockArr = new String[na.size()];
		stockArr = na.toArray(stockArr);

		ArrayList<String> al5 = new ArrayList();
		List<Beddata> l5 = si.getbedno();
		Iterator<Beddata> i5 = l5.iterator();
		while (i5.hasNext()) {
			Beddata pn5 = (Beddata) i5.next();

			al5.add(pn5.getBedno());
		}
		String[] stock5 = new String[al5.size()];
		stock5 = al5.toArray(stock5);

		ArrayList<String> al6 = new ArrayList();
		List<Beddata> l6 = si.getroomno();
		Iterator<Beddata> i6 = l6.iterator();
		while (i6.hasNext()) {
			Beddata pn6 = (Beddata) i6.next();

			al6.add(pn6.getRoomno());

		}
		String[] stock6 = new String[al6.size()];
		stock6 = al6.toArray(stock6);

		ArrayList<String> al7 = new ArrayList();
		List<Beddata> l7 = si.getroomrent();
		Iterator<Beddata> i7 = l7.iterator();
		while (i7.hasNext()) {
			Beddata pn7 = (Beddata) i7.next();

			al7.add(pn7.getRoomrent());

		}
		String[] stock7 = new String[al7.size()];
		stock7 = al7.toArray(stock7);

		ArrayList<String> al8 = new ArrayList();
		List<Patientdetail> l8 = si.getallotedby();
		Iterator<Patientdetail> i8 = l8.iterator();
		while (i8.hasNext()) {
			Patientdetail pn8 = (Patientdetail) i8.next();

			al8.add(pn8.getAllotedby());

		}

		String[] stock8 = new String[al8.size()];
		stock8 = al8.toArray(stock8);

		String[] s1 = new String[] { "Yes", "No" };
		String[] s2 = new String[] { "Cash", "Bank" };
		String[] s3 = new String[] { "Cash", "Bank" };
		System.out.println(s6);

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("updatedDetails", si.getDetails(inp.getId()));

		System.out.println(model);
		System.out.println(model);
		System.out.println(model);

		String bed = inp.getBedno();
		String roomn1 = "";
		String roomr1 = "";
		List<Beddata> pat = si.getDetailspatientbed(inp.getBedno());
		for (Beddata patientdetail : pat) {
			roomn1 = patientdetail.getRoomno();
			roomr1 = patientdetail.getRoomrent();
		}

		System.out.println(roomr1);

		ModelAndView m2 = new ModelAndView("InPatientupdate2");
		m2.addObject("model", model);

		m2.addObject("diet1", s1);
		m2.addObject("mrno", stock2);
		m2.addObject("doct", stockArr);
		m2.addObject("names", stock3);
		m2.addObject("date", stock4);
		m2.addObject("bedno", stock5);
		m2.addObject("roomno", stock6);
		m2.addObject("roomrent1", stock7);
		m2.addObject("allotedby", stock8);
		m2.addObject("mode", s2);
		m2.addObject("mode2", s3);

		m2.addObject("mrnopa", mrnooo);
		m2.addObject("name", names);
		m2.addObject("allot", alloted);
		m2.addObject("dietings", dieting);
		m2.addObject("modes", mop);
		m2.addObject("modes2", mop2);
		m2.addObject("rdate", rdate);
		m2.addObject("admitdateortime", admitdate);
		m2.addObject("cdoctor", consultdoct);
		m2.addObject("bednumber", bedn);
		m2.addObject("roomnumber", roomnum);
		m2.addObject("roomrents", roomren);

		m2.addObject("pojo", new entry());
		m2.addObject("beds", bed);
		/*
		 * m2.addObject("rooms", roomn1); m2.addObject("roomrents", roomr1);
		 */

		sess.setAttribute("rooms", roomn1);
		sess.setAttribute("roomrentspatient", roomr1);

		return m2;

	}

	@RequestMapping("/display")
	public ModelAndView pagsdsdfe(@ModelAttribute("inp") entry inp,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response, HttpSession sess) throws IOException

	{

		ArrayList<String> al2 = new ArrayList<>();
		List<Patientdetail> l2 = si.getmrno();
		Iterator<Patientdetail> i2 = l2.iterator();
		while (i2.hasNext()) {
			Patientdetail pn2 = (Patientdetail) i2.next();
			al2.add(pn2.getPatientmrno());
		}
		String[] stock2 = new String[al2.size()];
		stock2 = al2.toArray(stock2);

		ArrayList<String> al3 = new ArrayList<>();
		List<Patientdetail> l3 = si.getnames();
		Iterator<Patientdetail> i3 = l3.iterator();
		while (i3.hasNext()) {
			Patientdetail pn3 = (Patientdetail) i3.next();
			al3.add(pn3.getPatientname());
		}
		String[] stock3 = new String[al3.size()];
		stock3 = al3.toArray(stock3);

		ArrayList<String> al4 = new ArrayList<>();
		List<Patientdetail> l4 = si.getdate();
		Iterator<Patientdetail> i4 = l4.iterator();
		while (i4.hasNext()) {
			Patientdetail pn4 = (Patientdetail) i4.next();
			al4.add(pn4.getRegdate());
		}
		String[] stock4 = new String[al4.size()];
		stock4 = al4.toArray(stock4);

		ArrayList<String> na = new ArrayList();
		List<Doctordetail> l1 = doctorservice.getdoctors();
		Iterator<Doctordetail> i1 = l1.iterator();
		while (i1.hasNext()) {
			Doctordetail pn = (Doctordetail) i1.next();

			na.add(pn.getDoctorname());

		}
		String[] stockArr = new String[na.size()];
		stockArr = na.toArray(stockArr);
		/*
		 * ArrayList<String> al5 = new ArrayList(); List<Beddata> l5 =
		 * si.getbedno(); Iterator<Beddata> i5 = l5.iterator(); while
		 * (i5.hasNext()) { Beddata pn5 = (Beddata) i5.next();
		 * 
		 * al5.add(pn5.getBedno()); } String[] stock5 = new String[al5.size()];
		 * stock5 = al5.toArray(stock5);
		 */
		ArrayList<String> al6 = new ArrayList();
		List<Beddata> l6 = si.getroomno();
		Iterator<Beddata> i6 = l6.iterator();
		while (i6.hasNext()) {
			Beddata pn6 = (Beddata) i6.next();

			al6.add(pn6.getRoomno());

		}
		String[] stock6 = new String[al6.size()];
		stock6 = al6.toArray(stock6);

		ArrayList<String> al7 = new ArrayList();
		List<Beddata> l7 = si.getroomrent();
		Iterator<Beddata> i7 = l7.iterator();
		while (i7.hasNext()) {
			Beddata pn7 = (Beddata) i7.next();

			al7.add(pn7.getRoomrent());

		}
		String[] stock7 = new String[al7.size()];
		stock7 = al7.toArray(stock7);

		ArrayList<String> al8 = new ArrayList();
		List<Patientdetail> l8 = si.getallotedby();
		Iterator<Patientdetail> i8 = l8.iterator();
		while (i8.hasNext()) {
			Patientdetail pn8 = (Patientdetail) i8.next();

			al8.add(pn8.getAllotedby());

		}
		String[] stock8 = new String[al8.size()];
		stock8 = al8.toArray(stock8);

		String mrno = inp.getPatientmrno();
		String patientname = "";
		String patientData = "";

		List<Patientdetail> patient = si
				.getDetailspatient(inp.getPatientmrno());

		for (Patientdetail patientdetail : patient) {

			patientname = patientdetail.getPatientname();
			patientData = patientdetail.getRegdate();
			/* sess.setAttribute("patientname", patientname); */
			System.out.println(mrno + " " + patientname + " " + patientData);

		}

		String bed = inp.getBedno();
		/*
		 * String roomn1 = ""; String roomr1 = ""; List<Beddata> pat =
		 * si.getDetailspatientbed(inp.getBedno()); for (Beddata patientdetail :
		 * pat) { roomn1 = patientdetail.getRoomno(); roomr1 =
		 * patientdetail.getRoomrent(); System.out.println(bed + " " + roomn1 +
		 * " " + roomr1); }
		 */

		Map<String, Object> model1 = new HashMap<String, Object>();

		ArrayList<String> names = new ArrayList<String>();
		names.add(si.getRoomNumber(inp.getBedno()));

		String roomNo = "null";

		for (String s : names) {
			roomNo = s;
		}
		ArrayList<Double> roomAmount = new ArrayList<Double>();
		roomAmount.add(si.getRoomNumberAmount(roomNo));
		model1.put("roomNumber", names);
		model1.put("roomAmount", roomAmount);

		ArrayList<String[]> bedNos = new ArrayList<String[]>();
		bedNos.add(si.getBedNos());
		ArrayList<String> bedNos1 = new ArrayList<String>();
		for (String[] s : bedNos) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				bedNos1.add(s[j]);
				System.out.println(j + " " + s[j]);
			}
		}

		model1.put("bedNos", bedNos1);

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date datezsdv = new Date();
		String datrrgdd = dateFormat.format(datezsdv);

		String[] s1 = new String[] { "yes", "no" };
		String[] s2 = new String[] { "Cash", "Bank" };
		String[] s3 = new String[] { "Cash", "Bank" };

		ModelAndView m1 = new ModelAndView("InPatientAdmAdd2");
		// m1.addObject("model",model );

		/*
		 * Map<String, Object> model = new HashMap<String, Object>();
		 * model.put("details", si.getEntryDetails(inp.getPatientmrno()));
		 */
		/*
		 * System.out.println(model); System.out.println(model);
		 * System.out.println(model); System.out.println(model);
		 * System.out.println(model); System.out.println(model);
		 * 
		 * m1.addObject("model", model);
		 */

		m1.addObject("diet1", s1);
		m1.addObject("mode", s2);
		m1.addObject("mode2", s3);
		m1.addObject("doct", stockArr);
		m1.addObject("mrno", stock2);
		m1.addObject("names", stock3);
		m1.addObject("date", stock4);
		/* m1.addObject("bedno", stock5); */
		m1.addObject("roomno", stock6);
		m1.addObject("roomrent1", stock7);
		m1.addObject("allotedby", stock8);
		m1.addObject("mrno111", mrno);

		sess.setAttribute("patientname", patientname);
		sess.setAttribute("regdate", patientData);

		/* m1.addObject("regdate", patientData); */
		/* m1.addObject("data111", date); */
		/*
		 * sess.setAttribute("rooms", roomn1); sess.setAttribute("roomrents",
		 * roomr1);
		 */
		m1.addObject("beds", bed);
		// m1.addObject("rooms", roomn1);
		// m1.addObject("roomrents", roomr1);
		m1.addObject("time", datrrgdd);

		m1.addObject("inp", new entry());
		m1.addObject("model1", model1);

		return m1;
	}

	@RequestMapping("/displayDetails1234")
	public ModelAndView displayDetails1234(@ModelAttribute("inp") entry inp,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response, HttpSession sess) throws IOException

	{

		System.out.println("sdsdgvsdgvsdgsdsdsdfdc");
		ArrayList<String> al2 = new ArrayList<>();
		List<Patientdetail> l2 = si.getmrno();
		Iterator<Patientdetail> i2 = l2.iterator();
		while (i2.hasNext()) {
			Patientdetail pn2 = (Patientdetail) i2.next();
			al2.add(pn2.getPatientmrno());
		}
		String[] stock2 = new String[al2.size()];
		stock2 = al2.toArray(stock2);

		ArrayList<String> al3 = new ArrayList<>();
		List<Patientdetail> l3 = si.getnames();
		Iterator<Patientdetail> i3 = l3.iterator();
		while (i3.hasNext()) {
			Patientdetail pn3 = (Patientdetail) i3.next();
			al3.add(pn3.getPatientname());
		}
		String[] stock3 = new String[al3.size()];
		stock3 = al3.toArray(stock3);

		ArrayList<String> al4 = new ArrayList<>();
		List<Patientdetail> l4 = si.getdate();
		Iterator<Patientdetail> i4 = l4.iterator();
		while (i4.hasNext()) {
			Patientdetail pn4 = (Patientdetail) i4.next();
			al4.add(pn4.getRegdate());
		}
		String[] stock4 = new String[al4.size()];
		stock4 = al4.toArray(stock4);

		ArrayList<String> na = new ArrayList();
		List<Doctordetail> l1 = doctorservice.getdoctors();
		Iterator<Doctordetail> i1 = l1.iterator();
		while (i1.hasNext()) {
			Doctordetail pn = (Doctordetail) i1.next();

			na.add(pn.getDoctorname());

		}
		String[] stockArr = new String[na.size()];
		stockArr = na.toArray(stockArr);
		/*
		 * ArrayList<String> al5 = new ArrayList(); List<Beddata> l5 =
		 * si.getbedno(); Iterator<Beddata> i5 = l5.iterator(); while
		 * (i5.hasNext()) { Beddata pn5 = (Beddata) i5.next();
		 * 
		 * al5.add(pn5.getBedno()); } String[] stock5 = new String[al5.size()];
		 * stock5 = al5.toArray(stock5);
		 */
		ArrayList<String> al6 = new ArrayList();
		List<Beddata> l6 = si.getroomno();
		Iterator<Beddata> i6 = l6.iterator();
		while (i6.hasNext()) {
			Beddata pn6 = (Beddata) i6.next();

			al6.add(pn6.getRoomno());

		}
		String[] stock6 = new String[al6.size()];
		stock6 = al6.toArray(stock6);

		ArrayList<String> al7 = new ArrayList();
		List<Beddata> l7 = si.getroomrent();
		Iterator<Beddata> i7 = l7.iterator();
		while (i7.hasNext()) {
			Beddata pn7 = (Beddata) i7.next();

			al7.add(pn7.getRoomrent());

		}
		String[] stock7 = new String[al7.size()];
		stock7 = al7.toArray(stock7);

		ArrayList<String> al8 = new ArrayList();
		List<Patientdetail> l8 = si.getallotedby();
		Iterator<Patientdetail> i8 = l8.iterator();
		while (i8.hasNext()) {
			Patientdetail pn8 = (Patientdetail) i8.next();

			al8.add(pn8.getAllotedby());

		}
		String[] stock8 = new String[al8.size()];
		stock8 = al8.toArray(stock8);

		String mrno = inp.getPatientmrno();
		String patientname = "";
		String patientData = "";
		System.out.println("yhtf" + inp.getPatientmrno());
		System.out.println("page");

		List<Patientdetail> patient = si
				.getDetailspatient(inp.getPatientmrno());
		System.out.println(patient);
		System.out.println(patient);
		for (Patientdetail patientdetail : patient) {

			patientname = patientdetail.getPatientname();
			patientData = patientdetail.getRegdate();
			/* sess.setAttribute("patientname", patientname); */
			System.out.println(mrno + " " + patientname + " " + patientData);

		}

		String bed = inp.getBedno();
		/*
		 * String roomn1 = ""; String roomr1 = ""; List<Beddata> pat =
		 * si.getDetailspatientbed(inp.getBedno()); for (Beddata patientdetail :
		 * pat) { roomn1 = patientdetail.getRoomno(); roomr1 =
		 * patientdetail.getRoomrent(); System.out.println(bed + " " + roomn1 +
		 * " " + roomr1); }
		 */

		Map<String, Object> model1 = new HashMap<String, Object>();

		ArrayList<String> names = new ArrayList<String>();
		names.add(si.getRoomNumber(inp.getBedno()));

		String roomNo = "null";

		for (String s : names) {
			roomNo = s;
		}
		ArrayList<Double> roomAmount = new ArrayList<Double>();
		roomAmount.add(si.getRoomNumberAmount(roomNo));
		model1.put("roomNumber", names);
		model1.put("roomAmount", roomAmount);

		model1.put("updatedDetails", si.getDetails(inp.getId()));

		ArrayList<String[]> bedNos = new ArrayList<String[]>();
		bedNos.add(si.getBedNos());
		ArrayList<String> bedNos1 = new ArrayList<String>();
		for (String[] s : bedNos) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				bedNos1.add(s[j]);
				System.out.println(j + " " + s[j]);
			}
		}

		model1.put("bedNos", bedNos1);

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date datezsdv = new Date();
		String datrrgdd = dateFormat.format(datezsdv);

		String[] s1 = new String[] { "yes", "no" };
		String[] s2 = new String[] { "Cash", "Bank" };
		String[] s3 = new String[] { "Cash", "Bank" };

		ModelAndView m1 = new ModelAndView("InPatientupdate3");
		// m1.addObject("model",model );

		/*
		 * Map<String, Object> model = new HashMap<String, Object>();
		 * model.put("details", si.getEntryDetails(inp.getPatientmrno()));
		 */
		/*
		 * System.out.println(model); System.out.println(model);
		 * System.out.println(model); System.out.println(model);
		 * System.out.println(model); System.out.println(model);
		 * 
		 * m1.addObject("model", model);
		 */

		m1.addObject("diet1", s1);
		m1.addObject("mode", s2);
		m1.addObject("mode2", s3);
		m1.addObject("doct", stockArr);
		m1.addObject("mrno", stock2);
		m1.addObject("names", stock3);
		m1.addObject("date", stock4);
		/* m1.addObject("bedno", stock5); */
		m1.addObject("roomno", stock6);
		m1.addObject("roomrent1", stock7);
		m1.addObject("allotedby", stock8);
		m1.addObject("mrno111", mrno);

		sess.setAttribute("patientname", patientname);
		sess.setAttribute("regdate", patientData);

		/* m1.addObject("regdate", patientData); */
		/* m1.addObject("data111", date); */
		/*
		 * sess.setAttribute("rooms", roomn1); sess.setAttribute("roomrents",
		 * roomr1);
		 */
		m1.addObject("beds", bed);
		// m1.addObject("rooms", roomn1);
		// m1.addObject("roomrents", roomr1);
		m1.addObject("time", datrrgdd);

		m1.addObject("inp", new entry());
		m1.addObject("model1", model1);

		return m1;
	}

	@Autowired
	private Doctorservice doctorservice;

	@RequestMapping("/doctor")
	public ModelAndView first(@ModelAttribute("doctor") Doctordetail doctor,
			BindingResult result) {
		ModelAndView mv = new ModelAndView("DoctorInfo1");
		mv.addObject("abc", doctorservice.getdoctors());
		return mv;
	}

	@RequestMapping("/pagedoctor")
	public ModelAndView addbtn(@ModelAttribute("doctor") Doctordetail doctor,
			BindingResult result) {

		ArrayList<String> al2 = new ArrayList<>();
		List<dept> l2 = doctorservice.getdetpaert();
		for (dept dept : l2) {
			al2.add(dept.getDept());

		}
		String[] stock2 = new String[al2.size()];
		stock2 = al2.toArray(stock2);

		ModelAndView m1 = new ModelAndView("DoctorInfoAdd");

		m1.addObject("departmentnames1", stock2);
		return m1;

	}

	@RequestMapping("/save")
	public ModelAndView doctordisplay(
			@ModelAttribute("doctor") Doctordetail doctor, BindingResult result) {

		doctorservice.adddetails(doctor);

		return new ModelAndView("redirect:redirect.html");
	}

	@RequestMapping("/redirect")
	public ModelAndView redirect(@ModelAttribute("doctor") Doctordetail doctor,
			BindingResult result) {

		Map<String, Object> model = new HashMap<String, Object>();
		ArrayList<String> msg = new ArrayList<String>();
		msg.add("Record Inserted Sucessfully");
		model.put("msg1", msg);
		ModelAndView m2 = new ModelAndView("DoctorInfo1", model);
		m2.addObject("abc", doctorservice.getdoctors());
		return m2;
	}

	@RequestMapping("/doctorsearch.html")
	public ModelAndView doctorsearch(
			@ModelAttribute("doctor") Doctordetail doctor,
			BindingResult result, HttpServletRequest req) {

		String dname = req.getParameter("doctorname");
		System.out.println(dname);
		ModelAndView mav = new ModelAndView("DoctorInfo1");
		mav.addObject("abc", doctorservice.doctor(dname));
		if (dname != "") {
			System.out.println("if");
			List<Doctordetail> l2 = doctorservice.doctor(dname);
		}

		return mav;

	}

	@RequestMapping("/updated")
	public ModelAndView updateex(@ModelAttribute("doctor") Doctordetail doctor,
			BindingResult result, HttpServletRequest req) {
		System.out.println(doctor.getDoctorname());
		String dname = req.getParameter("doctornames");
		System.out.println(dname);
		String addre = req.getParameter("address");
		doctor.setAddress(addre);

		ArrayList<String> al2 = new ArrayList<>();
		List<dept> l2 = doctorservice.getdetpaert();
		for (dept dept : l2) {
			al2.add(dept.getDept());

		}
		String[] stock2 = new String[al2.size()];
		stock2 = al2.toArray(stock2);

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("updatedDetails", doctorservice.getDetails(doctor.getId()));

		ModelAndView mv = new ModelAndView("DoctorInfoUpdate");
		mv.addObject("departmentnames1", stock2);
		mv.addObject("model", model);

		return mv;

	}

	@RequestMapping("/updatedata")
	public ModelAndView saveUserUpdate(
			@ModelAttribute("doctor") Doctordetail doctor,

			BindingResult result) {
		doctorservice.addUserUpdate(doctor);

		ModelAndView mav = new ModelAndView("DoctorinfoSucess");

		// for getting all details
		mav.addObject("abc", doctorservice.getdoctors());

		return mav;
	}

	@RequestMapping("/deleted")
	public ModelAndView deletecontent(
			@ModelAttribute("doctor") Doctordetail doctor, BindingResult result) {
		System.out.println("iam in deleted controller");

		doctorservice.deleteDoctor(doctor);

		return new ModelAndView("redirect:deletedAfter.html");
	}

	@RequestMapping("/deletedAfter")
	public ModelAndView deletedAfter(
			@ModelAttribute("doctor") Doctordetail doctor, BindingResult result) {
		System.out.println("iam in deleted controller");

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("abc", doctorservice.getuser());
		return new ModelAndView("DoctorInfo1", model);
	}

	// stock Report
	String fileName1 = "StockDetails";
	java.sql.Connection conn = null;

	@RequestMapping(value = "/stockReport")
	public ModelAndView generateSupplierrReport(
			@ModelAttribute("user") ProductType99 user, BindingResult result,
			Model model, HttpServletRequest request,
			HttpServletResponse response) throws ParseException {

		String reportFileName = "StockDetails";

		try {
			try {

				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				System.out
						.println("Please include Classpath Where your MySQL Driver is located");
				e.printStackTrace();
			}

			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/SriLakshmiSuperSplhospital",
					"root", "root");

			if (conn != null) {
				System.out.println("Database Connected");
			} else {
				System.out.println(" connection Failed ");
			}

			HashMap<String, Object> hmParams = new HashMap<String, Object>();

			JasperReport jasperReport = getCompiledFile(reportFileName, request);
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					jasperReport, hmParams, conn);
			// For HTML report
		} catch (Exception sqlExp) {

			System.out.println("Exception::" + sqlExp.toString());

		} finally {

			try {

				if (conn != null) {
					conn.close();
					conn = null;
				}

			} catch (SQLException expSQL) {

				System.out.println("SQLExp::CLOSING::" + expSQL.toString());

			}

		}

		ArrayList<String[]> ptypes = new ArrayList<String[]>();
		ptypes.add(stockService.getProductTypes());
		ArrayList<String> ptypes1 = new ArrayList<String>();
		for (String[] s : ptypes) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				ptypes1.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}

		hmParams.put("user", stockService.GetParticularTypeDetails());
		hmParams.put("ptypes", ptypes1);

		ArrayList<String[]> names = new ArrayList<String[]>();
		names.add(stockService.getProductNames());
		ArrayList<String> names1 = new ArrayList<String>();
		for (String[] s : names) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				names1.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}

		hmParams.put("names", names1);
		hmParams.put("user", stockService.GetDetails());
		return new ModelAndView("StockPosition", hmParams);
	}

	private JasperReport getCompiledFile(String fileName,
			HttpServletRequest request) throws JRException {
		System.out.println("before compilation");
		File reportFile = new File(request.getSession().getServletContext()
				.getRealPath("/jasper/" + fileName1 + ".jasper"));
		// If compiled file is not found, then compile XML template
		if (!reportFile.exists()) {
			JasperCompileManager.compileReportToFile(
					request.getSession().getServletContext()
							.getRealPath("/jasper/" + fileName1 + ".jrxml"),
					request.getSession().getServletContext()
							.getRealPath("/jasper/" + fileName1 + ".jasper"));
		}
		System.out.println("ofter compilation");
		JasperReport jasperReport = (JasperReport) JRLoader
				.loadObjectFromFile(reportFile.getPath());
		System.out.println("path "
				+ request.getSession().getServletContext()
						.getRealPath("/jasper/" + fileName1 + ".jasper"));
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
				null, conn);
		JasperViewer.viewReport(jasperPrint, false);

		System.out.println("123456789");
		return jasperReport;
	}

	// supplier Report
	String fileName = "SupplierInfo";

	@RequestMapping(value = "/supplierReport")
	public ModelAndView generatevendorReport(@ModelAttribute("s") Supplier s,
			@ModelAttribute("user1") Hospital user1, BindingResult result,
			Model model, HttpServletRequest request,
			HttpServletResponse response) throws ParseException {

		String reportFileName = "SupplierInfo";

		try {
			try {

				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				System.out
						.println("Please include Classpath Where your MySQL Driver is located");
				e.printStackTrace();
			}

			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/SriLakshmiSuperSplhospital",
					"root", "root");

			if (conn != null) {
				System.out.println("Database Connected");
			} else {
				System.out.println(" connection Failed ");
			}

			HashMap<String, Object> hmParams = new HashMap<String, Object>();

			JasperReport jasperReport = getCompiledFile1(reportFileName,
					request);
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					jasperReport, hmParams, conn);
			// For HTML report
		} catch (Exception sqlExp) {

			System.out.println("Exception::" + sqlExp.toString());

		} finally {

			try {

				if (conn != null) {
					conn.close();
					conn = null;
				}

			} catch (SQLException expSQL) {

				System.out.println("SQLExp::CLOSING::" + expSQL.toString());

			}

		}
		hmParams.put("s", supplierService.listSupplier());
		ModelAndView mv = new ModelAndView("SuppliersInfo1", hmParams);
		return mv;
	}

	private JasperReport getCompiledFile1(String fileName,
			HttpServletRequest request) throws JRException {
		System.out.println("before compilation");
		File reportFile = new File(request.getSession().getServletContext()
				.getRealPath("/jasper/" + fileName + ".jasper"));
		// If compiled file is not found, then compile XML template
		if (!reportFile.exists()) {
			JasperCompileManager.compileReportToFile(
					request.getSession().getServletContext()
							.getRealPath("/jasper/" + fileName + ".jrxml"),
					request.getSession().getServletContext()
							.getRealPath("/jasper/" + fileName + ".jasper"));
		}
		System.out.println("ofter compilation");
		JasperReport jasperReport = (JasperReport) JRLoader
				.loadObjectFromFile(reportFile.getPath());
		System.out.println("path "
				+ request.getSession().getServletContext()
						.getRealPath("/jasper/" + fileName + ".jasper"));
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
				null, conn);
		JasperViewer.viewReport(jasperPrint, false);

		System.out.println("123456789");
		return jasperReport;
	}

	// sales entry Report
	String fileName2 = "SalesEntrys";

	@RequestMapping(value = "/salesReport")
	public String generateSalesReport(

	@ModelAttribute("user") SalesEntryPojo user, BindingResult result,
			Model model, HttpServletRequest request,
			HttpServletResponse response) throws ParseException {

		String reportFileName = "SalesEntrys";

		try {
			try {

				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				System.out
						.println("Please include Classpath Where your MySQL Driver is located");
				e.printStackTrace();
			}

			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/SriLakshmiSuperSplhospital",
					"root", "root");

			if (conn != null) {
				System.out.println("Database Connected");
			} else {
				System.out.println(" connection Failed ");
			}

			HashMap<String, Object> hmParams = new HashMap<String, Object>();

			JasperReport jasperReport = getCompiledFile2(reportFileName,
					request);
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					jasperReport,

					hmParams, conn); // For HTML report
		} catch (Exception sqlExp) {

			System.out.println("Exception::" + sqlExp.toString());

		} finally {

			try {

				if (conn != null) {
					conn.close();
					conn = null;
				}

			} catch (SQLException expSQL) {

				System.out.println("SQLExp::CLOSING::" + expSQL.toString());

			}

		}

		return null;
	}

	private JasperReport getCompiledFile2(String fileName,
			HttpServletRequest request) throws JRException {
		System.out.println("before compilation");
		File reportFile = new File(request.getSession().getServletContext()
				.getRealPath("/jasper/" + fileName2 + ".jasper"));
		// If compiled file not found, then compile XML template
		if (!reportFile.exists()) {
			JasperCompileManager.compileReportToFile(
					request.getSession().getServletContext()
							.getRealPath("/jasper/" + fileName2 + ".jrxml"),
					request.getSession().getServletContext()
							.getRealPath("/jasper/" + fileName2 + ".jasper"));
		}
		System.out.println("ofter compilation");
		JasperReport jasperReport = (JasperReport) JRLoader
				.loadObjectFromFile(reportFile.getPath());
		System.out.println("path "
				+ request.getSession().getServletContext()
						.getRealPath("/jasper/" + fileName2 + ".jasper"));
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
				null, conn);
		JasperViewer.viewReport(jasperPrint, false);

		System.out.println("123456789");
		return jasperReport;
	}

	// Duepatient Report
	String fileName3 = "DuePatient";

	@RequestMapping(value = "/duepatientReport")
	public ModelAndView generateDuePatientReport(
			@ModelAttribute("user") DuePatient user, BindingResult result,
			Model model, HttpServletRequest request,
			HttpServletResponse response) throws ParseException {

		String reportFileName = "DuePatient";

		try {
			try {

				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				System.out
						.println("Please include Classpath Where your MySQL Driver is located");
				e.printStackTrace();
			}

			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/SriLakshmiSuperSplhospital",
					"root", "root");

			if (conn != null) {
				System.out.println("Database Connected");
			} else {
				System.out.println(" connection Failed ");
			}

			HashMap<String, Object> hmParams = new HashMap<String, Object>();

			JasperReport jasperReport = getCompiledFile3(reportFileName,
					request);
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					jasperReport, hmParams, conn);
			// For HTML report
		} catch (Exception sqlExp) {

			System.out.println("Exception::" + sqlExp.toString());

		} finally {

			try {

				if (conn != null) {
					conn.close();
					conn = null;
				}

			} catch (SQLException expSQL) {

				System.out.println("SQLExp::CLOSING::" + expSQL.toString());

			}

		}
		hmParams.put("user", duePatientService.GetDetails());
		return new ModelAndView("DuePatient1", hmParams);
	}

	private JasperReport getCompiledFile3(String fileName,
			HttpServletRequest request) throws JRException {
		System.out.println("before compilation");
		File reportFile = new File(request.getSession().getServletContext()
				.getRealPath("/jasper/" + fileName3 + ".jasper"));
		// If compiled file is not found, then compile XML template
		if (!reportFile.exists()) {
			JasperCompileManager.compileReportToFile(
					request.getSession().getServletContext()
							.getRealPath("/jasper/" + fileName3 + ".jrxml"),
					request.getSession().getServletContext()
							.getRealPath("/jasper/" + fileName3 + ".jasper"));
		}
		System.out.println("ofter compilation");
		JasperReport jasperReport = (JasperReport) JRLoader
				.loadObjectFromFile(reportFile.getPath());
		System.out.println("path "
				+ request.getSession().getServletContext()
						.getRealPath("/jasper/" + fileName3 + ".jasper"));
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
				null, conn);
		JasperViewer.viewReport(jasperPrint, false);

		System.out.println("123456789");
		return jasperReport;
	}

	// RoomTarrif Report
	String fileName4 = "RoomTariff";

	@RequestMapping(value = "/tarrifReport")
	public ModelAndView generateTarrifReport(
			@ModelAttribute("tarrif") RoomTarrif tarrif, BindingResult result,
			Model model, HttpServletRequest request,
			HttpServletResponse response) throws ParseException {

		String reportFileName = "RoomTariff";

		try {
			try {

				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				System.out
						.println("Please include Classpath Where your MySQL Driver is located");
				e.printStackTrace();
			}

			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/SriLakshmiSuperSplhospital",
					"root", "root");

			if (conn != null) {
				System.out.println("Database Connected");
			} else {
				System.out.println(" connection Failed ");
			}

			HashMap<String, Object> hmParams = new HashMap<String, Object>();

			JasperReport jasperReport = getCompiledFile4(reportFileName,
					request);
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					jasperReport, hmParams, conn);
			// For HTML report
		} catch (Exception sqlExp) {

			System.out.println("Exception::" + sqlExp.toString());

		} finally {

			try {

				if (conn != null) {
					conn.close();
					conn = null;
				}

			} catch (SQLException expSQL) {

				System.out.println("SQLExp::CLOSING::" + expSQL.toString());

			}

		}
		Map<String, Object> model1 = new HashMap<String, Object>();
		model1.put("td", tarrifService.GetDetails());
		ArrayList<String[]> roonnos = new ArrayList<String[]>();
		roonnos.add(tarrifService.getRoomnosList());
		ArrayList<String> roonnos1 = new ArrayList<String>();
		for (String[] s1 : roonnos) {
			int i = s1.length;
			for (int j = 0; j < i; j++) {
				roonnos1.add(s1[j]);
			}
		}
		model1.put("roonnos", roonnos1);
		ModelAndView mv = new ModelAndView("RoomTariff1", model1);

		return mv;
	}

	private JasperReport getCompiledFile4(String fileName,
			HttpServletRequest request) throws JRException {
		System.out.println("before compilation");
		File reportFile = new File(request.getSession().getServletContext()
				.getRealPath("/jasper/" + fileName4 + ".jasper"));
		// If compiled file is not found, then compile XML template
		if (!reportFile.exists()) {
			JasperCompileManager.compileReportToFile(
					request.getSession().getServletContext()
							.getRealPath("/jasper/" + fileName4 + ".jrxml"),
					request.getSession().getServletContext()
							.getRealPath("/jasper/" + fileName4 + ".jasper"));
		}
		System.out.println("ofter compilation");
		JasperReport jasperReport = (JasperReport) JRLoader
				.loadObjectFromFile(reportFile.getPath());
		System.out.println("path "
				+ request.getSession().getServletContext()
						.getRealPath("/jasper/" + fileName4 + ".jasper"));
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
				null, conn);
		JasperViewer.viewReport(jasperPrint, false);

		System.out.println("123456789");
		return jasperReport;
	}

	// ProductDetails Report
	String fileName5 = "ProductDetail";

	@RequestMapping(value = "/productReport")
	public ModelAndView generateProductReport(
			@ModelAttribute("pd") ProductDetail pd,
			@ModelAttribute("user1") Hospital user1, BindingResult result,
			Model model, HttpServletRequest request,
			HttpServletResponse response) throws ParseException {

		String reportFileName = "ProductDetail";

		try {
			try {

				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				System.out
						.println("Please include Classpath Where your MySQL Driver is located");
				e.printStackTrace();
			}

			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/SriLakshmiSuperSplhospital",
					"root", "root");

			if (conn != null) {
				System.out.println("Database Connected");
			} else {
				System.out.println(" connection Failed ");
			}

			HashMap<String, Object> hmParams = new HashMap<String, Object>();

			JasperReport jasperReport = getCompiledFile5(reportFileName,
					request);
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					jasperReport, hmParams, conn);
			// For HTML report
		} catch (Exception sqlExp) {

			System.out.println("Exception::" + sqlExp.toString());

		} finally {

			try {

				if (conn != null) {
					conn.close();
					conn = null;
				}

			} catch (SQLException expSQL) {

				System.out.println("SQLExp::CLOSING::" + expSQL.toString());

			}

		}
		hmParams.put("pd", productDetailService.listProductDetail());
		return new ModelAndView("ProductDetails1", hmParams);
	}

	private JasperReport getCompiledFile5(String fileName,
			HttpServletRequest request) throws JRException {
		System.out.println("before compilation");
		File reportFile = new File(request.getSession().getServletContext()
				.getRealPath("/jasper/" + fileName5 + ".jasper"));
		// If compiled file is not found, then compile XML template
		if (!reportFile.exists()) {
			JasperCompileManager.compileReportToFile(
					request.getSession().getServletContext()
							.getRealPath("/jasper/" + fileName5 + ".jrxml"),
					request.getSession().getServletContext()
							.getRealPath("/jasper/" + fileName5 + ".jasper"));
		}
		System.out.println("ofter compilation");
		JasperReport jasperReport = (JasperReport) JRLoader
				.loadObjectFromFile(reportFile.getPath());
		System.out.println("path "
				+ request.getSession().getServletContext()
						.getRealPath("/jasper/" + fileName5 + ".jasper"));
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
				null, conn);
		JasperViewer.viewReport(jasperPrint, false);

		System.out.println("123456789");
		return jasperReport;
	}

	// Patient appontment Report
	String fileName6 = "PatAppoint";
	HashMap<String, Object> hmParams1 = new HashMap<String, Object>();

	@RequestMapping(value = "/get", params = "patientAmppointMneReport")
	public ModelAndView generatePatientReport(
			@ModelAttribute("user") PatientPojo user, BindingResult result,
			Model model, HttpServletRequest request,
			HttpServletResponse response) throws ParseException {

		String reportFileName = "PatAppoint";

		try {
			try {

				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				System.out
						.println("Please include Classpath Where your MySQL Driver is located");
				e.printStackTrace();
			}

			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/SriLakshmiSuperSplhospital",
					"root", "root");

			if (conn != null) {
				System.out.println("Database Connected");
			} else {
				System.out.println(" connection Failed ");
			}
			hmParams1.put("doctName", user.getDoctorName());
			hmParams1.put("aptDate", user.getAppointmentDate());
			System.out.println(user.getDoctorName());
			System.out.println(user.getAppointmentDate());
			System.out.println(user.getDoctorName());
			System.out.println(user.getAppointmentDate());
			System.out.println(hmParams1);
			JasperReport jasperReport = getCompiledFile6(reportFileName,
					request);
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					jasperReport, hmParams1, conn);
			// For HTML report
		} catch (Exception sqlExp) {

			System.out.println("Exception::" + sqlExp.toString());

		} finally {

			try {

				if (conn != null) {
					conn.close();
					conn = null;
				}

			} catch (SQLException expSQL) {

				System.out.println("SQLExp::CLOSING::" + expSQL.toString());

			}

		}

		return new ModelAndView("redirect:/bca.html");
	}

	private JasperReport getCompiledFile6(String fileName,
			HttpServletRequest request) throws JRException {
		System.out.println("before compilation");
		File reportFile = new File(request.getSession().getServletContext()
				.getRealPath("/jasper/" + fileName6 + ".jasper"));
		// If compiled file is not found, then compile XML template
		if (!reportFile.exists()) {
			JasperCompileManager.compileReportToFile(
					request.getSession().getServletContext()
							.getRealPath("/jasper/" + fileName6 + ".jrxml"),
					request.getSession().getServletContext()
							.getRealPath("/jasper/" + fileName6 + ".jasper"));
		}
		System.out.println("ofter compilation");
		JasperReport jasperReport = (JasperReport) JRLoader
				.loadObjectFromFile(reportFile.getPath());
		System.out.println("path "
				+ request.getSession().getServletContext()
						.getRealPath("/jasper/" + fileName6 + ".jasper"));
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
				hmParams1, conn);
		JasperViewer.viewReport(jasperPrint, false);

		return jasperReport;
	}

	// In Patient Report
	String fileName7 = "InPatientAdmission";

	@RequestMapping(value = "/InpatientReport")
	public ModelAndView generateInPatientReport(
			@ModelAttribute("user") InpatientPojo user, BindingResult result,
			Model model, HttpServletRequest request,
			HttpServletResponse response) throws ParseException {

		String reportFileName = "InPatientAdmission";

		try {
			try {

				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				System.out
						.println("Please include Classpath Where your MySQL Driver is located");
				e.printStackTrace();
			}

			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/SriLakshmiSuperSplhospital",
					"root", "root");

			if (conn != null) {
				System.out.println("Database Connected");
			} else {
				System.out.println(" connection Failed ");
			}

			HashMap<String, Object> hmParams = new HashMap<String, Object>();

			JasperReport jasperReport = getCompiledFile7(reportFileName,
					request);
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					jasperReport, hmParams, conn);
			// For HTML report
		} catch (Exception sqlExp) {

			System.out.println("Exception::" + sqlExp.toString());

		} finally {

			try {

				if (conn != null) {
					conn.close();
					conn = null;
				}

			} catch (SQLException expSQL) {

				System.out.println("SQLExp::CLOSING::" + expSQL.toString());

			}

		}

		return new ModelAndView("redirect:/inpatientAddmissionDetails.html");
	}

	private JasperReport getCompiledFile7(String fileName,
			HttpServletRequest request) throws JRException {
		System.out.println("before compilation");
		File reportFile = new File(request.getSession().getServletContext()
				.getRealPath("/jasper/" + fileName7 + ".jasper"));
		// If compiled file is not found, then compile XML template
		if (!reportFile.exists()) {
			JasperCompileManager.compileReportToFile(
					request.getSession().getServletContext()
							.getRealPath("/jasper/" + fileName7 + ".jrxml"),
					request.getSession().getServletContext()
							.getRealPath("/jasper/" + fileName7 + ".jasper"));
		}
		System.out.println("ofter compilation");
		JasperReport jasperReport = (JasperReport) JRLoader
				.loadObjectFromFile(reportFile.getPath());
		System.out.println("path "
				+ request.getSession().getServletContext()
						.getRealPath("/jasper/" + fileName7 + ".jasper"));
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
				null, conn);
		JasperViewer.viewReport(jasperPrint, false);

		System.out.println("123456789");
		return jasperReport;
	}

	// In Patient Report
	String fileName8 = "DoctorDetails";

	@RequestMapping(value = "/doctorReport")
	public ModelAndView generateDoctorReport(
			@ModelAttribute("user") Doctordetail user, BindingResult result,
			Model model, HttpServletRequest request,
			HttpServletResponse response) throws ParseException {

		String reportFileName = "DoctorDetails";

		try {
			try {

				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				System.out
						.println("Please include Classpath Where your MySQL Driver is located");
				e.printStackTrace();
			}

			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/SriLakshmiSuperSplhospital",
					"root", "root");

			if (conn != null) {
				System.out.println("Database Connected");
			} else {
				System.out.println(" connection Failed ");
			}

			HashMap<String, Object> hmParams = new HashMap<String, Object>();

			JasperReport jasperReport = getCompiledFile8(reportFileName,
					request);
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					jasperReport, hmParams, conn);
			// For HTML report
		} catch (Exception sqlExp) {

			System.out.println("Exception::" + sqlExp.toString());

		} finally {

			try {

				if (conn != null) {
					conn.close();
					conn = null;
				}

			} catch (SQLException expSQL) {

				System.out.println("SQLExp::CLOSING::" + expSQL.toString());

			}

		}

		return new ModelAndView("redirect:/doctor.html");
	}

	private JasperReport getCompiledFile8(String fileName,
			HttpServletRequest request) throws JRException {
		System.out.println("before compilation");
		File reportFile = new File(request.getSession().getServletContext()
				.getRealPath("/jasper/" + fileName8 + ".jasper"));
		// If compiled file is not found, then compile XML template
		if (!reportFile.exists()) {
			JasperCompileManager.compileReportToFile(
					request.getSession().getServletContext()
							.getRealPath("/jasper/" + fileName8 + ".jrxml"),
					request.getSession().getServletContext()
							.getRealPath("/jasper/" + fileName8 + ".jasper"));
		}
		System.out.println("ofter compilation");
		JasperReport jasperReport = (JasperReport) JRLoader
				.loadObjectFromFile(reportFile.getPath());
		System.out.println("path "
				+ request.getSession().getServletContext()
						.getRealPath("/jasper/" + fileName8 + ".jasper"));
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
				null, conn);
		JasperViewer.viewReport(jasperPrint, false);

		System.out.println("123456789");
		return jasperReport;
	}

	/* SALES ENTRY */

	@Autowired
	private SalesEntryService salesEntryService;

	@RequestMapping("/FirstSalesEntryListDet")
	public ModelAndView ShowSalesEntrylist(
			@ModelAttribute("salesEntryPojo") SalesEntryPojo salesEntryPojo,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();

		model.put("user", salesEntryService.getCustomerDetails());

		return new ModelAndView("saleslist", model);

	}

	@RequestMapping("/retriveDayDetails")
	public ModelAndView SearchPatient(
			@ModelAttribute("salesEntryPojo") SalesEntryPojo salesEntryPojo,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("user", salesEntryService
				.getUserParticularDayDetails(salesEntryPojo.getSalesDate()));
		System.out.println("retriving user data Data");
		return new ModelAndView("saleslist", model);
	}

	@RequestMapping("/salesEntryFirstDetailsOFSalesEntry")
	public ModelAndView getRegisterForm(
			@ModelAttribute("salesEntryPojo") SalesEntryPojo salesEntryPojo,
			BindingResult result) {

		ArrayList<String> customerType = new ArrayList<String>();
		customerType.add(" " + "InPatient" + " ");
		customerType.add(" " + "OutPatient" + " ");
		ArrayList<String> billingType = new ArrayList<String>();
		billingType.add("MRP");
		billingType.add("ABC");
		billingType.add("DEF");
		billingType.add("GHI");
		billingType.add("JKL");
		billingType.add("MNO");
		ArrayList<String> billStatus = new ArrayList<String>();
		billStatus.add(" " + "Paid");
		billStatus.add(" " + "Not Paid");
		ArrayList<String> salesType = new ArrayList<String>();
		salesType.add("CASH");
		salesType.add("CHEQUE");

		ArrayList<String[]> packingTypesnames = new ArrayList<String[]>();
		packingTypesnames.add(salesEntryService.getpackingTypesnamesDb());
		ArrayList<String> packingTypesnamesDb = new ArrayList<String>();
		for (String[] s : packingTypesnames) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				packingTypesnamesDb.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}

		ArrayList<String[]> productnameDb = new ArrayList<String[]>();
		productnameDb.add(salesEntryService.getCustomerName());
		ArrayList<Integer> customerId = new ArrayList<Integer>();
		customerId.add(salesEntryService.getCustomerMaxId());
		ArrayList<String> productname = new ArrayList<String>();
		for (String[] s : productnameDb) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				productname.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("productname", productname);
		model.put("customerType", customerType);
		model.put("billingType", billingType);
		model.put("billStatus", billStatus);
		model.put("salesType", salesType);
		model.put("customerId", customerId);
		model.put("packingTypesnames", packingTypesnamesDb);
		return new ModelAndView("salesEntryDetailsJsp", model);
	}

	@RequestMapping("/getSalesProductdetails")
	public ModelAndView getparticularproductDetails(
			@ModelAttribute("salesEntryPojo") SalesEntryPojo salesEntryPojo,
			BindingResult result) {

		ArrayList<String> customerType = new ArrayList<String>();
		customerType.add(" " + "InPatient" + " ");
		customerType.add(" " + "OutPatient" + " ");
		customerType.add(" " + "Others" + " ");
		ArrayList<String> billingType = new ArrayList<String>();
		billingType.add("MRP");
		billingType.add("ABC");
		billingType.add("DEF");
		billingType.add("GHI");
		billingType.add("JKL");
		billingType.add("MNO");
		ArrayList<String> billStatus = new ArrayList<String>();
		billStatus.add(" " + "Paid" + " ");
		billStatus.add(" " + "Not Paid" + " ");
		ArrayList<String> salesType = new ArrayList<String>();
		salesType.add("CASH");
		salesType.add("CHEQUE");
		ArrayList<Double> totalAmount = new ArrayList<Double>();
		totalAmount.add(salesEntryService.gettotalAmount());
		ArrayList<String[]> productnameDb = new ArrayList<String[]>();
		productnameDb.add(salesEntryService.getCustomerName());
		ArrayList<Integer> customerId = new ArrayList<Integer>();
		customerId.add(salesEntryService.getCustomerMaxId());
		ArrayList<String> productname = new ArrayList<String>();
		for (String[] s : productnameDb) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				productname.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("productname", productname);
		model.put("customerType", customerType);
		model.put("billingType", billingType);
		model.put("billStatus", billStatus);
		model.put("salesType", salesType);
		model.put("customerId", customerId);
		model.put("totalAmount", totalAmount);
		model.put("productRelatedDetails",
				salesEntryService.getProductRelateddetails(salesEntryPojo));
		model.put("getAddingProductDetails",
				salesEntryService.getAddingProductDetails());

		return new ModelAndView("salesEntryDetailsJsp2", model);
	}

	@RequestMapping(value = "/getSalesProductdetails", params = { "add" })
	public ModelAndView getparticularproductDetailsADD(
			@ModelAttribute("salesEntryPojo") SalesEntryPojo salesEntryPojo,
			BindingResult result) {

		salesEntryService.addproductDetails(salesEntryPojo);

		ArrayList<String> customerType = new ArrayList<String>();
		customerType.add(" " + "InPatient" + " ");
		customerType.add(" " + "OutPatient" + " ");
		customerType.add(" " + "Others" + " ");

		ArrayList<Double> totalAmount = new ArrayList<Double>();
		totalAmount.add(salesEntryService.gettotalAmount());

		ArrayList<String> billingType = new ArrayList<String>();
		billingType.add("MRP");
		billingType.add("ABC");
		billingType.add("DEF");
		billingType.add("GHI");
		billingType.add("JKL");
		billingType.add("MNO");
		ArrayList<String> billStatus = new ArrayList<String>();
		billStatus.add(" " + "Paid" + " ");
		billStatus.add(" " + "Not Paid" + " ");
		ArrayList<String> salesType = new ArrayList<String>();
		salesType.add("CASH");
		salesType.add("CHEQUE");
		ArrayList<String[]> productnameDb = new ArrayList<String[]>();
		productnameDb.add(salesEntryService.getCustomerName());
		ArrayList<Integer> customerId = new ArrayList<Integer>();
		customerId.add(salesEntryService.getCustomerMaxId());
		ArrayList<String> productname = new ArrayList<String>();
		for (String[] s : productnameDb) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				productname.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("productname", productname);
		model.put("customerType", customerType);
		model.put("billingType", billingType);
		model.put("billStatus", billStatus);
		model.put("salesType", salesType);
		model.put("customerId", customerId);
		model.put("totalAmount", totalAmount);

		model.put("productRelatedDetails",
				salesEntryService.getProductRelateddetails(salesEntryPojo));
		model.put("getAddingProductDetails",
				salesEntryService.getAddingProductDetails());

		return new ModelAndView("salesEntryDetailsJsp2", model);
	}

	@RequestMapping(value = "/closeSalesEntrydetails")
	public ModelAndView closeSalesEntrydetails(
			@ModelAttribute("salesEntryPojo") SalesEntryPojo salesEntryPojo,
			BindingResult result) {

		salesEntryService.canceslsalesEntryDetails();

		return new ModelAndView("redirect:/FirstSalesEntryListDet.html");
	}

	@RequestMapping(value = "/getSalesProductdetails", params = { "total" })
	public ModelAndView totalbilling(
			@ModelAttribute("salesEntryPojo") SalesEntryPojo salesEntryPojo,
			BindingResult result) {

		salesEntryService.totalBilling(salesEntryPojo);

		return new ModelAndView(
				"redirect:/FirstSalesEntryListDetOfterSaving.html");
	}

	@RequestMapping("/FirstSalesEntryListDetOfterSaving")
	public ModelAndView FirstSalesEntryListDetOfterSaving(
			@ModelAttribute("salesEntryPojo") SalesEntryPojo salesEntryPojo,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();

		model.put("user", salesEntryService.getCustomerDetails());
		ArrayList<String> message = new ArrayList<String>();
		message.add("Record Inserted Sucessfully");
		model.put("msg", message);
		return new ModelAndView("saleslist", model);

	}

	@RequestMapping(value = "/deleteSalesdetails")
	public ModelAndView deleteSalesEntry(
			@ModelAttribute("salesEntryPojo") SalesEntryPojo salesEntryPojo,
			BindingResult result) {
		System.out.println("am in delete controller");
		System.out.println(salesEntryPojo.getSalesid());
		salesEntryService.deleteSales(salesEntryPojo.getSalesid());

		ArrayList<String> customerType = new ArrayList<String>();
		customerType.add(" " + "InPatient" + " ");
		customerType.add(" " + "OutPatient" + " ");
		customerType.add(" " + "Others" + " ");
		ArrayList<Double> totalAmount = new ArrayList<Double>();
		totalAmount.add(salesEntryService.gettotalAmount());

		ArrayList<String> billingType = new ArrayList<String>();
		billingType.add("MRP");
		billingType.add("ABC");
		billingType.add("DEF");
		billingType.add("GHI");
		billingType.add("JKL");
		billingType.add("MNO");
		ArrayList<String> billStatus = new ArrayList<String>();
		billStatus.add(" " + "Paid" + " ");
		billStatus.add(" " + "Not Paid" + " ");
		ArrayList<String> salesType = new ArrayList<String>();
		salesType.add("CASH");
		salesType.add("CHEQUE");

		ArrayList<String[]> packingTypesnames = new ArrayList<String[]>();
		packingTypesnames.add(salesEntryService.getpackingTypesnamesDb());
		ArrayList<String> packingTypesnamesDb = new ArrayList<String>();
		for (String[] s : packingTypesnames) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				packingTypesnamesDb.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}
		ArrayList<String[]> productnameDb = new ArrayList<String[]>();
		productnameDb.add(salesEntryService.getCustomerName());
		ArrayList<Integer> customerId = new ArrayList<Integer>();
		customerId.add(salesEntryService.getCustomerMaxId());
		ArrayList<String> productname = new ArrayList<String>();
		for (String[] s : productnameDb) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				productname.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("productname", productname);
		model.put("customerType", customerType);
		model.put("billingType", billingType);
		model.put("billStatus", billStatus);
		model.put("salesType", salesType);
		model.put("customerId", customerId);
		model.put("totalAmount", totalAmount);
		model.put("packingTypesnames", packingTypesnamesDb);
		model.put("productRelatedDetails",
				salesEntryService.getProductRelateddetails(salesEntryPojo));
		model.put("getAddingProductDetails",
				salesEntryService.getAddingProductDetails());

		return new ModelAndView("salesEntryDetailsJsp2", model);
	}

	/* SALES RETURNS */

	@Autowired
	private SalesReturnsService salesReturnsService;

	@RequestMapping("/salesreturnsdetailsList446")
	public ModelAndView returnslistdetails(
			@ModelAttribute("salesEntryPojo") SalesEntryPojo salesEntryPojo,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();

		ArrayList<String[]> names = new ArrayList<String[]>();
		names.add(salesReturnsService.getSalesReturnsNames());
		ArrayList<String> names1 = new ArrayList<String>();

		for (String[] s : names) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				names1.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}
		model.put("details", salesReturnsService.getdetailsOfsalesReturns());
		model.put("names", names1);
		System.out.println(model);
		return new ModelAndView("salesReturnsListDetails", model);
	}

	@RequestMapping("/salesreturnsdetailsList446Name")
	public ModelAndView returnslistdetailsName(
			@ModelAttribute("salesEntryPojo") SalesEntryPojo salesEntryPojo,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();

		ArrayList<String[]> names = new ArrayList<String[]>();
		names.add(salesReturnsService.getSalesReturnsNames());
		ArrayList<String> names1 = new ArrayList<String>();

		for (String[] s : names) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				names1.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}

		model.put("details", salesReturnsService
				.getdetailsOfsalesReturnsName(salesEntryPojo));
		model.put("names", names1);
		System.out.println(model);
		return new ModelAndView("salesReturnsListDetails", model);
	}

	@RequestMapping("/salesEntryReturnsFirst2")
	public ModelAndView getRegisterForm2(
			@ModelAttribute("salesEntryPojo") SalesEntryPojo salesEntryPojo,
			BindingResult result) {

		ArrayList<String> customerType = new ArrayList<String>();
		customerType.add(" " + "InPatient" + " ");
		customerType.add(" " + "OutPatient" + " ");
		customerType.add(" " + "Others" + " ");
		ArrayList<String> billingType = new ArrayList<String>();
		billingType.add("MRP");
		billingType.add("ABC");
		billingType.add("DEF");
		billingType.add("GHI");
		billingType.add("JKL");
		billingType.add("MNO");
		ArrayList<String> billStatus = new ArrayList<String>();
		billStatus.add(" " + "Paid" + " ");
		billStatus.add(" " + "Not Paid" + " ");
		ArrayList<String> salesType = new ArrayList<String>();
		salesType.add("CASH");
		salesType.add("CHEQUE");

		ArrayList<String[]> productnameDb = new ArrayList<String[]>();
		productnameDb.add(salesReturnsService.getCustomerName(salesEntryPojo
				.getName()));
		ArrayList<String[]> customerName = new ArrayList<String[]>();
		customerName.add(salesReturnsService.getCustomerNameOnDb());
		ArrayList<String> productname = new ArrayList<String>();

		for (String[] s : productnameDb) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				productname.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}
		ArrayList<String> customerNameDb = new ArrayList<String>();
		for (String[] s : customerName) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				customerNameDb.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("productname", productname);
		model.put("customerType", customerType);
		model.put("billingType", billingType);
		model.put("billStatus", billStatus);
		model.put("salesType", salesType);
		model.put("customerName", customerNameDb);
		System.out.println(model);

		return new ModelAndView("salesReturnDetailsJsp", model);
	}

	@RequestMapping("/getProductdetailsSalesReturns")
	public ModelAndView getparticularproductDetails2(
			@ModelAttribute("salesEntryPojo") SalesEntryPojo salesEntryPojo,
			BindingResult result) {

		ArrayList<String> customerType = new ArrayList<String>();
		customerType.add(" " + "InPatient" + " ");
		customerType.add(" " + "OutPatient" + " ");
		customerType.add(" " + "Others" + " ");
		ArrayList<Double> qty = new ArrayList<Double>();

		qty.add(salesReturnsService.getQty(salesEntryPojo.getProductName(),
				salesEntryPojo.getName()));

		ArrayList<String> billingType = new ArrayList<String>();
		billingType.add("MRP");
		billingType.add("ABC");
		billingType.add("DEF");
		billingType.add("GHI");
		billingType.add("JKL");
		billingType.add("MNO");
		ArrayList<String> billStatus = new ArrayList<String>();
		billStatus.add(" " + "Paid" + " ");
		billStatus.add(" " + "Not Paid" + " ");
		ArrayList<String> salesType = new ArrayList<String>();
		salesType.add("CASH");
		salesType.add("CHEQUE");
		ArrayList<Double> totalAmount = new ArrayList<Double>();
		totalAmount.add(salesReturnsService.gettotalAmount());
		ArrayList<String[]> productnameDb = new ArrayList<String[]>();
		productnameDb.add(salesReturnsService.getCustomerName(salesEntryPojo
				.getName()));
		ArrayList<String[]> customerName = new ArrayList<String[]>();
		customerName.add(salesReturnsService.getCustomerNameOnDb());
		ArrayList<String> productname = new ArrayList<String>();
		for (String[] s : productnameDb) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				productname.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}
		ArrayList<String> customerNameDb = new ArrayList<String>();
		for (String[] s : customerName) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				customerNameDb.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}
		ArrayList<String> customerParticularName = new ArrayList<String>();
		customerParticularName.add(salesEntryPojo.getName());

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("productname", productname);
		model.put("customerType", customerType);
		model.put("billingType", billingType);
		model.put("billStatus", billStatus);

		model.put("qty", qty);

		model.put("salesType", salesType);
		model.put("customerName", customerNameDb);
		model.put("totalAmount", totalAmount);
		System.out.println(salesEntryPojo.getProductName());
		model.put("productRelatedDetails",
				salesReturnsService.getProductRelateddetails(salesEntryPojo));
		model.put("getAddingProductDetails",
				salesReturnsService.getAddingProductDetails());

		model.put("salesEntryPojogettingnames", salesReturnsService
				.getsalesPersonNames(salesEntryPojo.getName()));
		model.put("customerParticularName", customerParticularName);

		System.out.println(model);

		return new ModelAndView("salesReturnDetailsJsp2", model);
	}

	@RequestMapping(value = "/getProductdetailsSalesReturns", params = { "add" })
	public ModelAndView getparticularproductDetailsADD2(
			@ModelAttribute("salesEntryPojo") SalesEntryPojo salesEntryPojo,
			BindingResult result) {

		salesReturnsService.addproductDetails(salesEntryPojo);
		ArrayList<String> customerType = new ArrayList<String>();
		customerType.add(" " + "InPatient" + " ");
		customerType.add(" " + "OutPatient" + " ");
		customerType.add(" " + "Others" + " ");
		ArrayList<Double> totalAmount = new ArrayList<Double>();
		totalAmount.add(salesReturnsService.gettotalAmount());

		ArrayList<String> billingType = new ArrayList<String>();
		billingType.add("MRP");
		billingType.add("ABC");
		billingType.add("DEF");
		billingType.add("GHI");
		billingType.add("JKL");
		billingType.add("MNO");
		ArrayList<String> billStatus = new ArrayList<String>();
		billStatus.add(" " + "Paid" + " ");
		billStatus.add(" " + "Not Paid" + " ");
		ArrayList<String> salesType = new ArrayList<String>();
		salesType.add("CASH");
		salesType.add("CHEQUE");
		ArrayList<String[]> productnameDb = new ArrayList<String[]>();
		productnameDb.add(salesReturnsService.getCustomerName(salesEntryPojo
				.getName()));
		ArrayList<String[]> customerName = new ArrayList<String[]>();
		customerName.add(salesReturnsService.getCustomerNameOnDb());
		ArrayList<String> productname = new ArrayList<String>();
		for (String[] s : productnameDb) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				productname.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}
		ArrayList<String> customerNameDb = new ArrayList<String>();
		for (String[] s : customerName) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				customerNameDb.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("productname", productname);
		model.put("customerType", customerType);
		model.put("billingType", billingType);
		model.put("billStatus", billStatus);
		model.put("salesType", salesType);
		model.put("customerName", customerNameDb);
		model.put("totalAmount", totalAmount);
		model.put("productRelatedDetails",
				salesReturnsService.getProductRelateddetails(salesEntryPojo));
		model.put("getAddingProductDetails",
				salesReturnsService.getAddingProductDetails());
		System.out.println(model);
		System.out.println(salesEntryPojo.getCustomerId());
		System.out.println("add");

		return new ModelAndView("salesReturnDetailsJsp2", model);
	}

	@RequestMapping(value = "/getProductdetailsSalesReturns", params = { "total" })
	public ModelAndView totalbilling2(
			@ModelAttribute("salesEntryPojo") SalesEntryPojo salesEntryPojo,
			BindingResult result) {

		salesReturnsService.totalBilling(salesEntryPojo);

		return new ModelAndView("redirect:/salesreturnsdetailsList446.html");
	}

	@RequestMapping(value = "/cancessalesReturnsdetails")
	public ModelAndView cancessalesReturnsdetails(
			@ModelAttribute("salesEntryPojo") SalesEntryPojo salesEntryPojo,
			BindingResult result) {

		salesReturnsService.cancessalesReturns();

		return new ModelAndView("redirect:/salesreturnsdetailsList446.html");
	}

	@RequestMapping(value = "/deleteSalesRetursListDetails")
	public ModelAndView deleteSalesRetursListDetails(
			@ModelAttribute("salesEntryPojo") SalesEntryPojo salesEntryPojo,
			BindingResult result) {
		System.out.println("am in delete controller");
		System.out.println(salesEntryPojo.getSalesid());
		salesReturnsService.deleteSales(salesEntryPojo.getSalesReturnsid());

		ArrayList<String> customerType = new ArrayList<String>();
		customerType.add(" " + "InPatient" + " ");
		customerType.add(" " + "OutPatient" + " ");
		customerType.add(" " + "Others" + " ");
		ArrayList<Double> totalAmount = new ArrayList<Double>();
		totalAmount.add(salesReturnsService.gettotalAmount());

		ArrayList<String> billingType = new ArrayList<String>();
		billingType.add("MRP");
		billingType.add("ABC");
		billingType.add("DEF");
		billingType.add("GHI");
		billingType.add("JKL");
		billingType.add("MNO");
		ArrayList<String> billStatus = new ArrayList<String>();
		billStatus.add(" " + "Paid" + " ");
		billStatus.add(" " + "Not Paid" + " ");
		ArrayList<String> salesType = new ArrayList<String>();
		salesType.add("CASH");
		salesType.add("CHEQUE");
		ArrayList<String[]> productnameDb = new ArrayList<String[]>();
		productnameDb.add(salesReturnsService.getCustomerName(salesEntryPojo
				.getName()));
		ArrayList<String[]> customerName = new ArrayList<String[]>();
		customerName.add(salesReturnsService.getCustomerNameOnDb());
		ArrayList<String> productname = new ArrayList<String>();
		for (String[] s : productnameDb) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				productname.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}
		ArrayList<String> customerNameDb = new ArrayList<String>();
		for (String[] s : customerName) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				customerNameDb.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("productname", productname);
		model.put("customerType", customerType);
		model.put("billingType", billingType);
		model.put("billStatus", billStatus);
		model.put("salesType", salesType);
		model.put("customerName", customerNameDb);
		model.put("totalAmount", totalAmount);
		model.put("productRelatedDetails",
				salesReturnsService.getProductRelateddetails(salesEntryPojo));
		model.put("getAddingProductDetails",
				salesReturnsService.getAddingProductDetails());
		System.out.println(model);
		System.out.println(salesEntryPojo.getCustomerId());
		System.out.println("add");

		return new ModelAndView("salesReturnDetailsJsp2", model);
	}

	@RequestMapping("/getProductdetailsSalesReturnsName")
	public ModelAndView getProductdetailsName2(
			@ModelAttribute("salesEntryPojo") SalesEntryPojo salesEntryPojo,
			BindingResult result) {

		ArrayList<String> customerType = new ArrayList<String>();
		customerType.add(" " + "InPatient" + " ");
		customerType.add(" " + "OutPatient" + " ");
		customerType.add(" " + "Others" + " ");
		ArrayList<String> billingType = new ArrayList<String>();
		billingType.add("MRP");
		billingType.add("ABC");
		billingType.add("DEF");
		billingType.add("GHI");
		billingType.add("JKL");
		billingType.add("MNO");
		ArrayList<String> billStatus = new ArrayList<String>();
		billStatus.add(" " + "Paid" + " ");
		billStatus.add(" " + "Not Paid" + " ");
		ArrayList<String> salesType = new ArrayList<String>();
		salesType.add("CASH");
		salesType.add("CHEQUE");

		ArrayList<String[]> productnameDb = new ArrayList<String[]>();
		productnameDb.add(salesReturnsService.getCustomerName(salesEntryPojo
				.getName()));
		ArrayList<String[]> customerName = new ArrayList<String[]>();
		customerName.add(salesReturnsService.getCustomerNameOnDb());
		ArrayList<String> productname = new ArrayList<String>();

		for (String[] s : productnameDb) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				productname.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}
		ArrayList<String> customerNameDb = new ArrayList<String>();
		for (String[] s : customerName) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				customerNameDb.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}

		ArrayList<String> customerParticularName = new ArrayList<String>();
		customerParticularName.add(salesEntryPojo.getName());

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("productname", productname);
		model.put("customerType", customerType);
		model.put("billingType", billingType);
		model.put("billStatus", billStatus);
		model.put("salesType", salesType);
		model.put("customerName", customerNameDb);

		model.put("salesEntryPojogettingnames", salesReturnsService
				.getsalesPersonNames(salesEntryPojo.getName()));
		model.put("customerParticularName", customerParticularName);


		return new ModelAndView("salesReturnDetailsJsp3", model);
	}

	/* purchASE INVOICE */

	@Autowired
	private PurchaseInvoiceService purchaseInvoiceService;

	@RequestMapping(value = "/FirstPurchaseInvoice")
	public ModelAndView viewStckDetails(
			@ModelAttribute("purchaseInvoiceePojo") PurchaseInvoiceePojo purchaseInvoiceePojo,
			BindingResult result) {
		ArrayList<String> ricivedby = new ArrayList<String>();
		ricivedby.add("Reception");
		ricivedby.add("Admin");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("user", purchaseInvoiceService.GetDetails());

		System.out.println(model);
		return new ModelAndView("purchaselist1", model);
	}

	@RequestMapping(value = "/searchSuppliername")
	public ModelAndView viewStckDetailsi(
			@ModelAttribute("purchaseInvoiceePojo") PurchaseInvoiceePojo purchaseInvoiceePojo,
			BindingResult result) {
		ArrayList<String> ricivedby = new ArrayList<String>();
		ricivedby.add("Reception");
		ricivedby.add("Admin");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("user", purchaseInvoiceService
				.GetDetailsOfSuplierDetails(purchaseInvoiceePojo
						.getSupplierName()));
		System.out.println(model);
		return new ModelAndView("purchaselist1", model);
	}

	@RequestMapping("/addPurchaseInvoiceFirstFirst")
	public ModelAndView getRegisterForm(
			@ModelAttribute("purchaseInvoiceePojo") PurchaseInvoiceePojo purchaseInvoiceePojo,
			BindingResult result) {

		ArrayList<String> purchaseType = new ArrayList<String>();
		purchaseType.add("CASH");
		purchaseType.add("CHEQUE");
		ArrayList<String[]> supplierName = new ArrayList<String[]>();
		supplierName.add(purchaseInvoiceService.getSuppliername());
		/*
		 * ArrayList<String[]> supplierCode = new ArrayList<String[]>();
		 * supplierCode.add(purchaseInvoiceService.getSupplierCode());
		 */
		ArrayList<String> grnNo = new ArrayList<String>();
		grnNo.add(purchaseInvoiceService.getgrnMaxId());

		ArrayList<String[]> productnameDb = new ArrayList<String[]>();
		productnameDb.add(salesEntryService.getCustomerName());
		ArrayList<Integer> customerId = new ArrayList<Integer>();
		customerId.add(salesEntryService.getCustomerMaxId());
		ArrayList<String> productname = new ArrayList<String>();
		for (String[] s : productnameDb) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				productname.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}
		ArrayList<String[]> productName = new ArrayList<String[]>();
		productName.add(purchaseInvoiceService.getProductname());

		ArrayList<String> supplierNameDb = new ArrayList<String>();
		ArrayList<String> supplierCodeDb = new ArrayList<String>();
		ArrayList<String> productNameDb = new ArrayList<String>();
		for (String[] s : supplierName) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				supplierNameDb.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}

		/*
		 * for (String[] s : supplierCode) { int i = s.length; for (int j = 0; j
		 * < i; j++) { System.out.println(s.length); supplierCodeDb.add(s[j]);
		 * System.out.println(j + " " + s[j]); System.out.println(
		 * "am in controller retrive for each cusomerName"); } }
		 */
		for (String[] s : productName) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				productNameDb.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("supplierNameDb", supplierNameDb);
		/* model.put("supplierCodeDb", supplierCodeDb); */
		model.put("purchaseType", purchaseType);
		model.put("grnNo", grnNo);
		model.put("productNameDb", productNameDb);

		ArrayList<String> invoiceMax = new ArrayList<String>();
		invoiceMax.add(purchaseInvoiceService.invoiceMaxNumber());
		model.put("invoiceMax", invoiceMax);
		System.out.println(model);

		return new ModelAndView("purchaseinvoiseDetailsJsp1", model);
	}

	@RequestMapping("/getProductdetails123456")
	public ModelAndView getSupplierDetails(
			@ModelAttribute("purchaseInvoiceePojo") PurchaseInvoiceePojo purchaseInvoiceePojo,
			BindingResult result) {
		ArrayList<String> supplierAddress = new ArrayList<String>();
		supplierAddress.add(purchaseInvoiceService
				.getsupplierAddress(purchaseInvoiceePojo));
		ArrayList<String> supplierCity = new ArrayList<String>();
		supplierCity.add(purchaseInvoiceService
				.getsupplierCity(purchaseInvoiceePojo));
		ArrayList<String> SupplierName = new ArrayList<String>();
		SupplierName.add(purchaseInvoiceePojo.getSupplierName());

		ArrayList<String> purchaseType = new ArrayList<String>();
		purchaseType.add("CASH");
		purchaseType.add("CHEQUE");
		ArrayList<String[]> supplierName = new ArrayList<String[]>();
		supplierName.add(purchaseInvoiceService.getSuppliername());
		/*
		 * ArrayList<String[]> supplierCode = new ArrayList<String[]>();
		 * supplierCode.add(purchaseInvoiceService.getSupplierCode());
		 */
		ArrayList<String> grnNo = new ArrayList<String>();
		grnNo.add(purchaseInvoiceService.getgrnMaxId());
		ArrayList<String[]> productName = new ArrayList<String[]>();
		productName.add(purchaseInvoiceService.getProductname());
		ArrayList<String> supplierNameDb = new ArrayList<String>();
		ArrayList<String> supplierCodeDb = new ArrayList<String>();
		ArrayList<String> productNameDb = new ArrayList<String>();
		for (String[] s : supplierName) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				supplierNameDb.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}

		/*
		 * for (String[] s : supplierCode) { int i = s.length; for (int j = 0; j
		 * < i; j++) { System.out.println(s.length); supplierCodeDb.add(s[j]);
		 * System.out.println(j + " " + s[j]); System.out.println(
		 * "am in controller retrive for each cusomerName"); } }
		 */
		for (String[] s : productName) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				productNameDb.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}

		ArrayList<String[]> packingTypesnames = new ArrayList<String[]>();
		packingTypesnames.add(salesEntryService.getpackingTypesnamesDb());
		ArrayList<String> packingTypesnamesDb = new ArrayList<String>();
		for (String[] s : packingTypesnames) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				packingTypesnamesDb.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("supplierNameDb", supplierNameDb);
		model.put("supplierCodeDb", supplierCodeDb);
		model.put("purchaseType", purchaseType);
		model.put("grnNo", grnNo);
		model.put("productNameDb", productNameDb);
		model.put("supplierAddress", supplierAddress);
		model.put("supplierCity", supplierCity);
		model.put("SupplierName", SupplierName);
		model.put("packingTypesnames", packingTypesnamesDb);
		ArrayList<String> invoiceMax = new ArrayList<String>();
		invoiceMax.add(purchaseInvoiceService.invoiceMaxNumber());
		model.put("invoiceMax", invoiceMax);

		model.put("getProductDetails",
				purchaseInvoiceService.getgetProductDetails());
		System.out.println(model);

		return new ModelAndView("purchaseinvoiseDetailsJsp2", model);
	}

	@RequestMapping(value = "/getProductdetails123456", params = { "add" })
	public ModelAndView getproductDetails(
			@ModelAttribute("purchaseInvoiceePojo") PurchaseInvoiceePojo purchaseInvoiceePojo,
			BindingResult result) {

		ArrayList<String> supplierAddress = new ArrayList<String>();
		supplierAddress.add(purchaseInvoiceService
				.getsupplierAddress(purchaseInvoiceePojo.getSupplierName()));
		ArrayList<String> supplierCity = new ArrayList<String>();
		supplierCity.add(purchaseInvoiceService
				.getsupplierCity(purchaseInvoiceePojo.getSupplierName()));
		ArrayList<String> SupplierName = new ArrayList<String>();
		SupplierName.add(purchaseInvoiceePojo.getSupplierName());

		purchaseInvoiceService.saveParticularproducts(purchaseInvoiceePojo);

		ArrayList<String> purchaseType = new ArrayList<String>();
		purchaseType.add("CASH");
		purchaseType.add("CHEQUE");
		ArrayList<String[]> supplierName = new ArrayList<String[]>();
		supplierName.add(purchaseInvoiceService.getSuppliername());
		/*
		 * ArrayList<String[]> supplierCode = new ArrayList<String[]>();
		 * supplierCode.add(purchaseInvoiceService.getSupplierCode());
		 */
		ArrayList<String> grnNo = new ArrayList<String>();
		grnNo.add(purchaseInvoiceService.getgrnMaxId());
		ArrayList<String[]> productName = new ArrayList<String[]>();
		productName.add(purchaseInvoiceService.getProductname());
		ArrayList<String> supplierNameDb = new ArrayList<String>();
		ArrayList<String> supplierCodeDb = new ArrayList<String>();
		ArrayList<String> productNameDb = new ArrayList<String>();
		for (String[] s : supplierName) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				supplierNameDb.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}

		/*
		 * for (String[] s : supplierCode) { int i = s.length; for (int j = 0; j
		 * < i; j++) { System.out.println(s.length); supplierCodeDb.add(s[j]);
		 * System.out.println(j + " " + s[j]); System.out.println(
		 * "am in controller retrive for each cusomerName"); } }
		 */
		for (String[] s : productName) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				productNameDb.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}
		ArrayList<String[]> packingTypesnames = new ArrayList<String[]>();
		packingTypesnames.add(salesEntryService.getpackingTypesnamesDb());
		ArrayList<String> packingTypesnamesDb = new ArrayList<String>();
		for (String[] s : packingTypesnames) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				packingTypesnamesDb.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("supplierNameDb", supplierNameDb);
		model.put("supplierCodeDb", supplierCodeDb);
		model.put("purchaseType", purchaseType);
		model.put("grnNo", grnNo);
		model.put("productNameDb", productNameDb);
		model.put("supplierAddress", supplierAddress);
		model.put("supplierCity", supplierCity);
		model.put("SupplierName", SupplierName);
		model.put("packingTypesnames", packingTypesnamesDb);
		ArrayList<String> invoiceMax = new ArrayList<String>();
		invoiceMax.add(purchaseInvoiceService.invoiceMaxNumber());
		model.put("invoiceMax", invoiceMax);

		model.put("getProductDetails",
				purchaseInvoiceService.getgetProductDetails());
		ArrayList<Double> totalamount = new ArrayList<Double>();
		totalamount.add(purchaseInvoiceService.getTotalAmount());
		model.put("totalamount", totalamount);
		ArrayList<Integer> noOfItems = new ArrayList<Integer>();
		noOfItems.add(purchaseInvoiceService.getTotalNoOfItems());
		model.put("noOfItems", noOfItems);
		System.out.println(model);

		return new ModelAndView("purchaseinvoiseDetailsJsp2", model);

	}

	@RequestMapping(value = "/getProductdetails123456", params = { "total" })
	public ModelAndView totalSavingBill(
			@ModelAttribute("purchaseInvoiceePojo") PurchaseInvoiceePojo purchaseInvoiceePojo,
			BindingResult result) {

		purchaseInvoiceService.totalSave(purchaseInvoiceePojo);

		return new ModelAndView(
				"redirect:/FirstPurchaseInvoiceOfterSavingMsag.html");
	}

	@RequestMapping(value = "/FirstPurchaseInvoiceOfterSavingMsag")
	public ModelAndView FirstPurchaseInvoiceOfterSavingMsag(
			@ModelAttribute("purchaseInvoiceePojo") PurchaseInvoiceePojo purchaseInvoiceePojo,
			BindingResult result) {
		ArrayList<String> ricivedby = new ArrayList<String>();
		ricivedby.add("Reception");
		ricivedby.add("Admin");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("user", purchaseInvoiceService.GetDetails());
		System.out.println(model);
		ArrayList<String> msg = new ArrayList<String>();
		msg.add("Record Inserted Sucessfully");
		model.put("msg", msg);
		return new ModelAndView("purchaselist1", model);
	}

	@RequestMapping(value = "/cancelPurchaseInvoicedetilas")
	public ModelAndView cancelPurchaseInvoicedetilas(
			@ModelAttribute("purchaseInvoiceePojo") PurchaseInvoiceePojo purchaseInvoiceePojo,
			BindingResult result) {

		purchaseInvoiceService.cancelPurchaseInvoicedetilas();

		return new ModelAndView("redirect:/FirstPurchaseInvoice.html");
	}

	@RequestMapping(value = "/deletePurchaseInvoiceListDetails")
	public ModelAndView deletepurchaseInvoice(
			@ModelAttribute("purchaseInvoiceePojo") PurchaseInvoiceePojo purchaseInvoiceePojo,
			BindingResult result) {
		purchaseInvoiceService.delete(purchaseInvoiceePojo.getPurchaseId());

		ArrayList<String> supplierAddress = new ArrayList<String>();
		supplierAddress.add(purchaseInvoiceService
				.getsupplierAddress(purchaseInvoiceePojo.getSupplierName()));
		ArrayList<String> supplierCity = new ArrayList<String>();
		supplierCity.add(purchaseInvoiceService
				.getsupplierCity(purchaseInvoiceePojo.getSupplierName()));
		ArrayList<String> SupplierName = new ArrayList<String>();
		SupplierName.add(purchaseInvoiceePojo.getSupplierName());

		ArrayList<String> purchaseType = new ArrayList<String>();
		purchaseType.add("CASH");
		purchaseType.add("CHEQUE");
		ArrayList<String[]> supplierName = new ArrayList<String[]>();
		supplierName.add(purchaseInvoiceService.getSuppliername());
		/*
		 * ArrayList<String[]> supplierCode = new ArrayList<String[]>();
		 * supplierCode.add(purchaseInvoiceService.getSupplierCode());
		 */
		ArrayList<String> grnNo = new ArrayList<String>();
		grnNo.add(purchaseInvoiceService.getgrnMaxId());
		ArrayList<String[]> productName = new ArrayList<String[]>();
		productName.add(purchaseInvoiceService.getProductname());
		ArrayList<String> supplierNameDb = new ArrayList<String>();
		ArrayList<String> supplierCodeDb = new ArrayList<String>();
		ArrayList<String> productNameDb = new ArrayList<String>();
		for (String[] s : supplierName) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				supplierNameDb.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}

		/*
		 * for (String[] s : supplierCode) { int i = s.length; for (int j = 0; j
		 * < i; j++) { System.out.println(s.length); supplierCodeDb.add(s[j]);
		 * System.out.println(j + " " + s[j]); System.out.println(
		 * "am in controller retrive for each cusomerName"); } }
		 */
		for (String[] s : productName) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				productNameDb.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}
		ArrayList<String[]> packingTypesnames = new ArrayList<String[]>();
		packingTypesnames.add(salesEntryService.getpackingTypesnamesDb());
		ArrayList<String> packingTypesnamesDb = new ArrayList<String>();
		for (String[] s : packingTypesnames) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				packingTypesnamesDb.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("supplierNameDb", supplierNameDb);
		model.put("supplierCodeDb", supplierCodeDb);
		model.put("purchaseType", purchaseType);
		model.put("grnNo", grnNo);
		model.put("productNameDb", productNameDb);
		model.put("supplierAddress", supplierAddress);
		model.put("supplierCity", supplierCity);
		model.put("SupplierName", SupplierName);
		model.put("packingTypesnames", packingTypesnamesDb);
		model.put("getProductDetails",
				purchaseInvoiceService.getgetProductDetails());

		ArrayList<String> invoiceMax = new ArrayList<String>();
		invoiceMax.add(purchaseInvoiceService.invoiceMaxNumber());
		model.put("invoiceMax", invoiceMax);

		ArrayList<Double> totalamount = new ArrayList<Double>();
		totalamount.add(purchaseInvoiceService.getTotalAmount());
		model.put("totalamount", totalamount);
		ArrayList<Integer> noOfItems = new ArrayList<Integer>();
		noOfItems.add(purchaseInvoiceService.getTotalNoOfItems());
		model.put("noOfItems", noOfItems);
		System.out.println(model);
		ArrayList<String> msg = new ArrayList<String>();
		msg.add("Record Deleted Sucessfully");
		model.put("msg", msg);
		return new ModelAndView("purchaseinvoiseDetailsJsp2", model);

	}

	/* PURCHASE RETURNS */

	@Autowired
	private PurchaseInvoiceReturnsService purchaseInvoiceReturnsService;

	@RequestMapping("/puchaseReturnsListDetailsFirst")
	public ModelAndView puchaseReturnsListFirst(
			@ModelAttribute("purchaseInvoiceePojo") PurchaseInvoiceeReturns purchaseInvoiceePojo,
			BindingResult result) {

		ArrayList<String[]> supplierName = new ArrayList<String[]>();
		supplierName
				.add(purchaseInvoiceReturnsService.getSuppliernameDetails());
		ArrayList<String> supplierNameDb = new ArrayList<String>();
		for (String[] s : supplierName) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				supplierNameDb.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("purchaseReturnList",
				purchaseInvoiceReturnsService.getPurchaseReturnsList());
		model.put("supplierNameDb", supplierNameDb);
		System.out.println(model);

		return new ModelAndView("PurchaseReturnList", model);
	}

	@RequestMapping("/searchBySupplierName")
	public ModelAndView searchBySupplierName(
			@ModelAttribute("purchaseInvoiceePojo") PurchaseInvoiceeReturns purchaseInvoiceePojo,
			BindingResult result) {
		ArrayList<String[]> supplierName = new ArrayList<String[]>();
		supplierName
				.add(purchaseInvoiceReturnsService.getSuppliernameDetails());
		ArrayList<String> supplierNameDb = new ArrayList<String>();
		for (String[] s : supplierName) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				supplierNameDb.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("purchaseReturnList", purchaseInvoiceReturnsService
				.getListBySupplierName(purchaseInvoiceePojo));
		model.put("supplierNameDb", supplierNameDb);
		System.out.println(model);
		return new ModelAndView("PurchaseReturnList", model);
	}

	@RequestMapping("/purchaseReturnsFirstDetailsPurchase")
	public ModelAndView getRegisterForm(
			@ModelAttribute("purchaseInvoiceePojo") PurchaseInvoiceeReturns purchaseInvoiceePojo,
			BindingResult result) {

		ArrayList<String> purchaseType = new ArrayList<String>();
		purchaseType.add("CASH");
		purchaseType.add("CHEQUE");
		ArrayList<String[]> supplierName = new ArrayList<String[]>();
		supplierName.add(purchaseInvoiceReturnsService.getSuppliername());
		/*
		 * ArrayList<String[]> supplierCode = new ArrayList<String[]>();
		 * supplierCode.add(purchaseInvoiceReturnsService.getSupplierCode());
		 */
		ArrayList<String> grnNo = new ArrayList<String>();
		grnNo.add(purchaseInvoiceReturnsService.getgrnMaxId());

		ArrayList<String[]> productName = new ArrayList<String[]>();
		productName.add(purchaseInvoiceReturnsService
				.getProductname(purchaseInvoiceePojo));

		ArrayList<String> supplierNameDb = new ArrayList<String>();
		ArrayList<String> supplierCodeDb = new ArrayList<String>();
		ArrayList<String> productNameDb = new ArrayList<String>();
		for (String[] s : supplierName) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				supplierNameDb.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}
		/*
		 * for (String[] s : supplierCode) { int i = s.length; for (int j = 0; j
		 * < i; j++) { System.out.println(s.length); supplierCodeDb.add(s[j]);
		 * System.out.println(j + " " + s[j]); System.out.println(
		 * "am in controller retrive for each cusomerName"); } }
		 */
		for (String[] s : productName) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				productNameDb.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("supplierNameDb", supplierNameDb);
		model.put("supplierCodeDb", supplierCodeDb);
		model.put("purchaseType", purchaseType);
		model.put("grnNo", grnNo);
		model.put("productNameDb", productNameDb);

		ArrayList<String> SupplierNameDeatls = new ArrayList<String>();
		SupplierNameDeatls.add(purchaseInvoiceePojo.getSupplierName());
		model.put("SupplierNameDeatls", SupplierNameDeatls);

		System.out.println(model);

		return new ModelAndView("purchaseinvoiceReturns", model);
	}

	@RequestMapping("/getProductdetailsPurchaseReturns")
	public ModelAndView getSupplierDetails(
			@ModelAttribute("purchaseInvoiceePojo") PurchaseInvoiceeReturns purchaseInvoiceePojo,
			BindingResult result) {

		ArrayList<String> supplierAddress = new ArrayList<String>();
		supplierAddress.add(purchaseInvoiceReturnsService
				.getsupplierAddress(purchaseInvoiceePojo));
		ArrayList<String> supplierCity = new ArrayList<String>();
		supplierCity.add(purchaseInvoiceReturnsService
				.getsupplierCity(purchaseInvoiceePojo));
		ArrayList<String> SupplierName = new ArrayList<String>();
		SupplierName.add(purchaseInvoiceePojo.getSupplierName());

		ArrayList<String> purchaseTypegetting = new ArrayList<String>();
		purchaseTypegetting.add(purchaseInvoiceReturnsService
				.purchaseTypegetting(purchaseInvoiceePojo));
		ArrayList<String> invoicenumber = new ArrayList<String>();
		invoicenumber.add(purchaseInvoiceReturnsService
				.invoicenumber(purchaseInvoiceePojo));
		ArrayList<String> invoiceDate = new ArrayList<String>();
		invoiceDate.add(purchaseInvoiceReturnsService
				.invoiceDate(purchaseInvoiceePojo));
		ArrayList<String> purchaseDate = new ArrayList<String>();
		purchaseDate.add(purchaseInvoiceReturnsService
				.purchaseDate(purchaseInvoiceePojo));

		ArrayList<String> purchaseType = new ArrayList<String>();
		purchaseType.add("CASH");
		purchaseType.add("CHEQUE");
		ArrayList<String[]> supplierName = new ArrayList<String[]>();
		supplierName.add(purchaseInvoiceReturnsService.getSuppliername());
		/*
		 * ArrayList<String[]> supplierCode = new ArrayList<String[]>();
		 * supplierCode.add(purchaseInvoiceReturnsService.getSupplierCode());
		 */
		ArrayList<String> grnNo = new ArrayList<String>();
		grnNo.add(purchaseInvoiceReturnsService.getgrnMaxId());
		ArrayList<String[]> productName = new ArrayList<String[]>();
		productName.add(purchaseInvoiceReturnsService
				.getProductname(purchaseInvoiceePojo));
		ArrayList<String> supplierNameDb = new ArrayList<String>();
		ArrayList<String> supplierCodeDb = new ArrayList<String>();
		ArrayList<String> productNameDb = new ArrayList<String>();
		for (String[] s : supplierName) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				supplierNameDb.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}

		/*
		 * for (String[] s : supplierCode) { int i = s.length; for (int j = 0; j
		 * < i; j++) { System.out.println(s.length); supplierCodeDb.add(s[j]);
		 * System.out.println(j + " " + s[j]); System.out.println(
		 * "am in controller retrive for each cusomerName"); } }
		 */
		for (String[] s : productName) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				productNameDb.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("supplierNameDb", supplierNameDb);
		model.put("supplierCodeDb", supplierCodeDb);
		model.put("purchaseType", purchaseType);
		model.put("grnNo", grnNo);
		model.put("productNameDb", productNameDb);
		model.put("supplierAddress", supplierAddress);
		model.put("supplierCity", supplierCity);
		model.put("SupplierName", SupplierName);
		model.put("getProductDetails",
				purchaseInvoiceReturnsService.getgetProductDetails());

		model.put("purchaseTypegetting", purchaseTypegetting);
		model.put("invoicenumber", invoicenumber);
		model.put("invoiceDate", invoiceDate);
		model.put("purchaseDate", purchaseDate);

		ArrayList<String> SupplierNameDeatls = new ArrayList<String>();
		SupplierNameDeatls.add(purchaseInvoiceePojo.getSupplierName());
		model.put("SupplierNameDeatls", SupplierNameDeatls);

		System.out.println(model);

		return new ModelAndView("purchaseinvoiceReturns2", model);
	}

	@RequestMapping(value = "/getProductdetailsPurchaseInvoiceReturnsdetails123", params = { "add" })
	public ModelAndView getproductDetails(
			@ModelAttribute("purchaseInvoiceePojo") PurchaseInvoiceeReturns purchaseInvoiceePojo,
			BindingResult result) {
		System.out.println("im in add");
		System.out.println("im in add");
		System.out.println("im in add");
		System.out.println("im in add");
		System.out.println("im in add");
		System.out.println("im in add");
		ArrayList<String> supplierAddress = new ArrayList<String>();
		supplierAddress.add(purchaseInvoiceReturnsService
				.getsupplierAddress(purchaseInvoiceePojo.getSupplierName()));
		ArrayList<String> supplierCity = new ArrayList<String>();
		supplierCity.add(purchaseInvoiceReturnsService
				.getsupplierCity(purchaseInvoiceePojo.getSupplierName()));
		ArrayList<String> SupplierName = new ArrayList<String>();
		SupplierName.add(purchaseInvoiceePojo.getSupplierName());

		ArrayList<String> purchaseTypegetting = new ArrayList<String>();
		purchaseTypegetting.add(purchaseInvoiceReturnsService
				.purchaseTypegetting(purchaseInvoiceePojo));
		ArrayList<String> invoicenumber = new ArrayList<String>();
		invoicenumber.add(purchaseInvoiceReturnsService
				.invoicenumber(purchaseInvoiceePojo));
		ArrayList<String> invoiceDate = new ArrayList<String>();
		invoiceDate.add(purchaseInvoiceReturnsService
				.invoiceDate(purchaseInvoiceePojo));
		ArrayList<String> purchaseDate = new ArrayList<String>();
		purchaseDate.add(purchaseInvoiceReturnsService
				.purchaseDate(purchaseInvoiceePojo));

		purchaseInvoiceReturnsService
				.saveParticularproducts(purchaseInvoiceePojo);

		ArrayList<String> purchaseType = new ArrayList<String>();
		purchaseType.add("CASH");
		purchaseType.add("CHEQUE");
		ArrayList<String[]> supplierName = new ArrayList<String[]>();
		supplierName.add(purchaseInvoiceReturnsService.getSuppliername());

		ArrayList<String> grnNo = new ArrayList<String>();
		grnNo.add(purchaseInvoiceReturnsService.getgrnMaxId());
		ArrayList<String[]> productName = new ArrayList<String[]>();
		productName.add(purchaseInvoiceReturnsService
				.getProductname(purchaseInvoiceePojo));
		ArrayList<String> supplierNameDb = new ArrayList<String>();
		ArrayList<String> supplierCodeDb = new ArrayList<String>();
		ArrayList<String> productNameDb = new ArrayList<String>();
		for (String[] s : supplierName) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				supplierNameDb.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}

		for (String[] s : productName) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				productNameDb.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("supplierNameDb", supplierNameDb);
		model.put("supplierCodeDb", supplierCodeDb);
		model.put("purchaseType", purchaseType);
		model.put("grnNo", grnNo);
		model.put("productNameDb", productNameDb);
		model.put("supplierAddress", supplierAddress);
		model.put("supplierCity", supplierCity);
		model.put("SupplierName", SupplierName);
		model.put("getProductDetails",
				purchaseInvoiceReturnsService.getgetProductDetails());
		ArrayList<Double> totalamount = new ArrayList<Double>();
		totalamount.add(purchaseInvoiceReturnsService.getTotalAmount());
		model.put("totalamount", totalamount);
		ArrayList<Integer> noOfItems = new ArrayList<Integer>();
		noOfItems.add(purchaseInvoiceReturnsService.getTotalNoOfItems());
		model.put("noOfItems", noOfItems);

		model.put("purchaseTypegetting", purchaseTypegetting);
		model.put("invoicenumber", invoicenumber);
		model.put("invoiceDate", invoiceDate);
		model.put("purchaseDate", purchaseDate);

		ArrayList<String> SupplierNameDeatls = new ArrayList<String>();
		SupplierNameDeatls.add(purchaseInvoiceePojo.getSupplierName());
		model.put("SupplierNameDeatls", SupplierNameDeatls);
		System.out.println(model);
		return new ModelAndView("purchaseinvoiceReturns2", model);

	}

	@RequestMapping(value = "/deletePurchaseInvoiceReturnsListDetails")
	public ModelAndView deletePurchaseInvoiceReturnsListDetails(
			@ModelAttribute("purchaseInvoiceePojo") PurchaseInvoiceeReturns purchaseInvoiceePojo,
			BindingResult result) {

		ArrayList<String> supplierNameDetailsOs = new ArrayList<String>();
		supplierNameDetailsOs.add(purchaseInvoiceReturnsService
				.supplierNameDetailsOs(purchaseInvoiceePojo.getPurchaseId()));

		String name = "null";

		for (String s : supplierNameDetailsOs) {

			name = s;
		}

		purchaseInvoiceePojo.setSupplierName(name);

		purchaseInvoiceReturnsService.deleteDetailsOf(purchaseInvoiceePojo
				.getPurchaseId());

		ArrayList<String> supplierAddress = new ArrayList<String>();
		supplierAddress.add(purchaseInvoiceReturnsService
				.getsupplierAddress(purchaseInvoiceePojo.getSupplierName()));
		ArrayList<String> supplierCity = new ArrayList<String>();
		supplierCity.add(purchaseInvoiceReturnsService
				.getsupplierCity(purchaseInvoiceePojo.getSupplierName()));
		ArrayList<String> SupplierName = new ArrayList<String>();
		SupplierName.add(purchaseInvoiceePojo.getSupplierName());

		ArrayList<String> purchaseTypegetting = new ArrayList<String>();
		purchaseTypegetting.add(purchaseInvoiceReturnsService
				.purchaseTypegetting(purchaseInvoiceePojo));
		ArrayList<String> invoicenumber = new ArrayList<String>();
		invoicenumber.add(purchaseInvoiceReturnsService
				.invoicenumber(purchaseInvoiceePojo));
		ArrayList<String> invoiceDate = new ArrayList<String>();
		invoiceDate.add(purchaseInvoiceReturnsService
				.invoiceDate(purchaseInvoiceePojo));
		ArrayList<String> purchaseDate = new ArrayList<String>();
		purchaseDate.add(purchaseInvoiceReturnsService
				.purchaseDate(purchaseInvoiceePojo));

		ArrayList<String> purchaseType = new ArrayList<String>();
		purchaseType.add("CASH");
		purchaseType.add("CHEQUE");
		ArrayList<String[]> supplierName = new ArrayList<String[]>();
		supplierName.add(purchaseInvoiceReturnsService.getSuppliername());

		ArrayList<String> grnNo = new ArrayList<String>();
		grnNo.add(purchaseInvoiceReturnsService.getgrnMaxId());
		ArrayList<String[]> productName = new ArrayList<String[]>();
		productName.add(purchaseInvoiceReturnsService
				.getProductname(purchaseInvoiceePojo));
		ArrayList<String> supplierNameDb = new ArrayList<String>();
		ArrayList<String> supplierCodeDb = new ArrayList<String>();
		ArrayList<String> productNameDb = new ArrayList<String>();
		for (String[] s : supplierName) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				supplierNameDb.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}

		for (String[] s : productName) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				productNameDb.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("supplierNameDb", supplierNameDb);
		model.put("supplierCodeDb", supplierCodeDb);
		model.put("purchaseType", purchaseType);
		model.put("grnNo", grnNo);
		model.put("productNameDb", productNameDb);
		model.put("supplierAddress", supplierAddress);
		model.put("supplierCity", supplierCity);
		model.put("SupplierName", SupplierName);
		model.put("getProductDetails",
				purchaseInvoiceReturnsService.getgetProductDetails());
		ArrayList<Double> totalamount = new ArrayList<Double>();
		totalamount.add(purchaseInvoiceReturnsService.getTotalAmount());
		model.put("totalamount", totalamount);
		ArrayList<Integer> noOfItems = new ArrayList<Integer>();
		noOfItems.add(purchaseInvoiceReturnsService.getTotalNoOfItems());
		model.put("noOfItems", noOfItems);

		model.put("purchaseTypegetting", purchaseTypegetting);
		model.put("invoicenumber", invoicenumber);
		model.put("invoiceDate", invoiceDate);
		model.put("purchaseDate", purchaseDate);

		ArrayList<String> SupplierNameDeatls = new ArrayList<String>();
		SupplierNameDeatls.add(purchaseInvoiceePojo.getSupplierName());
		model.put("SupplierNameDeatls", SupplierNameDeatls);
		System.out.println(model);
		return new ModelAndView("purchaseinvoiceReturns2", model);

	}

	@RequestMapping(value = "/getProductdetailsPurchaseInvoiceReturnsdetails123")
	public ModelAndView getproductParticularDetails(
			@ModelAttribute("purchaseInvoiceePojo") PurchaseInvoiceeReturns purchaseInvoiceePojo,
			BindingResult result) {

		ArrayList<String> supplierAddress = new ArrayList<String>();
		supplierAddress.add(purchaseInvoiceReturnsService
				.getsupplierAddress(purchaseInvoiceePojo.getSupplierName()));
		ArrayList<String> supplierCity = new ArrayList<String>();
		supplierCity.add(purchaseInvoiceReturnsService
				.getsupplierCity(purchaseInvoiceePojo.getSupplierName()));
		ArrayList<String> SupplierName = new ArrayList<String>();
		SupplierName.add(purchaseInvoiceePojo.getSupplierName());
		ArrayList<String> purchaseTypegetting = new ArrayList<String>();
		purchaseTypegetting.add(purchaseInvoiceReturnsService
				.purchaseTypegetting(purchaseInvoiceePojo));
		ArrayList<String> invoicenumber = new ArrayList<String>();
		invoicenumber.add(purchaseInvoiceReturnsService
				.invoicenumber(purchaseInvoiceePojo));
		ArrayList<String> invoiceDate = new ArrayList<String>();
		invoiceDate.add(purchaseInvoiceReturnsService
				.invoiceDate(purchaseInvoiceePojo));
		ArrayList<String> purchaseDate = new ArrayList<String>();
		purchaseDate.add(purchaseInvoiceReturnsService
				.purchaseDate(purchaseInvoiceePojo));
		ArrayList<String> purchaseType = new ArrayList<String>();
		purchaseType.add("CASH");
		purchaseType.add("CHEQUE");
		ArrayList<String[]> supplierName = new ArrayList<String[]>();
		supplierName.add(purchaseInvoiceReturnsService.getSuppliername());

		ArrayList<String> grnNo = new ArrayList<String>();
		grnNo.add(purchaseInvoiceReturnsService.getgrnMaxId());
		ArrayList<String[]> productName = new ArrayList<String[]>();
		productName.add(purchaseInvoiceReturnsService
				.getProductname(purchaseInvoiceePojo));
		ArrayList<String> supplierNameDb = new ArrayList<String>();
		ArrayList<String> supplierCodeDb = new ArrayList<String>();
		ArrayList<String> productNameDb = new ArrayList<String>();
		for (String[] s : supplierName) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				supplierNameDb.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}

		for (String[] s : productName) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				productNameDb.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("supplierNameDb", supplierNameDb);
		model.put("supplierCodeDb", supplierCodeDb);
		model.put("purchaseType", purchaseType);
		model.put("grnNo", grnNo);
		model.put("productNameDb", productNameDb);
		model.put("supplierAddress", supplierAddress);
		model.put("supplierCity", supplierCity);
		model.put("SupplierName", SupplierName);
		model.put("getProductDetails",
				purchaseInvoiceReturnsService.getgetProductDetails());
		ArrayList<Double> totalamount = new ArrayList<Double>();
		totalamount.add(purchaseInvoiceReturnsService.getTotalAmount());
		model.put("totalamount", totalamount);
		ArrayList<Integer> noOfItems = new ArrayList<Integer>();
		noOfItems.add(purchaseInvoiceReturnsService.getTotalNoOfItems());
		model.put("noOfItems", noOfItems);
		model.put("purchaseTypegetting", purchaseTypegetting);
		model.put("invoicenumber", invoicenumber);
		model.put("invoiceDate", invoiceDate);
		model.put("purchaseDate", purchaseDate);

		ArrayList<String> SupplierNameDeatls = new ArrayList<String>();
		SupplierNameDeatls.add(purchaseInvoiceePojo.getSupplierName());
		model.put("SupplierNameDeatls", SupplierNameDeatls);

		ArrayList<String> manufacturingBy = new ArrayList<String>();
		manufacturingBy.add(purchaseInvoiceReturnsService
				.getproductmanufacturingBy(
						purchaseInvoiceePojo.getSupplierName(),
						purchaseInvoiceePojo.getProductName()));
		ArrayList<Double> vat = new ArrayList<Double>();
		vat.add(purchaseInvoiceReturnsService.getVat(
				purchaseInvoiceePojo.getSupplierName(),
				purchaseInvoiceePojo.getProductName()));
		ArrayList<Double> rate = new ArrayList<Double>();
		rate.add(purchaseInvoiceReturnsService.getrate(
				purchaseInvoiceePojo.getSupplierName(),
				purchaseInvoiceePojo.getProductName()));
		ArrayList<String> batchNo = new ArrayList<String>();
		batchNo.add(purchaseInvoiceReturnsService.getBatchNo(
				purchaseInvoiceePojo.getSupplierName(),
				purchaseInvoiceePojo.getProductName()));

		ArrayList<Double> quantity = new ArrayList<Double>();
		quantity.add(purchaseInvoiceReturnsService.getQuantity(
				purchaseInvoiceePojo.getSupplierName(),
				purchaseInvoiceePojo.getProductName()));

		model.put("quantity", quantity);
		model.put("manufacturingBy", manufacturingBy);
		model.put("vat", vat);
		model.put("rate", rate);
		model.put("batchNo", batchNo);

		return new ModelAndView("purchaseinvoiceReturns2", model);

	}

	@RequestMapping(value = "/getProductdetailsPurchaseInvoiceReturnsdetails123", params = { "total" })
	public ModelAndView totalSavingBill(
			@ModelAttribute("purchaseInvoiceePojo") PurchaseInvoiceeReturns purchaseInvoiceePojo,
			BindingResult result) {
		if (purchaseInvoiceePojo.getNoOfitems() >= 1) {
			purchaseInvoiceReturnsService.totalSave(purchaseInvoiceePojo);
			return new ModelAndView(
					"redirect:/puchaseReturnsListDetailsFirstOfterSavingmsg.html");
		} else {

			ArrayList<String> supplierAddress = new ArrayList<String>();
			supplierAddress
					.add(purchaseInvoiceReturnsService
							.getsupplierAddress(purchaseInvoiceePojo
									.getSupplierName()));
			ArrayList<String> supplierCity = new ArrayList<String>();
			supplierCity.add(purchaseInvoiceReturnsService
					.getsupplierCity(purchaseInvoiceePojo.getSupplierName()));
			ArrayList<String> SupplierName = new ArrayList<String>();
			SupplierName.add(purchaseInvoiceePojo.getSupplierName());

			ArrayList<String> purchaseTypegetting = new ArrayList<String>();
			purchaseTypegetting.add(purchaseInvoiceReturnsService
					.purchaseTypegetting(purchaseInvoiceePojo));
			ArrayList<String> invoicenumber = new ArrayList<String>();
			invoicenumber.add(purchaseInvoiceReturnsService
					.invoicenumber(purchaseInvoiceePojo));
			ArrayList<String> invoiceDate = new ArrayList<String>();
			invoiceDate.add(purchaseInvoiceReturnsService
					.invoiceDate(purchaseInvoiceePojo));
			ArrayList<String> purchaseDate = new ArrayList<String>();
			purchaseDate.add(purchaseInvoiceReturnsService
					.purchaseDate(purchaseInvoiceePojo));

			ArrayList<String> purchaseType = new ArrayList<String>();
			purchaseType.add("CASH");
			purchaseType.add("CHEQUE");
			ArrayList<String[]> supplierName = new ArrayList<String[]>();
			supplierName.add(purchaseInvoiceReturnsService.getSuppliername());

			ArrayList<String> grnNo = new ArrayList<String>();
			grnNo.add(purchaseInvoiceReturnsService.getgrnMaxId());
			ArrayList<String[]> productName = new ArrayList<String[]>();
			productName.add(purchaseInvoiceReturnsService
					.getProductname(purchaseInvoiceePojo));
			ArrayList<String> supplierNameDb = new ArrayList<String>();
			ArrayList<String> supplierCodeDb = new ArrayList<String>();
			ArrayList<String> productNameDb = new ArrayList<String>();
			for (String[] s : supplierName) {
				int i = s.length;
				for (int j = 0; j < i; j++) {
					System.out.println(s.length);
					supplierNameDb.add(s[j]);
					System.out.println(j + " " + s[j]);
					System.out
							.println("am in controller retrive for each cusomerName");
				}
			}

			for (String[] s : productName) {
				int i = s.length;
				for (int j = 0; j < i; j++) {
					System.out.println(s.length);
					productNameDb.add(s[j]);
					System.out.println(j + " " + s[j]);
					System.out
							.println("am in controller retrive for each cusomerName");
				}
			}

			Map<String, Object> model = new HashMap<String, Object>();
			model.put("supplierNameDb", supplierNameDb);
			model.put("supplierCodeDb", supplierCodeDb);
			model.put("purchaseType", purchaseType);
			model.put("grnNo", grnNo);
			model.put("productNameDb", productNameDb);
			model.put("supplierAddress", supplierAddress);
			model.put("supplierCity", supplierCity);
			model.put("SupplierName", SupplierName);
			model.put("getProductDetails",
					purchaseInvoiceReturnsService.getgetProductDetails());
			ArrayList<Double> totalamount = new ArrayList<Double>();
			totalamount.add(purchaseInvoiceReturnsService.getTotalAmount());
			model.put("totalamount", totalamount);
			ArrayList<Integer> noOfItems = new ArrayList<Integer>();
			noOfItems.add(purchaseInvoiceReturnsService.getTotalNoOfItems());
			model.put("noOfItems", noOfItems);

			model.put("purchaseTypegetting", purchaseTypegetting);
			model.put("invoicenumber", invoicenumber);
			model.put("invoiceDate", invoiceDate);
			model.put("purchaseDate", purchaseDate);

			ArrayList<String> msg = new ArrayList<String>();
			msg.add("Atleast Add One Retrun Product....");
			model.put("msg", msg);

			ArrayList<String> SupplierNameDeatls = new ArrayList<String>();
			SupplierNameDeatls.add(purchaseInvoiceePojo.getSupplierName());
			model.put("SupplierNameDeatls", SupplierNameDeatls);
			System.out.println(model);
			return new ModelAndView("purchaseinvoiceReturns2", model);

		}
	}

	@RequestMapping("/puchaseReturnsListDetailsFirstOfterSavingmsg")
	public ModelAndView puchaseReturnsListDetailsFirstOfterSavingmsg(
			@ModelAttribute("purchaseInvoiceePojo") PurchaseInvoiceeReturns purchaseInvoiceePojo,
			BindingResult result) {

		ArrayList<String[]> supplierName = new ArrayList<String[]>();
		supplierName
				.add(purchaseInvoiceReturnsService.getSuppliernameDetails());
		ArrayList<String> supplierNameDb = new ArrayList<String>();
		for (String[] s : supplierName) {
			int i = s.length;
			for (int j = 0; j < i; j++) {
				System.out.println(s.length);
				supplierNameDb.add(s[j]);
				System.out.println(j + " " + s[j]);
				System.out
						.println("am in controller retrive for each cusomerName");
			}
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("purchaseReturnList",
				purchaseInvoiceReturnsService.getPurchaseReturnsList());
		model.put("supplierNameDb", supplierNameDb);
		System.out.println(model);
		ArrayList<String> msg = new ArrayList<String>();
		msg.add("Record Inserted successfully");
		model.put("msg", msg);
		return new ModelAndView("PurchaseReturnList", model);
	}

	@RequestMapping(value = "/canelpurchaseReturnsdetails")
	public ModelAndView canelpurchaseReturnsdetails(
			@ModelAttribute("purchaseInvoiceePojo") PurchaseInvoiceeReturns purchaseInvoiceePojo,
			BindingResult result) {

		purchaseInvoiceReturnsService.canswelPurchaseReturns();

		return new ModelAndView("redirect:/puchaseReturnsListDetailsFirst.html");
	}

	/* BED DETAILS */

	@Autowired
	private BedService bedDetailsService;

	@RequestMapping(value = "/roomRegistration")
	public ModelAndView RoomsRegistarion(@ModelAttribute("room") RoomPojo room,
			BindingResult result) {

		Map<String, Object> model = new HashMap<String, Object>();
		ArrayList<String[]> locations = new ArrayList<String[]>();
		locations.add(bedDetailsService.getFloors());
		ArrayList<String> locations1 = new ArrayList<String>();

		for (String[] s1 : locations) {
			int i = s1.length;
			for (int j = 0; j < i; j++) {
				locations1.add(s1[j]);
			}
		}

		model.put("locations", locations1);

		ArrayList<String> bedtype = new ArrayList<String>();
		bedtype.add("single");
		bedtype.add("double");
		model.put("bedtype", bedtype);

		return new ModelAndView("RoomRegistrion", model);

	}

	@RequestMapping(value = "/searchbyRoomno1")
	public ModelAndView RoomsRegistarionSave(
			@ModelAttribute("room") RoomPojo room, BindingResult result) {

		bedDetailsService.saveRoomDetails(room);

		Map<String, Object> model = new HashMap<String, Object>();
		ArrayList<String[]> locations = new ArrayList<String[]>();
		locations.add(bedDetailsService.getFloors());

		ArrayList<String> message = new ArrayList<String>();
		message.add("Record Inserted Sucessfully");
		System.out.println(message);
		ArrayList<String> locations1 = new ArrayList<String>();

		for (String[] s1 : locations) {
			int i = s1.length;
			for (int j = 0; j < i; j++) {
				locations1.add(s1[j]);
			}
		}
		model.put("message", message);
		model.put("locations", locations1);
		ArrayList<String> bedtype = new ArrayList<String>();
		bedtype.add("single");
		bedtype.add("double");
		model.put("bedtype", bedtype);

		System.out.println(model);
		return new ModelAndView("redirect:/rePageDetails.html");

	}

	@RequestMapping(value = "/rePageDetails")
	public ModelAndView rePageDetails(@ModelAttribute("room") RoomPojo room,
			BindingResult result) {

		Map<String, Object> model = new HashMap<String, Object>();
		ArrayList<String[]> locations = new ArrayList<String[]>();
		locations.add(bedDetailsService.getFloors());

		ArrayList<String> message = new ArrayList<String>();
		message.add("Record Inserted Sucessfully");
		System.out.println(message);
		ArrayList<String> locations1 = new ArrayList<String>();

		for (String[] s1 : locations) {
			int i = s1.length;
			for (int j = 0; j < i; j++) {
				locations1.add(s1[j]);
			}
		}
		model.put("message", message);
		model.put("locations", locations1);
		ArrayList<String> bedtype = new ArrayList<String>();
		bedtype.add("single");
		bedtype.add("double");
		model.put("bedtype", bedtype);

		System.out.println(model);
		return new ModelAndView("RoomRegistrion", model);

	}

	@RequestMapping(value = "/beddetailsAdd")
	public ModelAndView AddDetails(@ModelAttribute("room") RoomPojo room,
			BedDetails deails, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		ArrayList<String[]> locations = new ArrayList<String[]>();
		locations.add(bedDetailsService.getFloors());

		ArrayList<String> locations1 = new ArrayList<String>();
		for (String[] s1 : locations) {
			int i = s1.length;
			for (int j = 0; j < i; j++) {
				locations1.add(s1[j]);
			}
		}
		model.put("locations", locations1);
		System.out.println(model);
		ArrayList<String[]> rommnos = new ArrayList<String[]>();
		rommnos.add(bedDetailsService.getRoomNos());
		ArrayList<String> rommnos1 = new ArrayList<String>();
		for (String[] s1 : rommnos) {
			int i = s1.length;
			for (int j = 0; j < i; j++) {
				rommnos1.add(s1[j]);
			}
		}
		System.out.println(rommnos1);
		model.put("rommnos", rommnos1);
		ArrayList<String> bedtype = new ArrayList<String>();
		bedtype.add("single");
		bedtype.add("double");
		model.put("bedtype", bedtype);

		return new ModelAndView("BedDetailsAdd", model);

	}

	@RequestMapping(value = "/getRoomDetails", params = { "add" })
	public ModelAndView beddetailsAddDetails(
			@ModelAttribute("room") RoomPojo room, BedDetails deails,
			BindingResult result) {

		System.out.println("am in add controller");

		boolean b = bedDetailsService.savesubtotal(room);

		ArrayList<String> msg1 = new ArrayList<String>();
		ArrayList<String> msg = new ArrayList<String>();
		if (b) {
			msg.add("Record Inserted Successfully");
		} else {
			msg1.add("BedNumber Already Exist / Beds Full in Room");
		}

		System.out.println("am in dropdown");
		Map<String, Object> model = new HashMap<String, Object>();
		ArrayList<String[]> rooms = new ArrayList<String[]>();
		rooms.add(bedDetailsService.getParticularRooms(room));
		ArrayList<String> rooms1 = new ArrayList<String>();
		for (String[] s1 : rooms) {
			int i = s1.length;
			for (int j = 0; j < i; j++) {
				rooms1.add(s1[j]);
			}
		}
		ArrayList<String[]> locations = new ArrayList<String[]>();
		locations.add(bedDetailsService.getFloors());
		ArrayList<String> locations1 = new ArrayList<String>();
		for (String[] s1 : locations) {
			int i = s1.length;
			for (int j = 0; j < i; j++) {
				locations1.add(s1[j]);
			}
		}
		model.put("locations", locations1);
		model.put("rommnos", rooms1);
		ArrayList<String> location = new ArrayList<String>();
		location.add(room.getFloorname());
		model.put("location", location);
		model.put(
				"roomDetails",
				bedDetailsService.getRoomDetails(room.getFloorname(),
						room.getRoomno()));
		model.put("getProductDetails", bedDetailsService.getProductDetails());
		ArrayList<String> bedtype = new ArrayList<String>();
		bedtype.add("single");
		bedtype.add("double");
		model.put("bedtype", bedtype);

		model.put("msg", msg);

		System.out.println(model);
		return new ModelAndView("BedDetailsAdd", model);

	}

	@RequestMapping(value = "/getRoomDetails", params = { "submit" })
	public ModelAndView totalSubmit(@ModelAttribute("room") RoomPojo room,
			BedDetails deails, BindingResult result) {

		bedDetailsService.savetotalValues(room);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("groundFlour", bedDetailsService.getGroundFlourDetails());
		model.put("firstFlour", bedDetailsService.getFirstFlourdetails());
		model.put("secondFlour", bedDetailsService.getSecondFlourDetails());
		model.put("thirdFlour", bedDetailsService.getThirdFlourDetails());
		model.put("fourthFlour", bedDetailsService.getFourthDetails());
		model.put("fifthFlour", bedDetailsService.getFifthFlourDetails());
		model.put("sixthFlour", bedDetailsService.getSixthFlourDetials());
		return new ModelAndView("BedDetailsSuccess", model);
	}

	@RequestMapping(value = "/firstBedTypePage")
	public ModelAndView FirsttotalSubmit(@ModelAttribute("room") RoomPojo room,
			BedDetails deails, BindingResult result) {

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("groundFlour", bedDetailsService.getGroundFlourDetails());
		model.put("firstFlour", bedDetailsService.getFirstFlourdetails());
		model.put("secondFlour", bedDetailsService.getSecondFlourDetails());
		model.put("thirdFlour", bedDetailsService.getThirdFlourDetails());
		model.put("fourthFlour", bedDetailsService.getFourthDetails());
		model.put("fifthFlour", bedDetailsService.getFifthFlourDetails());
		model.put("sixthFlour", bedDetailsService.getSixthFlourDetials());
		return new ModelAndView("BedDetailsSuccess", model);
	}

	@RequestMapping(value = "/getRoomDetails", params = { "cancel" })
	public ModelAndView bedDetailsCancel(@ModelAttribute("room") RoomPojo room,
			BedDetails deails, BindingResult result) {

		bedDetailsService.bedDetailsCancel();

		return new ModelAndView("redirect:/beddetailsAddDetails.html");
	}

	@RequestMapping(value = "/beddetailsAddDetails")
	public ModelAndView beddetailsAddDetailsParticularRooms(
			@ModelAttribute("room") RoomPojo room, BedDetails deails,
			BindingResult result) {

		System.out.println("am in   dropdown");
		Map<String, Object> model = new HashMap<String, Object>();
		ArrayList<String[]> rooms = new ArrayList<String[]>();
		rooms.add(bedDetailsService.getParticularRooms(room));
		ArrayList<String> rooms1 = new ArrayList<String>();
		for (String[] s1 : rooms) {
			int i = s1.length;
			for (int j = 0; j < i; j++) {
				rooms1.add(s1[j]);
			}
		}
		ArrayList<String[]> locations = new ArrayList<String[]>();
		locations.add(bedDetailsService.getFloors());
		ArrayList<String> locations1 = new ArrayList<String>();
		for (String[] s1 : locations) {
			int i = s1.length;
			for (int j = 0; j < i; j++) {
				locations1.add(s1[j]);
			}
		}
		model.put("locations", locations1);
		model.put("rommnos", rooms1);
		ArrayList<String> location = new ArrayList<String>();
		location.add(room.getFloorname());
		model.put("location", location);
		ArrayList<String> bedtype = new ArrayList<String>();
		bedtype.add("single");
		bedtype.add("double");
		model.put("bedtype", bedtype);
		return new ModelAndView("BedDetailsAdd", model);

	}

	@RequestMapping(value = "/getRoomDetails")
	public ModelAndView getRoomDetails(@ModelAttribute("room") RoomPojo room,
			BedDetails deails, BindingResult result) {

		System.out.println("am in room dropdown");
		Map<String, Object> model = new HashMap<String, Object>();
		ArrayList<String[]> rooms = new ArrayList<String[]>();
		rooms.add(bedDetailsService.getParticularRooms(room));
		ArrayList<String> rooms1 = new ArrayList<String>();
		for (String[] s1 : rooms) {
			int i = s1.length;
			for (int j = 0; j < i; j++) {
				rooms1.add(s1[j]);
			}
		}
		ArrayList<String[]> locations = new ArrayList<String[]>();
		locations.add(bedDetailsService.getFloors());
		ArrayList<String> locations1 = new ArrayList<String>();
		for (String[] s1 : locations) {
			int i = s1.length;
			for (int j = 0; j < i; j++) {
				locations1.add(s1[j]);
			}
		}
		model.put("locations", locations1);
		model.put("rommnos", rooms1);
		ArrayList<String> location = new ArrayList<String>();
		location.add(room.getFloorname());
		model.put("location", location);
		model.put(
				"roomDetails",
				bedDetailsService.getRoomDetails(room.getFloorname(),
						room.getRoomno()));
		ArrayList<String> bedtype = new ArrayList<String>();
		bedtype.add("single");
		bedtype.add("double");
		model.put("bedtype", bedtype);

		System.out.println(model);

		return new ModelAndView("BedDetailsAdd", model);

	}

	// Sample Report
	String fileNam = "SalesEntrys2";
	HashMap hmParams = new HashMap();

	@RequestMapping(value = "/sampleReport")
	public ModelAndView generateSalesEntry(
			@ModelAttribute("user") SalesEntryPojo user, BindingResult result,
			Model model, HttpServletRequest request,
			HttpServletResponse response) throws ParseException {

		String reportFileName = "SalesEntrys2";

		try {
			try {

				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				System.out
						.println("Please include Classpath Where your MySQL Driver is located");
				e.printStackTrace();
			}

			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/SriLakshmiSuperSplhospital",
					"root", "root");

			if (conn != null) {
				System.out.println("Database Connected");
			} else {
				System.out.println(" connection Failed ");
			}

			ArrayList<String> al = new ArrayList<String>();
			al.add(user.getSalesDate());
			hmParams.put("dg", al);

			System.out.println(al);
			System.out.println(hmParams);
			hmParams.put("dateGetting", user.getSalesDate());
			JasperReport jasperReport = getCompiledFil(reportFileName, request);
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					jasperReport, hmParams, conn);
			// For HTML report
		} catch (Exception sqlExp) {

			System.out.println("Exception::" + sqlExp.toString());

		} finally {

			try {

				if (conn != null) {
					conn.close();
					conn = null;
				}

			} catch (SQLException expSQL) {

				System.out.println("SQLExp::CLOSING::" + expSQL.toString());

			}

		}

		return new ModelAndView("DateSearch");
	}

	private JasperReport getCompiledFil(String fileName,
			HttpServletRequest request) throws JRException {
		System.out.println("before compilation");
		File reportFile = new File(request.getSession().getServletContext()
				.getRealPath("/jasper/" + fileNam + ".jasper"));
		// If compiled file is not found, then compile XML template
		if (!reportFile.exists()) {
			JasperCompileManager.compileReportToFile(
					request.getSession().getServletContext()
							.getRealPath("/jasper/" + fileNam + ".jrxml"),
					request.getSession().getServletContext()
							.getRealPath("/jasper/" + fileNam + ".jasper"));
		}
		System.out.println("ofter compilation");

		JasperReport jasperReport = (JasperReport) JRLoader
				.loadObjectFromFile(reportFile.getPath());
		System.out.println("path "
				+ request.getSession().getServletContext()
						.getRealPath("/jasper/" + fileNam + ".jasper"));
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
				hmParams, conn);
		JasperViewer.viewReport(jasperPrint, false);

		System.out.println("123456789");
		return jasperReport;
	}

	@RequestMapping(value = "/searchreports")
	public ModelAndView Stringview(@ModelAttribute("user") SalesEntryPojo user) {
		System.out.println("inside date search method");
		return new ModelAndView("DateSearch");

	}

	// purchase Invoise Report
	String fileName9 = "PurchaseInvoise";
	HashMap hmParams9 = new HashMap();

	@RequestMapping(value = "/generateInvoiseReport")
	public ModelAndView generatePurchaseInvoiseReport(
			@ModelAttribute("user") PurchaseInvoiceePojo user,
			BindingResult result, Model model, HttpServletRequest request,
			HttpServletResponse response) throws ParseException {

		String reportFileName = "PurchaseInvoise";

		try {
			try {

				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				System.out
						.println("Please include Classpath Where your MySQL Driver is located");
				e.printStackTrace();
			}

			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/SriLakshmiSuperSplhospital",
					"root", "root");

			if (conn != null) {
				System.out.println("Database Connected");
			} else {
				System.out.println(" connection Failed ");
			}

			JasperReport jasperReport = getCompiledPurchaseinvoise(
					reportFileName, request);
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					jasperReport, hmParams9, conn);
			// For HTML report
		} catch (Exception sqlExp) {

			System.out.println("Exception::" + sqlExp.toString());

		} finally {

			try {

				if (conn != null) {
					conn.close();
					conn = null;
				}

			} catch (SQLException expSQL) {

				System.out.println("SQLExp::CLOSING::" + expSQL.toString());

			}

		}

		return new ModelAndView("redirect:/FirstPurchaseInvoice.html");
	}

	private JasperReport getCompiledPurchaseinvoise(String fileName,
			HttpServletRequest request) throws JRException {
		System.out.println("before compilation");
		File reportFile = new File(request.getSession().getServletContext()
				.getRealPath("/jasper/" + fileName9 + ".jasper"));
		// If compiled file is not found, then compile XML template
		if (!reportFile.exists()) {
			JasperCompileManager.compileReportToFile(
					request.getSession().getServletContext()
							.getRealPath("/jasper/" + fileName9 + ".jrxml"),
					request.getSession().getServletContext()
							.getRealPath("/jasper/" + fileName9 + ".jasper"));
		}
		System.out.println("ofter compilation");

		JasperReport jasperReport = (JasperReport) JRLoader
				.loadObjectFromFile(reportFile.getPath());
		System.out.println("path "
				+ request.getSession().getServletContext()
						.getRealPath("/jasper/" + fileName9 + ".jasper"));
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
				null, conn);
		JasperViewer.viewReport(jasperPrint, false);

		System.out.println("123456789");
		return jasperReport;
	}

	// purchase Invoise Report
	String fileName10 = "PurchaseReturn";
	HashMap hmParams10 = new HashMap();

	@RequestMapping(value = "/purchaseReturnsreport")
	public ModelAndView generatePurchasereturnsrepport(
			@ModelAttribute("user") PurchaseInvoiceeReturns user,
			BindingResult result, Model model, HttpServletRequest request,
			HttpServletResponse response) throws ParseException {

		String reportFileName = "PurchaseReturn";

		try {
			try {

				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				System.out
						.println("Please include Classpath Where your MySQL Driver is located");
				e.printStackTrace();
			}

			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/SriLakshmiSuperSplhospital",
					"root", "root");

			if (conn != null) {
				System.out.println("Database Connected");
			} else {
				System.out.println(" connection Failed ");
			}

			JasperReport jasperReport = getCompiledPurchasereturns(
					reportFileName, request);
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					jasperReport, hmParams10, conn);
			// For HTML report
		} catch (Exception sqlExp) {

			System.out.println("Exception::" + sqlExp.toString());

		} finally {

			try {

				if (conn != null) {
					conn.close();
					conn = null;
				}

			} catch (SQLException expSQL) {

				System.out.println("SQLExp::CLOSING::" + expSQL.toString());

			}

		}

		return new ModelAndView("redirect:/puchaseReturnsListDetailsFirst.html");
	}

	private JasperReport getCompiledPurchasereturns(String fileName,
			HttpServletRequest request) throws JRException {
		System.out.println("before compilation");
		File reportFile = new File(request.getSession().getServletContext()
				.getRealPath("/jasper/" + fileName10 + ".jasper"));
		// If compiled file is not found, then compile XML template
		if (!reportFile.exists()) {
			JasperCompileManager.compileReportToFile(
					request.getSession().getServletContext()
							.getRealPath("/jasper/" + fileName10 + ".jrxml"),
					request.getSession().getServletContext()
							.getRealPath("/jasper/" + fileName10 + ".jasper"));
		}
		System.out.println("ofter compilation");

		JasperReport jasperReport = (JasperReport) JRLoader
				.loadObjectFromFile(reportFile.getPath());
		System.out.println("path "
				+ request.getSession().getServletContext()
						.getRealPath("/jasper/" + fileName9 + ".jasper"));
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
				null, conn);
		JasperViewer.viewReport(jasperPrint, false);

		System.out.println("123456789");
		return jasperReport;
	}

	// purchase Invoise Report
	String fileName11 = "PurchaseInvoise2";
	HashMap hmParams11 = new HashMap();

	@RequestMapping(value = "/purchaseinvoiseareport")
	public ModelAndView generatePurchaseInvoise(
			@ModelAttribute("user") PurchaseInvoiceePojo user,
			BindingResult result, Model model, HttpServletRequest request,
			HttpServletResponse response) throws ParseException {

		String reportFileName = "PurchaseInvoise2";

		try {
			try {

				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				System.out
						.println("Please include Classpath Where your MySQL Driver is located");
				e.printStackTrace();
			}

			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/SriLakshmiSuperSplhospital",
					"root", "root");

			if (conn != null) {
				System.out.println("Database Connected");
			} else {
				System.out.println(" connection Failed ");
			}

			hmParams11.put("id", user.getId());

			JasperReport jasperReport = getCompiledPurchaseInvoise(
					reportFileName, request);
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					jasperReport, hmParams11, conn);
			// For HTML report
		} catch (Exception sqlExp) {

			System.out.println("Exception::" + sqlExp.toString());

		} finally {

			try {

				if (conn != null) {
					conn.close();
					conn = null;
				}

			} catch (SQLException expSQL) {

				System.out.println("SQLExp::CLOSING::" + expSQL.toString());

			}

		}

		return new ModelAndView("redirect:/FirstPurchaseInvoice.html");
	}

	private JasperReport getCompiledPurchaseInvoise(String fileName,
			HttpServletRequest request) throws JRException {
		System.out.println("before compilation");
		File reportFile = new File(request.getSession().getServletContext()
				.getRealPath("/jasper/" + fileName11 + ".jasper"));
		// If compiled file is not found, then compile XML template
		if (!reportFile.exists()) {
			JasperCompileManager.compileReportToFile(
					request.getSession().getServletContext()
							.getRealPath("/jasper/" + fileName11 + ".jrxml"),
					request.getSession().getServletContext()
							.getRealPath("/jasper/" + fileName11 + ".jasper"));
		}
		System.out.println("ofter compilation");

		JasperReport jasperReport = (JasperReport) JRLoader
				.loadObjectFromFile(reportFile.getPath());
		System.out.println("path "
				+ request.getSession().getServletContext()
						.getRealPath("/jasper/" + fileName11 + ".jasper"));
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
				hmParams11, conn);
		JasperViewer.viewReport(jasperPrint, false);

		System.out.println("123456789");
		return jasperReport;
	}

	// purchase Invoise Report
	String fileName12 = "InPatientAdmission2";
	HashMap hmParams12 = new HashMap();

	@RequestMapping(value = "/reportinpatient")
	public ModelAndView generateInpatientDetails(
			@ModelAttribute("user") entry user, BindingResult result,
			Model model, HttpServletRequest request,
			HttpServletResponse response) throws ParseException {

		String reportFileName = "InPatientAdmission2";

		try {
			try {

				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				System.out
						.println("Please include Classpath Where your MySQL Driver is located");
				e.printStackTrace();
			}

			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/SriLakshmiSuperSplhospital",
					"root", "root");

			if (conn != null) {
				System.out.println("Database Connected");
			} else {
				System.out.println(" connection Failed ");
			}

			hmParams12.put("id", user.getId());

			JasperReport jasperReport = getCompiledInvoiseDetails(
					reportFileName, request);
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					jasperReport, hmParams12, conn);
			// For HTML report
		} catch (Exception sqlExp) {

			System.out.println("Exception::" + sqlExp.toString());

		} finally {

			try {

				if (conn != null) {
					conn.close();
					conn = null;
				}

			} catch (SQLException expSQL) {

				System.out.println("SQLExp::CLOSING::" + expSQL.toString());

			}

		}

		return new ModelAndView("redirect:/inpatientAddmissionDetails.html");
	}

	private JasperReport getCompiledInvoiseDetails(String fileName,
			HttpServletRequest request) throws JRException {
		System.out.println("before compilation");
		File reportFile = new File(request.getSession().getServletContext()
				.getRealPath("/jasper/" + fileName12 + ".jasper"));
		// If compiled file is not found, then compile XML template
		if (!reportFile.exists()) {
			JasperCompileManager.compileReportToFile(
					request.getSession().getServletContext()
							.getRealPath("/jasper/" + fileName12 + ".jrxml"),
					request.getSession().getServletContext()
							.getRealPath("/jasper/" + fileName12 + ".jasper"));
		}
		System.out.println("ofter compilation");

		JasperReport jasperReport = (JasperReport) JRLoader
				.loadObjectFromFile(reportFile.getPath());
		System.out.println("path "
				+ request.getSession().getServletContext()
						.getRealPath("/jasper/" + fileName12 + ".jasper"));
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
				hmParams12, conn);
		JasperViewer.viewReport(jasperPrint, false);

		System.out.println("123456789");
		return jasperReport;
	}

	// Mlc Report Report
	String fileName13 = "MlcReport";
	HashMap hmParamsi = new HashMap();

	@RequestMapping(value = "/mlcReport")
	public ModelAndView generateOutSideConsult(
			@ModelAttribute("user") Mlcpojo user, BindingResult result,
			Model model, HttpServletRequest request,
			HttpServletResponse response) throws ParseException {

		String reportFileName = "MlcReport";

		try {
			try {

				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				System.out
						.println("Please include Classpath Where your MySQL Driver is located");
				e.printStackTrace();
			}

			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/SriLakshmiSuperSplhospital",
					"root", "root");

			if (conn != null) {
				System.out.println("Database Connected");
			} else {
				System.out.println(" connection Failed ");
			}

			hmParamsi.put("id", user.getId());

			JasperReport jasperReport = getCompiledoutSideConsult(
					reportFileName, request);
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					jasperReport, hmParamsi, conn);
			// For HTML report
		} catch (Exception sqlExp) {

			System.out.println("Exception::" + sqlExp.toString());

		} finally {

			try {

				if (conn != null) {
					conn.close();
					conn = null;
				}

			} catch (SQLException expSQL) {

				System.out.println("SQLExp::CLOSING::" + expSQL.toString());

			}

		}
		List<Mlcpojo> l1 = null;
		int i = 0;
		ModelAndView m1 = new ModelAndView("MLC1");
		m1.addObject("abc", l1 = patientservicemlc.getpatients());
		for (Mlcpojo mlcpojo : l1) {

			i += 1;
		}
		m1.addObject("mlc", i);

		return m1;
	}

	private JasperReport getCompiledoutSideConsult(String fileName,
			HttpServletRequest request) throws JRException {
		System.out.println("before compilation");
		File reportFile = new File(request.getSession().getServletContext()
				.getRealPath("/jasper/" + fileName13 + ".jasper"));
		// If compiled file is not found, then compile XML template
		if (!reportFile.exists()) {
			JasperCompileManager.compileReportToFile(
					request.getSession().getServletContext()
							.getRealPath("/jasper/" + fileName13 + ".jrxml"),
					request.getSession().getServletContext()
							.getRealPath("/jasper/" + fileName13 + ".jasper"));
		}
		System.out.println("ofter compilation");

		JasperReport jasperReport = (JasperReport) JRLoader
				.loadObjectFromFile(reportFile.getPath());
		System.out.println("path "
				+ request.getSession().getServletContext()
						.getRealPath("/jasper/" + fileName13 + ".jasper"));
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
				hmParamsi, conn);
		JasperViewer.viewReport(jasperPrint, false);

		System.out.println("123456789");
		return jasperReport;
	}

	// Out Side Consult Report
	String fileName14 = "OutSideConsult";

	@RequestMapping(value = "/outsideconsultReport")
	public ModelAndView generateMlcReport(
			@ModelAttribute("user") OutsideConultPojo user,
			BindingResult result, Model model, HttpServletRequest request,
			HttpServletResponse response) throws ParseException {

		String reportFileName = "OutSideConsult";

		try {
			try {

				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				System.out
						.println("Please include Classpath Where your MySQL Driver is located");
				e.printStackTrace();
			}

			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/SriLakshmiSuperSplhospital",
					"root", "root");

			if (conn != null) {
				System.out.println("Database Connected");
			} else {
				System.out.println(" connection Failed ");
			}
			HashMap hmParams = new HashMap();

			JasperReport jasperReport = getCompiledMlcReport(reportFileName,
					request);
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					jasperReport, hmParams, conn);
			// For HTML report
		} catch (Exception sqlExp) {

			System.out.println("Exception::" + sqlExp.toString());

		} finally {

			try {

				if (conn != null) {
					conn.close();
					conn = null;
				}

			} catch (SQLException expSQL) {

				System.out.println("SQLExp::CLOSING::" + expSQL.toString());

			}

		}

		return new ModelAndView("redirect:/outconsult.html");
	}

	private JasperReport getCompiledMlcReport(String fileName,
			HttpServletRequest request) throws JRException {
		System.out.println("before compilation");
		File reportFile = new File(request.getSession().getServletContext()
				.getRealPath("/jasper/" + fileName14 + ".jasper"));
		// If compiled file is not found, then compile XML template
		if (!reportFile.exists()) {
			JasperCompileManager.compileReportToFile(
					request.getSession().getServletContext()
							.getRealPath("/jasper/" + fileName14 + ".jrxml"),
					request.getSession().getServletContext()
							.getRealPath("/jasper/" + fileName14 + ".jasper"));
		}
		System.out.println("ofter compilation");

		JasperReport jasperReport = (JasperReport) JRLoader
				.loadObjectFromFile(reportFile.getPath());
		System.out.println("path "
				+ request.getSession().getServletContext()
						.getRealPath("/jasper/" + fileName14 + ".jasper"));
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
				null, conn);
		JasperViewer.viewReport(jasperPrint, false);

		System.out.println("123456789");
		return jasperReport;
	}

	// Mlc Report Report
	String fileNamep = "PatientDetails";
	HashMap hmParamsp = new HashMap();

	@RequestMapping(value = "/patientReport")
	public ModelAndView generatePatentRegistrationReport(
			@ModelAttribute("user") AddPatient1 user, BindingResult result,
			Model model, HttpServletRequest request,
			HttpServletResponse response) throws ParseException {

		String reportFileName = "PatientDetails";

		try {
			try {

				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				System.out
						.println("Please include Classpath Where your MySQL Driver is located");
				e.printStackTrace();
			}

			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/SriLakshmiSuperSplhospital",
					"root", "root");

			if (conn != null) {
				System.out.println("Database Connected");
			} else {
				System.out.println(" connection Failed ");
			}

			hmParamsp.put("id", user.getRegistNo());

			JasperReport jasperReport = getPatientRegistration(reportFileName,
					request);
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					jasperReport, hmParamsp, conn);
			// For HTML report
		} catch (Exception sqlExp) {

			System.out.println("Exception::" + sqlExp.toString());

		} finally {

			try {

				if (conn != null) {
					conn.close();
					conn = null;
				}

			} catch (SQLException expSQL) {

				System.out.println("SQLExp::CLOSING::" + expSQL.toString());

			}

		}

		return new ModelAndView("redirect:/add.html");
	}

	private JasperReport getPatientRegistration(String fileName,
			HttpServletRequest request) throws JRException {
		System.out.println("before compilation");
		File reportFile = new File(request.getSession().getServletContext()
				.getRealPath("/jasper/" + fileNamep + ".jasper"));
		// If compiled file is not found, then compile XML template
		if (!reportFile.exists()) {
			JasperCompileManager.compileReportToFile(
					request.getSession().getServletContext()
							.getRealPath("/jasper/" + fileNamep + ".jrxml"),
					request.getSession().getServletContext()
							.getRealPath("/jasper/" + fileNamep + ".jasper"));
		}
		System.out.println("ofter compilation");

		JasperReport jasperReport = (JasperReport) JRLoader
				.loadObjectFromFile(reportFile.getPath());
		System.out.println("path "
				+ request.getSession().getServletContext()
						.getRealPath("/jasper/" + fileNamep + ".jasper"));
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
				hmParamsp, conn);
		JasperViewer.viewReport(jasperPrint, false);

		System.out.println("123456789");
		return jasperReport;
	}

	// Sales Entry By Id Report Report
	String fileNameid = "SalesEntrys3";
	HashMap hmParamsid = new HashMap();

	@RequestMapping(value = "/SalesReportById")
	public ModelAndView generateSalesReportByIdReport(
			@ModelAttribute("salesEntryPojo") SalesEntryPojo salesEntryPojo,
			BindingResult result, Model model, HttpServletRequest request,
			HttpServletResponse response) throws ParseException {

		String reportFileName = "SalesEntrys3";

		try {
			try {

				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				System.out
						.println("Please include Classpath Where your MySQL Driver is located");
				e.printStackTrace();
			}

			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/SriLakshmiSuperSplhospital",
					"root", "root");

			if (conn != null) {
				System.out.println("Database Connected");
			} else {
				System.out.println(" connection Failed ");
			}

			hmParamsid.put("salesid", salesEntryPojo.getSalesid());

			JasperReport jasperReport = getSalesEntryReportById(reportFileName,
					request);
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					jasperReport, hmParamsid, conn);
			// For HTML report
		} catch (Exception sqlExp) {

			System.out.println("Exception::" + sqlExp.toString());

		} finally {

			try {

				if (conn != null) {
					conn.close();
					conn = null;
				}

			} catch (SQLException expSQL) {

				System.out.println("SQLExp::CLOSING::" + expSQL.toString());

			}

		}

		return new ModelAndView("redirect:/FirstSalesEntryListDet.html");
	}

	private JasperReport getSalesEntryReportById(String fileName,
			HttpServletRequest request) throws JRException {
		System.out.println("before compilation");
		File reportFile = new File(request.getSession().getServletContext()
				.getRealPath("/jasper/" + fileNameid + ".jasper"));
		// If compiled file is not found, then compile XML template
		if (!reportFile.exists()) {
			JasperCompileManager.compileReportToFile(
					request.getSession().getServletContext()
							.getRealPath("/jasper/" + fileNameid + ".jrxml"),
					request.getSession().getServletContext()
							.getRealPath("/jasper/" + fileNameid + ".jasper"));
		}
		System.out.println("ofter compilation");

		JasperReport jasperReport = (JasperReport) JRLoader
				.loadObjectFromFile(reportFile.getPath());
		System.out.println("path "
				+ request.getSession().getServletContext()
						.getRealPath("/jasper/" + fileNameid + ".jasper"));
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
				hmParamsid, conn);
		JasperViewer.viewReport(jasperPrint, false);

		System.out.println("123456789");
		return jasperReport;
	}

	// Sales Returns Report Report
	String fileNameSalesReturns = "SalesReturns1";
	HashMap hmParamsSalesReturns = new HashMap();

	@RequestMapping(value = "/salesreturnsdetailsList446NameReport")
	public ModelAndView generateReturnsReport(
			@ModelAttribute("salesEntryPojo") SalesEntryReturns salesEntryPojo,
			BindingResult result, Model model, HttpServletRequest request,
			HttpServletResponse response) throws ParseException {

		String reportFileName = "SalesReturns1";

		try {
			try {

				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				System.out
						.println("Please include Classpath Where your MySQL Driver is located");
				e.printStackTrace();
			}

			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/SriLakshmiSuperSplhospital",
					"root", "root");

			if (conn != null) {
				System.out.println("Database Connected");
			} else {
				System.out.println(" connection Failed ");
			}

			JasperReport jasperReport = getSalesReturnsReport(reportFileName,
					request);
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					jasperReport, hmParamsSalesReturns, conn);
			// For HTML report
		} catch (Exception sqlExp) {

			System.out.println("Exception::" + sqlExp.toString());

		} finally {

			try {

				if (conn != null) {
					conn.close();
					conn = null;
				}

			} catch (SQLException expSQL) {

				System.out.println("SQLExp::CLOSING::" + expSQL.toString());

			}

		}

		return new ModelAndView("redirect:/salesreturnsdetailsList446.html");
	}

	private JasperReport getSalesReturnsReport(String fileName,
			HttpServletRequest request) throws JRException {
		System.out.println("before compilation");
		File reportFile = new File(request.getSession().getServletContext()
				.getRealPath("/jasper/" + fileNameSalesReturns + ".jasper"));
		// If compiled file is not found, then compile XML template
		if (!reportFile.exists()) {
			JasperCompileManager.compileReportToFile(
					request.getSession()
							.getServletContext()
							.getRealPath(
									"/jasper/" + fileNameSalesReturns
											+ ".jrxml"),
					request.getSession()
							.getServletContext()
							.getRealPath(
									"/jasper/" + fileNameSalesReturns
											+ ".jasper"));
		}
		System.out.println("ofter compilation");

		JasperReport jasperReport = (JasperReport) JRLoader
				.loadObjectFromFile(reportFile.getPath());
		System.out.println("path "
				+ request
						.getSession()
						.getServletContext()
						.getRealPath(
								"/jasper/" + fileNameSalesReturns + ".jasper"));
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
				null, conn);
		JasperViewer.viewReport(jasperPrint, false);

		System.out.println("123456789");
		return jasperReport;
	}

	/* REGISTRATION */

	@Autowired
	private EmployeeService employeeService123;

	@RequestMapping("/registerRegister")
	public ModelAndView getRegisterForm(@ModelAttribute("user") Employee user,
			BindingResult result) {
		ArrayList<String> gender = new ArrayList<String>();
		gender.add("Male");
		gender.add("Female");
		ArrayList<String> contry = new ArrayList<String>();
		contry.add("Afghanistan");
		contry.add("Albania");
		contry.add("Algeria");
		contry.add("Angola");
		contry.add("Anguilla");
		contry.add("American Samoa");
		contry.add("Antarctica");
		contry.add("Antigua and Barbuda");
		contry.add("Australia");
		contry.add("Azerbaijan");
		contry.add("Bahamas");
		contry.add("Bahrain");
		contry.add("Belgium");
		contry.add("Belarus");
		contry.add("Belize");
		contry.add("Benin");
		contry.add("Bermuda");
		contry.add("Bhutan");
		contry.add("Bolivia");
		contry.add("Bosnia and Herzegowina");
		contry.add("Botswana");
		contry.add("Bouvet");
		contry.add("Brazil");
		contry.add("British Indian Ocean Territory");
		contry.add("Brunei Darussalam");
		contry.add("Bulgaria");
		contry.add("Burkina Faso");
		contry.add("Burundi");
		contry.add("Cambodia");
		contry.add("Cameroon");
		contry.add("Canada");
		contry.add("Cape Verde");
		contry.add("China");
		contry.add("Christmas Island");
		contry.add("Cuba");
		contry.add("Congo, the Democratic Republic of the");
		contry.add("Denmark");
		contry.add("Finland");
		contry.add("Greenland");
		contry.add("Hong Kong");
		contry.add("India");
		contry.add("Nepal");
		contry.add("Malaysia");
		contry.add("Pakistan");
		contry.add("South Africa");
		contry.add("Saudi Arabia");
		contry.add("United States");
		contry.add("Zimbabwe");
		ArrayList<String> state = new ArrayList<String>();
		state.add("Andhra Pradesh");
		state.add(" Arunachal Prades");
		state.add("Assam");
		state.add(" Bihar");
		state.add("Chhattisgarh");
		state.add("Goa");
		state.add("Gujarat");
		state.add("Haryana");
		state.add("Himachal Pradesh");
		state.add("Jammu & Kashmir");
		state.add("Jharkhand");
		state.add("Karnataka");
		state.add("Kerala");
		state.add("Madhya Pradesh");
		state.add("Maharashtra");
		state.add("Manipur");
		state.add("Meghalaya");
		state.add("Mizoram");
		state.add("Nagaland");
		state.add("Odisha");
		state.add("Punjab");
		state.add("Rajasthan");
		state.add("Sikkim");
		state.add("Tamil Nadu");
		state.add("Telangana");
		state.add("Tripura");
		state.add("Uttarakhand");
		state.add("Uttar Pradesh");
		state.add("West Bengal");
		state.add("Delhi");
		state.add("Andaman & Nicobar Island");
		state.add("Chandigarh");
		state.add("Dadra & Nagar Haveli");
		state.add("Daman & Diu");
		state.add("Lakshadweep");
		state.add("Puducherry");
		ArrayList<String> MAXID = new ArrayList<String>();
		MAXID.add(employeeService123.getMaxId2());
		ArrayList<String> idproof = new ArrayList<String>();
		idproof.add("Driving license");
		idproof.add("Voter ID card");
		idproof.add("Adhaar Card");
		idproof.add("PAN Card");
		idproof.add("Ration card");

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("gender", gender);
		model.put("state", state);
		model.put("country", contry);
		model.put("MAXID", MAXID);
		model.put("idproof", idproof);
		model.put("user", employeeService123.getUser());

		ArrayList<String> depatment = new ArrayList<String>();
		depatment.add("Medical");
		depatment.add(" Pharmasy");
		depatment.add("X-Ray");
		depatment.add("Lab");
		depatment.add("Central Stores");
		depatment.add("Diabatics");
		depatment.add("Ultra Sound With Color Dopler");
		depatment.add("Radiology");
		depatment.add("Ortho");
		depatment.add("PHYSIOTHEROPHY");
		depatment.add("BIOLOGY");
		depatment.add("Department");
		depatment.add("Others");

		model.put("depatment", depatment);

		System.out.println("am in first step controller");
		System.out.println("Register Form");

		return new ModelAndView("Addemployee", "model", model);
	}

	@RequestMapping("/saveRegistrationEmployee")
	public ModelAndView saveUserData(@ModelAttribute("user") Employee user,
			@RequestParam("file") MultipartFile file) {

		System.out.println("File:" + file.getName());
		System.out.println("after file name");
		System.out.println("ContentType:" + file.getContentType());
		System.out.println("after content type");

		try {
			System.out.println("am in try block");
			Blob blob = Hibernate.createBlob(file.getInputStream());

			user.setFilename(file.getOriginalFilename());
			user.setContent(blob);
			System.out.println("content  = " + user.getContent());
			user.setContentType(file.getContentType());
		} catch (IOException e) {
			e.printStackTrace();
		}

		ArrayList<String> gender = new ArrayList<String>();
		gender.add("Male");
		gender.add("Female");
		ArrayList<String> state = new ArrayList<String>();
		state.add("Andhra Pradesh");
		state.add(" Arunachal Prades");
		state.add("Assam");
		state.add(" Bihar");
		state.add("Chhattisgarh");
		state.add("Goa");
		state.add("Gujarat");
		state.add("Haryana");
		state.add("Himachal Pradesh");
		state.add("Jammu & Kashmir");
		state.add("Jharkhand");
		state.add("Karnataka");
		state.add("Kerala");
		state.add("Madhya Pradesh");
		state.add("Maharashtra");
		state.add("Manipur");
		state.add("Meghalaya");
		state.add("Mizoram");
		state.add("Nagaland");
		state.add("Odisha");
		state.add("Punjab");
		state.add("Rajasthan");
		state.add("Sikkim");
		state.add("Tamil Nadu");
		state.add("Telangana");
		state.add("Tripura");
		state.add("Uttarakhand");
		state.add("Uttar Pradesh");
		state.add("West Bengal");
		state.add("Delhi");
		state.add("Andaman & Nicobar Island");
		state.add("Chandigarh");
		state.add("Dadra & Nagar Haveli");
		state.add("Daman & Diu");
		state.add("Lakshadweep");
		state.add("Puducherry");
		ArrayList<String> contry = new ArrayList<String>();
		contry.add("Afghanistan");
		contry.add("Albania");
		contry.add("Algeria");
		contry.add("Angola");
		contry.add("Anguilla");
		contry.add("American Samoa");
		contry.add("Antarctica");
		contry.add("Antigua and Barbuda");
		contry.add("Australia");
		contry.add("Azerbaijan");
		contry.add("Bahamas");
		contry.add("Bahrain");
		contry.add("Belgium");
		contry.add("Belarus");
		contry.add("Belize");
		contry.add("Benin");
		contry.add("Bermuda");
		contry.add("Bhutan");
		contry.add("Bolivia");
		contry.add("Bosnia and Herzegowina");
		contry.add("Botswana");
		contry.add("Bouvet");
		contry.add("Brazil");
		contry.add("British Indian Ocean Territory");
		contry.add("Brunei Darussalam");
		contry.add("Bulgaria");
		contry.add("Burkina Faso");
		contry.add("Burundi");
		contry.add("Cambodia");
		contry.add("Cameroon");
		contry.add("Canada");
		contry.add("Cape Verde");
		contry.add("China");
		contry.add("Christmas Island");
		contry.add("Cuba");
		contry.add("Congo, the Democratic Republic of the");
		contry.add("Denmark");
		contry.add("Finland");
		contry.add("Greenland");
		contry.add("Hong Kong");
		contry.add("India");
		contry.add("Nepal");
		contry.add("Malaysia");
		contry.add("Pakistan");
		contry.add("South Africa");
		contry.add("Saudi Arabia");
		contry.add("United States");
		contry.add("Zimbabwe");
		ArrayList<String> MAXID = new ArrayList<String>();
		MAXID.add(employeeService123.getMaxId2());
		ArrayList<String> idproof = new ArrayList<String>();
		idproof.add("Driving license");
		idproof.add("Voter ID card");
		idproof.add("Adhaar Card");
		idproof.add("PAN Card");
		idproof.add("Ration card");

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("gender", gender);
		model.put("state", state);
		model.put("country", contry);
		model.put("MAXID", MAXID);
		model.put("idproof", idproof);
		model.put("user", employeeService123.getUser());

		ArrayList<String> depatment = new ArrayList<String>();
		depatment.add("Medical");
		depatment.add(" Pharmasy");
		depatment.add("X-Ray");
		depatment.add("Lab");
		depatment.add("Central Stores");
		depatment.add("Diabatics");
		depatment.add("Ultra Sound With Color Dopler");
		depatment.add("Radiology");
		depatment.add("Ortho");
		depatment.add("PHYSIOTHEROPHY");
		depatment.add("BIOLOGY");
		depatment.add("Department");
		depatment.add("Others");

		model.put("depatment", depatment);

		boolean isValidUser = employeeService123.addUser(user);

		if (isValidUser) {

			ArrayList<String> msg = new ArrayList<String>();
			msg.add("User Name/Mobile Number/Email already exist");
			model.put("msg", msg);

			return new ModelAndView("Addemployee", "model", model);
		} else {

			return new ModelAndView("redirect:/conformRegister.html");
		}
	}

	@RequestMapping("/conformRegister")
	public ModelAndView conformRegister(@ModelAttribute("user") Employee user,
			BindingResult result) {
		ArrayList<String> gender = new ArrayList<String>();
		gender.add("Male");
		gender.add("Female");
		ArrayList<String> contry = new ArrayList<String>();
		contry.add("Afghanistan");
		contry.add("Albania");
		contry.add("Algeria");
		contry.add("Angola");
		contry.add("Anguilla");
		contry.add("American Samoa");
		contry.add("Antarctica");
		contry.add("Antigua and Barbuda");
		contry.add("Australia");
		contry.add("Azerbaijan");
		contry.add("Bahamas");
		contry.add("Bahrain");
		contry.add("Belgium");
		contry.add("Belarus");
		contry.add("Belize");
		contry.add("Benin");
		contry.add("Bermuda");
		contry.add("Bhutan");
		contry.add("Bolivia");
		contry.add("Bosnia and Herzegowina");
		contry.add("Botswana");
		contry.add("Bouvet");
		contry.add("Brazil");
		contry.add("British Indian Ocean Territory");
		contry.add("Brunei Darussalam");
		contry.add("Bulgaria");
		contry.add("Burkina Faso");
		contry.add("Burundi");
		contry.add("Cambodia");
		contry.add("Cameroon");
		contry.add("Canada");
		contry.add("Cape Verde");
		contry.add("China");
		contry.add("Christmas Island");
		contry.add("Cuba");
		contry.add("Congo, the Democratic Republic of the");
		contry.add("Denmark");
		contry.add("Finland");
		contry.add("Greenland");
		contry.add("Hong Kong");
		contry.add("India");
		contry.add("Nepal");
		contry.add("Malaysia");
		contry.add("Pakistan");
		contry.add("South Africa");
		contry.add("Saudi Arabia");
		contry.add("United States");
		contry.add("Zimbabwe");

		ArrayList<String> state = new ArrayList<String>();
		state.add("Andhra Pradesh");
		state.add(" Arunachal Prades");
		state.add("Assam");
		state.add(" Bihar");
		state.add("Chhattisgarh");
		state.add("Goa");
		state.add("Gujarat");
		state.add("Haryana");
		state.add("Himachal Pradesh");
		state.add("Jammu & Kashmir");
		state.add("Jharkhand");
		state.add("Karnataka");
		state.add("Kerala");
		state.add("Madhya Pradesh");
		state.add("Maharashtra");
		state.add("Manipur");
		state.add("Meghalaya");
		state.add("Mizoram");
		state.add("Nagaland");
		state.add("Odisha");
		state.add("Punjab");
		state.add("Rajasthan");
		state.add("Sikkim");
		state.add("Tamil Nadu");
		state.add("Telangana");
		state.add("Tripura");
		state.add("Uttarakhand");
		state.add("Uttar Pradesh");
		state.add("West Bengal");
		state.add("Delhi");
		state.add("Andaman & Nicobar Island");
		state.add("Chandigarh");
		state.add("Dadra & Nagar Haveli");
		state.add("Daman & Diu");
		state.add("Lakshadweep");
		state.add("Puducherry");
		ArrayList<String> MAXID = new ArrayList<String>();
		MAXID.add(employeeService123.getMaxId2());
		ArrayList<String> idproof = new ArrayList<String>();
		idproof.add("Driving license");
		idproof.add("Voter ID card");
		idproof.add("Adhaar Card");
		idproof.add("PAN Card");
		idproof.add("Ration card");

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("gender", gender);
		model.put("state", state);
		model.put("country", contry);
		model.put("MAXID", MAXID);
		model.put("idproof", idproof);
		model.put("user", employeeService123.getUser());

		ArrayList<String> depatment = new ArrayList<String>();
		depatment.add("Medical");
		depatment.add(" Pharmasy");
		depatment.add("X-Ray");
		depatment.add("Lab");
		depatment.add("Central Stores");
		depatment.add("Diabatics");
		depatment.add("Ultra Sound With Color Dopler");
		depatment.add("Radiology");
		depatment.add("Ortho");
		depatment.add("PHYSIOTHEROPHY");
		depatment.add("BIOLOGY");
		depatment.add("Department");
		depatment.add("Others");

		model.put("depatment", depatment);

		ArrayList<String> msg = new ArrayList<String>();
		msg.add("User Successfully Registered");
		model.put("msg", msg);

		System.out.println("am in first step controller");
		System.out.println("Register Form");

		return new ModelAndView("Addemployee", "model", model);
	}

	@RequestMapping("/passForget")
	public ModelAndView getForget(
			@ModelAttribute("register") RegisterPojo register,
			BindingResult result) {

		System.out.println("am in first step controller");
		System.out.println("Register Form");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("FORGETDB", employeeService123.getEmployeeIDDB());
		return new ModelAndView("ForgetPass", model);
	}

	@RequestMapping("/saveForgetPASS")
	public ModelAndView getForgetFromJSP(
			@ModelAttribute("register") RegisterPojo register,
			BindingResult result) {

		System.out.println("am in first step controller");
		System.out.println("Register Form");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("FORGETDB", employeeService123.getEmployeeIDDB());
		model.put("FETCH", employeeService123.getEmployee(register.getId()));
		return new ModelAndView("ForgetPass2", model);
	}

	@RequestMapping("/saveForgetPASS2")
	public ModelAndView getForgetFromJSP2(
			@ModelAttribute("register") RegisterPojo register,
			BindingResult result) {

		System.out.println("am in first step controller");
		System.out.println("Register Form");
		employeeService123.addPassword(register);
		return new ModelAndView("redirect:/saveForgetPASSOfterSuccess.html");
	}

	@RequestMapping("/saveForgetPASSOfterSuccess")
	public ModelAndView getForgetOfterSuccess(
			@ModelAttribute("register") RegisterPojo register,
			BindingResult result) {

		System.out.println("am in first step controller");
		System.out.println("Register Form");
		ArrayList<String> Sussess = new ArrayList<String>();
		Sussess.add("Password Changed SuccessFully");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("msg", Sussess);
		return new ModelAndView("Login", model);
	}

	@RequestMapping("/download")
	public ModelAndView seeImageEmployee(@ModelAttribute("user") Employee user,
			HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		System.out.println("im in download photo controler");

		Employee doc = employeeService123.get(user.getId());
		try {
			System.out.println("am in downlaod controller in try block");
			response.setHeader("Content-Disposition", "inline;filename=\""
					+ doc.getFilename() + "\"");
			OutputStream out = response.getOutputStream();
			response.setContentType(doc.getContentType());
			IOUtils.copy(doc.getContent().getBinaryStream(), out);
			out.flush();
			out.close();
			System.out.println("am in down load controller try last");

		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("am in doenoad controller last");

		return new ModelAndView("display");
	}

	@RequestMapping("/supplierTypeList")
	public ModelAndView SupplierTypeAdd(
			@ModelAttribute("asdf") NewSupplierInfoPojo asdf,
			BindingResult result) {

		Map<String, Object> model = new HashMap<String, Object>();
		ArrayList<Integer> tokenMax = new ArrayList<Integer>();
		tokenMax.add(supplierService.getSupplierId());
		model.put("tokenMax", tokenMax);
		model.put("asdf", supplierService.GetDetailsOfSupplierType());
		return new ModelAndView("SuplierTypeList", model);
	}

	@RequestMapping("/searchSupplierType")
	public ModelAndView searchSupplierType(
			@ModelAttribute("asdf") NewSupplierInfoPojo asdf,
			BindingResult result) {

		Map<String, Object> model = new HashMap<String, Object>();
		ArrayList<Integer> tokenMax = new ArrayList<Integer>();
		tokenMax.add(supplierService.getSupplierId());
		model.put("tokenMax", tokenMax);
		model.put("asdf", supplierService.GetDetailsOfSupplierType(asdf
				.getSupplierType()));
		return new ModelAndView("SuplierTypeList", model);
	}

	@RequestMapping("/addSupplierType")
	public ModelAndView SupplierTypeAddPPagwParticularType(
			@ModelAttribute("asdf") NewSupplierInfoPojo asdf,
			BindingResult result) {

		Map<String, Object> model = new HashMap<String, Object>();
		ArrayList<Integer> tokenMax = new ArrayList<Integer>();
		tokenMax.add(supplierService.getSupplierId());
		model.put("tokenMax", tokenMax);
		return new ModelAndView("AddSupplier", model);
	}

	@RequestMapping("/saveSupplierType")
	public ModelAndView SupplierTypeSaveAddParticularType(
			@ModelAttribute("asdf") NewSupplierInfoPojo asdf,
			BindingResult result) {

		boolean b = supplierService.SaveSupplierTypeDetAILs(asdf);
		if (b) {
			return new ModelAndView("redirect:/supplierTypeSuccessList.html");
		} else {
			Map<String, Object> model = new HashMap<String, Object>();
			ArrayList<Integer> tokenMax = new ArrayList<Integer>();
			tokenMax.add(supplierService.getSupplierId());
			model.put("tokenMax", tokenMax);

			ArrayList<String> msg = new ArrayList<String>();
			msg.add("Supplier type alreadyExist");
			model.put("msg", msg);

			return new ModelAndView("AddSupplier", model);
		}
	}

	@RequestMapping("/supplierTypeSuccessList")
	public ModelAndView supplierTypeSuccessList(
			@ModelAttribute("asdf") NewSupplierInfoPojo asdf,
			BindingResult result) {

		Map<String, Object> model = new HashMap<String, Object>();
		ArrayList<Integer> tokenMax = new ArrayList<Integer>();
		tokenMax.add(supplierService.getSupplierId());
		model.put("tokenMax", tokenMax);
		model.put("asdf", supplierService.GetDetailsOfSupplierType());
		ArrayList<String> msg = new ArrayList<String>();
		msg.add("Record Inserted successfully");
		model.put("msg", msg);
		return new ModelAndView("SuplierTypeList", model);
	}

	@RequestMapping("/supplierTypeUpdateSuccessList")
	public ModelAndView supplierTypeUpdateSuccessList(
			@ModelAttribute("asdf") NewSupplierInfoPojo asdf,
			BindingResult result) {

		Map<String, Object> model = new HashMap<String, Object>();
		ArrayList<Integer> tokenMax = new ArrayList<Integer>();
		tokenMax.add(supplierService.getSupplierId());
		model.put("tokenMax", tokenMax);
		model.put("asdf", supplierService.GetDetailsOfSupplierType());
		ArrayList<String> msg = new ArrayList<String>();
		msg.add("Record Updated successfully");
		model.put("msg", msg);
		return new ModelAndView("SuplierTypeList", model);
	}

	@RequestMapping("/editSupplierType")
	public ModelAndView SupplierTypeEditAddParticularTypeget(
			@ModelAttribute("asdf") NewSupplierInfoPojo asdf,
			BindingResult result) {

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("asdf1",
				supplierService.GetDetailsOfSupplierByType(asdf.getId()));
		ArrayList<String> msg = new ArrayList<String>();
		msg.add("Supplier Type already exist try another ");
		model.put("msg1", msg);
		return new ModelAndView("EditSupplierType", model);
	}

	@RequestMapping("/editSupplierTypeeSav")
	public ModelAndView SupplierTypeEditAddParticularType(
			@ModelAttribute("asdf") NewSupplierInfoPojo asdf,
			BindingResult result) {

		boolean b = supplierService.editSupplierTypeDetAILs(asdf);
		if (b) {
			System.out.println("am in success");
			return new ModelAndView(
					"redirect:/supplierTypeUpdateSuccessList.html");
		} else {

			return new ModelAndView(
					"redirect:/supplierTypeUpdateSuccessList.html");
		}
	}

	@RequestMapping("/reception")
	public ModelAndView reception(
			@ModelAttribute("register") RegisterPojo register,
			BindingResult result) {
		System.out.println("am in first time in controller");
		ArrayList<String> userName = new ArrayList<String>();
		userName.add(register.getUsername());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("userName", userName);
		System.out.println(model);
		return new ModelAndView("reception", model);
	}

	@RequestMapping("/doctordetails")
	public ModelAndView doctordetails(
			@ModelAttribute("register") RegisterPojo register,
			BindingResult result) {
		System.out.println("am in first time in controller");
		ArrayList<String> userName = new ArrayList<String>();
		userName.add(register.getUsername());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("userName", userName);
		System.out.println(model);
		return new ModelAndView("Doctor", model);
	}

	@RequestMapping("/wardDetails")
	public ModelAndView wardDetails(
			@ModelAttribute("register") RegisterPojo register,
			BindingResult result) {
		System.out.println("am in first time in controller");
		ArrayList<String> userName = new ArrayList<String>();
		userName.add(register.getUsername());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("userName", userName);
		System.out.println(model);
		return new ModelAndView("ward", model);
	}

	@RequestMapping("/pharmacyDetails")
	public ModelAndView pharmacyDetails(
			@ModelAttribute("register") RegisterPojo register,
			BindingResult result) {
		System.out.println("am in first time in controller");
		ArrayList<String> userName = new ArrayList<String>();
		userName.add(register.getUsername());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("userName", userName);
		System.out.println(model);
		return new ModelAndView("pharmacy", model);
	}

	@RequestMapping("/storeDetails")
	public ModelAndView storeDetails(
			@ModelAttribute("register") RegisterPojo register,
			BindingResult result) {
		System.out.println("am in first time in controller");
		ArrayList<String> userName = new ArrayList<String>();
		userName.add(register.getUsername());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("userName", userName);
		System.out.println(model);
		return new ModelAndView("store", model);
	}

	@RequestMapping("/settingDetials")
	public ModelAndView settingDetials(
			@ModelAttribute("register") RegisterPojo register,
			BindingResult result) {
		System.out.println("am in first time in controller");
		ArrayList<String> userName = new ArrayList<String>();
		userName.add(register.getUsername());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("userName", userName);
		System.out.println(model);
		return new ModelAndView("setting", model);
	}

}
