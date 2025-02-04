//package io.kotest.core.script
//
//import io.kotest.core.names.TestName
//import io.kotest.core.plan.Descriptor
//import io.kotest.core.plan.DisplayName
//import io.kotest.core.plan.Name
//import io.kotest.core.Source
//import io.kotest.core.plan.toDescriptor
//import io.kotest.core.sourceRef
//import io.kotest.core.test.Description
//import io.kotest.core.test.TestCaseConfig
//import io.kotest.core.test.TestContext
//import io.kotest.core.test.TestType
//import io.kotest.core.test.createNestedTest
//
///**
// * Registers a root test, with the given name.
// */
//fun test(name: String, test: suspend TestContext.() -> Unit) {
//   ScriptRuntime.registerRootTest(TestName(name), false, TestType.Test, test)
//}
//
///**
// * Registers a root test, with the given name.
// */
//fun should(name: String, test: suspend TestContext.() -> Unit) {
//   ScriptRuntime.registerRootTest(TestName(name), false, TestType.Test, test)
//}
//
///**
// * Registers a root context scope, which allows further tests to be registered with test and should keywords.
// */
//fun context(name: String, test: suspend ContextScope.() -> Unit) {
//   val testName = TestName(name)
//   val description = ScriptSpec().description().append(testName, TestType.Container)
//   val d = description.toDescriptor(sourceRef()).append(
//      Name(testName.name),
//      DisplayName(testName.displayName),
//      TestType.Container,
//      Source.TestSource(sourceRef().fileName, sourceRef().lineNumber),
//   )
//   ScriptRuntime.registerRootTest(testName, false, TestType.Container) {
//      ContextScope(
//         description,
//         it,
//         d
//      ).test()
//   }
//}
//
//class ContextScope(
//   val description: Description,
//   val testContext: TestContext,
//   val descriptor: Descriptor,
//) {
//
//   suspend fun test(name: String, test: suspend TestContext.() -> Unit) {
//      val testName = TestName(name)
//      registerNestedTest(
//         name = testName,
//         testContext = testContext,
//         xdisabled = false,
//         test = test,
//         config = TestCaseConfig(),
//         type = TestType.Test,
//         descriptor = descriptor.append(
//            Name(testName.name),
//            DisplayName(testName.displayName),
//            TestType.Test,
//            Source.TestSource(sourceRef().fileName, sourceRef().lineNumber),
//         ),
//      )
//   }
//
//   suspend fun should(name: String, test: suspend TestContext.() -> Unit) {
//      val testName = TestName(name)
//      registerNestedTest(
//         name = testName,
//         testContext = testContext,
//         xdisabled = false,
//         test = test,
//         config = TestCaseConfig(),
//         type = TestType.Test,
//         descriptor = descriptor.append(
//            Name(testName.name),
//            DisplayName(testName.displayName),
//            TestType.Test,
//            Source.TestSource(sourceRef().fileName, sourceRef().lineNumber),
//         ),
//      )
//   }
//}
//
///**
// * Registers a root 'describe' scope, with the given name.
// */
//fun describe(name: String, test: suspend DescribeScope.() -> Unit) {
//   val testName = TestName("Describe: ", name, false)
//   val description = ScriptSpec().description().append(testName, TestType.Container)
//   val d = description.toDescriptor(sourceRef()).append(
//      Name(testName.name),
//      DisplayName(testName.displayName),
//      TestType.Container,
//      Source.TestSource(sourceRef().fileName, sourceRef().lineNumber),
//   )
//   ScriptRuntime.registerRootTest(
//      testName,
//      false,
//      TestType.Container
//   ) { DescribeScope(description, it, d).test() }
//}
//
//class DescribeScope(
//   val description: Description,
//   val testContext: TestContext,
//   val descriptor: Descriptor,
//) {
//
//   /**
//    * Registers a nested 'describe' scope, with the given name.
//    */
//   suspend fun describe(name: String, test: suspend DescribeScope.() -> Unit) {
//      val testName = TestName("Describe: ", name, false)
//      val d = descriptor.append(
//         Name(testName.name),
//         DisplayName(testName.displayName),
//         TestType.Container,
//         Source.TestSource(sourceRef().fileName, sourceRef().lineNumber),
//      )
//      registerNestedTest(
//         name = testName,
//         testContext = testContext,
//         xdisabled = false,
//         test = { DescribeScope(description.append(testName, TestType.Test), testContext, d).test() },
//         config = TestCaseConfig(),
//         type = TestType.Test,
//         descriptor = d,
//      )
//   }
//
//   suspend fun it(name: String, test: suspend TestContext.() -> Unit) {
//      val testName = TestName(name)
//      registerNestedTest(
//         name = testName,
//         testContext = testContext,
//         xdisabled = false,
//         test = test,
//         config = TestCaseConfig(),
//         type = TestType.Test,
//         descriptor = descriptor.append(
//            Name(testName.name),
//            DisplayName(testName.displayName),
//            TestType.Test,
//            Source.TestSource(sourceRef().fileName, sourceRef().lineNumber),
//         ),
//      )
//   }
//}
//
//private suspend fun registerNestedTest(
//   descriptor: Descriptor.TestDescriptor,
//   name: TestName,
//   xdisabled: Boolean,
//   test: suspend TestContext.() -> Unit,
//   config: TestCaseConfig,
//   testContext: TestContext,
//   type: TestType,
//) {
//   testContext.registerTestCase(
//      createNestedTest(
//         name = name,
//         xdisabled = xdisabled,
//         config = config,
//         type = type,
//         descriptor = descriptor,
//         factoryId = null,
//         test = test,
//      )
//   )
//}
