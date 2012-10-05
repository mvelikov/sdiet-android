package eu.mihailvelikov.scarsdale;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DatesDataSource {
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private String[] allColumns = {
			MySQLiteHelper.COLUMN_ID,
			MySQLiteHelper.COLUMN_DAY,
			MySQLiteHelper.COLUMN_MONTH,
			MySQLiteHelper.COLUMN_YEAR
	};
	
	public DatesDataSource(Context context) {
		dbHelper = new MySQLiteHelper(context);
	}
	
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		dbHelper.close();
	}
	
	public MyDate createDate(int day, int month, int year) {
		ContentValues values = new ContentValues();
		return null;
		
	}

	//http://www.vogella.com/articles/AndroidSQLite/article.html
}
