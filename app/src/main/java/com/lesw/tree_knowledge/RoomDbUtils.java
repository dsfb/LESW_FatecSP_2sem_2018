package com.lesw.tree_knowledge;

import android.arch.persistence.room.RoomSQLiteQuery;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RoomDbUtils {
    public static boolean insertKnowledgeByNameAndUp(String name, int up, Context context) {
        Knowledge[] knowledgeArray = {new Knowledge(name, up)};
        return RoomDbUtils.insertKnowledgeArray(knowledgeArray, context);
    }

    public static boolean insertKnowledgeArray(Knowledge[] knowledgeArray, Context context) {
        final Single<Boolean> knowledgeObservable = Single.create(emitter -> {
            Thread thread = new Thread(() -> {
                try {
                    AppDatabase.getInstance(context).knowledgeDao().insertAll(knowledgeArray);
                    emitter.onSuccess(true);
                } catch (Exception e) {
                    emitter.onError(e);
                }
            });
            thread.start();
        });

        final AtomicReference<Boolean> ref = new AtomicReference<>();
        knowledgeObservable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(k -> ref.set(k));
        return ref.get();
    }

    public static Knowledge getKnowledgeById(int id, Context context) {
        final Single<Knowledge> knowledgeObservable = Single.create(emitter -> {
            Thread thread = new Thread(() -> {
                try {
                    Knowledge knowledge = AppDatabase.
                            getInstance(context).
                            knowledgeDao().findById(id);

                    emitter.onSuccess(knowledge);
                } catch (Exception e) {
                    emitter.onError(e);
                }
            });
            thread.start();
        });

        final AtomicReference<Knowledge> ref = new AtomicReference<>();
        knowledgeObservable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(k -> ref.set(k));
        return ref.get();
    }

    public static List<Employee> getAllEmployees(Context context) {
        final Single<List<Employee>> listEmployeeObserved = Single.create(emitter -> {
            Thread thread = new Thread(() -> {
                try {
                    List<Employee> le = AppDatabase.
                            getInstance(context).
                            employeeDao().getAll();

                    emitter.onSuccess(le);
                } catch (Exception e) {
                    emitter.onError(e);
                }
            });
            thread.start();
        });

        final AtomicReference<List<Employee>> ref = new AtomicReference<>();
        listEmployeeObserved.subscribe(k -> ref.set(k));

        List<Employee> employees = ref.get();

        if (employees == null) {
            employees = new ArrayList<>();
        }

        return employees;
    }

    public static boolean insertEmployee(Employee employee, Context context) {
        Employee[] employees = { employee };
        return RoomDbUtils.insertEmployees(employees, context);
    }

    public static boolean insertEmployees(Employee[] employees, Context context) {
        final Single<Boolean> employeeObervable = Single.create(emitter -> {
            Thread thread = new Thread(() -> {
                try {
                    AppDatabase.getInstance(context).employeeDao().insertAll(employees);
                    emitter.onSuccess(true);
                } catch (Exception e) {
                    emitter.onError(e);
                }
            });
            thread.start();
        });

        final AtomicReference<Boolean> ref = new AtomicReference<>();
        employeeObervable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(k -> ref.set(k));
        return ref.get();
    }
}


