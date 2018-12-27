package org.springmvc.file.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springmvc.file.dao.BookDao;
import org.springmvc.file.model.Book;

@Transactional
@Service
public class BookService {

	@Autowired
	BookDao bookDao;

	public void persist(Book entity) {

		bookDao.persist(entity);

	}

	public void update(Book entity) {

		bookDao.update(entity);

	}

	public Book findById(String id) {

		Book book = bookDao.findById(id);

		return book;
	}

	public List<Book> findByTitle(String title) {

		List<Book> books = bookDao.findByTitle(title);

		return books;

	}

	public void delete(String id) {

		Book book = bookDao.findById(id);
		bookDao.delete(book);

	}

	public List<Book> findAll() {

		List<Book> books = bookDao.findAll();

		return books;
	}

	public void deleteAll(String filename) {

		bookDao.deleteAll(filename);

	}

}
