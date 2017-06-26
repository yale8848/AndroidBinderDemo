// IBookAddedListener.aidl
package ren.yale.android.androidbinderdemo;

// Declare any non-default types here with import statements
import ren.yale.android.androidbinderdemo.Book;

interface IBookAddedListener {
      void bookAdded(in Book book);
}
