package io.kotest.engine.reporter

import com.github.ajalt.mordant.TermColors
import io.kotest.core.descriptors.Descriptor
import io.kotest.core.config.configuration
import io.kotest.core.descriptors.spec
import io.kotest.core.descriptors.toDescriptor
import io.kotest.core.extensions.formatTestPath
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.core.test.TestStatus
import io.kotest.core.test.TestType
import io.kotest.engine.test.names.DefaultDisplayNameFormatter
import io.kotest.engine.test.names.getDisplayNameFormatter
import kotlin.reflect.KClass

/**
 * Generates test output in the 'augustus' Kotest style.
 */
class TaycanConsoleReporter : ConsoleReporter {

   private var term: TermColors = TermColors()

   override fun setTerm(term: TermColors) {
      this.term = term
   }

   private var errors = 0
   private var start = System.currentTimeMillis()
   private var testsFailed = emptyList<Pair<TestCase, TestResult>>()
   private var testsIgnored = 0
   private var testsPassed = 0
   private var specsFailed = emptyList<Descriptor.SpecDescriptor>()
   private var specsSeen = emptyList<Descriptor>()
   private var slow = 500
   private var verySlow = 5000
   private val formatter = getDisplayNameFormatter(configuration)

   private fun green(str: String) = term.green(str)
   private fun greenBold(str: String) = term.green.plus(term.bold).invoke(str)
   private fun red(str: String) = term.red(str)
   private fun brightRed(str: String) = term.brightRed(str)
   private fun brightRedBold(str: String) = term.brightRed.plus(term.bold).invoke(str)
   private fun redBold(str: String) = term.red.plus(term.bold).invoke(str)
   private fun yellow(str: String) = term.yellow(str)
   private fun brightYellow(str: String) = term.brightYellow(str)
   private fun brightYellowBold(str: String) = term.brightYellow.plus(term.bold).invoke(str)
   private fun yellowBold(str: String) = term.yellow.plus(term.bold).invoke(str)
   private fun bold(str: String) = term.bold(str)

   private val intros = listOf(
      "Feeding the kotest engine with freshly harvested tests",
      "Engaging kotest engine at warp factor 9",
      "Harvesting the test fields",
      "Preparing to sacrifice your code to the demi-god of test",
      "Hamsters are turning the wheels of kotest",
      "Battle commanders are ready to declare war on bugs",
      "Be afraid - be very afraid - of failing tests",
      "The point is, ladies and gentlemen, that green is good",
      "Lock test-foils in attack position",
      "Fasten your seatbelts. It's going to be a bumpy test-run",
      "Lets crack open this test suite",
      "Lets get testing, I'm on the clock here",
      "Test time is the best time",
      "Open the test suite doors, HAL",
      "Mama always said testing was like a box of chocolates. You don't know which ones are gonna fail",
      "A test suite. Shaken, not stirred",
      "I'm going to make him a test he can't refuse",
      "You testing me? I don't see any other tests here, so you must be testing me",
      "I love the smell of tests in the morning",
      "Do you feel lucky punk? Do you think your tests will pass? Well, do ya?",
      "Mirab, with tests unfurled",
      "Dolly works 9 to 5. I test 24/7",
      "A test suite's gotta do what a test suite's gotta do",
      "I test code and chew bubblegum, and I'm all out of bubblegum"
   )

   override fun engineStarted(classes: List<KClass<*>>) {
      println(bold(">> Kotest"))
      println("- " + intros.shuffled().first())
      print("- Test plan has ")
      print(greenBold(classes.size.toString()))
      println(" specs")
      println()
   }

   override fun hasErrors(): Boolean = errors > 0

   override fun engineFinished(t: List<Throwable>) {

      if (specsSeen.isEmpty()) return

      if (t.isNotEmpty()) {
         errors += t.size
         t.forEach {
            printThrowable(it, 0)
         }
      }

      val duration = System.currentTimeMillis() - start
      val seconds = duration / 1000

      if (errors == 0) {
         println(bold(">> All tests passed"))
      } else {
         println(redBold(">> There were test failures"))
         println()
         specsFailed.distinct().forEach { spec ->
            println(brightRedBold(" ${formatter.format(spec.kclass)}"))
            testsFailed.filter { it.first.spec::class.toDescriptor() == spec }.forEach { (testCase, _) ->
               println(brightRed(" - ${formatter.formatTestPath(testCase, " -- ")}"))
            }
         }
      }

      println()
      printSpecCounts()
      printTestsCounts()
      print("Time:    ")
      println(bold("${seconds}s"))
   }

   private fun printThrowable(error: Throwable?, padding: Int) {
      if (error != null) {
         val message = error.message
         if (message != null) {
            println(brightRed(message.padStart(padding, ' ')))
         }
         error.stackTrace?.forEach {
            println(red("".padStart(padding + 2, ' ') + it))
         }
      }
   }

   private fun printSpecCounts() {
      val specsSeenSize = specsSeen.distinct().size
      val specsPassedSize = specsSeen.distinct().minus(specsFailed).size
      val specsFailedSize = specsFailed.distinct().size
      print("Specs:   ")
      print(greenBold("$specsPassedSize passed"))
      print(", ")
      if (specsFailed.isEmpty()) {
         print(bold("$specsFailedSize failed"))
         print(bold(", "))
      } else {
         print(redBold("$specsFailedSize failed"))
         print(bold(", "))
      }
      println("$specsSeenSize total")
   }

   private fun printTestsCounts() {
      print("Tests:   ")
      print(greenBold("$testsPassed passed"))
      print(", ")
      if (testsFailed.isEmpty()) {
         print(bold("${testsFailed.size} failed"))
         print(", ")
      } else {
         print(redBold("${testsFailed.size} failed"))
         print(", ")
      }
      if (testsIgnored > 0) {
         print(yellowBold("$testsIgnored ignored"))
         print(", ")
      } else {
         print(bold("$testsIgnored ignored"))
         print(", ")
      }
      println("${testsPassed + testsFailed.size + testsIgnored} total")
   }

   override fun specStarted(kclass: KClass<*>) {
      specsSeen = specsSeen + kclass.toDescriptor()
      val specCount = specsSeen.size
      print(bold("$specCount. ".padEnd(4, ' ')))
      println(bold(formatter.format(kclass)))
   }

   override fun specFinished(kclass: KClass<*>, t: Throwable?, results: Map<TestCase, TestResult>) {
      if (t != null) {
         errors++
         specsFailed = specsFailed + kclass.toDescriptor()
         printThrowable(t, 4)
      }
      println()
   }

   override fun testIgnored(testCase: TestCase) {
      testsIgnored++
      print("".padEnd(testCase.descriptor.depth() * 4, ' '))
      print("- " + formatter.format(testCase))
      println(brightYellowBold(" IGNORED"))
   }

   private fun durationString(durationMs: Long): String {
      return when {
         durationMs in slow..verySlow -> term.brightYellow("(${durationMs}ms)")
         durationMs > verySlow -> term.brightRed("(${durationMs}ms)")
         else -> ""
      }
   }

   override fun testFinished(testCase: TestCase, result: TestResult) {
      // only leaf tests or failed containers contribute to the counts
      when (result.status) {
         TestStatus.Success -> if (testCase.type == TestType.Test) testsPassed++
         TestStatus.Failure, TestStatus.Error -> {
            errors++
            testsFailed = testsFailed + Pair(testCase, result)
            specsFailed = specsFailed + testCase.descriptor.spec()
         }
         else -> Unit
      }

      // we only print the name and status for leafs, as containers are printed in advance
      if (testCase.type == TestType.Test) {
         print("".padEnd(testCase.descriptor.depth() * 4, ' '))
         print("- " + formatter.format(testCase))
         when (result.status) {
            TestStatus.Success -> print(greenBold(" OK"))
            TestStatus.Error -> print(brightRed(" ERROR"))
            TestStatus.Failure -> print(brightRed(" FAILED"))
            TestStatus.Ignored -> print(brightYellow(" IGNORED"))
         }

         if (result.duration > slow) {
            print(" ${durationString(result.duration)}")
         }
         println()
      }

      if (result.error != null) {
         println()
         printThrowable(result.error, testCase.descriptor.depth() * 4)
         println()
      }
   }

   override fun testStarted(testCase: TestCase) {
      // containers we display straight away without pass / fail message
      if (testCase.type == TestType.Container) {
         print("".padEnd(testCase.descriptor.depth() * 4, ' '))
         println("+ " + formatter.format(testCase))
      }
   }
}
