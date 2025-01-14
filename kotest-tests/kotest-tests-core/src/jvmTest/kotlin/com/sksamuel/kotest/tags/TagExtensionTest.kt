package com.sksamuel.kotest.tags

import io.kotest.core.Tag
import io.kotest.core.Tags
import io.kotest.core.config.configuration
import io.kotest.core.extensions.TagExtension
import io.kotest.core.spec.Isolate
import io.kotest.core.spec.Spec
import io.kotest.core.spec.style.StringSpec
import io.kotest.core.test.TestStatus
import io.kotest.matchers.shouldBe

@Isolate
class TagExtensionTest : StringSpec() {

   object TagA : Tag()
   object TagB : Tag()

   private val ext = object : TagExtension {
      override fun tags(): Tags =
         Tags(setOf(TagA), setOf(TagB))
   }


   override fun beforeSpec(spec: Spec) {
      configuration.registerExtension(ext)
   }

   override fun afterSpec(spec: Spec) {
      configuration.deregisterExtension(ext)
   }

   init {

      finalizeSpec { results ->
         results.b.map { it.key.name.testName to it.value.status }.toMap() shouldBe mapOf(
            "should be tagged with tagA and therefore included" to TestStatus.Success,
            "should be untagged and therefore excluded" to TestStatus.Ignored,
            "should be tagged with tagB and therefore excluded" to TestStatus.Ignored
         )
      }

      "should be tagged with tagA and therefore included".config(tags = setOf(TagA)) { }

      "should be untagged and therefore excluded" { }

      "should be tagged with tagB and therefore excluded".config(tags = setOf(TagB)) { }
   }
}
