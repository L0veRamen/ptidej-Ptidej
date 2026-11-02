package padl.visitor.traversal;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import junit.framework.TestCase;
import padl.kernel.Cardinality;
import padl.kernel.IAbstractModel;
import padl.kernel.IAssociation;
import padl.kernel.IClass;
import padl.kernel.IConstituent;
import padl.kernel.IConstructor;
import padl.kernel.IFactory;
import padl.kernel.IField;
import padl.kernel.IGetter;
import padl.kernel.IGhost;
import padl.kernel.IInterface;
import padl.kernel.IMemberClass;
import padl.kernel.IMethod;
import padl.kernel.IMethodInvocation;
import padl.kernel.IOperation;
import padl.kernel.IPackage;
import padl.kernel.IParameter;
import padl.kernel.ISetter;
import padl.kernel.impl.Factory;
import padl.visitor.IWalker;
import padl.visitor.repository.JavaGenerator;

public class TraversalTest extends TestCase {
  private final IFactory factory = Factory.getInstance();
  private IAbstractModel model;

  public TraversalTest(final String name) {
    super(name);
  }

  protected void setUp() {
    this.model = this.createModel(true);
  }

  /**
   * The container composition of this model has no origin field, getter, or setter, which makes
   * JavaGenerator throw a NullPointerException when visiting it.
   */
  private IAbstractModel createModel(final boolean withContainerComposition) {
    final IAbstractModel aModel = this.factory.createCodeLevelModel("TraversalModel");

    final IPackage aPackage = this.factory.createPackage("p".toCharArray());
    aModel.addConstituent(aPackage);

    final IClass b = this.factory.createClass("p.B".toCharArray(), "B".toCharArray());
    final IInterface i = this.factory.createInterface("p.I".toCharArray(), "I".toCharArray());
    final IGhost g = this.factory.createGhost("p.G".toCharArray(), "G".toCharArray());
    final IClass a = this.factory.createClass("p.A".toCharArray(), "A".toCharArray());
    aPackage.addConstituent(a);
    aPackage.addConstituent(b);
    aPackage.addConstituent(i);
    aPackage.addConstituent(g);

    final IField field =
        this.factory.createField("f".toCharArray(), "f".toCharArray(), "int".toCharArray(), 0);
    a.addConstituent(field);

    final IConstructor constructor =
        this.factory.createConstructor("A()".toCharArray(), "A".toCharArray());
    a.addConstituent(constructor);

    final IMethod method = this.factory.createMethod("m(B)".toCharArray(), "m".toCharArray());
    final IParameter parameter = this.factory.createParameter(b, "aB".toCharArray(), 0);
    method.addConstituent(parameter);
    final IMethodInvocation invocation =
        this.factory.createMethodInvocation(
            IMethodInvocation.CLASS_INSTANCE, Cardinality.One, 0, b);
    method.addConstituent(invocation);
    a.addConstituent(method);

    final IGetter getter = this.factory.createGetter("getF()".toCharArray(), "getF".toCharArray());
    a.addConstituent(getter);
    final ISetter setter = this.factory.createSetter("setF()".toCharArray(), "setF".toCharArray());
    a.addConstituent(setter);

    final IAssociation association =
        this.factory.createAssociationRelationship("toB".toCharArray(), b, Cardinality.One);
    a.addConstituent(association);
    a.addConstituent(
        this.factory.createAggregationRelationship("aggB".toCharArray(), b, Cardinality.Many));
    a.addConstituent(
        this.factory.createCompositionRelationship("compB".toCharArray(), b, Cardinality.Many));
    if (withContainerComposition) {
      a.addConstituent(
          this.factory.createContainerCompositionRelationship(
              "contB".toCharArray(), b, Cardinality.Many));
    }
    a.addConstituent(
        this.factory.createCreationRelationship("newB".toCharArray(), b, Cardinality.One));

    final IMemberClass member =
        this.factory.createMemberClass("p.A$M".toCharArray(), "M".toCharArray());
    member.addConstituent(
        this.factory.createField("g".toCharArray(), "g".toCharArray(), "int".toCharArray(), 0));
    a.addConstituent(member);

    return aModel;
  }

  /**
   * A walker recording each call as "name(DeclaredParameterType) constituent", so the events show
   * which overload of open(), close(), or visit() each dispatch selected.
   */
  private static IWalker createRecorder(final List<String> events) {
    return (IWalker)
        Proxy.newProxyInstance(
            IWalker.class.getClassLoader(),
            new Class[] {IWalker.class},
            (proxy, method, args) -> {
              switch (method.getName()) {
                case "getName":
                  return "Recorder";
                case "getResult":
                  return events;
                case "reset":
                  events.clear();
                  return null;
                case "unknownConstituentHandler":
                  events.add("unknown " + ((IConstituent) args[1]).getDisplayID());
                  return null;
                default:
                  final Object argument = args[0];
                  final String name =
                      argument instanceof IConstituent
                          ? ((IConstituent) argument).getDisplayID()
                          : ((IAbstractModel) argument).getDisplayName();
                  events.add(
                      method.getName()
                          + '('
                          + method.getParameterTypes()[0].getSimpleName()
                          + ") "
                          + name);
                  return null;
              }
            });
  }

  private List<String> recordWithAccept() {
    final List<String> events = new ArrayList<>();
    this.model.walk(TraversalTest.createRecorder(events));
    return events;
  }

  private List<String> recordWith(final ITraversal aTraversal) {
    final List<String> events = new ArrayList<>();
    aTraversal.walk(this.model, TraversalTest.createRecorder(events));
    return events;
  }

  private static Map<String, Integer> count(final List<String> events) {
    final Map<String, Integer> counts = new HashMap<>();
    for (final String event : events) {
      counts.merge(event, 1, Integer::sum);
    }
    return counts;
  }

  private static int indexOfFirst(final List<String> events, final String prefix) {
    for (int i = 0; i < events.size(); i++) {
      if (events.get(i).startsWith(prefix)) {
        return i;
      }
    }
    return -1;
  }

  public void testDepthFirstCallsVisitorLikeAccept() {
    final List<String> expected = this.recordWithAccept();
    final List<String> actual = this.recordWith(new DepthFirstTraversal());

    assertEquals(expected, actual);
  }

  public void testDepthFirstGeneratesSameCodeAsAccept() {
    final IAbstractModel aModel = this.createModel(false);
    final String expected = aModel.generate(new JavaGenerator());
    final String actual = new DepthFirstTraversal().generate(aModel, new JavaGenerator());

    assertTrue("The generated code should not be empty", expected.length() > 0);
    assertEquals(expected, actual);
  }

  public void testTypedDispatchSelectsMostSpecificOverload() {
    final List<String> events = this.recordWith(new DepthFirstTraversal());

    assertTrue(events.contains("open(IPackage) p"));
    assertTrue(events.contains("open(IClass) p.A"));
    assertTrue(events.contains("open(IInterface) p.I"));
    assertTrue(events.contains("open(IGhost) p.G"));
    assertTrue(events.contains("open(IMemberClass) p.A$M"));
    assertTrue(events.contains("open(IConstructor) A()"));
    assertTrue(events.contains("open(IMethod) m(B)"));
    assertTrue(events.contains("open(IGetter) getF()"));
    assertTrue(events.contains("open(ISetter) setF()"));
    assertTrue(events.contains("visit(IField) f"));
    // Parameters, invocations, and relationships get a generated suffix in their IDs.
    assertTrue(indexOfFirst(events, "visit(IParameter) Parameter") >= 0);
    assertTrue(indexOfFirst(events, "visit(IMethodInvocation) Method Invocation") >= 0);
    assertTrue(indexOfFirst(events, "visit(IAssociation) toB") >= 0);
    assertTrue(indexOfFirst(events, "visit(IAggregation) aggB") >= 0);
    assertTrue(indexOfFirst(events, "visit(IComposition) compB") >= 0);
    assertTrue(indexOfFirst(events, "visit(IContainerComposition) contB") >= 0);
    assertTrue(indexOfFirst(events, "visit(ICreation) newB") >= 0);
  }

  public void testBreadthFirstVisitsLevelByLevel() {
    final List<String> depthFirst = this.recordWith(new DepthFirstTraversal());
    final List<String> breadthFirst = this.recordWith(new BreadthFirstTraversal());

    // Same calls, different order.
    assertEquals(count(depthFirst), count(breadthFirst));
    assertFalse(depthFirst.equals(breadthFirst));

    // Model first and last.
    assertEquals("open(IAbstractModel) TraversalModel", breadthFirst.get(0));
    assertEquals(
        "close(IAbstractModel) TraversalModel", breadthFirst.get(breadthFirst.size() - 1));

    // Depth 2 (entities) before depth 3 (their members) before depth 4 (parameters, fields of
    // member classes).
    final int lastEntity = breadthFirst.lastIndexOf("close(IGhost) p.G");
    final int firstMember = indexOfFirst(breadthFirst, "visit(IField) f");
    final int lastMember = breadthFirst.lastIndexOf("close(IMemberClass) p.A$M");
    final int firstParameter = indexOfFirst(breadthFirst, "visit(IParameter)");
    assertTrue(lastEntity < firstMember);
    assertTrue(lastMember < firstParameter);
    assertTrue(breadthFirst.indexOf("visit(IField) g") > lastMember);

    // Containers are opened and closed immediately.
    final int open = breadthFirst.indexOf("open(IClass) p.A");
    assertEquals("close(IClass) p.A", breadthFirst.get(open + 1));
  }

  public void testFilterPrunesWholeSubtree() {
    final List<String> events =
        this.recordWith(
            new DepthFirstTraversal(aConstituent -> !(aConstituent instanceof IOperation)));

    assertEquals(-1, indexOfFirst(events, "open(IMethod)"));
    assertEquals(-1, indexOfFirst(events, "open(IConstructor)"));
    assertEquals(-1, indexOfFirst(events, "open(IGetter)"));
    assertEquals(-1, indexOfFirst(events, "visit(IParameter)"));
    assertEquals(-1, indexOfFirst(events, "visit(IMethodInvocation)"));
    assertTrue(events.contains("visit(IField) f"));
    assertTrue(events.contains("visit(IField) g"));
    assertTrue(events.contains("open(IClass) p.A"));
  }

  public void testVisitorExceptionsArePropagatedUnlikeAccept() {
    // The reflective accept() catches the exception thrown by visit(IContainerComposition),
    // retries with visit(IContainerAggregation), which throws again, then reports the constituent
    // as unknown: the error is lost and the partial output of both attempts stays in the code.
    final String code = this.model.generate(new JavaGenerator());
    final String partialOutput = "// Aggregation: padl.kernel.impl.ContainerComposition";
    assertEquals(2, code.split(partialOutput, -1).length - 1);

    // The typed dispatch calls the visitor directly, so the exception reaches the caller.
    try {
      new DepthFirstTraversal().generate(this.model, new JavaGenerator());
      fail("The NullPointerException thrown by JavaGenerator should not be swallowed");
    } catch (final NullPointerException e) {
      // Expected.
    }
  }
}
