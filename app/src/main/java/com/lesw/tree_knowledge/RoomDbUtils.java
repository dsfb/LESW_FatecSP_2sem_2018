package com.lesw.tree_knowledge;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RoomDbUtils {
    public static Knowledge getKnowledgeById(int id) {
        final Single<Knowledge> knowledgeObservable = Single.create(emitter -> {
            Thread thread = new Thread(() -> {
                try {
                    Knowledge knowledge = AppDatabase.
                            getInstance(ApplicationContextProvider.getContext()).
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

    public static List<Employee> getAllEmployees() {
        final Observable<List<Employee>> observer = Observable.create(emitter -> {
            Thread thread = new Thread(() -> {
                try {
                    List<Employee> le = AppDatabase.
                            getInstance(ApplicationContextProvider.getContext()).
                            employeeDao().getAll();

                    emitter.onSuccess(le);
                } catch (Exception e) {
                    emitter.onError(e);
                }
            });
            thread.start();
        });

        final AtomicReference<List<Employee>> ref = new AtomicReference<>();
        observer.subscribe(k -> ref.set(k));
    }
}


