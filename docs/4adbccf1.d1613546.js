(window.webpackJsonp=window.webpackJsonp||[]).push([[34],{101:function(e,t,n){"use strict";n.r(t),n.d(t,"frontMatter",(function(){return i})),n.d(t,"metadata",(function(){return a})),n.d(t,"rightToc",(function(){return s})),n.d(t,"default",(function(){return b}));var r=n(3),o=n(7),c=(n(0),n(163)),i={id:"robolectric",title:"Robolectric",sidebar_label:"Robolectric",slug:"robolectric.html"},a={unversionedId:"extensions/robolectric",id:"extensions/robolectric",isDocsHomePage:!1,title:"Robolectric",description:"Robolectric",source:"@site/docs/extensions/roboelectric.md",slug:"/extensions/robolectric.html",permalink:"/docs/extensions/robolectric.html",editUrl:"https://github.com/kotest/kotest/blob/master/documentation/docs/extensions/roboelectric.md",version:"current",sidebar_label:"Robolectric",sidebar:"extensions",previous:{title:"WireMock",permalink:"/docs/extensions/wiremock.html"},next:{title:"Pitest",permalink:"/docs/extensions/pitest.html"}},s=[{value:"Robolectric",id:"robolectric",children:[]}],l={rightToc:s};function b(e){var t=e.components,n=Object(o.a)(e,["components"]);return Object(c.b)("wrapper",Object(r.a)({},l,n,{components:t,mdxType:"MDXLayout"}),Object(c.b)("h2",{id:"robolectric"},"Robolectric"),Object(c.b)("p",null,Object(c.b)("a",Object(r.a)({parentName:"p"},{href:"http://robolectric.org/"}),"Robolectric")," can be used with Kotest through the ",Object(c.b)("inlineCode",{parentName:"p"},"RobolectricExtension")," which can be found in a separate repository,",Object(c.b)("a",Object(r.a)({parentName:"p"},{href:"https://github.com/kotest/kotest-extensions-robolectric"}),"kotest-extensions-robolectric")),Object(c.b)("p",null,"To add this module to project you need specify following in your ",Object(c.b)("inlineCode",{parentName:"p"},"build.gradle"),":"),Object(c.b)("p",null,Object(c.b)("a",Object(r.a)({parentName:"p"},{href:"https://search.maven.org/artifact/io.kotest.extensions/kotest-extensions-robolectric"}),Object(c.b)("img",Object(r.a)({parentName:"a"},{src:"https://img.shields.io/maven-central/v/io.kotest.extensions/kotest-extensions-robolectric",alt:"Latest Release"})))),Object(c.b)("pre",null,Object(c.b)("code",Object(r.a)({parentName:"pre"},{className:"language-kotlin"}),'testImplementation("io.kotest.extensions:kotest-extensions-robolectric:${version}")\n')),Object(c.b)("p",null,"This dependency brings in ",Object(c.b)("inlineCode",{parentName:"p"},"RobolectricExtension"),", which is autoregistered to your projects."),Object(c.b)("p",null,"Now all you need to do is annotate Robolectric specs with ",Object(c.b)("inlineCode",{parentName:"p"},"@RobolectricTest")," and you're set!"),Object(c.b)("pre",null,Object(c.b)("code",Object(r.a)({parentName:"pre"},{className:"language-kotlin"}),'@RobolectricTest\nclass MyTest : ShouldSpec({\n    should("Access Robolectric normally!") {\n    \n    }\n})\n')))}b.isMDXComponent=!0},163:function(e,t,n){"use strict";n.d(t,"a",(function(){return p})),n.d(t,"b",(function(){return m}));var r=n(0),o=n.n(r);function c(e,t,n){return t in e?Object.defineProperty(e,t,{value:n,enumerable:!0,configurable:!0,writable:!0}):e[t]=n,e}function i(e,t){var n=Object.keys(e);if(Object.getOwnPropertySymbols){var r=Object.getOwnPropertySymbols(e);t&&(r=r.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),n.push.apply(n,r)}return n}function a(e){for(var t=1;t<arguments.length;t++){var n=null!=arguments[t]?arguments[t]:{};t%2?i(Object(n),!0).forEach((function(t){c(e,t,n[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(n)):i(Object(n)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(n,t))}))}return e}function s(e,t){if(null==e)return{};var n,r,o=function(e,t){if(null==e)return{};var n,r,o={},c=Object.keys(e);for(r=0;r<c.length;r++)n=c[r],t.indexOf(n)>=0||(o[n]=e[n]);return o}(e,t);if(Object.getOwnPropertySymbols){var c=Object.getOwnPropertySymbols(e);for(r=0;r<c.length;r++)n=c[r],t.indexOf(n)>=0||Object.prototype.propertyIsEnumerable.call(e,n)&&(o[n]=e[n])}return o}var l=o.a.createContext({}),b=function(e){var t=o.a.useContext(l),n=t;return e&&(n="function"==typeof e?e(t):a(a({},t),e)),n},p=function(e){var t=b(e.components);return o.a.createElement(l.Provider,{value:t},e.children)},u={inlineCode:"code",wrapper:function(e){var t=e.children;return o.a.createElement(o.a.Fragment,{},t)}},d=o.a.forwardRef((function(e,t){var n=e.components,r=e.mdxType,c=e.originalType,i=e.parentName,l=s(e,["components","mdxType","originalType","parentName"]),p=b(n),d=r,m=p["".concat(i,".").concat(d)]||p[d]||u[d]||c;return n?o.a.createElement(m,a(a({ref:t},l),{},{components:n})):o.a.createElement(m,a({ref:t},l))}));function m(e,t){var n=arguments,r=t&&t.mdxType;if("string"==typeof e||r){var c=n.length,i=new Array(c);i[0]=d;var a={};for(var s in t)hasOwnProperty.call(t,s)&&(a[s]=t[s]);a.originalType=e,a.mdxType="string"==typeof e?e:r,i[1]=a;for(var l=2;l<c;l++)i[l]=n[l];return o.a.createElement.apply(null,i)}return o.a.createElement.apply(null,n)}d.displayName="MDXCreateElement"}}]);