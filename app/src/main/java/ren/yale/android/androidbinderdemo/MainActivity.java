package ren.yale.android.androidbinderdemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private IBookManager mIBookManager;
    private int mCount= 0;
    public static final String TAG="aidl-demo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void bindService(View v){
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("ren.yale.android.androidbinderdemo","ren.yale.android.androidbinderdemo.BookService"));
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }
    public void addBook(View v){
        addOneBook();
    }

    private void addOneBook(){
        int id = mCount++;
        Book book = new Book(id,""+id);
        Book b = null;
        try {
            b = mIBookManager.addBook(book);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
    }

    public void getBooks(View v){

        try {
            for (Book b: mIBookManager.getBook()) {
                Log.d(TAG,""+b.toString());
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
    private IBookAddedListener mIBookAddedListener = new IBookAddedListener.Stub() {
        @Override
        public void bookAdded(Book b) throws RemoteException {
           Log.d(TAG,"one book added: "+b.toString());
        }
    };
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            Log.d(TAG,"connect service success");
            mIBookManager = IBookManager.Stub.asInterface(service);
            try {
                mIBookManager.register(mIBookAddedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            try {
                mIBookManager.unRegister(mIBookAddedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            mIBookManager = null;
        }
    };

    //in
/*
*                     data.enforceInterface(DESCRIPTOR);
                    ren.yale.android.androidbinderdemo.Book _arg0;
                    if ((0 != data.readInt())) {
                        _arg0 = ren.yale.android.androidbinderdemo.Book.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    ren.yale.android.androidbinderdemo.Book _result = this.addBook(_arg0);
                    reply.writeNoException();
                    if ((_result != null)) {
                        reply.writeInt(1);
                        _result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;




                                     data.enforceInterface(DESCRIPTOR);
                    ren.yale.android.androidbinderdemo.Book _arg0;
                    if ((0 != data.readInt())) {
                        _arg0 = ren.yale.android.androidbinderdemo.Book.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    this.bookAdded(_arg0);
                    reply.writeNoException();
                    return true;



                    */







    //out
    /**
     *
     *data.enforceInterface(DESCRIPTOR);
     ren.yale.android.androidbinderdemo.Book _arg0;
     _arg0 = new ren.yale.android.androidbinderdemo.Book();
     ren.yale.android.androidbinderdemo.Book _result = this.addBook(_arg0);
     reply.writeNoException();
     if ((_result != null)) {
     reply.writeInt(1);
     _result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
     } else {
     reply.writeInt(0);
     }
     if ((_arg0 != null)) {
     reply.writeInt(1);
     _arg0.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
     } else {
     reply.writeInt(0);
     }
     return true;
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *                     data.enforceInterface(DESCRIPTOR);
     ren.yale.android.androidbinderdemo.Book _arg0;
     _arg0 = new ren.yale.android.androidbinderdemo.Book();
     this.bookAdded(_arg0);
     reply.writeNoException();
     if ((_arg0 != null)) {
     reply.writeInt(1);
     _arg0.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
     } else {
     reply.writeInt(0);
     }
     return true;
     */
}
