package a.b;

import static org.assertj.core.api.Assertions.assertThat;

import javassist.ClassPool;
import javassist.CtClass;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class PatchingTest {
  @ParameterizedTest
  @ValueSource(strings={
      "a.Empty", // fails
      "a.b.Empty", // succeeds
      "a.b.c.Empty", // fails
      "x.x.x.Empty", // fails
  })
  public void testPackageNames(String className) throws Exception {
    ClassPool cp = new ClassPool(true);
    CtClass ctClass = cp.get(className);

    assertThat(ctClass.getPackageName()).isNotNull();

    Class<?> c = ctClass.toClass();

    assertThat(c.getPackage()).isNotNull();
  }

  @Test
  public void testClassInitialization() throws Exception {
    String name = "x.x.x.ClassWithStaticInitializer";
    ClassPool cp = new ClassPool(true);
    CtClass ctClass = cp.get(name);

    Class<?> c = ctClass.toClass();

    // same behaviour with explicit constructor call
    c.newInstance();
  }
}
