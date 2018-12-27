package org.springmvc.file.dao;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import org.springmvc.file.model.Book;

@Repository
public class BookDao implements BookDaoInterface<Book, String> {

	public BookDao() {

	}

	@Override
	public void persist(Book book) {
		
		//https://stackoverflow.com/questions/22113310/write-multiple-objects-in-a-file-and-read-them?rq=1
		try  {
			boolean exists = new File("d://booklib.ser").exists();
		    FileOutputStream fos = new FileOutputStream("d://booklib.ser", true);
		    ObjectOutputStream oos = exists ? new ObjectOutputStream(fos) {
		                protected void writeStreamHeader() throws IOException {
		                    reset();
		                }
		            }:new ObjectOutputStream(fos);
		            
		    oos.writeObject(book);    
		    oos.close();
		    fos.close();
		}  
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void update(Book entity) {
		ArrayList<Book> books = (ArrayList<Book>) findAll();
		
		ArrayList<Book> filtered  = (ArrayList<Book>) books.stream().filter(book -> !book.getId().equals(entity.getId())).collect(Collectors.toList());
		System.out.println("update method :: Filtered book list " + filtered);
		deleteAll("d://booklib.ser");
		System.out.println("update method :: Deleted file");
		filtered.add(entity);
		System.out.println("update method :: Added :: "+entity);
		for(Book book : filtered) {
			persist(book);
		}
	}

	@Override
	public Book findById(String id) {
		ArrayList<Book> books = (ArrayList<Book>) findAll();
		
		Book searched = books.stream().filter(book -> book.getId().equals(id)).findFirst().get();
		System.out.println("Searched book by id " + searched);
		
		return searched;
	}

	@Override
	public void delete(Book entity) {
		ArrayList<Book> books = (ArrayList<Book>) findAll();
		
		ArrayList<Book> filtered  = (ArrayList<Book>) books.stream().filter(book -> !book.getId().equals(entity.getId())).collect(Collectors.toList());
		System.out.println("update method :: Filtered book list " + filtered);
		deleteAll("d://booklib.ser");
		System.out.println("update method :: Deleted file");
		for(Book book : filtered) {
			persist(book);
		}

	}

	@Override
	public List<Book> findAll() {
		Book book = null;

		ArrayList<Book> books = new ArrayList<Book>();

		boolean cont = true;
		try (FileInputStream fileIn = new FileInputStream("d://booklib.ser");
				ObjectInputStream in = new ObjectInputStream(fileIn)) {

			while (cont) {
				book = (Book) in.readObject();
				if (book != null) {
					System.out.println("Found book :: " + book);
					books.add(book);
					System.out.println("Added to list book :: " + books.get(0).getTitle());
					book = null;
				} else
					cont = false;
					
			}
		
		} catch (EOFException e) {
			System.out.println("EOF caught");
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return books;
	}

	@Override
	public void deleteAll(String filename) {
		
		
		File f = new File(filename);
	    if (f.delete()) {
	      System.out.println(filename + " deleted sucessfully...");
	    } else {
	      System.out.println(filename + " deletion failed!");
	    }

	}

	
	@Override
	public List<Book> findByTitle(String title) {
		ArrayList<Book> books = (ArrayList<Book>) findAll();
		
		ArrayList<Book> searched = (ArrayList<Book>) books.stream().filter(book -> book.getTitle().equalsIgnoreCase(title)).collect(Collectors.toList());
		System.out.println("Searched book" + searched);
		return searched;
	}

}
