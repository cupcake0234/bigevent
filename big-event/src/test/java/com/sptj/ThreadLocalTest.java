package com.sptj;

import org.junit.jupiter.api.Test;

public class ThreadLocalTest {

    @Test
    public void testThreadlocalSetAndGet(){
        //
        ThreadLocal tl = new ThreadLocal();

        //
        new Thread(()->{
            tl.set("晓燕");
            System.out.println(Thread.currentThread().getName()+": "+tl.get());
            System.out.println(Thread.currentThread().getName()+": "+tl.get());
            System.out.println(Thread.currentThread().getName()+": "+tl.get());
        },"蓝色").start();


        new Thread(()->{
            tl.set("药城");
            System.out.println(Thread.currentThread().getName()+": "+tl.get());
            System.out.println(Thread.currentThread().getName()+": "+tl.get());
            System.out.println(Thread.currentThread().getName()+": "+tl.get());
        },"绿色").start();

    }
}
