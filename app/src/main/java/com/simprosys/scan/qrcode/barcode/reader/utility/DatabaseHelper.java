package com.simprosys.scan.qrcode.barcode.reader.utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper {

	Database command;
	SQLiteDatabase connection;
	Context context;

	public static int READ_MODE = 0;
	public static int WRITE_MODE = 1;

	class Database extends SQLiteOpenHelper {

		public Database(Context context, String name, CursorFactory factory,
                        int version) {
			super(context, name, factory, version);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			//db.execSQL("CREATE TABLE if not exists History(id integer PRIMARY KEY AUTOINCREMENT,type varchar(55),details text,datetime varchar(50),detailsFormated text)");

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			//db.execSQL("CREATE TABLE if not exists History(id integer PRIMARY KEY AUTOINCREMENT,type varchar(55),details text,datetime varchar(50),detailsFormated text)");
		}

	}

	public DatabaseHelper(Context con) {
		this.context = con;
	}

	public void open(int mode) {
		command = new Database(context, "QRCode", null, 1);
		if (mode == READ_MODE)
			connection = command.getReadableDatabase();
		else
			connection = command.getWritableDatabase();
	}

	public Cursor selectQuery(String query) {
		return connection.rawQuery(query, null);
	}

	public void executeQuery(String query) {

		connection.execSQL(query);
	}

	public void insertData(String tableName, ContentValues cv)
	{
		connection.insert(tableName, null, cv);
	}

	public void close() {
		command.close();
		connection.close();
	}
}
