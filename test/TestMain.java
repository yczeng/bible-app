package test;

public class TestMain{
  public static void main(String[] args) {
    System.out.println("Hello World");

    JSONBible testbible = new JSONBible("test");
    System.out.println(testbible.get_json());
  }
}
