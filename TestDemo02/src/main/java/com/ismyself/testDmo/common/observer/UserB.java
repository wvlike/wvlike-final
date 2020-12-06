package com.ismyself.testDmo.common.observer;

import java.util.Observable;
import java.util.Observer;

/**
 * package com.ismyself.testDmo.common.observer;
 *
 * @auther txw
 * @create 2020-07-12
 * @description：
 */
public class UserB implements Observer {

    private Observable observable;

    public UserB() {
    }

    public UserB(Observable ob) {
        this.observable = ob;
        ob.addObserver(this);
    }

    /**
     * This method is called whenever the observed object is changed. An
     * application calls an <tt>Observable</tt> object's
     * <code>notifyObservers</code> method to have all the object's
     * observers notified of the change.
     *
     * @param o   the observable object.
     * @param arg an argument passed to the <code>notifyObservers</code>
     */
    @Override
    public void update(Observable o, Object arg) {

        if (o instanceof ObserverCore) {
            ObserverCore obser = (ObserverCore) o;
            System.out.println(obser.getName());
            System.out.println(obser.getNum());
            System.out.println(obser.getFlag());
        }

    }
}
