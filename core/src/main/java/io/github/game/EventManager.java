package io.github.game;

import java.util.ArrayList;
import java.util.List;

class EventManager {
    private List<Observer> observers = new ArrayList<>();

    void subscribe(Observer observer) {
        observers.add(observer);
    }

    void unsubscribe(Observer observer) {
        observers.remove(observer);
    }

    void notify(String event, Object data) {
        for (Observer observer : observers) {
            observer.update(event, data);
        }
    }
    void enemyKilled() {
        notify("EnemyKilled", null); 
    }
}