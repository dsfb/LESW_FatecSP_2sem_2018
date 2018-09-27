package com.lesw.tree_knowledge;

import android.content.Context;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RoomDbUtils {
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
        return ref.get();
    }
}


