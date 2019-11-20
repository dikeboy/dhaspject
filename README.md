## What's this

An gradle plugin  for android project  aspject

Support for multiple  library, Kotlin, Java

Support for  debug mode,release mode or close

Support for multiple flavors

## How to use

project:  build.gradle   

     dependencies {
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
        classpath 'org.aspectj:aspectjtools:1.8.9'
       classpath 'com.dhaspject:dhaspject:1.0.7'
    }
    



app module(main module): build.gradle

    apply plugin: "com.dhaspject"
    laop{
    kotlinfiles("testpro.com","com.kotlin")  //kotlin package need to be aspject
    javafiles("com.lin")  //java package need to be aspject
    aopModule("api_log") // the aop libray name 
    aopType("debug")  // aop type, debug: debug mode, default: always use, close: unuse
       hasFlavors(true) //if have flavors
}

`dependencies{
...
implementation 'org.aspectj:aspectjrt:1.8.9'
}`




api_log( aop module): build.gradle( the aop module, you can modify the name by yourself)

    apply plugin: "com.dhaspject"
    laop{
     aopModule("api_log")
    aopType("debug")
}


`dependencies{
...
implementation 'org.aspectj:aspectjrt:1.8.9'
}`


other module need aspject:
my_lib: build.gradle

    apply plugin: "com.dhaspject"
    laop{
    javafiles("com.lance.aspectlib") // witch package need to be aspject
    aopModule("api_log") //the aop modle
    aopType("debug")
}

`dependencies{
...
implementation 'org.aspectj:aspectjrt:1.8.9'
}`

### 参考
* [Dhaspject Demo](https://github.com/dikeboy/DhaspjectDemo)
* [android 无痕打点](https://github.com/dikeboy/IntrusiveClick)
* [AspectJ官网](https://eclipse.org/aspectj/)
