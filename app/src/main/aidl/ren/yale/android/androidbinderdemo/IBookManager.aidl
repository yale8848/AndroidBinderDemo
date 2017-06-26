// IBookManager.aidl
package ren.yale.android.androidbinderdemo;

// Declare any non-default types here with import statements
import ren.yale.android.androidbinderdemo.Book;
import ren.yale.android.androidbinderdemo.IBookAddedListener;

interface IBookManager {

     List<Book> getBook();
     Book addBook(in Book book);

     void register(IBookAddedListener listener );
     void unRegister(IBookAddedListener listener );

}
