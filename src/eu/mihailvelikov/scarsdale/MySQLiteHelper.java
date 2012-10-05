package eu.mihailvelikov.scarsdale;

import android.database.sqlite.*;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {
	public static final String TABLE_DATES = "dates";
	public static final String COLUMN_ID = "id";
	private static final String DATABASE_NAME = "dates.db";
	public static final String COLUMN_DAY = "day";
	public static final String COLUMN_YEAR = "year";
	public static final String COLUMN_MONTH = "month";
	private static final int DATABASE_VERSION = 1;
	
	private static final String DATABASE_CREATE = "CREATE TABLE" + TABLE_DATES
			+ "(" + COLUMN_ID + " INTEGER PRIMERY KEY AUTOINCREMENT, "
			+ COLUMN_DAY + " INTEGER NOT NULL, "
			+ COLUMN_MONTH + " INTEGER NOT NULL, "
			+ COLUMN_YEAR + " INTEGER NOT NULL);"
			;
	public MySQLiteHelper(Context context) {
	    super(context, DATABASE_NAME, null, DATABASE_VERSION);
	  }
	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(MySQLiteHelper.class.getName(),
		        "Upgrading database from version " + oldVersion + " to "
		            + newVersion + ", which will destroy all old data");
		    db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATES);
		    onCreate(db);
	}
}
