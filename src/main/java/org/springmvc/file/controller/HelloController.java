package org.springmvc.file.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springmvc.file.model.Book;
import org.springmvc.file.service.BookService;

@Controller
public class HelloController {

	@Autowired
	BookService bookService;

	/*
	 * @ModelAttribute("list") public List<Book> getList() {
	 * 
	 * List<Book> list = bookService.findAll();
	 * 
	 * return list; }
	 */

	@RequestMapping("/")
	public String home(@RequestParam(value = "sortby", required = false) String sortby,
			@RequestParam(value = "searchString", required = false) String searchString, Model m) {

		System.out.println("request parm ::" + sortby);

		if (sortby == null && searchString == null) {
			List<Book> list = bookService.findAll();
			m.addAttribute("list", list);

		} else if (sortby == null && searchString != null) {
			List<Book> searchedlist = bookService.findByTitle(searchString);
			System.out.println("Searched List :: " + searchedlist);
			m.addAttribute("list", searchedlist);
			
		} else if (sortby.equals("title")) {

			List<Book> sortedList = bookService.findAll().stream()
					.sorted((o1, o2) -> o1.getTitle().compareTo(o2.getTitle())).collect(Collectors.toList());
			System.out.println("Sorted List :: " + sortedList);
			m.addAttribute("list", sortedList);
		}

		return "home";
	}

	@RequestMapping("/bookform")
	public String showform(ModelMap m) {
		m.addAttribute("command", new Book());
		return "bookform";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@ModelAttribute("book") Book book) {
		bookService.persist(book);
		return "redirect:/";
	}

	@RequestMapping(value = "/editbook/{id}")
	public String edit(@PathVariable String id, ModelMap m) {
		Book book = bookService.findById(id);
		m.addAttribute("command", book);
		return "bookeditform";
	}

	@RequestMapping(value = "/editsave", method = RequestMethod.POST)
	public String editsave(@ModelAttribute("book") Book book) {
		bookService.update(book);
		return "redirect:/";
	}

	@RequestMapping(value = "/deletebook/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable String id) {
		bookService.delete(id);
		return "redirect:/";
	}

	@RequestMapping(value = "/sort", method = RequestMethod.POST)
	public String sort() {
		return "redirect:/?sortby=title";
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String search(@RequestParam("searchString") String searchString) {
		System.out.println("Search term :: " + searchString);
		return "redirect:/?searchString=" + searchString;
	}
}
