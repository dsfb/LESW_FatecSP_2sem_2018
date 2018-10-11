package com.lesw.tree_knowledge;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.common.base.Converter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    // Acesso ao Banco de Dados
    static private SQLiteDatabase db = null;

    // Logcat tag
    private static final String LOG = "TreeKnowledge-DbHelper";

    // If you change the database schema, you must increment the database version.
    // Database Version
    public static final int DATABASE_VERSION = 1;

    // Database Name
    public static final String DATABASE_NAME = "TreeKnowledge.db";

    // Table Names
    private static final String TABLE_KNOWLEDGE = "knowledge";
    private static final String TABLE_EMPLOYEE = "employee";
    private static final String TABLE_CERTIFICATION = "certification";

    // Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT = "created_at";
    private static final String KEY_NAME = "name";

    // KNOWLEDGE Table - column names
    private static final String KEY_UP = "up";

    // EMPLOYEE Table - column names
    private static final String KEY_ROLE = "role";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PIN = "pin";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_FUNCTION = "function";
    private static final String KEY_KNOWLEDGE_SET = "knowledge_set";

    // CERTIFICATION Table - column names
    private static final String KEY_KNOWLEDGE = "knowledge";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_DATE = "date";
    private static final String KEY_STATUS = "status";
    private static final String KEY_CERTIFICATION = "certification";

    // Table Create Statements
    // Knowledge table create statement
    private static final String CREATE_TABLE_KNOWLEDGE = "CREATE TABLE "
            + TABLE_KNOWLEDGE + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            KEY_NAME + " TEXT, " + KEY_UP + " INTEGER NOT NULL, " + KEY_CREATED_AT + " DATETIME" +
            ")";

    // Employee table create statement
    private static final String CREATE_TABLE_EMPLOYEE = "CREATE TABLE " + TABLE_EMPLOYEE
            + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            KEY_NAME + " TEXT, " + KEY_ROLE + " TEXT, " + KEY_EMAIL + " TEXT, " + KEY_PIN +
            " TEXT, " + KEY_PASSWORD + " TEXT, " + KEY_FUNCTION + " TEXT, " + KEY_KNOWLEDGE_SET +
            " TEXT, " + KEY_CREATED_AT + " DATETIME" + ")";

    // Certification table create statement
    private static final String CREATE_TABLE_CERTIFICATION = "CREATE TABLE "
            + TABLE_CERTIFICATION + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + KEY_KNOWLEDGE + " TEXT, " + KEY_USERNAME + " TEXT, " + KEY_DATE + " TEXT, "
            + KEY_STATUS + " TEXT, " + KEY_CERTIFICATION + " TEXT, " + KEY_CREATED_AT +
            " DATETIME" + ")";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_KNOWLEDGE);
        db.execSQL(CREATE_TABLE_EMPLOYEE);
        db.execSQL(CREATE_TABLE_CERTIFICATION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KNOWLEDGE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CERTIFICATION);

        // create new tables
        onCreate(db);
    }

    // getting access to database
    private SQLiteDatabase getDb() {
        if (db == null || !db.isOpen()) {
            db = this.getWritableDatabase();
        }

        return db;
    }

    // closing database
    public void closeDB() {
        if (db != null && db.isOpen())
            db.close();
    }

    /**
     * get datetime
     * */
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    // ------------------------ "knowledge" table methods ----------------//

    /*
     * Creating a knowledge
     */
    public void createKnowledge(Knowledge knowledge) {
        db = getDb();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, knowledge.getName());
        values.put(KEY_UP, knowledge.getUp());
        values.put(KEY_CREATED_AT, getDateTime());

        // insert row
        db.insert(TABLE_KNOWLEDGE, null, values);
    }

    // Função auxiliar para pegar o Knowledge a partir do Cursor!
    public Knowledge getKnowledgeFromCursor(Cursor c) {
        Knowledge k = new Knowledge();
        k.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        k.setName((c.getString(c.getColumnIndex(KEY_NAME))));
        k.setUp(c.getInt(c.getColumnIndex(KEY_UP)));

        return k;
    }

    /**
     * get single knowledge
     */
    public Knowledge getKnowledge(long id) {
        db = getDb();

        String selectQuery = "SELECT  * FROM " + TABLE_KNOWLEDGE + " WHERE "
                + KEY_ID + " = " + id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        return this.getKnowledgeFromCursor(c);
    }

    /**
     * getting all knowledge
     * */
    public List<Knowledge> getAllKnowledge() {
        List<Knowledge> knowledgeList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_KNOWLEDGE;

        Log.e(LOG, selectQuery);

        db = getDb();

        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                // adding to knowledge list
                knowledgeList.add(this.getKnowledgeFromCursor(c));
            } while (c.moveToNext());
        }

        return knowledgeList;
    }

    /**
     * Updating a knowledge
     */
    public int updateKnowledge(Knowledge knowledge) {
        db = getDb();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, knowledge.getName());
        values.put(KEY_UP, knowledge.getUp());

        // updating row
        return db.update(TABLE_KNOWLEDGE, values, KEY_ID + " = ?",
                new String[] { String.valueOf(knowledge.getId()) });
    }

    /**
     * Deleting a knowledge
     */
    public void deleteKnowledge(long id) {
        db = getDb();

        db.delete(TABLE_KNOWLEDGE, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    // ------------------------ "employee" table methods ----------------//

    /*
     * Creating a employee
     */
    public void createEmployee(Employee employee) {
        db = getDb();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, employee.getName());
        values.put(KEY_ROLE, employee.getRole());
        values.put(KEY_EMAIL, employee.getEmail());
        values.put(KEY_PIN, employee.getPin());
        values.put(KEY_PASSWORD, employee.getPassword());
        values.put(KEY_FUNCTION, Converters.roleEnumToString(employee.getFunction()));
        values.put(KEY_KNOWLEDGE_SET, employee.getKnowledgeSetStr());
        values.put(KEY_CREATED_AT, getDateTime());

        // insert row
        db.insert(TABLE_EMPLOYEE, null, values);
    }

    // Função auxiliar para pegar o Employee a partir do Cursor!
    public Employee getEmployeeFromCursor(Cursor c) {
        Employee e = new Employee();
        e.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        e.setName((c.getString(c.getColumnIndex(KEY_NAME))));
        e.setRole((c.getString(c.getColumnIndex(KEY_ROLE))));
        e.setEmail((c.getString(c.getColumnIndex(KEY_EMAIL))));
        e.setPin((c.getString(c.getColumnIndex(KEY_PIN))));
        e.setPassword((c.getString(c.getColumnIndex(KEY_PASSWORD))));
        e.setFunction(Converters.stringToRoleEnum(c.getString(c.getColumnIndex(KEY_FUNCTION))));
        String str = (c.getString(c.getColumnIndex(KEY_KNOWLEDGE_SET)));

        if (str == null) {
            Log.e("TreeKnowledge", "Opa! KnowledgeSet eh null! getEmployeeFromCursor!");
        }
        e.setKnowledgeSetStr(str);

        return e;
    }

    /**
     * get single employee
     */
    public Employee getEmployee(long id) {
        db = getDb();

        String selectQuery = "SELECT  * FROM " + TABLE_EMPLOYEE + " WHERE "
                + KEY_ID + " = " + id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        return this.getEmployeeFromCursor(c);
    }

    /**
     * getting all employees
     * */
    public List<Employee> getAllEmployees() {
        List<Employee> employeeList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_EMPLOYEE;

        Log.e(LOG, selectQuery);

        db = getDb();

        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                // adding to employee list
                Employee e = this.getEmployeeFromCursor(c);

                employeeList.add(e);
            } while (c.moveToNext());
        }

        return employeeList;
    }

    /**
     * Updating a employee
     */
    public int updateEmployee(Employee employee) {
        db = getDb();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, employee.getName());
        values.put(KEY_ROLE, employee.getRole());
        values.put(KEY_EMAIL, employee.getEmail());
        values.put(KEY_PIN, employee.getPin());
        values.put(KEY_PASSWORD, employee.getPassword());
        values.put(KEY_FUNCTION, Converters.roleEnumToString(employee.getFunction()));
        values.put(KEY_KNOWLEDGE_SET, employee.getKnowledgeSetStr());

        // updating row
        return db.update(TABLE_EMPLOYEE, values, KEY_ID + " = ?",
                new String[] { String.valueOf(employee.getId()) });
    }

    /**
     * Deleting a employee
     */
    public void deleteEmployee(long id) {
        db = getDb();

        db.delete(TABLE_EMPLOYEE, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
    }
}
