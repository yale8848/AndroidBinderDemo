package ren.yale.android.androidbinderdemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BookService extends Service {

    public static final String TAG="aidl-demo";
    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();
    private RemoteCallbackList<IBookAddedListener> mRemoteCallbackList = new RemoteCallbackList<>();



    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            synchronized (BookService.class){
                Book book = (Book) msg.obj;
                int num = mRemoteCallbackList.beginBroadcast();
                for (int  i = 0;i<num;i++){
                    IBookAddedListener listener =  mRemoteCallbackList.getBroadcastItem(i);
                    try {
                        listener.bookAdded(book);
                        mRemoteCallbackList.finishBroadcast();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };
    private Binder mIBinder = new IBookManager.Stub(){
        @Override
        public List<Book> getBook() throws RemoteException {
            return mBookList;
        }
        @Override
        public Book addBook(Book book) throws RemoteException {
            mBookList.add(book);
            mHandler.obtainMessage(1,book).sendToTarget();
            Log.d(TAG,"BookService addBook ");
            return book;

        }

        @Override
        public void register(IBookAddedListener listener) throws RemoteException {
            mRemoteCallbackList.register(listener);

        }
        @Override
        public void unRegister(IBookAddedListener listener) throws RemoteException {
            mRemoteCallbackList.unregister(listener);
        }
    };

    public BookService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mIBinder;
    }
}
