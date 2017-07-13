/* Знакомство с дженериками
Параметризируйте классы SomeClass и Solution следующим образом:
1. SomeClass должен работать с типами, которые наследуются от Number;
2. Solution должен работать с типами, которые наследуются от List, который в свою очередь параметризируется типом SomeClass.
*/
public class Solution <T extends List <Solution.SomeClass>>{
    public static class SomeClass <T extends Number> {

    }
}
*****************************************************************************************************************************
*****************************************************************************************************************************
/* Вызов статического метода
Измените статический метод в классе GenericStatic так, чтобы он стал дженериком.
Пример вызова дан в методе main.
*/
public class Solution {
    public static void main(String[] args) {
        Number number = GenericStatic.<Number>someStaticMethod(new Integer(3));
    }
}
class GenericStatic {
    public static <T> T someStaticMethod(T genericObject) {
        System.out.println(genericObject);
        return genericObject;
    }
}
*****************************************************************************************************************************
*****************************************************************************************************************************
/* Простой generics
Параметризируйте класс Solution, чтобы он мог работать со всеми классами, которые наследуются от HashMap.
Метод getMap должен возвращать тип поля map.
*/
public class Solution <T extends HashMap>{
    private T map;

    public Solution(T map) {
        this.map = map;
    }

    public HashMap getMap() {
        return map;
    }

    public static void main(String[] args) {
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("string", 4);
        Solution solution = new Solution(hashMap);
        HashMap mapFromSolution = solution.getMap();
        System.out.println(mapFromSolution.getClass());


        LinkedHashMap<Solution, Solution> hashMap2 = new LinkedHashMap<>();
        hashMap2.put(solution, solution);
        Solution solution2 = new Solution(hashMap2);
        LinkedHashMap mapFromSolution2 = (LinkedHashMap)solution2.getMap();   //need to cast  :(
        System.out.println(mapFromSolution2.getClass());
    }
}
*****************************************************************************************************************************
*****************************************************************************************************************************
/* Несколько суперклассов для дженерика
Дан класс Solution, параметризированный T.
Ограничьте параметр T.
T должен быть наследником класса ClassForGenerics и одновременно реализовывать интерфейс InterfaceForGenerics.
Менять можно только класс Solution.
*/
public class Solution<T extends ClassForGenerics & InterfaceForGenerics> {
    public static void main(String[] args) {
        Solution<TestClassGood> testClassSolution = new Solution<>();
        testClassSolution.check();
    }

    public void check() {
        System.out.println("Works!");
    }

    public static class TestClassGood extends ClassForGenerics implements InterfaceForGenerics {
    }
}
interface InterfaceForGenerics {}
class ClassForGenerics {}
*****************************************************************************************************************************
*****************************************************************************************************************************
/* Wildcards
Перепишите дженерики в методе add импользуя wildcards.
Логику не меняйте.
Не оставляйте закомментированный код.
*/
public class Solution {

    public static <D> void add(List<? super D> destinationList, List<? extends D> sourceList) {
        ListIterator<? super D> destListIterator = destinationList.listIterator();
        ListIterator<? extends D> srcListIterator = sourceList.listIterator();
        for (int i = 0; i < sourceList.size(); i++) {
            destListIterator.add(srcListIterator.next());
        }
    }


    public static void main(String[] args) {
        List<B> destination = new ArrayList<>();
        destination.add(new B());
        List<C> source = new ArrayList<>();
        source.add(new C());
        add(destination, source);
        System.out.println(destination);
        System.out.println(source);


    }

    static class A {
    }

    static class B extends A {
    }

    static class C extends B {
    }
}
/* вывод:
[com.javarush.test.level35.lesson08.task01.Solution$C@61bbe9ba, com.javarush.test.level35.lesson08.task01.Solution$B@610455d6]
        [com.javarush.test.level35.lesson08.task01.Solution$C@61bbe9ba]
        */
        
*****************************************************************************************************************************
*****************************************************************************************************************************
/* extends vs super
Логика всех методов - добавить source в destination.
!!!Расставьте ?, extends и super где необходимо:!!!
1) one - должен работать с одним и тем же типом;
2) two - должен добавлять любых наследников типа T в список, умеющий хранить только тип T;
3) three - должен добавлять объекты типа T в любой список, параметризированный любым родительским классом;
4) four - должен добавлять любых наследников типа T в список, параметризированный любым родительским классом.
Не оставляйте закомментированный код.
*/
public abstract class Solution {
    public abstract <T> void one(List<T> destination, List<T> source);

    public abstract <T> void two(List<T> destination, List<? extends T> source);

    public abstract <T> void three(List<? super T> destination, List<T> source);

    public abstract <T> void four(List<? super T> destination, List<? extends T> source);
}
