package test;

import library.interfaces.daos.IBookHelper;
import library.interfaces.entities.IBook;

public class BookHelperStub implements IBookHelper{

	@Override
	public IBook makeBook(String author, String title, String callNumber, int id) {
		return null;
	}



}
