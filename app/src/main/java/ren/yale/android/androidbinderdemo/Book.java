package ren.yale.android.androidbinderdemo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Yale on 2017/6/26.
 */

public class Book implements Parcelable{

    public int bookId;
    public String bookName;

    public Book(){

    }

    public Book(int bookId, String bookName) {
        this.bookId = bookId;
        this.bookName = bookName;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(bookId);
        dest.writeString(bookName);
    }

    public void readFromParcel(Parcel in){
        bookId = in.readInt();          //先读出age，保持与写同顺序
        bookName = in.readString();  //其次读出name，保持与写同顺序
    }

    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {
        @Override public Book createFromParcel(Parcel source) {
            return new Book(source);
        }

        @Override public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    private Book(Parcel source) {
        bookId = source.readInt();
        bookName = source.readString();
    }

    @Override public String toString() {
        return "ID: " + bookId + ", BookName: " + bookName;
    }
}
