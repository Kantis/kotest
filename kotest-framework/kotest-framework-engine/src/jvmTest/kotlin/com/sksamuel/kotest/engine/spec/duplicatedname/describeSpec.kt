package com.sksamuel.kotest.engine.spec.duplicatedname

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

abstract class DescribeSpecDuplicateTestNameModeInfoTest(iso: IsolationMode) : DescribeSpec() {
   init {
      isolationMode = iso
      describe("foo") {
         it("woo") {}
         it("woo") {
            this.testCase.name.testName shouldBe "(1) woo"
         }
         it("woo") {
            this.testCase.name.testName shouldBe "(2) woo"
         }
      }
      describe("foo") {
         this.testCase.name.testName shouldBe "(1) foo"
      }
      describe("foo") {
         this.testCase.name.testName shouldBe "(2) foo"
      }
      context("goo") {}
      context("goo") {
         this.testCase.name.testName shouldBe "(1) goo"
      }
      context("goo") {
         this.testCase.name.testName shouldBe "(2) goo"
      }
   }
}

class DescribeSpecSingleInstanceDuplicateTestNameModeInfoTest : DescribeSpecDuplicateTestNameModeInfoTest(IsolationMode.SingleInstance)
class DescribeSpecInstancePerLeafDuplicateTestNameModeInfoTest : DescribeSpecDuplicateTestNameModeInfoTest(IsolationMode.InstancePerLeaf)
class DescribeSpecInstancePerTestDuplicateTestNameModeInfoTest : DescribeSpecDuplicateTestNameModeInfoTest(IsolationMode.InstancePerTest)
