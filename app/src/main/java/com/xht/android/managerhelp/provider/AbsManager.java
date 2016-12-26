package com.xht.android.managerhelp.provider;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * 2016-12-16
 */
public interface AbsManager {
	
	Cursor query(SQLiteDatabase db, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder);
	
	public abstract Uri insert(SQLiteDatabase database, Uri uri, ContentValues cValues);
	
	int delete(SQLiteDatabase db, Uri uri, String where, String[] whereArgs);
	
	int update(SQLiteDatabase db, Uri uri, ContentValues values,
			   String where, String[] whereArgs);

}
