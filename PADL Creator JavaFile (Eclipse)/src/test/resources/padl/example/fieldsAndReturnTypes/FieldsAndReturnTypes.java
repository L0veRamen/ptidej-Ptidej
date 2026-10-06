package padl.example.fieldsAndReturnTypes;

import java.util.List;
import java.util.Map;

public abstract class FieldsAndReturnTypes {
  // refaire mettre les meme choses en return type, en type field et en param
  // e faire demain matin inchaAllah et voir ce que donne le .class avec les
  // memes donnees, les passer au comparateur...

  // Fields
  int q;

  String compilerCompliance = "1.6";

  Integer i;

  NamedReader[] classpathEntries;

  int[] tabI;

  Object[] tabObjects;

  Object[][][] multiTabObjects;

  List l;

  List<Object>[] tabListOjects;

  List<NamedReader> listNamedReader;

  List<NamedReader[]> listTabsNamedReader;

  List<NamedReader[][]> listMultiTabsNamedReader;

  List<Integer> listIntegers;

  List<List<Integer>> listListsIntegers;

  List[] listTabsObjects; // array

  Map<String, String> map;

  // voir le cas d'un member class comme type et compare avec le resultat en
  // .class

  // Return Types
  protected abstract int m1();

  protected abstract String m2();

  protected abstract Integer m3();

  protected abstract int[] m4();

  protected abstract NamedReader[] m5();

  protected abstract NamedReader[][] m6();

  protected abstract Object[][] m7();

  protected abstract List<Object>[] m8();

  protected abstract List<NamedReader> m9();

  protected abstract List<NamedReader[]> m10();

  protected abstract List<NamedReader[][]> m11();

  protected abstract List m12();

  protected abstract List<Integer> m13();

  protected abstract List<List<Integer>> m14();

  protected abstract List[] m15();

  protected abstract Map<String, String> m16();

  protected List<NamedReader[]> methodWithParams(
      int q,
      String compilerCompliance,
      Integer i,
      NamedReader[] classpathEntries,
      int[] tabI,
      Object[] tabObjects,
      Object[][][] multiTabObjects,
      List l,
      List<Object>[] tabListOjects,
      List<NamedReader> listNamedReader,
      List<NamedReader[]> listTabsNamedReader,
      List<NamedReader[][]> listMultiTabsNamedReader,
      List<Integer> listIntegers,
      List<List<Integer>> listListsIntegers,
      List[] listTabsObjects,
      Map<String, String> map) {

    return null;
  }
}
