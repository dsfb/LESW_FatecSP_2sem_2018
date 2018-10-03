package com.lesw.tree_knowledge;

import android.arch.persistence.room.RoomSQLiteQuery;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.Completable;
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
        final AtomicReference<Boolean> ref = new AtomicReference<>();

        Completable.fromAction(() -> AppDatabase.getInstance(context).knowledgeDao().insertAll(knowledgeArray))
            .subscribeOn(Schedulers.io())
            .subscribe(() -> {
                ref.set(true);
            }, throwable -> {
                ref.set(false);
            });

        Boolean value;

        do {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Log.e("TreeKnowledge", "Opa! insertKnowledgeArray forçou InterruptedException!");
            } finally {
                value = ref.get();
            }
        } while (value == null);

        return value;
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

        Knowledge knowledge;

        do {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Log.e("TreeKnowledge", "Opa! getKnowledgeById forçou InterruptedException!");
            } finally {
                knowledge = ref.get();
            }
        } while (knowledge == null);

        return knowledge;
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
        listEmployeeObserved.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(k -> ref.set(k));

        List<Employee> employees;

        do {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Log.e("TreeKnowledge", "Opa! getAllEmployees forçou InterruptedException!");
            } finally {
                employees = ref.get();
            }
        } while (employees == null);

        return employees;
    }

    public static boolean insertEmployee(Employee employee, Context context) {
        Employee[] employees = { employee };
        return RoomDbUtils.insertEmployees(employees, context);
    }

    public static boolean insertEmployees(Employee[] employees, Context context) {
        final AtomicReference<Boolean> ref = new AtomicReference<>();

        Completable.fromAction(() -> AppDatabase.getInstance(context).employeeDao().insertAll(employees))
                .subscribeOn(Schedulers.io())
                .subscribe(() -> {
                    ref.set(true);
                }, throwable -> {
                    ref.set(false);
                });

        Boolean value;

        do {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Log.e("TreeKnowledge", "Opa! insertEmployees forçou InterruptedException!");
            } finally {
                value = ref.get();
            }
        } while (value == null);

        return value;
    }
}


