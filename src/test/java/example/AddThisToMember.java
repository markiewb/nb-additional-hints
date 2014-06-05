/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package example;

/**
 *
 * @author markiewb
 */
public class AddThisToMember {

    public class TestA {

        @SuppressWarnings("ABC")
        void method() {
        }
    }

    public class TestB {

        @SuppressWarnings(value = "ABC")
        void method() {
        }
    }

    public class TestC {

        @SuppressWarnings(value = {"ABC"})
        void method() {
        }
    }

    public class SuperClass {

        public void member() {
        }

        class SubClass extends SuperClass {

            public void method() {
                super.member();
            }

        }
    }

}
