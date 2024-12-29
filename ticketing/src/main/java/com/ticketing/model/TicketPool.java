package com.ticketing.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@AllArgsConstructor
@Data
public class TicketPool {
    private final ConcurrentLinkedQueue<Ticket> ticketQueue = new ConcurrentLinkedQueue<>();
    private final int maxTicketCapacity;
    private final int totalTicketsLimit;
    @Getter
    private int totalTicketCount=0;//counter for totalTicketsIssued

    private final Lock lock = new ReentrantLock();
    private final Condition notEmpty = lock.newCondition();
    private final Condition notFull = lock.newCondition();

    private volatile boolean shutdown = false; //for graceful shutdown purposes
    @Getter
    private volatile boolean vendorProductionStopped = false; //flag for vendor's active status

    public TicketPool(int maxTicketCapacity, int totalTicketsLimit) {
        this.maxTicketCapacity = maxTicketCapacity;
        this.totalTicketsLimit = totalTicketsLimit;
    }


    public int getQueueSize(){
        return ticketQueue.size();
    }

    public void addTicket(Ticket ticket) {

        lock.lock();
        try{
            //Checking of total count has reached ort exceeded the limit
            if(totalTicketCount >= totalTicketsLimit){
                vendorProductionStopped = true;
                System.out.println(Thread.currentThread().getName()+ ": Total tickets limit reached. Stopping ticket production");
                return;
            }

            while(ticketQueue.size()>= maxTicketCapacity && !shutdown){
                System.out.println(Thread.currentThread().getName()+ ": Ticket pool is full, waiting...");
                notFull.await();
            }
            ticketQueue.add(ticket);
            totalTicketCount++;
            notEmpty.signalAll();


        }catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }finally {
            lock.unlock();
        }

    }

    public Ticket removeTicket() {

        lock.lock();
        try{
            //Wait while queue is empty and vendors are still producing
            while(ticketQueue.isEmpty() && !vendorProductionStopped && !shutdown){
                System.out.println(Thread.currentThread().getName()+ ": No tickets available, waiting...");
                notEmpty.await();
            }

            //If queue is emptty and vendors have stopped, return null to signal the thread to stop
            if (ticketQueue.isEmpty() && vendorProductionStopped) {
                return null;
            }
            Ticket ticket = ticketQueue.poll();
            notFull.signalAll();
            return ticket;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }finally {
            lock.unlock();
        }
    }

    public void shutdown(){
        lock.lock();
        try{
            shutdown = true;
            notEmpty.signalAll();
            notFull.signalAll();
        }finally {
            lock.unlock();
        }
    }
}
