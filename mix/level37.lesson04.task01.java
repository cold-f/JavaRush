//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.javarush.test.level37.lesson04.task01;

import java.util.ArrayList;
import java.util.Iterator;

public class Solution<T> extends ArrayList<T> {
    public Solution() {
    }

    public static void main(String[] args) {
        Solution list = new Solution();
        list.add(Integer.valueOf(1));
        list.add(Integer.valueOf(2));
        list.add(Integer.valueOf(3));
        int count = 0;
        Iterator var3 = list.iterator();

        while(var3.hasNext()) {
            Integer i = (Integer)var3.next();
            System.out.print(i + " ");
            ++count;
            if(count == 10) {
                break;
            }
        }

    }

    public Iterator<T> iterator() {
        return new Solution.RoundIterator();
    }

    public class RoundIterator implements Iterator<T> {
        private Iterator<T> iterator = Solution.super.iterator();

        public RoundIterator() {
        }

        public boolean hasNext() {
            return Solution.this.size() > 0;
        }

        public T next() {
            if(!this.iterator.hasNext()) {
                this.iterator = Solution.super.iterator();
            }

            return this.iterator.next();
        }

        public void remove() {
            this.iterator.remove();
        }
    }
}
