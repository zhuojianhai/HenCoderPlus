package com.hencoder.a19_io;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public  class StateMachine {
    /**
     * 使用读写锁，可用于多线程中，
     * 注意 锁升级，会产生死锁，同一个线程在没有释放读锁的情况下去申请写锁是不成立的，
     * 不然会产生死锁
     */
    private final ReadWriteLock mLock = new ReentrantReadWriteLock();
    private final ReentrantReadWriteLock.WriteLock mWriteLock = (ReentrantReadWriteLock.WriteLock) mLock.writeLock();
    private final ReentrantReadWriteLock.ReadLock mReadLock = (ReentrantReadWriteLock.ReadLock) mLock.readLock();

    /**
     * 0
     */
    private static final int STATE_ZERO      = 0x00000000;
    /**
     * 1
     */
    private static final int STATE_ONE       = 0x00000001;
    /**
     * 10
     */
    private static final int STATE_TWO       = 0x00000002;
    /**
     * 100
     */
    private static final int STATE_THREE     = 0x00000004;
    /**
     * 1000
     */
    private static final int STATE_FOUR      = 0x00000008;

    /**
     * 10000
     */
    private static final int STATE_FIVE      = 0x00000010;
    /**
     * 100000
     */
    private static final int STATE_SIX       = 0x00000020;


    private int mState, mInitState;

    private static final long serialVersionUID = 1L;

    /**
     * 限定状态范围
     */
    @IntDef(flag = true, value = {STATE_ONE, STATE_TWO, STATE_THREE, STATE_FOUR, STATE_FIVE, STATE_SIX,})
    @Retention(RetentionPolicy.SOURCE)
    public @interface STATE {
    }


    /**
     * 通过 | 增加状态
     * @param flag
     */
    public void addState(@STATE final int flag) {
        updateWriteState(new Callback<Void>() {
            @Override
            public Void callback() {
                mState |= flag;
                return null;
            }
        });
    }

    /**
     * 通过 &(~flag) 移除状态
     * @param flag
     */
    public void removeState(@STATE final int flag) {

        updateWriteState(new Callback<Void>() {
            @Override
            public Void callback() {
                mState &= ~flag;
                return null;
            }
        });
    }

    /**
     * 清除所有状态包括 初始状态和 当前状态
     */
    public void clearState() {

        updateWriteState(new Callback<Void>() {
            @Override
            public Void callback() {
                mInitState = mState &= STATE_ZERO;
                return null;
            }
        });
    }

    /**
     * 重置当前状态为初始状态
     */
    public void resetState() {

        updateWriteState(new Callback<Void>() {
            @Override
            public Void callback() {
                mState &= STATE_ZERO;
                mState |= mInitState;
                return null;
            }
        });
    }

    /**
     * 比较两状态机 当前状态是否相同
     * @param state
     * @return
     */
    public boolean compareState(final StateMachine state) {

        return updateReadState(new Callback<Boolean>() {
            @Override
            public Boolean callback() {
                return (mState ^ state.getCurrentState()) == 0;
            }
        });
    }


    /**
     * * 比较当前状态是否相同
     * @param state
     * @return
     */
    private boolean compareState(@STATE final int state) {

        return updateReadState(new Callback<Boolean>() {
            @Override
            public Boolean callback() {
                return (mState ^ state) == 0;
            }
        });
    }

    /**
     * 获取两状态机 当前不同的状态
     * @param state
     * @return
     */
    public int getDiffState(final StateMachine  state) {

        return updateReadState(new Callback<Integer>() {
            @Override
            public Integer callback() {
                return mState ^ state.getCurrentState();
            }
        });
    }

    /**
     * 获取当前不同的状态
     * @param state
     * @return
     */
    private int getDiffState(@STATE final int state) {

        return updateReadState(new Callback<Integer>() {
            @Override
            public Integer callback() {
                return mState ^ state;
            }
        });
    }

    /**
     * 获取状态机当前状态
     * @return
     */
    public int getCurrentState() {
        return mState;
    }

    /**
     * 获取状态机某二进制位的状态
     * @param state
     * @return
     */
    public int getState(@STATE final int state) {

        return updateReadState(new Callback<Integer>() {
            @Override
            public Integer callback() {
                return mState & state;
            }
        });
    }

    /**
     * 返回当前状态的二进制字符串形式
     * @param state
     * @return
     */
    public String getStateWithBinary(@STATE final int state) {

        return updateReadState(new Callback<String>() {
            @Override
            public String callback() {
                return Integer.toBinaryString(getState(state));
            }
        });
    }


    /**
     * 查询某二进制位的状态
     * @param state
     * @return
     */
    public boolean hasState(@STATE final int state) {

        return updateReadState(new Callback<Boolean>() {
            @Override
            public Boolean callback() {
                return getState(state) == state;
            }
        });

    }


    /**
     * 通过写锁，更新操作
     * @param callback
     * @param <T>
     * @return
     */
    private <T> T updateWriteState(Callback<T> callback) {

        mWriteLock.lock();
        try {
            if (callback != null) {
                return callback.callback();
            }
        } finally {
            mWriteLock.unlock();
        }
        return null;
    }

    /**
     * * 通过读锁，查询操作
     * @param callback
     * @param <T>
     * @return
     */
    private <T> T updateReadState(Callback<T> callback) {

        mReadLock.lock();
        try {
            if (callback != null) {
                return callback.callback();
            }
        } finally {
            mReadLock.unlock();
        }
        return null;
    }

    /**
     * 对扩展开放
     * @param <T>
     */
    private static interface Callback<T> {
        T callback();
    }


    /**
     * 静态方法创建实例对象
     * @param initState
     * @return
     */
    public static StateMachine createStateMachine(@STATE int initState) {
        return new StateMachine(initState);
    }

    public static StateMachine createStateMachine() {
        return createStateMachine(STATE_ZERO);
    }


    /**
     * 有参构造
     * @param initState
     */
    private StateMachine(@STATE int initState) {
        mState = mInitState = initState;
    }

    /**
     * 构建私有化，提供默认构造，允许反射创建
     */
    private StateMachine() {
    }


    @Override
    public String toString() {
        return "InitState "+ Integer.toBinaryString(mInitState) + "   current State : " + Integer.toBinaryString(mState);
    }

    public  static <T> void println(T t){
        System.out.println(t);
    }
}