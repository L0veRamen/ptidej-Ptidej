/**
 * @author Mathieu Lemoine
 * @created 2008-12-15 (月)
 *     <p>Licensed under 3-clause BSD License: Copyright © 2009, Mathieu Lemoine All rights
 *     reserved.
 *     <p>Redistribution and use in source and binary forms, with or without modification, are
 *     permitted provided that the following conditions are met: * Redistributions of source code
 *     must retain the above copyright notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright notice, this list of
 *     conditions and the following disclaimer in the documentation and/or other materials provided
 *     with the distribution. * Neither the name of Mathieu Lemoine nor the names of contributors
 *     may be used to endorse or promote products derived from this software without specific prior
 *     written permission.
 *     <p>THIS SOFTWARE IS PROVIDED BY Mathieu Lemoine ''AS IS'' AND ANY EXPRESS OR IMPLIED
 *     WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *     FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL Mathieu Lemoine BE LIABLE
 *     FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *     (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 *     DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 *     WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 *     ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package jct.test.rsc.jct.kernel;

import java.util.regex.Pattern;

/** Constants list for JCT interfaces */
public final class Constants {
  private Constants() {}

  // Path separators
  public static final char DOT_SEPARATOR = '.';
  public static final char DOLLAR_SEPARATOR = '$';

  public static final char METHOD_MARKER = '(';
  public static final char PARAMETER_SEPARATOR = ',';

  public static final Pattern PARAMETER_SPLITTER_PATTERN =
      Pattern.compile("\\" + Constants.PARAMETER_SEPARATOR);

  // Type markers, separators and patterns
  public static final char ARRAY_MARKER = '[';
  public static final char CLASS_MARKER = 'L';

  public static final char INTERSECTION_MARKER = '&';
  public static final char INTERSECTION_SEPARATOR = '|';

  public static final Pattern INTERSECTION_SPLITTER_PATTERN =
      Pattern.compile("\\" + Constants.INTERSECTION_SEPARATOR);
  public static final Pattern DOLLAR_SPLITTER_PATTERN =
      Pattern.compile("\\" + Constants.DOLLAR_SEPARATOR);

  // Magic Strings
  public static final String ARRAY_TYPE = "$$ARRAY_TYPE$$";
  public static final String PACKAGE_DECLARATION_FILENAME = "package-info.java";

  public static final String CONSTRUCTOR_NAME = "<init>";
  public static final String INSTANCE_INITIALIZER_NAME = ">init<";
  public static final String CLASS_INITIALIZER_NAME = "<clinit>";

  // Gloabl Java API names
  public static final String PACKAGE_JAVA_LANG = "java.lang";

  public static final String PATH_TO_PACKAGE_JAVA_LANG = "java/lang/";
  public static final String CLASSFILE_EXTENSION = ".class";

  public static final String THIS_NAME = "this";
  public static final String SUPER_NAME = "super";
  public static final String CLASS_NAME = "class";

  public static final String LENGTH_NAME = "length";
  public static final String CLONE_NAME = "clone";

  public static final String CLASSNAME_OBJECT = "Object";
  public static final String CLASSNAME_CLASS = "Class";
  public static final String CLASSNAME_VOID = "Void";

  public static final String CLASSNAME_DOUBLE = "Double";
  public static final String CLASSNAME_FLOAT = "Float";
  public static final String CLASSNAME_LONG = "Long";
  public static final String CLASSNAME_INT = "Integer";
  public static final String CLASSNAME_SHORT = "Short";
  public static final String CLASSNAME_BYTE = "Byte";
  public static final String CLASSNAME_BOOLEAN = "Boolean";
  public static final String CLASSNAME_CHAR = "Character";
  public static final String CLASSNAME_STRING = "String";

  public static final String CLASSPATH_OBJECT =
      Constants.PACKAGE_JAVA_LANG + Constants.DOT_SEPARATOR + Constants.CLASSNAME_OBJECT;
  public static final String CLASSPATH_CLASS =
      Constants.PACKAGE_JAVA_LANG + Constants.DOT_SEPARATOR + Constants.CLASSNAME_CLASS;
  public static final String CLASSPATH_VOID =
      Constants.PACKAGE_JAVA_LANG + Constants.DOT_SEPARATOR + Constants.CLASSNAME_VOID;

  public static final String CLASSPATH_DOUBLE =
      Constants.PACKAGE_JAVA_LANG + Constants.DOT_SEPARATOR + Constants.CLASSNAME_DOUBLE;
  public static final String CLASSPATH_FLOAT =
      Constants.PACKAGE_JAVA_LANG + Constants.DOT_SEPARATOR + Constants.CLASSNAME_FLOAT;
  public static final String CLASSPATH_LONG =
      Constants.PACKAGE_JAVA_LANG + Constants.DOT_SEPARATOR + Constants.CLASSNAME_LONG;
  public static final String CLASSPATH_INT =
      Constants.PACKAGE_JAVA_LANG + Constants.DOT_SEPARATOR + Constants.CLASSNAME_INT;
  public static final String CLASSPATH_SHORT =
      Constants.PACKAGE_JAVA_LANG + Constants.DOT_SEPARATOR + Constants.CLASSNAME_SHORT;
  public static final String CLASSPATH_BYTE =
      Constants.PACKAGE_JAVA_LANG + Constants.DOT_SEPARATOR + Constants.CLASSNAME_BYTE;
  public static final String CLASSPATH_BOOLEAN =
      Constants.PACKAGE_JAVA_LANG + Constants.DOT_SEPARATOR + Constants.CLASSNAME_BOOLEAN;
  public static final String CLASSPATH_CHAR =
      Constants.PACKAGE_JAVA_LANG + Constants.DOT_SEPARATOR + Constants.CLASSNAME_CHAR;
  public static final String CLASSPATH_STRING =
      Constants.PACKAGE_JAVA_LANG + Constants.DOT_SEPARATOR + Constants.CLASSNAME_STRING;

  public static final String CLASS_BINARYNAME_OBJECT =
      Constants.CLASS_MARKER + Constants.CLASSPATH_OBJECT;
  public static final String CLASS_BINARYNAME_CLASS =
      Constants.CLASS_MARKER + Constants.CLASSPATH_CLASS;
  public static final String CLASS_BINARYNAME_VOID =
      Constants.CLASS_MARKER + Constants.CLASSPATH_VOID;

  public static final String CLASS_BINARYNAME_DOUBLE =
      Constants.CLASS_MARKER + Constants.CLASSPATH_DOUBLE;
  public static final String CLASS_BINARYNAME_FLOAT =
      Constants.CLASS_MARKER + Constants.CLASSPATH_FLOAT;
  public static final String CLASS_BINARYNAME_LONG =
      Constants.CLASS_MARKER + Constants.CLASSPATH_LONG;
  public static final String CLASS_BINARYNAME_INT =
      Constants.CLASS_MARKER + Constants.CLASSPATH_INT;
  public static final String CLASS_BINARYNAME_SHORT =
      Constants.CLASS_MARKER + Constants.CLASSPATH_SHORT;
  public static final String CLASS_BINARYNAME_BYTE =
      Constants.CLASS_MARKER + Constants.CLASSPATH_BYTE;
  public static final String CLASS_BINARYNAME_BOOLEAN =
      Constants.CLASS_MARKER + Constants.CLASSPATH_BOOLEAN;
  public static final String CLASS_BINARYNAME_CHAR =
      Constants.CLASS_MARKER + Constants.CLASSPATH_CHAR;
  public static final String CLASS_BINARYNAME_STRING =
      Constants.CLASS_MARKER + Constants.CLASSPATH_STRING;
}
